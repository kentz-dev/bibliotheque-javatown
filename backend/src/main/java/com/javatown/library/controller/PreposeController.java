package com.javatown.library.controller;

import com.javatown.library.dto.PreposeDTO;
import com.javatown.library.service.PreposeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/preposes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PreposeController {

    private final PreposeService preposeService;

    @PostMapping
    public ResponseEntity<PreposeDTO> inscrirePrepose(@RequestBody PreposeDTO dto) {
        return ResponseEntity.ok(preposeService.inscrirePrepose(dto));
    }

    @GetMapping
    public ResponseEntity<List<PreposeDTO>> getAllPreposes() {
        return ResponseEntity.ok(preposeService.recupererTousLesPreposes());
    }
}
