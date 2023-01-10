package br.com.biblioteca.models;

import br.com.biblioteca.dto.request.ClienteRequestForm;
import br.com.biblioteca.dto.request.FuncionarioRequestForm;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_pessoa")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public abstract class Pessoa implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long userId;
    @Column(nullable = false)
    protected String nome;
    @Column(nullable = false, unique = true)
    @CPF(message = "CPF INVÁLIDO")
    protected String cpf;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id", referencedColumnName = "userId")
    protected Endereco endereco;
    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    protected LocalDateTime dataCriacao;
    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    protected LocalDateTime dataUltimaAtualizacao;

    public Pessoa(ClienteRequestForm cliente) {
        this.nome = cliente.getNome();
        this.cpf = cliente.getCpf();
        this.endereco = new Endereco(cliente.getEndereco());
    }

    public Pessoa(FuncionarioRequestForm funcionario) {
        this.nome = funcionario.getNome();
        this.cpf = funcionario.getCpf();
        this.endereco = new Endereco(funcionario.getEndereco());
    }
}
