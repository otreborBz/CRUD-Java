package controller;

import java.util.ArrayList;

import dao.ExceptionDAO;
import dao.TarefaDAO;
import model.Tarefa;

/**
 * CLASSE CONTROLE PARA ACESSAR OS OUTRAS CLASSES
 */
public class TarefaController {

	private Tarefa tarefa;
	private TarefaDAO tarefaDAO;
	
	
/**
 * CONSTRUTOR
 * @param tarefa
 */
	public TarefaController(Tarefa tarefa) {
		this.tarefa = tarefa;
	}
	
	public TarefaController() {
		tarefa = new Tarefa();
		tarefaDAO = new TarefaDAO();
	}
/**
 * METODOS DE ACESSOS	
 * @param nome
 * @param descricao
 * @return
 * @throws ExceptionDAO
 */
	public boolean cadastrarTarefa(String nome, String descricao) throws ExceptionDAO {
		if(nome!=null && nome.length()>0 && descricao!=null && descricao.length()>0) {
			Tarefa tarefa = new Tarefa(nome, descricao);
			tarefaDAO.cadastrarTarefaDAO(tarefa);
			return true;
		}		
		return false;
	}

	public void deletarTarefa() throws ExceptionDAO {
		tarefaDAO.deletarTarefa(tarefa);
	}
	
	public ArrayList<Tarefa> atualizarTarefa() throws ExceptionDAO {
		return tarefaDAO.listarTarefa();
	}
	
	public void alterarTarefa() throws ExceptionDAO {
		tarefaDAO.alterarTarefa(tarefa);
	}
	
	
/**
 * GETTERS AND SETTERS
 * @return
 */
	public Tarefa getTarefa() {
		return tarefa;
	}

	public void setTarefa(Tarefa tarefa) {
		this.tarefa = tarefa;
	}

	public TarefaDAO getTarefaDAO() {
		return tarefaDAO;
	}

	public void setTarefaDAO(TarefaDAO tarefaDAO) {
		this.tarefaDAO = tarefaDAO;
	}
		

}
