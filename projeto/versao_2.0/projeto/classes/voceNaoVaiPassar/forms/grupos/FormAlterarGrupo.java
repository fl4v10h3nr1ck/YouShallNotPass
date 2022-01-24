
package voceNaoVaiPassar.forms.grupos;


import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import voceNaoVaiPassar.Comuns;
import voceNaoVaiPassar.beans.Grupo;
import voceNaoVaiPassar.componentes.Dialogo;
import voceNaoVaiPassar.componentes.Format_TextField_MaxComp;
import voceNaoVaiPassar.dao.GruposDAO;





public class FormAlterarGrupo extends Dialogo{



private static final long serialVersionUID = 1L;


private GruposDAO dao;
private Grupo grupo;

private JTextField tf_nome;
private JTextField tf_cod;
private JTextField tf_descricao;




	public FormAlterarGrupo( GruposDAO dao, Grupo grupo){
		
	super("Alterar Grupo", 650, 150);
	
	this.dao = dao;
	this.grupo = grupo;
	
	addComponentes();
	
	this.tf_nome.setText(this.grupo.getNome());
	this.tf_cod.setText(this.grupo.getCod());
	this.tf_descricao.setText(this.grupo.getDescricao());
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
	add( p_fundo, cons );
	
	
	cons.fill = GridBagConstraints.HORIZONTAL;
	cons.weighty = 0;
	
	cons.gridwidth  = 1;
	cons.weightx = 0.35;
	cons.insets = new Insets(2, 2, 0, 2);
	p_fundo.add(new JLabel("<html>Nome:<font color=red>*</font></html>"), cons);
	cons.weightx = 0.2;
	p_fundo.add(new JLabel("<html>Cod:<font color=red>*</font></html>"), cons);
	cons.gridwidth  = GridBagConstraints.REMAINDER;
	cons.weightx = 0.45;
	p_fundo.add(new JLabel("Descrição:"), cons);

	cons.weightx = 0.35;
	cons.gridwidth = 1;
	cons.insets = new Insets(2, 2, 2, 2);
	this.tf_nome  = new JTextField();
	this.tf_nome.setDocument( new Format_TextField_MaxComp(  150, this.tf_nome  ) );
	p_fundo.add(this.tf_nome, cons);
	
	cons.weightx = 0.2;
	this.tf_cod  = new JTextField();
	this.tf_cod.setDocument( new Format_TextField_MaxComp(  10, this.tf_cod  ) );
	p_fundo.add(this.tf_cod, cons);
	
	cons.weightx = 0.4;
	cons.gridwidth  = GridBagConstraints.REMAINDER;
	this.tf_descricao  = new JTextField();
	this.tf_descricao.setDocument( new Format_TextField_MaxComp(  200, this.tf_descricao  ) );
	p_fundo.add(this.tf_descricao, cons);
	
	
	cons.fill = GridBagConstraints.NONE;
	cons.anchor = GridBagConstraints.CENTER;
	cons.weighty  = 0;
	cons.weightx  = 0;
	cons.insets = new Insets(5, 2, 2, 2);
	cons.ipadx = 30;
	JButton btAlter = new JButton("Salvar");
	p_fundo.add(btAlter, cons);

		btAlter.addActionListener( new ActionListener(){
		@Override
		public void actionPerformed( ActionEvent event ){
	    	
			if(acaoPrincipal()){	
					
			JOptionPane.showMessageDialog(null, "Alterações realizadas com sucesso.", "SUCESSO", JOptionPane.INFORMATION_MESSAGE);			
			dispose();
			}
		}});
	
		
		
		btAlter.addKeyListener(new KeyAdapter(){
		@Override	
		public void keyPressed(KeyEvent ek){
				
			if(acaoPrincipal()){	
				
			JOptionPane.showMessageDialog(null, "Alterações realizadas com sucesso.", "SUCESSO", JOptionPane.INFORMATION_MESSAGE);			
			dispose();
			}
		}});
		
		
		
		
		
	List<JTextField> textFieldList = new ArrayList<JTextField>();
	textFieldList.add(tf_nome);
	textFieldList.add(tf_cod);
	Comuns.addEventoDeFoco(textFieldList);
	
	this.getRootPane().setDefaultButton(btAlter);
	}

	

	
	


	
	public static boolean validacao(int id_grupo, JTextField nome, JTextField cod, GruposDAO dao){
	
		if(nome.getText().length() == 0){
			
		JOptionPane.showMessageDialog(null, "Informe o nome do grupo.", "ERRO", JOptionPane.ERROR_MESSAGE);
		Comuns.textFieldErroMode(nome);
		return false;
		}
			
		
		if(dao.campoEmUso("nome", nome.getText(), id_grupo)){
			
		JOptionPane.showMessageDialog(null, "O nome do grupo informado já está em uso.", "ERRO", JOptionPane.ERROR_MESSAGE);
		Comuns.textFieldErroMode(nome);
		return false;	
		}
		
		
		if(cod.getText().length() == 0){
			
		JOptionPane.showMessageDialog(null, "Informe o código do grupo.", "ERRO", JOptionPane.ERROR_MESSAGE);
		Comuns.textFieldErroMode(cod);
		return false;	
		}
		
		
		if(dao.campoEmUso("cod", cod.getText(), id_grupo)){
			
		JOptionPane.showMessageDialog(null, "O código do grupo informado já está em uso.", "ERRO", JOptionPane.ERROR_MESSAGE);
		Comuns.textFieldErroMode(cod);
		return false;	
		}
		
		
	return true;
	}

	
	
	
	
	public boolean acaoPrincipal(){
		
	if(!FormAlterarGrupo.validacao(this.grupo.getId(), this.tf_nome, this.tf_cod, this.dao))	
	return false;	
				
	grupo.setNome(this.tf_nome.getText());
	grupo.setCod(this.tf_cod.getText());
	grupo.setDescricao(this.tf_descricao.getText());
			
	return this.dao.altera(grupo);	
	}
	
	
	
}
