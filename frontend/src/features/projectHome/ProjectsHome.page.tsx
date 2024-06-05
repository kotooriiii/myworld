import React from 'react';
import {AppShell, Autocomplete, Button, Center, Divider, Group, Flex, Stack, Container, Grid, Card, Text, Badge, AspectRatio, rem, Space} from "@mantine/core";
import { IconWorld, IconUserCircle, IconSearch, IconPlus } from '@tabler/icons-react';
import { FeaturesCards } from './FeaturesCards';

const ProjectsHome: React.FC = () => {
    return (
        <AppShell
        header={{ height: 60 }}
        padding="md"
        >
            <AppShell.Header>
                <Flex align='center' justify='space-between' h='100%'>
                    <IconWorld/>
                    <Autocomplete
                    styles={{ wrapper: { width: 600 } }}
                    radius="xl"
                    placeholder="Search"
                    leftSection={<IconSearch/>}
                    data={['Project1', 'Project2', 'Project3', 'Project4']}
                    />
                    <IconUserCircle/>
                </Flex>
            </AppShell.Header>

            <AppShell.Main>
                <Container>
                    <Center>
                        <Card shadow="sm" padding="lg" radius="md" withBorder>
                            <Center>
                                <Text fw={500}>
                                    Start new project
                                </Text>
                            </Center>
                            <Space h='md'/>
                            <Card shadow="sm" radius="xs" withBorder>
                                <Center><IconPlus/></Center>
                            </Card>
                            
                            <Space h='md'/>
                        </Card>
                    </Center>
                </Container>
                <Divider my='md'/>
                <FeaturesCards/>
            </AppShell.Main>
        </AppShell>
    );
};

export default ProjectsHome;
