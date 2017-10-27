package br.edu.ifpr;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;
import javax.swing.JTable;
import javax.swing.JButton;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import javax.swing.JInternalFrame;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;

public class TelaCarro extends JFrame {

	private JPanel contentPane;
	private JPanel panelButton;
	private JButton btnAlterar;
	private JButton btnInserir;
	private JButton btnExcluir;
	private JInternalFrame internalinsertCarro;

	private JTextField txNome;
	private JLabel lblCombustivel;
	private JTextField txCombustivel;
	private JLabel lblPotncia;
	private JTextField txPotencia;
	private JTextField formattedPlaca;
	private JLabel lblPlaca;
	private JTextField txtCor;
	private JScrollPane scroll;
	
	CarroTableModel tableModel = new CarroTableModel();
	Carro temp = null;
	
	private Connection con;
	PreparedStatement ps;
	ResultSet rs;
	
	private JTable table;
	private JInternalFrame internalEditCarro;
	private JLabel label;
	private JTextField editar_txNome;
	private JLabel label_1;
	private JTextField editar_txCombustivel;
	private JLabel label_2;
	private JTextField editar_txPotencia;
	private JLabel label_3;
	private JTextField editar_formattedPlaca;
	private JLabel label_4;
	private JTextField editar_txCor;
	private JButton editarBtnGravar;
	private JButton editarBtnCancelar;
	
	private String numeros = "0987654321";
	private MaskFormatter maskPlaca;

	public TelaCarro(){
		try{
			con = new FabricaConexao().getConnection();
		}catch (Exception e) {
			System.out.println("Impossivel Conectar ao BD");
		}

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 604, 300);
		this.setResizable(false);
		this.setTitle("Carros");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		/*
		 * 
		 * INICIO PAINEL DE EDITAR CARRO
		 * 
		 * */
		internalEditCarro = new JInternalFrame("Editar Carro");
		internalEditCarro.setBounds(12, 12, 435, 239);
		contentPane.add(internalEditCarro);
		internalEditCarro.getContentPane().setLayout(null);
		
		label = new JLabel("Nome:");
		label.setBounds(88, 14, 45, 15);
		internalEditCarro.getContentPane().add(label);
		
		editar_txNome = new JTextField();
		editar_txNome.setColumns(10);
		editar_txNome.setBounds(139, 12, 180, 19);
		internalEditCarro.getContentPane().add(editar_txNome);
		
		label_1 = new JLabel("Combustivel:");
		label_1.setBounds(42, 39, 91, 15);
		internalEditCarro.getContentPane().add(label_1);
		
		editar_txCombustivel = new JTextField();
		editar_txCombustivel.setColumns(10);
		editar_txCombustivel.setBounds(139, 37, 114, 19);
		internalEditCarro.getContentPane().add(editar_txCombustivel);
		
		label_2 = new JLabel("Potência:");
		label_2.setBounds(64, 63, 70, 15);
		internalEditCarro.getContentPane().add(label_2);
		
		editar_txPotencia = new JTextField();
		editar_txPotencia.setColumns(10);
		editar_txPotencia.setBounds(139, 61, 58, 19);
		editar_txPotencia.addKeyListener(new KeyListener(){
			@Override
			public void keyTyped(KeyEvent e) {
				if(!numeros.contains(e.getKeyChar()+"")){
					e.consume();
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
		internalEditCarro.getContentPane().add(editar_txPotencia);
		
		label_3 = new JLabel("Placa:");
		label_3.setBounds(88, 89, 45, 15);
		internalEditCarro.getContentPane().add(label_3);
		
		try {
			maskPlaca = new MaskFormatter("UUU-####");
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		maskPlaca.setValidCharacters("ABCDEFGHIJKLMNOPQRSTUVWXYZ0987654321");
		editar_formattedPlaca = new JFormattedTextField(maskPlaca);
		editar_formattedPlaca.setBounds(139, 85, 70, 19);
		internalEditCarro.getContentPane().add(editar_formattedPlaca);
		
		label_4 = new JLabel("Cor:");
		label_4.setBounds(102, 110, 31, 15);
		internalEditCarro.getContentPane().add(label_4);
		
		editar_txCor = new JTextField();
		editar_txCor.setColumns(10);
		editar_txCor.setBounds(139, 108, 114, 19);
		internalEditCarro.getContentPane().add(editar_txCor);
		
		editarBtnGravar = new JButton("Gravar");
		editarBtnGravar.setIcon(new ImageIcon(TelaCarro.class.getResource("/br/edu/resources/database_save.png")));
		editarBtnGravar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(editar_txNome.getText().isEmpty())
					editar_txNome.requestFocus();
				else if(editar_txCombustivel.getText().isEmpty())
					editar_txCombustivel.requestFocus();
				else if(editar_txPotencia.getText().isEmpty())
					editar_txPotencia.requestFocus();
				else if(editar_formattedPlaca.getText().isEmpty())
					editar_formattedPlaca.requestFocus();
				else if(editar_txCor.getText().isEmpty())
					editar_txCor.requestFocus();
				else{
					update();
					internalEditCarro.doDefaultCloseAction();
					btnInserir.setVisible(true);
					btnExcluir.setVisible(true);
					refresh();					
				}
			}
		});
		editarBtnGravar.setBounds(310, 176, 103, 19);
		internalEditCarro.getContentPane().add(editarBtnGravar);
		
		editarBtnCancelar = new JButton("Cancelar");
		editarBtnCancelar.setIcon(new ImageIcon(TelaCarro.class.getResource("/br/edu/resources/cancel.png")));
		editarBtnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				internalEditCarro.setVisible(false);
				btnInserir.setVisible(true);
				btnExcluir.setVisible(true);
			}
		});
		editarBtnCancelar.setBounds(203, 176, 116, 19);
		internalEditCarro.getContentPane().add(editarBtnCancelar);
		internalEditCarro.setResizable(false);
		/*
		 * 
		 * FIM PAINEL EDITAR CARRO
		 * ************************
		 * 
		 * INICIO PAINEL INSERIR CARRO
		 * 
		 * */
		internalinsertCarro = new JInternalFrame("Inserir Carro");
		internalinsertCarro.setResizable(false);
		internalinsertCarro.setBounds(12, 12, 435, 239);
		contentPane.add(internalinsertCarro);
		internalinsertCarro.getContentPane().setLayout(null);
		
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setBounds(88, 14, 45, 15);
		internalinsertCarro.getContentPane().add(lblNome);
		
		txNome = new JTextField();
		txNome.setBounds(139, 12, 180, 19);
		internalinsertCarro.getContentPane().add(txNome);
		txNome.setColumns(10);
		
		lblCombustivel = new JLabel("Combustivel:");
		lblCombustivel.setBounds(42, 39, 91, 15);
		internalinsertCarro.getContentPane().add(lblCombustivel);
		
		txCombustivel = new JTextField();
		txCombustivel.setBounds(139, 37, 114, 19);
		internalinsertCarro.getContentPane().add(txCombustivel);
		txCombustivel.setColumns(10);
		
		lblPotncia = new JLabel("Potência:");
		lblPotncia.setBounds(63, 63, 70, 15);
		internalinsertCarro.getContentPane().add(lblPotncia);
		
		txPotencia = new JTextField();
		txPotencia.setBounds(139, 61, 58, 19);
		txPotencia.addKeyListener(new KeyListener(){
			@Override
			public void keyTyped(KeyEvent e) {
				if(!numeros.contains(e.getKeyChar()+"")){
					e.consume();
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
		internalinsertCarro.getContentPane().add(txPotencia);
		txPotencia.setColumns(10);
		
		lblPlaca = new JLabel("Placa:");
		lblPlaca.setBounds(88, 89, 45, 15);
		internalinsertCarro.getContentPane().add(lblPlaca);	
		
		formattedPlaca = new JFormattedTextField(maskPlaca);
		formattedPlaca.setBounds(139, 85, 70, 19);
		internalinsertCarro.getContentPane().add(formattedPlaca);
		
		JLabel lblCor = new JLabel("Cor:");
		lblCor.setBounds(102, 110, 31, 15);
		internalinsertCarro.getContentPane().add(lblCor);
		
		txtCor = new JTextField();
		txtCor.setBounds(139, 108, 114, 19);
		internalinsertCarro.getContentPane().add(txtCor);
		txtCor.setColumns(10);
		
		JButton btnGravar = new JButton("Gravar");
		btnGravar.setIcon(new ImageIcon(TelaCarro.class.getResource("/br/edu/resources/database_save.png")));
		btnGravar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(txNome.getText().isEmpty())
					txNome.requestFocus();
				else if(txCombustivel.getText().isEmpty())
					txCombustivel.requestFocus();
				else if(txPotencia.getText().isEmpty())
					txPotencia.requestFocus();
				else if(formattedPlaca.getText().isEmpty())
					formattedPlaca.requestFocus();
				else if(txtCor.getText().isEmpty())
					txtCor.requestFocus();				
				else{
					inserir();
					internalinsertCarro.doDefaultCloseAction();
					btnAlterar.setVisible(true);
					btnExcluir.setVisible(true);
					refresh();
					
				}
			}
		});
		btnGravar.setBounds(310, 176, 103, 19);
		internalinsertCarro.getContentPane().add(btnGravar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setIcon(new ImageIcon(TelaCarro.class.getResource("/br/edu/resources/cancel.png")));
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				internalinsertCarro.doDefaultCloseAction();
				btnAlterar.setVisible(true);
				btnExcluir.setVisible(true);
			}
		});
		btnCancelar.setBounds(200, 176, 116, 19);
		internalinsertCarro.getContentPane().add(btnCancelar);
		
		/*
		 * 
		 * FIM PAINEL INSERIR CARRO
		 * ************************
		 * 
		 * INICIO PAINEL DE BOTÕES
		 * */
		
		panelButton = new JPanel();
		panelButton.setBounds(459, 71, 133, 115);
		contentPane.add(panelButton);
		panelButton.setLayout(null);
		
		btnInserir = new JButton("Inserir");
		btnInserir.setIcon(new ImageIcon(TelaCarro.class.getResource("/br/edu/resources/car_add.png")));
		btnInserir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				initInserir();
			}
		});
		btnInserir.setBounds(0, 0, 117, 25);
		panelButton.add(btnInserir);
		
		btnAlterar = new JButton("Alterar");
		btnAlterar.setIcon(new ImageIcon(TelaCarro.class.getResource("/br/edu/resources/cog_edit.png")));
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(temp != null){
					initUpdate();
				}else{
					JOptionPane.showMessageDialog(null, "Favor escolher um Veiculo");
				}
			}
		});
		btnAlterar.setBounds(0, 37, 117, 25);
		panelButton.add(btnAlterar);
		
		btnExcluir = new JButton("Excluir");
		btnExcluir.setIcon(new ImageIcon(TelaCarro.class.getResource("/br/edu/resources/car_delete.png")));
		btnExcluir.setBounds(0, 74, 117, 25);
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(temp != null){
					delete();
					refresh();
				}else{
					JOptionPane.showMessageDialog(null, "Favor escolher um Veiculo");
				}
				
			}
		});
		panelButton.add(btnExcluir);
		
		/*
		 * FIM PAINEL DE BOTÕES
		 * ********************
		 * 
		 * INICIO TABLE E TABLEMODEL
		 * 
		 * */
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				temp = (Carro) tableModel.update(table.getSelectedRow()); //retorna o objeto selecionado
				if(temp.getDisponivel() == 0){ //desabilita a opcao de excluir e alterar carros alugados
					btnAlterar.setVisible(false);
					btnExcluir.setVisible(false);
				}else{
					btnAlterar.setVisible(true);
					btnExcluir.setVisible(true);
				}
			}
		});
		table.setModel(tableModel);
		scroll = new JScrollPane(table);
		scroll.setBounds(12, 12, 435, 239);
		contentPane.add(scroll);
	}

	public void initInserir(){
		internalinsertCarro.setVisible(true);
		txNome.setText("");
		txCombustivel.setText("");
		txPotencia.setText("");
		formattedPlaca.setText("AAA-0000");
		txtCor.setText("");
		btnAlterar.setVisible(false);
		btnExcluir.setVisible(false);
	}
	
	public void initUpdate(){
		internalEditCarro.setVisible(true);
		editar_txNome.setText(temp.getNome());
		editar_txCombustivel.setText(temp.getCombustivel());
		editar_txPotencia.setText(String.valueOf((temp.getPotencia())));
		editar_formattedPlaca.setText(temp.getPlaca());
		editar_txCor.setText(temp.getCor());
		btnInserir.setVisible(false);
		btnExcluir.setVisible(false);
	}
	public void refresh(){
		tableModel.addRow();
		temp = null;
	}
	public void inserir(){
		String insert = "INSERT INTO Carros "
				+ "(nome,combustivel,potencia,placa,cor,disponivel) values (?,?,?,?,?,?)";
		try {
			ps = con.prepareStatement(insert);
			ps.setString(1, txNome.getText());
			ps.setString(2, txCombustivel.getText());
			ps.setInt(3, Integer.parseInt(txPotencia.getText()));
			ps.setString(4, formattedPlaca.getText());
			ps.setString(5, txtCor.getText());
			ps.setInt(6, 1);
			ps.execute();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void update(){
		String atualiza = "Update Carros "
				+ "set nome = ?, combustivel = ?, potencia = ?, placa = ?, cor = ? where id_carro = ?";
		try {
			ps = con.prepareStatement(atualiza);
			ps.setString(1, editar_txNome.getText());
			ps.setString(2, editar_txCombustivel.getText());
			ps.setInt(3, Integer.parseInt(editar_txPotencia.getText()));
			ps.setString(4, editar_formattedPlaca.getText());
			ps.setString(5, editar_txCor.getText());
			ps.setInt(6, temp.getId());
			ps.executeUpdate();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void delete(){
		if (JOptionPane.showConfirmDialog(null, "Deseja realmente Excluir?", "Confirmação", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			String remove = "delete from Carros Where id_carro = ?";
			try {
				ps = con.prepareStatement(remove);
				ps.setInt(1, temp.getId());
				ps.executeUpdate();
				ps.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
