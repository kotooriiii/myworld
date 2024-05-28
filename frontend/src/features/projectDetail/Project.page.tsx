import React from 'react';
import {useDisclosure} from "@mantine/hooks";
import {AppShell, Badge, Burger, Group, NavLink, ScrollArea} from "@mantine/core";
import {IconActivity, IconChevronRight, IconCircleOff, IconGauge, IconHome2} from "@tabler/icons-react";
import AppRichTextEditor from "../../common/components/RichTextEditor/AppRichTextEditor.tsx";

const Project: React.FC = () => {
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
                    <NavLink
                        href="#required-for-focus"
                        label="With right section"
                        leftSection={<IconGauge size="1rem" stroke={1.5}/>}
                        rightSection={
                            <IconChevronRight size="0.8rem" stroke={1.5} className="mantine-rotate-rtl"/>
                        }
                    />
                    <NavLink
                        href="#required-for-focus"
                        label="Disabled"
                        leftSection={<IconCircleOff size="1rem" stroke={1.5}/>}
                        disabled
                    />
                    <NavLink
                        href="#required-for-focus"
                        label="With description"
                        description="Additional information"
                        leftSection={
                            <Badge size="xs" color="red" circle>
                                3
                            </Badge>
                        }
                    />
                    <NavLink
                        href="#required-for-focus"
                        label="Active subtle"
                        leftSection={<IconActivity size="1rem" stroke={1.5}/>}
                        rightSection={
                            <IconChevronRight size="0.8rem" stroke={1.5} className="mantine-rotate-rtl"/>
                        }
                        variant="subtle"
                        active
                    />
                    <NavLink
                        href="#required-for-focus"
                        label="Active light"
                        leftSection={<IconActivity size="1rem" stroke={1.5}/>}
                        rightSection={
                            <IconChevronRight size="0.8rem" stroke={1.5} className="mantine-rotate-rtl"/>
                        }
                        active
                    />
                    <NavLink
                        href="#required-for-focus"
                        label="Active filled"
                        leftSection={<IconActivity size="1rem" stroke={1.5}/>}
                        rightSection={
                            <IconChevronRight size="0.8rem" stroke={1.5} className="mantine-rotate-rtl"/>
                        }
                        variant="filled"
                        active
                    />

                    <NavLink
                        href="#required-for-focus"
                        label="Tasks"
                        leftSection={<IconGauge size="1rem" stroke={1.5}/>}
                        childrenOffset={28}
                    >
                        <NavLink href="#required-for-focus" label="First child link"/>
                        <NavLink label="Second child link" href="#required-for-focus"/>
                        <NavLink label="Nested parent link" childrenOffset={28} href="#required-for-focus">
                            <NavLink label="First child link" href="#required-for-focus"/>
                            <NavLink label="Second child link" href="#required-for-focus"/>
                            <NavLink label="Third child link" href="#required-for-focus"/>
                        </NavLink>
                    </NavLink>
                </ScrollArea>

            </AppShell.Navbar>
            <AppShell.Main>
                <AppRichTextEditor/>
            </AppShell.Main>
            <AppShell.Aside>Aside</AppShell.Aside>
        </AppShell>
    );
};

export default Project;
