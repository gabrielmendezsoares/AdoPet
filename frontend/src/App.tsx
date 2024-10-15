import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import AnimalListPage from './pages/AnimalListPage';
import CreateAnimalPage from './pages/CreateAnimalPage';

const App: React.FC = () => {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<AnimalListPage />} />
        <Route path="/animal-list" element={<AnimalListPage />} />
        <Route path="/create-animal" element={<CreateAnimalPage />} />
      </Routes>
    </Router>
  );
};

export default App;
