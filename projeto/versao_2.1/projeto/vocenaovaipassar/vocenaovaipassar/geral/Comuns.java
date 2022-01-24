package vocenaovaipassar.geral;


import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JTextField;

public class Comuns {

	
	
	
public static final String NOME_TB_USUARIOS = "usuarios";	
public static final String NOME_TB_ACESSOS = "acessos";	
public static final String NOME_TB_GRUPOS = "grupos";	
public static final String NOME_TB_GRUPO_X_ACESSO = "grupos_x_acessos";	
public static final String NOME_TB_USUARIOS_X_GRUPO = "usuarios_x_grupos";	
	


public static final String NOME_USUARIO_ADMIN = "admin";	
public static final String EMAIL_USUARIO_ADMIN = "admin@admin.com";	
public static final String SENHA_USUARIO_ADMIN = "123456";	

public static final String NOME_GRUPO_ADMIN = "ADMINS";	
public static final String COD_GRUPO_ADMIN = "ADMINS";	


public static final Color COR_FUNDO= new Color(151, 161, 175); 




	public static String getDataAtual(){
	
	Date dataNow = new Date();
	SimpleDateFormat dateFormat = new SimpleDateFormat( "dd/MM/yyyy HH:mm");  	
				
		
	return dateFormat.format( dataNow);
	}






	public static String cript(String string){
	
	MessageDigest algorithm= null;
	byte messageDigest[] = null;
		
		try { algorithm = MessageDigest.getInstance("SHA-256"); }catch (NoSuchAlgorithmException e) {return "";}
	
		try {messageDigest = algorithm.digest(string.getBytes("UTF-8")); } catch (UnsupportedEncodingException e)  {return "";}
		 
	StringBuilder hexString = new StringBuilder();
	for (byte b : messageDigest)
	hexString.append(String.format("%02X", 0xFF & b));
	
			
	return hexString.toString();	
	}
	




	public static void textFieldErroMode(JTextField field){	
	
	if(field == null)
	return;
	
	if(field.getText().length() == 0)
	field.setBackground(Color.red);
	else
	field.setForeground(Color.red);	
		
	}
	
	

	
	public static void addEventoDeFoco(List<JTextField> fields){
		
	if(fields == null)
	return;
	
		for( final JTextField field : fields){
	
			field.addFocusListener(new FocusAdapter() {  
			
				@Override
				public void focusGained(FocusEvent e) {
		   
				if(field.getText().length() == 0)
				field.setBackground(Color.white);
				else
				field.setForeground(Color.black);	
				}
			});
		}
	}



	
	
	
}