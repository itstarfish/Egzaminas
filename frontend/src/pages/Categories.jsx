import React, {useState, useEffect} from 'react';
import {useNavigate, useSearchParams} from 'react-router-dom';
import {useAuth} from '../context/AuthContext';
import {useLoading} from '../context/LoadingContext';
import {getCategories, updateCategory, deleteCategory, createCategory} from '../services/CategoryService';
import {useCategories} from "../context/CategoryContext.jsx";

const Categories = () => {
    const {token, userRole} = useAuth();
    const {setLoading} = useLoading();
    const navigate = useNavigate();

    const [searchParams] = useSearchParams();
    const searchQuery = searchParams.get('search') || '';
    const [categories, setCategories] = useState([]);
    const [error, setError] = useState(null);
    const [modal, setModal] = useState({type: null, data: null});
    const [showCreateModal, setShowCreateModal] = useState(false);

    const fetchCategories = async () => {
        try {
            setLoading(true);
            let response;

            if (searchQuery.trim() !== '') {
                response = await getCategories(token, {search: searchQuery});
            } else {
                response = await getCategories(token);
            }

            setCategories(Array.isArray(response) ? response : []);
        } catch (err) {
            console.error('Error fetching categories:', err);
            setError('Failed to fetch categories.');
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        if (!token) {
            navigate('/login');
        } else {
            fetchCategories();
        }
    }, [token, searchQuery, navigate]);

    const handleCreate = async (categoryData) => {
        try {
            await createCategory(categoryData, token);
            await fetchCategories();
            setShowCreateModal(false);
        } catch (err) {
            setError('Failed to create category.');
        }
    };

    const handleUpdate = async (id, categoryData) => {
        try {
            await updateCategory(id, categoryData, token);
            await fetchCategories();
            setModal({type: null, data: null});
        } catch (err) {
            setError('Failed to update category.');
        }
    };

    const handleDelete = async (id) => {
        try {
            await deleteCategory(id, token);
            await fetchCategories();
        } catch (err) {
            setError('Failed to delete category.');
        }
    };

    if (error) {
        return <div>Error: {error}</div>;
    }

    return (
        <main>
            <div className="container">
                <h1>Categories</h1>
                {userRole === 'ROLE_ADMIN' && (
                    <button
                        className="btn btn-primary mb-3"
                        onClick={() => setShowCreateModal(true)}
                    >
                        Create Category
                    </button>
                )}

                {searchQuery && (
                    <p>
                        Search results for: <strong>{searchQuery}</strong>
                    </p>
                )}

                {categories.length > 0 ? (
                    <div className="row row-cols-1 row-cols-md-2 g-4">
                        {categories.map(category => (
                            <div key={category.id} className="col">
                                <div className="card">
                                    <div className="card-body">
                                        <h5 className="card-title">{category.name}</h5>
                                        <p className="card-text">{category.description}</p>
                                        {userRole === 'ROLE_ADMIN' && (
                                            <div className="mt-2">
                                                <button
                                                    className="btn btn-warning btn-sm me-2"
                                                    onClick={() => setModal({type: 'update', data: category})}
                                                >
                                                    Edit
                                                </button>
                                                <button
                                                    className="btn btn-danger btn-sm"
                                                    onClick={() => handleDelete(category.id)}
                                                >
                                                    Delete
                                                </button>
                                            </div>
                                        )}
                                    </div>
                                </div>
                            </div>
                        ))}
                    </div>
                ) : (
                    <p>No categories found.</p>
                )}
            </div>
        </main>
    );
};

export default Categories;