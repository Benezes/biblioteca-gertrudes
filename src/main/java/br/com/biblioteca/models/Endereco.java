package br.com.biblioteca.models;

import br.com.biblioteca.dto.request.EnderecoRequestForm;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tb_endereco")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class Endereco implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Column(nullable = false)
    private String cep;
    @Column(nullable = false)
    private String logradouro;
    private String complemento;
    @Column(nullable = false)
    private String numero;
    @Column(nullable = false)
    private String bairro;
    @Column(nullable = false)
    private String localidade;
    @Column(nullable = false)
    private String uf;
    @OneToOne(mappedBy = "endereco")
    private Pessoa pessoa;

    public Endereco(EnderecoRequestForm endereco) {
        this.cep = endereco.getCep();
        this.logradouro = endereco.getLogradouro();
        this.complemento = endereco.getComplemento();
        this.numero = endereco.getNumero();
        this.bairro = endereco.getBairro();
        this.localidade = endereco.getLocalidade();
        this.uf = endereco.getUf();
    }

}

