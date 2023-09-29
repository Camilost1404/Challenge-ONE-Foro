package com.alura.foro_inicial.infra.errores;

public class ValidacionDeIntegridad extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ValidacionDeIntegridad(String s) {
		super(s);
	}
}
