package voceNaoVaiPassar.componentes;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

import voceNaoVaiPassar.componentes.Dialogo;



public class OuvinteDeTeclado extends KeyAdapter{

	
private Dialogo dialogo;	
	
	



	public OuvinteDeTeclado(Dialogo dialogo){
		
	this.dialogo = dialogo;	
	}



	
	
	public void keyPressed(KeyEvent ek){
		
	if(ek.getKeyCode() == KeyEvent.VK_ESCAPE)
	this.dialogo.dispose();
	
	Component c  =ek.getComponent();
	
		if(ek.getKeyCode() == KeyEvent.VK_ENTER || ek.getKeyCode() == KeyEvent.VK_DOWN){	

		if(c instanceof JTextArea || c instanceof JTable)
		return;
		
	
		
		c.transferFocus();
		
		
		//rola o scroll caso o componente estiver num scrollpane e não estiver visivel.
		Component aux = c.getParent();
			for(int i = 0;i < 5;i++){
			
			if(aux==null || aux instanceof JScrollPane)
			break;
			
			aux = aux.getParent();
			}
		
			if(aux!=null && aux instanceof JScrollPane){
			
			JScrollPane scroll = (JScrollPane)aux;  
			Component   panel  = scroll.getViewport().getComponent(0);  
	                      
				if (panel instanceof JPanel){  
		        Point p = new Point((int) scroll.getViewport().getViewPosition().getX(), (int) c.getLocation().getY()-50);  
		        scroll.getViewport().setViewPosition(p);
				}
			}  
		}
	
		
		
		if(ek.getKeyCode() == KeyEvent.VK_UP){
		
		if(c instanceof JTable)
		return;	
				
		c.transferFocusBackward();	
		}
		
		
		
		
	if(ek.getKeyCode() == KeyEvent.VK_F12)	
	this.dialogo.acaoPrincipal();	
		
	}
	
	
	
	
	
}

