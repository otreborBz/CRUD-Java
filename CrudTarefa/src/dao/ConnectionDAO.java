package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

/**
 * CLASSE CONEXAO COM O BANCO DE DADOS
 */
public class ConnectionDAO {
	
	
	public Connection getConnection() {
		
		Connection conn = null;
		
		try {
			//CARREGA DINAMICAMENTE A CLASSE DO DRIVE, PARA FAZER A COMUNICAÇÃO COM O BANCO DE DADOS
			Class.forName("com.mysql.jdbc.Driver");
			
		} catch (ClassNotFoundException erro) {
			JOptionPane.showMessageDialog(null, "ERRO ConnectionDAO: Endereço da connection - "+ erro.getMessage());
		}
		
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/crudtarefa?useSSL=false","root", "admin");
			
		} catch (SQLException erro) {
			JOptionPane.showMessageDialog(null, "ERRO: url da conexão - "+ erro.getMessage());
		}
		
		return conn;
	}

}
