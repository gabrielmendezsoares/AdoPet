import React, { useState } from 'react';
import { Animal } from '../types/Animal';
import { createAnimalFromApi } from '../services/api';
import '../styles/AnimalForm.css';

const initialAnimalState: Animal = {
  image: '',
  name: '',
  description: '',
  category: '',
  birthday: '',
  age: 0,
  status: 'Disponível',
};

const AnimalForm: React.FC = () => {
  const [animal, setAnimal] = useState<Animal>(initialAnimalState);

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    const { name, value } = e.target;
    setAnimal((prevAnimal) => ({
      ...prevAnimal,
      [name]: value,
    }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      await createAnimalFromApi(animal);
      setAnimal(initialAnimalState);
    } catch (error) {
      console.error('Erro ao cadastrar animal', error);
    }
  };

  return (
    <form onSubmit={handleSubmit} className="animal-form">
      <input
        type="text"
        name="name"
        value={animal.name}
        onChange={handleInputChange}
        placeholder="Nome"
        className="animal-form__input"
      />
      <input
        type="text"
        name="image"
        value={animal.image}
        onChange={handleInputChange}
        placeholder="Imagem (URL)"
        className="animal-form__input"
      />
      <textarea
        name="description"
        value={animal.description}
        onChange={handleInputChange}
        placeholder="Descrição"
        className="animal-form__textarea"
      ></textarea>
      <input
        type="text"
        name="category"
        value={animal.category}
        onChange={handleInputChange}
        placeholder="Categoria"
        className="animal-form__input"
      />
      <input
        type="date"
        name="birthday"
        value={animal.birthday}
        onChange={handleInputChange}
        className="animal-form__input"
      />
      <input
        type="number"
        name="age"
        value={animal.age}
        onChange={handleInputChange}
        placeholder="Idade"
        className="animal-form__input"
      />
      <button type="submit" className="animal-form__button">Cadastrar Animal</button>
    </form>
  );
};

export default AnimalForm;
