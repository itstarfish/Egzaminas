// src/pages/Register.jsx
import React, {useState, useEffect} from 'react';
import {Link, useNavigate} from 'react-router-dom';
import '../styles/user/user.scss';
import {useLoading} from '../context/LoadingContext';
import {createUser} from '../services/AuthServices';

function Register() {
    const {setLoading} = useLoading();
    const navigate = useNavigate();
    const [username, setUsername] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [passwordConfirm, setPasswordConfirm] = useState('');
    const [error, setError] = useState(null);
    const [isLoading, setIsLoading] = useState(false);

    useEffect(() => {
        document.title = 'Registracija';
    }, []);

    const handleSubmit = async (e) => {
        e.preventDefault();
        setIsLoading(true);
        setLoading(true);
        setError(null);


        if (!username.trim() || !email.trim() || !password.trim() || !passwordConfirm.trim()) {
            setError('Prašome užpildyti visus laukus.'); // Show error for incomplete form
            setIsLoading(false);
            setLoading(false);
            return;
        }


        if (password.length < 6) {
            setError('Slaptažodis turi būti bent 6 simboliai.'); // Show error for short password
            setIsLoading(false);
            setLoading(false);
            return;
        }

        const userData = {username, email, password, passwordConfirm}; // Prepare user data object
        try {
            await createUser(userData);
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
            <h2>Registracija</h2>
            <form onSubmit={handleSubmit} noValidate> {/* Form submission handler */}
                <label htmlFor="username">Vartotojo vardas</label>
                <input
                    type="text"
                    id="username"
                    name="username"
                    className="input"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)} // Handle username input changes
                    required
                    disabled={isLoading} // Disable input when loading
                />
                <label htmlFor="email">El. paštas</label>
                <input
                    type="email"
                    id="email"
                    name="email"
                    className="input"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)} // Handle email input changes
                    required
                    disabled={isLoading} // Disable input when loading
                />
                <label htmlFor="password">Slaptažodis</label>
                <input
                    type="password"
                    id="password"
                    name="password"
                    className="input"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)} // Handle password input changes
                    required
                    disabled={isLoading} // Disable input when loading
                />
                <label htmlFor="password">Pakartok Slaptažodį</label>
                <input
                    type="password"
                    id="passwordConfirm"
                    name="passwordConfirm"
                    className="input"
                    value={passwordConfirm}
                    onChange={(e) => setPasswordConfirm(e.target.value)} // Handle password confirm input changes
                    required
                    disabled={isLoading} // Disable input when loading
                />
                <button type="submit" className='btn-submit' disabled={isLoading}> {/* Disable button when loading */}
                    {isLoading ? 'Registruojama...' : 'Registruotis'} {/* Show loading text while processing */}
                </button>
                {error && <p className="error">{error}</p>} {/* Display error message if exists */}
            </form>
            <div className="form-footer">
                Jau turite paskyrą? <Link to="/login">Prisijunkite</Link> {/* Link to login page */}
            </div>
        </div>
    );
}

export default Register;