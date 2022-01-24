package vocenaovaipassar.forms;




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

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import vocenaovaipassar.beans.UsuarioAtual;
import vocenaovaipassar.componentes.Dialogo;
import vocenaovaipassar.componentes.Format_TextField_MaxComp;
import vocenaovaipassar.dao.LoginDAO;





public final class FormDeLogin  extends Dialogo{

	

private static final long serialVersionUID = 1L;


private LoginDAO dao;
private UsuarioAtual usuarioAtual;

private JTextField user;
private JPasswordField password;


private String usuario_dev;
private String senha_dev;




	public FormDeLogin( LoginDAO dao, UsuarioAtual usuarioAtual, String usuario_dev, String senha_dev){
		
	super("Login", 450, 350);
	
	this.dao = dao;
	this.usuarioAtual = usuarioAtual;
	
	this.usuario_dev  = usuario_dev;
	this.senha_dev  = senha_dev;
	
	addComponentes();
	}
		
	 	

	
	
	
	public void addComponentes(){
	
	GridBagConstraints cons = new GridBagConstraints();  
	
	cons.fill = GridBagConstraints.BOTH;
	cons.gridwidth = GridBagConstraints.REMAINDER;
	cons.weighty =1;
	cons.weightx = 1;
	JPanel p_fundo = new JPanel(){
	         	
		private static final long serialVersionUID = 1L;
		@Override
	   	public final void paintComponent( Graphics g){	
	    	    	 	    
		super.paintComponents(g);	
	    	    		
		Graphics2D g2 = (Graphics2D) g.create();	
	    	    	
	    	    	
	    int altura 	= this.getHeight();
	    int largura = this.getWidth();
	    	  
	    g2.setColor(Color.white );
	   	g2.fillRect(  0 , 0 ,  largura,  altura);
	   		
	    	try {	
		   	BufferedImage	imagem = ImageIO.read(getClass().getResource("/icons/fundo.jpg"));	            
			g2.drawImage(imagem, 0, (altura - imagem.getHeight())/2,   imagem.getWidth(), imagem.getHeight(), null);
	
			g2.setColor(new Color(151, 161, 175) );
			g2.fillRect(  imagem.getWidth(),  0,   largura,  altura);
	   		} 
	   		catch (IOException e) {e.printStackTrace();} 
	   	}};
	p_fundo.setLayout(new GridBagLayout());
	add( p_fundo, cons );
	
	
	cons.gridwidth = 1;
	cons.weightx = 0.65;
	p_fundo.add( new JLabel(""), cons);
	
	cons.gridwidth = GridBagConstraints.REMAINDER;
	cons.weightx = 0.35;
	JPanel p_login = new JPanel();
	p_login.setLayout(new GridBagLayout());
	p_login.setOpaque(false);
	p_fundo.add( p_login, cons);

	
	cons.weighty =0;
	cons.weightx = 1;
	cons.insets = new Insets(0, 5, 2, 5);
	p_login.add(new JLabel("Usuário:"), cons  );
	
	cons.insets = new Insets(0, 5, 8, 5);
	p_login.add(this.user  = new JTextField(), cons);
	this.user.setDocument( new Format_TextField_MaxComp(  20, this.user  ) );
	
	
	cons.insets = new Insets(0, 5, 2, 5);
	p_login.add(new JLabel("Senha:"), cons);
	
	cons.insets = new Insets(0, 5, 8, 5);
	p_login.add(this.password  = new JPasswordField(), cons);
	this.password.setDocument( new Format_TextField_MaxComp(  16, this.password  ) );
	
	
	cons.insets = new Insets(5, 0, 0, 0);
	cons.fill = GridBagConstraints.NONE;
	cons.anchor = GridBagConstraints.CENTER;
	cons.weighty =0;
	cons.weightx = 0;
	cons.ipadx =20;
	JButton loginBT = new JButton("Entrar", new ImageIcon(getClass().getResource("/icons/chave.png")));
	loginBT.setFocusable(true);
	p_login.add(loginBT, cons);
	    
	
		loginBT.addActionListener( new ActionListener(){
		@Override
		public void actionPerformed( ActionEvent event ){
	    	
			
		if(acaoPrincipal())
		dispose();
		else
		JOptionPane.showMessageDialog(null, "Senha ou Usuário incorreto.", "ERRO", JOptionPane.ERROR_MESSAGE);		
				
		}});
	
	

		loginBT.addKeyListener(new KeyAdapter(){
		@Override	
		public void keyPressed(KeyEvent ek){
						
			if(ek.getKeyCode() == KeyEvent.VK_ENTER){
				
			if(acaoPrincipal())
			dispose();
			else
			JOptionPane.showMessageDialog(null, "Senha ou Usuário incorreto.", "ERRO", JOptionPane.ERROR_MESSAGE);		
						
			}
		}});
		
	this.rootPane.setDefaultButton(loginBT);	
	}






	@Override
	public boolean acaoPrincipal() {
	
		if(this.usuario_dev!=null && 
			this.senha_dev!=null &&
			 this.usuario_dev.length()>0 &&
			  this.senha_dev.length()>0){
		
			if(this.usuario_dev.compareTo(user.getText())==0 && 
				this.senha_dev.compareTo(String.valueOf(this.password.getPassword()))==0){
			
			usuarioAtual.setId(999);
			usuarioAtual.setNome("DEV");
			usuarioAtual.setUsuarioDEV(true);
			return true;
			}
		}	
		
		
	dao.login(user.getText(), String.valueOf(password.getPassword()), usuarioAtual);
		
	return usuarioAtual!= null && usuarioAtual.getId() > 0;
	}


		
	
	
	
	
	

}
