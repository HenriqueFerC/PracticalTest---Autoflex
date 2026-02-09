import { type SelectHTMLAttributes, type ReactNode } from 'react';
import './Select.css';

interface SelectProps extends SelectHTMLAttributes<HTMLSelectElement> {
    label?: string;
    error?: string;
    children: ReactNode;
}

const Select = ({ label, error, className = '', style, children, ...props }: SelectProps) => {
    return (
        <div className="select-wrapper">
            {label && (
                <label className="select-label">
                    {label}
                </label>
            )}
            <div className="select-container">
                <select
                    className={`select-field ${error ? 'has-error' : ''} ${className}`}
                    style={style}
                    {...props}
                >
                    {children}
                </select>
                <div className="select-icon">
                    ▼
                </div>
            </div>
            {error && (
                <span className="select-error-message">{error}</span>
            )}
        </div>
    );
};

export default Select;
