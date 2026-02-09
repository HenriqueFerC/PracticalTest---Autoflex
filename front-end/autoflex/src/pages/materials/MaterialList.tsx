import { useEffect, useState } from 'react';
import { Plus, Package } from 'lucide-react';
import { Link } from 'react-router-dom';
import api from '../../services/api';
import Card from '../../components/ui/Card';
import Button from '../../components/ui/Button';
import './MaterialList.css';

interface Material {
    id: number;
    name: string;
    stock: number;
}

export default function MaterialList() {
    const [materials, setMaterials] = useState<Material[]>([]);
    const [isLoading, setIsLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);


    const fetchMaterials = async () => {
        try {
            const response = await api.get('/rawMaterial/listRawMaterials');
            setMaterials(response.data);
        } catch (err) {
            console.error(err);
            setError('Failed to load materials.');
        } finally {
            setIsLoading(false);
        }
    };

    useEffect(() => {
        fetchMaterials();
    }, []);

    return (
        <div className="container">
            <div className="materials-header">
                <div className="materials-title-wrapper">
                    <h1>Raw Materials</h1>
                    <p className="text-muted materials-subtitle">Manage your inventory of raw materials.</p>
                </div>
                <Link to="/materials/create">
                    <Button>
                        <Plus size={18} className="btn-icon" />
                        New Material
                    </Button>
                </Link>
            </div>

            {isLoading ? (
                <div className="materials-loading-container text-muted">Loading materials...</div>
            ) : error ? (
                <div className="materials-error-container">{error}</div>
            ) : materials.length === 0 ? (
                <div className="materials-empty-container">
                    <Package size={48} className="empty-icon" />
                    <p>No raw materials found.</p>
                </div>
            ) : (
                <div className="materials-grid">
                    {materials.map((item, index) => (
                        <Card key={index} className="material-card-content">
                            <div className="material-card-header">
                                <h3 className="material-name">{item.name}</h3>
                                <span className="material-id-badge">
                                    {item.id}
                                </span>
                            </div>
                            <div className="material-stock-wrapper">
                                <span className="text-muted">Stock:</span>
                                <span className={`material-stock-value ${item.stock > 0 ? 'stock-positive' : 'stock-zero'}`}>
                                    {item.stock}
                                </span>
                            </div>
                        </Card>
                    ))}
                </div>
            )}
        </div>
    );
}
