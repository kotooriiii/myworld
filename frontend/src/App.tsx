import "@mantine/core/styles.css";
import '@mantine/tiptap/styles.css';

import {MantineProvider} from "@mantine/core";
import {theme} from "./theme";
 import AppRouter from "./router/AppRouter.tsx";

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
