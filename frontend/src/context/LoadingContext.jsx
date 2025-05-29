import React, {createContext, useState, useContext} from 'react';
import '../styles/base/loading.scss';

// Create a context for loading state
export const LoadingContext = createContext();

// Provider component to handle loading state and display loading overlay
export function LoadingProvider({children}) {
    // State to manage loading status
    const [loading, setLoading] = useState(false);

    return (
        <LoadingContext.Provider value={{loading, setLoading}}>
            {/* Conditional rendering of loading overlay */}
            {loading && (
                <div className="loading-overlay">
                    <div className="loading-container">
                        <div className="spinner"></div>
                        <p>Kraunama...</p>
                    </div>
                </div>
            )}
            {children}
        </LoadingContext.Provider>
    );
}

// Custom hook to access loading context
export const useLoading = () => useContext(LoadingContext);