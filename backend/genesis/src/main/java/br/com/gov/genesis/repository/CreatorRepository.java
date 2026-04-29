package br.com.gov.genesis.repository;

import br.com.gov.genesis.model.Creator;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface CreatorRepository extends CrudRepository<Creator, UUID> {
}
