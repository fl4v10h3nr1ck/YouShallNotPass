package voceNaoVaiPassar.forms.usuarios;

import voceNaoVaiPassar.Comuns;
import voceNaoVaiPassar.beans.Usuario;
import voceNaoVaiPassar.dao.UsuariosDAO;






public class FormAlterarUsuario extends FormDeUsuarioBase{


private static final long serialVersionUID = 1L;




	public FormAlterarUsuario( UsuariosDAO dao, Usuario usuario){
		
	super(dao, "Alterar Usuário", 430, 330);
	
	this.usuario = usuario;
	
	this.tf_usuario.setText(this.usuario.getUsuario());
	this.tf_email.setText(this.usuario.getEmail());
	
	if(this.usuario.getUsuario()!=null && this.usuario.getUsuario().compareTo(Comuns.NOME_USUARIO_ADMIN)==0)
	this.tf_usuario.setEnabled(false);	
	}
		
	 	

	

	
	public boolean acaoPrincipal() {
		
	if(!this.validacao())
	return false;	

	this.usuario.setUsuario(this.tf_usuario.getText());
	this.usuario.setEmail(this.tf_email.getText());
	
	if(this.pw_senha.getPassword().length>0)
	this.usuario.setSenha(Comuns.cript(String.valueOf(this.pw_senha.getPassword())));
		
	return dao.altera(this.usuario);
	}

	
	
	

}
