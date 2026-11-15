# StaySync: Enterprise Hostel Management System

StaySync is a comprehensive, modern web application designed for academic hostels and residential campuses. It seamlessly connects residents, wardens, administrative officers, maintenance technicians, and gate security personnel within a unified, role-based platform enhanced with **Groq Llama-3 AI**.

---

## 🏗️ Architecture & Five-Phase Implementation

```
staysync/
├── backend/                       # Phase 2: Java Spring Boot REST API
│   ├── pom.xml                    # Maven dependencies (Spring Security, JPA, JWT, MySQL/H2)
│   ├── src/main/java/com/staysync/
│   │   ├── StaySyncApplication.java
│   │   ├── config/                # Spring Security RBAC & JWT Filters
│   │   ├── controller/            # REST Controllers (Auth, Rooms, Fees, Complaints, Leaves, Visitors, AI)
│   │   ├── model/                 # JPA Entities (User, Room, Bed, Fee, Complaint, Leave, Visitor, MessMenu)
│   │   ├── repository/            # Spring Data Repositories
│   │   ├── service/               # Business Logic & Groq AI Service
│   │   └── dto/                   # Request/Response Data Transfer Objects
│   └── src/main/resources/
│       ├── application.properties # H2 / MySQL DB and JWT/Groq Configuration
│       └── data.sql               # Seed Data for instant startup
├── frontend/                      # Phase 3 & 4: React + Vite Application
│   ├── package.json
│   ├── vite.config.js
│   ├── src/
│   │   ├── App.jsx
│   │   ├── main.jsx
│   │   └── services/api.js        # REST API Client with JWT authorization
├── database/                      # Phase 1: Relational Database Schemas
│   ├── mysql_schema.sql           # MySQL DDL with indexes & constraints
│   ├── supabase_schema.sql        # Supabase/PostgreSQL schema with Row-Level Security (RLS)
│   └── seed_data.sql              # Realistic seed records for all roles
├── live-app/                      # Interactive Single-Page Web App
│   └── index.html                 # Complete live UI with all 5 role dashboards & Groq AI
├── start_demo_server.py           # Instant Python local server & auto-browser launcher
└── run_app.bat                    # One-click Windows runner
```

---

## 👥 User Roles & Access Control (RBAC)

| Role | Permissions & Primary Features |
|---|---|
| **STUDENT** | View room & bed details, pay hostel/mess fees with instant receipt, lodge complaints with AI urgency detection, apply for leave, view digital QR Gate Pass, and inspect mess menus. |
| **WARDEN** | Manage room occupancy & bed allocations, review and approve/reject leave applications, assign complaints to maintenance, broadcast emergency campus notices. |
| **ADMINISTRATOR** | High-level KPI analytics, user role administration (RBAC promotion/demotion), fee structures, financial reconciliation, and Groq AI system settings. |
| **MAINTENANCE** | Filter assigned maintenance tickets (Plumbing, Electrical, Furniture, AC), update progress statuses, log repair notes, and mark work orders resolved. |
| **SECURITY** | Student Gate Pass token and QR verification at campus gates, Check-Out / Check-In logging, and visitor entry/exit pass generation. |

---

## 🚀 Quick Start & How to Run

### Option 1: Instant Browser Launch (Zero Installation Needed)
Double-click `run_app.bat` or run in PowerShell:
```powershell
& "C:\Users\hp\AppData\Local\Programs\Python\Python313\python.exe" start_demo_server.py
```
Open **[http://localhost:8000/live-app/index.html](http://localhost:8000/live-app/index.html)** in your browser. Use the top-bar role selector to seamlessly test all 5 user roles.

### Option 2: Running the Spring Boot Backend
Ensure Java 21+ and Maven are available, then run:
```bash
cd backend
mvn clean spring-boot:run
```
- API Base URL: `http://localhost:8080/api`
- H2 In-Memory DB Console: `http://localhost:8080/h2-console` (`sa` / empty password)

### Option 3: Running the React + Vite Frontend
```bash
cd frontend
npm install
npm run dev
```
Open `http://localhost:5173`.

---

## 🤖 AI Assistant & Groq Integration (Phase 4)

- **Model**: `llama-3.3-70b-versatile` / `llama-3.1-8b-instant` via Groq REST API.
- **Automated Complaint Categorization**: Detects keywords (e.g. water leakage, sparks, WiFi dropout) to predict the correct department and assign an urgency score.
- **Smart Room Recommendation**: Suggests blocks and room types based on student study habits (e.g., quiet single suites for Year 3 vs double rooms for first-year engineering peers).
- **24/7 Hostel FAQ Bot**: Answers resident questions on mess timings, curfew policies (10:00 PM), fee deadlines, and warden office hours.
