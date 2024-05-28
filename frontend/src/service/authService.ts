import apiClient from './apiClient.ts';
import {AuthorRegistrationRequest, AuthorLoginRequest} from "../common/types/auth.ts";

const register = async (data: AuthorRegistrationRequest) => {
    const response = await apiClient.post('/authors',
        data);
    return response;
};

const login = async (data: AuthorLoginRequest) => {
    const response = await apiClient.post('/auth/login',
        data);
    return response;
};

const accountInfo = async () => {
    const response = await apiClient.get('/authors/account');
    return response;
};



export default { register, login, accountInfo};