package voceNaoVaiPassar.dao;

import java.sql.Connection;

import javax.swing.JOptionPane;


public abstract class DAO {


	
public Connection   conector;




	
	public DAO( Connection   connector){
				
	this.conector = connector;
	}	

	
	
	
	
	
	
	public boolean mensagemDeErroDeAcessoAoBD(){
		
	JOptionPane.showMessageDialog(null, "Acesso ao banco de dados indisponível.", "ERRO", JOptionPane.ERROR_MESSAGE);
	return false;
	}
	
	
	
}
