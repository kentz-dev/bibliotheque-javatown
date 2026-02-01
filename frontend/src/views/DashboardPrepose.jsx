import React, { useState, useEffect } from 'react';
import { getTousLesClients } from '../services/clientService';
import { createDocument } from '../services/documentService';

const DashboardPrepose = ({ user }) => {
  const [clients, setClients] = useState([]);
  const [message, setMessage] = useState('');
  const [newDoc, setNewDoc] = useState({
    titre: '',
    annee: new Date().getFullYear(),
    categorie: 'Informatique',
    exemplairesDisponibles: 1,
    type: 'LIVRE',
    auteur: '',
    isbn: '',
    artiste: '',
    realisateur: ''
  });

  useEffect(() => {
    fetchClients();
  }, []);

  const fetchClients = async () => {
    try {
      const response = await getTousLesClients();
      setClients(response.data);
    } catch (error) {
      console.error("Erreur lors de la récupération des clients", error);
    }
  };

  const handleDocChange = (e) => {
    setNewDoc({ ...newDoc, [e.target.name]: e.target.value });
  };

  const handleCreateDoc = async (e) => {
    e.preventDefault();
    try {
      await createDocument(newDoc, user.email);
      setMessage("Document créé avec succès !");
      setNewDoc({
        titre: '',
        annee: new Date().getFullYear(),
        categorie: 'Informatique',
        exemplairesDisponibles: 1,
        type: 'LIVRE',
        auteur: '',
        isbn: '',
        artiste: '',
        realisateur: ''
      });
    } catch (error) {
      setMessage("Erreur lors de la création : " + (error.response?.status === 403 ? "Accès refusé" : "Erreur technique"));
    }
  };

  return (
    <div>
      <h2>Tableau de bord Préposé</h2>
      {message && <div className="message info-message">{message}</div>}

      <section className="form-container" style={{ maxWidth: '100%', marginBottom: '40px' }}>
        <h3>Ajouter un nouveau document</h3>
        <form onSubmit={handleCreateDoc} style={{ display: 'grid', gap: '20px', gridTemplateColumns: 'repeat(auto-fit, minmax(200px, 1fr))' }}>
          <div className="form-group">
            <label>Titre</label>
            <input name="titre" placeholder="Titre" value={newDoc.titre} onChange={handleDocChange} required />
          </div>
          <div className="form-group">
            <label>Année</label>
            <input name="annee" type="number" placeholder="Année" value={newDoc.annee} onChange={handleDocChange} required />
          </div>
          <div className="form-group">
            <label>Catégorie</label>
            <select name="categorie" value={newDoc.categorie} onChange={handleDocChange}>
              <option value="Informatique">Informatique</option>
              <option value="Roman">Roman</option>
              <option value="Musique">Musique</option>
              <option value="Film">Film</option>
            </select>
          </div>
          <div className="form-group">
            <label>Exemplaires</label>
            <input name="exemplairesDisponibles" type="number" placeholder="Stock" value={newDoc.exemplairesDisponibles} onChange={handleDocChange} required />
          </div>
          <div className="form-group">
            <label>Type de document</label>
            <select name="type" value={newDoc.type} onChange={handleDocChange}>
              <option value="LIVRE">Livre</option>
              <option value="CD">CD</option>
              <option value="DVD">DVD</option>
            </select>
          </div>
          
          {newDoc.type === 'LIVRE' && (
            <>
              <div className="form-group">
                <label>Auteur</label>
                <input name="auteur" placeholder="Auteur" value={newDoc.auteur} onChange={handleDocChange} required />
              </div>
              <div className="form-group">
                <label>ISBN</label>
                <input name="isbn" placeholder="ISBN" value={newDoc.isbn} onChange={handleDocChange} required />
              </div>
            </>
          )}
          {newDoc.type === 'CD' && (
            <div className="form-group">
              <label>Artiste</label>
              <input name="artiste" placeholder="Artiste" value={newDoc.artiste} onChange={handleDocChange} required />
            </div>
          )}
          {newDoc.type === 'DVD' && (
            <div className="form-group">
              <label>Réalisateur</label>
              <input name="realisateur" placeholder="Réalisateur" value={newDoc.realisateur} onChange={handleDocChange} required />
            </div>
          )}
          
          <button type="submit" className="primary" style={{ gridColumn: '1 / -1', marginTop: '10px' }}>Créer le document</button>
        </form>
      </section>
      
      <h3>Liste des Clients et Emprunts Actifs</h3>
      <div className="table-responsive">
        <table>
          <thead>
            <tr>
              <th>ID</th>
              <th>Nom complet</th>
              <th>Email</th>
              <th>Emprunts Actifs</th>
            </tr>
          </thead>
          <tbody>
            {clients.map(client => (
              <tr key={client.id}>
                <td>{client.id}</td>
                <td style={{ fontWeight: '600' }}>{client.prenom} {client.nom}</td>
                <td>{client.email}</td>
                <td>
                  {client.empruntsActifs && client.empruntsActifs.length > 0 ? (
                    <ul style={{ paddingLeft: '20px', margin: 0 }}>
                      {client.empruntsActifs.map((emp, index) => (
                        <li key={index} style={{ fontSize: '0.9rem' }}>{emp}</li>
                      ))}
                    </ul>
                  ) : (
                    <span style={{ color: '#999', fontStyle: 'italic' }}>Aucun emprunt</span>
                  )}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
      {clients.length === 0 && <p className="message info-message">Aucun client inscrit.</p>}
    </div>
  );
};

export default DashboardPrepose;
