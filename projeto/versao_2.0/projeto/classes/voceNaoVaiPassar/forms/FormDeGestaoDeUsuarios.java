

package voceNaoVaiPassar.forms;


import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import voceNaoVaiPassar.Comuns;
import voceNaoVaiPassar.beans.Usuario;
import voceNaoVaiPassar.componentes.Dialogo;
import voceNaoVaiPassar.dao.UsuariosDAO;
import voceNaoVaiPassar.forms.grupos.FormAddUsuarioEmGrupo;
import voceNaoVaiPassar.forms.grupos.FormGestaoDeGrupos;
import voceNaoVaiPassar.forms.usuarios.FormAlterarUsuario;
import voceNaoVaiPassar.forms.usuarios.FormNovoUsuario;




public class FormDeGestaoDeUsuarios extends Dialogo{

	

private static final long serialVersionUID = 1L;


private UsuariosDAO dao;

public DefaultTableModel tableModel;
public JScrollPane scrollpane;


public JTable table;


public List<Usuario> lista_de_usuarios;



	public FormDeGestaoDeUsuarios( Connection   conector){
		
	super("Gestão de Usuários", 650, 450);
	
	this.dao = new UsuariosDAO(conector); 
	
	this.lista_de_usuarios = new ArrayList<Usuario>();
	
	addComponentes();
	}
		
	 

	
	public void addComponentes(){
		
	GridBagConstraints cons = new GridBagConstraints();  
		
	cons.fill = GridBagConstraints.BOTH;
	cons.anchor = GridBagConstraints.CENTER;
	cons.weighty  = 1;
	cons.weightx  = 1;
	cons.gridwidth  = GridBagConstraints.REMAINDER;	
	JPanel p_fundo = new JPanel();
	p_fundo.setBackground(Color.white);
	p_fundo.setLayout(new GridBagLayout());    
	this.add( p_fundo, cons);
	
	
	cons.insets = new Insets(2, 2, 2, 2);
	cons.fill = GridBagConstraints.HORIZONTAL;
	cons.weighty  = 0;
	cons.weightx  = 1;
	JPanel p_opcoes = new JPanel();
	p_opcoes.setBackground(Color.WHITE);
	p_opcoes.setLayout(new GridBagLayout());    
	p_fundo.add( p_opcoes, cons);

	cons.fill = GridBagConstraints.BOTH;
	cons.weighty  = 1;
	cons.weightx  = 1;
	cons.insets = new Insets(0, 0, 0, 0);
	JPanel p_tabela = new JPanel();
	p_tabela.setBackground(Color.white);
	p_tabela.setLayout(new GridBagLayout());    
	p_fundo.add( p_tabela, cons);
	p_tabela.setBorder(BorderFactory.createTitledBorder("Usuários Cadastrados"));  
	
	
	cons.fill = GridBagConstraints.NONE;
	cons.gridwidth  = 1;
	cons.weighty  = 0;
	cons.weightx  = 0;
	cons.insets = new Insets(2, 2, 0, 2);
	JButton newUser = new JButton(new ImageIcon(getClass().getResource("/icons/bt_add_user.png")));
	newUser.setToolTipText("Adicionar novo usuário"); 
	p_opcoes.add(newUser, cons);
	
	JButton alterUser = new JButton(new ImageIcon(getClass().getResource("/icons/bt_alter_user.png")));
	alterUser.setToolTipText("Alterar usuário selecionado."); 
	p_opcoes.add(alterUser, cons);
	
	JButton deleteUser = new JButton(new ImageIcon(getClass().getResource("/icons/bt_remove_user.png")));
	deleteUser.setToolTipText("Deletar usuário Selecionado."); 
	p_opcoes.add(deleteUser, cons);
	
	JButton ativaDesativa = new JButton(new ImageIcon(getClass().getResource("/icons/ativa_desativa.png")));
	ativaDesativa.setToolTipText("Ativar/desativar usuário Selecionado."); 
	p_opcoes.add(ativaDesativa, cons);
	
	
	JButton groupsBT = new JButton(new ImageIcon(getClass().getResource("/icons/bt_group.png")));
	groupsBT.setToolTipText("Gerir grupos de usuários.");
	p_opcoes.add(groupsBT, cons);
	
	JButton addUserToGroup = new JButton(new ImageIcon(getClass().getResource("/icons/bt_privilege.png")));
	addUserToGroup.setToolTipText("Adicionar ou remover usuários de grupos de acesso.");
	p_opcoes.add(addUserToGroup, cons);
	
	cons.fill = GridBagConstraints.HORIZONTAL;
	cons.gridwidth  = GridBagConstraints.REMAINDER;	
	cons.weightx  = 1;
	p_opcoes.add(new JLabel(""), cons);
	
	cons.fill = GridBagConstraints.NONE;
	cons.weightx  = 0;
	cons.gridwidth  = 1;
	cons.insets = new Insets(0, 2, 2, 2);
	p_opcoes.add(new JLabel("Novo"), cons);
	p_opcoes.add(new JLabel("Alterar"), cons);
	p_opcoes.add(new JLabel("Excluir"), cons);
	p_opcoes.add(new JLabel("Ativi/Desati."), cons);
	p_opcoes.add(new JLabel("Grupos"), cons);
	p_opcoes.add(new JLabel("Permissões"), cons);
	
	
	cons.fill = GridBagConstraints.HORIZONTAL;
	cons.gridwidth  = GridBagConstraints.REMAINDER;	
	cons.weightx  = 1;
	p_opcoes.add(new JLabel(""), cons);
	

	
	table = new JTable();
		tableModel = new DefaultTableModel( null, new String[]{"ID", "USUÁRIO", "E-MAIL", "STATUS", "CAD. EM", "ÚLT. ALTER."}){  
		private static final long serialVersionUID = 1L;
		@Override
		public boolean isCellEditable(int row, int col ){  
			            
		return false;
		}}; 
	table.setModel( tableModel);
	table.setRowHeight(20);    

	table.getColumnModel().getColumn(0).setPreferredWidth(50);
	table.getColumnModel().getColumn(1).setPreferredWidth(150);
	table.getColumnModel().getColumn(2).setPreferredWidth(250);
	table.getColumnModel().getColumn(3).setPreferredWidth(100);
	table.getColumnModel().getColumn(4).setPreferredWidth(150);
	table.getColumnModel().getColumn(5).setPreferredWidth(150);
	
	
	
		table.addMouseListener(new MouseAdapter(){

			@Override
			public void mouseClicked(MouseEvent e) {
			
				if(e.getClickCount()==2){
				
				int index = table.getSelectedRow();
					
					if(index>= 0 && lista_de_usuarios.size()>index){
					
						if(lista_de_usuarios.get(index)!=null){
							
						FormAlterarUsuario newAlterForm = new FormAlterarUsuario(dao, lista_de_usuarios.get(index));
						newAlterForm.mostrar();
						atualizaInfoDeUsuarios();
						}
						else
						JOptionPane.showMessageDialog(null, "Dados do usuários estão inacessíveis.", "ERRO", JOptionPane.ERROR_MESSAGE);		
							
					}
					else
					JOptionPane.showMessageDialog(null, "Selecione uma linha da tabela de usuários para alteração.", "ERRO", JOptionPane.ERROR_MESSAGE);		
					
				}
			
			}	
		}); 
	

	
	cons.gridwidth  = GridBagConstraints.REMAINDER;
	cons.insets = new Insets(0, 0, 0, 0);
	cons.fill = GridBagConstraints.BOTH;
	cons.weighty  = 1;
	cons.weightx  = 1;
	p_tabela.add(new JScrollPane( table), cons);
	
	


		newUser.addActionListener( new ActionListener(){
		@Override
		public void actionPerformed( ActionEvent event ){
	    	
		FormNovoUsuario newUserForm = new FormNovoUsuario(dao);
		newUserForm.mostrar();	
		
		atualizaInfoDeUsuarios();
		}});
	
	
	
		alterUser.addActionListener( new ActionListener(){
		@Override
		public void actionPerformed( ActionEvent event ){
	    	
		int index = table.getSelectedRow();
			
			if(index>= 0 && lista_de_usuarios.size()>index){
			
				if(lista_de_usuarios.get(index)!=null){
					
				FormAlterarUsuario newAlterForm = new FormAlterarUsuario(dao, lista_de_usuarios.get(index));
				newAlterForm.mostrar();
				atualizaInfoDeUsuarios();
				}
				else
				JOptionPane.showMessageDialog(null, "Os dados do usuário estão inacessíveis.", "ERRO", JOptionPane.ERROR_MESSAGE);		
					
			}
			else
			JOptionPane.showMessageDialog(null, "Selecione uma linha da tabela de usuários para alteração.", "ERRO", JOptionPane.ERROR_MESSAGE);		
			
		
		}});
	
	
	
		
		deleteUser.addActionListener( new ActionListener(){
		@Override
		public void actionPerformed( ActionEvent event ){
	    	
		int index = table.getSelectedRow();
			
			if(index>= 0 && lista_de_usuarios.size()>index){
				
				if(lista_de_usuarios.get(index)!=null){
				
					if(lista_de_usuarios.get(index).getUsuario()!=null && 
							lista_de_usuarios.get(index).getUsuario().compareTo(Comuns.NOME_USUARIO_ADMIN)==0){
						
					JOptionPane.showMessageDialog(null, "O usuário administrador não deve ser excluído.", "ERRO", JOptionPane.ERROR_MESSAGE);		
					return;	
					}
					
				if(dao.deleta(lista_de_usuarios.get(index).getId()))
				atualizaInfoDeUsuarios();
				}
				else
				JOptionPane.showMessageDialog(null, "Os dados do usuário estão inacessíveis.", "ERRO", JOptionPane.ERROR_MESSAGE);		
				
			}
			else
			JOptionPane.showMessageDialog(null, "Selecione uma linha da tabela de usuários para remoção.", "ERRO", JOptionPane.ERROR_MESSAGE);		
			
		}});
	
	
		

		ativaDesativa.addActionListener( new ActionListener(){
		@Override
		public void actionPerformed( ActionEvent event ){
	    	
			
		int index = table.getSelectedRow();
			
			if(index>= 0 && lista_de_usuarios.size()>index){
				
				if(lista_de_usuarios.get(index)!=null){
				
					if(lista_de_usuarios.get(index).getUsuario()!=null && 
							lista_de_usuarios.get(index).getUsuario().compareTo(Comuns.NOME_USUARIO_ADMIN)==0){
						
					JOptionPane.showMessageDialog(null, "O usuário administrador não deve ser desativado.", "ERRO", JOptionPane.ERROR_MESSAGE);		
					return;	
					}
					
				if(dao.ativaOuDesativa(lista_de_usuarios.get(index)))
				atualizaInfoDeUsuarios();
				}
				else
				JOptionPane.showMessageDialog(null, "Os dados do usuário estão inacessíveis.", "ERRO", JOptionPane.ERROR_MESSAGE);		
					
			}
			else
			JOptionPane.showMessageDialog(null, "Selecione uma linha da tabela de usuários para ativar ou desativar.", "ERRO", JOptionPane.ERROR_MESSAGE);		
			
		
		}});
	
	
		
		
	
	
		groupsBT.addActionListener( new ActionListener(){
		@Override
		public void actionPerformed( ActionEvent event ){
	    
		FormGestaoDeGrupos groupForm = new FormGestaoDeGrupos(dao.conector);
		groupForm.mostrar();
		}
		});
	
	
	
	
		addUserToGroup.addActionListener( new ActionListener(){
		@Override
		public void actionPerformed( ActionEvent event ){
	    
		FormAddUsuarioEmGrupo form = new FormAddUsuarioEmGrupo(dao.conector);
		form.mostrar();		
		}
	});
	
	

	this.atualizaInfoDeUsuarios();
	}

	

	
	
	public void atualizaInfoDeUsuarios(){
		
	this.tableModel.setNumRows(0);	
	this.lista_de_usuarios.clear();
	
	this.lista_de_usuarios.addAll(this.dao.getUsuarios());	
	
	String[] aux = new String[6];
				
		for( Usuario usuario: this.lista_de_usuarios){
				
		aux[0] = String.valueOf(usuario.getId());	
		aux[1] = usuario.getUsuario();	
		aux[2] = usuario.getEmail();	
		aux[3] = "<html><font color="+(usuario.getStatus()!=null &&usuario.getStatus().compareTo("ATIVO")==0?"green":"red")+">"+usuario.getStatus()+"</font></html>";	
		aux[4] = usuario.getData_cadastro();
		aux[5] = usuario.getData_ult_alter()!=null && usuario.getData_ult_alter().length()>0?usuario.getData_ult_alter():"";	
				
		tableModel.addRow(aux);
		}
	}
	
	

	
	
	public boolean acaoPrincipal() {return true;}
	
	
	
	
}
