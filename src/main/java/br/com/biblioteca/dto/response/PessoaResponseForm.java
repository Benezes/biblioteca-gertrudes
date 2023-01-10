package br.com.biblioteca.dto.response;

import br.com.biblioteca.models.Cliente;
import br.com.biblioteca.models.Funcionario;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class PessoaResponseForm implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nome;
    private String cpf;
    private EnderecoResponseForm endereco;

    public PessoaResponseForm(Cliente entity) {
        this.nome = entity.getNome();
        this.cpf = entity.getCpf();
        this.endereco = new EnderecoResponseForm(entity.getEndereco());
    }

    public PessoaResponseForm(Funcionario entity) {
        this.nome = entity.getNome();
        this.cpf = entity.getCpf();
        this.endereco = new EnderecoResponseForm(entity.getEndereco());
    }
}
