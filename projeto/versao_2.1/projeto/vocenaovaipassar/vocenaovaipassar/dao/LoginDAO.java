package vocenaovaipassar.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import vocenaovaipassar.beans.ListaDeRecursosDoSistema;
import vocenaovaipassar.beans.Recurso;
import vocenaovaipassar.beans.UsuarioAtual;
import vocenaovaipassar.geral.Comuns;





public class LoginDAO extends DAO{

	

	
	public LoginDAO( Connection   conector){
				
	super(conector);
	}
	
	
	
	
	
	public boolean validaBD(){
		
		try{	

		ResultSet tables = this.conector.getMetaData().getTables(null, null, "", null); 
		
		boolean tb_users = false;
		boolean tb_accesses = false;
		boolean tb_groups = false;
		boolean tb_gxa = false;
		boolean tb_uxg = false;

			while(tables.next()){
			
			if( tables.getString("TABLE_NAME").compareTo(Comuns.NOME_TB_USUARIOS) == 0)
			tb_users = true;
			
			if( tables.getString("TABLE_NAME").compareTo(Comuns.NOME_TB_ACESSOS) == 0)
			tb_accesses = true;
			
			if( tables.getString("TABLE_NAME").compareTo(Comuns.NOME_TB_GRUPOS) == 0)
			tb_groups = true;
			
			if( tables.getString("TABLE_NAME").compareTo(Comuns.NOME_TB_GRUPO_X_ACESSO) == 0)
			tb_gxa = true;
			
			if( tables.getString("TABLE_NAME").compareTo(Comuns.NOME_TB_USUARIOS_X_GRUPO) == 0)
			tb_uxg = true;
			}
			
		tables.close();  
				
		if(tb_users && tb_accesses && tb_groups && tb_gxa && tb_uxg)	
		return true;
		
		}
		catch (SQLException e) {return false;}	
			
	
	return false;
	}
	
	
	

	
	public boolean criaTabelasDeLogin(ListaDeRecursosDoSistema listaDeRecursosDoSistema){
	
		try { 
				
		Statement stmt = conector.createStatement();
		stmt.executeUpdate("CREATE TABLE IF NOT EXISTS "+Comuns.NOME_TB_USUARIOS+" (id INT NOT NULL AUTO_INCREMENT, usuario VARCHAR(150) NOT NULL, email VARCHAR(150) NOT NULL, data_cadastro VARCHAR(100) NOT NULL, data_ult_alter VARCHAR(100) NULL, senha TEXT NOT NULL, status ENUM('ATIVO', 'INATIVO') NOT NULL, PRIMARY KEY (id)) ENGINE = MyISAM;"); 
		stmt.executeUpdate("CREATE TABLE IF NOT EXISTS "+Comuns.NOME_TB_GRUPOS+" (id INT NOT NULL AUTO_INCREMENT, nome VARCHAR(100) NOT NULL, cod VARCHAR(45) NOT NULL, descricao VARCHAR(150) NULL, data_criacao VARCHAR(40) NOT NULL, PRIMARY KEY (id)) ENGINE = MyISAM;");
		stmt.executeUpdate("CREATE TABLE IF NOT EXISTS "+Comuns.NOME_TB_ACESSOS+" (id INT NOT NULL AUTO_INCREMENT, nome VARCHAR(200) NOT NULL, tipo ENUM('VER_ED_REM','SIM_NAO') NOT NULL, cod VARCHAR(45) NOT NULL, PRIMARY KEY (id)) ENGINE = InnoDB;");
		stmt.executeUpdate("CREATE TABLE IF NOT EXISTS "+Comuns.NOME_TB_USUARIOS_X_GRUPO+" (id INT NOT NULL AUTO_INCREMENT, fk_usuario INT NOT NULL, fk_grupo INT NOT NULL, PRIMARY KEY (id), INDEX fk_usuario_idx (fk_usuario ASC), INDEX fk_grupo_idx (fk_grupo ASC)) ENGINE = MyISAM;");
		stmt.executeUpdate("CREATE TABLE IF NOT EXISTS "+Comuns.NOME_TB_GRUPO_X_ACESSO+" (id INT NOT NULL AUTO_INCREMENT, fk_grupo INT NOT NULL, fk_acesso INT NOT NULL, PRIMARY KEY (id), valor VARCHAR(45), INDEX fk_acesso_idx (fk_acesso ASC), INDEX fk_grupo_idx (fk_grupo ASC)) ENGINE = MyISAM;");
		
		
		PreparedStatement statement	= null;
		
		statement =  this.conector.prepareStatement( 			      		
		"INSERT INTO "+Comuns.NOME_TB_USUARIOS
		+ " (usuario, email, data_cadastro, senha, status)"
		+" VALUES "
		+ " (?,?,?,?,?);", Statement.RETURN_GENERATED_KEYS);   
				
		statement.setString( 1,  Comuns.NOME_USUARIO_ADMIN); 
		statement.setString( 2,  Comuns.EMAIL_USUARIO_ADMIN);
		statement.setString( 3,  Comuns.getDataAtual());
		statement.setString( 4,  Comuns.cript(Comuns.SENHA_USUARIO_ADMIN));
		statement.setString( 5,  "ATIVO"); 
		statement.executeUpdate(); 		
		
		int id_admin = 0;
		
		ResultSet resultSet = statement.getGeneratedKeys();
		
		if(resultSet.next())
		id_admin = resultSet.getInt(1);	
				
		if(id_admin ==0)
		return this.mensagemDeErroDeAcessoAoBD();  

			  
		statement = (PreparedStatement) this.conector.prepareStatement( 			      		
		"INSERT INTO "+Comuns.NOME_TB_GRUPOS+" ("
		+ "nome, cod, data_criacao)"+
		" VALUES ("
		+ "?,?,?)", Statement.RETURN_GENERATED_KEYS);   
						
		statement.setString( 1,  Comuns.NOME_GRUPO_ADMIN); 
		statement.setString( 2,  Comuns.COD_GRUPO_ADMIN);
		statement.setString( 3,  Comuns.getDataAtual());
		
		statement.executeUpdate(); 
		
		
		int id_grupo_admin = 0;
		
		resultSet = statement.getGeneratedKeys();
			
		if(resultSet.next())
		id_grupo_admin = resultSet.getInt(1);	
				
		if(id_grupo_admin ==0)
		return this.mensagemDeErroDeAcessoAoBD(); 
		
		
		statement = (PreparedStatement) this.conector.prepareStatement( 			      		
		"INSERT INTO "+Comuns.NOME_TB_USUARIOS_X_GRUPO+"("
		+ "fk_usuario, fk_grupo)"+
		" VALUES ("
		+ "?,?)", Statement.RETURN_GENERATED_KEYS);   
								
		statement.setInt( 1,  id_admin); 
		statement.setInt( 2,  id_grupo_admin);
		statement.executeUpdate(); 
		
			
			if(listaDeRecursosDoSistema!=null){
		
				for( Recurso aux: listaDeRecursosDoSistema.getRecursos()){
				
				statement = (PreparedStatement) this.conector.prepareStatement( 			      		
				"INSERT INTO "+Comuns.NOME_TB_ACESSOS+" ("
				+ "nome, tipo, cod)"+
				" VALUES ("
				+ "?,?,?)", Statement.RETURN_GENERATED_KEYS);   
													
				statement.setString( 1,  aux.getNome()); 
				statement.setString( 2,  aux.getTipo());
				statement.setString( 3,  aux.getCodigo());
				statement.executeUpdate(); 
				}
			}
		
		}
		catch (SQLException e) {e.printStackTrace();return this.mensagemDeErroDeAcessoAoBD();}	
		

	return true;	
	}
	
	
	
		
	

	public void login(String usuario, String senha, UsuarioAtual usuarioAtual){
	
	senha = Comuns.cript(senha);	
		
		try {
			
		PreparedStatement statement	 = null;
		statement = (PreparedStatement) this.conector.prepareStatement(
		"SELECT id FROM "+Comuns.NOME_TB_USUARIOS+" WHERE usuario= ? and senha = ? and status = 'ATIVO'");
		statement.setString(1,  usuario);
		statement.setString(2,  senha);
		ResultSet set = statement.executeQuery();
		
			if( set.next() ){
			
			usuarioAtual.setId(set.getInt(1));
			usuarioAtual.setNome(usuario);	
			}
			
		statement.close();
		}
		catch (SQLException e) {
		
		JOptionPane.showMessageDialog(null, "Um erro ocorreu ao tentar recuperar as informações do usuário.", "ERRO", JOptionPane.ERROR_MESSAGE);		
		return;
		}	
	}
	
	
	
	
	
}
