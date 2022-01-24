package voceNaoVaiPassar.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import voceNaoVaiPassar.Comuns;
import voceNaoVaiPassar.beans.Grupo;
import voceNaoVaiPassar.beans.Usuario;

import com.mysql.jdbc.PreparedStatement;






public class GruposDAO extends DAO{

		
		

		
	public GruposDAO( Connection   connector){
					
	super(connector);
	}
		
		
	
	
	
	

	public List<Grupo> getGrupos(){
	
	List<Grupo> lista = new ArrayList<Grupo>();
		
		try {
			
		PreparedStatement  statement = (PreparedStatement) this.conector.prepareStatement(
		"SELECT "
		+ "id, cod, nome, descricao, data_criacao FROM "+Comuns.NOME_TB_GRUPOS);
		ResultSet set = statement.executeQuery();
		
			while( set.next()){
			
			Grupo aux = new Grupo();	
				
				
			aux.setId(set.getInt(1));	
			aux.setCod(set.getString(2));	
			aux.setNome(set.getString(3));	
			aux.setDescricao(set.getString(4));	
			aux.setData_criacao(set.getString(5));		
			
			lista.add(aux);
			}
		}
		catch (SQLException e) {
		JOptionPane.showMessageDialog(null, "Um erro ocorreu ao recuperar os dados dos grupos.", "ERRO", JOptionPane.ERROR_MESSAGE);		
		return new ArrayList<Grupo>();
		}
		
	return lista;
	}
	
	
	
	
	
	
	public boolean campoEmUso(String campo, String valor, int id_excessao){
	
			
	boolean retorno = false;
	
		try {
			
		PreparedStatement  statement = (PreparedStatement) this.conector.prepareStatement(
		"SELECT id FROM "+Comuns.NOME_TB_GRUPOS+" where "+campo+"='"+valor+"'"+(id_excessao>0?" and id<>"+id_excessao:""));
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
	
	
	
	
	


	public boolean novo(Grupo grupo){
		
		try {
			
		PreparedStatement statement	= null;

		statement = (PreparedStatement) this.conector.prepareStatement( 			      		
		"INSERT INTO "+Comuns.NOME_TB_GRUPOS+"("
		+ "nome, cod, descricao, data_criacao"
		+ ") VALUES ("
		+ "?,?,?,?)");   
				
		statement.setString( 1,  grupo.getNome());  
		statement.setString( 2,  grupo.getCod()); 
		statement.setString( 3,  grupo.getDescricao()); 
		statement.setString( 4,  Comuns.getDataAtual()); 
		statement.executeUpdate(); 
			
		}
		catch (SQLException e) {
			
		JOptionPane.showMessageDialog(null, "Um erro ocorreu ao salvar as informações.", "ERRO", JOptionPane.ERROR_MESSAGE);		
		return false;	
		}		
		
	
	return true;
	}
	
	
	
	
	
	

	public boolean deleta( int id){
		
	int dialogButton = JOptionPane.YES_NO_OPTION;
	int dialogResult = JOptionPane.showConfirmDialog(null, "Você tem certeza que deseja excluir este grupo?", "Confirmação",dialogButton);
			
		if(dialogResult==0){
		
			try {
				
			Statement stmt = conector.createStatement();
			
			stmt.executeUpdate("delete from "+Comuns.NOME_TB_GRUPOS+" where id = "+id); 
			
			stmt.executeUpdate("delete from "+Comuns.NOME_TB_GRUP_ACESS+" where fk_grupo = "+id); 
			
			}
			catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Um erro ocorreu ao alterar as informações do grupo.", "ERRO", JOptionPane.ERROR_MESSAGE);		
			return false;
			}			
	
		return true;
		}
	
	return false;
	}
	
	
	
	
	

	
	public Grupo getGrupo(int id){
		
	
	Grupo grupo = null;
		
		try {
			
		PreparedStatement  statement = (PreparedStatement) this.conector.prepareStatement(
		"SELECT "
		+ "id, cod, nome, descricao, data_criacao FROM "+Comuns.NOME_TB_GRUPOS+" WHERE id = "+id);
		ResultSet set = statement.executeQuery();
		
			if( set.next()){
			
			grupo = new Grupo();	
					
			grupo.setId(set.getInt(1));	
			grupo.setCod(set.getString(2));	
			grupo.setNome(set.getString(3));	
			grupo.setDescricao(set.getString(4));	
			grupo.setData_criacao(set.getString(5));		
			}
		}
		catch (SQLException e) {
		JOptionPane.showMessageDialog(null, "Um erro ocorreu ao recuperar os dados do grupo.", "ERRO", JOptionPane.ERROR_MESSAGE);		
		return null;
		}
		
	return grupo;		
	}
	
	
	
	
	

	public boolean altera(Grupo grupo){
		
		try {
	
		PreparedStatement statement	= null;
		statement = (PreparedStatement) this.conector.prepareStatement(
		"UPDATE "+Comuns.NOME_TB_GRUPOS+" SET "+
		"nome = ?, "+ 
		"cod = ?, "+ 
		"descricao = ? "+
		"WHERE id="+grupo.getId());
		
		statement.setString(1, grupo.getNome());
		statement.setString(2, grupo.getCod());
		statement.setString(3, grupo.getDescricao());	
		statement.executeUpdate();
		}
		catch (SQLException e) {
		JOptionPane.showMessageDialog(null, "Um erro ocorreu ao salvar as informações do grupo.", "ERRO", JOptionPane.ERROR_MESSAGE);		
		return false;
		}		
			
	return true;	
	}

	

	
	
	public List<Usuario> getUsuariosDeGrupo(int id_grupo){
		
	
	List<Usuario> lista = new ArrayList<Usuario>();
		
		try {
			
		PreparedStatement  statement = (PreparedStatement) this.conector.prepareStatement(
		"SELECT "
		+ "user.id, user.usuario "
		+ "FROM "+Comuns.NOME_TB_USUARIOS+" as user "
		+ "INNER JOIN "+Comuns.NOME_TB_USER_GRUP+" as uxg on uxg.fk_usuario = user.id "
		+ "WHERE user.status = 'ATIVO' and uxg.fk_grupo ="+id_grupo);
		ResultSet set = statement.executeQuery();
		

			while( set.next()){
			
			Usuario usuario = new Usuario(); 	
			usuario.setId(set.getInt(1));	
			usuario.setUsuario(set.getString(2));	
			
			lista.add(usuario);
			}
		}
		catch (SQLException e) {
		JOptionPane.showMessageDialog(null, "Um erro ocorreu ao recuperar os membros do grupo.", "ERRO", JOptionPane.ERROR_MESSAGE);		
		return new ArrayList<Usuario>();
		}
		
	return lista;			
	}
	
	
	
	
	
	
	
	public List<Usuario> getUsuariosForaDeGrupo(int id_grupo){
		
	
	List<Usuario> lista = new ArrayList<Usuario>();
		
		try {
			
		PreparedStatement  statement = (PreparedStatement) this.conector.prepareStatement(
		"SELECT "
		+ "user.id, user.usuario "
		+ "FROM "+Comuns.NOME_TB_USUARIOS+" as user "
		+ "WHERE user.status = 'ATIVO' and user.usuario<> '"+Comuns.NOME_USUARIO_ADMIN+"' and id NOT IN (select fk_usuario from "+Comuns.NOME_TB_USER_GRUP+" where fk_grupo="+id_grupo+")");
		ResultSet set = statement.executeQuery();
		

			while( set.next()){
			
			Usuario usuario = new Usuario(); 	
			usuario.setId(set.getInt(1));	
			usuario.setUsuario(set.getString(2));	
			
			lista.add(usuario);
			}
		}
		catch (SQLException e) {
		JOptionPane.showMessageDialog(null, "Um erro ocorreu ao recuperar os membros do grupo.", "ERRO", JOptionPane.ERROR_MESSAGE);		
		return new ArrayList<Usuario>();
		}
		
	return lista;			
	}
	

	
	
	
	
	public boolean removerMembroDeGrupo(int id_grupo, int id_membro){
		
	int dialogButton = JOptionPane.YES_NO_OPTION;
	int dialogResult = JOptionPane.showConfirmDialog(null, "Você tem certeza que deseja remover este membro do grupo?", "Confirmação",dialogButton);
				
		if(dialogResult==0){
			
			try {
					
			Statement stmt = conector.createStatement();
					
			stmt.executeUpdate("delete from "+Comuns.NOME_TB_USER_GRUP+" where fk_grupo = "+id_grupo+" and fk_usuario="+id_membro); 
				
			}
			catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Um erro ocorreu ao alterar as informações do grupo.", "ERRO", JOptionPane.ERROR_MESSAGE);		
			return false;
			}			
		
		return true;
		}
		
	return false;	
	}
	
	
	
	

	public boolean addUsuarioAGrupo(int id_grupo, int id_usuario){
			
		try {
					
		PreparedStatement  statement = (PreparedStatement) this.conector.prepareStatement(
		"INSERT INTO "+Comuns.NOME_TB_USER_GRUP+"(fk_usuario, fk_grupo) VALUES (?,?)");   			
		statement.setInt( 1,  id_usuario);  
		statement.setInt( 2,  id_grupo); 
		statement.executeUpdate(); 
		
		}
		catch (SQLException e) {
		JOptionPane.showMessageDialog(null, "Um erro ocorreu ao alterar as informações do grupo.", "ERRO", JOptionPane.ERROR_MESSAGE);		
		return false;
		}			
			
	return true;	
	}
	
	
	
	
	
}
