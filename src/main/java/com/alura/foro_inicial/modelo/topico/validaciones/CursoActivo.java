package com.alura.foro_inicial.modelo.topico.validaciones;

import com.alura.foro_inicial.modelo.topico.DatosAgregarTopico;
import com.alura.foro_inicial.repository.CursoRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CursoActivo implements ValidadorDeTopicos {
	@Autowired
	private CursoRepository repository;

	public void validar(DatosAgregarTopico datos) {
		if (datos.idCurso() == null) {
			return;
		}
		var cursoActivo = repository.findActivoById(datos.idCurso());
		if (!cursoActivo) {
			throw new ValidationException("No se puede agregar topicos de cursos inactivos en el sistema");
		}
	}
}