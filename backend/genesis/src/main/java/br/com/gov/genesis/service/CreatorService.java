package br.com.gov.genesis.service;

import br.com.gov.genesis.model.Creator;
import br.com.gov.genesis.dto.CreatorDTO;
import br.com.gov.genesis.repository.CreatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CreatorService {

    @Autowired
    private CreatorRepository repository;

    public Creator criar(CreatorDTO dto) {
        Creator creator = new Creator(dto.getNome());
        return repository.save(creator);
    }

    public List<Creator> listarTudo() {
        List<Creator> list = new ArrayList<>();
        repository.findAll().forEach(list::add);
        return list;
    }

    public Creator buscarPorId(UUID id) {
        return repository.findById(id).orElse(null);
    }

    public Creator save(Creator creator) {
        return repository.save(creator);
    }
}
