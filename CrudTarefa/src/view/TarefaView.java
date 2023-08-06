package view;

import java.awt.BorderLayout;


import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;

import controller.TarefaController;
import dao.ExceptionDAO;
import model.Tarefa;


/**
 * METODO PRINCIPAL PARA A INTERACAO COM O USUARIO
 */
public class TarefaView {

	private JFrame frame;
	private JTextField txtNome;
	private JTextField txtDescricao;
	private JTextField txtId;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {

		}
		EventQueue.invokeLater(new Runnable() {

			public void run() {
				try {
					TarefaView window = new TarefaView();
					window.frame.setLocationRelativeTo(null);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TarefaView() {
		try {
			initialize();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @throws SQLException
	 */
	private void initialize() throws SQLException {
		/*
		 * SETA UM TAMANHO PADRÃO DOS ICONES DOS BOTOES
		 */
		int newWidth = 25;
		int newHeight = 25;
		int widthBtn = 150;
		int heightBtn = 50;
	

		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel txtInterface = new JLabel("JAVA | CRUD Tarefa");
		txtInterface.setFont(new Font("Calibri", Font.BOLD, 40));
		txtInterface.setBounds(231, 11, 341, 90);
		frame.getContentPane().add(txtInterface);

		JPanel panelCrud = new JPanel();
		panelCrud.setBackground(new Color(192, 192, 192));
		panelCrud.setBounds(10, 83, 764, 194);
		frame.getContentPane().add(panelCrud);
		panelCrud.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("Nome da Tarefa:");
		lblNewLabel_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_1.setFont(new Font("Calibri", Font.PLAIN, 20));
		lblNewLabel_1.setBounds(6, 45, 202, 31);
		panelCrud.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Descrição da Tarefa");
		lblNewLabel_2.setForeground(new Color(255, 255, 255));
		lblNewLabel_2.setFont(new Font("Calibri", Font.PLAIN, 20));
		lblNewLabel_2.setBounds(6, 90, 202, 30);
		panelCrud.add(lblNewLabel_2);

		txtNome = new JTextField();
		txtNome.setBounds(218, 46, 540, 27);
		panelCrud.add(txtNome);
		txtNome.setColumns(10);

		txtDescricao = new JTextField();
		txtDescricao.setColumns(10);
		txtDescricao.setBounds(218, 91, 540, 27);
		panelCrud.add(txtDescricao);
		
		JPanel panelTable = new JPanel();
		panelTable.setBounds(10, 289, 764, 261);
		frame.getContentPane().add(panelTable);
		panelTable.setLayout(new BorderLayout());
		
		JTable tabelaTarefa = new JTable();
		tabelaTarefa.setDragEnabled(true);
		tabelaTarefa.setSelectionBackground(new Color(192, 192, 192));
		tabelaTarefa.setFont(new Font("Calibri", Font.PLAIN, 12));
		JScrollPane scrollPane = new JScrollPane(tabelaTarefa);
		panelTable.add(scrollPane, BorderLayout.CENTER);
		tabelaTarefa.setBounds(6, 6, 752, 225);
		
		DefaultTableModel model = (DefaultTableModel) tabelaTarefa.getModel();
		model.isCellEditable(0, 0);//nao deixa modificar o conteudo da tabela
		model.addColumn("Código");
		model.addColumn("Nome");
		model.addColumn("Tarefa");
		listarTarefa(model);
		
		//BOTAO ATUALIZAR A TAREFA
		JButton btnAtualizar = new JButton("ATUALIZAR");
		btnAtualizar.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				try {			
					alterarDadoTabela(model);
					listarTarefa(model);
					limparConteudo();					
				} catch (ExceptionDAO | SQLException erro) {
					JOptionPane.showMessageDialog(null, "ERRO: btnAtualizar - " + erro.getMessage());
				}
			}		
			
			/**
			 * 
			 * @param model
			 * @throws ExceptionDAO
			 */
			private void alterarDadoTabela(DefaultTableModel model) throws ExceptionDAO {
				int id;
				String nome;
				String descricao;
				
				id = Integer.parseInt(txtId.getText());
				nome = txtNome.getText();
				descricao = txtDescricao.getText();
				
				TarefaController tarefaController = new TarefaController();
				tarefaController.getTarefa().setId(id);
				tarefaController.getTarefa().setNome(nome);
				tarefaController.getTarefa().setDescricao(descricao);			
				tarefaController.alterarTarefa();				
			}
		});
		ImageIcon iconAtualizar = new ImageIcon(TarefaView.class.getResource("/image/iconAtualizar.png"));
		Image imageAtualizar = iconAtualizar.getImage();
		Image newImageAtualizar = imageAtualizar.getScaledInstance(newWidth, newHeight, java.awt.Image.SCALE_SMOOTH);
		btnAtualizar.setIcon(new ImageIcon(newImageAtualizar));
		btnAtualizar.setBounds(411, 132, widthBtn, heightBtn);
		panelCrud.add(btnAtualizar);
		
		//BOTAO DELETAR UMA TAREFA
		JButton btnDeletar = new JButton("DELETAR");
		btnDeletar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean sucesso;
				
				try {
					sucesso = deletarTarefa();
					if(sucesso) {
						JOptionPane.showMessageDialog(null, "Deletado com sucesso!");
						
					}
					
				} catch (ExceptionDAO erro) {
					JOptionPane.showMessageDialog(null, "ERRO: btnDeletar - " + erro.getMessage());
				}finally {
					limparConteudo();
					try {
						listarTarefa(model);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
			
			/**
			 * 
			 * @return
			 * @throws ExceptionDAO
			 */
			public boolean deletarTarefa() throws ExceptionDAO {
				 String idText = txtId.getText();
				 
				    if (!idText.isEmpty()) {
				        try {
				            int id = Integer.parseInt(idText);
				            TarefaController tController = new TarefaController();
				            tController.getTarefa().setId(id);
				            tController.deletarTarefa();
				            return true;
				            
				        } catch (NumberFormatException erro ) {
				            JOptionPane.showMessageDialog(null, "ERRO: btnDletarTarefa"+erro.getMessage());
				            return false;
				        }
				    } else {
				        JOptionPane.showMessageDialog(null, "Por favor, Carregue a tarefa que deseja deletar.");
				        return false;
				    }
			}
		});
		ImageIcon iconDeletar = new ImageIcon(TarefaView.class.getResource("/image/iconDeletar.png"));
		Image imageDeletar = iconDeletar.getImage();
		Image newImageDeletar = imageDeletar.getScaledInstance(newWidth, newHeight, java.awt.Image.SCALE_SMOOTH);
		btnDeletar.setIcon(new ImageIcon(newImageDeletar));
		btnDeletar.setBounds(206, 132, widthBtn, heightBtn);
		panelCrud.add(btnDeletar);
		
		
		//BOTAO ADICIONAR UMA TAREFA
		JButton btnAdicionar = new JButton("ADICIONAR");
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					adicionarTarefa();
				} catch (SQLException erro) {
					JOptionPane.showMessageDialog(null, "ERRO: btnAdicionar - " + erro.getMessage());
				}
			}
			
			
			/**
			 * METODO PARA ADICIONAR UMA TAREFA
			 * @throws SQLException
			 */
			private void adicionarTarefa() throws SQLException {
				TarefaController tController = new TarefaController();
				
				String nome = txtNome.getText();// pega o conteudo
				String descricao = txtDescricao.getText();
				boolean sucesso;// verifica se foi adicionado

				
				try {
					sucesso = tController.cadastrarTarefa(nome, descricao);

					if (sucesso) {
						JOptionPane.showMessageDialog(null, "Tarefa Adicionada com SUCESSO!");
						limparConteudo();
						listarTarefa(model);
					}
				} catch (ExceptionDAO erro) {
					JOptionPane.showMessageDialog(null, "ERRO: Metodo btnAdicionar - " + erro.getMessage());
				} 
				
			}

			
		});
		ImageIcon iconAdicionar = new ImageIcon(TarefaView.class.getResource("/image/iconAdicionar.png"));
		Image imageAdicionar = iconAdicionar.getImage();
		Image newImageAdicionar = imageAdicionar.getScaledInstance(newWidth, newHeight, java.awt.Image.SCALE_SMOOTH);
		btnAdicionar.setIcon(new ImageIcon(newImageAdicionar));
		btnAdicionar.setBounds(6, 132, widthBtn, heightBtn);
		panelCrud.add(btnAdicionar);
		
		
		//BOTAO PARA CARREGAR AS TAREFAS PARA EDITA-LAS
		JButton btnCarregar = new JButton("CARREGAR");		
		btnCarregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					carregarCampos(tabelaTarefa);
				} catch (Exception erro) {
					JOptionPane.showMessageDialog(null, "ERRO: btnCarregar - " + erro.getMessage());
				}
				
			}
		});
		ImageIcon iconCarregar = new ImageIcon(TarefaView.class.getResource("/image/iconAtualizar.png"));
		Image imageCarregar = iconCarregar.getImage();
		Image newImageCarregar = imageCarregar.getScaledInstance(newWidth, newHeight, java.awt.Image.SCALE_SMOOTH);
		btnCarregar.setIcon(new ImageIcon(newImageCarregar));
		btnCarregar.setBounds(608, 132, 150, 50);
		panelCrud.add(btnCarregar);
		
		JLabel lblNewLabel_1_1 = new JLabel("Id:");
		lblNewLabel_1_1.setForeground(Color.WHITE);
		lblNewLabel_1_1.setFont(new Font("Calibri", Font.PLAIN, 20));
		lblNewLabel_1_1.setBounds(6, 6, 32, 31);
		panelCrud.add(lblNewLabel_1_1);
		
		txtId = new JTextField();
		txtId.setEditable(false);
		txtId.setColumns(10);
		txtId.setBounds(50, 6, 42, 27);
		panelCrud.add(txtId);

	}
	private void limparConteudo() {
		txtNome.setText("");
		txtDescricao.setText("");
		txtId.setText("");
	}

	
	/**
	 * 
	 * @param model
	 * @throws SQLException
	 */
	private void listarTarefa(DefaultTableModel model) throws SQLException {

		try {
			
			TarefaController tController = new TarefaController();
			model.setNumRows(0);			
			ArrayList<Tarefa> lista = tController.atualizarTarefa();

			for (int i = 0; i < lista.size(); i++) {
				model.addRow(
						new Object[] { 
								lista.get(i).getId(), 
								lista.get(i).getNome(), 
								lista.get(i).getDescricao() });
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "ERRO: Listar tarefa" + e.getMessage());
		}
	}
	
	
	/**
	 * PEGAS O DADOS DA TABELA E LEVA PARA OS CAMPOS PARA EDITA-LA
	 * @param tabelaTarefa
	 */
	private void carregarCampos(JTable tabelaTarefa) {
		
		try {
			
			int setar = tabelaTarefa.getSelectedRow();
			txtId.setText(tabelaTarefa.getModel().getValueAt(setar, 0).toString());
			txtNome.setText(tabelaTarefa.getModel().getValueAt(setar, 1).toString());
			txtDescricao.setText(tabelaTarefa.getModel().getValueAt(setar, 2).toString());
			
		} catch (Exception erro) {
			JOptionPane.showMessageDialog(null, "ERRO: metodo carregar Campo "+erro.getMessage());
		}
		
		
	}
	
	
}
