import React from 'react';
import { BrowserRouter as Router } from 'react-router-dom';
import {AppShell, MantineProvider} from '@mantine/core';
import AppRoutes from './routes/AppRoutes';
import Sidebar from "./components/sidebar/Sidebar.tsx";

const App: React.FC = () => {
    return (
        <MantineProvider>'
            <AppShell>
                <Router>
                    <div className="flex">
                        <Sidebar />
                        <div className="flex-1 p-4">
                            <AppRoutes />
                        </div>
                    </div>
                </Router>
            </AppShell>
        </MantineProvider>
    );
};

export default App;
