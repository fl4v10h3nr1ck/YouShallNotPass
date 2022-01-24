package vocenaovaipassar.componentes;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagLayout;

import javax.swing.JDialog;
import javax.swing.WindowConstants;




public abstract class Dialogo extends JDialog{

	
	
private static final long serialVersionUID = 1L;





	public Dialogo(String titulo, int largura, int altura){
		
	super();	
		
	this.setTitle(titulo);
	this.setSize( largura, altura); 
	this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	this.setLocationRelativeTo(null);
	this.setLayout(new GridBagLayout());
	this.setModal(true);  	
	
	}
	
	
	
	
	
	public void mostrar(){
	
	this.addOuvinte(this);		
		
	this.setVisible(true);	
	}
		
	
	
	
	private void addOuvinte(Container container) {  
		
	for (Component c : container.getComponents())       
	addOuvinte((Container)c); 
			
	container.addKeyListener(new OuvinteDeTeclado(this));
	} 

	
	
	
	
	
	public abstract void addComponentes();
	
	
	
	
	
	public abstract boolean acaoPrincipal();
	
	
	
	
	
}
