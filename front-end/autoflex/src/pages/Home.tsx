import { LayoutDashboard, Package, Boxes, Link as LinkIcon, BarChart3, ArrowRight } from 'lucide-react';
import { Link } from 'react-router-dom';
import Card from '../components/ui/Card';
import './Home.css';

export default function Home() {
    const cards = [
        {
            title: 'Products',
            description: 'Manage your product catalog, prices, and details.',
            icon: <Package size={32} className="text-primary" />,
            link: '/products',
            color: 'var(--color-primary)'
        },
        {
            title: 'Raw Materials',
            description: 'Track inventory stock and material definitions.',
            icon: <Boxes size={32} className="text-secondary" />,
            link: '/materials',
            color: 'var(--color-secondary)'
        },
        {
            title: 'Relationships',
            description: 'Link materials to products to define composition.',
            icon: <LinkIcon size={32} className="text-warning" style={{ color: '#f59e0b' }} />,
            link: '/relations',
            color: '#f59e0b'
        },
        {
            title: 'Reports',
            description: 'Analyze material usage and product possibilities.',
            icon: <BarChart3 size={32} className="text-info" style={{ color: '#0ea5e9' }} />,
            link: '/reports',
            color: '#0ea5e9'
        }
    ];

    return (
        <div className="dashboard-container">
            <div className="dashboard-header">
                <div className="dashboard-icon-wrapper">
                    <LayoutDashboard size={32} className="text-primary" />
                </div>
                <div>
                    <h1 className="dashboard-title">Dashboard</h1>
                    <p className="text-muted dashboard-subtitle">Welcome to Autoflex Management System</p>
                </div>
            </div>

            <div className="cards-grid">
                {cards.map((card, index) => (
                    <Link key={index} to={card.link} className="dashboard-link">
                        <Card
                            className="dashboard-card"
                            style={{
                                '--card-color-main': card.color,
                                '--card-color-bg': `${card.color}15`,
                                '--card-color-border': `${card.color}30`,
                                '--card-color-shadow': `${card.color}20`
                            } as React.CSSProperties}
                        >
                            <div className="card-icon-header">
                                <div className="card-icon">
                                    {card.icon}
                                </div>
                                <ArrowRight size={20} className="text-muted" />
                            </div>

                            <h3 className="card-title">
                                {card.title}
                            </h3>
                            <p className="text-muted card-description">
                                {card.description}
                            </p>
                        </Card>
                    </Link>
                ))}
            </div>
        </div >
    );
}
