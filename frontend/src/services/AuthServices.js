const API_URL = 'http://localhost:8080/api/v1/auth'; // Base URL for authentication API

// Helper function to get authorization headers with JWT token
const getAuthHeaders = () => {
  const token = localStorage.getItem('jwtToken');
  return token
      ? { 'Content-Type': 'application/json', Authorization: `Bearer ${token}` }
      : { 'Content-Type': 'application/json' };
};

// Generic function to handle API requests
const fetchRequest = async (url, options = {}) => {
  try {
    const response = await fetch(`${API_URL}${url}`, {
      ...options,
      headers: getAuthHeaders(),
    });

    const contentType = response.headers.get('content-type');

    if (!response.ok) {
      if (contentType && contentType.includes('application/json')) {
        const errorData = await response.json();
        const message =
            errorData.message || errorData.error || errorData.msg || 'Įvyko klaida';

        console.error('Klaidos atsakymas iš serverio:', errorData);
        throw new Error(message);
      } else {
        const text = await response.text();
        throw new Error(text || `HTTP klaida: ${response.status}`);
      }
    }

    return await response.json();
  } catch (err) {
    console.error('Užklausos klaida:', err.message);
    throw err;
  }
};

// Function to handle user login and store role
export const login = async (username, password) => {
  const userData = await fetchRequest('/signin', {
    method: 'POST',
    body: JSON.stringify({ username, password }),
  });

  console.log('Gautas vartotojas:', userData);

  if (userData.accessToken) {
    localStorage.setItem('jwtToken', userData.accessToken);
  }

  if (userData.id) {
    localStorage.setItem('userId', userData.id);
  }

  if (Array.isArray(userData.roles) && userData.roles.length > 0) {
    localStorage.setItem('userRole', userData.roles[0]); // Paimam pirmą rolę
  }

  return userData;
};

// Function to handle user registration
export const createUser = async (userData) => {
  const data = await fetchRequest('/signup', {
    method: 'POST',
    body: JSON.stringify(userData),
  });

  if (data.token) {
    localStorage.setItem('jwtToken', data.token);
  }
  if (data.id) {
    localStorage.setItem('userId', data.id);
  }

  if (data.roles) {
    localStorage.setItem('userRole', data.roles);
  }

  return data;
};

// Function to handle user logout
export const logout = () => {
  localStorage.removeItem('jwtToken');
  localStorage.removeItem('userId');
  localStorage.removeItem('userRole');
};
