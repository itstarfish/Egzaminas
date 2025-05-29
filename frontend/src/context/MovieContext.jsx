import React, { createContext, useContext, useMemo } from 'react';
import * as movieServices from '../services/MovieService';
import { useAuth } from './AuthContext';

const MovieContext = createContext();

export const MovieProvider = ({ children }) => {
    const { token, getResponse } = useAuth();

    // Movies CRUD operations
    const getMovies = async (queryParams = {}) => {
        const response = await getResponse(movieServices.getMovies(token, queryParams));
        console.log('getMovies response:', response);

        if (Array.isArray(response)) {
            return {
                data: response,
                status: 200,
                results: response.length,
            };
        }

        return {
            data: response?.data || [],
            status: response?.status || 500,
            results: response?.results || 0,
        };
    };

    const getMovieById = async (id) => {
        const response = await getResponse(movieServices.getMovieById(id, token));
        return response || null;
    };

    const createMovie = async (movieData) => {
        return await getResponse(movieServices.createMovie(movieData, token));
    };

    const updateMovie = async (id, movieData) => {
        try {
            const response = await getResponse(movieServices.updateMovie(id, movieData, token));
            return response;
        } catch (error) {
            console.error('Update movie error:', error);
            throw error;
        }
    };

    const deleteMovie = async (id) => {
        return await getResponse(movieServices.deleteMovie(id, token));
    };

    const searchMovies = async (name) => {
        return await getResponse(movieServices.searchMovies(name, token));
    };

    // Memorize the context value to avoid unnecessary rerenders
    const contextValue = useMemo(() => ({
        getMovies,
        getMovieById,
        createMovie,
        updateMovie,
        deleteMovie,
        searchMovies,
    }), [token]);

    return (
        <MovieContext.Provider value={contextValue}>
            {children}
        </MovieContext.Provider>
    );
};

export const useMovies = () => {
    const context = useContext(MovieContext);
    if (!context) {
        throw new Error('useMovies must be used within a MovieProvider');
    }
    return context;
};


