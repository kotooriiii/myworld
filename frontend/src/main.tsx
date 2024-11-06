import ReactDOM from "react-dom/client";

import App from "./app/App.tsx";
import {Provider} from 'react-redux';
import store from './app/store';

import "./styles/index.pcss"
import '@fontsource/inter/100.css'
import '@fontsource/inter/200.css'
import '@fontsource/inter/300.css'
import '@fontsource/inter/400.css'
import '@fontsource/inter/500.css'
import '@fontsource/inter/600.css'
import '@fontsource/inter/700.css'


ReactDOM.createRoot(document.getElementById("root") as HTMLElement).render(
    <Provider store={store}>
        <App/>
    </Provider>);
