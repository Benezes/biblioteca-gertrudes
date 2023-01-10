package br.com.biblioteca.service;

import br.com.biblioteca.dto.request.EnderecoRequestForm;
import br.com.biblioteca.dto.request.FuncionarioRequestForm;
import br.com.biblioteca.dto.response.FuncionarioResponseForm;
import br.com.biblioteca.dto.response.IncidenciaEnderecoResponseForm;
import br.com.biblioteca.models.Funcionario;
import br.com.biblioteca.repository.FuncionarioRepository;
import br.com.biblioteca.service.exception.CpfInvalidoFoundException;
import br.com.biblioteca.service.exception.PessoaNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class FuncionarioService {

    public static final String HTTPS_VIACEP_COM_BR_WS_CEP_JSON = "https://viacep.com.br/ws/{cep}/json/";
    public static final String TARGET = "-";
    public static final String REPLACEMENT = "";
    public static final String USER_ID_IGNORE_PROPERTIES = "userId";
    public static final String PESSOA_NAO_ENCONTRADA_EXCEPTION = "Pessoa não encontrada";
    public static final String CPF_JA_EXISTENTE = "Cpf já existente";

    @Autowired
    private FuncionarioRepository funcionarioRepository;
    @Autowired
    private ExternalCall externalApiCall;

    public Page<FuncionarioResponseForm> findAllFuncionarioPaged(Pageable pageable) {
        return funcionarioRepository.findAll(pageable).map(FuncionarioResponseForm::new);
    }

    public List<EnderecoRequestForm> findAllFuncionarioOrderByIncidencia() {

        List<IncidenciaEnderecoResponseForm> regioes = funcionarioRepository.findAllFuncionariosByEnderecoCepIncidencia();
        List<EnderecoRequestForm> localidade = new ArrayList<>();

        for (IncidenciaEnderecoResponseForm incidencia : regioes) {
            RestTemplate restTemplate = new RestTemplate();
            Map<String, String> params = new HashMap<String, String>();
            params.put("cep", incidencia.getCep());

            ResponseEntity<EnderecoRequestForm> response = restTemplate.exchange(HTTPS_VIACEP_COM_BR_WS_CEP_JSON,
                    HttpMethod.GET, null, EnderecoRequestForm.class, params);

            localidade.add(new EnderecoRequestForm(Objects.requireNonNull(response.getBody()).getCep(), incidencia.getIncidencia()));
        }

        return localidade;
    }

    @Transactional
    public FuncionarioResponseForm createFuncionario(FuncionarioRequestForm funcionario) {
        if (funcionarioRepository.findByCpf(funcionario.getCpf()) != null) {
            throw new CpfInvalidoFoundException(CPF_JA_EXISTENTE);
        }
        EnderecoRequestForm endereco = externalApiCall.createEndereco(funcionario.getCep(), funcionario.getNumeroResidencia());
        funcionario.setEndereco(endereco);
        Funcionario funcionarioEntity = new Funcionario(funcionario);
        funcionarioEntity.setDataCriacao(LocalDateTime.now());
        return new FuncionarioResponseForm(funcionarioRepository.save(funcionarioEntity));
    }

    @Transactional
    public FuncionarioResponseForm updateFuncionario(Long id, FuncionarioRequestForm funcionario) {
        Funcionario funcionarioEntity = funcionarioRepository.findById(id).orElseThrow(() -> new PessoaNotFoundException(PESSOA_NAO_ENCONTRADA_EXCEPTION));
        if (!funcionario.getCep().equals(funcionarioEntity.getEndereco().getCep().replace(TARGET, REPLACEMENT))) {
            funcionario.setEndereco(externalApiCall.createEndereco(funcionario.getCep(), funcionario.getNumeroResidencia()));
        }
        BeanUtils.copyProperties(funcionario, funcionarioEntity, USER_ID_IGNORE_PROPERTIES);
        funcionarioEntity.setDataUltimaAtualizacao(LocalDateTime.now());

        if (!funcionarioEntity.getEndereco().getComplemento().equals(funcionario.getComplemento()))
            funcionarioEntity.getEndereco().setComplemento(funcionario.getComplemento());

        return new FuncionarioResponseForm(funcionarioRepository.save(funcionarioEntity));
    }

    @Transactional
    public void deleteFuncionario(Long id) {
        Funcionario funcionarioEntity = funcionarioRepository.findById(id).orElseThrow(() -> new PessoaNotFoundException(PESSOA_NAO_ENCONTRADA_EXCEPTION));
        funcionarioRepository.delete(funcionarioEntity);
    }
}
