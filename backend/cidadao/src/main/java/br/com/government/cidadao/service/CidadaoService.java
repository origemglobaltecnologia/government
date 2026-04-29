package br.com.government.cidadao.service;

import br.com.government.cidadao.dto.CidadaoRequestDTO;
import br.com.government.cidadao.dto.CidadaoResponseDTO;
import br.com.government.cidadao.model.Cidadao;
import br.com.government.cidadao.repository.CidadaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class CidadaoService {

    private final CidadaoRepository repository;

    public CidadaoResponseDTO create(CidadaoRequestDTO dto) {
        Cidadao model = toEntity(dto);
        model.setUuid(UUID.randomUUID());
        return toDTO(repository.save(model));
    }

    public List<CidadaoResponseDTO> findAll() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public CidadaoResponseDTO findById(UUID uuid) {
        return repository.findById(uuid)
                .map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Cidadão não encontrado"));
    }

    public CidadaoResponseDTO update(UUID uuid, CidadaoRequestDTO dto) {
        if (!repository.existsById(uuid)) throw new RuntimeException("Cidadão não encontrado");
        Cidadao model = toEntity(dto);
        model.setUuid(uuid);
        return toDTO(repository.save(model));
    }

    public void delete(UUID uuid) {
        repository.deleteById(uuid);
    }

    private Cidadao toEntity(CidadaoRequestDTO dto) {
        return Cidadao.builder()
                .nome(dto.nome())
                .cpf(dto.cpf())
                .build();
    }

    private CidadaoResponseDTO toDTO(Cidadao model) {
        return new CidadaoResponseDTO(
                model.getUuid(), 
                model.getNome(), 
                model.getCpf()
        );
    }
}
