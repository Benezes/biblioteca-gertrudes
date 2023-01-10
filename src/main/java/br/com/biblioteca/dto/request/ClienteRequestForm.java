package br.com.biblioteca.dto.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class ClienteRequestForm extends PessoaRequestForm {
    private static final long serialVersionUID = 1L;

    @NotBlank
    private String cep;
    @NotBlank
    private String numeroResidencia;
    private String complemento;
    @NotBlank
    @Size(max = 15)
    private String numeroCelular;
    @NotBlank
    @Size(max = 60)
    private String email;

}
