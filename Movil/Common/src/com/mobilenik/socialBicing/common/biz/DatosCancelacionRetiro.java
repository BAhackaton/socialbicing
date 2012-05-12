package com.mobilenik.socialBicing.common.biz;

public class DatosCancelacionRetiro extends DatosCancelacion{

	public static final int MOTIVO_SOLICITANTE = 1;
	public static final int MOTIVO_RESPONSABLE = 2;
	
	private int motivo;
	
	public DatosCancelacionRetiro(String mensaje, int motivo) {
		super(mensaje);
		this.setMotivo(motivo);
	}

	public void setMotivo(int motivo) {
		this.motivo = motivo;
	}

	public int getMotivo() {
		return motivo;
	}

}
