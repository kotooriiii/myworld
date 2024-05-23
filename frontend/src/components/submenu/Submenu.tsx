import React from 'react';
import { NavLink } from '@mantine/core';
import { Link } from 'react-router-dom';

interface SubmenuProps {
    items: { label: string; path: string }[];
}

const Submenu: React.FC<SubmenuProps> = ({ items }) => {
    return (
        <div className="pl-4">
            {items.map((item, index) => (
                <NavLink key={index} label={item.label} component={Link} to={item.path} className="mb-2" />
            ))}
        </div>
    );
};

export default Submenu;
