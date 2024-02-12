import {useState} from "react";


import {Button, Layout, theme} from "antd";
import {MenuFoldOutlined, MenuUnfoldOutlined} from "@ant-design/icons";

import Logo from "./components/Logo.tsx";
import MenuList from "./components/MenuList.tsx";
import ToggleThemeButton from "./components/ToggleThemeButton.tsx";

const {Header, Sider} = Layout;

function App()
{
    const [darkTheme, setDarkTheme] = useState<boolean>(true);
    const [collapsed, setCollapsed] = useState<boolean>(false);
    const toggleTheme: () => void = () =>
    {
        setDarkTheme(!darkTheme);
    };

    const
        {
            token: {colorBgContainer},
        } = theme.useToken();


return (
    <>
        <Layout>
            <Sider collapsed={collapsed} collapsible trigger={null} className={'sidebar'} theme={darkTheme ? 'dark' : 'light'}>
                <Logo/>
                <MenuList darkTheme={darkTheme}/>
                <ToggleThemeButton darkTheme={darkTheme} toggleTheme={toggleTheme}/>
            </Sider>
            <Layout>
                <Header style={{padding: 0, background: colorBgContainer}}>
                    <Button
                        type='text'
                        icon={[collapsed ? <MenuUnfoldOutlined/> : <MenuFoldOutlined/>]}
                        className='toggle'
                        onClick={() =>
                        {
                            setCollapsed(!collapsed)
                        }}/>
                </Header>
            </Layout>
        </Layout>
    </>
)
}

export default App
