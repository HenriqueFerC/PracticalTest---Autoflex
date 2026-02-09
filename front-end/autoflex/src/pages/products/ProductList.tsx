import { useEffect, useState } from 'react';
import { Plus, Package, DollarSign } from 'lucide-react';
import { Link } from 'react-router-dom';
import api from '../../services/api';
import Card from '../../components/ui/Card';
import Button from '../../components/ui/Button';
import './ProductList.css';

interface Product {
    id: number;
    name: string;
    value: number;
}

export default function ProductList() {
    const [products, setProducts] = useState<Product[]>([]);
    const [isLoading, setIsLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        fetchProducts();
    }, []);

    const fetchProducts = async () => {
        try {
            const response = await api.get('/product/listProducts');
            setProducts(response.data);
        } catch (err) {
            console.error(err);
            setError('Failed to load products.');
        } finally {
            setIsLoading(false);
        }
    };

    return (
        <div className="container">
            <div className="products-header">
                <div className="products-title-wrapper">
                    <h1>Products</h1>
                    <p className="text-muted products-subtitle">Manage your product catalog.</p>
                </div>
                <Link to="/products/create">
                    <Button>
                        <Plus size={18} className="btn-icon" />
                        New Product
                    </Button>
                </Link>
            </div>

            {isLoading ? (
                <div className="products-loading-container text-muted">Loading products...</div>
            ) : error ? (
                <div className="products-error-container">{error}</div>
            ) : products.length === 0 ? (
                <div className="products-empty-container">
                    <Package size={48} className="empty-icon" />
                    <p>No products found.</p>
                </div>
            ) : (
                <div className="products-grid">
                    {products.map((item, index) => (
                        <Card key={index} className="product-card-content">
                            <div className="product-card-header">
                                <h3 className="product-name">{item.name}</h3>
                                <span className="product-id-badge">
                                    {item.id}
                                </span>
                            </div>
                            <div className="product-price-wrapper">
                                <DollarSign size={16} className="text-muted" />
                                <span className="product-price">
                                    {item.value.toFixed(2)}
                                </span>
                            </div>
                        </Card>
                    ))}
                </div>
            )}
        </div>
    );
}
