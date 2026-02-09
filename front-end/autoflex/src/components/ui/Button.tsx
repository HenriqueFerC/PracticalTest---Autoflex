import type { ButtonHTMLAttributes, ReactNode } from 'react';
import './Button.css';

interface ButtonProps extends ButtonHTMLAttributes<HTMLButtonElement> {
    children: ReactNode;
    variant?: 'primary' | 'secondary' | 'danger' | 'ghost';
    isLoading?: boolean;
}

export default function Button({
    children,
    variant = 'primary',
    isLoading = false,
    className = '',
    disabled,
    ...props
}: ButtonProps) {

    return (
        <button
            className={`btn btn-${variant} ${isLoading ? 'is-loading' : ''} ${className}`}
            disabled={disabled || isLoading}
            {...props}
        >
            {isLoading ? 'Loading...' : children}
        </button>
    );
}
