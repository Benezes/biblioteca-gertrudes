package br.com.biblioteca.dto.request;

import br.com.biblioteca.models.enums.CargoEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class FuncionarioRequestForm extends PessoaRequestForm {
    private static final long serialVersionUID = 1L;

    @NotBlank
    private String cep;
    @NotBlank
    private String numeroResidencia;
    private String complemento;
    private CargoEnum cargo;
    private Double salario;

}
