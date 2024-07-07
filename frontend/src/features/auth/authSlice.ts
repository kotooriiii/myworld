import {createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import {AuthorLoginRequest, AuthorRegistrationRequest, ErrorMessage, UserData} from "../../common/types/auth.ts";
import authService from "../../service/authService.ts";
import oAuthService from "../../service/oAuthService.ts";

import {AxiosError} from "axios";


interface AccountInfoStateResult
{
    user: UserData
    token: string
}
interface LoginStateResult
{
    user: UserData
    token: string
}

interface RegisterStateResult
{
    user: UserData
    token: string
}

//Thunks
const accountInfo = createAsyncThunk<AccountInfoStateResult, void, { rejectValue: ErrorMessage }>('auth/accountInfo',
    async (_, thunkAPI) =>
    {
        try
        {
            const response = await authService.accountInfo();
            const token: string = localStorage.getItem('token') as string;
            return {user: response.data as UserData, token: token};
        } catch (error)
        {
            // Check if the error is due to the user not existing
            if (error instanceof AxiosError)
            {
                if (error.response?.status === 404)
                {
                    return thunkAPI.rejectWithValue('User does not exist' as ErrorMessage);
                }

                return thunkAPI.rejectWithValue(error.response?.data as ErrorMessage);
            } else
            {
                console.log("Unexpected error.", error)
                return thunkAPI.rejectWithValue('An unexpected error occurred' as ErrorMessage);

            }

        }
    });

const login = createAsyncThunk<LoginStateResult, AuthorLoginRequest, { rejectValue: ErrorMessage }>('auth/login',
    async (data, thunkAPI) =>
    {
        try
        {
            const response = await authService.login(data);
            const token: string = response.headers['authorization'] || response.data.token;
            return {user: response.data as UserData, token: token};
        } catch (error)
        {
            // Check if the error is due to the user not existing
            if (error instanceof AxiosError)
            {
                if (error.response?.status === 404)
                {
                    return thunkAPI.rejectWithValue('User does not exist' as ErrorMessage);
                }

                return thunkAPI.rejectWithValue(error.response?.data as ErrorMessage);
            } else
            {
                console.log("Unexpected error.", error)
                return thunkAPI.rejectWithValue('An unexpected error occurred' as ErrorMessage);

            }

        }
    });

const authenticate = createAsyncThunk<LoginStateResult, string, { rejectValue: ErrorMessage }>('auth/callback',
    async (authorizationCode, thunkAPI) =>
    {
        try
        {
            const response= await oAuthService.authenticate(authorizationCode);

            const token: string = response.headers['authorization'] || response.data.token;
            return {user: response.data as UserData, token: token};
        } catch (error)
        {
            // Check if the error is due to the user not existing
            if (error instanceof AxiosError)
            {
                if (error.response?.status === 404)
                {
                    return thunkAPI.rejectWithValue('User does not exist' as ErrorMessage);
                }


                return thunkAPI.rejectWithValue(error.response?.data as ErrorMessage);
            } else
            {
                console.log("Unexpected error.", error)
                return thunkAPI.rejectWithValue('An unexpected error occurred' as ErrorMessage);

            }

        }
    });

const register = createAsyncThunk<RegisterStateResult, AuthorRegistrationRequest, { rejectValue: ErrorMessage } >('auth/register', async (data, thunkAPI) =>
{
    try
    {
        const response = await authService.register(data);
        const token: string = response.headers['authorization'] || response.data.token;
        return {user: response.data as UserData, token: token} as RegisterStateResult;
    } catch (error)
    {
        // Check if the error is due to the user not existing
        if (error instanceof AxiosError)
        {
            if (error.response?.status === 404)
            {
                return thunkAPI.rejectWithValue('User does not exist'); //todo
            }

            return thunkAPI.rejectWithValue(error.response?.data);
        } else
        {
            console.log("Unexpected error.", error)
            return thunkAPI.rejectWithValue('An unexpected error occurred' as ErrorMessage);
        }
    }
});

//Slice

interface AuthState
{
    user: UserData | null;
    token: string | null;
    isLoading: boolean;
    isAuthenticated: boolean;
    error: ErrorMessage | null;
}

const initialState: AuthState = {
    user: null,
    token: localStorage.getItem('token'),
    isLoading: false,
    isAuthenticated: false,
    error: null,
};

const authSlice = createSlice({
    name: 'auth',
    initialState: initialState,
    reducers: {
        logout: (state) =>
        {
            localStorage.removeItem('token');
            state.user = null;
            state.token = null;
            state.isAuthenticated = false;
        }
    },
    extraReducers: (builder) =>
    {
        builder
            .addCase(register.pending, (state) =>
            {
                state.isLoading = true;
            })
            .addCase(register.fulfilled, (state, action) =>
            {
                state.isLoading = false;
                state.user = action.payload.user;
                state.token = action.payload.token;
                state.isAuthenticated = true;
                localStorage.setItem('token', action.payload.token);
                localStorage.removeItem("code_verifier");
                localStorage.removeItem('oauth_provider');

            })
            .addCase(register.rejected, (state, action) =>
            {
                state.isLoading = false;
                state.error = action.payload || null;
            })
            .addCase(login.pending, (state) =>
            {
                state.isLoading = true;
            })
            .addCase(login.fulfilled, (state, action) =>
            {
                state.isLoading = false;
                state.user = action.payload.user;
                state.token = action.payload.token;
                state.isAuthenticated = true;
                localStorage.setItem('token', action.payload.token);
                localStorage.removeItem("code_verifier");
                localStorage.removeItem('oauth_provider');

            })
            .addCase(login.rejected, (state, action) =>
            {
                state.isLoading = false;
                state.error = action.payload || null;
                localStorage.removeItem('token');
                localStorage.removeItem("code_verifier");
                localStorage.removeItem('oauth_provider');
                state.user = null;
                state.token = null;
                state.isAuthenticated = false;
            })
            .addCase(authenticate.pending, (state) =>
            {
                state.isLoading = true;
            })
            .addCase(authenticate.fulfilled, (state, action) =>
            {
                state.isLoading = false;
                state.user = action.payload.user;
                state.token = action.payload.token;
                state.isAuthenticated = true;
                localStorage.setItem('token', action.payload.token);
                localStorage.removeItem("code_verifier");
                localStorage.removeItem('oauth_provider');

            })
            .addCase(authenticate.rejected, (state, action) =>
            {
                state.isLoading = false;
                state.error = action.payload || null;
                localStorage.removeItem('token');
                localStorage.removeItem("code_verifier");
                localStorage.removeItem('oauth_provider');
                state.user = null;
                state.token = null;
                state.isAuthenticated = false;
            })
            .addCase(accountInfo.pending, (state) =>
            {
                state.isLoading = true;
            })
            .addCase(accountInfo.fulfilled, (state, action) =>
            {
                state.isLoading = false;
                state.user = action.payload.user;
                state.token = action.payload.token;
                state.isAuthenticated = true;
                localStorage.setItem('token', action.payload.token);
                localStorage.removeItem("code_verifier");
                localStorage.removeItem('oauth_provider');

            })
            .addCase(accountInfo.rejected, (state, action) =>
            {
                state.isLoading = false;
                state.error = action.payload || null;
                localStorage.removeItem('token');
                localStorage.removeItem("code_verifier");
                localStorage.removeItem('oauth_provider');
                state.user = null;
                state.token = null;
                state.isAuthenticated = false;
            })


        ;
    },
});

export const {logout} = authSlice.actions;
export {login, register, accountInfo, authenticate}

export default authSlice.reducer;
