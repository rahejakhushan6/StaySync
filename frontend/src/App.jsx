import React, { useState } from 'react';

export default function App() {
  const [role, setRole] = useState('STUDENT');

  return (
    <div className="min-h-screen bg-slate-50 text-slate-800 font-sans">
      <header className="bg-white border-b border-slate-200 sticky top-0 z-50">
        <div className="max-w-7xl mx-auto px-4 h-16 flex items-center justify-between">
          <div className="flex items-center space-x-3">
            <div className="w-10 h-10 rounded-xl bg-indigo-600 flex items-center justify-center text-white font-extrabold text-lg shadow-md">
              S
            </div>
            <div>
              <span className="font-extrabold text-xl text-slate-900 tracking-tight">Stay<span className="text-indigo-600">Sync</span></span>
              <span className="ml-2 text-xs font-semibold px-2 py-0.5 rounded-full bg-indigo-50 text-indigo-700 border border-indigo-200">React + Vite</span>
            </div>
          </div>

          <div className="flex items-center space-x-3">
            <span className="text-xs font-medium text-slate-500">Active View:</span>
            <select 
              value={role} 
              onChange={e => setRole(e.target.value)}
              className="text-xs font-bold bg-slate-100 border border-slate-200 rounded-lg px-3 py-1.5"
            >
              <option value="STUDENT">Student (Alex Rivers)</option>
              <option value="WARDEN">Warden (Dr. Robert Sharma)</option>
              <option value="ADMINISTRATOR">Administrator (Dean Vance)</option>
              <option value="MAINTENANCE">Maintenance (Marcus Fixer)</option>
              <option value="SECURITY">Security (Officer Khan)</option>
            </select>
          </div>
        </div>
      </header>

      <main className="max-w-7xl mx-auto px-4 py-8">
        <div className="bg-indigo-600 text-white rounded-2xl p-6 shadow-lg mb-8 flex justify-between items-center">
          <div>
            <h1 className="text-2xl font-bold">StaySync Hostel Management System</h1>
            <p className="text-indigo-100 text-xs mt-1">Full-stack React + Vite and Spring Boot RBAC Architecture</p>
          </div>
          <a 
            href="/live-app/index.html" 
            target="_blank" 
            rel="noreferrer"
            className="bg-white text-indigo-700 hover:bg-indigo-50 font-bold px-4 py-2 rounded-xl text-xs shadow transition"
          >
            Launch Interactive Demo App ➔
          </a>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
          <div className="bg-white p-6 rounded-2xl border border-slate-200 shadow-sm space-y-2">
            <h3 className="font-bold text-slate-900">Phase 1: DB Design</h3>
            <p className="text-xs text-slate-600">MySQL & Supabase/PostgreSQL relational schemas with RLS for Users, Rooms, Beds, Fees, Complaints, Leaves, Visitors & Mess.</p>
          </div>
          <div className="bg-white p-6 rounded-2xl border border-slate-200 shadow-sm space-y-2">
            <h3 className="font-bold text-slate-900">Phase 2: Spring Boot API</h3>
            <p className="text-xs text-slate-600">Enterprise REST APIs with Spring Security, JWT stateless tokens, and granular Role-Based Access Control (RBAC).</p>
          </div>
          <div className="bg-white p-6 rounded-2xl border border-slate-200 shadow-sm space-y-2">
            <h3 className="font-bold text-slate-900">Phase 3 & 4: React & Groq AI</h3>
            <p className="text-xs text-slate-600">Tailored multi-role dashboards, instant AI complaint categorization, smart room recommendations, and digital QR gate passes.</p>
          </div>
        </div>
      </main>
    </div>
  );
}
