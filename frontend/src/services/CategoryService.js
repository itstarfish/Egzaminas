import fetchRequest from './fetchRequest';

export const getCategories = async (token) => {
    try {
        const response = await fetchRequest('/categories', { method: 'GET' }, token);
        return response;
    } catch (error) {
        console.error('Get categories error:', error);
        throw error;
    }
};

export const getCategoryById = async (id, token) => {
    return fetchRequest(`/categories/${id}`, { method: 'GET' }, token);
};

export const createCategory = async (categoryData, token) => {
    return fetchRequest('/categories', {
        method: 'POST',
        body: JSON.stringify(categoryData)
    }, token);
};

export const updateCategory = async (id, categoryData, token) => {
    return fetchRequest(`/categories/${id}`, {
        method: 'PUT',
        body: JSON.stringify(categoryData)
    }, token);
};

export const deleteCategory = async (id, token) => {
    return fetchRequest(`/categories/${id}`, {
        method: 'DELETE'
    }, token);
};
