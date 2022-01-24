package teste;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import voceNaoVaiPassar.VoceNaoVaiPassar;
import voceNaoVaiPassar.beans.ListaDeRecursosDoSistema;
import voceNaoVaiPassar.beans.Recurso;
import voceNaoVaiPassar.beans.UsuarioAtual;

public class Main {

	
	
	
	public static void main(String[] args){
		
		try {UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");}
    	catch (ClassNotFoundException e) {e.printStackTrace();}
    	catch (InstantiationException e) {e.printStackTrace();}
    	catch (IllegalAccessException e) {e.printStackTrace();}
    	catch (UnsupportedLookAndFeelException e) {e.printStackTrace();}	
		
		try{	
			
		Class.forName("com.mysql.jdbc.Driver");		
		Connection connector = DriverManager.getConnection("jdbc:mysql://localhost/dialab_2_0?user=root&password=");		
		
		ListaDeRecursosDoSistema lista = new ListaDeRecursosDoSistema(){

			@Override
			public List<Recurso> getRecursos() {
				
				
			List<Recurso> lista_de_recursos = new ArrayList<Recurso>();	
			
			lista_de_recursos.add(new Recurso("Movimento de clientes", Recurso.VER_ED_REM, "GEREMOV"));	
			lista_de_recursos.add(new Recurso("Incluir resultados de exames", Recurso.SIM_NAO, "INRESULT"));	
			lista_de_recursos.add(new Recurso("Cadastros", Recurso.VER_ED_REM, "GERECAD"));	
			lista_de_recursos.add(new Recurso("Tabelas", Recurso.VER_ED_REM, "GERETAB"));	
			lista_de_recursos.add(new Recurso("Gerar relatórios", Recurso.SIM_NAO, "GEREREL"));	
			lista_de_recursos.add(new Recurso("Faturamento", Recurso.SIM_NAO, "FATURA"));	
			lista_de_recursos.add(new Recurso("Registro e licenciamento", Recurso.SIM_NAO, "REGLICEN"));	
			lista_de_recursos.add(new Recurso("Informações empresariais", Recurso.SIM_NAO, "INFOEMP"));	
					
			return lista_de_recursos;
			}	
		};
		
		
		
		VoceNaoVaiPassar youShallNotPass = new VoceNaoVaiPassar(connector, lista);
		
		
			if(youShallNotPass.prepara()){
				
				
			//UsuarioAtual usuario= youShallNotPass.login();
			
			//if(usuario !=null){
			//System.out.println("acesso permitido");
			
				
				UsuarioAtual usuarioAtual = new 	UsuarioAtual();
				usuarioAtual.setId(1);
				usuarioAtual.setNome("admin");	
			
			youShallNotPass.formGestaoDeUsuarios(usuarioAtual);
			//}
			//else
			//System.out.println("acesso negado");
			}
			
		}
		catch(ClassNotFoundException | SQLException erroSQL){ 
		System.out.println("erro de acesso");			
		return;
		}
	
	}
	
	
	
	
}
