import api from './api';

export const creerEmprunt = (clientId, documentId) => {
  return api.post(`/emprunts?clientId=${clientId}&documentId=${documentId}`);
};

export const creerEmpruntsBatch = (clientId, documentIds) => {
  return api.post(`/emprunts/batch?clientId=${clientId}`, documentIds);
};

export const retournerDocument = (empruntId) => {
  return api.post(`/emprunts/${empruntId}/retour`);
};

export const getEmpruntsClient = (clientId) => {
  return api.get(`/emprunts/client/${clientId}`);
};

export const getEmpruntsActifsClient = (clientId) => {
  return api.get(`/emprunts/client/${clientId}/actifs`);
};
