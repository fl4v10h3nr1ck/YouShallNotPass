


package voceNaoVaiPassar.beans;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import voceNaoVaiPassar.Comuns;

import com.mysql.jdbc.PreparedStatement;






public final class UsuarioAtual{

	

	
	
	
private int Id;	
private String nome;

	
	


	public int getId(){ return this.Id;}
	public void setId( int ID){ this.Id = ID;}
	public String getNome() {return nome;}
	public void setNome(String nome) {this.nome = nome;}
	
	
	
	
	public boolean pertenceAoGrupo(Connection connector, String codGroup){
	
	if(connector == null || codGroup == null)
	return false;
			
		try {
			
		PreparedStatement statement = (PreparedStatement) connector.prepareStatement(
		"SELECT uxg.id FROM "+Comuns.NOME_TB_USER_GRUP+" as uxg inner join "+Comuns.NOME_TB_GRUPOS+" as g on g.id=uxg.fk_grupo WHERE uxg.fk_usuario= ? and g.cod = ?");
		statement.setInt(1,  this.getId());
		statement.setString(2,  codGroup);
		ResultSet set = statement.executeQuery();
		
			if( set.next()){
			
			statement.close();
			return true;
			}
		
		statement.close();
		}
		catch (SQLException e) {
		
		JOptionPane.showMessageDialog(null, "Não foi possível verificar as permissões do usuário.", "ERRO", JOptionPane.ERROR_MESSAGE);		
		return false;
		}	
		
	return false;
	}

	
	
	

	
	public boolean temPermissao(Connection connector, String codPermission, String permissionTo){
	
	if(connector == null || codPermission == null || codPermission.length() == 0)
	return false;

	boolean control = false;	
	
		try {
		
		PreparedStatement statement = (PreparedStatement) connector.prepareStatement("SELECT g.cod FROM "+Comuns.NOME_TB_GRUPOS+" as g inner join "+Comuns.NOME_TB_USER_GRUP+" as uxg on g.id=uxg.fk_grupo WHERE uxg.fk_usuario= ?");
		statement.setInt(1,  this.getId());
		ResultSet set = statement.executeQuery();	
		
			while( set.next()){
			
			if(set.getString(1) == null || set.getString(1).length() == 0)
			continue;
			
				if(set.getString(1).compareTo(Comuns.COD_GRUPO_ADMIN) == 0){
				
				control = true;
				break;
				}	
			}
		
			if(!control){
		
			statement = (PreparedStatement) connector.prepareStatement("SELECT gxa.valor, a.tipo FROM "+Comuns.NOME_TB_GRUP_ACESS+" as gxa inner join "+Comuns.NOME_TB_ACESSOS+" as a on a.id=gxa.fk_acesso WHERE gxa.fk_grupo IN (select fk_grupo from "+Comuns.NOME_TB_USER_GRUP+" where fk_usuario=?) and a.cod = ?");
			statement.setInt(1,  this.getId());
			statement.setString(2,  codPermission);
			set = statement.executeQuery();
	
				while( set.next()){
				
				if(set.getString(1) == null || 	set.getString(1).length() == 0)
				continue;
					
					if(set.getString(1).compareTo("VER") == 0 ||
						set.getString(1).compareTo("EXCLUIR") == 0 ||
						set.getString(1).compareTo("EDITAR") == 0 ||
						set.getString(1).compareTo("SIM") == 0 ||
						set.getString(1).compareTo("NAO") == 0){
				
						if(set.getString(2).compareTo("VER_ED_REM") == 0){
						
							if(set.getString(1).compareTo("VER") == 0){
								
								if(permissionTo.compareTo("VER") == 0){	
								control = true;
								break;
								}
							}
							
							if(set.getString(1).compareTo("EDITAR") == 0){
								
								if(permissionTo.compareTo("VER") == 0 || permissionTo.compareTo("EDITAR") == 0){	
								control = true;
								break;
								}
							}
							
							if(set.getString(1).compareTo("EXCLUIR") == 0){
								
								if(permissionTo.compareTo("VER") == 0 || permissionTo.compareTo("EDITAR") == 0 || permissionTo.compareTo("EXCLUIR") == 0){	
								control = true;
								break;
								}
							}
						}
						else if(set.getString(2).compareTo("SIM_NAO") == 0){
							
							if(set.getString(1).compareTo("SIM") == 0 && permissionTo.compareTo("SIM") == 0){
								
							control = true;
							break;
							}
						}
					}
				}	
			}
			
		statement.close();	
		}
		catch (SQLException e) {
		
		JOptionPane.showMessageDialog(null, "Não foi possível verificar as permissões do usuário.", "ERRO", JOptionPane.ERROR_MESSAGE);		
		return false;
		}	
		
	return control;
	}


	

	
	
	
	
	
	
	
	
	
	
	
}
