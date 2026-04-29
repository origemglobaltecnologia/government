package br.com.government.constituicao.controller;

import br.com.government.constituicao.dto.ConstituicaoDTO;
import br.com.government.constituicao.model.Constituicao;
import br.com.government.constituicao.service.ConstituicaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/constituicao")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ConstituicaoController {

    private final ConstituicaoService service;

    @PostMapping
    public ResponseEntity<Constituicao> create(@RequestBody ConstituicaoDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<Constituicao>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<Constituicao> findById(@PathVariable UUID uuid) {
        return service.findById(uuid)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<Constituicao> update(@PathVariable UUID uuid, @RequestBody ConstituicaoDTO dto) {
        return ResponseEntity.ok(service.update(uuid, dto));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable UUID uuid) {
        service.delete(uuid);
        return ResponseEntity.noContent().build();
    }
}
