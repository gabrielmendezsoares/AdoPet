package com.adopet.controller;

import com.adopet.model.Animal;
import com.adopet.service.AnimalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import java.util.Base64;
import java.util.Collections;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AnimalControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Mock
  private AnimalService animalService;

  @InjectMocks
  private AnimalController animalController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  private RequestPostProcessor basicAuth(String username, String password) {
    return request -> {
      String auth = username + ":" + password;
      byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes());
      String authHeader = "Basic " + new String(encodedAuth);

      request.addHeader("Authorization", authHeader);

      return request;
    };
  }

  @Test
  void testFindAll() throws Exception {
    when(animalService.findAll()).thenReturn(Collections.emptyList());

    mockMvc
        .perform(get("/animals")
            .with(basicAuth("User", "Password")))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$").isArray());
  }

  @Test
  void testCreate() throws Exception {
    Animal animal = new Animal();

    animal.setImage("http://exemplo.com/imagem.jpg");
    animal.setName("Rex");
    animal.setDescription("Cachorro amigável e brincalhão");
    animal.setCategory("Cachorro");
    animal.setBirthday("2022-01-01");
    animal.setAge(3);
    animal.setStatus("Disponível");
    when(animalService.create(any(Animal.class))).thenReturn(animal);

    mockMvc
        .perform(post("/animals")
            .with(basicAuth("User", "Password"))
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                "{ \"image\": \"http://exemplo.com/imagem.jpg\", " +
                    "\"name\":\"Rex\", " +
                    "\"description\":\"Cachorro amigável e brincalhão\", " +
                    "\"category\":\"Cachorro\", " +
                    "\"birthday\":\"2022-01-01\", " +
                    "\"age\":3, " +
                    "\"status\":\"Disponível\"}"))
        .andExpect(status().isCreated())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.image").value("http://exemplo.com/imagem.jpg"))
        .andExpect(jsonPath("$.name").value("Rex"))
        .andExpect(jsonPath("$.description").value("Cachorro amigável e brincalhão"))
        .andExpect(jsonPath("$.category").value("Cachorro"))
        .andExpect(jsonPath("$.birthday").value("2022-01-01"))
        .andExpect(jsonPath("$.age").value(3))
        .andExpect(jsonPath("$.status").value("Disponível"));
  }

  @Test
  void testDelete() throws Exception {
    mockMvc
        .perform(delete("/animals/1")
            .with(basicAuth("User", "Password")))
        .andExpect(status().isNoContent());
  }
}
