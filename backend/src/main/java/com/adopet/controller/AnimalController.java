package com.adopet.controller;

import com.adopet.model.Animal;
import com.adopet.service.AnimalService;
import com.adopet.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/animals")
@CrossOrigin(origins = "http://localhost:3000", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
    RequestMethod.DELETE, RequestMethod.PATCH, RequestMethod.OPTIONS })
@Tag(name = "AnimalController", description = "Controlador de animais.")
public class AnimalController {

  private final AnimalService animalService;

  @Autowired
  public AnimalController(AnimalService animalService) {
    this.animalService = animalService;
  }

  /**
   * Retorna todos os animais.
   * 
   * @return lista de todos os animais
   */
  @Operation(summary = "Buscar todos os animais", description = "Retorna uma lista de todos os animais.")
  @ApiResponse(responseCode = "200", description = "Lista de animais retornada com sucesso")
  @GetMapping
  public ResponseEntity<List<Animal>> findAll() {
    List<Animal> animals = animalService.findAll();

    return ResponseEntity.ok(animals);
  }

  /**
   * Encontra um animal pelo ID.
   * 
   * @param id o ID do animal
   * @return o animal encontrado ou uma resposta de erro
   */
  @Operation(summary = "Buscar animal por ID", description = "Retorna um animal específico com base no ID fornecido.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Animal encontrado"),
      @ApiResponse(responseCode = "404", description = "Animal não encontrado")
  })
  @GetMapping("/{id}")
  public ResponseEntity<Animal> findById(
      @Parameter(description = "ID do animal que deseja buscar", required = true) @PathVariable Long id) {
    Optional<Animal> optionalAnimal = animalService.findById(id);

    if (optionalAnimal.isPresent()) {
      return ResponseEntity.ok(optionalAnimal.get());
    } else {
      throw new ResourceNotFoundException("Animal not found with id " + id);
    }
  }

  /**
   * Cria um novo animal.
   * 
   * @param newAnimal o animal a ser criado
   * @return o animal criado
   */
  @Operation(summary = "Criar um novo animal", description = "Cria um novo registro de animal.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Animal criado com sucesso"),
      @ApiResponse(responseCode = "400", description = "Erro na criação do animal")
  })
  @PostMapping
  public ResponseEntity<Animal> create(@RequestBody Animal newAnimal) {
    Animal createdAnimal = animalService.create(newAnimal);

    return ResponseEntity.status(HttpStatus.CREATED).body(createdAnimal);
  }

  /**
   * Atualiza um animal pelo ID.
   * 
   * @param id        o ID do animal a ser atualizado
   * @param newAnimal o animal com os novos dados
   * @return o animal atualizado
   */
  @Operation(summary = "Atualizar um animal", description = "Atualiza os dados de um animal existente com base no ID.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Animal atualizado com sucesso"),
      @ApiResponse(responseCode = "404", description = "Animal não encontrado"),
      @ApiResponse(responseCode = "400", description = "Erro na atualização do animal")
  })
  @PutMapping("/{id}")
  public ResponseEntity<Animal> update(
      @Parameter(description = "ID do animal que deseja atualizar", required = true) @PathVariable Long id,
      @RequestBody Animal newAnimal) {
    Optional<Animal> optionalAnimal = animalService.findById(id);

    if (!optionalAnimal.isPresent()) {
      throw new ResourceNotFoundException("Animal not found with id " + id);
    }

    Animal updatedAnimal = animalService.update(optionalAnimal.get(), newAnimal);

    return ResponseEntity.ok(updatedAnimal);
  }

  /**
   * Deleta um animal pelo ID.
   * 
   * @param id o ID do animal a ser deletado
   * @return uma resposta vazia
   */
  @Operation(summary = "Deletar um animal", description = "Remove um animal do sistema com base no ID fornecido.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Animal deletado com sucesso"),
      @ApiResponse(responseCode = "404", description = "Animal não encontrado")
  })
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(
      @Parameter(description = "ID do animal que deseja deletar", required = true) @PathVariable Long id) {
    if (!animalService.existsById(id)) {
      throw new ResourceNotFoundException("Animal not found with id " + id);
    }

    animalService.delete(id);

    return ResponseEntity.noContent().build();
  }
}
