package br.com.biblioteca.models;

import br.com.biblioteca.dto.request.FuncionarioRequestForm;
import br.com.biblioteca.models.enums.CargoEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class Funcionario extends Pessoa {
    private static final long serialVersionUID = 1L;

    @Column()
    @Enumerated(EnumType.STRING)
    private CargoEnum cargo;
    @Column()
    private Double salario;

    public Funcionario(FuncionarioRequestForm funcionario) {
        super(funcionario);
        this.cargo = funcionario.getCargo();
        this.salario = funcionario.getSalario();
    }
}