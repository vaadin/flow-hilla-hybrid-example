import { LoginOverlay } from '@vaadin/react-components/LoginOverlay.js';
import { useState } from 'react';
import { Navigate } from 'react-router-dom';
import { useAuth } from 'Frontend/auth';

/**
 * Login views in Hilla
 */
export default function Login() {
    const { state, login } = useAuth();
    const [hasError, setError] = useState<boolean>();
    const [url, setUrl] = useState<string>();

    if (state.user && url) {
        const path = new URL(url, document.baseURI).pathname;
        return <Navigate to={path} replace />;
    }

    return (
        <LoginOverlay
            opened
            error={hasError}
            noForgotPassword
            title={'Hybrid App with Stateful Auth'}
            description={'User: user/user, Admin: admin/admin'}
            onLogin={async ({ detail: { username, password } }) => {
                const { defaultUrl, error, redirectUrl } = await login(username, password);

                if (error) {
                    setError(true);
                } else {
                    setUrl(redirectUrl ?? defaultUrl ?? '/');
                }
            }}
        />
    );
}
