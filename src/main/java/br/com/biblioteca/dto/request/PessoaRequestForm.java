package br.com.biblioteca.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class PessoaRequestForm implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank
    private String nome;
    @NotBlank
    private String cpf;
    private EnderecoRequestForm endereco;
}
