import {useState, useEffect} from 'react';
import Modal from 'react-bootstrap/Modal';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
// Remove useMovies import, since categories come from useCategories now
import {useCategories} from '../context/CategoryContext';

const MovieCreateModal = ({show, onClose, onCreate}) => {
    const {getCategories} = useCategories();  // Use getCategories from CategoryContext
    const [categories, setCategories] = useState([]);
    const [movie, setMovie] = useState({
        name: '',
        description: '',
        movieCategory: '',
        imdbrating: ''
    });

    useEffect(() => {
        const fetchCategories = async () => {
            try {
                const response = await getCategories();
                if (response && Array.isArray(response)) {
                    setCategories(response);
                } else if (response && Array.isArray(response.data)) {
                    setCategories(response.data);
                } else {
                    console.error('Unexpected categories format:', response);
                }
            } catch (error) {
                console.error('Error fetching categories:', error);
            }
        };

        fetchCategories();
    }, [getCategories]);

    const handleChange = (e) => {
        const {name, value} = e.target;
        setMovie(prev => ({...prev, [name]: value}));
    };

    const handleSubmit = (e) => {
        e.preventDefault();

        const cleanedMovie = {
            ...movie,
            movieCategory: Number(movie.movieCategory),
            imdbrating: movie.imdbrating ? parseFloat(movie.imdbrating) : undefined,
        };

        onCreate(cleanedMovie);
    };

    return (
        <Modal show={show} onHide={onClose}>
            <Modal.Header closeButton>
                <Modal.Title>Create New Movie</Modal.Title>
            </Modal.Header>
            <Form onSubmit={handleSubmit}>
                <Modal.Body>
                    <Form.Group className="mb-3">
                        <Form.Label>Movie Name</Form.Label>
                        <Form.Control
                            type="text"
                            name="name"
                            value={movie.name}
                            onChange={handleChange}
                            required
                        />
                    </Form.Group>
                    <Form.Group className="mb-3">
                        <Form.Label>Description</Form.Label>
                        <Form.Control
                            as="textarea"
                            name="description"
                            value={movie.description}
                            onChange={handleChange}
                            rows={3}
                        />
                    </Form.Group>
                    <Form.Group className="mb-3">
                        <Form.Label>Category</Form.Label>
                        <Form.Select
                            name="movieCategory"
                            value={movie.movieCategory}
                            onChange={handleChange}
                            required
                        >
                            <option value="">Select a category</option>
                            {categories.length > 0 ? (
                                categories.map(category => (
                                    <option key={category.id} value={category.id}>
                                        {category.name}
                                    </option>
                                ))
                            ) : (
                                <option disabled>Loading categories...</option>
                            )}
                        </Form.Select>
                    </Form.Group>
                    <Form.Group className="mb-3">
                        <Form.Label>IMDB Rating</Form.Label>
                        <Form.Control
                            type="number"
                            name="imdbrating"
                            value={movie.imdbrating}
                            onChange={handleChange}
                            step="0.1"
                            min="0"
                            max="10"
                        />
                    </Form.Group>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={onClose}>
                        Cancel
                    </Button>
                    <Button variant="primary" type="submit">
                        Create Movie
                    </Button>
                </Modal.Footer>
            </Form>
        </Modal>
    );
};

export default MovieCreateModal;