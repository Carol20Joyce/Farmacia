package com.generation.farmacia.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.generation.farmacia.model.Remedio;
import com.generation.farmacia.repository.CategoriaRepository;
import com.generation.farmacia.repository.RemedioRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/remedios")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RemedioController {

	@Autowired
	private RemedioRepository remedioRepository;

	@Autowired
	private CategoriaRepository categoriaRepository;

	@GetMapping
	public ResponseEntity<List<Remedio>> getAll() {
		return ResponseEntity.ok(remedioRepository.findAll());

	}

	@GetMapping("/{id}")
	public ResponseEntity<ResponseEntity<Remedio>> getById(@PathVariable Long id) {
		return remedioRepository.findById(id).map(resposta -> ResponseEntity.ok(resposta))
				.map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());

	}

	@GetMapping("/nome/{nome}")
	public ResponseEntity<List<Remedio>> getByNome(@PathVariable String nome) {
		return ResponseEntity.ok(remedioRepository.findAllByNomeContainingIgnoreCase(nome));

	}

	@PostMapping
	public ResponseEntity<Remedio> postRemedio(@Valid @RequestBody Remedio remedio){
		return categoriaRepository.findById(remedio.getCategoria().getId())
				.map(resposta -> ResponseEntity.status(HttpStatus.CREATED).body(remedioRepository.save(remedio)))
				.orElse(ResponseEntity.badRequest().build());

	}

	@PutMapping
	public void put(@PathVariable Long id, @RequestBody Remedio remedio) {
		Optional<Remedio> post = remedioRepository.findById(id);
		if (post.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		ResponseEntity.status(HttpStatus.OK).body(remedioRepository.save(remedio));

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@PathVariable Long id) {
		return remedioRepository.findById(id).map(resposta -> {
			remedioRepository.deleteById(id);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		})
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}

}
