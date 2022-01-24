
package voceNaoVaiPassar.forms.usuarios;

import voceNaoVaiPassar.Comuns;
import voceNaoVaiPassar.beans.Usuario;
import voceNaoVaiPassar.dao.UsuariosDAO;



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
