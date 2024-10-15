package com.adopet.service;

import com.adopet.model.Animal;
import com.adopet.repository.AnimalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AnimalServiceTest {

  @Mock
  private AnimalRepository animalRepository;

  @InjectMocks
  private AnimalService animalService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testFindAll() {
    when(animalRepository.findAll()).thenReturn(Collections.emptyList());

    List<Animal> animals = animalService.findAll();

    assertNotNull(animals);
    assertEquals(0, animals.size());
    verify(animalRepository, times(1)).findAll();
  }

  @Test
  void testFindById() {
    Animal animal = new Animal();

    animal.setId(1L);
    animal.setImage("http://exemplo.com/imagem.jpg");
    animal.setName("Rex");
    animal.setDescription("Cachorro amigável e brincalhão");
    animal.setCategory("Cachorro");
    animal.setBirthday("2022-01-01");
    animal.setAge(3);
    animal.setStatus("Disponível");
    when(animalRepository.findById(1L)).thenReturn(Optional.of(animal));

    Optional<Animal> foundAnimal = animalService.findById(1L);

    assertTrue(foundAnimal.isPresent());
    assertEquals("http://exemplo.com/imagem.jpg", foundAnimal.get().getImage());
    assertEquals("Rex", foundAnimal.get().getName());
    assertEquals("Cachorro amigável e brincalhão", foundAnimal.get().getDescription());
    assertEquals("Cachorro", foundAnimal.get().getCategory());
    assertEquals("2022-01-01", foundAnimal.get().getBirthday());
    assertEquals(3, foundAnimal.get().getAge());
    assertEquals("Disponível", foundAnimal.get().getStatus());
    verify(animalRepository, times(1)).findById(1L);
  }

  @Test
  void testCreate() {
    Animal animal = new Animal();

    animal.setImage("http://exemplo.com/imagem.jpg");
    animal.setName("Rex");
    animal.setDescription("Cachorro amigável e brincalhão");
    animal.setCategory("Cachorro");
    animal.setBirthday("2022-01-01");
    animal.setAge(3);
    animal.setStatus("Disponível");
    when(animalRepository.save(any(Animal.class))).thenReturn(animal);

    Animal createdAnimal = animalService.create(animal);

    assertNotNull(createdAnimal);
    assertEquals("http://exemplo.com/imagem.jpg", createdAnimal.getImage());
    assertEquals("Rex", createdAnimal.getName());
    assertEquals("Cachorro amigável e brincalhão", createdAnimal.getDescription());
    assertEquals("Cachorro", createdAnimal.getCategory());
    assertEquals("2022-01-01", createdAnimal.getBirthday());
    assertEquals(3, createdAnimal.getAge());
    assertEquals("Disponível", createdAnimal.getStatus());
    verify(animalRepository, times(1)).save(animal);
  }

  @Test
  void testUpdate() {
    Animal animal = new Animal();
    Animal newAnimal = new Animal();

    animal.setId(1L);
    animal.setImage("http://exemplo.com/imagem.jpg");
    animal.setName("Rex");
    animal.setDescription("Cachorro amigável e brincalhão");
    animal.setCategory("Cachorro");
    animal.setBirthday("2022-01-01");
    animal.setAge(3);
    animal.setStatus("Disponível");
    newAnimal.setStatus("Adotado");
    when(animalRepository.save(any(Animal.class))).thenReturn(animal);

    Animal updatedAnimal = animalService.update(animal, newAnimal);

    assertNotNull(updatedAnimal);
    assertEquals(1L, updatedAnimal.getId());
    assertEquals("http://exemplo.com/imagem.jpg", updatedAnimal.getImage());
    assertEquals("Rex", updatedAnimal.getName());
    assertEquals("Cachorro amigável e brincalhão", updatedAnimal.getDescription());
    assertEquals("Cachorro", updatedAnimal.getCategory());
    assertEquals("2022-01-01", updatedAnimal.getBirthday());
    assertEquals(3, updatedAnimal.getAge());
    assertEquals("Adotado", updatedAnimal.getStatus());
    verify(animalRepository, times(1)).save(animal);
  }

  @Test
  void testDelete() {
    animalService.delete(1L);

    verify(animalRepository, times(1)).deleteById(1L);
  }
}
