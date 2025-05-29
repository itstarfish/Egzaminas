const API_URL = 'http://localhost:8080/api/v1';

const fetchRequest = async (url, options = {}, token) => {

    const headers = {
        'Content-Type': 'application/json',
        'Authorization': token ? `Bearer ${token}` : '',
        ...options.headers
    };

    try {
        const response = await fetch(`${API_URL}${url}`, {
            ...options,
            headers
        });

        if (!response.ok) {
            const errorData = await response.json();
            console.error('API Error:', errorData);
            throw new Error(errorData.message || 'Unexpected error occurred');
        }

        const contentType = response.headers.get('content-type');
        if (contentType && contentType.includes('application/json')) {
            return await response.json();
        } else {
            const textResponse = await response.text();
            return { message: textResponse };
        }
    } catch (error) {
        console.error('Fetch Error:', error);
        throw error;
    }
};

export default fetchRequest;
