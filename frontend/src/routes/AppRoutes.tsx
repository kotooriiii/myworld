import React from 'react';
import { Route, Switch } from 'react-router-dom';
import HomePage from '../pages/Home.page.tsx';
import ProjectPage from '../pages/Project.page.tsx';

const AppRoutes: React.FC = () => {
    return (
        <Switch>
            <Route path="/" exact={true} component={HomePage} />
            <Route path="/projects/:uuid" component={ProjectPage} />
        </Switch>
    );
};

export default AppRoutes;
