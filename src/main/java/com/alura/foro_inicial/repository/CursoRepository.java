package com.alura.foro_inicial.repository;

import com.alura.foro_inicial.modelo.curso.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.alura.foro_inicial.modelo.curso.Curso;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {
	Page<Curso> findByActivoTrue(Pageable paginacion);

	@Query("""
			select c from Curso c
			where
			c.categoria=:categoria
			order by rand()
			limit 1
			""")
	Curso seleccionarCursoConCategoria(Categoria categoria);

	@Query("""
			select c.activo
			from Curso c
			where c.id=:idCurso
			""")
	Boolean findActivoById(Long idCurso);
}
