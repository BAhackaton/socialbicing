package com.mobilenik.socialBicing.common.biz;

public class DatosRetiroBici {
	
	private String codigo;

	public DatosRetiroBici(String codigo){
		this.setCodigo(codigo);
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getCodigo() {
		return codigo;
	}
}
