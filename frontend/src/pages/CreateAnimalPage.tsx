import React from 'react';
import { useNavigate } from 'react-router-dom';
import AnimalForm from '../components/AnimalForm';
import '../styles/CreateAnimalPage.css';

const CreateAnimalPage: React.FC = () => {
  const navigate = useNavigate();

  const handleBackClick = () => {
    navigate('/animal-list');
  };

  return (
    <div className="create-animal-page">
      <header className="create-animal-page__header">
        <h1>Cadastrar Animal</h1>
        <button onClick={handleBackClick} className="create-animal-page__back-button">
          Voltar para a Lista de Animais
        </button>
      </header>
      <AnimalForm />
    </div>
  );
};

export default CreateAnimalPage;