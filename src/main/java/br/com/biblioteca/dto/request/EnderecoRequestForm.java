package br.com.biblioteca.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EnderecoRequestForm implements Serializable {
    private static final long serialVersionUID = 1L;

    private String cep;
    private String logradouro;
    private String complemento;
    private String numero;
    private String bairro;
    private String localidade;
    private String uf;
    private Integer incidencia;

    public EnderecoRequestForm(String cep, Integer incidencia) {
        this.cep = cep;
        this.incidencia = incidencia;
    }
}
