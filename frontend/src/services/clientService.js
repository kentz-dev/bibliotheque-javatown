import api from './api';

export const inscrireClient = (clientData) => {
  return api.post('/clients', clientData);
};

export const getTousLesClients = () => {
  return api.get('/clients');
};
