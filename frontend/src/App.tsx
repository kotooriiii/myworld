import React from 'react';

import {BasicAppShell} from "./BasicAppShell.tsx";
import {MantineProvider} from "@mantine/core";

const App: React.FC = () =>
{
    return (
      <MantineProvider>
          <BasicAppShell/>
      </MantineProvider>
    );
};

export default App;
