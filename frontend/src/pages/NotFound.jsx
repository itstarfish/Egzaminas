import React from 'react';

// NotFound component displays a 404 error message for undefined routes
function NotFound() {
    return (
        <div
            className="not-found-container"
            style={{textAlign: 'center', padding: '50px'}} // Inline styles for centering and padding
        >
            <h1>404 - Puslapis nerastas</h1> {/* Heading for the 404 error */}
            <p>Atsiprašome, bet ieškomas puslapis neegzistuoja.</p> {/* Message for the user */}
            <a
                href="/"
                style={{color: '#007bff', textDecoration: 'none'}} // Inline styles for the link
            >
                Grįžti į pagrindinį puslapį {/* Link to navigate back to the home page */}
            </a>
        </div>
    );
}

export default NotFound; // Exporting the NotFound component for use in other parts of the app