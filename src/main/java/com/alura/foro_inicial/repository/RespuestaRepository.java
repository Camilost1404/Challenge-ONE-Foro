package com.alura.foro_inicial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.alura.foro_inicial.modelo.respuesta.Respuesta;

import java.util.List;

@Repository
public interface RespuestaRepository extends JpaRepository<Respuesta, Long> {

	@Query("""
			select r from Respuesta r
			where
			topico.id=:idTopico
			""")
	List<Respuesta> findAllByTopicoId(Long idTopico);

}