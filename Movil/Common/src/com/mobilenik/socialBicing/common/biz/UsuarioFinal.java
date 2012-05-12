package com.mobilenik.socialBicing.common.biz;


public class UsuarioFinal extends AbsUsuario{

	private Bici bici;

	
	public UsuarioFinal(int id, String nombre, int estadoUsuario, Bici bici){
		super(id, nombre, estadoUsuario);
		this.setBici(bici);
	}


	public void setBici(Bici bici) {
		this.bici = bici;
	}


	public Bici getBici() {
		return bici;
	}

}
