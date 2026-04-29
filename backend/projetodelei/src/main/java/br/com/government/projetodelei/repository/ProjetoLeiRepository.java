package br.com.government.projetodelei.repository;

import br.com.government.projetodelei.model.ProjetoLei;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface ProjetoLeiRepository extends CrudRepository<ProjetoLei, UUID> {
}
