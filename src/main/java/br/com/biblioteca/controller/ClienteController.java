package br.com.biblioteca.controller;

import br.com.biblioteca.dto.request.ClienteRequestForm;
import br.com.biblioteca.dto.response.ClienteResponseForm;
import br.com.biblioteca.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public ResponseEntity<Page<ClienteResponseForm>> getAllClientes(
            @PageableDefault(page = 0, size = 10, sort = "userId", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(clienteService.findAllClientesPaged(pageable));
    }

    @PostMapping
    public ResponseEntity<ClienteResponseForm> postCliente(@RequestBody @Valid ClienteRequestForm dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.createCliente(dto));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ClienteResponseForm> update(
            @PathVariable Long id,
            @RequestBody @Valid ClienteRequestForm dto) {
        return ResponseEntity.ok(clienteService.updateCliente(id, dto));
    }
}
