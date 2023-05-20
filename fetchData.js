import axios from 'axios';

const API_BASE_URL="http://localhost:8080"
export const fetchUserData = async (userId) => {
  try {
    const response = await axios.get(`${API_BASE_URL}/users/${userId}`);
    return response.data;
  } catch (error) {
    throw new Error('Failed to fetch user data');
  }
};
