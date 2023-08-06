package dao;


/**
 * UMA CLASSE CRIADA PARA SUBESCREVER UMA EXCEPTION
 */
@SuppressWarnings("serial")
public class ExceptionDAO extends Exception {
	
	public ExceptionDAO(String mensagem) {
		super(mensagem);	
	}

}
