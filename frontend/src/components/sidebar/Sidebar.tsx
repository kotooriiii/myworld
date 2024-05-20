import React, {useState} from 'react';
import {AppShell, NavLink} from '@mantine/core';
import {Home, ChevronRight, ChevronDown} from 'tabler-icons-react';
import Submenu from "../submenu/Submenu.tsx";

const Sidebar: React.FC = () =>
{
    const [submenuOpened, setSubmenuOpened] = useState<string | null>(null);

    const toggleSubmenu = (menu: string) =>
    {
        setSubmenuOpened(submenuOpened === menu ? null : menu);
    };

    return (
        <AppShell.Navbar p="xs" className="h-screen bg-gray-100 border-r border-gray-200">
            <NavLink label="With icon" leftSection={<Home size={16} stroke={'1.5'}/>}/>
            <NavLink
                label="Projects"
                leftSection={<Home/>}
                onClick={() => toggleSubmenu('projects')}
                rightSection={
                    submenuOpened === 'projects' ? <ChevronDown/> : <ChevronRight/>
                }
                className="mb-2"
            />
            {submenuOpened === 'projects' && (
                <Submenu
                    items={[{label: 'Project 1', path: '/projects/123'}, {label: 'Project 2', path: '/projects/456'}]}/>
            )}
        </AppShell.Navbar>
    );
};

export default Sidebar;
