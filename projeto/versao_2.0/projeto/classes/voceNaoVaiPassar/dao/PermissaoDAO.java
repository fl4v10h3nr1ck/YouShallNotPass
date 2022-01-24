package voceNaoVaiPassar.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import voceNaoVaiPassar.Comuns;
import voceNaoVaiPassar.beans.PermissaoDeGrupo;
import voceNaoVaiPassar.beans.Recurso;

import com.mysql.jdbc.PreparedStatement;

public class PermissaoDAO extends DAO{

	
	
	
	
	public PermissaoDAO( Connection   conector){
		
	super(conector);
	}
		
		
		
		
	
	public List<Recurso> getPermissoesQueGrupoNaoTem(int idGrupo){
		
		
	List<Recurso> lista = new ArrayList<Recurso>();
		
		try {
			
		PreparedStatement  statement = (PreparedStatement) this.conector.prepareStatement(
		"SELECT "
		+ "a.id, a.nome, a.tipo, a.cod "
		+ "FROM "+Comuns.NOME_TB_ACESSOS+" as a WHERE a.id NOT IN "
		+ "(select fk_acesso from "+Comuns.NOME_TB_GRUP_ACESS+" WHERE fk_grupo ="+idGrupo+")");
		ResultSet set = statement.executeQuery();
		
		
			while( set.next()){
			
			Recurso recurso = new Recurso(); 	
			recurso.setId(set.getInt(1));	
			recurso.setNome(set.getString(2));	
			recurso.setTipo(set.getString(3));	
			recurso.setCodigo(set.getString(4));		
			
			lista.add(recurso);
			}
		}
		catch (SQLException e) {
		JOptionPane.showMessageDialog(null, "Um erro ocorreu ao recuperar as permissões.", "ERRO", JOptionPane.ERROR_MESSAGE);		
		return new ArrayList<Recurso>();
		}
		
	return lista;			
	}
	
	
	
	
	
	public boolean addPermissaoAGrupo(int idGrupo, int idPermissao){
	
	boolean ja_existe = false;	
		
		try {
			
		PreparedStatement  statement = (PreparedStatement) this.conector.prepareStatement(
		"SELECT id FROM "+Comuns.NOME_TB_GRUP_ACESS+" WHERE fk_grupo="+idGrupo+" AND fk_acesso="+idPermissao);
		ResultSet set = statement.executeQuery();
									
		if( set.next())	
		ja_existe = true;
		
		
			if(!ja_existe){
			
			statement = (PreparedStatement) this.conector.prepareStatement("INSERT INTO "+Comuns.NOME_TB_GRUP_ACESS+"(fk_grupo, fk_acesso) VALUES (?,?)");   			
			statement.setInt( 1,  idGrupo);  
			statement.setInt( 2,  idPermissao); 
			statement.executeUpdate(); 

			}
			
		statement.close();
		}
		catch (SQLException e) {
					
		JOptionPane.showMessageDialog(null, "Um erro ocorreu ao salvar as informações.", "ERRO", JOptionPane.ERROR_MESSAGE);		
		return false;	
		}						
			
	return true;	
	}
	
	
	
	
	

	public List<PermissaoDeGrupo> getPermissoes(int id_grupo){
		
	List<PermissaoDeGrupo> lista = new ArrayList<PermissaoDeGrupo>();
		
		try {
			
		PreparedStatement  statement = (PreparedStatement) this.conector.prepareStatement(
		"SELECT "
		+ "a.id, a.nome, a.tipo, a.cod,"
		+ "axg.id, axg.valor "
		+ "FROM "+Comuns.NOME_TB_GRUP_ACESS+" as axg "
		+ "INNER JOIN "+Comuns.NOME_TB_ACESSOS+" as a on a.id = axg.fk_acesso "
		+ "WHERE axg.fk_grupo ="+id_grupo+" ORDER BY a.id ASC");
		ResultSet set = statement.executeQuery();
		
			while( set.next()){
			
			Recurso recurso = new Recurso(); 	
			recurso.setId(set.getInt(1));	
			recurso.setNome(set.getString(2));	
			recurso.setTipo(set.getString(3));	
			recurso.setCodigo(set.getString(4));		
				
			PermissaoDeGrupo permissao = new PermissaoDeGrupo();	
			permissao.setId(set.getInt(5));	
			permissao.setValor(set.getString(6));	
			permissao.setId_grupo(id_grupo);
			permissao.setRecurso(recurso);
			
			lista.add(permissao);
			}
		}
		catch (SQLException e) {
		JOptionPane.showMessageDialog(null, "Um erro ocorreu ao recuperar as permissões do grupo.", "ERRO", JOptionPane.ERROR_MESSAGE);		
		return new ArrayList<PermissaoDeGrupo>();
		}
		
	return lista;	
		
	}
	
	
	
	
	

	
	public boolean deletaPermissaoDeGrupo(int id_grupo, int id_permissao){
	
	int dialogButton = JOptionPane.YES_NO_OPTION;
	int dialogResult = JOptionPane.showConfirmDialog(null, "Você tem certeza que deseja excluir esta permissão de grupo?", "Confirmação",dialogButton);
				
		if(dialogResult==0){
			
			try {
					
			Statement stmt = conector.createStatement();
	
			stmt.executeUpdate("delete from "+Comuns.NOME_TB_GRUP_ACESS+" where fk_grupo = "+id_grupo +" and fk_acesso = "+id_permissao); 
				
			}
			catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Um erro ocorreu ao alterar as informações do grupo.", "ERRO", JOptionPane.ERROR_MESSAGE);		
			return false;
			}			
		
		return true;
		}
		
	return false;	
	}
	

	
	
	
	
	public boolean addPermisoes(int id_grupo, List<PermissaoDeGrupo> permissoes){
		
		try {
			
		Statement stmt = conector.createStatement();
		stmt.executeUpdate("delete from "+Comuns.NOME_TB_GRUP_ACESS+" where fk_grupo = "+id_grupo); 
		
			for(PermissaoDeGrupo aux: permissoes){
			
			PreparedStatement  statement = (PreparedStatement) this.conector.prepareStatement("INSERT INTO "+Comuns.NOME_TB_GRUP_ACESS+"(fk_grupo, fk_acesso, valor) VALUES (?,?,?)");   			
			statement.setInt( 1,  id_grupo);  
			statement.setInt( 2,  aux.getRecurso().getId()); 
			
			String valor = aux.getCombo().getSelectedItem().toString();
			statement.setString( 3,  valor.compareTo("...")==0?"":valor); 
			statement.executeUpdate(); 
				
			}	
				
		}
		catch (SQLException e) {
		JOptionPane.showMessageDialog(null, "Um erro ocorreu ao alterar as informações do grupo.", "ERRO", JOptionPane.ERROR_MESSAGE);		
		return false;
		}			
			
	return true;
	}
	
	
	
	
}
