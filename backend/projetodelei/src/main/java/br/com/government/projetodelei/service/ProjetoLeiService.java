package br.com.government.projetodelei.service;

import br.com.government.projetodelei.dto.ProjetoLeiRequestDTO;
import br.com.government.projetodelei.dto.ProjetoLeiResponseDTO;
import br.com.government.projetodelei.model.ProjetoLei;
import br.com.government.projetodelei.repository.ProjetoLeiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class ProjetoLeiService {
    private final ProjetoLeiRepository repository;

    public ProjetoLeiResponseDTO create(ProjetoLeiRequestDTO dto) {
        ProjetoLei model = toEntity(dto);
        model.setUuid(UUID.randomUUID());
        return toDTO(repository.save(model));
    }

    public List<ProjetoLeiResponseDTO> findAll() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ProjetoLeiResponseDTO findById(UUID uuid) {
        return repository.findById(uuid).map(this::toDTO).orElseThrow();
    }

    public void delete(UUID uuid) {
        repository.deleteById(uuid);
    }

    private ProjetoLei toEntity(ProjetoLeiRequestDTO dto) {
        return ProjetoLei.builder()
                .identificador(dto.identificador())
                .titulo(dto.titulo())
                .ementa(dto.ementa())
                .responsavel(dto.responsavel())
                .voto(dto.voto())
                .build();
    }

    private ProjetoLeiResponseDTO toDTO(ProjetoLei model) {
        return new ProjetoLeiResponseDTO(
                model.getUuid(), model.getIdentificador(), model.getTitulo(),
                model.getEmenta(), model.getResponsavel(), model.getVoto()
        );
    }
}
