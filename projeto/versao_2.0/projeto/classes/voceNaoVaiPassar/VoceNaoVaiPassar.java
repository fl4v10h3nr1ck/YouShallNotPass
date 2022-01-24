package voceNaoVaiPassar;



import java.sql.Connection;

import javax.swing.JOptionPane;

import voceNaoVaiPassar.beans.ListaDeRecursosDoSistema;
import voceNaoVaiPassar.beans.UsuarioAtual;
import voceNaoVaiPassar.dao.LoginDAO;
import voceNaoVaiPassar.forms.FormDeGestaoDeUsuarios;
import voceNaoVaiPassar.forms.FormDeLogin;





public final class VoceNaoVaiPassar{

	
	
	
private LoginDAO dao;	

private ListaDeRecursosDoSistema listaDeRecursosDoSistema;


	


	public VoceNaoVaiPassar( Connection connector){
	
	this(connector, null);
	}
	
	




	public VoceNaoVaiPassar( Connection connector, ListaDeRecursosDoSistema listaDeRecursosDoSistema){
		
	this.dao = new LoginDAO(connector);	
	
	this.listaDeRecursosDoSistema = listaDeRecursosDoSistema;
	}
	
	
	

	
	public final boolean prepara(){
			
	if(!this.dao.validaBD())
	return 	this.dao.criaTabelasDeLogin(this.listaDeRecursosDoSistema);	

	return true;
	}
	
	
	
	
	
	public final UsuarioAtual login(){
		
	UsuarioAtual usuarioAtual = new UsuarioAtual();
			
	FormDeLogin loginForm = new FormDeLogin(this.dao, usuarioAtual);
	loginForm.mostrar();
					
	return usuarioAtual!=null && usuarioAtual.getId()>0?usuarioAtual:null;
	}
		
		
		
		

	
	public final void formGestaoDeUsuarios(UsuarioAtual usuarioAtual){	
		
	if(usuarioAtual == null)
	return;
			
		if(usuarioAtual.pertenceAoGrupo(this.dao.conector, Comuns.COD_GRUPO_ADMIN)){	
			
		FormDeGestaoDeUsuarios newUsersForm = new FormDeGestaoDeUsuarios(this.dao.conector);
		newUsersForm.mostrar();	
		}
		else
		JOptionPane.showMessageDialog(null, "O usuário atual não possui permissão para acessar este recurso do sistema.", "ERRO", JOptionPane.ERROR_MESSAGE);		
					
	}
		
		
	
	
	
	
	
}
