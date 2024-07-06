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
import {MicrosoftButton} from "../../common/components/oauth_buttons/MicrosoftButton.tsx";

import {AuthorLoginRequest} from "../../common/types/auth.ts";
import {LoginRegisterLayout} from "../../common/layout/LoginRegisterLayout.tsx";
import {redirectByGoogle, redirectByMicrosoft} from "../../service/oAuthService.ts";
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
    };

    const handleLoginByMicrosoft = (e: React.MouseEvent<HTMLButtonElement, MouseEvent>) =>
    {
        e.preventDefault();

        void redirectByMicrosoft(location.state);
        return <div>Redirecting to authentication provider...</div>
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
                    <MicrosoftButton radius="xl" onClick={(e) =>
                    {
                        handleLoginByMicrosoft(e)
                    }}>Microsoft</MicrosoftButton>
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
