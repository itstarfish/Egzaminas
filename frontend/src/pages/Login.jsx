import React, { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import '../styles/user/user.scss';
import { useLoading } from '../context/LoadingContext';
import { login } from '../services/AuthServices';
import { useAuth } from '../context/AuthContext';

function Login() {
    const { setLoading } = useLoading(); // Global loading context
    const navigate = useNavigate();
    const { userRole } = useAuth(); // Access current user role from context

    const [username, setUserName] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState(null);
    const [isLoading, setIsLoading] = useState(false);

    useEffect(() => {
        document.title = 'Prisijungimas';
    }, []);

    const handleSubmit = async (e) => {
        e.preventDefault();
        setIsLoading(true);
        setLoading(true);
        setError(null);

        if (!username.trim() || !password.trim()) {
            setError('Prašome užpildyti visus laukus.');
            setIsLoading(false);
            setLoading(false);
            return;
        }

        try {
            const userData = await login(username, password);

            // role from userData
            const role = Array.isArray(userData.roles) ? userData.roles[0] : 'nežinoma';

            console.log('Prisijungimas sėkmingas:', username, 'Rolė:', role);

            navigate('/dashboard');
        } catch (err) {
            setError(err.message);
        } finally {
            setIsLoading(false);
            setLoading(false);
        }
    };

    return (
        <div className="form-container">
            <h2>Prisijungimas</h2>

            {/* Show user role if available */}
            {userRole && (
                <p style={{ marginBottom: '1rem', color: 'green' }}>
                    Jūsų rolė: <strong>{userRole}</strong>
                </p>
            )}

            <form onSubmit={handleSubmit} noValidate>
                <label htmlFor="username">Vartotojo vardas</label>
                <input
                    type="text"
                    id="username"
                    name="username"
                    className="input"
                    value={username}
                    onChange={(e) => setUserName(e.target.value)}
                    required
                    disabled={isLoading}
                />

                <label htmlFor="password">Slaptažodis</label>
                <input
                    type="password"
                    id="password"
                    name="password"
                    className="input"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required
                    disabled={isLoading}
                />

                <button type="submit" className="btn-submit" disabled={isLoading}>
                    {isLoading ? 'Prisijungiama...' : 'Prisijungti'}
                </button>

                {error && <p className="error">{error}</p>}
            </form>

            <div className="form-footer">
                Neturite paskyros? <Link to="/register">Užsiregistruokite</Link>
            </div>
        </div>
    );
}

export default Login;
