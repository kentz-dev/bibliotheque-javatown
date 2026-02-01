import React, { useState, useEffect } from 'react';
import { getDocuments, searchDocuments } from '../services/documentService';
import { creerEmpruntsBatch } from '../services/empruntService';

const Catalogue = ({ user }) => {
  const [documents, setDocuments] = useState([]);
  const [message, setMessage] = useState('');
  const [panier, setPanier] = useState([]);
  const [filters, setFilters] = useState({
    titre: '',
    auteur: '',
    annee: '',
    categorie: ''
  });

  useEffect(() => {
    fetchDocuments();
  }, []);

  const fetchDocuments = async () => {
    try {
      const response = await getDocuments();
      setDocuments(response.data);
    } catch (error) {
      console.error("Erreur lors de la récupération des documents", error);
    }
  };

  const handleFilterChange = (e) => {
    setFilters({ ...filters, [e.target.name]: e.target.value });
  };

  const handleSearch = async (e) => {
    e.preventDefault();
    try {
      const response = await searchDocuments(filters);
      setDocuments(response.data);
    } catch (error) {
      console.error("Erreur lors de la recherche", error);
    }
  };

  const ajouterAuPanier = (doc) => {
    if (panier.find(item => item.id === doc.id)) {
      setMessage("Ce document est déjà dans votre panier.");
      return;
    }
    setPanier([...panier, doc]);
    setMessage(`${doc.titre} ajouté au panier.`);
  };

  const retirerDuPanier = (id) => {
    setPanier(panier.filter(item => item.id !== id));
  };

  const validerEmprunt = async () => {
    if (!user || panier.length === 0) return;
    try {
      const ids = panier.map(item => item.id);
      await creerEmpruntsBatch(user.id, ids);
      setMessage(`Emprunt de ${panier.length} document(s) réussi !`);
      setPanier([]);
      fetchDocuments();
    } catch (error) {
      setMessage("Erreur lors de l'emprunt : " + (error.response?.data?.message || "Erreur technique"));
    }
  };

  return (
    <div>
      <h2>Catalogue de Documents</h2>
      {message && <div className="message info-message">{message}</div>}

      {user && user.type === 'CLIENT' && panier.length > 0 && (
        <section className="form-container" style={{ maxWidth: '100%', marginBottom: '30px', border: '2px dashed var(--accent-color)' }}>
          <h3>🛒 Mon Panier d'emprunt ({panier.length})</h3>
          <ul style={{ marginBottom: '15px' }}>
            {panier.map(item => (
              <li key={item.id} style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '5px' }}>
                <span>{item.titre} ({item.type})</span>
                <button className="danger" onClick={() => retirerDuPanier(item.id)} style={{ padding: '2px 8px', fontSize: '0.8rem' }}>Retirer</button>
              </li>
            ))}
          </ul>
          <button className="primary" onClick={validerEmprunt} style={{ width: '100%' }}>Confirmer l'emprunt groupé</button>
        </section>
      )}
      
      <form onSubmit={handleSearch} className="search-bar">
        <div className="form-group">
          <label>Titre</label>
          <input name="titre" placeholder="Titre du document" value={filters.titre} onChange={handleFilterChange} />
        </div>
        <div className="form-group">
          <label>Auteur / Artiste</label>
          <input name="auteur" placeholder="Auteur, artiste..." value={filters.auteur} onChange={handleFilterChange} />
        </div>
        <div className="form-group">
          <label>Année</label>
          <input name="annee" type="number" placeholder="Ex: 2023" value={filters.annee} onChange={handleFilterChange} />
        </div>
        <div className="form-group">
          <label>Catégorie</label>
          <select name="categorie" value={filters.categorie} onChange={handleFilterChange}>
            <option value="">Toutes catégories</option>
            <option value="Informatique">Informatique</option>
            <option value="Roman">Roman</option>
            <option value="Musique">Musique</option>
            <option value="Film">Film</option>
          </select>
        </div>
        <button type="submit" className="primary">Rechercher</button>
        <button type="button" onClick={() => { setFilters({ titre: '', auteur: '', annee: '', categorie: '' }); fetchDocuments(); }}>Réinitialiser</button>
      </form>

      <div className="card-grid">
        {documents.map(doc => (
          <div key={doc.id} className="card">
            <div className="card-body">
              <span className={`badge badge-${doc.type?.toLowerCase()}`}>{doc.type}</span>
              <h3 className="card-title">{doc.titre}</h3>
              <p><strong>Année :</strong> {doc.annee}</p>
              <p><strong>Catégorie :</strong> {doc.categorie}</p>
              {doc.auteur && <p><strong>Auteur :</strong> {doc.auteur}</p>}
              {doc.artiste && <p><strong>Artiste :</strong> {doc.artiste}</p>}
              {doc.realisateur && <p><strong>Réalisateur :</strong> {doc.realisateur}</p>}
              <p>
                <strong>Statut :</strong> 
                <span style={{ color: doc.exemplairesDisponibles > 0 ? 'var(--success-color)' : 'var(--danger-color)', marginLeft: '5px', fontWeight: 'bold' }}>
                  {doc.exemplairesDisponibles > 0 ? `${doc.exemplairesDisponibles} disponible(s)` : 'Épuisé'}
                </span>
              </p>
            </div>
            {user && user.type === 'CLIENT' && (
              <div className="card-footer">
                <button 
                  className="primary"
                  style={{ width: '100%' }}
                  onClick={() => ajouterAuPanier(doc)} 
                  disabled={doc.exemplairesDisponibles <= 0 || panier.some(item => item.id === doc.id)}
                >
                  {panier.some(item => item.id === doc.id) ? 'Dans le panier' : 'Ajouter au panier'}
                </button>
              </div>
            )}
          </div>
        ))}
      </div>
      {documents.length === 0 && (
        <div className="message info-message" style={{ marginTop: '20px' }}>
          Aucun document ne correspond à vos critères de recherche.
        </div>
      )}
    </div>
  );
};

export default Catalogue;
