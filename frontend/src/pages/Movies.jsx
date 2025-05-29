import React, {useState, useEffect} from 'react';
import {useNavigate, useSearchParams} from 'react-router-dom';
import {useAuth} from '../context/AuthContext';
import {useMovies} from '../context/MovieContext';
import {useLoading} from '../context/LoadingContext';
import MovieDetails from '../components/MovieDetails';
import MovieEditModal from '../components/MovieEditModal';
import MovieCreateModal from '../components/MovieCreateModal';


const Movies = () => {
    const {token, userRole} = useAuth(); //get userRole here
    const {setLoading} = useLoading();
    const {getMovies, updateMovie, deleteMovie, createMovie} = useMovies();
    const navigate = useNavigate();

    const [searchParams] = useSearchParams();
    const searchQuery = searchParams.get('search') || '';
    const [movies, setMovies] = useState([]);
    const [error, setError] = useState(null);
    const [modal, setModal] = useState({type: null, data: null});
    const [showCreateModal, setShowCreateModal] = useState(false);

    const fetchMovies = async () => {
        try {
            setLoading(true);
            let response;

            if (searchQuery.trim() !== '') {
                response = await getMovies({search: searchQuery});
            } else {
                response = await getMovies();
            }

            setMovies(Array.isArray(response.data) ? response.data : []);
        } catch (err) {
            console.error('Error fetching movies:', err);
            setError('Failed to fetch movies.');
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        if (!token) {
            navigate('/login');
        } else {
            fetchMovies();
        }
    }, [token, searchQuery, navigate]);

    const handleCreate = async (movieData) => {
        try {
            await createMovie(movieData);
            await fetchMovies();
            setShowCreateModal(false);
        } catch (err) {
            setError('Failed to create movie.');
        }
    };

    const handleUpdate = async (id, movieData) => {
        try {
            await updateMovie(id, movieData);
            await fetchMovies();
            setModal({type: null, data: null});
        } catch (err) {
            setError('Failed to update movie.');
        }
    };

    const handleDelete = async (id) => {
        try {
            await deleteMovie(id);
            await fetchMovies();
        } catch (err) {
            setError('Failed to delete movie.');
        }
    };

    if (error) {
        return <div>Error: {error}</div>;
    }

    return (
        <main>
            <div className="container">
                <h1>Movies</h1>
                {userRole === 'ROLE_ADMIN' && (
                    <button
                        className="btn btn-primary mb-3"
                        onClick={() => setShowCreateModal(true)}
                    >
                        Create Movie
                    </button>
                )}

                {searchQuery && (
                    <p>
                        Search results for: <strong>{searchQuery}</strong>
                    </p>
                )}

                {movies.length > 0 ? (
                    <div className="row row-cols-1 row-cols-md-2 g-4">
                        {movies.map(movie => (
                            <div key={movie.id} className="col">
                                <MovieDetails movie={movie}/>
                                {/* Show Edit/Delete buttons only for admin */}
                                {userRole === 'ROLE_ADMIN' && (
                                    <div className="mt-2">
                                        <button
                                            className="btn btn-warning btn-sm me-2"
                                            onClick={() => setModal({type: 'update', data: movie})}
                                        >
                                            Edit
                                        </button>
                                        <button
                                            className="btn btn-danger btn-sm"
                                            onClick={() => handleDelete(movie.id)}
                                        >
                                            Delete
                                        </button>
                                    </div>
                                )}
                            </div>
                        ))}
                    </div>
                ) : (
                    <p>No movies found.</p>
                )}

                {/* Modals */}
                {modal.type === 'update' && (
                    <MovieEditModal
                        movie={modal.data}
                        onClose={() => setModal({type: null, data: null})}
                        onUpdate={(movieData) => handleUpdate(modal.data.id, movieData)}
                        show={true}
                    />
                )}
                {showCreateModal && (
                    <MovieCreateModal
                        onClose={() => setShowCreateModal(false)}
                        onCreate={handleCreate}
                        show={true}
                    />
                )}
            </div>
        </main>
    );
};

export default Movies;