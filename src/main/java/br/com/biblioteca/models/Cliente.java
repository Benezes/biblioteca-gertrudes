package br.com.biblioteca.models;

import br.com.biblioteca.dto.request.ClienteRequestForm;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class Cliente extends Pessoa {
    private static final long serialVersionUID = 1L;

    @Column(unique = true, length = 15)
    private String numeroCelular;
    @Column(unique = true, length = 60)
    private String email;

    public Cliente(ClienteRequestForm cliente) {
        super(cliente);
        this.numeroCelular = cliente.getNumeroCelular();
        this.email = cliente.getEmail();
    }
}
