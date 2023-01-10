package br.com.biblioteca.dto.response;

import br.com.biblioteca.models.Endereco;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class EnderecoResponseForm implements Serializable {
    private static final long serialVersionUID = 1L;

    private String cep;
    private String logradouro;
    private String complemento;
    private String numero;
    private String bairro;
    private String localidade;
    private String uf;

    public EnderecoResponseForm(Endereco entity) {
        this.cep = entity.getCep();
        this.logradouro = entity.getLogradouro();
        this.complemento = entity.getComplemento();
        this.numero = entity.getNumero();
        this.bairro = entity.getBairro();
        this.localidade = entity.getLocalidade();
        this.uf = entity.getUf();
    }

}
