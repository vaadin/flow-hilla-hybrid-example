import { createElement } from 'react';
import { createRoot } from 'react-dom/client';
import { RouterProvider } from 'react-router-dom';
import { AuthProvider} from "./auth";
import router from 'Frontend/routes.js';

function App() {
    return (
        <AuthProvider>
            <RouterProvider router={router} />
        </AuthProvider>
    );
}

createRoot(document.getElementById('outlet')!).render(createElement(App));
