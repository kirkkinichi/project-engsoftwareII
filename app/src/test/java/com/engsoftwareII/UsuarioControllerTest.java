package com.engsoftwareII;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import com.engsoftwareII.repository.UserRepositoryImpl;
import com.engsoftwareII.request.UserRequest;
import com.engsoftwareII.user.contract.UserRepository;
import com.engsoftwareII.user.entity.Usuario;
import com.engsoftwareII.user.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class UsuarioControllerTest {

    private static final Long ID = 1L;
    private static final String NOME = "Sandro";
    private static final int IDADE = 42;
    private static final boolean MATRICULA = true;
    private static final String URL = "/api/user";

    @MockBean
    private UserService userService;

    @Autowired
    UserRepositoryImpl userRepository;

    @Autowired
    MockMvc mvc;

    @Autowired
    protected WebApplicationContext context;

    @Before
    public void prepare() throws Exception {
        this.mvc = webAppContextSetup(this.context).build();
    }

    @Test
    public void testSave() throws Exception {

        BDDMockito.given(userService.save(Mockito.any(Usuario.class))).willReturn(getMockUser());

        mvc.perform(post(URL).content(getJsonPayload(ID, NOME, IDADE, MATRICULA))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id").value(ID))
                .andExpect(jsonPath("$.data.nome").value(NOME))
                .andExpect(jsonPath("$.data.idade").value(IDADE))
                .andExpect(jsonPath("$.data.matricula").value(MATRICULA));
    }

    @Test
    public void testGetById() throws Exception{

        BDDMockito.given(userService.getById(Mockito.anyLong())).willReturn(Optional.of(getMockUser()));

        mvc.perform(MockMvcRequestBuilders.get(URL+"/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(ID))
                .andExpect(jsonPath("$.data.nome").value(NOME))
                .andExpect(jsonPath("$.data.idade").value(IDADE))
                .andExpect(jsonPath("$.data.matricula").value(MATRICULA));
    }

    @Test
    public void shouldStore() throws Exception {

        mvc.perform(post("/api/user")
                        .content(getJsonPayload(NOME, IDADE, MATRICULA))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        List<Usuario> users = userRepository.encontrarTodos();

        Assertions.assertEquals("Sandro", users.get(0).getNome());
    }

    private Usuario getMockUser() {

        Usuario usuario = new Usuario();
        usuario.setId(ID);
        usuario.setNome(NOME);
        usuario.setIdade(IDADE);
        usuario.setMatricula(MATRICULA);

        return usuario;
    }

        private String getJsonPayload(Long id, String nome, int idade, boolean matricula) throws JsonProcessingException, JsonProcessingException {
        UserRequest dto = new UserRequest();
        dto.setId(id);
        dto.setNome(nome);
        dto.setIdade(idade);
        dto.setMatricula(matricula);


        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(dto);
    }

    private String getJsonPayload(String nome, int idade, boolean matricula) throws JsonProcessingException, JsonProcessingException {
        UserRequest dto = new UserRequest();
        dto.setNome(nome);
        dto.setIdade(idade);
        dto.setMatricula(matricula);


        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(dto);
    }
}
