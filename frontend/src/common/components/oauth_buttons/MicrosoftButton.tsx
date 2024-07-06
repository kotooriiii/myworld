import {Button, ButtonProps} from '@mantine/core';


function MicrosoftIcon(props: React.ComponentPropsWithoutRef<'svg'>)
{
    return (
        <svg xmlns="http://www.w3.org/2000/svg" preserveAspectRatio="xMidYMid" viewBox="0 0 23 23"
             style={{width: '0.9rem', height: '0.9rem'}} {...props}>
            <path fill="#f3f3f3" d="M0 0h23v23H0z"/>
            <path fill="#f35325" d="M1 1h10v10H1z"/>
            <path fill="#81bc06" d="M12 1h10v10H12z"/>
            <path fill="#05a6f0" d="M1 12h10v10H1z"/>
            <path fill="#ffba08" d="M12 12h10v10H12z"/>
        </svg>
    );
}

export function MicrosoftButton(props: ButtonProps & React.ComponentPropsWithoutRef<'button'>)
{
    return <Button leftSection={<MicrosoftIcon/>} variant="default" {...props} />;
}