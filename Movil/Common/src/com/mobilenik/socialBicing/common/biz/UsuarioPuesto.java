package com.mobilenik.socialBicing.common.biz;
import java.util.Vector;


public class UsuarioPuesto extends AbsUsuario{

	private Vector bicis;

	public UsuarioPuesto(int id, String nombre, int estadoUsuario, Vector bicis){
		super(id, nombre, estadoUsuario);
		this.setBicis(bicis);
	}

	public void setBicis(Vector bicis) {
		this.bicis = bicis;
	}

	public Vector getBicis() {
		return bicis;
	}
	

}
