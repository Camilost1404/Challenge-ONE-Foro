package com.alura.foro_inicial.modelo.topico;

import com.alura.foro_inicial.modelo.curso.Categoria;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosAgregarTopico(
        @NotBlank
        String titulo,
        @NotBlank
        String mensaje,
        @NotNull
        Long idAutor,
        Long idCurso,

        Categoria categoria) {

}
