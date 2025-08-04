import { BrowserRouter as Router, Routes, Route } from 'react-router-dom'
import './App.css'
import VehiclesPage from './pages/VehiclesPage'


function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<VehiclesPage />} />
        {/* Other routes will live here later*/}
      </Routes>
    </Router>
  );
}

export default App
