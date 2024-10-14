package com.adopet.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "animals")
@Schema(description = "Representa um animal para adoção")
public class Animal {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(description = "Identificador único do animal", example = "1")
  private Long id;

  @Column(nullable = false)
  @NotNull(message = "A imagem não pode ser nula")
  @Schema(description = "URL da imagem do animal", example = "http://exemplo.com/imagem.jpg")
  private String image;

  @Column(unique = true, nullable = false)
  @NotNull(message = "O nome não pode ser nulo")
  @Schema(description = "Nome do animal", example = "Rex")
  private String name;

  @Column(nullable = false)
  @NotNull(message = "A descrição não pode ser nula")
  @Schema(description = "Descrição do animal", example = "Cachorro amigável e brincalhão")
  private String description;

  @Column(nullable = false)
  @NotNull(message = "A categoria não pode ser nula")
  @Schema(description = "Categoria do animal (ex: cachorro, gato)", example = "Cachorro")
  private String category;

  @Column(nullable = false)
  @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "A data de nascimento deve seguir o padrão 'yyyy-MM-dd'")
  @NotNull(message = "A data de nascimento não pode ser nula")
  @Schema(description = "Data de nascimento do animal", example = "2022-01-01")
  private String birthday;

  @Column(nullable = false)
  @Min(value = 0, message = "A idade deve ser maior ou igual a 0")
  @NotNull(message = "A idade não pode ser nula")
  @Schema(description = "Idade do animal em anos", example = "3")
  private Integer age;

  @Column(nullable = false)
  @Pattern(regexp = "Disponível|Adotado", message = "O status deve ser 'Disponível' ou 'Adotado'")
  @NotNull(message = "O status não pode ser nulo")
  @Schema(description = "Status do animal", example = "Disponível")
  private String status;

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getImage() {
    return this.image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getCategory() {
    return this.category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getBirthday() {
    return this.birthday;
  }

  public void setBirthday(String birthday) {
    this.birthday = birthday;
  }

  public Integer getAge() {
    return this.age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public String getStatus() {
    return this.status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}
