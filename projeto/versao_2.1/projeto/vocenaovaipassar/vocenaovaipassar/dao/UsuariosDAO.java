package vocenaovaipassar.dao;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import vocenaovaipassar.beans.Usuario;
import vocenaovaipassar.geral.Comuns;

import com.mysql.jdbc.PreparedStatement;





public class UsuariosDAO extends DAO{

	
	

	
	public UsuariosDAO( Connection   connector){
				
	super(connector);
	}
	
	
	

	
	public Usuario getUsuario(int id){
		
	
	Usuario usuario = null;
		
		try {
			
		PreparedStatement  statement = (PreparedStatement) this.conector.prepareStatement(
		"SELECT "
		+ "id, usuario, email, status, data_cadastro, "
		+ "data_ult_alter FROM "+Comuns.NOME_TB_USUARIOS+" WHERE id = "+id);
		ResultSet set = statement.executeQuery();
		
			if( set.next()){
			
			usuario = new Usuario();	
					
			usuario.setId(set.getInt(1));	
			usuario.setUsuario(set.getString(2));	
			usuario.setEmail(set.getString(3));	
			usuario.setStatus(set.getString(4));	
			usuario.setData_cadastro(set.getString(5));	
			usuario.setData_ult_alter(set.getString(6));	
			}
		}
		catch (SQLException e) {
		JOptionPane.showMessageDialog(null, "Um erro ocorreu ao recuperar os dados do usuário.", "ERRO", JOptionPane.ERROR_MESSAGE);		
		return null;
		}
		
	return usuario;		
	}
	
	
	
	
	
	
	public List<Usuario> getUsuarios(){
	
	List<Usuario> lista = new ArrayList<Usuario>();
		
		try {
			
		PreparedStatement  statement = (PreparedStatement) this.conector.prepareStatement(
		"SELECT "
		+ "id, usuario, email, status, data_cadastro, "
		+ "data_ult_alter FROM "+Comuns.NOME_TB_USUARIOS);
		ResultSet set = statement.executeQuery();
		
			while( set.next()){
			
			Usuario aux = new Usuario();	
				
				
			aux.setId(set.getInt(1));	
			aux.setUsuario(set.getString(2));	
			aux.setEmail(set.getString(3));	
			aux.setStatus(set.getString(4));	
			aux.setData_cadastro(set.getString(5));	
			aux.setData_ult_alter(set.getString(6));	
			
			lista.add(aux);
			}
		}
		catch (SQLException e) {
		JOptionPane.showMessageDialog(null, "Um erro ocorreu ao recuperar os dados dos usuários.", "ERRO", JOptionPane.ERROR_MESSAGE);		
		return new ArrayList<Usuario>();
		}
		
	return lista;
	}
	
	
	
	
	
	public boolean nomeDeUsuarioEmUso(String nome, int id_excessao){
	
	boolean retorno = false;
		try {
			
		PreparedStatement  statement = (PreparedStatement) this.conector.prepareStatement(
		"SELECT id FROM "+Comuns.NOME_TB_USUARIOS+" where usuario='"+nome+"'"+(id_excessao>0?" and id<>"+id_excessao:""));
		ResultSet set = statement.executeQuery();
		
		if( set.next())
		retorno = true;
				
		set.close();
		
		}
		catch (SQLException e) {
		JOptionPane.showMessageDialog(null, "Um erro ocorreu ao recuperar os dados dos usuários.", "ERRO", JOptionPane.ERROR_MESSAGE);		
		return false;
		}
	
	return retorno;
	}
	
	
	
	
	
	

	public boolean novo(Usuario usuario){
		
		try {
			
		PreparedStatement statement	= null;

		statement = (PreparedStatement) this.conector.prepareStatement( 			      		
		"INSERT INTO "+Comuns.NOME_TB_USUARIOS+"("
		+ "usuario, email, data_cadastro, senha, status"
		+ ") VALUES ("
		+ "?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);   
				
		statement.setString( 1,  usuario.getUsuario());  
		statement.setString( 2,  usuario.getEmail()); 
		statement.setString( 3,  Comuns.getDataAtual()); 
		statement.setString( 4,  usuario.getSenha()); 
		statement.setString( 5,  "ATIVO");  
		statement.executeUpdate(); 
			
		}
		catch (SQLException e) {
			
		JOptionPane.showMessageDialog(null, "Um erro ocorreu ao salvar as informações.", "ERRO", JOptionPane.ERROR_MESSAGE);		
		return false;	
		}		
		
	
	return true;
	}
	
	
	
	
	
	
	
	public boolean altera(Usuario usuario){
		
		try {
	
		PreparedStatement statement	= null;
		statement = (PreparedStatement) this.conector.prepareStatement(
		"UPDATE "+Comuns.NOME_TB_USUARIOS+" SET "+
		"usuario = ?, "+ 
		"email = ?, "+ 
		"data_ult_alter = ? "+
		(usuario.getSenha()!=null && usuario.getSenha().length() > 0?", senha = ? ":"")+
		"WHERE id="+usuario.getId());
		
		statement.setString(1, usuario.getUsuario());
		statement.setString(2, usuario.getEmail());
		statement.setString(3, Comuns.getDataAtual());
		
		if(usuario.getSenha()!=null && usuario.getSenha().length() > 0)	
		statement.setString(4, usuario.getSenha());
		
		statement.executeUpdate();
		}
		catch (SQLException e) {
		JOptionPane.showMessageDialog(null, "Um erro ocorreu ao salvar as informações do usuário.", "ERRO", JOptionPane.ERROR_MESSAGE);		
		return false;
		}		
			
	
	return true;	
	}

	
	
	
	
	
	
	public boolean deleta( int id){
		
	int dialogButton = JOptionPane.YES_NO_OPTION;
	int dialogResult = JOptionPane.showConfirmDialog(null, "Você tem certeza que deseja excluir este usuário?", "Confirmação",dialogButton);
			
		if(dialogResult==0){
		
			try {
				
			Statement stmt = conector.createStatement();
			
			stmt.executeUpdate("delete from "+Comuns.NOME_TB_USUARIOS+" where id = "+id); 
			
			stmt.executeUpdate("delete from "+Comuns.NOME_TB_USUARIOS_X_GRUPO+" where fk_usuario = "+id); 
			
			}
			catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Um erro ocorreu ao alterar as informações do usuário.", "ERRO", JOptionPane.ERROR_MESSAGE);		
			return false;
			}			
	
		return true;
		}
	
	return false;
	}
	
	

	
	
	
	
	public boolean ativaOuDesativa(Usuario usuario){
		
		try {
	
		PreparedStatement statement	= null;
		statement = (PreparedStatement) this.conector.prepareStatement(
		"UPDATE "+Comuns.NOME_TB_USUARIOS+" SET "+
		"status = ? "+ 
		"WHERE id="+usuario.getId());
		
		statement.setString(1, usuario.getStatus()!=null && usuario.getStatus().compareTo("ATIVO")==0?"INATIVO":"ATIVO");
		statement.executeUpdate();
		}
		catch (SQLException e) {
		JOptionPane.showMessageDialog(null, "Um erro ocorreu ao salvar as informações do usuário.", "ERRO", JOptionPane.ERROR_MESSAGE);		
		return false;
		}		
			
	
	return true;	
	}
	
}

