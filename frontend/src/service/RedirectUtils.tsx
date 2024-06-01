import {NavigateFunction} from 'react-router-dom';
import {CustomLocationState} from "../common/types/navigation.ts";

function getRedirectPathURL(locationState: CustomLocationState): string {
    return (locationState && locationState.from) ? locationState.from.pathname: '/projects';
}

function navigateWithRedirect(locationState: CustomLocationState, navigate: NavigateFunction) {
    const redirectPath = getRedirectPathURL(locationState);
     navigate(redirectPath);
}


export {getRedirectPathURL, navigateWithRedirect };
