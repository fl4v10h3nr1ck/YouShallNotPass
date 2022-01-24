package vocenaovaipassar.forms.grupos;


import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;





import java.sql.Connection;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import vocenaovaipassar.beans.Grupo;
import vocenaovaipassar.beans.Usuario;
import vocenaovaipassar.componentes.Dialogo;
import vocenaovaipassar.dao.GruposDAO;
import vocenaovaipassar.geral.Comuns;



public class FormAddUsuarioEmGrupo   extends Dialogo{


private static final long serialVersionUID = 1L;

private GruposDAO dao;	


private JTable tb_grupos;
private JTable tb_usuarios;
private JTable tb_membros;

public DefaultTableModel modelo_grupos;
public DefaultTableModel modelo_usuarios;
public DefaultTableModel modelo_membros;





	public FormAddUsuarioEmGrupo(Connection conector){
		
	super("Adicionar Usuário à Grupo", 900, 380);
		
	this.dao = new GruposDAO(conector);
		
	addComponentes();	
	}
	
	
	
	
	
	public void addComponentes(){
	
		
	GridBagConstraints cons = new GridBagConstraints();  
		
	cons.fill = GridBagConstraints.BOTH;
	cons.anchor = GridBagConstraints.CENTER;
	cons.weighty  = 1;
	cons.weightx  = 1;
	cons.insets = new Insets(0, 0, 0, 0);
	cons.gridwidth  = GridBagConstraints.REMAINDER;		
	JPanel p_fundo = new JPanel();
	p_fundo.setBackground(Color.white);	 
	p_fundo.setLayout( new GridBagLayout());
	this.add(p_fundo, cons);
	
	cons.gridwidth  = 1;	
	cons.weightx  = 0.4;
	JPanel p_grupo = new JPanel();
	p_grupo.setBackground(Color.white);	 
	p_grupo.setLayout( new GridBagLayout());
	p_fundo.add(p_grupo, cons);
	p_grupo.setBorder(BorderFactory.createTitledBorder("Grupos Cadastrados"));  
	
	
	cons.weightx  = 0.28;
	JPanel p_membros = new JPanel();
	p_membros.setBackground(Color.white);	 
	p_membros.setLayout( new GridBagLayout());
	p_fundo.add(p_membros, cons);
	p_membros.setBorder(BorderFactory.createTitledBorder("Membros do Grupo"));  
	
	
	cons.weightx  = 0.02;
	JPanel p_bt_add = new JPanel();
	p_bt_add.setBackground(Color.white);	 
	p_bt_add.setLayout( new GridBagLayout());
	p_fundo.add(p_bt_add, cons);
	
	cons.gridwidth  = GridBagConstraints.REMAINDER;
	cons.weightx  = 0.3;
	JPanel p_usuarios = new JPanel();
	p_usuarios.setBackground(Color.white);	 
	p_usuarios.setLayout( new GridBagLayout());
	p_fundo.add(p_usuarios, cons);
	p_usuarios.setBorder(BorderFactory.createTitledBorder("Usuários Disponíveis"));  
	
	
	cons.fill = GridBagConstraints.BOTH;
	cons.anchor = GridBagConstraints.CENTER;
	cons.weighty  = 1;
	cons.weightx  = 1;
	cons.gridwidth  = GridBagConstraints.REMAINDER;		
	
	tb_grupos = new JTable();
	tb_grupos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	
	modelo_grupos = new DefaultTableModel( null, new String[]{"ID", "NOME"}){  
		private static final long serialVersionUID = 1L;
			@Override
			public boolean isCellEditable(int row, int col ){  
			            
			return false;
			}
		};
		
	tb_grupos.setModel( modelo_grupos);
	tb_grupos.getColumnModel().getColumn(0).setPreferredWidth(50);
	tb_grupos.getColumnModel().getColumn(1).setPreferredWidth(350);
	p_grupo.add(new JScrollPane( tb_grupos), cons);

	
	
	tb_membros = new JTable();
	tb_membros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	modelo_membros = new DefaultTableModel( null, new String[]{"ID", "MEMBRO"}){  
		private static final long serialVersionUID = 1L;
			@Override
			public boolean isCellEditable(int row, int col ){  
			            
			return false;
			}
		}; 
	tb_membros.setModel( modelo_membros);
	tb_membros.getColumnModel().getColumn(0).setPreferredWidth(50);
	tb_membros.getColumnModel().getColumn(1).setPreferredWidth(250);
	

	p_membros.add(new JScrollPane( tb_membros), cons);
	

	cons.fill = GridBagConstraints.NONE;
	cons.anchor = GridBagConstraints.EAST;
	cons.weighty = 0;
	cons.weightx = 0;
	cons.insets = new Insets(0, 0, 0, 0);
	JButton btRemoveMember = new JButton("Remover Membro");
	p_membros.add(btRemoveMember, cons);

	
	cons.anchor = GridBagConstraints.CENTER;
	JButton btAdd = new JButton("<< Adicionar <<");
	p_bt_add.add(btAdd, cons);
		
	
	tb_usuarios = new JTable();
	tb_usuarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	modelo_usuarios = new DefaultTableModel( null, new String[]{"ID", "USUÁRIO"}){  
		private static final long serialVersionUID = 1L;
			@Override
			public boolean isCellEditable(int row, int col ){  
			            
			return false;
			}
		}; 
	tb_usuarios.setModel( modelo_usuarios);
	tb_usuarios.getColumnModel().getColumn(0).setPreferredWidth(50);
	tb_usuarios.getColumnModel().getColumn(1).setPreferredWidth(250);
	
	cons.fill = GridBagConstraints.BOTH;
	cons.weighty  = 1;
	cons.weightx  = 1;
	p_usuarios.add(new JScrollPane( tb_usuarios), cons);
	
	
	
	
		tb_grupos.addMouseListener(new MouseAdapter(){
		@Override
		public void mouseClicked(MouseEvent e) {
		
		int index = tb_grupos.getSelectedRow();
			
		if(index>= 0)
		atualizaInfoDeMembros(Integer.parseInt((String)tb_grupos.getValueAt(index, 0)));
		
		}}); 


	
		btRemoveMember.addActionListener( new ActionListener(){
		@Override
		public void actionPerformed( ActionEvent event ){
		
		int index_membro = tb_membros.getSelectedRow();
			
			if(index_membro < 0){
				
			JOptionPane.showMessageDialog(null, "Selecione uma linha da tabela de membros para exclusão.", "ERRO", JOptionPane.ERROR_MESSAGE);		
			return;
			}
					
		int index_grupo = tb_grupos.getSelectedRow();
			
			if(index_grupo < 0){
			
			JOptionPane.showMessageDialog(null, "Selecione uma linha da tabela de grupos.", "ERRO", JOptionPane.ERROR_MESSAGE);		
			return;
			}
		
			if(tb_membros.getValueAt(index_membro, 1).toString().compareTo(Comuns.NOME_USUARIO_ADMIN)==0){
				
			JOptionPane.showMessageDialog(null, "O usuário ADMINISTRADOR não pode ser removido do grupo de ADMINISTRADORES.", "ERRO", JOptionPane.ERROR_MESSAGE);		
			return;	
			}
			else{
				
			int id_grupo = Integer.parseInt((String)tb_grupos.getValueAt(index_grupo, 0));
			int id_membro = Integer.parseInt((String)tb_membros.getValueAt(index_membro, 0));
				
			if(dao.removerMembroDeGrupo(id_grupo, id_membro))	
			atualizaInfoDeMembros(id_grupo);
			}
		}});
	
	
	

		btAdd.addActionListener( new ActionListener(){		
		@Override
		public void actionPerformed( ActionEvent event ){
		
		int index_usuarios = tb_usuarios.getSelectedRow();
			
			if(index_usuarios < 0){
				
			JOptionPane.showMessageDialog(null, "Selecione uma linha da tabela de usuários disponíveis.", "ERRO", JOptionPane.ERROR_MESSAGE);		
			return;
			}
					
		int index_group = tb_grupos.getSelectedRow();
			
			if(index_group < 0){
			
			JOptionPane.showMessageDialog(null, "Selecione uma linha da tabela de grupos.", "ERRO", JOptionPane.ERROR_MESSAGE);		
			return;
			}
			
			
		int id_grupo = Integer.parseInt((String)tb_grupos.getValueAt(index_group, 0));
		int id_usuario = Integer.parseInt((String)tb_usuarios.getValueAt(index_usuarios, 0));
				
		if(dao.addUsuarioAGrupo(id_grupo, id_usuario))	
		atualizaInfoDeMembros(id_grupo);	
		}});
	
		
		
	this.atualizaInfoDeGrupos();
	}
	

	

	
	private void atualizaInfoDeGrupos(){
		
	this.modelo_grupos.setNumRows(0);	
	
	GruposDAO  dao = new GruposDAO(this.dao.conector);
	
	List<Grupo> grupos  = dao.getGrupos();
		
	String[] aux = new String[2];
		
		for(Grupo grupo: grupos){

		aux[0] = ""+grupo.getId();			
		aux[1] = grupo.getNome();	
			
		this.modelo_grupos.addRow(aux);
		}
	}
	
	
	
	
	
	public void atualizaInfoDeUsuarios(int idGroup){
			
	this.modelo_usuarios.setNumRows(0);	
		
	List<Usuario> usuarios  = dao.getUsuariosForaDeGrupo(idGroup);
				
	String[] aux = new String[2];
				
		for(Usuario usuario: usuarios){

		aux[0] = ""+usuario.getId();			
		aux[1] = usuario.getUsuario();	
					
		this.modelo_usuarios.addRow(aux);
		}	
	}

	
	
	
	public void atualizaInfoDeMembros(int idGroup){
		
	this.modelo_membros.setNumRows(0);	
			
	List<Usuario> usuarios  = dao.getUsuariosDeGrupo(idGroup);
			
	String[] aux = new String[2];
			
		for(Usuario usuario: usuarios){

		aux[0] = ""+usuario.getId();			
		aux[1] = usuario.getUsuario();	
				
		this.modelo_membros.addRow(aux);
		}
	
	this.atualizaInfoDeUsuarios(idGroup);
	}
	
	
	
	
	public boolean acaoPrincipal(){return true;}
	
	
	
}
