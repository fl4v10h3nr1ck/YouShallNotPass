package vocenaovaipassar.forms.usuarios;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import vocenaovaipassar.beans.Usuario;
import vocenaovaipassar.componentes.Dialogo;
import vocenaovaipassar.componentes.Format_TextField_MaxComp;
import vocenaovaipassar.dao.UsuariosDAO;
import vocenaovaipassar.geral.Comuns;



public abstract class FormDeUsuarioBase   extends Dialogo{

	

private static final long serialVersionUID = 1L;


protected UsuariosDAO dao;

protected JTextField tf_usuario;
protected JTextField tf_email;
protected JPasswordField pw_senha;
protected JPasswordField pw_repete_senha;


protected Usuario usuario;



	public FormDeUsuarioBase( UsuariosDAO dao, String titulo, int largura, int altura){
		
	super(titulo, largura, altura);
	
	this.dao = dao;

	this.addComponentes();
	}
		
	 	

	
	
	@Override
	public void addComponentes() {
	
	GridBagConstraints cons = new GridBagConstraints();  
	
	cons.fill = GridBagConstraints.BOTH;
	cons.gridwidth = GridBagConstraints.REMAINDER;
	cons.weightx = 1;
	cons.weighty = 1;
		JPanel p_fundo = new JPanel(){
	        	 	
		private static final long serialVersionUID = 1L;

	    			
			@Override
			public final void paintComponent( Graphics g){	
	    	    	
	    	 	    
			super.paintComponents(g);	
	    	    		
			Graphics2D g2 = (Graphics2D) g.create();	
	    	    	
	    	    	
	    	int altura 		      = this.getHeight();
	    	int largura             = this.getWidth();

	    	g2.setColor(Color.white );
	   	 	g2.fillRect(  0 , 0 ,  largura,  altura);
	    	    
			try{ 
			
			BufferedImage	imagem = ImageIO.read(getClass().getResource("/icons/icon_new_user.png"));	            
			g2.drawImage(imagem, largura - imagem.getWidth() -40, (altura - imagem.getHeight())/2 +20,   imagem.getWidth(), imagem.getHeight(), null);
			}
			catch (IOException e) {e.printStackTrace();} 
			}};
			
	p_fundo.setLayout(new GridBagLayout());
	add( p_fundo, cons );
	p_fundo.setBorder(BorderFactory.createTitledBorder("Dados de Usuário"));  
	
	
	cons.fill = GridBagConstraints.HORIZONTAL;
	cons.gridwidth = GridBagConstraints.REMAINDER;
	cons.weighty = 0;
	cons.insets = new Insets(2, 2, 0, 2);
	p_fundo.add(new JLabel("<html>Nome de Usuário:<font color=red>*</font></html>"), cons);
	
	cons.insets = new Insets(2, 2, 2, 2);
	p_fundo.add(this.tf_usuario  = new JTextField(), cons);
	this.tf_usuario.setDocument( new Format_TextField_MaxComp(  20, this.tf_usuario  ) );
	
	
	cons.insets = new Insets(2, 2, 0, 2);
	p_fundo.add(new JLabel("<html>E-mail:<font color=red>*</font></html>"), cons);
	
	cons.insets = new Insets(2, 2, 2, 2);
	p_fundo.add(this.tf_email  = new JTextField(), cons);
	this.tf_email.setDocument( new Format_TextField_MaxComp(  100, this.tf_email  ) );
	
	
	cons.insets = new Insets(2, 2, 0, 2);
	p_fundo.add(new JLabel("<html>Senha:<font color=red>*</font></html>"), cons);
	
	cons.gridwidth = 1;
	cons.insets = new Insets(2, 2, 2, 2);
	p_fundo.add(this.pw_senha  = new JPasswordField(), cons);
	this.pw_senha.setDocument( new Format_TextField_MaxComp(  16, this.pw_senha  ) );
	
	
	cons.gridwidth = GridBagConstraints.REMAINDER;
	p_fundo.add(new JLabel(""), cons);
	
	cons.insets = new Insets(2, 2, 0, 2);
	p_fundo.add(new JLabel("<html>Repita a Senha:<font color=red>*</font></html>"), cons);
	
	cons.gridwidth = 1;
	cons.insets = new Insets(2, 2, 2, 2);
	p_fundo.add(this.pw_repete_senha  = new JPasswordField(), cons);
	this.pw_repete_senha.setDocument( new Format_TextField_MaxComp(  16, this.pw_repete_senha  ) );
	
	
	cons.gridwidth = GridBagConstraints.REMAINDER;
	p_fundo.add(new JLabel(""), cons);
	
	
	cons.fill = GridBagConstraints.NONE;
	cons.anchor = GridBagConstraints.CENTER;
	cons.weightx = 0;
	cons.insets = new Insets(20, 0, 10, 0);
	JButton saveBT = new JButton("Salvar Usuário", new ImageIcon(getClass().getResource("/icons/icon_save.png")));
	saveBT.setFocusable(true);
	p_fundo.add(saveBT, cons);
	
 
		saveBT.addActionListener( new ActionListener(){
			@Override
			public void actionPerformed( ActionEvent event ){
		    	
				if(acaoPrincipal()){
					
				JOptionPane.showMessageDialog(null, "Informações salvas com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
				dispose();	
				}	
			}
		});
	
		
		saveBT.addKeyListener(new KeyAdapter(){
		@Override	
		public void keyPressed(KeyEvent ek){
							
			if(acaoPrincipal()){
				
			JOptionPane.showMessageDialog(null, "Informações salvas com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
			dispose();	
			}
		}});
			
		
		
		
		
	List<JTextField> textFieldList = new ArrayList<JTextField>();
	textFieldList.add(tf_usuario);
	textFieldList.add(tf_email);
	textFieldList.add(pw_senha);
	textFieldList.add(pw_repete_senha);
	

	Comuns.addEventoDeFoco(textFieldList);
	}


		
	

	
	public boolean validacao(){
		
		
		if(this.tf_usuario.getText().length() == 0){
		
		JOptionPane.showMessageDialog(null, "Informe o nome de usuário.", "ERRO", JOptionPane.ERROR_MESSAGE);
		Comuns.textFieldErroMode(this.tf_usuario);
		return false;
		}
		
		
		if(this.dao.nomeDeUsuarioEmUso(this.tf_usuario.getText(), this.usuario!=null?this.usuario.getId():0)){
			
		JOptionPane.showMessageDialog(null, "O nome de usuário informado já está em uso.", "ERRO", JOptionPane.ERROR_MESSAGE);
		Comuns.textFieldErroMode(this.tf_usuario);
		return false;
		}
		
		

		if(this.tf_email.getText().length() == 0){
			
		JOptionPane.showMessageDialog(null, "Informe o e-mail do usuário.", "ERRO", JOptionPane.ERROR_MESSAGE);
		Comuns.textFieldErroMode(this.tf_email);
		return false;	
		}

		
	Pattern pattern = Pattern.compile(".+@.+\\.[a-z]+");  
		
		if(!pattern.matcher(this.tf_email.getText()).matches()){
			
		JOptionPane.showMessageDialog(null, "Informe um e-mail válido.", "ERRO", JOptionPane.ERROR_MESSAGE);
		Comuns.textFieldErroMode(this.tf_email);
		return false;	
		}
		
		
		if(this.usuario==null || (this.usuario!=null && this.pw_senha.getPassword().length>0) ){
		
		
			if( String.valueOf(this.pw_senha.getPassword()).length() < 6){
				
			JOptionPane.showMessageDialog(null, "A senha deve ter ao menos 6 caracteres.", "ERRO", JOptionPane.ERROR_MESSAGE);
			Comuns.textFieldErroMode(this.pw_senha);
			return false;		
			}
			
			
			if( String.valueOf(this.pw_senha.getPassword()).compareTo(String.valueOf(this.pw_repete_senha.getPassword())) != 0){
				
			JOptionPane.showMessageDialog(null, "As senhas informadas não são iguais.", "ERRO", JOptionPane.ERROR_MESSAGE);
			Comuns.textFieldErroMode(this.pw_repete_senha);
			return false;		
			}
		}
		
	return true;
	}


	
	
}
