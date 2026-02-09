import { useState } from 'react';
import { Search, Package, Layers } from 'lucide-react';
import api from '../services/api';
import Card from '../components/ui/Card';
import Input from '../components/ui/Input';
import Button from '../components/ui/Button';
import { type FormEvent } from 'react';
import './Reports.css';

export default function Reports() {
    const [activeTab, setActiveTab] = useState<'product' | 'material'>('product');


    const [searchId, setSearchId] = useState('');
    const [results, setResults] = useState<any[]>([]);
    const [isLoading, setIsLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);
    const [searched, setSearched] = useState(false);

    const handleSearch = async (e: FormEvent) => {
        e.preventDefault();
        if (!searchId) return;

        setIsLoading(true);
        setError(null);
        setResults([]);
        setSearched(false);

        try {
            let endpoint = '';
            if (activeTab === 'product') {
                endpoint = `/rawMaterialProduct/necessaryRawMaterial/${searchId}`;
            } else {
                endpoint = `/rawMaterialProduct/productsAvailable/${searchId}`;
            }

            const response = await api.get(endpoint);


            const data = response.data;
            if (data.content && Array.isArray(data.content)) {
                setResults(data.content);
            } else if (Array.isArray(data)) {
                setResults(data);
            } else {
                setResults([]);
            }
            setSearched(true);
        } catch (err: any) {
            console.error(err);
            setError('No records found or Invalid ID.');
            setSearched(true);
        } finally {
            setIsLoading(false);
        }
    };

    return (
        <div className="container reports-container">
            <h1 className="reports-title">Reports</h1>
            <p className="text-muted mb-6">Analyze product composition and material usage.</p>


            <div className="reports-tabs">
                <button
                    onClick={() => { setActiveTab('product'); setSearchId(''); setResults([]); setSearched(false); setError(null); }}
                    className={`reports-tab-btn ${activeTab === 'product' ? 'active' : ''}`}
                >
                    Materials for Product
                </button>
                <button
                    onClick={() => { setActiveTab('material'); setSearchId(''); setResults([]); setSearched(false); setError(null); }}
                    className={`reports-tab-btn ${activeTab === 'material' ? 'active' : ''}`}
                >
                    Products for Material
                </button>
            </div>

            <Card className="reports-search-card">
                <form onSubmit={handleSearch} className="reports-search-form">
                    <Input
                        label={activeTab === 'product' ? 'Enter Product ID' : 'Enter Raw Material ID'}
                        placeholder="ID..."
                        value={searchId}
                        onChange={(e) => setSearchId(e.target.value)}
                        type="number"
                        className="reports-search-input"
                    />
                    <Button type="submit" isLoading={isLoading} className="reports-search-btn">
                        <Search size={18} className="btn-icon" />
                        Search
                    </Button>
                </form>
            </Card>

            {error && (
                <div className="reports-error">
                    {error}
                </div>
            )}

            {searched && !error && results.length === 0 && (
                <div className="reports-empty">
                    No records found for this ID.
                </div>
            )}

            {results.length > 0 && (
                <div className="reports-results-grid">
                    <h3 className="reports-results-title">Results</h3>
                    {results.map((item, index) => (
                        <Card key={index} className="report-result-card">
                            {activeTab === 'product' ? (
                                <>
                                    <div className="report-item-wrapper">
                                        <Layers size={20} className="text-muted" />
                                        <div>
                                            <div className="report-item-name">{item.nameRawMaterial}</div>
                                            <div className="report-item-sub">Required by {item.nameProduct}</div>
                                        </div>
                                    </div>
                                    <div className="report-item-value">
                                        <div className="report-value-primary">{item.quantityToOneProduct} units</div>
                                        <div className="report-value-sub">per product</div>
                                    </div>
                                </>
                            ) : (
                                <>
                                    <div className="report-item-wrapper">
                                        <Package size={20} className="text-muted" />
                                        <div>
                                            <div className="report-item-name">{item.nameProduct}</div>
                                            <div className="report-item-sub">Using {item.nameRawMaterial}</div>
                                        </div>
                                    </div>
                                    <div className="report-item-value">
                                        <div className="report-value-success">${item.totalValue ? item.totalValue.toFixed(2) : '0.00'}</div>
                                        <div className="report-value-sub">Potential Value</div>
                                    </div>
                                </>
                            )}
                        </Card>
                    ))}
                </div>
            )}
        </div>
    );
}
