package com.alura.foro_inicial.modelo.respuesta;

import jakarta.validation.constraints.NotNull;

public record DatosCancelamientoRespuesta(
		@NotNull
	    Long idRespuesta,
	    MotivoCancelamientoRespuesta motivo) {

}
