import React from 'react';
import {useDispatch} from 'react-redux';
import {useNavigate} from 'react-router-dom';

import {register} from "./authSlice.ts";
import {AppDispatch} from "../../app/store.ts";


import {useForm} from '@mantine/form';
import {DateInput} from '@mantine/dates';
import {
    Anchor,
    Button,
    Center,
    Checkbox,
    Divider,
    Group,
    NativeSelect,
    Paper,
    PasswordInput,
    Space,
    Stack,
    Text,
    TextInput,
} from '@mantine/core';

import {GoogleButton} from "../../common/components/oauth_buttons/GoogleButton.tsx";
import {MicrosoftButton} from "../../common/components/oauth_buttons/MicrosoftButton.tsx";

import {AuthorRegistrationRequest, Gender} from "../../common/types/auth.ts";
import {LoginRegisterLayout} from "../../common/layout/LoginRegisterLayout.tsx";

const Register: React.FC = () =>
{
    const dispatch = useDispatch<AppDispatch>();

     const navigate = useNavigate();

    const handleRegister = async (values: typeof form.values) =>
    {

        const data: AuthorRegistrationRequest = {
            name: values.name,
            email: values.email,
            password: values.password,
            birthDate: values.dateOfBirth,
            gender: values.gender
        };
        const resultAction = await dispatch(register(data));

        if (register.fulfilled.match(resultAction))
        {
            navigate('/projects'); //do not use predefined utils, because this is the first time theyve made a project.
        }
    };

    interface RegisterState
    {
        name: string,
        dateOfBirth: Date,
        gender: Gender,
        email: string,
        password: string,
        terms: boolean,
    }

    const initialState: RegisterState = {
        name: '',
        dateOfBirth: new Date("2019-02-22"),
        gender: Gender.MALE,
        email: '',
        password: '',
        terms: false,
    };

    const form = useForm({
        initialValues: initialState,

        validate: {
            email: (val) => (/^\S+@\S+$/.test(val) ? null : 'Invalid email'),
            password: (val) => (val.length <= 6 ? 'Password should include at least 6 characters' : null),
            terms: (val) => (val === true  ? null : "Please accept the terms and conditions.")
        },
    });


    return (
        <LoginRegisterLayout>
            <Paper radius="md" p="xl" withBorder>
                <Center>
                    <Text size="lg" fw={500}>
                        Welcome to MyWorld, register with
                    </Text>
                </Center>

                <Group grow mb="md" mt="md">
                    <GoogleButton radius="xl">Google</GoogleButton>
                    <MicrosoftButton radius="xl">Twitter</MicrosoftButton>
                </Group>

                <Divider label="Or continue with email" labelPosition="center" my="lg"/>

                <form onSubmit={form.onSubmit(handleRegister)}>
                    <Stack>
                        <TextInput
                            required
                            label="Name"
                            placeholder="Karen Williams"
                            value={form.values.name}
                            onChange={(event) => form.setFieldValue('name', event.currentTarget.value)}
                            radius="md"
                        />

                        <TextInput
                            required
                            label="Email"
                            placeholder="karenwilliams264@gmail.com"
                            value={form.values.email}
                            onChange={(event) => form.setFieldValue('email', event.currentTarget.value)}
                            error={form.errors.email && 'Invalid email'}
                            radius="md"
                        />

                        <DateInput
                            required
                            label="Date of Birth"
                            placeholder="02/13/1995"
                            value={form.values.dateOfBirth}
                            onDateChange={(event) => form.setFieldValue('dateOfBirth', event)}
                            radius="md"
                        />

                        <NativeSelect
                            required
                            label="Gender"
                            data={[
                                {label: 'Male', value: Gender.MALE.toString()},
                                {label: 'Female', value: Gender.FEMALE.toString()},
                                {label: 'Unspecified', value: Gender.UNSPECIFIED.toString()}
                            ]}
                            value={form.values.gender}
                            onChange={(event) => form.setFieldValue('gender', Gender[event.currentTarget.value as keyof typeof Gender])}
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

                        <Space h="md" />

                        <Checkbox
                            label="I accept terms and conditions"
                            key={form.key('terms')}
                            checked={form.values.terms}
                            onChange={(event) => form.setFieldValue('terms', event.currentTarget.checked)}
                            error={form.errors.terms && "Please accept the terms and conditions."}
                        />
                    </Stack>

                    <Group justify="space-between" mt="xl">
                        <Anchor component="button" type="button" c="dimmed" onClick={() => navigate('/login')}
                                size="xs">
                            Already have an account? Login
                        </Anchor>
                        <Button type="submit" radius="xl">
                            Register
                        </Button>
                    </Group>
                </form>
            </Paper>
        </LoginRegisterLayout>
    );
};

export default Register;
