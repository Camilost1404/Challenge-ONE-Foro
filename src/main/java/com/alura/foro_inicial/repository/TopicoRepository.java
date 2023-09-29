package com.alura.foro_inicial.repository;

import com.alura.foro_inicial.modelo.curso.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alura.foro_inicial.modelo.topico.Topico;

import java.time.LocalDateTime;

@Repository
public interface TopicoRepository extends JpaRepository<Topico, Long> {

	Boolean existsByCursoAndFechaCreacion(Curso curso, LocalDateTime fechaCreacion);

	Boolean existsByTitulo(String titulo);
}