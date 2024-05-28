import React from 'react';
import {useDisclosure} from "@mantine/hooks";
import {AppShell, Burger, Group, NavLink, ScrollArea} from "@mantine/core";
import {IconHome2} from "@tabler/icons-react";
import AppRichTextEditor from "../../common/components/RichTextEditor/AppRichTextEditor.tsx";

const ProjectsHome: React.FC = () => {
     const [opened, {toggle}] = useDisclosure();



    return (
        <AppShell
            header={{height: 60}}
            navbar={{width: 300, breakpoint: 'sm', collapsed: {mobile: !opened}}}
            padding="md"
        >
            <AppShell.Header>
                <Group h="100%" px="md">
                    <Burger opened={opened} onClick={toggle} hiddenFrom="sm" size="sm"/>
                </Group>
            </AppShell.Header>

            <AppShell.Navbar p="md">
                <ScrollArea h={'100%'} type="never">
                    <NavLink
                        href="#required-for-focus"
                        label="With icon"
                        leftSection={<IconHome2 size="1rem" stroke={1.5}/>}
                    />
                </ScrollArea>

            </AppShell.Navbar>
            <AppShell.Main>
                <AppRichTextEditor/>
            </AppShell.Main>
            <AppShell.Aside>Aside</AppShell.Aside>
        </AppShell>
    );
};

export default ProjectsHome;
