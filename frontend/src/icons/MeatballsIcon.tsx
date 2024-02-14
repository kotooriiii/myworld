import {rem} from "@mantine/core";
import React from "react";

interface MeatballsIconProps
{
    onClick: (e: React.MouseEvent<any>) => void
}

export function MeatballsIcon({onClick}: MeatballsIconProps) {
    return (
        <svg
            xmlns="http://www.w3.org/2000/svg"
            style={{ width: rem(16), height: rem(16) }}
            viewBox="0 0 24 24"
            strokeWidth="1.5"
            stroke="currentColor"
            fill="none"
            strokeLinecap="round"
            strokeLinejoin="round"
            onClick={onClick}
        >
            <path d="M5 12m-1 0a1 1 0 1 0 2 0a1 1 0 1 0 -2 0" />
            <path d="M12 12m-1 0a1 1 0 1 0 2 0a1 1 0 1 0 -2 0" />
            <path d="M19 12m-1 0a1 1 0 1 0 2 0a1 1 0 1 0 -2 0" />
        </svg>
    );
}