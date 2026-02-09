import { type InputHTMLAttributes } from 'react';
import './Input.css';

interface InputProps extends InputHTMLAttributes<HTMLInputElement> {
    label?: string;
    error?: string;
}

const Input = ({ label, error, className = '', style, ...props }: InputProps) => {
    return (
        <div className="input-wrapper">
            {label && (
                <label className="input-label">
                    {label}
                </label>
            )}
            <input
                className={`input-field ${error ? 'has-error' : ''} ${className}`}
                style={style}
                {...props}
            />
            {error && (
                <span className="input-error-message">{error}</span>
            )}
        </div>
    );
};

export default Input;
