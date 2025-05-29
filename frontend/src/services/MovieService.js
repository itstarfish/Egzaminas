import fetchRequest from './fetchRequest';

export const getMovies = async (token, queryParams = {}) => {
    let url = '/movies';

    if (typeof queryParams.search === 'string' && queryParams.search.trim() !== '') {
        const encodedSearch = encodeURIComponent(queryParams.search.trim());
        url = `/movies/search?name=${encodedSearch}`;
    }

    console.log('Fetching movies from:', url);

    try {
        const response = await fetchRequest(url, { method: 'GET' }, token);
        return response;
    } catch (error) {
        console.error('GetMovies Error:', error);
        throw error;
    }
};

export const getMovieById = async (id, token) => {
    return fetchRequest(`/movies/${id}`, { method: 'GET' }, token);
};

export const createMovie = async (movieData, token) => {
    const formattedData = {
        name: movieData.name,
        description: movieData.description,
        imdbrating: movieData.imdbrating,
        movieCategory: typeof movieData.movieCategory === 'object'
            ? movieData.movieCategory.id
            : movieData.movieCategory
    };
    return fetchRequest('/movies', {
        method: 'POST',
        body: JSON.stringify(formattedData)
    }, token);
};

export const updateMovie = async (id, movieData, token) => {
    const cleanedData = {
        name: movieData.name,
        description: movieData.description,
        movieCategory: movieData.movieCategory,
        imdbrating: movieData.imdbrating
    };

    return fetchRequest(`/movies/${id}`, {
        method: 'PUT',
        body: JSON.stringify(cleanedData)
    }, token);
};

export const deleteMovie = async (id, token) => {
    try {
        return await fetchRequest(`/movies/${id}`, {
            method: 'DELETE'
        }, token);
    } catch (error) {
        console.error('Delete movie error:', error);
        throw error;
    }
};

// New searchMovies function
export const searchMovies = async (name, token) => {
    if (!name) {
        throw new Error('Search term "name" is required');
    }
    const url = `/movies/search?name=${encodeURIComponent(name)}`;
    try {
        const response = await fetchRequest(url, { method: 'GET' }, token);
        return response;
    } catch (error) {
        console.error('searchMovies Error:', error);
        throw error;
    }
};

