import googleConfig from "../features/auth/oAuthConfig.ts";
import baseClient from "./baseClient.ts";
import pkceChallenge from "pkce-challenge";
import OAuthConfig from "../features/auth/oAuthConfig.ts";
import {CustomLocationState} from "../common/types/navigation.ts";
import {getRedirectPathURL} from "./RedirectUtils.tsx";


const authenticate = async (authorizationCode: string) => {
    const code_verifier =  sessionStorage.getItem('code_verifier');
    const response = await baseClient.post('/authenticate', {code_verifier, authorizationCode});
    return response;
};

async function redirectToAuthorizationServerByConfig(config: typeof OAuthConfig, locationState: CustomLocationState)
{
    try
    {
        const {code_verifier, code_challenge} = await pkceChallenge();

        // Store the code verifier in session storage for later use
        sessionStorage.setItem('code_verifier', code_verifier);

        const authUrl = new URL(`${config.authority}/o/oauth2/v2/auth`);
        authUrl.searchParams.append('client_id', config.client_id);
        authUrl.searchParams.append('redirect_uri', config.redirect_uri);
        authUrl.searchParams.append('response_type', config.response_type);
        authUrl.searchParams.append('scope', config.scope);
        authUrl.searchParams.append('code_challenge', code_challenge);
        authUrl.searchParams.append('code_challenge_method', 'S256');
        authUrl.searchParams.append("state", getRedirectPathURL(locationState))

        // Redirect the user to the authorization URL
        window.location.href = authUrl.toString();
    } catch (error) {
        console.error('Error generating PKCE challenge:', error);
    }
}

async function redirectByGoogle(locationState: CustomLocationState): Promise<void>
{
    await redirectToAuthorizationServerByConfig(googleConfig, locationState);
}


export default { authenticate };
export {redirectByGoogle};