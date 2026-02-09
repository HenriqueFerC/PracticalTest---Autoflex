import { useState, type FormEvent } from 'react';
import { ArrowLeft, Save, AlertCircle, CheckCircle } from 'lucide-react';
import { Link } from 'react-router-dom';
import api from '../../services/api';
import Card from '../../components/ui/Card';
import Input from '../../components/ui/Input';
import Button from '../../components/ui/Button';
import './ProductCreate.css';

export default function ProductCreate() {
    const [name, setName] = useState('');
    const [value, setValue] = useState('');
    const [isLoading, setIsLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);
    const [successId, setSuccessId] = useState<string | null>(null);

    const handleSubmit = async (e: FormEvent) => {
        e.preventDefault();
        setError(null);
        setSuccessId(null);
        setIsLoading(true);

        try {
            const response = await api.post('/product/register', {
                name,
                value: parseFloat(value)
            });

            if (response.data && response.data.id) {
                setSuccessId(response.data.id);
                setName('');
                setValue('');
            } else {
                setSuccessId('Unknown');
            }
        } catch (err: any) {
            console.error(err);
            setError(err.response?.data?.message || 'Failed to create product.');
        } finally {
            setIsLoading(false);
        }
    };

    return (
        <div className="container product-create-container">
            <Link to="/products" className="product-back-link">
                <ArrowLeft size={18} />
                Back to List
            </Link>

            <Card>
                <div className="product-create-header">
                    <div className="product-header-icon">
                        <Save size={24} color="white" />
                    </div>
                    <h1 className="product-header-title">New Product</h1>
                </div>

                {error && (
                    <div className="product-alert product-alert-error">
                        <div className="product-alert-message">
                            <AlertCircle size={20} />
                            <span>{error}</span>
                        </div>
                    </div>
                )}

                {successId && (
                    <div className="product-alert product-alert-success">
                        <div className="product-success-icon">
                            <CheckCircle size={32} />
                        </div>
                        <h3 className="product-success-title">Success!</h3>
                        <p>Product created.</p>
                        <p className="product-success-id">
                            <span className="text-muted">Generated ID:</span>
                            <span className="product-success-id-val">{successId}</span>
                        </p>
                        <p className="product-success-note">
                            * Please note this ID down. It is required for linking materials.
                        </p>
                    </div>
                )}

                <form onSubmit={handleSubmit}>
                    <Input
                        label="Name"
                        placeholder="e.g., Table, Chair"
                        value={name}
                        onChange={(e) => setName(e.target.value)}
                        required
                        minLength={2}
                        maxLength={30}
                    />

                    <Input
                        label="Value ($)"
                        type="number"
                        step="0.01"
                        placeholder="0.00"
                        value={value}
                        onChange={(e) => setValue(e.target.value)}
                        required
                        min={0}
                    />

                    <div className="product-form-actions">
                        <Button type="submit" isLoading={isLoading}>
                            Create Product
                        </Button>
                    </div>
                </form>
            </Card>
        </div>
    );
}
