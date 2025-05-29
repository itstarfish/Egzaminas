// src/pages/Dashboard.jsx
import React, {useEffect} from 'react';
import {Link, useNavigate} from 'react-router-dom';
import '../styles/home/dashboard.scss';
import {useLoading} from '../context/LoadingContext';
import {logout} from '../services/AuthServices';

function Dashboard() {
    const {setLoading} = useLoading(); // Hook to handle global loading state
    const navigate = useNavigate(); // Navigation function from react-router
    const token = localStorage.getItem('jwtToken'); // Get JWT token from localStorage

    useEffect(() => {
        document.title = 'Sveiki atvykę'; // Set the document title on component mount

        // Immediate check for token
        if (!token) {
            navigate('/login');
            return;
        }
    }, [token, navigate]);

    const handleLogout = async () => {
        setLoading(true); // Start loading process
        try {
            logout(); // Clear user authentication data
            await navigate('/'); // Wait for navigation to complete
        } finally {
            setLoading(false); // Stop loading process
        }
    };

    return (
        <main className="content">
            <div className="dashboard-wrapper">
                <nav className="sidebar">
                    <div className="sidebar-header">
                        <h4 className="text-white">Vartotojai</h4>
                    </div>
                    <ul className="nav flex-column">
                        <li className="nav-item">
                            <button
                                onClick={handleLogout} // Call logout handler on click
                                className="nav-link text-white w-100 text-start"
                            >
                                <i className="bi bi-box-arrow-right me-2"></i> Atsijungti
                            </button>
                        </li>
                    </ul>
                </nav>

                <div className="main-content">
                    <div className="container-fluid p-4">
                        <h1 className="dashboard-title">Jūsų Dashboard</h1>
                        <p className="lead text-muted">Sveiki atvykę! Valdykite savo filmus efektyviai.</p>

                        <div className="row row-cols-1 row-cols-md-2 g-4">
                            {/* Cards Section: Movie List */}
                            <div className="col">
                                <div className="card h-100">
                                    <div className="card-body">
                                        <h5 className="card-title">Filmų sąrašas</h5>
                                        <p className="card-text">Peržiūrėkite visus filmus.</p>
                                        <Link to="/movies" className="btn btn-primary">
                                            Žiūrėti sąrašą
                                        </Link>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>
    );
}

export default Dashboard;