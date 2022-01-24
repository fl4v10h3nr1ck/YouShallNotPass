package voceNaoVaiPassar.forms.grupos;




import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import voceNaoVaiPassar.Comuns;
import voceNaoVaiPassar.beans.Grupo;
import voceNaoVaiPassar.beans.PermissaoDeGrupo;
import voceNaoVaiPassar.componentes.Dialogo;
import voceNaoVaiPassar.componentes.Format_TextField_MaxComp;
import voceNaoVaiPassar.dao.GruposDAO;
import voceNaoVaiPassar.dao.PermissaoDAO;





public class FormGestaoDeGrupos extends Dialogo{

	
private static final long serialVersionUID = 1L;


private GruposDAO grupoDAO;
private PermissaoDAO permissaoDAO;


private JTextField tf_nome;
private JTextField tf_cod;
private JTextField tf_descricao;


public DefaultTableModel modelo_de_tabela;
public JTable tabela_de_grupos;

public List<PermissaoDeGrupo> lista_de_permissoes;

public JPanel p_de_permissoes;


private JButton bt_salvar;
private JButton bt_nova_permissao;
private JButton bt_deleta_grupo;


public List<Grupo> lista_de_grupos;


	public FormGestaoDeGrupos( Connection   connector){
		
	super("Gestão de Grupos de Usuários", 780, 500);
	
	this.grupoDAO = new GruposDAO(connector);
	this.permissaoDAO = new PermissaoDAO(connector);
	
	lista_de_permissoes = new ArrayList<PermissaoDeGrupo>();
	lista_de_grupos = new ArrayList<Grupo>();
	
	
	
	addComponentes();
	}
		
	 	

	
	
	public void addComponentes(){

		
	GridBagConstraints cons = new GridBagConstraints();  	
	cons.fill = GridBagConstraints.BOTH;
	cons.weighty  = 1;
	cons.weightx  = 1;
	cons.gridwidth  = GridBagConstraints.REMAINDER;	
		
	JPanel p_fundo = new JPanel();	
	p_fundo.setBackground(Color.white);
	p_fundo.setLayout( new GridBagLayout());
	add( p_fundo, cons);
	
	
	cons.fill = GridBagConstraints.HORIZONTAL;
	cons.weighty  = 0;
	JPanel p_novo_grupo = new JPanel();	
	p_novo_grupo.setBackground(Color.white);
	p_novo_grupo.setLayout( new GridBagLayout());
	p_fundo.add( p_novo_grupo, cons);
	p_novo_grupo.setBorder(BorderFactory.createTitledBorder("Novo Grupo"));  
	
	
	cons.gridwidth  = 1;
	cons.weightx = 0.3;
	cons.insets = new Insets(2, 2, 0, 2);
	p_novo_grupo.add(new JLabel("<html>Nome:<font color=red>*</font></html>"), cons);
	cons.weightx = 0.2;
	p_novo_grupo.add(new JLabel("<html>Cod:<font color=red>*</font></html>"), cons);
	cons.weightx = 0.5;
	cons.gridwidth  = GridBagConstraints.REMAINDER;
	p_novo_grupo.add(new JLabel("Descrição:"), cons);

	cons.weightx = 0.3;
	cons.gridwidth = 1;
	cons.insets = new Insets(2, 2, 2, 2);
	this.tf_nome  = new JTextField();
	this.tf_nome.setDocument( new Format_TextField_MaxComp(  150, this.tf_nome  ) );
	p_novo_grupo.add(this.tf_nome, cons);
	
	cons.weightx = 0.2;
	this.tf_cod  = new JTextField();
	this.tf_cod.setDocument( new Format_TextField_MaxComp(  10, this.tf_cod  ) );
	p_novo_grupo.add(this.tf_cod, cons);
	
	cons.weightx = 0.45;
	this.tf_descricao  = new JTextField();
	this.tf_descricao.setDocument( new Format_TextField_MaxComp(  200, this.tf_descricao  ) );
	p_novo_grupo.add(this.tf_descricao, cons);
	
	cons.weightx = 0.05;
	cons.gridwidth  = GridBagConstraints.REMAINDER;
	JButton bt_novo_grupo = new JButton("Novo Grupo");
	p_novo_grupo.add(bt_novo_grupo, cons);

	
	cons.fill = GridBagConstraints.BOTH;
	cons.weighty = 1;
	cons.weightx = 1;
	JPanel p_fundo_2 = new JPanel();	
	p_fundo_2.setBackground(Color.white);
	p_fundo_2.setLayout( new GridBagLayout());
	p_fundo.add( p_fundo_2, cons);
	p_fundo_2.setBorder(BorderFactory.createTitledBorder("Permissões de Grupo"));  
	
	
	
	cons.gridwidth = 1;
	cons.weightx = 0.8;
	p_fundo_2.add(new JScrollPane(this.tabela_de_grupos = new JTable()), cons);
	
	
		modelo_de_tabela = new DefaultTableModel( null, new String[]{"ID", "COD", "GRUPO"}){  
		private static final long serialVersionUID = 1L;
		@Override
			public boolean isCellEditable(int row, int col ){  
				            
			return false;
			}
		}; 
	
	tabela_de_grupos.setModel( modelo_de_tabela);
	tabela_de_grupos.setRowHeight(20); 
	
	
	cons.weightx = 0.2;
	cons.gridwidth  = GridBagConstraints.REMAINDER;
	JPanel p_fundo_3 = new JPanel();	
	p_fundo_3.setBackground(Color.white);
	p_fundo_3.setLayout( new GridBagLayout());
	p_fundo_2.add( p_fundo_3, cons);
	
	
	cons.fill = GridBagConstraints.BOTH;
	cons.weighty = 1;
	cons.weightx = 1;
	p_fundo_3.add(new JScrollPane(this.p_de_permissoes = new JPanel()), cons);
	this.p_de_permissoes.setLayout(new GridBagLayout());
	
	cons.fill = GridBagConstraints.HORIZONTAL;
	cons.weightx = 0.1;
	cons.weighty = 0;
	cons.gridwidth = 1;
	bt_deleta_grupo = new JButton("Deletar Grupo");
	p_fundo_3.add(bt_deleta_grupo, cons);

	cons.weightx = 0.1;
	bt_nova_permissao = new JButton("Nova Permissão");
	p_fundo_3.add(bt_nova_permissao, cons);
	
	bt_salvar = new JButton("Salvar Alterações");
	p_fundo_3.add(bt_salvar, cons);
	
	
	
		bt_novo_grupo.addActionListener( new ActionListener(){
		@Override
		public void actionPerformed( ActionEvent event ){
	    	
			if(acaoPrincipal()){	
					
			JOptionPane.showMessageDialog(null, "Grupo cadastrado com sucesso.", "SUCESSO", JOptionPane.INFORMATION_MESSAGE);			
			atualizaTabelaDeGrupos();
			
			tf_nome.setText("");
			tf_cod.setText("");
			tf_descricao.setText("");
			}
		}});
	
	
		
		bt_novo_grupo.addKeyListener(new KeyAdapter(){
		@Override	
		public void keyPressed(KeyEvent ek){
				
			if(acaoPrincipal()){	
					
			JOptionPane.showMessageDialog(null, "Grupo cadastrado com sucesso.", "SUCESSO", JOptionPane.INFORMATION_MESSAGE);			
			atualizaTabelaDeGrupos();
					
			tf_nome.setText("");
			tf_cod.setText("");
			tf_descricao.setText("");
			}
		}});
		
		
		
	
	
		this.bt_deleta_grupo.addActionListener( new ActionListener(){
		@Override
		public void actionPerformed( ActionEvent event ){
	   	
		int index = tabela_de_grupos.getSelectedRow();
			
			if(index >= 0 && lista_de_grupos.size()>index){	
			
				if(lista_de_grupos.get(index)!=null && lista_de_grupos.get(index).getId()>0){
				
					if(lista_de_grupos.get(index).getCod()!=null && lista_de_grupos.get(index).getCod().compareTo(Comuns.COD_GRUPO_ADMIN)==0){
						
					JOptionPane.showMessageDialog(null, "O grupo de administradores não deve ser excluído.", "ERRO", JOptionPane.ERROR_MESSAGE);					
					return;		
					}	
					
				if(grupoDAO.deleta(lista_de_grupos.get(index).getId()))	
				atualizaTabelaDeGrupos();
				
				}
				else
				JOptionPane.showMessageDialog(null, "Os dados do grupo estão inacessíveis.", "ERRO", JOptionPane.ERROR_MESSAGE);		
				
			}
			else
			JOptionPane.showMessageDialog(null, "Selecione uma linha da tabela de grupos exclusão.", "ERRO", JOptionPane.ERROR_MESSAGE);		
			
			
		}});
	
	
		
		this.tabela_de_grupos.addMouseListener(new MouseAdapter(){
		@Override
		public void mouseClicked(MouseEvent e) {
				
			if(e.getClickCount()==2){	
				
			int index = tabela_de_grupos.getSelectedRow();
				
				if(index >= 0 && lista_de_grupos.size()>index){	
				
					if(lista_de_grupos.get(index)!=null && lista_de_grupos.get(index).getId()>0){
					
						
						if(lista_de_grupos.get(index).getCod()!=null && lista_de_grupos.get(index).getCod().compareTo(Comuns.COD_GRUPO_ADMIN)==0){
								
						JOptionPane.showMessageDialog(null, "O grupo de administradores não deve ser alterado.", "ERRO", JOptionPane.ERROR_MESSAGE);					
						return;	
						}
				
			
					FormAlterarGrupo alterGroup = new FormAlterarGrupo(grupoDAO, lista_de_grupos.get(index));
					alterGroup.mostrar();
					}
					else
					JOptionPane.showMessageDialog(null, "Os dados do grupo estão inacessíveis.", "ERRO", JOptionPane.ERROR_MESSAGE);		
						
					
				atualizaTabelaDeGrupos();
				}
				else
				JOptionPane.showMessageDialog(null, "Selecione uma linha da tabela de grupos alteração.", "ERRO", JOptionPane.ERROR_MESSAGE);		
					
			return;
			}
			
		atualizaPainelDePermissoes();
		}}); 
		
		
		
		

		this.bt_nova_permissao.addActionListener( new ActionListener(){	
		@Override
		public void actionPerformed( ActionEvent event ){
	   	
		int index = tabela_de_grupos.getSelectedRow();
			
			if(index >= 0 && lista_de_grupos.size()>index){	
			
				if(lista_de_grupos.get(index)!=null && lista_de_grupos.get(index).getId()>0){
				

				FormNovaPermissao	newPermission = new FormNovaPermissao(grupoDAO.conector, lista_de_grupos.get(index));
				newPermission.mostrar();
			
				atualizaPainelDePermissoes();
				}
			}
		}});
	
	
	
		this.bt_salvar.addActionListener( new ActionListener(){
		@Override
		public void actionPerformed( ActionEvent event ){
	    	
		int index = tabela_de_grupos.getSelectedRow();
			
			if(index >= 0 && lista_de_grupos.size()>index){	
			
				if(lista_de_grupos.get(index)!=null && lista_de_grupos.get(index).getId()>0){
				
				
					if(permissaoDAO.addPermisoes(lista_de_grupos.get(index).getId(), lista_de_permissoes)){
					
					JOptionPane.showMessageDialog(null, "Permissões de grupo configuradas com sucesso.", "SUCESSO", JOptionPane.INFORMATION_MESSAGE);			
					atualizaPainelDePermissoes();
					}
				}
			}
		}});
	
	
	
	
	List<JTextField> textFieldList = new ArrayList<JTextField>();
	textFieldList.add(tf_nome);
	textFieldList.add(tf_cod);
	

	Comuns.addEventoDeFoco(textFieldList);
	
	
	this.atualizaTabelaDeGrupos();
	}


		
	
	
	
	public void atualizaTabelaDeGrupos(){
		
	this.modelo_de_tabela.setNumRows(0);	
	
	this.lista_de_grupos  = this.grupoDAO.getGrupos();
	
	String[] aux = new String[3];
	
		for(Grupo grupo: this.lista_de_grupos){

		aux[0] = ""+grupo.getId();	
		aux[1] = grupo.getCod();		
		aux[2] = grupo.getNome();	
		
		this.modelo_de_tabela.addRow(aux);
		}
	
	this.atualizaPainelDePermissoes();
	}
	

	
	
	

	private void atualizaPainelDePermissoes(){

	this.p_de_permissoes.removeAll();
	this.p_de_permissoes.repaint();
	this.lista_de_permissoes.clear();
	
	GridBagConstraints cons = new GridBagConstraints();  	
	
	cons.fill = GridBagConstraints.HORIZONTAL;
	cons.gridwidth = GridBagConstraints.REMAINDER;
	
		if(this.tabela_de_grupos.getSelectedRow() >= 0){	
	
			if(this.modelo_de_tabela.getValueAt(this.tabela_de_grupos.getSelectedRow(), 1).toString().compareTo(Comuns.COD_GRUPO_ADMIN) == 0){
			
				
			cons.fill = GridBagConstraints.NONE;
			cons.weighty = 0;
			cons.weightx = 0;
			p_de_permissoes.add(new JLabel("<html><font color=red><b>Grupo de Administradores do Sistema</b></font></html>"), cons);		
			p_de_permissoes.add(new JLabel("<html><font color=red>Todos os recursos ativados</font></html>"), cons);			
			this.bt_salvar.setEnabled(false);
			this.bt_deleta_grupo.setEnabled(false);	
			this.bt_nova_permissao.setEnabled(false);
			}
			else{
				
			
			cons.weighty = 0;
			cons.weightx = 1;
			
			this.lista_de_permissoes.addAll(this.permissaoDAO.getPermissoes(Integer.parseInt(this.modelo_de_tabela.getValueAt(this.tabela_de_grupos.getSelectedRow(), 0).toString())));
				
			cons.insets = new Insets(5, 10, 5, 0);
			p_de_permissoes.add(new JLabel("<html><b>GRUPO</b>: "+this.modelo_de_tabela.getValueAt(this.tabela_de_grupos.getSelectedRow(), 2)+"</html>"), cons);	
			
			cons.insets = new Insets(0, 0, 2, 0);
			cons.weightx = 1;
			p_de_permissoes.add(new JSeparator(SwingConstants.HORIZONTAL), cons);
			cons.weighty=0;

			
				if(this.lista_de_permissoes.size()==0){
			
				cons.fill = GridBagConstraints.NONE;
				cons.weighty = 1;
				cons.weightx = 0;
				p_de_permissoes.add(new JLabel("<<Nenhum Nível de Acesso Configurado para este Grupo>>"), cons);	
				this.bt_salvar.setEnabled(false);	
				}
				else{
					
					for( PermissaoDeGrupo aux: this.lista_de_permissoes ){
					
					cons.fill = GridBagConstraints.NONE;
					cons.weightx = 0;
					cons.gridwidth  = 1;
					JButton deleteAccess = new JButton(new ImageIcon(getClass().getResource("/icons/bt_remove_permissao.png")));
					deleteAccess.setToolTipText("Remover Permissão de grupo."); 
					p_de_permissoes.add(deleteAccess, cons);
			
							deleteAccess.addActionListener( new ActionListener(){
								
							@Override
							public void actionPerformed( ActionEvent event ){
							  
								
								if(tabela_de_grupos.getSelectedRow()>= 0){		
									
								int id_grupo = Integer.parseInt((String)modelo_de_tabela.getValueAt(tabela_de_grupos.getSelectedRow(), 0));
											
								if(permissaoDAO.deletaPermissaoDeGrupo(id_grupo, aux.getRecurso().getId()))
								atualizaPainelDePermissoes();
								
								}
							}});
		
					cons.fill = GridBagConstraints.HORIZONTAL;
					cons.weightx = 0.9;
					p_de_permissoes.add(new JLabel("("+aux.getRecurso().getCodigo()+") "+aux.getRecurso().getNome()), cons);
						
					cons.weightx = 0.1;
					cons.gridwidth  = GridBagConstraints.REMAINDER;
					p_de_permissoes.add(aux.getCombo(), cons);	
					aux.getCombo().setSelectedItem(aux.getValor()==null ||aux.getValor().length()==0?"...":aux.getValor() );
					}
				
				cons.fill = GridBagConstraints.BOTH;
				cons.weightx = 1;
				cons.weighty = 1;
				p_de_permissoes.add(new JLabel(""), cons);			
				this.bt_salvar.setEnabled(true);		
				}	
			
			this.bt_deleta_grupo.setEnabled(true);	
			this.bt_nova_permissao.setEnabled(true);	
			}
		}
		else{
				
		p_de_permissoes.add(new JLabel("<<Nenhum Grupo Selecionado>>"), cons);	
		this.bt_salvar.setEnabled(false);
		this.bt_deleta_grupo.setEnabled(false);	
		this.bt_nova_permissao.setEnabled(false);
		}
	
	p_de_permissoes.revalidate();
	} 

	
	
	
	

	public boolean acaoPrincipal(){
	
	if(!FormAlterarGrupo.validacao(0, this.tf_nome, this.tf_cod, this.grupoDAO))	
	return false;	
		
	Grupo grupo = new Grupo();
	grupo.setNome(this.tf_nome.getText());
	grupo.setCod(this.tf_cod.getText());
	grupo.setDescricao(this.tf_descricao.getText());
	
	return this.grupoDAO.novo(grupo);
	}
	
	
}
