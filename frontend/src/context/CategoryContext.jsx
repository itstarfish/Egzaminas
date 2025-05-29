import { createContext, useContext } from 'react';
import * as movieServices from '../services/CategoryService.js';
import { useAuth } from './AuthContext';

const CategoryContext = createContext();

export const CategoryProvider = ({ children }) => {
    const { token, getResponse } = useAuth();

    // Categories CRUD operations
    const getCategories = async () => {
        return await getResponse(movieServices.getCategories(token));
    };

    const getCategoryById = async (id) => {
        return await getResponse(movieServices.getCategoryById(id, token));
    };

    const createCategory = async (categoryData) => {
        return await getResponse(movieServices.createCategory(categoryData, token));
    };

    const updateCategory = async (id, categoryData) => {
        return await getResponse(movieServices.updateCategory(id, categoryData, token));
    };

    const deleteCategory = async (id) => {
        return await getResponse(movieServices.deleteCategory(id, token));
    };

    return (
        <CategoryContext.Provider
            value={{
                getCategories,
                getCategoryById,
                createCategory,
                updateCategory,
                deleteCategory
            }}
        >
            {children}
        </CategoryContext.Provider>
    );
};

export const useCategories = () => {
    const context = useContext(CategoryContext);
    if (!context) {
        throw new Error('useCategories must be used within CategoryProvider');
    }
    return context;
};
