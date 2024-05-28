import axios from 'axios';

const baseClient = axios.create({
    baseURL: 'http://localhost:8080/api/v1',
    timeout: 5000,
    headers: {
        'Content-Type': 'application/json',
    },
});
export default baseClient;
