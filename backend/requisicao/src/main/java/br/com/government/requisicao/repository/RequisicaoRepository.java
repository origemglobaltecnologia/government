package br.com.government.requisicao.repository;

import br.com.government.requisicao.model.Requisicao;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface RequisicaoRepository extends CrudRepository<Requisicao, UUID> {
}
