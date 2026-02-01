import React, { useState } from 'react';
import { inscrireClient } from '../services/clientService';

const InscriptionClient = () => {
  const [formData, setFormData] = useState({
    nom: '',
    prenom: '',
    email: '',
    motDePasse: ''
  });
  const [message, setMessage] = useState({ type: '', text: '' });

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!formData.nom || !formData.prenom || !formData.email || !formData.motDePasse) {
      setMessage({ type: 'error', text: 'Tous les champs sont obligatoires.' });
      return;
    }

    try {
      await inscrireClient(formData);
      setMessage({ type: 'success', text: 'Inscription réussie ! Vous pouvez maintenant vous connecter.' });
      setFormData({ nom: '', prenom: '', email: '', motDePasse: '' });
    } catch (error) {
      setMessage({ 
        type: 'error', 
        text: error.response?.data?.message || "Erreur lors de l'inscription. L'email est peut-être déjà utilisé." 
      });
    }
  };

  return (
    <div className="form-container">
      <h2>Inscription Client</h2>
      {message.text && <div className={`message ${message.type}`}>{message.text}</div>}
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label>Nom :</label>
          <input type="text" name="nom" value={formData.nom} onChange={handleChange} placeholder="Votre nom" />
        </div>
        <div className="form-group">
          <label>Prénom :</label>
          <input type="text" name="prenom" value={formData.prenom} onChange={handleChange} placeholder="Votre prénom" />
        </div>
        <div className="form-group">
          <label>Email :</label>
          <input type="email" name="email" value={formData.email} onChange={handleChange} placeholder="votre@email.com" />
        </div>
        <div className="form-group">
          <label>Mot de passe :</label>
          <input type="password" name="motDePasse" value={formData.motDePasse} onChange={handleChange} placeholder="Choisissez un mot de passe" />
        </div>
        <button type="submit" className="primary" style={{ width: '100%', marginTop: '10px' }}>Créer mon compte</button>
      </form>
      <p style={{ textAlign: 'center', marginTop: '20px', fontSize: '0.9rem' }}>
        Déjà inscrit ? <a href="/login" style={{ color: 'var(--accent-color)' }}>Connectez-vous</a>
      </p>
    </div>
  );
};

export default InscriptionClient;
