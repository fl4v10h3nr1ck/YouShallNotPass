package vocenaovaipassar.beans;

public class Recurso {

private int id;	
private String nome;
private String tipo;
private String codigo;


public static final int VER_ED_REM = 0;
public  static final int SIM_NAO = 1;



	public Recurso(){
		
	this("", 0, "");	
	}

	
	
	public Recurso(String nome, int tipo, String cod){
		
	this.setNome(nome);
	this.setTipo(tipo);
	this.setCodigo(cod);
	}


	public String getTipo() {return tipo;}
	public void setTipo(int tipo) {
		
	this.tipo	 = "";
		
	if(tipo == Recurso.VER_ED_REM)
	this.tipo = "VER_ED_REM";
	else if(tipo == Recurso.SIM_NAO)
	this.tipo = "SIM_NAO";
	}
	public void setTipo(String tipo) {this.tipo = tipo;}
	
	
	
	public String getNome() {		return nome;}	
	
	public void setNome(String nome) {		this.nome = nome;}
	
	
	public String getCodigo() {		return codigo;}
	public void setCodigo(String codigo) {	this.codigo = codigo;}


	public int getId() {		return id;}
	public void setId(int id) {		this.id = id;}


	
	
	
	
}
