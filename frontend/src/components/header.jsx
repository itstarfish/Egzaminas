import React, {useState, useEffect} from 'react';
import {Link, useNavigate} from 'react-router-dom';
import '../styles/base/header.scss';
import {useAuth} from '../context/AuthContext';
import {useMovies} from '../context/MovieContext';

function Header() {
    const {token, userRole, logout} = useAuth(); // get userRole from context
    const {getMovies} = useMovies();
    const isAuthenticated = !!token; // convert token to boolean
    const [searchValue, setSearchValue] = useState('');
    const [categories, setCategories] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchCategories = async () => {
            try {
                const response = await getMovies();
                const uniqueCategories = [...new Set(response.data.map(movie => movie.movieCategory))];
                setCategories(uniqueCategories);
            } catch (error) {
                console.error('Error fetching categories:', error);
            }
        };

        if (isAuthenticated) {
            fetchCategories();
        }
    }, [isAuthenticated]);

    const handleSearch = (e) => {
        e.preventDefault();

        if (searchValue.trim() !== '') {
            // Redirect to the movies page with the search query as a URL parameter
            navigate(`/movies?search=${encodeURIComponent(searchValue.trim())}`);
        }
    };

    return (
        <header>
            <nav className="navbar navbar-expand-lg bg-primary">
                <div className="container">
                    <Link className="logo-title" to="/">Movie IS</Link>

                    <button
                        className="navbar-toggler"
                        type="button"
                        data-bs-toggle="collapse"
                        data-bs-target="#navbarNav"
                    >
                        <span className="navbar-toggler-icon"></span>
                    </button>

                    <div className="collapse navbar-collapse" id="navbarNav">
                        <ul className="navbar-nav ms-auto align-items-center">
                            {/* Common link for everyone */}
                            <li className="nav-item">
                                <Link className="nav-link" to="/">Pagrindinis</Link>
                            </li>

                            {/* Role-based links */}
                            {isAuthenticated && (
                                <>
                                    <li className="nav-item dropdown">
                                        <a className="nav-link dropdown-toggle" href="#" id="navbarDropdown"
                                           role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                            Filmai
                                        </a>
                                        <ul className="dropdown-menu" aria-labelledby="navbarDropdown">
                                            <li><Link className="dropdown-item" to="/movies">Visi Filmai</Link></li>
                                            {categories.map(category => (
                                                <li key={category}>
                                                    <Link
                                                        className="dropdown-item"
                                                        to={`/movies?category=${category}`}
                                                    >
                                                        {category}
                                                    </Link>
                                                </li>
                                            ))}
                                        </ul>
                                    </li>
                                        <li className="nav-item">
                                            <Link className="nav-link" to="/categories">Kategorijos</Link>
                                        </li>
                                </>
                            )}

                            {!isAuthenticated && (
                                <>
                                    <li className="nav-item">
                                        <Link className="nav-link" to="/login">Prisijungti</Link>
                                    </li>
                                    <li className="nav-item">
                                        <Link className="nav-link" to="/register">Registracija</Link>
                                    </li>
                                </>
                            )}

                            {/* Logout button for authenticated users */}
                            {isAuthenticated && (
                                <li className="nav-item">
                                    <button
                                        onClick={logout}
                                        className="nav-link btn btn-link"
                                        style={{cursor: 'pointer', padding: 0}}
                                    >
                                        Atsijungti
                                    </button>
                                </li>
                            )}

                            {/* Search Form */}
                            <li className="nav-item ms-3">
                                <form className="d-flex" onSubmit={handleSearch}>
                                    <input
                                        type="search"
                                        className="form-control search-bar"
                                        placeholder="Ieškoti filmų..."
                                        value={searchValue}
                                        onChange={(e) => setSearchValue(e.target.value)}
                                    />
                                    <button className="btn btn-light ms-2" type="submit">Paieška</button>
                                </form>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>
        </header>
    );
}

export default Header;