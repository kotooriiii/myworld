import {Navigate, Outlet, useLocation} from 'react-router-dom'
import {useDispatch, useSelector} from 'react-redux';
import {AppDispatch, RootState} from "./store.ts";
import {accountInfo} from "../features/auth/authSlice.ts";
import {useEffect} from "react";


const ProtectedRoutes = () => {

    const dispatch = useDispatch<AppDispatch>();
    const token = useSelector((state: RootState) => state.auth.token);
    const isAuthenticated = useSelector((state: RootState) => state.auth.isAuthenticated);
    const location = useLocation();

    //token is in storage, but state is not updated. did the page reload?
    useEffect(() => {
        dispatch(accountInfo());
    }, [token, isAuthenticated]);


    if (token && !isAuthenticated)
    {
        return <div>Loading...</div>;  //try to authenticate to see if token is still valid
    }

    return (
        isAuthenticated ? <Outlet/> : <Navigate to='/login' state={{ from: location }}/>
    )
}

export default ProtectedRoutes;
