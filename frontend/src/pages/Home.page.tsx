import {Sidebar} from "../components/sidebar/Sidebar.tsx";
import {AppShell, Button} from '@mantine/core';
import {useDisclosure} from '@mantine/hooks';

export function HomePage()
{

    const [mobileOpened, {toggle: toggleMobile}] = useDisclosure();
    const [desktopOpened, {toggle: toggleDesktop}] = useDisclosure(true);

    return (
        <AppShell
            padding="md"
            header={{height: 60}}
            navbar={{
                width: 300,
                breakpoint: 'sm',
                collapsed: {mobile: !mobileOpened, desktop: !desktopOpened},
            }}
        >
            <AppShell.Header>Header</AppShell.Header>
            <AppShell.Navbar>
                <Sidebar/>
            </AppShell.Navbar>
            <AppShell.Main>
                <Button onClick={toggleDesktop} visibleFrom="sm">
                    Toggle navbar
                </Button>
                <Button onClick={toggleMobile} hiddenFrom="sm">
                    Toggle navbar
                </Button>
            </AppShell.Main>
        </AppShell>
    );
}
