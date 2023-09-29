package com.alura.foro_inicial.modelo.topico;

import jakarta.validation.constraints.NotNull;

public record DatosCancelamientoTopico(
		@NotNull
	    Long idTopico,
	    MotivoCancelamiento motivo) {

}
