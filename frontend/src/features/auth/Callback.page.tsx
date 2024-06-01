import React, {useEffect, useState} from 'react';
import {useNavigate} from "react-router-dom";
import {AppDispatch, RootState} from "../../app/store.ts";
import {useDispatch, useSelector} from "react-redux";
import {authenticate} from "./authSlice.ts";
import {navigateWithRedirect} from "../../service/RedirectUtils.tsx";
import {UserData} from "../../common/types/auth.ts";

const AuthCallback: React.FC = () =>
{
    const dispatch = useDispatch<AppDispatch>();
    const navigate = useNavigate();
    const [authError, setAuthError] = useState(false);
    const user: UserData = useSelector<RootState>(state => state.auth.user) as UserData;
    const [originalRequestURL, setOriginalRequestURL] = useState('');
    const [authCompleted, setAuthCompleted] = useState(false);


    useEffect(() => {
        if (authCompleted) {
            if (user.isNewUser) {
                navigate("/projects");
            } else if (originalRequestURL) {
                navigateWithRedirect({from:{ pathname: originalRequestURL}}, navigate);
            }
        }
    }, [authCompleted]);

    //Set the flag that the authentication was complete.
    useEffect(() => {
        const attemptToAuthenticate = async () => {
            const params = new URLSearchParams(window.location.search);
            const code = params.get('code');
            const state = params.get("state");
            setOriginalRequestURL(state || '');

            if (code) {
                const callbackResult = await dispatch(authenticate(code));

                if (authenticate.fulfilled.match(callbackResult)) {
                    setAuthCompleted(true);
                } else if (authenticate.rejected.match(callbackResult)) {
                    setAuthError(true); // Set error state to true if authentication failed
                }
            }
        };

       void attemptToAuthenticate();
    }, [dispatch]);




    if (authError)
    {
        return <div>Failed to authenticate token...</div>;
    }

    return <div>Authenticating token...</div>

};

export default AuthCallback;