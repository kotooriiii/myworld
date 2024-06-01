import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';


import Login from "../features/auth/Login.page.tsx";
import Project from "../features/projectDetail/Project.page.tsx";
import ProtectedRoutes from "./protectedRoutes.tsx";
import Register from "../features/auth/Register.page.tsx";
import ProjectsHome from "../features/projectHome/ProjectsHome.page.tsx";
import Callback from "../features/auth/Callback.page.tsx";

const AppRouter: React.FC = () => {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<Login />} />
                <Route path="/register" element={<Register />} />
                <Route path="/login" element={<Login />} />
                <Route path="/callback" element={<Callback/>} />

                <Route element={<ProtectedRoutes/>}>
                    <Route path='/projects/:projectUUID' element={<Project/>} />
                    <Route path='/projects' element={<ProjectsHome/>} />
                </Route>

            </Routes>
        </Router>
    );
};

export default AppRouter;
