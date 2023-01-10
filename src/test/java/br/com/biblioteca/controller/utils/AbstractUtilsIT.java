package br.com.biblioteca.controller.utils;

import br.com.biblioteca.repository.ClienteRepository;
import br.com.biblioteca.repository.FuncionarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

public class AbstractUtilsIT {

    static final String PORT = "8080";
    static final String URL = "http://localhost:" + PORT;

    @Autowired
    protected ClienteRepository clienteRepository;
    @Autowired
    protected FuncionarioRepository funcionarioRepository;
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;
}
