import { AppShell, Skeleton } from '@mantine/core';

export function BasicAppShell() {

    return (
        <AppShell
            header={{ height: 60 }}
            navbar={{ width: 300, breakpoint: 'sm' }}
            padding="md"
        >

            <AppShell.Navbar p="md">
                Navbar
                {Array(15)
                    .fill(0)
                    .map((_, index) => (
                        <Skeleton key={index} h={28} mt="sm" animate={false} />
                    ))}
            </AppShell.Navbar>
            <AppShell.Main>Main</AppShell.Main>
        </AppShell>
    );
}
