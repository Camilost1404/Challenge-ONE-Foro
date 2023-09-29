package com.alura.foro_inicial.controller;

import com.alura.foro_inicial.infra.errores.ValidacionDeIntegridad;
import com.alura.foro_inicial.modelo.respuesta.*;
import com.alura.foro_inicial.repository.RespuestaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.transaction.annotation.Transactional;
import jakarta.validation.Valid;

import java.util.List;
import java.util.ArrayList;

@RestController
@ResponseBody
@RequestMapping("/respuestas")
@SecurityRequirement(name = "bearer-key")
public class RespuestaController {
	
	@Autowired
    RespuestaRepository respuestaRepository;

    @Autowired
	private AgendaDeRespuestaService service;
	
	@GetMapping
	public ResponseEntity<Page<DatosDetalleRespuesta>> listar (@PageableDefault(size = 10) Pageable paginacion) {
        var response = service.consultar(paginacion);
        return ResponseEntity.ok(response);
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<?> agregar(@RequestBody @Valid DatosAgregarRespuesta datos) throws ValidacionDeIntegridad {
        var response = service.agregar(datos);
        return ResponseEntity.ok(response);
	}

    @PutMapping
    @Transactional
    public ResponseEntity<?> actualizarRespuesta(@RequestBody @Valid DatosActualizarRespuesta datosActualizarRespuesta) {
        Respuesta respuesta = respuestaRepository.getReferenceById(datosActualizarRespuesta.id());
        respuesta.actualizarDatos(datosActualizarRespuesta);
        return ResponseEntity.ok(new DatosDetalleRespuesta(respuesta.getId(), respuesta.getMensaje(),
                respuesta.getTopico().getId(), respuesta.getfechaCreacion(), respuesta.getAutor().getId()));
    }
	
	@DeleteMapping
    @Transactional
    public ResponseEntity<?> eliminar(@RequestBody @Valid DatosCancelamientoRespuesta datos) throws ValidacionDeIntegridad {
        service.eliminar(datos);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/topico/{topico_id}")
    public ResponseEntity<List<DatosDetalleRespuesta>> retornaDatosRespuestaPorTopico(@PathVariable Long topico_id) {
        List<Respuesta> respuestas = respuestaRepository.findAllByTopicoId(topico_id);
        List<DatosDetalleRespuesta> datosRespuestas = new ArrayList<>();
        for (Respuesta respuesta : respuestas) {
            DatosDetalleRespuesta datosRespuesta = new DatosDetalleRespuesta(respuesta.getId(), respuesta.getMensaje(),
                    respuesta.getTopico().getId(), respuesta.getfechaCreacion(), respuesta.getAutor().getId());
            datosRespuestas.add(datosRespuesta);
        }
        return ResponseEntity.ok(datosRespuestas);
    }
}
