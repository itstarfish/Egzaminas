import React from 'react';
import '../styles/base/footer.scss';

// Footer component to display the footer of the application
function Footer() {
    return (
        <footer className="bg-dark text-white text-center">
            {/* Footer text with copyright information */}
            <p>&copy; Filmų Informacinė Systema - Visos teisės saugomos</p>
        </footer>
    );
}

export default Footer;