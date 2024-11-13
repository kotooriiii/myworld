import { Card, Image, Divider, Text } from '@mantine/core';
import { range } from '@mantine/hooks';
import React from 'react';
import barlosImage from "../../assets/images/barlos.png";


const ProjectsHome: React.FC = () => {
    return (
        <div className="bg-[url('../../assets/images/barlos.png')] bg-cover bg-center h-screen">
            <div className='bg-white w-full p-3'>This is supposed to be a header</div>
            <div className='container mx-auto max-w-5xl pt-10'>
                <div className='grid grid-cols-3 sm:grid-cols-3 md:grid-cols-4 lg:grid-cols-5 gap-1'>
                    <div className='col-start-3'>
                        <NewCard />
                    </div>
                </div>

                <Divider size='sm' my='md' className='border-slate-300'/>

                <div className='flex justify-between pl-2 pr-2'>
                    <Text fw={600}>Recent documents</Text>
                    <Text fw={600}>Owned by anyone</Text>
                    <Text fw={600}>Icons</Text>
                </div>
                <div className='grid grid-cols-3 sm:grid-cols-3 md:grid-cols-4 lg:grid-cols-5 gap-1'>
                    {range(1, 5).map((e) => <TempCard n={e} />)}
                </div>
            </div>
        </div>
    );
};


function NewCard() {
    return (
        <Card component='a' href='https://localhost/projects/12345' className='hover:outline outline-1 outline-sky-500 hover:shadow-sm'>
            <Card.Section>
                <Image className='border rounded-none border-slate-400' src={barlosImage} />
            </Card.Section>

            <Card.Section className='p-3'>
                <Text fw={600}>Blank document</Text>
            </Card.Section>
        </Card>

    );
}

function TempCard({ n }) {
    return (
        <Card component='a' href='https://localhost/projects/12345' className='hover:outline outline-1 outline-sky-500 hover:shadow-sm'>
            <Card.Section>
                <Image className='border rounded-none border-slate-400' src={barlosImage} />
            </Card.Section>

            <Card.Section className='p-3'>
                <Text fw={600}>Sample Document {n}</Text>
                <Text>Nov 7, 2024</Text>
            </Card.Section>
        </Card>
    );
};

export default ProjectsHome;
