
// PKCE is used by default in oidc-client-ts, no need to configure it explicitly
interface OAuthConfig
{
    authority: string
    client_id: string,
    redirect_uri: string,
    response_type: string
    scope: string,
}

const googleConfig: OAuthConfig = {
    authority: 'https://accounts.google.com',
    client_id: '439019832619-11bsjppbcmlunh2ge3l5orop68n605ln.apps.googleusercontent.com',
    redirect_uri: 'https://localhost/callback',
    response_type: 'code',
    scope: 'openid profile email'
};

export default googleConfig;
