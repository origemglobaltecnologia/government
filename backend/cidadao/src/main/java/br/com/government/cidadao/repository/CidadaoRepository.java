package br.com.government.cidadao.repository;

import br.com.government.cidadao.model.Cidadao;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface CidadaoRepository extends CrudRepository<Cidadao, UUID> {
}
