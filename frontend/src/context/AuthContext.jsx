import { createContext, useState, useContext, useEffect } from 'react';
import * as authServices from '../services/AuthServices';

// Create a context for authentication
const AuthContext = createContext();

// AuthProvider component to provide authentication-related data and methods
export const AuthProvider = ({ children }) => {
    // State to store the authentication token and user role
    const [token, setToken] = useState(() => localStorage.getItem('jwtToken') || null);
    const [userRole, setUserRole] = useState(() => localStorage.getItem('userRole') || null);

    // Effect to sync token state with localStorage
    useEffect(() => {
        if (token) {
            localStorage.setItem('jwtToken', token);
        } else {
            localStorage.removeItem('jwtToken');
        }
    }, [token]);

    // Effect to sync userRole state with localStorage
    useEffect(() => {
        if (userRole) {
            localStorage.setItem('userRole', userRole);
        } else {
            localStorage.removeItem('userRole');
        }
    }, [userRole]);

    // Handles responses from API and sets token and role if available
    const getResponse = async (response) => {
        const data = await response;
        if (data?.token) {
            setToken(data.token);
        }
        if (Array.isArray(data?.roles) && data.roles.length > 0) {
            setUserRole(data.roles[0]);
        }
        return data;
    };

    // Logs in the user by making API request with email and password
    const login = async (email, password) => {
        return getResponse(authServices.login(email, password));
    };

    // Logs out the user by clearing the token and role
    const logout = () => {
        setToken(null);
        setUserRole(null);
    };

    // Creates a new user by making API request with user data
    const createUser = async (userData) => {
        return getResponse(authServices.createUser(userData, token));
    };

    // Provide authentication methods, token, and role to children components
    return (
        <AuthContext.Provider
            value={{
                token,
                userRole,
                setToken,
                setUserRole,
                login,
                logout,
                createUser,
                getResponse,
            }}
        >
            {children}
        </AuthContext.Provider>
    );
};

// Custom hook to use authentication context
export const useAuth = () => {
    const context = useContext(AuthContext);
    if (!context) {
        throw new Error('useAuth must be used within AuthProvider');
    }
    return context;
};
