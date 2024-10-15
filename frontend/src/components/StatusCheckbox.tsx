import React, { useState } from 'react';
import '../styles/StatusCheckbox.css';
import { Animal } from '../types/Animal';
import { updateAnimalFromApi } from '../services/api';

interface StatusCheckboxProps {
  animal: Animal;
}

const StatusCheckbox: React.FC<StatusCheckboxProps> = ({ animal }) => {
  const [animalStatus, setAnimalStatus] = useState(animal.status);

  const toggleStatus = (currentStatus: string): string => {
    return currentStatus === 'Disponível' ? 'Adotado' : 'Disponível';
  };

  const handleChangeStatus = async () => {
    const newStatus = toggleStatus(animalStatus);
    if (animal.id === undefined) {
      console.error('Erro: ID do animal está indefinido');
      return;
    }

    try {
      await updateAnimalFromApi(animal.id, newStatus);
      setAnimalStatus(newStatus);
    } catch (error) {
      console.error('Erro ao atualizar status', error);
    }
  };

  return (
    <div className="container">
      <label className="label">Status:</label>
      <label className="label">{animalStatus}</label>
      <input
        type="checkbox"
        className="checkbox"
        checked={animalStatus === 'Adotado'}
        onChange={handleChangeStatus}
      />
    </div>
  );
};

export default StatusCheckbox;