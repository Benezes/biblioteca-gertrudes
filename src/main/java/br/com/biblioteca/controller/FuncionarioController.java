package br.com.biblioteca.controller;

import br.com.biblioteca.dto.request.EnderecoRequestForm;
import br.com.biblioteca.dto.request.FuncionarioRequestForm;
import br.com.biblioteca.dto.response.FuncionarioResponseForm;
import br.com.biblioteca.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;

    @GetMapping
    public ResponseEntity<Page<FuncionarioResponseForm>> getAllFuncionarios(
            @PageableDefault(page = 0, size = 10, sort = "userId", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(funcionarioService.findAllFuncionarioPaged(pageable));
    }

    @GetMapping(value = "/regiao")
    public ResponseEntity<List<EnderecoRequestForm>> getFuncionariosOrderByCep() {
        return ResponseEntity.ok(funcionarioService.findAllFuncionarioOrderByIncidencia());
    }

    @PostMapping
    public ResponseEntity<FuncionarioResponseForm> postFuncionario(@RequestBody @Valid FuncionarioRequestForm dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(funcionarioService.createFuncionario(dto));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<FuncionarioResponseForm> updateFuncionario(
            @PathVariable Long id,
            @RequestBody @Valid FuncionarioRequestForm dto) {
        return ResponseEntity.ok(funcionarioService.updateFuncionario(id, dto));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> updateFuncionario(@PathVariable Long id) {
        funcionarioService.deleteFuncionario(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

