/**
 * Created by `rsc`
 */

import FireFilled from '@ant-design/icons/FireFilled';
import React from "react";
const Logo : React.FC = () =>
{
    return (
        <div className={'logo'}>
            <div className={'logo-icon'}>
                <FireFilled/>
            </div>
        </div>
    );
};

export default Logo;