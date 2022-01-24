
package vocenaovaipassar.forms.usuarios;

import vocenaovaipassar.beans.Usuario;
import vocenaovaipassar.dao.UsuariosDAO;
import vocenaovaipassar.geral.Comuns;



public class FormNovoUsuario   extends FormDeUsuarioBase{

	


private static final long serialVersionUID = 1L;






	public FormNovoUsuario( UsuariosDAO dao){
		
	super(dao, "Novo Usuário", 430, 330);
	}
		
	 	

	

	
	public boolean acaoPrincipal() {
		
	if(!this.validacao())
	return false;	

	Usuario usuario = new Usuario();
	usuario.setUsuario(this.tf_usuario.getText());
	usuario.setEmail(this.tf_email.getText());
	usuario.setSenha(Comuns.cript(String.valueOf(this.pw_senha.getPassword())));
		
	return dao.novo(usuario);
	}

	
	
	
}
