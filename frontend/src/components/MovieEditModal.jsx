import {useState, useEffect} from 'react';
import Modal from 'react-bootstrap/Modal';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import {useCategories} from '../context/CategoryContext';

const MovieEditModal = ({show, movie, onClose, onUpdate}) => {
    const {getCategories} = useCategories();
    const [categories, setCategories] = useState([]);
    const [editedMovie, setEditedMovie] = useState({
        id: movie.id,
        name: movie.name || '',
        description: movie.description || '',
        movieCategory: movie.movieCategory ? String(movie.movieCategory) : '',
        imdbrating: movie.imdbrating || ''
    });

    useEffect(() => {
        const fetchCategories = async () => {
            try {
                const response = await getCategories();

                const categoryList = response?.data || response || [];
                setCategories(categoryList);

            } catch (error) {
                console.error('Error fetching categories:', error);
            }
        };

        fetchCategories();
    }, [getCategories]);

    const handleChange = (e) => {
        const {name, value} = e.target;
        setEditedMovie(prev => ({...prev, [name]: value}));
    };

    const handleSubmit = (e) => {
        e.preventDefault();

        const cleanedMovie = {
            ...editedMovie,
            movieCategory: Number(editedMovie.movieCategory),
            imdbrating: editedMovie.imdbrating ? parseFloat(editedMovie.imdbrating) : undefined,
        };

        onUpdate(cleanedMovie);
    };

    return (
        <Modal show={show} onHide={onClose}>
            <Modal.Header closeButton>
                <Modal.Title>Edit Movie</Modal.Title>
            </Modal.Header>
            <Form onSubmit={handleSubmit}>
                <Modal.Body>
                    <Form.Group className="mb-3">
                        <Form.Label>Movie Name</Form.Label>
                        <Form.Control
                            type="text"
                            name="name"
                            value={editedMovie.name}
                            onChange={handleChange}
                            required
                        />
                    </Form.Group>
                    <Form.Group className="mb-3">
                        <Form.Label>Description</Form.Label>
                        <Form.Control
                            as="textarea"
                            name="description"
                            value={editedMovie.description}
                            onChange={handleChange}
                            rows={3}
                        />
                    </Form.Group>
                    <Form.Group className="mb-3">
                        <Form.Label>Category</Form.Label>
                        <Form.Select
                            name="movieCategory"
                            value={editedMovie.movieCategory}
                            onChange={handleChange}
                            required
                        >
                            <option value="">Select a category</option>
                            {categories.map((category) => (
                                <option key={category.id} value={category.id}>
                                    {category.name}
                                </option>
                            ))}
                        </Form.Select>
                    </Form.Group>
                    <Form.Group className="mb-3">
                        <Form.Label>IMDB Rating</Form.Label>
                        <Form.Control
                            type="number"
                            name="imdbrating"
                            value={editedMovie.imdbrating}
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
                        Save Changes
                    </Button>
                </Modal.Footer>
            </Form>
        </Modal>
    );
};

export default MovieEditModal;