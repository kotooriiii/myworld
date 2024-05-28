import {ReactNode} from 'react';
import {AppShell, Container} from "@mantine/core";


interface LoginRegisterLayoutProps
{
    children: ReactNode;
}

export const LoginRegisterLayout = ({children}: LoginRegisterLayoutProps) =>
{
    return (
        <AppShell
            padding="md"
        >
            <AppShell.Main>
                <Container  size={420} my={40}>
                    {children}
                </Container>
            </AppShell.Main>
        </AppShell>
    );
};
