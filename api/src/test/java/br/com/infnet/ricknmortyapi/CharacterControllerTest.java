package br.com.infnet.ricknmortyapi;

import br.com.infnet.ricknmortyapi.model.Personagem;
import br.com.infnet.ricknmortyapi.repository.PersonagemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CharacterController.class)
class CharacterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PersonagemRepository repository;

    @Test
    void searchByNameReturnsResults() throws Exception {
        Personagem rick = Personagem.builder()
                .id(1L)
                .name("Rick Sanchez")
                .status("Alive")
                .species("Human")
                .build();

        when(repository.findByNameContainingIgnoreCase("Rick"))
                .thenReturn(List.of(rick));

        mockMvc.perform(get("/api/characters/search").param("name", "Rick"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Rick Sanchez"));
    }

    @Test
    void searchByNameReturnsEmptyList() throws Exception {
        when(repository.findByNameContainingIgnoreCase("xyz"))
                .thenReturn(List.of());

        mockMvc.perform(get("/api/characters/search").param("name", "xyz"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }
}
