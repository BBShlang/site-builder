// src/api/client.ts
import axios, { AxiosError } from 'axios'

// Создаём экземпляр axios
const api = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || '/',
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json',
  },
})

// Request interceptor: добавляем токен, если есть
api.interceptors.request.use((config) => {
  const token = localStorage.getItem('authToken')
  if (token) {
    config.headers.Authorization = `Bearer ${token}`
  }
  return config
})

api.interceptors.response.use(
  (response) => response,
  (error: AxiosError) => {
    // Просто прокидываем ошибку — НИКАКОЙ ЛОГИКИ РЕДИРЕКТА ЗДЕСЬ!
    return Promise.reject(error)
  }
)
// ======================
// Сервисы аутентификации
// ======================

/**
 * Авторизация пользователя
 * @returns JWT-токен
 */
export const login = async (email: string, password: string): Promise<string> => {
  try {
    const response = await api.post<{ token: string }>('/api/auth/login', {
      email,
      password,
    })

    const token = response.data.token
    localStorage.setItem('authToken', token)

    // ⬇️ Извлекаем userId из JWT (base64-decode payload)
    try {
      const payload = JSON.parse(atob(token.split('.')[1]))
      localStorage.setItem('userId', payload.sub || payload.userId || '')
    } catch (e) {
      console.warn('Не удалось извлечь userId из токена')
    }

    return token
  } catch (error) {
    if (axios.isAxiosError(error)) {
      if (error.response?.status === 403) {
        throw new Error('Неверные учетные данные или доступ запрещён')
      }
      if (error.response?.status === 401) {
        throw new Error('Неверный логин или пароль')
      }
    }
    throw new Error('Не удалось подключиться к серверу')
  }
}

/**
 * Регистрация нового пользователя
 */
export const register = async (
  username: string,
  email: string,
  password: string,
): Promise<void> => {
  try {
    await api.post('/api/users/register', {
      username,
      email,
      password,
    })
  } catch (error) {
    if (axios.isAxiosError(error)) {
      if (error.response?.status === 400 || error.response?.status === 409) {
        throw new Error('Пользователь с таким email или именем уже существует')
      }
      if (error.response?.status === 500) {
        throw new Error('Ошибка сервера. Повторите попытку позже.')
      }
    }
    throw new Error('Не удалось подключиться к серверу')
  }
}

// Экспортируем экземпляр для других API-запросов (проекты, редактор и т.д.)
export default api
