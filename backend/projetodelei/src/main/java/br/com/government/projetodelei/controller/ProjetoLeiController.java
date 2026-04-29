package br.com.government.projetodelei.controller;

import br.com.government.projetodelei.dto.ProjetoLeiRequestDTO;
import br.com.government.projetodelei.dto.ProjetoLeiResponseDTO;
import br.com.government.projetodelei.service.ProjetoLeiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/projetos-lei")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ProjetoLeiController {
    private final ProjetoLeiService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProjetoLeiResponseDTO create(@RequestBody ProjetoLeiRequestDTO dto) {
        return service.create(dto);
    }

    @GetMapping
    public List<ProjetoLeiResponseDTO> readAll() {
        return service.findAll();
    }

    @GetMapping("/{uuid}")
    public ProjetoLeiResponseDTO readOne(@PathVariable UUID uuid) {
        return service.findById(uuid);
    }

    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID uuid) {
        service.delete(uuid);
    }
}
