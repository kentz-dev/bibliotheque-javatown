import React, { useState, useEffect } from 'react';
import { BrowserRouter as Router, Routes, Route, Link, Navigate } from 'react-router-dom';
import InscriptionClient from './views/InscriptionClient';
import Catalogue from './views/Catalogue';
import MesEmprunts from './views/MesEmprunts';
import DashboardPrepose from './views/DashboardPrepose';
import Login from './views/Login';
import './App.css';

function App() {
  const [user, setUser] = useState(null);

  useEffect(() => {
    const savedUser = localStorage.getItem('user');
    if (savedUser) {
      setUser(JSON.parse(savedUser));
    }
  }, []);

  const handleLogin = (userData) => {
    setUser(userData);
    localStorage.setItem('user', JSON.stringify(userData));
  };

  const handleLogout = () => {
    setUser(null);
    localStorage.removeItem('user');
  };

  return (
    <Router>
      <header>
        <div className="container">
          <div className="header-content">
            <h1>JavaTown Library</h1>
            {user && (
              <div className="user-info">
                <span>{user.prenom} {user.nom} ({user.type})</span>
                <button className="danger" onClick={handleLogout} style={{ padding: '5px 10px', fontSize: '0.8rem' }}>Déconnexion</button>
              </div>
            )}
          </div>
        </div>
      </header>
      <nav>
        <Link to="/">Catalogue</Link>
        {!user && <Link to="/inscription">S'inscrire</Link>}
        {!user && <Link to="/login">Connexion</Link>}
        {user && user.type === 'CLIENT' && <Link to="/mes-emprunts">Mes Emprunts</Link>}
        {user && user.type === 'PREPOSE' && <Link to="/admin">Dashboard Préposé</Link>}
      </nav>
      
      <div className="container">
        <main style={{ marginTop: '2rem' }}>
          <Routes>
            <Route path="/" element={<Catalogue user={user} />} />
            <Route path="/login" element={!user ? <Login onLogin={handleLogin} /> : <Navigate to="/" />} />
            <Route path="/inscription" element={!user ? <InscriptionClient /> : <Navigate to="/" />} />
            <Route path="/mes-emprunts" element={user && user.type === 'CLIENT' ? <MesEmprunts user={user} /> : <Navigate to="/login" />} />
            <Route path="/admin" element={user && user.type === 'PREPOSE' ? <DashboardPrepose user={user} /> : <Navigate to="/login" />} />
          </Routes>
        </main>

        <footer>
          <p>&copy; 2026 Bibliothèque Municipale de JavaTown — Système de Gestion de Documents</p>
        </footer>
      </div>
    </Router>
  );
}

export default App;
