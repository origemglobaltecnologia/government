package br.com.government.constituicao.service;

import br.com.government.constituicao.dto.ConstituicaoDTO;
import br.com.government.constituicao.model.Constituicao;
import br.com.government.constituicao.repository.ConstituicaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class ConstituicaoService {

    private final ConstituicaoRepository repository;

    public Constituicao create(ConstituicaoDTO dto) {
        Constituicao model = new Constituicao();
        BeanUtils.copyProperties(dto, model);
        model.setUuid(UUID.randomUUID());
        model.setDataCriacao(LocalDateTime.now());
        return repository.save(model);
    }

    public List<Constituicao> findAll() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                            .collect(Collectors.toList());
    }

    public Optional<Constituicao> findById(UUID uuid) {
        return repository.findById(uuid);
    }

    public Constituicao update(UUID uuid, ConstituicaoDTO dto) {
        return repository.findById(uuid).map(existing -> {
            BeanUtils.copyProperties(dto, existing);
            existing.setUuid(uuid);
            return repository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Constituição não encontrada"));
    }

    public void delete(UUID uuid) {
        repository.deleteById(uuid);
    }
}
