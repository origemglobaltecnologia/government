package br.com.government.requisicao.controller;

import br.com.government.requisicao.dto.RequisicaoRequestDTO;
import br.com.government.requisicao.dto.RequisicaoResponseDTO;
import br.com.government.requisicao.service.RequisicaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/requisicoes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class RequisicaoController {

    private final RequisicaoService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RequisicaoResponseDTO create(@RequestBody RequisicaoRequestDTO dto) {
        return service.create(dto);
    }

    @GetMapping
    public List<RequisicaoResponseDTO> readAll() {
        return service.findAll();
    }

    @GetMapping("/{uuid}")
    public RequisicaoResponseDTO readOne(@PathVariable UUID uuid) {
        return service.findById(uuid);
    }

    @PutMapping("/{uuid}")
    public RequisicaoResponseDTO update(@PathVariable UUID uuid, @RequestBody RequisicaoRequestDTO dto) {
        return service.update(uuid, dto);
    }

    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID uuid) {
        service.delete(uuid);
    }
}
