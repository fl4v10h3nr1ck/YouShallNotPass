package voceNaoVaiPassar.dao;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import voceNaoVaiPassar.Comuns;
import voceNaoVaiPassar.beans.Usuario;

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
		+ "?,?,?,?,?)");   
				
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
			
			stmt.executeUpdate("delete from "+Comuns.NOME_TB_USER_GRUP+" where fk_usuario = "+id); 
			
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

	
	
	
	
/*	
	
	
	
	
	public void addUserValues(int id, JTextField name, JTextField email, @SuppressWarnings("rawtypes") JComboBox status){
		
		try {
			
		PreparedStatement statement = (PreparedStatement) this.conector.prepareStatement("SELECT usuario, email, status  FROM usuarios where id= ?");
		statement.setInt(1,  id);
		ResultSet set = statement.executeQuery();
			
			if( set.next() ){
			
			name.setText(set.getString(1));
			email.setText(set.getString(2));	
			status.setSelectedItem(set.getString(3));
			
			}
		
		statement.close();
		}
		catch (SQLException e) {
		JOptionPane.showMessageDialog(null, "Um erro ocorreu ao tentar recuperar as informações do usuário.", "ERRO", JOptionPane.ERROR_MESSAGE);		
		return;
		}	
	}
	
	
	
	
	
	private boolean valuesNewUserAccepted( PreparedStatement statement, JTextField name, JTextField email, int exceptID) throws SQLException{
		
	statement = (PreparedStatement) this.conector.prepareStatement("SELECT usuario, email FROM usuarios WHERE (usuario= ? OR email = ?)"+(exceptID>0?" and id <> "+exceptID:""));
	statement.setString(1,  name.getText());
	statement.setString(2,  email.getText());
	ResultSet set = statement.executeQuery();
		
		if( set.next() ){
			
			if(set.getString(1).compareTo(name.getText()) == 0){	
				
			JOptionPane.showMessageDialog(null, "Já existe um usuário com o nome informado.", "ERRO", JOptionPane.ERROR_MESSAGE);
			LoginConfiguration.textFieldErroMode(name);
			return false;	
			}
				
			if(set.getString(2).compareTo(email.getText()) == 0){	
					
			JOptionPane.showMessageDialog(null, "Já existe um usuário com o e-mail informado.", "ERRO", JOptionPane.ERROR_MESSAGE);
			LoginConfiguration.textFieldErroMode(email);
			return false;	
			}
		}
		
	return true;	
	}
	
	
	
	
	
	
	
	
	
	

	

	public void getAllAcesses(List<JCheckBox>	accessChecks, List<Integer>	accessIDs, int id){
	
		try {
			
		PreparedStatement  statement = (PreparedStatement) this.conector.prepareStatement("SELECT id, cod, nome FROM acessos where id NOT IN (select fk_acesso from grupos_x_acessos where fk_grupo=?)");
		statement.setInt(1,  id);
		ResultSet set = statement.executeQuery();
			
			while( set.next()){
			
			accessIDs.add(set.getInt(1));
			accessChecks.add(new JCheckBox("("+set.getString(2)+") "+set.getString(3)));
			}
			
		}
		catch (SQLException e) {
		JOptionPane.showMessageDialog(null, "Um erro ocorreu ao recuperar os dados dos usuários.", "ERRO", JOptionPane.ERROR_MESSAGE);		
		return;
		}	
		
	return;
	}
	
	
	
	
	
	
	public boolean newGroup(JTextField name, JTextField cod, JTextField description){
		
		try {
			
		PreparedStatement statement	= null;
				
		if(!this.valuesNewGroupAccepted(statement, name, cod, 0))
		return false;	
							
		statement = (PreparedStatement) this.conector.prepareStatement("INSERT INTO grupos(nome, cod, descricao, data_criacao) VALUES (?,?,?,?)");   			
		statement.setString( 1,  name.getText());  
		statement.setString( 2,  cod.getText()); 
		statement.setString( 3,  description.getText()); 
		statement.setString( 4,  LoginConfiguration.getDataAtual()); 
		statement.executeUpdate(); 
				

		}
		catch (SQLException e) {
				
		JOptionPane.showMessageDialog(null, "Um erro ocorreu ao salvar as informações.", "ERRO", JOptionPane.ERROR_MESSAGE);		
		return false;	
		}		
			
		
	return true;
	}
	
	
	
	
	
	private boolean valuesNewGroupAccepted( PreparedStatement statement, JTextField name, JTextField cod, int exceptID) throws SQLException{
		
	statement = (PreparedStatement) this.conector.prepareStatement("SELECT nome, cod FROM grupos WHERE (nome= ? OR cod = ?)"+(exceptID>0?" and id <> "+exceptID:""));
	statement.setString(1,  name.getText());
	statement.setString(2,  cod.getText());
	ResultSet set = statement.executeQuery();
		
		if( set.next() ){
			
			if(set.getString(1).compareTo(name.getText()) == 0){	
				
			JOptionPane.showMessageDialog(null, "Já existe um grupo com o nome informado.", "ERRO", JOptionPane.ERROR_MESSAGE);
			LoginConfiguration.textFieldErroMode(name);
			return false;	
			}
				
			if(set.getString(2).compareTo(cod.getText()) == 0){	
					
			JOptionPane.showMessageDialog(null, "Já existe um grupo com o código informado.", "ERRO", JOptionPane.ERROR_MESSAGE);
			LoginConfiguration.textFieldErroMode(cod);
			return false;	
			}
		}
		
	return true;	
	}
	

	
	
	
	public void getGroupAccesses( int id, List<Access> accesses){
		
		try {
			
		PreparedStatement  statement = (PreparedStatement) this.conector.prepareStatement("SELECT g.valor, a.id, a.cod, a.nome, a.tipo FROM grupos_x_acessos as g inner join acessos as a on g.fk_acesso = a.id where g.fk_grupo ="+id);
		ResultSet set = statement.executeQuery();
			
		while( set.next())
		accesses.add(new Access(set.getInt(2), set.getString(3), set.getString(4), set.getString(5), set.getString(1)));
					
		}
		catch (SQLException e) {
					
		JOptionPane.showMessageDialog(null, "Um erro ocorreu ao tentar recuperar as informações.", "ERRO", JOptionPane.ERROR_MESSAGE);		
		return;	
		}		

	}
	
	
	


	public boolean setGroupAccesses( int id, List<Access> accesses){
		
		try {
		PreparedStatement  statement = null;
		
			for(Access aux: accesses)	{
			
			statement = (PreparedStatement) this.conector.prepareStatement("UPDATE grupos_x_acessos SET valor = ? where fk_grupo = ? and fk_acesso= ?");   			
			statement.setString( 1,  aux.combo.getSelectedItem().toString().compareTo("...")==0?"":aux.combo.getSelectedItem().toString()); 
			statement.setInt( 2,  id); 
			statement.setInt( 3, aux.id); 
			statement.executeUpdate(); 
			}		
		}
		catch (SQLException e) {
					
		JOptionPane.showMessageDialog(null, "Um erro ocorreu ao salvar as informações.", "ERRO", JOptionPane.ERROR_MESSAGE);		
		return false;	
		}	
		
	return true;	
	}

	
	
	
	
	public boolean deleteGroupAccess( int idGroup, int idAccess){
		
		try {
			
		PreparedStatement  statement = null;
			
		statement = (PreparedStatement) this.conector.prepareStatement("delete from grupos_x_acessos where fk_grupo = ? and fk_acesso= ?");   			
		statement.setInt( 1,  idGroup); 
		statement.setInt( 2, idAccess); 
		statement.executeUpdate(); 
		
		}
		catch (SQLException e) {
						
		JOptionPane.showMessageDialog(null, "Um erro ocorreu ao salvar as informações.", "ERRO", JOptionPane.ERROR_MESSAGE);		
		return false;	
		}	
			
	return true;	
	}
	
	
	
	
	
	public boolean addAccess(List<JCheckBox>	accessChecks, List<Integer>	accessIDs, int id){
		
		try {
		
		PreparedStatement  statement  = null;
			
			for(int i= 0; i < accessChecks.size(); i++ )	{
			
				if(accessChecks.get(i).isSelected()){
				
				statement = (PreparedStatement) this.conector.prepareStatement("INSERT INTO grupos_x_acessos(fk_grupo, fk_acesso) VALUES (?,?)");
				statement.setInt(1,  id);
				statement.setInt(2,  accessIDs.get(i));
				statement.executeUpdate(); 
				}
			}		
		}
		catch (SQLException e) {
		JOptionPane.showMessageDialog(null, "Um erro ocorreu ao salvar as informações.", "ERRO", JOptionPane.ERROR_MESSAGE);		
		return false;
		}	
		
	return true;
	}
	
	
	
	
	
	public boolean deleteGroup(int idGroup){
		
		try {
			
		Statement stmt = conector.createStatement();

		stmt.executeUpdate("delete from grupos_x_acessos where fk_grupo = "+idGroup); 
		
		stmt.executeUpdate("delete from usuarios_x_grupos where fk_grupo = "+idGroup); 
		
		stmt.executeUpdate("delete from grupos where id = "+idGroup); 
		
		}
		catch (SQLException e) {
		JOptionPane.showMessageDialog(null, "Um erro ocorreu ao alterar as informações do grupo.", "ERRO", JOptionPane.ERROR_MESSAGE);		
		return false;
		}			
	
	return true;
	}
	
	
	

	
	public boolean AlterGroup(int idGroup, JTextField name, JTextField cod, JTextField description){
		
		try {
		
		PreparedStatement statement = null;
			
		if(!this.valuesNewGroupAccepted(statement, name, cod, idGroup))
		return false;	
		
		statement = (PreparedStatement) this.conector.prepareStatement("UPDATE grupos SET nome = ?, cod =?, descricao = ? WHERE id = ?" );
		statement.setString(1,  name.getText());
		statement.setString(2,  cod.getText());
		statement.setString(3,  description.getText());
		statement.setInt(4, idGroup);	
		statement.executeUpdate();	

		}
		catch (SQLException e) {
		JOptionPane.showMessageDialog(null, "Um erro ocorreu ao salvar as informações do grupo.", "ERRO", JOptionPane.ERROR_MESSAGE);		
		return false;
		}		
	
	return true;
	}
	
	
	

	
	public void addGroupValues(int idGroup, JTextField name, JTextField cod, JTextField description){
		
		try {
			
		PreparedStatement statement = (PreparedStatement) this.conector.prepareStatement("SELECT nome, cod, descricao  FROM grupos where id= ?");
		statement.setInt(1,  idGroup);
		ResultSet set = statement.executeQuery();
			
			if( set.next() ){
			
			name.setText(set.getString(1));
			cod.setText(set.getString(2));	
			description.setText(set.getString(3));	
			
			}
		
		statement.close();
		}
		catch (SQLException e) {
		JOptionPane.showMessageDialog(null, "Um erro ocorreu ao tentar recuperar as informações do grupo.", "ERRO", JOptionPane.ERROR_MESSAGE);		
		return;
		}	
	}
	
	

	

	public void getAllMembers(DefaultTableModel tableModelMembers, int idGroup){
	
		try {
			
		PreparedStatement  statement = (PreparedStatement) this.conector.prepareStatement("SELECT id, usuario FROM usuarios where id IN (select fk_usuario from usuarios_x_grupos where fk_grupo=?)");
		statement.setInt(1,  idGroup);
		ResultSet set = statement.executeQuery();
		
		String[] aux = new String[2];
			while( set.next()){
			
			aux[0] = String.valueOf(set.getInt(1));	
			aux[1] = set.getString(2);	

			tableModelMembers.addRow(aux);
			}
		}
		catch (SQLException e) {
		JOptionPane.showMessageDialog(null, "Um erro ocorreu ao recuperar os dados dos grupos.", "ERRO", JOptionPane.ERROR_MESSAGE);		
		return;
		}	
	}
	
	
	
	
	
	public void getAllUsersAvailables(DefaultTableModel tableModelUsers, int idGroup){
	
		try {
			
		PreparedStatement  statement = (PreparedStatement) this.conector.prepareStatement("SELECT id, usuario FROM usuarios where id NOT IN (select fk_usuario from usuarios_x_grupos where fk_grupo=?)");
		statement.setInt(1,  idGroup);
		ResultSet set = statement.executeQuery();
		
		String[] aux = new String[2];
			while( set.next()){
			
			aux[0] = String.valueOf(set.getInt(1));	
			aux[1] = set.getString(2);	

			tableModelUsers.addRow(aux);
			}
		}
		catch (SQLException e) {
		JOptionPane.showMessageDialog(null, "Um erro ocorreu ao recuperar os dados dos usuários.", "ERRO", JOptionPane.ERROR_MESSAGE);		
		return;
		}	
	}
	
	
	
	
	
	public boolean addUserToGroup(int idGroup, int idUser){
		
		try {
			
		PreparedStatement  statement  = (PreparedStatement) this.conector.prepareStatement("INSERT INTO usuarios_x_grupos(fk_grupo, fk_usuario) VALUES (?,?)");
		statement.setInt(1,  idGroup);
		statement.setInt(2,  idUser);
		statement.executeUpdate(); 
	
		}
		catch (SQLException e) {
		JOptionPane.showMessageDialog(null, "Um erro ocorreu ao gravar as informações.", "ERRO", JOptionPane.ERROR_MESSAGE);		
		return false;
		}	
			
	return true;		
	}
	
	
	
	
	
	public boolean removeUserGroup(int idGroup, int idUser){
	
	
		try {
		
		int dialogButton = JOptionPane.YES_NO_OPTION;
		int dialogResult = JOptionPane.showConfirmDialog(null, "Você tem certeza que deseja excluir este membro do grupo?", "Confirmação",dialogButton);
					
			if(dialogResult==0){	
			
			Statement stmt = conector.createStatement();
			stmt.executeUpdate("delete from usuarios_x_grupos where fk_grupo = "+idGroup+" and fk_usuario="+idUser); 
			}
		}
		catch (SQLException e) {
		JOptionPane.showMessageDialog(null, "Um erro ocorreu ao alterar as informações do grupo.", "ERRO", JOptionPane.ERROR_MESSAGE);		
		return false;
		}			
		
	return true;
	}	
		
		
		
*/
	
}

