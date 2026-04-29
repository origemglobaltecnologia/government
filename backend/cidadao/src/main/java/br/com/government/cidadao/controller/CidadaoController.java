package br.com.government.cidadao.controller;

import br.com.government.cidadao.dto.CidadaoRequestDTO;
import br.com.government.cidadao.dto.CidadaoResponseDTO;
import br.com.government.cidadao.service.CidadaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cidadaos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CidadaoController {

    private final CidadaoService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadaoResponseDTO create(@RequestBody CidadaoRequestDTO dto) {
        return service.create(dto);
    }

    @GetMapping
    public List<CidadaoResponseDTO> readAll() {
        return service.findAll();
    }

    @GetMapping("/{uuid}")
    public CidadaoResponseDTO readOne(@PathVariable UUID uuid) {
        return service.findById(uuid);
    }

    @PutMapping("/{uuid}")
    public CidadaoResponseDTO update(@PathVariable UUID uuid, @RequestBody CidadaoRequestDTO dto) {
        return service.update(uuid, dto);
    }

    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID uuid) {
        service.delete(uuid);
    }
}
