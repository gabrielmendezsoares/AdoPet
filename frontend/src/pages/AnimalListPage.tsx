import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import AnimalCard from '../components/AnimalCard';
import { getAnimalsFromApi, deleteAnimalFromApi } from '../services/api';
import { Animal } from '../types/Animal';
import '../styles/AnimalListPage.css';

const AnimalListPage: React.FC = () => {
  const [animals, setAnimals] = useState<Animal[]>([]);
  const navigate = useNavigate();

  useEffect(() => {
    fetchAnimals();
  }, []);

  const fetchAnimals = async () => {
    try {
      const response = await getAnimalsFromApi();
      setAnimals(response);
    } catch (error) {
      console.error('Erro ao buscar animais:', error);
    }
  };

  const handleDelete = (id: number) => {
    setAnimals((prevAnimals) => prevAnimals.filter((animal) => animal.id !== id));
    deleteAnimalFromApi(id);
  };

  const renderAnimalCards = () => {
    return animals.length > 0 ? (
      animals.map((animal) => (
        <AnimalCard key={animal.id} animal={animal} onDelete={handleDelete} />
      ))
    ) : (
      <p>Nenhum animal encontrado</p>
    );
  };

  const handleRegisterClick = () => {
    navigate('/create-animal');
  };

  return (
    <div className="animal-list-page">
      <header className="animal-list-page__header">
        <h1>Lista de Animais</h1>
        <button onClick={handleRegisterClick} className="animal-list-page__register-button">
          Registrar Novo Animal
        </button>
      </header>
      <div className="animal-list-page__cards">{renderAnimalCards()}</div>
    </div>
  );
};

export default AnimalListPage;