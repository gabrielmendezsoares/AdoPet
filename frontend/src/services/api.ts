const USERNAME = 'User';
const PASSWORD = 'Password';

const apiUrl = 'http://localhost:9090';

export const getAnimalsFromApi = async () => {
  const response = await fetch(`${apiUrl}/animals`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Basic ${btoa(`${USERNAME}:${PASSWORD}`)}`
    },
  });

  if (!response.ok) {
    throw new Error('Failed to get animals');
  }

  return response.json();
};

export const getAnimalByIdFromApi = async (animalId: number) => {
  const response = await fetch(`${apiUrl}/animals/${animalId}`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Basic ${btoa(`${USERNAME}:${PASSWORD}`)}`
    },
  });

  if (!response.ok) {
    throw new Error('Failed to get animal');
  }

  return response.json();
};

export const createAnimalFromApi = async (animalData: any) => {
  const response = await fetch(`${apiUrl}/animals`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Basic ${btoa(`${USERNAME}:${PASSWORD}`)}`
    },

    body: JSON.stringify(animalData),
  });

  if (!response.ok) {
    throw new Error('Failed to create animal');
  }

  return response.json();
};

export const updateAnimalFromApi = async (animalId: number, status: string) => {
  const response = await fetch(`${apiUrl}/animals/${animalId}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Basic ${btoa(`${USERNAME}:${PASSWORD}`)}`
    },

    body: JSON.stringify({ status }),
  });

  if (!response.ok) {
    throw new Error('Failed to update animal');
  }

  return response.json();
};

export const deleteAnimalFromApi = async (animalId: number) => {
  const response = await fetch(`${apiUrl}/animals/${animalId}`, {
    method: 'DELETE',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Basic ${btoa(`${USERNAME}:${PASSWORD}`)}`
    },
  });

  if (!response.ok) {
    throw new Error('Failed to delete animal');
  }

  return response.json();
};
