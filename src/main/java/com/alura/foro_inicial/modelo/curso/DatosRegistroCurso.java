package com.alura.foro_inicial.modelo.curso;

import jakarta.validation.constraints.*;

public record DatosRegistroCurso(
	@NotBlank
    String nombre,
    @NotNull
    Categoria categoria) {
}
