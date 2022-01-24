
package vocenaovaipassar.beans;

import javax.swing.JComboBox;



public class PermissaoDeGrupo {

private int id;
private int id_grupo;
private String valor;

private Recurso recurso;


private JComboBox<String> combo;


	

	public PermissaoDeGrupo(){
			
	this.combo = new JComboBox<String>();
	}




public int getId() {	return id;}
public void setId(int id) {	this.id = id;}

public int getId_grupo() {	return id_grupo;}
public void setId_grupo(int id_grupo) {	this.id_grupo = id_grupo;}

public String getValor() {	return valor;}	
public void setValor(String valor) {	this.valor = valor;}



public Recurso getRecurso() {	return recurso;}	
	public void setRecurso(Recurso recurso) {	
	
	this.recurso = recurso;
	
		if(this.recurso!=null){
	
		this.combo.removeAllItems();
	
			if(this.getRecurso().getTipo().compareTo("VER_ED_REM")==0){
				
			this.combo.addItem("...");
			this.combo.addItem("VER");
			this.combo.addItem("EDITAR");
			this.combo.addItem("EXCLUIR");
			}
			else{
				
			this.combo.addItem("...");
			this.combo.addItem("SIM");
			this.combo.addItem("NAO");
			}
		}
	}




public JComboBox<String> getCombo() {return this.combo;}




	
}
