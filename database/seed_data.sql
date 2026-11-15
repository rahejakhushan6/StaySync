-- ========================================================
-- StaySync: Seed Data for Initial Setup & Demo
-- Populates all 5 roles, rooms, beds, fees, complaints, leaves,
-- visitors, and weekly mess schedules.
-- ========================================================

-- Passwords hash for 'password123' (BCrypt $2a$10$7EqJtq98hPqEX7fNZaFWoOhtg1JgWl8Fq.e3rS9fFm2W4vR6Zp7uC or demo placeholder)
INSERT INTO users (id, name, email, password_hash, role, phone, emergency_contact, room_number, bed_number, student_id_number, department, year_of_study, status) VALUES
(1, 'Alex Rivers', 'alex.student@staysync.edu', '$2a$10$w09G9T.L0X1H6z.b546M7eu5610vL8M2CeqxH1J8m4O9s4K3L6Ueq', 'STUDENT', '+1-555-0101', '+1-555-9901', 'B-204', 'B-1', 'STU2024-889', 'Computer Science', 3, 'ACTIVE'),
(2, 'Sarah Chen', 'sarah.student@staysync.edu', '$2a$10$w09G9T.L0X1H6z.b546M7eu5610vL8M2CeqxH1J8m4O9s4K3L6Ueq', 'STUDENT', '+1-555-0102', '+1-555-9902', 'B-204', 'B-2', 'STU2024-890', 'Mechanical Engg', 2, 'ACTIVE'),
(3, 'Dr. Robert Sharma', 'warden@staysync.edu', '$2a$10$w09G9T.L0X1H6z.b546M7eu5610vL8M2CeqxH1J8m4O9s4K3L6Ueq', 'WARDEN', '+1-555-0201', '+1-555-9903', NULL, NULL, NULL, 'Chief Warden Office', NULL, 'ACTIVE'),
(4, 'Dean Eleanor Vance', 'admin@staysync.edu', '$2a$10$w09G9T.L0X1H6z.b546M7eu5610vL8M2CeqxH1J8m4O9s4K3L6Ueq', 'ADMINISTRATOR', '+1-555-0301', '+1-555-9904', NULL, NULL, NULL, 'Hostel Administration', NULL, 'ACTIVE'),
(5, 'Marcus Fixer', 'maintenance@staysync.edu', '$2a$10$w09G9T.L0X1H6z.b546M7eu5610vL8M2CeqxH1J8m4O9s4K3L6Ueq', 'MAINTENANCE', '+1-555-0401', '+1-555-9905', NULL, NULL, NULL, 'Facilities & Repairs', NULL, 'ACTIVE'),
(6, 'Officer Vikram Khan', 'security@staysync.edu', '$2a$10$w09G9T.L0X1H6z.b546M7eu5610vL8M2CeqxH1J8m4O9s4K3L6Ueq', 'SECURITY', '+1-555-0501', '+1-555-9906', NULL, NULL, NULL, 'Campus Security Gate 1', NULL, 'ACTIVE');

-- Rooms
INSERT INTO rooms (id, room_number, block_name, floor_number, room_type, capacity, occupied_count, monthly_rent, amenities, status) VALUES
(1, 'A-101', 'Aryabhata Block (East)', 1, 'SINGLE', 1, 1, 450.00, 'AC, Attached Bath, High-Speed LAN, Study Balcony', 'FULL'),
(2, 'A-102', 'Aryabhata Block (East)', 1, 'SINGLE', 1, 0, 450.00, 'AC, Attached Bath, High-Speed LAN, Study Balcony', 'AVAILABLE'),
(3, 'B-204', 'Bhabha Block (West)', 2, 'DOUBLE', 2, 2, 320.00, 'Ceiling Fan, Twin Desks, Wardrobe, Shared Bath', 'FULL'),
(4, 'B-205', 'Bhabha Block (West)', 2, 'DOUBLE', 2, 1, 320.00, 'Ceiling Fan, Twin Desks, Wardrobe, Shared Bath', 'AVAILABLE'),
(5, 'C-301', 'Curie Block (North)', 3, 'TRIPLE', 3, 2, 240.00, 'Triple Study Area, Lockers, Balcony View', 'AVAILABLE'),
(6, 'D-401', 'Da Vinci Block (Executive)', 4, 'DELUXE_AC', 1, 0, 600.00, 'Premium Suite, Refrigerator, Silent AC, Ergonomic Chair', 'AVAILABLE');

-- Beds
INSERT INTO beds (id, room_id, bed_number, is_occupied, student_id, assigned_date) VALUES
(1, 1, 'A-1', TRUE, 1, '2026-08-15'),
(2, 2, 'A-1', FALSE, NULL, NULL),
(3, 3, 'B-1', TRUE, 1, '2026-08-15'),
(4, 3, 'B-2', TRUE, 2, '2026-08-15'),
(5, 4, 'B-1', TRUE, NULL, NULL),
(6, 4, 'B-2', FALSE, NULL, NULL),
(7, 5, 'C-1', TRUE, NULL, NULL),
(8, 5, 'C-2', TRUE, NULL, NULL),
(9, 5, 'C-3', FALSE, NULL, NULL);

-- Fees
INSERT INTO fees (id, student_id, fee_type, amount, due_date, paid_date, status, transaction_ref, payment_method, receipt_number) VALUES
(1, 1, 'HOSTEL_RENT', 960.00, '2026-11-05', '2026-11-03', 'PAID', 'TXN-982144781', 'Stripe UPI', 'REC-2026-1102'),
(2, 1, 'MESS_FEE', 420.00, '2026-11-25', NULL, 'PENDING', NULL, NULL, NULL),
(3, 1, 'MAINTENANCE_DEPOSIT', 100.00, '2026-10-15', '2026-10-12', 'PAID', 'TXN-874512994', 'Credit Card', 'REC-2026-1012'),
(4, 2, 'HOSTEL_RENT', 960.00, '2026-11-05', NULL, 'OVERDUE', NULL, NULL, NULL);

-- Complaints
INSERT INTO complaints (id, student_id, title, description, category, priority, ai_predicted_category, ai_urgency_score, ai_suggested_action, status, assigned_to_id, resolution_notes) VALUES
(1, 1, 'Bathroom pipe leakage in Room B-204', 'Water dripping continuously under the washbasin sink creating a puddle.', 'PLUMBING', 'HIGH', 'PLUMBING', 0.85, 'Dispatch plumber with rubber gasket replacement set', 'IN_PROGRESS', 5, 'Inspection completed; parts ordered from supplier'),
(2, 1, 'Ceiling fan making clicking sound', 'High speed makes rhythmic knocking noise. Vibration noticed.', 'ELECTRICAL', 'MEDIUM', 'ELECTRICAL', 0.55, 'Check mounting bracket and blade alignment', 'SUBMITTED', 5, NULL),
(3, 2, 'Study desk lamp flicker', 'Fluorescent tube starts and switches off repeatedly.', 'ELECTRICAL', 'LOW', 'ELECTRICAL', 0.30, 'Replace LED tube or starter', 'RESOLVED', 5, 'Replaced bulb with 12W Phillips Warm LED');

-- Leaves
INSERT INTO leaves (id, student_id, leave_type, departure_date, expected_return_date, reason, destination_address, parent_contact, status, warden_remarks, approved_by_id, gate_pass_token) VALUES
(1, 1, 'HOME_VISIT', '2026-11-20 18:00:00', '2026-11-23 21:00:00', 'Family Diwali celebration and sibling wedding function', '42 Maple Street, Springfield', '+1-555-9901', 'APPROVED', 'Parent consent confirmed by phone. Gate pass valid.', 3, 'GP-2026-98114'),
(2, 2, 'ACADEMIC_CONFERENCE', '2026-11-18 08:00:00', '2026-11-19 20:00:00', 'Presenting paper at IEEE Regional Student Symposium', 'Tech Convention Center, Metro City', '+1-555-9902', 'PENDING', NULL, NULL, NULL);

-- Visitors
INSERT INTO visitors (id, visitor_name, visitor_phone, id_proof_type, id_proof_number, student_id, relation_to_student, purpose, check_in_time, check_out_time, status, gate_officer_id) VALUES
(1, 'Michael Rivers', '+1-555-4411', 'DRIVING_LICENSE', 'DL-CA-994821', 1, 'Father', 'Delivering winter clothing and medicine', '2026-11-14 14:15:00', '2026-11-14 16:30:00', 'CHECKED_OUT', 6),
(2, 'Jessica Chen', '+1-555-4422', 'PASSPORT', 'PASS-US-882194', 2, 'Sister', 'Weekend campus visit', '2026-11-15 11:30:00', NULL, 'INSIDE', 6);

-- Mess Menus
INSERT INTO mess_menus (id, day_of_week, meal_type, timing, menu_items, dietary_info, calories_est) VALUES
(1, 'MONDAY', 'BREAKFAST', '07:30 - 09:30 AM', 'Idli with Sambar & Coconut Chutney, Poha, Boiled Eggs, Fresh Fruits, Tea & Coffee', 'High Protein & Carb Balance', 520),
(2, 'MONDAY', 'LUNCH', '12:30 - 02:30 PM', 'Paneer Butter Masala, Yellow Dal Tadka, Steamed Rice, Tandoori Roti, Cucumber Salad, Curd', 'Balanced Indian Thali', 740),
(3, 'MONDAY', 'SNACKS', '05:00 - 06:15 PM', 'Crispy Veg Pakoras, Green Mint Chutney, Sweet Biscuits, Masala Chai', 'Evening Snack', 320),
(4, 'MONDAY', 'DINNER', '07:45 - 09:45 PM', 'Chicken Curry / Kadai Mushroom, Jeera Rice, Chapati, Mixed Salad, Gulab Jamun', 'Hearty Dinner', 780),
(5, 'TUESDAY', 'BREAKFAST', '07:30 - 09:30 AM', 'Aloo Paratha with Curd, Cornflakes with Hot Milk, Scrambled Eggs, Oranges, Coffee', 'Energizing Morning', 580),
(6, 'TUESDAY', 'LUNCH', '12:30 - 02:30 PM', 'Rajma Chawal, Aloo Gobi Dry, Phulka, Boondi Raita, Roasted Papad', 'Homestyle North Indian', 710),
(7, 'WEDNESDAY', 'DINNER', '07:45 - 09:45 PM', 'Hyderabadi Veg/Egg Dum Biryani, Mirchi Ka Salan, Raita, Rasgulla', 'Chef Special Midweek Feast', 820);
