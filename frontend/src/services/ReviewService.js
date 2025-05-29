import fetchRequest from './fetchRequest';
export const getAllReviews = async (token) => {
    return fetchRequest('/reviews', { method: 'GET' }, token);
};

export const getMovieReviews = async (movieId, token) => {
    return fetchRequest(`/movies/${movieId}/reviews`, { method: 'GET' }, token);
};

export const createReview = async (movieId, reviewData, token) => {
    return fetchRequest(`/reviews`, {
        method: 'POST',
        body: JSON.stringify({ ...reviewData, movieId }), // make sure movieId is sent in body
        headers: { 'Content-Type': 'application/json' }
    }, token);
};

export const updateReview = async (reviewId, reviewData, token) => {
    // reviewData should be: { review: string }
    return fetchRequest(`/reviews/${reviewId}`, {
        method: 'PUT',
        body: JSON.stringify(reviewData),
    }, token);
};

export const deleteReview = async (reviewId, token) => {
    return fetchRequest(`/reviews/${reviewId}`, {
        method: 'DELETE',
    }, token);
};