package br.com.biblioteca.repository;

import br.com.biblioteca.dto.response.IncidenciaEnderecoResponseForm;
import br.com.biblioteca.models.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
    @Query(nativeQuery = true, value = "SELECT count(te.cep) AS incidencia, te.cep FROM tb_pessoa tf\n" +
            "JOIN tb_endereco te \n" +
            "ON te.user_id = tf.user_id\n" +
            "WHERE dtype = 'Funcionario'\n" +
            "GROUP BY te.cep\n" +
            "ORDER BY 1 desc;\n" +
            "\n")
    List<IncidenciaEnderecoResponseForm> findAllFuncionariosByEnderecoCepIncidencia();

    Funcionario findByCpf(String cpf);
}
