import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Layout from './components/Layout/Layout';
import Home from './pages/Home';
import ProductList from './pages/products/ProductList';
import ProductCreate from './pages/products/ProductCreate';
import MaterialList from './pages/materials/MaterialList';
import MaterialCreate from './pages/materials/MaterialCreate';
import Relationship from './pages/Relationship';
import Reports from './pages/Reports';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Layout />}>
          <Route index element={<Home />} />
          <Route path="products" element={<ProductList />} />
          <Route path="products/create" element={<ProductCreate />} />
          <Route path="materials" element={<MaterialList />} />
          <Route path="materials/create" element={<MaterialCreate />} />
          <Route path="relations" element={<Relationship />} />
          <Route path="reports" element={<Reports />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
