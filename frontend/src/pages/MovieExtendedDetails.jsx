import {useEffect, useState} from 'react';
import {useParams} from 'react-router-dom';
import {useAuth} from '../context/AuthContext';
import {getMovieById} from '../services/MovieService';
import {getMovieReviews, createReview} from '../services/ReviewService';
import MovieDetails from "../components/MovieDetails.jsx";


function MovieExtendedDetails() {
    const {id} = useParams(); // movie id from URL
    const {token} = useAuth();
    const [movie, setMovie] = useState(null);
    const [reviews, setReviews] = useState([]);
    const [newReview, setNewReview] = useState('');
    const [error, setError] = useState(null);
    const [newRating, setNewRating] = useState(1); // Default rating of 1

    const fetchMovieAndReviews = async () => {
        try {
            const movieData = await getMovieById(id, token);
            setMovie(movieData);

            const reviewsData = await getMovieReviews(id, token);
            setReviews(reviewsData);
        } catch (err) {
            setError(err.message);
        }
    };

    useEffect(() => {
        fetchMovieAndReviews();
    }, [id]);

    const handleReviewSubmit = async (e) => {
        e.preventDefault();
        console.log("Submit clicked"); // DEBUG

        if (!newReview.trim()) {
            console.log("Review empty, abort");
            return;
        }

        try {
            console.log("Sending review:", {review: newReview, rating: newRating, movieId: parseInt(id)}); // DEBUG
            await createReview(id, {
                review: newReview,
                rating: newRating,
                movieId: parseInt(id)
            }, token);
            console.log("Review created successfully"); // DEBUG

            setNewReview('');
            setNewRating(1);
            fetchMovieAndReviews(); // Refresh reviews after submit
        } catch (err) {
            console.error("Error creating review:", err);
            setError(err.message);
        }
    };

    const handleEdit = async (updatedMovie) => {
        setMovie(updatedMovie);
    };

    const handleDelete = async () => {
        setMovie(null); // Clear movie data if deleted
    };

    if (error) return <p className="text-danger">Error: {error}</p>;
    if (!movie) return <p>Loading movie details...</p>;

    return (
        <main className="content container py-5">
            <h1>{movie.name}</h1>
            <MovieDetails movie={movie} onEdit={handleEdit} onDelete={handleDelete}/>


            <section className="reviews mt-5">
                <h2>Atsiliepimai</h2>

                {reviews.length === 0 && <p>Nėra atsiliepimų.</p>}

                <ul className="list-group mb-4">
                    {reviews.map((review) => (
                        <li key={review.id} className="list-group-item">
                            <p><strong>{review.createdByName || 'Anonimas'}</strong> (Įvertinimas: {review.rating}/10):
                            </p>
                            <p>{review.review}</p>
                        </li>
                    ))}
                </ul>

                {token ? (
                    <form onSubmit={handleReviewSubmit}>
                        <div className="mb-3">
                            <label htmlFor="newReview" className="form-label">Pridėti atsiliepimą:</label>
                            <textarea
                                id="newReview"
                                className="form-control"
                                rows="3"
                                value={newReview}
                                onChange={(e) => setNewReview(e.target.value)}
                                required
                            />
                            <label htmlFor="newRating" className="form-label">Įvertinimas (1–10):</label>
                            <select
                                id="newRating"
                                className="form-select"
                                value={newRating}
                                onChange={(e) => setNewRating(Number(e.target.value))}
                                required
                            >
                                {[...Array(10)].map((_, i) => (
                                    <option key={i + 1} value={i + 1}>{i + 1}</option>
                                ))}
                            </select>
                        </div>
                        <button type="submit" className="btn btn-primary">Siųsti</button>
                    </form>
                ) : (
                    <p><em>Prisijunkite, kad galėtumėte palikti atsiliepimą.</em></p>
                )}
            </section>
        </main>
    );
}

export default MovieExtendedDetails;
