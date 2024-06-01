import React from 'react';
import {useDispatch} from 'react-redux';
import {useLocation, useNavigate} from 'react-router-dom';

import {login} from "./authSlice.ts";

import {AppDispatch} from "../../app/store.ts";


import {useForm} from '@mantine/form';
import {
    TextInput,
    PasswordInput,
    Text,
    Paper,
    Group,
    Button,
    Divider,
    Anchor,
    Stack,
    Center
} from '@mantine/core';
import {GoogleButton} from "../../common/components/oauth_buttons/GoogleButton.tsx";
import {TwitterButton} from "../../common/components/oauth_buttons/TwitterButton.tsx";
import {AuthorLoginRequest} from "../../common/types/auth.ts";
import {LoginRegisterLayout} from "../../common/layout/LoginRegisterLayout.tsx";
import {redirectByGoogle} from "../../service/oAuthService.ts";
import {navigateWithRedirect} from "../../service/RedirectUtils.tsx";

const Login: React.FC = () =>
{
    const dispatch = useDispatch<AppDispatch>();
    const location = useLocation();
    const navigate = useNavigate();


    const handleLogin = async (values: typeof form.values) =>
    {

        const data: AuthorLoginRequest = {username: values.email, password: values.password};

        const resultAction = await dispatch(login(data));

        if (login.fulfilled.match(resultAction))
        {
            navigateWithRedirect(location.state, navigate);
        }

    };

    const handleLoginByGoogle = (e: React.MouseEvent<HTMLButtonElement, MouseEvent>) =>
    {
        e.preventDefault();

        void redirectByGoogle(location.state);
        return <div>Redirecting to authentication provider...</div>


//        window.location.href = `https://accounts.google.com/o/oauth2/auth?response_type=code&client_id=}`;

        //document.location.href = `http://localhost:8080/oauth2/authorization/google?redirectTo=${encodeURIComponent(window.location.origin + from.pathname)}`; //todo

        // const resultAction = await dispatch(loginByGoogle());
        //
        // if (login.fulfilled.match(resultAction))
        // {
        //     // If login was successful, redirect to the saved URL or default to the homepage
        //     navigate(from, {replace: true});
        // }
    };

    interface LoginState
    {
        email: string,
        password: string,
    }

    const initialState: LoginState = {
        email: '',
        password: '',
    };

    const form = useForm({
        initialValues: initialState,
        validate: {
            email: (val) => (/^\S+@\S+$/.test(val) ? null : 'Invalid email'),
        },
    });


    return (
        <LoginRegisterLayout>
            <Paper radius="md" p="xl" withBorder>
                <Center>
                    <Text size="lg" fw={500}>
                        Welcome back, login with
                    </Text>
                </Center>

                <Group grow mb="md" mt="md">
                    <GoogleButton radius="xl" onClick={(e) =>
                    {
                        handleLoginByGoogle(e)
                    }}>Google</GoogleButton>
                    <TwitterButton radius="xl">Twitter</TwitterButton>
                </Group>

                <Divider label="Or continue with email" labelPosition="center" my="lg"/>

                <form onSubmit={form.onSubmit(handleLogin)}>
                    <Stack>
                        <TextInput
                            required
                            label="Email"
                            placeholder="karenwilliams264@gmail.com"
                            value={form.values.email}
                            onChange={(event) => form.setFieldValue('email', event.currentTarget.value)}
                            error={form.errors.email && 'Invalid email'}
                            radius="md"
                        />

                        <PasswordInput
                            required
                            label="Password"
                            placeholder="Password"
                            value={form.values.password}
                            onChange={(event) => form.setFieldValue('password', event.currentTarget.value)}
                            error={form.errors.password && 'Password should include at least 6 characters'}
                            radius="md"
                        />


                    </Stack>

                    <Group justify="space-between" mt="xl">
                        <Anchor component="button" type="button" c="dimmed" onClick={() => navigate('/register')}
                                size="xs">
                            Don't have an account? Register
                        </Anchor>
                        <Button type="submit" radius="xl">
                            Login
                        </Button>
                    </Group>
                </form>
            </Paper>
        </LoginRegisterLayout>
    );
};

export default Login;
