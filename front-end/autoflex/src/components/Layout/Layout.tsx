import { useState } from 'react';
import { NavLink, Outlet } from 'react-router-dom';
import {
    LayoutDashboard,
    Package,
    Boxes,
    Link as LinkIcon,
    BarChart3,
    Menu,
    X,
    Factory
} from 'lucide-react';
import './Layout.css';

export default function Layout() {
    const [isMobileOpen, setIsMobileOpen] = useState(false);

    const toggleSidebar = () => setIsMobileOpen(!isMobileOpen);
    const closeSidebar = () => setIsMobileOpen(false);

    return (
        <div className="layout">

            {isMobileOpen && (
                <div className="overlay" onClick={closeSidebar} />
            )}


            <button className="mobile-toggle" onClick={toggleSidebar}>
                {isMobileOpen ? <X size={24} /> : <Menu size={24} />}
            </button>


            <aside className={`sidebar ${isMobileOpen ? 'open' : ''}`}>
                <div className="logo-container">
                    <Factory size={32} />
                    <span>Autoflex</span>
                </div>

                <nav className="nav-links">
                    <NavLink
                        to="/"
                        className={({ isActive }) => `nav-link ${isActive ? 'active' : ''}`}
                        onClick={closeSidebar}
                        end
                    >
                        <LayoutDashboard size={20} />
                        <span>Dashboard</span>
                    </NavLink>

                    <NavLink
                        to="/products"
                        className={({ isActive }) => `nav-link ${isActive ? 'active' : ''}`}
                        onClick={closeSidebar}
                    >
                        <Package size={20} />
                        <span>Products</span>
                    </NavLink>

                    <NavLink
                        to="/materials"
                        className={({ isActive }) => `nav-link ${isActive ? 'active' : ''}`}
                        onClick={closeSidebar}
                    >
                        <Boxes size={20} />
                        <span>Raw Materials</span>
                    </NavLink>

                    <NavLink
                        to="/relations"
                        className={({ isActive }) => `nav-link ${isActive ? 'active' : ''}`}
                        onClick={closeSidebar}
                    >
                        <LinkIcon size={20} />
                        <span>Relationships</span>
                    </NavLink>

                    <NavLink
                        to="/reports"
                        className={({ isActive }) => `nav-link ${isActive ? 'active' : ''}`}
                        onClick={closeSidebar}
                    >
                        <BarChart3 size={20} />
                        <span>Reports</span>
                    </NavLink>
                </nav>
            </aside>


            <main className="main-content">
                <Outlet />
            </main>
        </div>
    );
}
