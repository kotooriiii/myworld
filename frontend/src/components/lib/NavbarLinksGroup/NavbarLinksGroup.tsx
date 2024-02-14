import React, {useState} from 'react';
import {Group, Box, Collapse, ThemeIcon, Text, UnstyledButton, rem, Menu} from '@mantine/core';
import {IconCalendarStats, IconChevronRight, IconExternalLink, IconDots} from '@tabler/icons-react';
import classes from './NavbarLinksGroup.module.css';

interface LinksGroupProps
{
    icon: React.FC<any>;
    label: string;
    initiallyOpened?: boolean;
    links?: { label: string; link: string }[];
}

export function LinksGroup({icon: Icon, label, initiallyOpened, links}: LinksGroupProps)
{
    const hasLinks = Array.isArray(links);
    const [opened, setOpened] = useState(initiallyOpened || false);
    const items = (hasLinks ? links : []).map((link) => (
        <Text<'a'>
            component="a"
            className={classes.link}
            href={link.link}
            key={link.label}
            onClick={(event) => event.preventDefault()}
        >
            {link.label}
        </Text>
    ));

    const toggleMenu = (e: React.MouseEvent<SVGSVGElement>) => {
        e.stopPropagation();
    };

    return (
        <>

        <UnstyledButton onClick={() => setOpened((o) => !o)} className={classes.control}>

            <Group align="center" justify="space-between" gap={0} >


                    <Box style={{display: 'flex', alignItems: 'center'}}>

                        <ThemeIcon variant="light" size={30}>
                            <Icon style={{width: rem(18), height: rem(18)}}/>
                        </ThemeIcon>
                        <Box ml="md">{label}</Box>

                        {hasLinks && (
                            <IconChevronRight
                                className={classes.chevron}
                                stroke={1.5}
                                style={{
                                    width: rem(16),
                                    height: rem(16),
                                    transform: opened ? 'rotate(90deg)' : 'none',
                                }}
                            />
                        )}
                    </Box>


                {hasLinks &&

                    <Menu width={200} shadow="md">

                        <Menu.Target>
                            <IconDots onClick={toggleMenu}/>
                        </Menu.Target>

                        <Menu.Dropdown>
                            <Menu.Item component="a" href="https://mantine.dev">
                                Mantine website
                            </Menu.Item>
                            <Menu.Item
                                leftSection={<IconExternalLink style={{width: rem(14), height: rem(14)}}/>}
                                component="a"
                                href="https://mantine.dev"
                                target="_blank"
                            >
                                External link
                            </Menu.Item>
                        </Menu.Dropdown>
                    </Menu>

                }
            </Group>
        </UnstyledButton>

                    {hasLinks ? <Collapse in={opened}>{items}</Collapse> : null}
        </>
    );
}

const mockdata = {
    label: 'Releases',
    icon: IconCalendarStats,
    links: [
        {label: 'Upcoming releases', link: '/'},
        {label: 'Previous releases', link: '/'},
        {label: 'Releases schedule', link: '/'},
    ],
};

export function NavbarLinksGroup()
{
    return (
        <Box mih={220} p="md">
            <LinksGroup {...mockdata} />
        </Box>
    );
}