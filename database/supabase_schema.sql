-- ========================================================
-- StaySync: Hostel Management System (Supabase / PostgreSQL Schema)
-- Complete schema with Row Level Security (RLS) policies
-- ========================================================

-- Enable UUID extension
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Create ENUM types
CREATE TYPE user_role AS ENUM ('STUDENT', 'WARDEN', 'ADMINISTRATOR', 'MAINTENANCE', 'SECURITY');
CREATE TYPE room_type_enum AS ENUM ('SINGLE', 'DOUBLE', 'TRIPLE', 'DELUXE_AC');
CREATE TYPE fee_status_enum AS ENUM ('PENDING', 'PAID', 'OVERDUE', 'CANCELLED');
CREATE TYPE complaint_status_enum AS ENUM ('SUBMITTED', 'IN_PROGRESS', 'RESOLVED', 'CLOSED', 'REJECTED');
CREATE TYPE leave_status_enum AS ENUM ('PENDING', 'APPROVED', 'REJECTED', 'CHECKED_OUT', 'CHECKED_IN');
CREATE TYPE visitor_status_enum AS ENUM ('INSIDE', 'CHECKED_OUT');

-- 1. Profiles / Users Table
CREATE TABLE IF NOT EXISTS public.profiles (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    auth_user_id UUID REFERENCES auth.users(id) ON DELETE CASCADE,
    name TEXT NOT NULL,
    email TEXT UNIQUE NOT NULL,
    role user_role NOT NULL DEFAULT 'STUDENT',
    phone TEXT,
    emergency_contact TEXT,
    room_number TEXT,
    bed_number TEXT,
    student_id_number TEXT,
    department TEXT,
    year_of_study INTEGER,
    status TEXT DEFAULT 'ACTIVE',
    avatar_url TEXT,
    created_at TIMESTAMPTZ DEFAULT NOW(),
    updated_at TIMESTAMPTZ DEFAULT NOW()
);

-- 2. Rooms Table
CREATE TABLE IF NOT EXISTS public.rooms (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    room_number TEXT NOT NULL UNIQUE,
    block_name TEXT NOT NULL,
    floor_number INTEGER NOT NULL,
    room_type room_type_enum NOT NULL,
    capacity INTEGER NOT NULL,
    occupied_count INTEGER NOT NULL DEFAULT 0,
    monthly_rent NUMERIC(10, 2) NOT NULL,
    amenities TEXT DEFAULT 'WiFi, Study Table, Wardrobe, Ceiling Fan',
    status TEXT DEFAULT 'AVAILABLE',
    created_at TIMESTAMPTZ DEFAULT NOW()
);

-- 3. Beds Table
CREATE TABLE IF NOT EXISTS public.beds (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    room_id UUID REFERENCES public.rooms(id) ON DELETE CASCADE,
    bed_number TEXT NOT NULL,
    is_occupied BOOLEAN DEFAULT FALSE,
    student_id UUID REFERENCES public.profiles(id) ON DELETE SET NULL,
    assigned_date DATE,
    UNIQUE (room_id, bed_number)
);

-- 4. Fees Table
CREATE TABLE IF NOT EXISTS public.fees (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    student_id UUID REFERENCES public.profiles(id) ON DELETE CASCADE,
    fee_type TEXT NOT NULL,
    amount NUMERIC(10, 2) NOT NULL,
    due_date DATE NOT NULL,
    paid_date DATE,
    status fee_status_enum DEFAULT 'PENDING',
    transaction_ref TEXT,
    receipt_number TEXT,
    created_at TIMESTAMPTZ DEFAULT NOW()
);

-- 5. Complaints Table
CREATE TABLE IF NOT EXISTS public.complaints (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    student_id UUID REFERENCES public.profiles(id) ON DELETE CASCADE,
    title TEXT NOT NULL,
    description TEXT NOT NULL,
    category TEXT NOT NULL,
    priority TEXT DEFAULT 'MEDIUM',
    ai_predicted_category TEXT,
    ai_urgency_score NUMERIC(3, 2),
    ai_suggested_action TEXT,
    status complaint_status_enum DEFAULT 'SUBMITTED',
    assigned_to_id UUID REFERENCES public.profiles(id) ON DELETE SET NULL,
    resolution_notes TEXT,
    resolved_at TIMESTAMPTZ,
    created_at TIMESTAMPTZ DEFAULT NOW(),
    updated_at TIMESTAMPTZ DEFAULT NOW()
);

-- 6. Leaves Table
CREATE TABLE IF NOT EXISTS public.leaves (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    student_id UUID REFERENCES public.profiles(id) ON DELETE CASCADE,
    leave_type TEXT NOT NULL,
    departure_date TIMESTAMPTZ NOT NULL,
    expected_return_date TIMESTAMPTZ NOT NULL,
    actual_return_date TIMESTAMPTZ,
    reason TEXT NOT NULL,
    destination_address TEXT NOT NULL,
    parent_contact TEXT NOT NULL,
    status leave_status_enum DEFAULT 'PENDING',
    warden_remarks TEXT,
    approved_by_id UUID REFERENCES public.profiles(id) ON DELETE SET NULL,
    gate_pass_token TEXT,
    created_at TIMESTAMPTZ DEFAULT NOW()
);

-- 7. Visitors Table
CREATE TABLE IF NOT EXISTS public.visitors (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    visitor_name TEXT NOT NULL,
    visitor_phone TEXT NOT NULL,
    id_proof_type TEXT NOT NULL,
    id_proof_number TEXT NOT NULL,
    student_id UUID REFERENCES public.profiles(id) ON DELETE CASCADE,
    relation_to_student TEXT NOT NULL,
    purpose TEXT NOT NULL,
    check_in_time TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    check_out_time TIMESTAMPTZ,
    status visitor_status_enum DEFAULT 'INSIDE',
    gate_officer_id UUID REFERENCES public.profiles(id) ON DELETE SET NULL,
    created_at TIMESTAMPTZ DEFAULT NOW()
);

-- 8. Mess Menus Table
CREATE TABLE IF NOT EXISTS public.mess_menus (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    day_of_week TEXT NOT NULL,
    meal_type TEXT NOT NULL,
    timing TEXT NOT NULL,
    menu_items TEXT NOT NULL,
    dietary_info TEXT DEFAULT 'Standard Veg / Non-Veg options',
    calories_est INTEGER DEFAULT 650,
    created_at TIMESTAMPTZ DEFAULT NOW(),
    UNIQUE (day_of_week, meal_type)
);

-- Enable Row Level Security (RLS)
ALTER TABLE public.profiles ENABLE ROW LEVEL SECURITY;
ALTER TABLE public.fees ENABLE ROW LEVEL SECURITY;
ALTER TABLE public.complaints ENABLE ROW LEVEL SECURITY;
ALTER TABLE public.leaves ENABLE ROW LEVEL SECURITY;

-- Students can read their own profiles and fees
CREATE POLICY "Students see own profile" ON public.profiles
    FOR SELECT USING (auth.uid() = auth_user_id);

CREATE POLICY "Students see own fees" ON public.fees
    FOR SELECT USING (auth.uid() = (SELECT auth_user_id FROM public.profiles WHERE id = fees.student_id));
