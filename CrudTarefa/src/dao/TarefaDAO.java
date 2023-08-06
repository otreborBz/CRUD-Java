package dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Tarefa;

/**
 * CLASSE PARA COMUNICAÇÃO COM O BANCO DE DADOS 
 */
public class TarefaDAO {
	
	private Connection connection = null;//CRIA UMA VARIAVEL DE CONEXAO
	private PreparedStatement ps = null;//cria a preparacao para inserir os dados
	private ResultSet rs;
	private ArrayList<Tarefa> lista = new ArrayList<>();
	
	
/**
 * METODO PARA CADASTRAR UMA TAREFA
 * @param tarefa
 * @throws ExceptionDAO
 */
	public void cadastrarTarefaDAO(Tarefa tarefa) throws ExceptionDAO{
		
		String sql = "insert into tarefa(nome, descricao) value(?, ?)";//insiro os o codigo a ser enviado ao banco
		
		
		try {
			
			connection = new ConnectionDAO().getConnection(); //conecta ao banco de dados
			ps = connection.prepareStatement(sql);
			ps.setString(1, tarefa.getNome());
			ps.setString(2, tarefa.getDescricao());
			ps.execute();
			
		} catch (SQLException erro) {
			throw new ExceptionDAO("ERRO: Ao enviar dados para a tabela - "+erro.getMessage());
			
		}finally {
			try {
				if(ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				throw new ExceptionDAO("ERRO: Ao fechar o prepareStatement : "+e.getMessage());
			}
			try {
				if(connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				throw new ExceptionDAO("ERRO: Ao fechar o connection: "+e.getMessage());
			}
			
		}
	}
		
/**
 * METODO TRAS PARA O USUARIO AS TAREFAS QUE ESTAO SALVAS NO BANCO DE DADOS
 * @return
 * @throws ExceptionDAO
 */
	public ArrayList<Tarefa> listarTarefa()throws ExceptionDAO{
		
		String sql = "select * from tarefa ";
		
		try {
			connection = new ConnectionDAO().getConnection();
			ps = connection.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while(rs.next()) {//ELE EXECUTA ATE QUE EXISTIR UMA PROXIMA LINHA NO BANCO DE DADOS
				Tarefa tarefa = new Tarefa();
				tarefa.setId(rs.getInt("codigo"));//O NOME "CODIGO" TEM QUE SER IGUAL AO QUE ESTA NO BANCO
				tarefa.setNome(rs.getString("nome"));//SETA O NOME COM O QUE É PEGO NO BANCO DE DADOS
				tarefa.setDescricao(rs.getString("descricao"));
				lista.add(tarefa);
			}	
		} catch (SQLException erro) {
			throw new ExceptionDAO("ERRO: Ao listar dados para a tabela - "+erro.getMessage());
		}finally {
			try {
				if(ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				throw new ExceptionDAO("ERRO: Ao fechar o prepareStatement : "+e.getMessage());
			}
			try {
				if(connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				throw new ExceptionDAO("ERRO: Ao fechar o connection: "+e.getMessage());
			}
			
		}
		
		return lista;
	}

/**
 * METODO PARA ALTERAR AS TAREFAS SALVAS NO BANCO DE DADOS
 * @param tarefa
 * @throws ExceptionDAO
 */
	public void alterarTarefa(Tarefa tarefa) throws ExceptionDAO {
		String sql = "UPDATE tarefa SET nome = ?, descricao = ? WHERE codigo = ?";
		
		try {
			connection = new ConnectionDAO().getConnection();
			ps = connection.prepareStatement(sql);
			
			ps.setString(1, tarefa.getNome());
			ps.setString(2, tarefa.getDescricao());
			ps.setInt(3, tarefa.getId());
			ps.execute();
				
		} catch (Exception e) {

		}finally {
			try {
				if(ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				throw new ExceptionDAO("ERRO: Ao fechar o prepareStatement : "+e.getMessage());
			}
			try {
				if(connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				throw new ExceptionDAO("ERRO: Ao fechar o connection: "+e.getMessage());
			}
			
		}
		
	}
	/**
	 * METODO PARA DELETAR UMA TAREFA DO BANCO DE DADOS
	 * @param tarefa
	 * @throws ExceptionDAO
	 */
	public void deletarTarefa(Tarefa tarefa) throws ExceptionDAO {
		String sql = "DELETE FROM tarefa WHERE codigo = ? ";

		try {
			connection = new ConnectionDAO().getConnection();
			ps = connection.prepareStatement(sql);
			ps.setInt(1, tarefa.getId());
			ps.execute();
			
		} catch (Exception e) {
			throw new ExceptionDAO("ERRO: Deletar a tabela : "+e.getMessage());
		}finally {
			try {
				if(ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				throw new ExceptionDAO("ERRO: Ao fechar o prepareStatement : "+e.getMessage());
			}
			try {
				if(connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				throw new ExceptionDAO("ERRO: Ao fechar o connection: "+e.getMessage());
			}
			
		}
				
	}
	

}
