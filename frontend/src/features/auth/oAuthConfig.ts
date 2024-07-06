
// PKCE is used by default in oidc-client-ts, no need to configure it explicitly
interface OAuthConfigInterface
{
    authority: string
    client_id: string,
    redirect_uri: string,
    response_type: string
    scope: string,
}

const googleConfig: OAuthConfigInterface = {
    authority: 'https://accounts.google.com/o/oauth2/v2/auth',
    client_id: '439019832619-11bsjppbcmlunh2ge3l5orop68n605ln.apps.googleusercontent.com',
    redirect_uri: 'https://localhost/callback',
    response_type: 'code',
    scope: 'openid profile email https://www.googleapis.com/auth/user.gender.read https://www.googleapis.com/auth/user.birthday.read'
};

const microsoftConfig: OAuthConfigInterface = {
    authority: 'https://login.microsoftonline.com/consumers/oauth2/v2.0/authorize',
    client_id: '1b6e1baf-2366-442b-abf5-9511a426ff0c',
    redirect_uri: 'https://localhost/callback',
    response_type: 'code',
    scope: 'openid profile email User.Read User.Read.All User.ReadBasic.All'
};

export type {OAuthConfigInterface}
export {googleConfig, microsoftConfig}
