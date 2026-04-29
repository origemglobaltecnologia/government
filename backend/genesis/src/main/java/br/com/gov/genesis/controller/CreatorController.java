package br.com.gov.genesis.controller;

import br.com.gov.genesis.model.Creator;
import br.com.gov.genesis.dto.CreatorDTO;
import br.com.gov.genesis.service.CreatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/creators")
public class CreatorController {

    @Autowired
    private CreatorService service;

    @PostMapping
    public Creator criar(@RequestBody CreatorDTO dto) {
        return service.criar(dto);
    }

    @GetMapping
    public List<Creator> listar() {
        return service.listarTudo();
    }

    @GetMapping("/{id}")
    public Creator buscar(@PathVariable UUID id) {
        return service.buscarPorId(id);
    }
}
