import { Group, Code, ScrollArea, rem } from '@mantine/core';
import {
    IconNotes,
    IconCalendarStats,
    IconGauge,
    IconFileAnalytics,
    IconAdjustments,
    IconLock,
} from '@tabler/icons-react';
import { UserButton } from '../lib/UserButton/UserButton';
import { LinksGroup } from '../lib/NavbarLinksGroup/NavbarLinksGroup';
import { Logo } from './Logo.tsx';
import classes from './Sidebar.module.css';

const mockdata = [
    { label: 'Dashboard', icon: IconGauge },
    {
        label: 'Manuscript',
        icon: IconNotes,
        initiallyOpened: true,
        links: [
            { label: 'Chapter 1', link: '/' },
            { label: 'Chapter 2', link: '/' },
            { label: 'Chapter 3', link: '/' },
            { label: 'Chapter 4', link: '/' },
        ],
    },
    {
        label: 'Plot',
        icon: IconCalendarStats,
        links: [
            { label: 'Plot 1', link: '/' },
            { label: 'Plot 2', link: '/' },
            { label: 'Plot 3', link: '/' },
        ],
    },
    {
        label: 'Characters',
        icon: IconCalendarStats,
        links: [
            { label: 'Character 1', link: '/' },
            { label: 'Character 2', link: '/' },
            { label: 'Character 3', link: '/' },
        ],
    },
    { label: 'Story Notes', icon: IconFileAnalytics },

    { label: 'Settings', icon: IconAdjustments },
    {
        label: 'Security',
        icon: IconLock,
        links: [
            { label: 'Enable 2FA', link: '/' },
            { label: 'Change password', link: '/' },
            { label: 'Recovery codes', link: '/' },
        ],
    },
];

export function Sidebar() {
    const links = mockdata.map((item) => <LinksGroup {...item} key={item.label} />);

    return (
        <nav className={classes.navbar}>
            <div className={classes.header}>
                <Group justify="space-between">
                    <Logo style={{ width: rem(120) }} />
                    <Code fw={700}>v3.1.2</Code>
                </Group>
            </div>

            <ScrollArea className={classes.links}>
                <div className={classes.linksInner}>{links}</div>
            </ScrollArea>

            <div className={classes.footer}>
                <UserButton />
            </div>
        </nav>
    );
}