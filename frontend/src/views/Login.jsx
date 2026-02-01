import React, { useState } from 'react';
import authService from '../services/authService';

function Login({ onLogin }) {
  const [email, setEmail] = useState('');
  const [motDePasse, setMotDePasse] = useState('');
  const [error, setError] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    try {
      const user = await authService.login(email, motDePasse);
      onLogin(user);
    } catch (err) {
      setError('Email ou mot de passe incorrect.');
    }
  };

  return (
    <div className="login-container">
      <h2>Connexion</h2>
      {error && <div className="message error">{error}</div>}
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label>Email :</label>
          <input
            type="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
            placeholder="votre@email.com"
          />
        </div>
        <div className="form-group">
          <label>Mot de passe :</label>
          <input
            type="password"
            value={motDePasse}
            onChange={(e) => setMotDePasse(e.target.value)}
            required
            placeholder="••••••••"
          />
        </div>
        <button type="submit" className="primary" style={{ width: '100%', marginTop: '10px' }}>Se connecter</button>
      </form>
      <p style={{ textAlign: 'center', marginTop: '20px', fontSize: '0.9rem' }}>
        Nouveau ici ? <a href="/inscription" style={{ color: 'var(--accent-color)' }}>S'inscrire</a>
      </p>
    </div>
  );
}

export default Login;
