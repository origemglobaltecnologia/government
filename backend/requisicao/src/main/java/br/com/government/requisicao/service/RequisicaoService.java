package br.com.government.requisicao.service;

import br.com.government.requisicao.dto.RequisicaoRequestDTO;
import br.com.government.requisicao.dto.RequisicaoResponseDTO;
import br.com.government.requisicao.model.Requisicao;
import br.com.government.requisicao.repository.RequisicaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class RequisicaoService {

    private final RequisicaoRepository repository;

    public RequisicaoResponseDTO create(RequisicaoRequestDTO dto) {
        Requisicao model = toEntity(dto);
        model.setUuid(UUID.randomUUID());
        return toDTO(repository.save(model));
    }

    public List<RequisicaoResponseDTO> findAll() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public RequisicaoResponseDTO findById(UUID uuid) {
        return repository.findById(uuid)
                .map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Requisição não encontrada"));
    }

    public RequisicaoResponseDTO update(UUID uuid, RequisicaoRequestDTO dto) {
        if (!repository.existsById(uuid)) throw new RuntimeException("Requisição não encontrada");
        Requisicao model = toEntity(dto);
        model.setUuid(uuid);
        return toDTO(repository.save(model));
    }

    public void delete(UUID uuid) {
        repository.deleteById(uuid);
    }

    private Requisicao toEntity(RequisicaoRequestDTO dto) {
        return Requisicao.builder()
                .motivo(dto.motivo())
                .descricao(dto.descricao())
                .responsavel(dto.responsavel())
                .build();
    }

    private RequisicaoResponseDTO toDTO(Requisicao model) {
        return new RequisicaoResponseDTO(
                model.getUuid(),
                model.getMotivo(),
                model.getDescricao(),
                model.getResponsavel()
        );
    }
}
