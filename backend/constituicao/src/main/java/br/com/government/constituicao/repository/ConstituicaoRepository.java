package br.com.government.constituicao.repository;

import br.com.government.constituicao.model.Constituicao;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface ConstituicaoRepository extends CrudRepository<Constituicao, UUID> {
}
