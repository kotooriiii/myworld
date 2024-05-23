import React from 'react';
import { Container, Title } from '@mantine/core';
import { useParams } from 'react-router-dom';

interface ProjectPageParams extends Record<string, string | undefined> {
    uuid: string;
}

const ProjectPage: React.FC = () => {
    const { uuid } = useParams<ProjectPageParams>();

    return (
        <Container>
            <Title className="text-2xl font-bold">Project: {uuid}</Title>
        </Container>
    );
};

export default ProjectPage;
