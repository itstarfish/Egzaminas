import React, { useEffect } from 'react';
import { Link } from 'react-router-dom';
import { useLoading } from '../context/LoadingContext';
import { useAuth } from '../context/AuthContext';

function Home() {
    const { setLoading } = useLoading();
    const { token, logout } = useAuth();  // use token here

    useEffect(() => {
        setLoading(true);
        const fetchData = () => {
            setTimeout(() => setLoading(false), 200);
        };
        fetchData();
    }, [setLoading]);

    const handleLogout = () => {
        logout();
    };

    return (
        <main>
            <div className="home-container">
                <div className="bg-light text-center py-5">
                    <h1 className="display-5 fw-bold text-dark mb-3">Sveiki atvykę į Filmų Informacinę sistemą</h1>
                    <p className="lead text-secondary mb-3">Filmų valdymas</p>

                    {!token ? (
                        <>
                            <Link to="/register" className="btn btn-primary btn-lg me-2">Registruotis</Link>
                            <Link to="/login" className="btn btn-secondary btn-lg">Prisijungti</Link>
                        </>
                    ) : (
                        <>
                            <Link to="/movies" className="btn btn-primary btn-lg me-2">Filmai</Link>
                            <button onClick={handleLogout} className="btn btn-danger btn-lg">Atsijungti</button>
                        </>
                    )}
                </div>

                {/* rest of your content unchanged */}
                <div className="container my-5">
                    <div className="row align-items-center">
                        <div className="col-md-6">
                            <h2 className="mb-2">Kaip tai veikia?</h2>
                            <p className="mb-4">
                                Naudodami IS, galite tvarkyti filmų duomenis, bei peržiūrėti atsiliepimus.
                            </p>

                            <div className="d-flex flex-column gap-3">
                                <div className="d-flex align-items-center p-3 border rounded shadow-sm bg-white">
                                    <span className="text-success fs-4 me-2">✔️</span>
                                    <span>Sukurkite filmą</span>
                                </div>
                                <div className="d-flex align-items-center p-3 border rounded shadow-sm bg-white">
                                    <span className="text-success fs-4 me-2">✔️</span>
                                    <span>Valdyti filmų duomenis</span>
                                </div>
                                <div className="d-flex align-items-center p-3 border rounded shadow-sm bg-white">
                                    <span className="text-success fs-4 me-2">✔️</span>
                                    <span>Peržiūrėti visu filmų duomenis</span>
                                </div>
                                <div>
                                    <Link to="/movies" className="btn btn-outline-primary mt-3">Filmai</Link>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>
    );
}

export default Home;
