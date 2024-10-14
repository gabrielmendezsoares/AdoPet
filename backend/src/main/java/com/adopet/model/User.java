package com.adopet.model;

import java.util.Set;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "users")
@Schema(description = "Representa um usuário do sistema")
public class User {

  @Id
  @Column(unique = true, nullable = false)
  @NotNull(message = "O nome de usuário não pode ser nulo")
  @Schema(description = "Nome de usuário único", example = "john_doe")
  private String username;

  @Column(nullable = false)
  @NotNull(message = "A senha não pode ser nula")
  @Schema(description = "Senha do usuário", example = "senhaSegura123")
  private String password;

  @Column(nullable = false)
  @Pattern(regexp = "true|false", message = "O status deve ser 'true' ou 'false'")
  @NotNull(message = "O status não pode ser nulo")
  @Schema(description = "Indica se o usuário está habilitado", example = "true")
  private boolean enabled;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @Schema(description = "Conjunto de autoridades atribuídas ao usuário")
  private Set<Authority> authorities;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public Set<Authority> getAuthorities() {
    return authorities;
  }

  public void setAuthorities(Set<Authority> authorities) {
    this.authorities = authorities;
  }
}
