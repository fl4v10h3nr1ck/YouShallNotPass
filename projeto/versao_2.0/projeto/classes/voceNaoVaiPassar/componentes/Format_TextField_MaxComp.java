package voceNaoVaiPassar.componentes;



/**
 * 
 *  
 *Esta classe descende de PlainDocument e pode ser usada, atraves do método subscrito insertString,
* 
*esta classe é usada para se limitar o tamanho do texto informado nos JTextField's
*
* esta classe deve ser chamada da sequinte forma:
* 
* JTextField textField = new JTextField( num_colunn);
* textField.setDocument( new FormTextField_MaxComp(  maxLength, textField )  ); 
* 
* 
* STATUS = COMPLETA
* 
* 
*/




/* esta classe descende de PlainDocument e pode ser usada, atraves do método subscrito insertString,
 para formatar o field JTextField quanto à quantidade maxima de caractes suportados,
 * tipo de caracteres, bem com adicionar separadores como no caso de numeros de telefones e cpf.
 
 
 esta classe deve ser chamada da sequinte forma:
 * 
 * JTextField textField = new JTextField( num_colunn);
 * textField.setDocument( new FormatTextField_CEP( maxLength ,  textField )  ); 
 * 
 * 
 */



import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;







/* o contrutor recebe um inteiro representado o numero maximo de caracteres no campo
 * uma referencia ao proprio JTextField.
 */
    
public class Format_TextField_MaxComp extends PlainDocument  {  
   
	
	

private static final long serialVersionUID = 1L;


private  int maxLength;  
private JTextField textField;

  






    public Format_TextField_MaxComp ( int maxLength ,  JTextField textField ) {  
        
    super();  
    
    this.maxLength = maxLength;
    this.textField = textField;
    }  

    
    
    
    
    
    
    
    
    
    
    
    
   /* o metodo insertString é subscrito da classe PlainDocument e é invocado sempre que o usuario alterar o
     * campo do jtextfield.
     */
    
 
    public void insertString( int offset, String str, AttributeSet attr)  throws BadLocationException {  
       
       
    String aux = "";
    String campo = "";
    int i;
       

       /* str não pode ser null*/ 
    if( str == null )
    return;
   
       
    
    /*adiciona a string str que foi digitada no campo de texto, na posicao correta na string que ja havia
     * no campo de texto caso o usuario cole, adicione no fim, inicio ou meio.
     */
    
        for( i = 0 ; i < offset ; i++ ) 
       aux+= getText( i , 1 );	
        
        
        
        
        for( i = 0 ; i < str.length() ; i++ )
        aux+= str.charAt(i);	
        	
        
        int posicao_cursor = aux.length(); 	
        if( posicao_cursor > this.maxLength)
        posicao_cursor = maxLength;
        
        
        
        for( i = offset ; i < getLength() ; i++ )
        aux+= getText( i , 1 );	
        
        
 
        
        for( i = 0 ; i < aux.length() && i < maxLength; i++) 
        campo += aux.charAt(i); 
    	
       
        
 	
 
     // limpa o campo antes de regrava-lo.  
    this.textField.setText( null );
    
    //grava a nova string no textfield.
    super.insertString( 0, campo , attr);  
    
    
    

    textField.moveCaretPosition(posicao_cursor); // move o cursor para a posição seguinte à string adicionada.
    textField.setSelectionEnd(0);// remove qualquer seleção.
    }  
      
    	
    	
}
