import { createContext, useContext } from 'react';
import * as movieServices from '../services/ReviewService.js';
import { useAuth } from './AuthContext';

const ReviewContext = createContext();

export const ReviewProvider = ({ children }) => {
    const { token, getResponse } = useAuth();

    // Reviews CRUD operations
    const getAllReviews = async () => {
        return await getResponse(movieServices.getAllReviews(token));
    };

    const getMovieReviews = async (movieId) => {
        return await getResponse(movieServices.getMovieReviews(movieId, token));
    };

    const createReview = async (reviewData) => {
        return await getResponse(movieServices.createReview(reviewData, token));
    };

    const updateReview = async (reviewId, reviewData) => {
        return await getResponse(movieServices.updateReview(reviewId, reviewData, token));
    };

    const deleteReview = async (reviewId) => {
        return await getResponse(movieServices.deleteReview(reviewId, token));
    };


    return (
        <ReviewContext.Provider
            value={{
                getAllReviews,
                getMovieReviews,
                createReview,
                updateReview,
                deleteReview
            }}
        >
            {children}
        </ReviewContext.Provider>
    );
};

export const useReviews = () => {
    const context = useContext(ReviewContext);
    if (!context) {
        throw new Error('useReviews must be used within ReviewProvider');
    }
    return context;
};
