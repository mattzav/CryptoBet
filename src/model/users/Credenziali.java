package model.users;

import java.io.Serializable;

public class Credenziali implements Serializable{
	
	private String username;
	private String password;
	private String tipo;
	
	public Credenziali(String username2, String password2) {
		// TODO Auto-generated constructor stub
		username=username2;
		password=password2;
	}

	public String getUsername() {
		// TODO Auto-generated method stub
		return username;
	}

	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	public void setTipo(String tipo) {
		this.tipo=tipo;
	}

	public String getTipo() {
		return tipo;
	}
}
