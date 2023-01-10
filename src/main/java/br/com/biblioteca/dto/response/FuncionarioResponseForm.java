package br.com.biblioteca.dto.response;

import br.com.biblioteca.models.Funcionario;
import br.com.biblioteca.models.enums.CargoEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class FuncionarioResponseForm extends PessoaResponseForm {
    private static final long serialVersionUID = 1L;

    private CargoEnum cargo;
    private Double salario;

    public FuncionarioResponseForm(Funcionario entity) {
        super(entity);
        this.cargo = entity.getCargo();
        this.salario = entity.getSalario();
    }
}
