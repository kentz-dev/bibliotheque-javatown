import api from './api';

export const getDocuments = () => {
  return api.get('/documents');
};

export const searchDocuments = (params) => {
  return api.get('/documents/search', { params });
};

export const createDocument = (documentData, preposeEmail) => {
  return api.post('/documents', documentData, {
    headers: { 'X-Prepose-Email': preposeEmail }
  });
};
