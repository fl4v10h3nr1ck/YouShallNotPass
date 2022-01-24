package vocenaovaipassar.forms.grupos;


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
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import vocenaovaipassar.beans.Grupo;
import vocenaovaipassar.beans.Recurso;
import vocenaovaipassar.componentes.Dialogo;
import vocenaovaipassar.dao.PermissaoDAO;



public class FormNovaPermissao   extends Dialogo{


private static final long serialVersionUID = 1L;

private PermissaoDAO dao;	

private Grupo grupo;
private List<JCheckBox>	permissoesChecks;
private List<Recurso>	listaDePermissoes;
private JCheckBox checkTodos;


	




	public FormNovaPermissao(Connection conector, Grupo grupo){
		
	super("Nova Permissão para Grupo", 500, 450);
		
	this.grupo = grupo;
	this.dao = new PermissaoDAO(conector);
	
	permissoesChecks = new ArrayList<JCheckBox>();
	listaDePermissoes = new ArrayList<Recurso>();
	
	addComponentes();	
	}
	
	
	
	
	public void addComponentes(){
	
	GridBagConstraints cons = new GridBagConstraints();  	
	cons.fill = GridBagConstraints.BOTH;
	cons.weighty  = 1;
	cons.weightx  = 1;
	cons.gridwidth  = GridBagConstraints.REMAINDER;		

	JPanel p_fundo = new JPanel();
	p_fundo.setBackground(Color.WHITE);
	p_fundo.setLayout(new GridBagLayout());  
	this.add(p_fundo, cons);
	p_fundo.setBorder(BorderFactory.createTitledBorder("Lista de Permissões do Sistema"));  

	
	JPanel p_lista = new JPanel();
	p_lista.setBackground(Color.WHITE);
	p_lista.setLayout(new GridBagLayout());  
	
	cons.fill = GridBagConstraints.BOTH;
	cons.weighty  = 1;
	p_fundo.add( new JScrollPane( p_lista), cons);
	
	
	cons.fill = GridBagConstraints.HORIZONTAL;
	cons.weighty  = 0;
	cons.insets = new Insets(2, 0, 2, 0);
	cons.gridwidth  =1;		
	p_fundo.add(this.checkTodos = new JCheckBox("Marcar Todos."), cons);
		
	cons.fill = GridBagConstraints.NONE;
	cons.weighty  = 0;
	cons.weightx  = 0;
	cons.insets = new Insets(2, 0, 2, 0);
	cons.gridwidth  = GridBagConstraints.REMAINDER;		
	cons.anchor = 	GridBagConstraints.WEST;		
	JButton btSave = new JButton("Adicionar Permissões");
	p_fundo.add(btSave, cons);
		
	
		btSave.addActionListener( new ActionListener(){		
		@Override
		public void actionPerformed( ActionEvent event ){
		
			if(acaoPrincipal()){
					
			JOptionPane.showMessageDialog(null, "Permissões de grupo adicionadas com sucesso.", "SUCESSO", JOptionPane.INFORMATION_MESSAGE);			
			dispose();
			}
		}});
	
	
	
		
		
		btSave.addKeyListener(new KeyAdapter(){
		@Override	
		public void keyPressed(KeyEvent ek){
							
			if(acaoPrincipal()){
				
			JOptionPane.showMessageDialog(null, "Permissões de grupo adicionadas com sucesso.", "SUCESSO", JOptionPane.INFORMATION_MESSAGE);			
			dispose();
			}
		}});
		
		

		this.checkTodos.addMouseListener(new MouseAdapter(){
		@Override
		public void mouseClicked(MouseEvent e) {
		
				
		for(JCheckBox aux: permissoesChecks)
		aux.setSelected(true);
			
		}}); 


	this.listaDePermissoes.addAll(this.dao.getPermissoesQueGrupoNaoTem(this.grupo.getId()));	
	

		if(this.listaDePermissoes.size() == 0){
			
		cons.fill = GridBagConstraints.NONE;
		cons.anchor= GridBagConstraints.CENTER;
		cons.weightx = 1;
		cons.gridwidth  = GridBagConstraints.REMAINDER;
		cons.insets = new Insets(5, 0, 5, 0);
		p_lista.add(new JLabel("<<Sem Permissões para Este Grupo>>"), cons);		
		}
		else{
			
			for(Recurso aux: this.listaDePermissoes){
				
			cons.fill = GridBagConstraints.HORIZONTAL;
			cons.anchor= GridBagConstraints.WEST;
			cons.weightx = 0.95;
			cons.gridwidth  = 1;
			cons.insets = new Insets(4, 4, 4, 0);
			p_lista.add(new JLabel(aux.getNome()), cons);
			
			cons.insets = new Insets(0, 0, 0, 0);
			cons.gridwidth  = GridBagConstraints.REMAINDER;
			cons.weightx = 0.05;
			JCheckBox check  = new JCheckBox();
			p_lista.add(check, cons);
			this.permissoesChecks.add(check);
			
			cons.weightx = 1;
			p_lista.add(new JSeparator(SwingConstants.HORIZONTAL), cons);
			
			}
		}
	
		
	cons.fill = GridBagConstraints.BOTH;
	cons.anchor= GridBagConstraints.WEST;
	cons.weightx = 1;
	cons.weighty = 1;
	p_lista.add(new JLabel(""), cons);
	}
	

	
	
	
	
	public boolean acaoPrincipal(){
		
	
		for(JCheckBox aux: permissoesChecks){
			
			if(aux.isSelected()){
		
			if(!this.dao.addPermissaoAGrupo(this.grupo.getId(), this.listaDePermissoes.get(permissoesChecks.indexOf(aux)).getId()))	
			return false;	
			}
		}	
	
	return true;
	}
	
	
	
	
	
}
