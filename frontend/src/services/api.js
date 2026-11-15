// ========================================================
// StaySync REST API Client (Spring Boot Integration)
// ========================================================

const API_BASE_URL = import.meta.env?.VITE_API_BASE_URL || 'http://localhost:8080/api';

function getAuthHeader() {
  const token = localStorage.getItem('staysync_jwt');
  return token ? { 'Authorization': `Bearer ${token}` } : {};
}

async function request(endpoint, options = {}) {
  const headers = {
    'Content-Type': 'application/json',
    ...getAuthHeader(),
    ...options.headers,
  };

  const response = await fetch(`${API_BASE_URL}${endpoint}`, {
    ...options,
    headers,
  });

  if (!response.ok) {
    const errorData = await response.json().catch(() => ({}));
    throw new Error(errorData.error || `HTTP error ${response.status}`);
  }

  return response.json();
}

export const api = {
  // Auth
  login: (email, password) => 
    request('/auth/login', { method: 'POST', body: JSON.stringify({ email, password }) }),
  register: (userData) => 
    request('/auth/register', { method: 'POST', body: JSON.stringify(userData) }),
  getDemoAccounts: () => 
    request('/auth/demo-accounts'),

  // Rooms & Beds
  getRooms: () => request('/rooms'),
  getBeds: () => request('/rooms/beds'),
  allocateBed: (bedId, studentId) => 
    request('/rooms/allocate', { method: 'POST', body: JSON.stringify({ bedId, studentId }) }),
  deallocateBed: (bedId) => 
    request(`/rooms/deallocate/${bedId}`, { method: 'POST' }),

  // Fees
  getStudentFees: (studentId) => request(`/fees/student/${studentId}`),
  getAllFees: () => request('/fees'),
  payFee: (feeId, paymentMethod) => 
    request(`/fees/${feeId}/pay`, { method: 'POST', body: JSON.stringify({ paymentMethod }) }),

  // Complaints & AI
  getStudentComplaints: (studentId) => request(`/complaints/student/${studentId}`),
  getAllComplaints: () => request('/complaints'),
  submitComplaint: (studentId, complaintData) => 
    request(`/complaints/student/${studentId}`, { method: 'POST', body: JSON.stringify(complaintData) }),
  updateComplaintStatus: (complaintId, status, resolutionNotes) => 
    request(`/complaints/${complaintId}/status`, { method: 'PATCH', body: JSON.stringify({ status, resolutionNotes }) }),

  // Leaves & Gate Pass
  getStudentLeaves: (studentId) => request(`/leaves/student/${studentId}`),
  getAllLeaves: () => request('/leaves'),
  applyLeave: (studentId, leaveData) => 
    request(`/leaves/student/${studentId}`, { method: 'POST', body: JSON.stringify(leaveData) }),
  reviewLeave: (leaveId, wardenId, approved, remarks) => 
    request(`/leaves/${leaveId}/review`, { method: 'POST', body: JSON.stringify({ wardenId, approved, remarks }) }),
  verifyGatePass: (gatePassToken, action) => 
    request('/leaves/gate-check', { method: 'POST', body: JSON.stringify({ gatePassToken, action }) }),

  // Visitors
  getVisitors: () => request('/visitors'),
  getActiveVisitors: () => request('/visitors/active'),
  logVisitorEntry: (studentId, gateOfficerId, visitorData) => 
    request(`/visitors/entry?studentId=${studentId}&gateOfficerId=${gateOfficerId}`, { method: 'POST', body: JSON.stringify(visitorData) }),
  logVisitorExit: (visitorId) => 
    request(`/visitors/${visitorId}/exit`, { method: 'POST' }),

  // Mess
  getMessMenus: () => request('/mess'),
  getTodayMessMenu: (day) => request(`/mess/today/${day}`),

  // Groq AI
  askAiAssistant: (prompt, contextRole) => 
    request('/ai/chat', { method: 'POST', body: JSON.stringify({ prompt, contextRole }) }),
  categorizeComplaintWithAi: (title, description) => 
    request('/ai/categorize-complaint', { method: 'POST', body: JSON.stringify({ title, description }) }),
  recommendRoomWithAi: (yearOfStudy, studyHabit, budgetTier, preferredRoomType) => 
    request('/ai/recommend-room', { method: 'POST', body: JSON.stringify({ yearOfStudy, studyHabit, budgetTier, preferredRoomType }) })
};
