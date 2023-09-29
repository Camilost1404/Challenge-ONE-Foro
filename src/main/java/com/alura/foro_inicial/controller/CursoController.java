package com.alura.foro_inicial.controller;

import com.alura.foro_inicial.repository.CursoRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import java.net.URI;

import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import com.alura.foro_inicial.modelo.curso.Curso;
import com.alura.foro_inicial.modelo.curso.DatosActualizarCurso;
import com.alura.foro_inicial.modelo.curso.DatosListadoCurso;
import com.alura.foro_inicial.modelo.curso.DatosRegistroCurso;
import com.alura.foro_inicial.modelo.curso.DatosRespuestaCurso;

@RestController
@RequestMapping("/cursos")
@SecurityRequirement(name = "bearer-key")
public class CursoController {

	@Autowired
	private CursoRepository cursoRepository;

	@PostMapping
	@Transactional
	public ResponseEntity<DatosRespuestaCurso> registrarCurso(@RequestBody @Valid DatosRegistroCurso datosRegistroCurso,
															  UriComponentsBuilder uriComponentsBuilder) {
		Curso curso = cursoRepository.save(new Curso(datosRegistroCurso));
		DatosRespuestaCurso datosRespuestaCurso = new DatosRespuestaCurso(curso.getId(), curso.getNombre(), curso.getCategoria().toString());

		URI url = uriComponentsBuilder.path("/cursos/{id}").buildAndExpand(curso.getId()).toUri();
		return ResponseEntity.created(url).body(datosRespuestaCurso);
	}

	@GetMapping
	public ResponseEntity<Page<DatosListadoCurso>> listadoCursos(@PageableDefault(size = 2) Pageable paginacion) {
		 return ResponseEntity.ok(cursoRepository.findByActivoTrue(paginacion).map(DatosListadoCurso::new));
	}

	@PutMapping
	@Transactional
	public ResponseEntity<?> actualizarCurso(@RequestBody @Valid DatosActualizarCurso datosActualizarCurso) {
		Curso curso = cursoRepository.getReferenceById(datosActualizarCurso.id());
		curso.actualizarDatos(datosActualizarCurso);
		return ResponseEntity.ok(new DatosRespuestaCurso(curso.getId(), curso.getNombre(), curso.getCategoria().toString()));
	}

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> desactivarCurso(@PathVariable Long id) {
        Curso curso = cursoRepository.getReferenceById(id);
        curso.desactivarCurso();
        return ResponseEntity.noContent().build();
    }

	@GetMapping("/{id}")
	public ResponseEntity<DatosRespuestaCurso> retornaDatosCurso(@PathVariable Long id) {
		Curso curso = cursoRepository.getReferenceById(id);
		var datosCurso = new DatosRespuestaCurso(curso.getId(), curso.getNombre(), curso.getCategoria().toString());
		var cursoActivo=cursoRepository.findActivoById(datosCurso.id());
		if(!cursoActivo){
			throw new ValidationException("No se pueden mostrar registros de cursos inactivos en el sistema");
		}
		return ResponseEntity.ok(datosCurso);
	}
}
