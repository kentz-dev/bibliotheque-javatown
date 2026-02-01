import React, { useState, useEffect } from 'react';
import { getEmpruntsActifsClient, retournerDocument } from '../services/empruntService';

const MesEmprunts = ({ user }) => {
  const [emprunts, setEmprunts] = useState([]);
  const [message, setMessage] = useState('');

  useEffect(() => {
    if (user) {
      fetchEmprunts();
    }
  }, [user]);

  const fetchEmprunts = async () => {
    try {
      const response = await getEmpruntsActifsClient(user.id);
      setEmprunts(response.data);
    } catch (error) {
      console.error("Erreur lors de la récupération des emprunts", error);
    }
  };

  const handleRetour = async (empruntId) => {
    try {
      await retournerDocument(empruntId);
      setMessage("Document retourné avec succès !");
      fetchEmprunts();
    } catch (error) {
      setMessage("Erreur lors du retour.");
    }
  };

  return (
    <div>
      <h2>Mes Emprunts</h2>
      {message && <div className="message info-message">{message}</div>}

      <div className="table-responsive">
        <table>
          <thead>
            <tr>
              <th>Document</th>
              <th>Date d'emprunt</th>
              <th>Date de retour prévue</th>
              <th>Status</th>
              <th>Action</th>
            </tr>
          </thead>
          <tbody>
            {emprunts.map(emp => {
              const isOverdue = new Date(emp.dateRetourPrevue) < new Date();
              return (
                <tr key={emp.id}>
                  <td style={{ fontWeight: '600' }}>{emp.documentTitre}</td>
                  <td>{new Date(emp.dateEmprunt).toLocaleDateString()}</td>
                  <td style={{ color: isOverdue ? 'var(--danger-color)' : 'inherit', fontWeight: isOverdue ? 'bold' : 'normal' }}>
                    {new Date(emp.dateRetourPrevue).toLocaleDateString()}
                  </td>
                  <td>
                    {isOverdue ? (
                      <span className="badge" style={{ backgroundColor: '#fcd3d3', color: '#c53030' }}>Retard</span>
                    ) : (
                      <span className="badge" style={{ backgroundColor: '#d1fae5', color: '#065f46' }}>En cours</span>
                    )}
                  </td>
                  <td>
                    <button className="primary" onClick={() => handleRetour(emp.id)}>Retourner</button>
                  </td>
                </tr>
              );
            })}
          </tbody>
        </table>
      </div>
      {emprunts.length === 0 && (
        <div className="message info-message">
          Vous n'avez aucun emprunt actif pour le moment.
        </div>
      )}
    </div>
  );
};

export default MesEmprunts;
