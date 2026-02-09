import type { ReactNode, HTMLAttributes } from 'react';
import './Card.css';

interface CardProps extends HTMLAttributes<HTMLDivElement> {
    children: ReactNode;
    className?: string;
}

export default function Card({ children, className = '', ...props }: CardProps) {
    return (
        <div
            className={`card ${className}`}
            {...props}
        >
            {children}
        </div>
    );
}
