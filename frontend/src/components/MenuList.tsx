import { useState} from 'react';
import { Menu } from 'antd';
import { HomeOutlined, BookOutlined, TeamOutlined, SnippetsOutlined, DotChartOutlined } from '@ant-design/icons';

const { SubMenu } = Menu;


const MenuList = ({ darkTheme }: { darkTheme: boolean }) => { // Update the component signature to accept props as an object
    const [openKeys, setOpenKeys] = useState<string[]>(() => {
        const storedOpenKeys : string | null = localStorage.getItem('openKeys');
        return storedOpenKeys ? JSON.parse(storedOpenKeys) : ["Plot", "Characters", "Notes"];
    });

    const handleOpenChange = (keys: string[]) => {
        setOpenKeys(keys);
        localStorage.setItem('openKeys', JSON.stringify(keys));
    };

    return (
        <div>
            <Menu className='menu-bar' theme={darkTheme ? 'dark' : 'light'} mode="inline" openKeys={openKeys} onOpenChange={handleOpenChange}>
                <Menu.Item key="home" icon={<HomeOutlined />}>
                    Home
                </Menu.Item>
                <SubMenu key="Document" icon={<BookOutlined />} title="Document">
                    <Menu.Item key="chapter1">Chapter 1</Menu.Item>
                    <Menu.Item key="chapter2">Chapter 2</Menu.Item>
                    <Menu.Item key="chapter3">Chapter 3</Menu.Item>
                </SubMenu>
                <SubMenu key="Plot" icon={<DotChartOutlined />} title="Plot">
                    <Menu.Item key="plot1">Plot 1</Menu.Item>
                    <Menu.Item key="plot2">Plot 2</Menu.Item>
                    <Menu.Item key="plot3">Plot 3</Menu.Item>
                </SubMenu>
                <SubMenu key="Characters" icon={<TeamOutlined />} title="Characters">
                    <Menu.Item key="character1">Character 1</Menu.Item>
                    <Menu.Item key="character2">Character 2</Menu.Item>
                    <Menu.Item key="character3">Character 3</Menu.Item>
                </SubMenu>
                <SubMenu key="Notes" icon={<SnippetsOutlined />} title="Notes">
                    <Menu.Item key="notes1">Note 1</Menu.Item>
                    <Menu.Item key="notes2">Note 2</Menu.Item>
                    <Menu.Item key="notes3">Note 3</Menu.Item>
                </SubMenu>
            </Menu>
        </div>
    );
}

export default MenuList;
