package com.adopet.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "authorities", uniqueConstraints = { @UniqueConstraint(columnNames = { "username", "authority" }) })
@Schema(description = "Representa uma autoridade atribuída a um usuário")
public class Authority {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(description = "Identificador único da autoridade", example = "1")
  private Long id;

  @Column(name = "authority", nullable = false)
  @Pattern(regexp = "USER|ADMIN", message = "A autoridade deve ser 'USER' ou 'ADMIN'")
  @NotNull(message = "A autoridade não pode ser nula")
  @Schema(description = "Nome da autoridade (ex: USER, ADMIN)", example = "USER")
  private String authority;

  @ManyToOne
  @JoinColumn(name = "username", nullable = false)
  @NotNull(message = "O usuário não pode ser nulo")
  @Schema(description = "Usuário ao qual a autoridade está atribuída")
  private User user;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getAuthority() {
    return authority;
  }

  public void setAuthority(String authority) {
    this.authority = authority;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}
