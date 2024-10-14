package com.adopet.service;

import com.adopet.model.Animal;
import com.adopet.repository.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AnimalService {

  private final AnimalRepository animalRepository;

  @Autowired
  public AnimalService(AnimalRepository animalRepository) {
    this.animalRepository = animalRepository;
  }

  /**
   * Retorna todos os animais.
   * 
   * @return lista de todos os animais
   */
  public List<Animal> findAll() {
    return animalRepository.findAll();
  }

  /**
   * Encontra um animal pelo ID.
   * 
   * @param id o ID do animal
   * @return o animal encontrado
   */
  public Optional<Animal> findById(Long id) {
    return animalRepository.findById(id);
  }

  /**
   * Cria um novo animal.
   * 
   * @param newAnimal o animal a ser criado
   * @return o animal criado
   */
  public Animal create(Animal newAnimal) {
    return animalRepository.save(newAnimal);
  }

  /**
   * Atualiza um animal.
   * 
   * @param animal    o animal a ser atualizado
   * @param newAnimal o animal com os novos dados
   * @return o animal atualizado
   */
  public Animal update(Animal animal, Animal newAnimal) {
    animal.setStatus(newAnimal.getStatus());

    return animalRepository.save(animal);
  }

  /**
   * Deleta um animal pelo ID.
   * 
   * @param id o ID do animal
   */
  public void delete(Long id) {
    animalRepository.deleteById(id);
  }

  /**
   * Verifica se um animal existe pelo ID.
   * 
   * @param id o ID do animal
   * @return true se o animal existe, false caso contrário
   */
  public boolean existsById(Long id) {
    return animalRepository.existsById(id);
  }
}
