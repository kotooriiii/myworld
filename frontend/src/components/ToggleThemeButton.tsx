import { Button } from 'antd';
import { HiOutlineSun, HiOutlineMoon } from 'react-icons/hi';
import React from "react";

interface ToggleThemeButtonProps {
    darkTheme: boolean;
    toggleTheme: () => void;
}

const ToggleThemeButton: React.FC<ToggleThemeButtonProps> = ({ darkTheme, toggleTheme }) => {
    return (
        <div className="toggle-theme-btn">
            <Button onClick={toggleTheme} type="primary" shape="circle" icon={darkTheme ? <HiOutlineSun /> : <HiOutlineMoon />} />
        </div>
    );
};

export default ToggleThemeButton;
