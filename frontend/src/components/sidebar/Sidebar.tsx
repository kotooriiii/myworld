import React  from 'react';
import { AppShell, Skeleton } from '@mantine/core';

const Sidebar: React.FC = () =>
{

    return (
        <AppShell.Navbar p="md">
            Navbar
            {Array(15)
                .fill(0)
                .map((_, index) => (
                    <Skeleton key={index} h={28} mt="sm" animate={false} />
                ))}
        </AppShell.Navbar>
     );
};

export default Sidebar;
