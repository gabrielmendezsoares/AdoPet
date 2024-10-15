import React from 'react';
import { Animal } from '../types/Animal';
import StatusCheckbox from './StatusCheckbox';
import '../styles/AnimalCard.css';

interface AnimalCardProps {
  animal: Animal;
  onDelete: (id: number) => void;
}

const AnimalCard: React.FC<AnimalCardProps> = ({ animal, onDelete }) => {
  const { id, image, name, description, category, birthday, age } = animal;

  const handleDelete = () => {
    if (id !== undefined) {
      onDelete(id);
    }
  };

  return (
    <div className="animal-card">
      <div className="animal-card__image-container">
        {image ? (
          <img src={image} alt={name} className="animal-card__image" />
        ) : (
          <span>Imagem não disponível</span>
        )}
      </div>
      <div className="animal-card__details">
        <h3 className="animal-card__name">{name}</h3>
        <p className="animal-card__description">{description}</p>
        <p className="animal-card__category">Categoria: {category}</p>
        <p className="animal-card__birthday">Data de nascimento: {birthday}</p>
        <p className="animal-card__age">Idade: {age}</p>
        <StatusCheckbox animal={animal} />
        <button className="animal-card__delete-button" onClick={handleDelete}>
          Deletar
        </button>
      </div>
    </div>
  );
};

export default AnimalCard;