import {useEffect, useState} from 'react';
import {getCategories} from '../services/CategoryService.js';
import {useAuth} from '../context/AuthContext';
import {useNavigate} from 'react-router-dom';

const MovieDetails = ({movie, onEdit, onDelete}) => {
    const [categoryName, setCategoryName] = useState('');
    const {token} = useAuth();
    const navigate = useNavigate();

    useEffect(() => {
        const fetchCategoryName = async () => {
            try {
                const categories = await getCategories(token);
                const foundCategory = categories.find(c => c.id === movie.movieCategory);
                setCategoryName(foundCategory ? foundCategory.name : 'Uncategorized');
            } catch (error) {
                console.error('Failed to fetch categories:', error);
                setCategoryName('Uncategorized');
            }
        };

        if (movie.movieCategory) {
            fetchCategoryName();
        } else {
            setCategoryName('Uncategorized');
        }
    }, [movie.movieCategory, token]);

    const formatDate = (dateString) => {
        if (!dateString) return 'N/A';
        return new Date(dateString).toLocaleDateString();
    };

    return (
        <div className="col" onClick={() => navigate(`/movies/${movie.id}`)}>
            <div className="card">
                <div className="card-body">
                    <h5 className="card-title">{movie.name || 'Untitled'}</h5>
                    <div className="row">
                        <div className="col-md-8">
                            <p className="card-text">
                                <strong>Aprašymas:</strong> {movie.description || 'No description available'}<br/>
                                <strong>IMDB ratingas:</strong> {movie.imdbrating || 'No rating'}<br/>
                                <strong>Kategorija:</strong> {categoryName}<br/>
                                <strong>Sukūrė:</strong> {movie.createdByName || 'Unknown'}<br/>
                                <strong>Sukūrimo data:</strong> {formatDate(movie.createdAt)}<br/>
                                {movie.updatedAt && (
                                    <><strong>Atnaujintas:</strong> {formatDate(movie.updatedAt)}<br/></>
                                )}
                            </p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default MovieDetails;