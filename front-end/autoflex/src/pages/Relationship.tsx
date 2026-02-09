import { useState, type FormEvent, useEffect } from 'react';
import { Link2, AlertCircle, CheckCircle } from 'lucide-react';
import api from '../services/api';
import Card from '../components/ui/Card';
import Input from '../components/ui/Input';
import Button from '../components/ui/Button';
import Select from '../components/ui/Select';
import './Relationship.css';

interface Product {
    id: number;
    name: string;
}

interface Material {
    id: number;
    name: string;
}

export default function Relationship() {
    const [productId, setProductId] = useState('');
    const [materialId, setMaterialId] = useState('');
    const [quantity, setQuantity] = useState('');

    const [products, setProducts] = useState<Product[]>([]);
    const [materials, setMaterials] = useState<Material[]>([]);

    const [isLoading, setIsLoading] = useState(false);
    const [isFetchingData, setIsFetchingData] = useState(true);
    const [error, setError] = useState<string | null>(null);
    const [success, setSuccess] = useState(false);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const [productsRes, materialsRes] = await Promise.all([
                    api.get('/product/listProducts'),
                    api.get('/rawMaterial/listRawMaterials')
                ]);
                setProducts(productsRes.data);
                setMaterials(materialsRes.data);
            } catch (err) {
                console.error("Error fetching data:", err);
                setError("Failed to load products or materials. Please try reloading the page.");
            } finally {
                setIsFetchingData(false);
            }
        };

        fetchData();
    }, []);

    const handleSubmit = async (e: FormEvent) => {
        e.preventDefault();
        setError(null);
        setSuccess(false);

        if (!productId || !materialId) {
            setError("Please select both a product and a raw material.");
            return;
        }

        setIsLoading(true);

        try {
            await api.post(`/rawMaterialProduct/product/${productId}/rawMaterial/${materialId}`, {
                quantity: parseInt(quantity)
            });
            setSuccess(true);
            setQuantity('');
            setProductId('');
            setMaterialId('');
        } catch (err: any) {
            console.error(err);
            setError(err.response?.data?.message || 'Failed to create relationship.');
        } finally {
            setIsLoading(false);
        }
    };

    return (
        <div className="container relationship-container">
            <Card>
                <div className="relationship-header">
                    <div className="relationship-header-icon">
                        <Link2 size={24} color="white" />
                    </div>
                    <h1 className="relationship-header-title">Link Material to Product</h1>
                </div>

                <p className="relationship-description">
                    Define how much of a raw material is needed to produce a single unit of a product.
                </p>

                {error && (
                    <div className="relationship-alert relationship-alert-error">
                        <AlertCircle size={20} />
                        <span>{error}</span>
                    </div>
                )}

                {success && (
                    <div className="relationship-alert relationship-alert-success">
                        <div className="relationship-success-icon">
                            <CheckCircle size={32} />
                        </div>
                        <h3 className="relationship-success-title">Relationship Created!</h3>
                    </div>
                )}

                <form onSubmit={handleSubmit}>
                    {isFetchingData ? (
                        <div className="relationship-loading">Loading options...</div>
                    ) : (
                        <div className="relationship-form-row">
                            <Select
                                label="Product"
                                value={productId}
                                onChange={(e) => setProductId(e.target.value)}
                                required
                            >
                                <option value="" disabled>Select a Product</option>
                                {products.map(p => (
                                    <option key={p.id} value={p.id}>{p.name} (ID: {p.id})</option>
                                ))}
                            </Select>

                            <Select
                                label="Raw Material"
                                value={materialId}
                                onChange={(e) => setMaterialId(e.target.value)}
                                required
                            >
                                <option value="" disabled>Select a Material</option>
                                {materials.map(m => (
                                    <option key={m.id} value={m.id}>{m.name} (ID: {m.id})</option>
                                ))}
                            </Select>
                        </div>
                    )}

                    <Input
                        label="Quantity Required"
                        placeholder="Amount needed per product unit"
                        value={quantity}
                        onChange={(e) => setQuantity(e.target.value)}
                        required
                        type="number"
                        min={1}
                    />

                    <div className="relationship-form-actions">
                        <Button type="submit" isLoading={isLoading} disabled={isFetchingData}>
                            Link Material
                        </Button>
                    </div>
                </form>
            </Card>
        </div>
    );
}
