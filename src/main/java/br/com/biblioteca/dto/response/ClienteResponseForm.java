package br.com.biblioteca.dto.response;


import br.com.biblioteca.models.Cliente;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class ClienteResponseForm extends PessoaResponseForm {
    private static final long serialVersionUID = 1L;

    private String numeroCelular;
    private String email;

    public ClienteResponseForm(Cliente entity) {
        super(entity);
        this.numeroCelular = entity.getNumeroCelular();
        this.email = entity.getEmail();
    }
}
