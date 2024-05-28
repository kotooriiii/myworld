import "@mantine/core/styles.css";
import '@mantine/tiptap/styles.css';
import '@mantine/dates/styles.css';

import {MantineProvider} from "@mantine/core";
import {theme} from "./theme.ts";
import AppRouter from "./AppRouter.tsx";

export default function App()
{
    return (
            <MantineProvider
                theme={theme}
            >
                <AppRouter/>
            </MantineProvider>
    );
}
