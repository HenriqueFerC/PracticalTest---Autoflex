import { useState, type FormEvent } from 'react';
import { ArrowLeft, Save, AlertCircle, CheckCircle } from 'lucide-react';
import { Link } from 'react-router-dom';
import api from '../../services/api';
import Card from '../../components/ui/Card';
import Input from '../../components/ui/Input';
import Button from '../../components/ui/Button';
import './MaterialCreate.css';

export default function MaterialCreate() {
    const [name, setName] = useState('');
    const [stock, setStock] = useState('');
    const [isLoading, setIsLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);
    const [successId, setSuccessId] = useState<string | null>(null);

    const handleSubmit = async (e: FormEvent) => {
        e.preventDefault();
        setError(null);
        setSuccessId(null);
        setIsLoading(true);

        try {
            const response = await api.post('/rawMaterial/register', {
                name,
                stock: parseInt(stock)
            });

            if (response.data && response.data.id) {
                setSuccessId(response.data.id);
                setName('');
                setStock('');
            } else {
                setSuccessId('Unknown (Check List)');
                setName('');
                setStock('');
            }
        } catch (err: any) {
            console.error(err);
            setError(err.response?.data?.message || 'Failed to create raw material. Check inputs.');
        } finally {
            setIsLoading(false);
        }
    };

    return (
        <div className="container material-create-container">
            <Link to="/materials" className="back-link">
                <ArrowLeft size={18} />
                Back to List
            </Link>

            <Card>
                <div className="create-header">
                    <div className="header-icon-wrapper">
                        <Save size={24} color="white" />
                    </div>
                    <h1 className="header-title">New Raw Material</h1>
                </div>

                {error && (
                    <div className="alert-message alert-error">
                        <AlertCircle size={20} />
                        {error}
                    </div>
                )}

                {successId && (
                    <div className="alert-message alert-success">
                        <div className="success-icon-wrapper">
                            <CheckCircle size={32} />
                        </div>
                        <h3 className="success-title">Success!</h3>
                        <p>Raw Material created.</p>
                        <p className="success-id-line">
                            <span className="text-muted">Generated ID:</span>
                            <span className="success-id-text">{successId}</span>
                        </p>
                        <p className="success-note">
                            Please note this ID down. It is required for linking to products.
                        </p>
                    </div>
                )}

                <form onSubmit={handleSubmit}>
                    <Input
                        label="Name"
                        placeholder="e.g., Wood, Steel, Plastic"
                        value={name}
                        onChange={(e) => setName(e.target.value)}
                        required
                        minLength={2}
                        maxLength={30}
                    />

                    <Input
                        label="Initial Stock"
                        type="number"
                        placeholder="0"
                        value={stock}
                        onChange={(e) => setStock(e.target.value)}
                        required
                        min={0}
                    />

                    <div className="form-actions">
                        <Button type="submit" isLoading={isLoading}>
                            Create Material
                        </Button>
                    </div>
                </form>
            </Card>
        </div>
    );
}
