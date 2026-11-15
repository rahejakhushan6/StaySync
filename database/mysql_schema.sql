-- ========================================================
-- StaySync: Hostel Management System (MySQL Database Schema)
-- Relational schema for Users, Rooms, Beds, Fees, Complaints,
-- Leaves, Visitors, and Mess Menus
-- ========================================================

CREATE DATABASE IF NOT EXISTS staysync_db;
USE staysync_db;

-- 1. Users Table (Supports Student, Warden, Administrator, Maintenance, Security)
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(120) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    role ENUM('STUDENT', 'WARDEN', 'ADMINISTRATOR', 'MAINTENANCE', 'SECURITY') NOT NULL DEFAULT 'STUDENT',
    phone VARCHAR(20),
    emergency_contact VARCHAR(20),
    room_number VARCHAR(20) DEFAULT NULL,
    bed_number VARCHAR(10) DEFAULT NULL,
    student_id_number VARCHAR(50) DEFAULT NULL,
    department VARCHAR(80) DEFAULT NULL,
    year_of_study INT DEFAULT NULL,
    status ENUM('ACTIVE', 'SUSPENDED', 'ALUMNI') DEFAULT 'ACTIVE',
    avatar_url VARCHAR(255) DEFAULT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 2. Rooms Table
CREATE TABLE IF NOT EXISTS rooms (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    room_number VARCHAR(20) NOT NULL UNIQUE,
    block_name VARCHAR(50) NOT NULL,
    floor_number INT NOT NULL,
    room_type ENUM('SINGLE', 'DOUBLE', 'TRIPLE', 'DELUXE_AC') NOT NULL,
    capacity INT NOT NULL,
    occupied_count INT NOT NULL DEFAULT 0,
    monthly_rent DECIMAL(10, 2) NOT NULL,
    amenities VARCHAR(255) DEFAULT 'WiFi, Study Table, Wardrobe, Ceiling Fan',
    status ENUM('AVAILABLE', 'FULL', 'MAINTENANCE', 'RESERVED') DEFAULT 'AVAILABLE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 3. Beds Table
CREATE TABLE IF NOT EXISTS beds (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    room_id BIGINT NOT NULL,
    bed_number VARCHAR(10) NOT NULL,
    is_occupied BOOLEAN DEFAULT FALSE,
    student_id BIGINT DEFAULT NULL,
    assigned_date DATE DEFAULT NULL,
    FOREIGN KEY (room_id) REFERENCES rooms(id) ON DELETE CASCADE,
    FOREIGN KEY (student_id) REFERENCES users(id) ON DELETE SET NULL,
    UNIQUE KEY unique_room_bed (room_id, bed_number)
);

-- 4. Fees Table
CREATE TABLE IF NOT EXISTS fees (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id BIGINT NOT NULL,
    fee_type ENUM('HOSTEL_RENT', 'MESS_FEE', 'MAINTENANCE_DEPOSIT', 'SECURITY_DEPOSIT', 'FINE') NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    due_date DATE NOT NULL,
    paid_date DATE DEFAULT NULL,
    status ENUM('PENDING', 'PAID', 'OVERDUE', 'CANCELLED') DEFAULT 'PENDING',
    transaction_ref VARCHAR(100) DEFAULT NULL,
    payment_method VARCHAR(50) DEFAULT NULL,
    receipt_number VARCHAR(100) DEFAULT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (student_id) REFERENCES users(id) ON DELETE CASCADE
);

-- 5. Complaints Table (Integrated with AI Auto-Categorization)
CREATE TABLE IF NOT EXISTS complaints (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id BIGINT NOT NULL,
    title VARCHAR(150) NOT NULL,
    description TEXT NOT NULL,
    category ENUM('PLUMBING', 'ELECTRICAL', 'FURNITURE', 'CLEANING', 'WIFI_NETWORK', 'SECURITY', 'MESS_FOOD', 'OTHER') NOT NULL,
    priority ENUM('LOW', 'MEDIUM', 'HIGH', 'URGENT') DEFAULT 'MEDIUM',
    ai_predicted_category VARCHAR(50) DEFAULT NULL,
    ai_urgency_score DECIMAL(3, 2) DEFAULT NULL,
    ai_suggested_action TEXT DEFAULT NULL,
    status ENUM('SUBMITTED', 'IN_PROGRESS', 'RESOLVED', 'CLOSED', 'REJECTED') DEFAULT 'SUBMITTED',
    assigned_to_id BIGINT DEFAULT NULL,
    resolution_notes TEXT DEFAULT NULL,
    resolved_at TIMESTAMP NULL DEFAULT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (student_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (assigned_to_id) REFERENCES users(id) ON DELETE SET NULL
);

-- 6. Leave Applications Table (With Gate Pass & Warden Approval)
CREATE TABLE IF NOT EXISTS leaves (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id BIGINT NOT NULL,
    leave_type ENUM('HOME_VISIT', 'MEDICAL', 'ACADEMIC_CONFERENCE', 'EMERGENCY', 'VACATION') NOT NULL,
    departure_date DATETIME NOT NULL,
    expected_return_date DATETIME NOT NULL,
    actual_return_date DATETIME DEFAULT NULL,
    reason TEXT NOT NULL,
    destination_address TEXT NOT NULL,
    parent_contact VARCHAR(20) NOT NULL,
    status ENUM('PENDING', 'APPROVED', 'REJECTED', 'CHECKED_OUT', 'CHECKED_IN') DEFAULT 'PENDING',
    warden_remarks TEXT DEFAULT NULL,
    approved_by_id BIGINT DEFAULT NULL,
    gate_pass_token VARCHAR(100) DEFAULT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (student_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (approved_by_id) REFERENCES users(id) ON DELETE SET NULL
);

-- 7. Visitors Table (Security Log)
CREATE TABLE IF NOT EXISTS visitors (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    visitor_name VARCHAR(100) NOT NULL,
    visitor_phone VARCHAR(20) NOT NULL,
    id_proof_type ENUM('AADHAAR', 'PASSPORT', 'DRIVING_LICENSE', 'VOTER_ID', 'OTHER') NOT NULL,
    id_proof_number VARCHAR(50) NOT NULL,
    student_id BIGINT NOT NULL,
    relation_to_student VARCHAR(50) NOT NULL,
    purpose TEXT NOT NULL,
    check_in_time DATETIME NOT NULL,
    check_out_time DATETIME DEFAULT NULL,
    status ENUM('INSIDE', 'CHECKED_OUT') DEFAULT 'INSIDE',
    gate_officer_id BIGINT DEFAULT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (student_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (gate_officer_id) REFERENCES users(id) ON DELETE SET NULL
);

-- 8. Mess Menus Table
CREATE TABLE IF NOT EXISTS mess_menus (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    day_of_week ENUM('MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY', 'SATURDAY', 'SUNDAY') NOT NULL,
    meal_type ENUM('BREAKFAST', 'LUNCH', 'SNACKS', 'DINNER') NOT NULL,
    timing VARCHAR(50) NOT NULL,
    menu_items TEXT NOT NULL,
    dietary_info VARCHAR(100) DEFAULT 'Standard Veg / Non-Veg options',
    calories_est INT DEFAULT 650,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY unique_day_meal (day_of_week, meal_type)
);

-- Create Indexes for performance
CREATE INDEX idx_users_role ON users(role);
CREATE INDEX idx_fees_student ON fees(student_id);
CREATE INDEX idx_complaints_status ON complaints(status);
CREATE INDEX idx_leaves_status ON leaves(status);
CREATE INDEX idx_visitors_status ON visitors(status);
