import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom'
import './App.css'
import VehiclesPage from './pages/VehiclesPage'
import LoginPage from './pages/LoginPage';


function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Navigate to="/login" />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/vehicles" element={<VehiclesPage />} />
        {/* Other routes will live here later*/}
      </Routes>
    </Router>
  );
}

export default App
