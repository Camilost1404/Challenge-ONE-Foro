package com.alura.foro_inicial.controller;

import com.alura.foro_inicial.modelo.topico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.alura.foro_inicial.infra.errores.ValidacionDeIntegridad;
import com.alura.foro_inicial.repository.TopicoRepository;
import jakarta.transaction.Transactional;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import jakarta.validation.Valid;

@RestController
@ResponseBody
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearer-key")
public class TopicoController {

	@Autowired
	private TopicoRepository topicoRepository;

	@Autowired
	private AgendaDeTopicoService service;

	public ResponseEntity<Page<DatosDetalleTopico>> listar(@PageableDefault(size = 10) Pageable paginacion) {
		var response = service.consultar(paginacion);
		return ResponseEntity.ok(response);
	}

	@PostMapping
	@Transactional
	public ResponseEntity<?> agregar(@RequestBody @Valid DatosAgregarTopico datos) throws ValidacionDeIntegridad {
		var response = service.agregar(datos);
		return ResponseEntity.ok(response);
	}

	@PutMapping
	@Transactional
	public ResponseEntity<?> actualizarTopico(@RequestBody @Valid DatosActualizarTopico datosActualizarTopico) {
		Topico topico = topicoRepository.getReferenceById(datosActualizarTopico.id());
		topico.actualizarDatos(datosActualizarTopico);
		return ResponseEntity.ok(new DatosDetalleTopico(topico.getId(), topico.getTitulo(), topico.getMensaje(),
				topico.getfechaCreacion(), topico.getStatus(), topico.getAutor().getId(), topico.getCurso().getId()));
	}

	@DeleteMapping
	@Transactional
	public ResponseEntity<?> eliminar(@RequestBody @Valid DatosCancelamientoTopico datos)
			throws ValidacionDeIntegridad {
		service.cancelar(datos);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<DatosDetalleTopico> retornaDatosTopico(@PathVariable Long id) {
		Topico topico = topicoRepository.getReferenceById(id);
		var datosTopico = new DatosDetalleTopico(topico.getId(), topico.getTitulo(), topico.getMensaje(),
				topico.getfechaCreacion(), topico.getStatus(), topico.getAutor().getId(), topico.getCurso().getId());
		return ResponseEntity.ok(datosTopico);
	}
}
