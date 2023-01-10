package br.com.biblioteca.service;

import br.com.biblioteca.dto.request.EnderecoRequestForm;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class ExternalCall {

    public static final String HTTPS_VIACEP_COM_BR_WS_CEP_JSON = "https://viacep.com.br/ws/{cep}/json/";

    public EnderecoRequestForm createEndereco(final String cep, final String numero) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> params = new HashMap<String, String>();
        params.put("cep", cep);

        ResponseEntity<EnderecoRequestForm> response = restTemplate.exchange(HTTPS_VIACEP_COM_BR_WS_CEP_JSON,
                HttpMethod.GET, null, EnderecoRequestForm.class, params);

        EnderecoRequestForm enderecoDTO = response.getBody();
        enderecoDTO.setNumero(numero);
        return enderecoDTO;
    }
}
