import api from './api';

const authService = {
  login: async (email, motDePasse) => {
    const response = await api.post('/auth/login', { email, motDePasse });
    return response.data;
  },
};

export default authService;
