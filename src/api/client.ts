// src/api/client.ts
import axios from 'axios'

export const api = axios.create({
  // как у тебя изначально было — через proxy Vite
  baseURL: '/api',
  withCredentials: false,
})

// Всегда подставляем JWT из localStorage
api.interceptors.request.use((config) => {
  const token = localStorage.getItem('authToken')
  if (token) {
    config.headers = config.headers || {}
    config.headers.Authorization = `Bearer ${token}`
  }
  return config
})

/* ===========================
   AUTH
=========================== */

// БЭК: @RequestMapping("/api/auth") + @PostMapping("/login")
// БЭК: @RequestMapping("/api/auth") + @PostMapping("/login")
export const login = async (username: string, password: string) => {
  const res = await api.post('/auth/login', { username, password })
  return res.data
}

// БЭК: @RequestMapping("/api/users") + @PostMapping("/register")
export const register = async (username: string, email: string, password: string) => {
  const res = await api.post('/users/register', { username, email, password })
  return res.data as { token: string }
}


/* ===========================
   PROJECTS
=========================== */

// GET /api/projects — список проектов текущего пользователя
export const fetchProjects = async () => {
  const res = await api.get('/projects')
  return res.data
}

// GET /api/projects/{id}
export const fetchProjectById = async (id: string) => {
  const res = await api.get(`/projects/${id}`)
  return res.data
}

// POST /api/projects  { jsonData: string }
export const createProject = async (json: string) => {
  const res = await api.post('/projects', { jsonData: json })
  return res.data
}

// PUT /api/projects/{id}  { jsonData: string }
export const updateProject = async (id: string, json: string) => {
  const res = await api.put(`/projects/${id}`, { jsonData: json })
  return res.data
}

// DELETE /api/projects/{id}
export const deleteProject = async (id: string) => {
  const res = await api.delete(`/projects/${id}`)
  return res.data
}

/* ===========================
   TEMPLATES
=========================== */

// GET /api/templates
export const fetchTemplates = async () => {
  const res = await api.get('/templates')
  return res.data
}

// POST /api/templates  { name, jsonData }
export const createTemplate = async (payload: { name: string; jsonData: string }) => {
  const res = await api.post('/templates', payload)
  return res.data
}

export const publishProject = async (id: string, json: string) => {
  const res = await api.post(`/projects/${id}/publish`, { jsonData: json })
  return res.data
}

export default api
