package br.edu.ifpr;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import javax.swing.JTextField;
import javax.swing.JFormattedTextField;
import javax.swing.ImageIcon;

public class TelaCliente extends JFrame {
	
	private Connection con;
	PreparedStatement ps;
	ResultSet rs;

	private JPanel contentPane;
	private JTable table;
	private JScrollPane scroll;
	private JPanel panel;
	private JButton btnInserir;
	private JButton btnAlterar;
	private JButton btnExcluir;
	private JLabel lNome;
	private JTextField txNome;
	private JLabel lblCpf;
	private JFormattedTextField txCpf;
	private JLabel lblTelefone;
	private JFormattedTextField txTelefone;
	private JLabel lblEmail;
	private JTextField txEmail;
	private JLabel lblCelular;
	private JFormattedTextField txCelular;
	private JButton btnCancelar;
	private JButton btnSalvar;
	
	private JInternalFrame internalInsertCliente;
	private JInternalFrame internalEditCliente;
	
	private JLabel edit_lNome;
	private JTextField edit_txNome;
	private JLabel edit_lblCpf;
	private JFormattedTextField edit_txCpf;
	private JLabel edit_lblTelefone;
	private JFormattedTextField edit_txTelefone;
	private JLabel edit_lblEmail;
	private JTextField edit_txEmail;
	private JLabel edit_lblCelular;
	private JFormattedTextField edit_txCelular;
	private JButton edit_btnCancelar;
	private JButton edit_btnSalvar;
	
	private MaskFormatter maskCpf;
	private MaskFormatter maskTelefone;
	private MaskFormatter maskCelular;
	
	Cliente temp;
	ClienteTableModel tableModel = new ClienteTableModel();
	/**
	 * Create the frame.
	 */
	public TelaCliente() {
		try{
			con = new FabricaConexao().getConnection();
		}catch (Exception e) {
			System.out.println("Impossivel Conectar ao BD");
		}
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 648, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		this.setResizable(false);
		this.setTitle("Clientes");
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				temp = (Cliente) tableModel.update(table.getSelectedRow()); //retorna o objeto selecionado
				if(temp.getAlugou() == 1) //desabilita a opcao de excluir para clientes que alugaram carros
					btnExcluir.setVisible(false);
				else
					btnExcluir.setVisible(true);
			}
		});
		contentPane.setLayout(null);
		
		/*
		 * 
		 * INICIO PAINEL INSERIR CLIENTE
		 * 
		 * */
		
		internalEditCliente = new JInternalFrame("Editar Cliente");
		internalEditCliente.setBounds(12, 0, 488, 258);
		internalEditCliente.setVisible(false);
		contentPane.add(internalEditCliente);
		
		edit_lNome = new JLabel("Nome:");
		edit_lNome.setBounds(120, 43, 45, 15);
		
		edit_txNome = new JTextField();
		edit_txNome.setBounds(177, 41, 190, 19);
		edit_txNome.setColumns(10);
		
		edit_lblCpf = new JLabel("CPF:");
		edit_lblCpf.setBounds(134, 66, 31, 15);
		
		try {
			maskCpf = new MaskFormatter("###.###.###-##");
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		edit_txCpf = new JFormattedTextField(maskCpf);
		edit_txCpf.setBounds(177, 66, 114, 19);
		edit_txCpf.setColumns(10);
		
		edit_lblEmail = new JLabel("E-mail:");
		edit_lblEmail.setBounds(118, 93, 47, 15);
		
		edit_txEmail = new JTextField();
		edit_txEmail.setBounds(177, 91, 190, 19);
		edit_txEmail.setColumns(10);
		
		edit_lblTelefone = new JLabel("Telefone:");
		edit_lblTelefone.setBounds(98, 116, 67, 15);
		
		try {
			maskTelefone = new MaskFormatter("(##)####-####");
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		edit_txTelefone = new JFormattedTextField(maskTelefone);
		edit_txTelefone.setBounds(177, 116, 114, 19);
		
		edit_lblCelular = new JLabel("Celular:");
		edit_lblCelular.setBounds(110, 143, 55, 15);
		
		try {
			maskCelular = new MaskFormatter("(##)#####-####");
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		edit_txCelular = new JFormattedTextField(maskCelular);
		edit_txCelular.setBounds(177, 141, 114, 19);
		
		edit_btnCancelar = new JButton("Cancelar");
		edit_btnCancelar.setBounds(252, 188, 116, 26);
		edit_btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				internalEditCliente.doDefaultCloseAction();
				btnInserir.setVisible(true);
				btnExcluir.setVisible(true);
			}
		});
		edit_btnCancelar.setIcon(new ImageIcon(TelaCliente.class.getResource("/br/edu/resources/cancel.png")));
		
		edit_btnSalvar = new JButton("Salvar");
		edit_btnSalvar.setBounds(374, 188, 98, 26);
		edit_btnSalvar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(edit_txNome.getText().isEmpty())
					edit_txNome.requestFocus();
				else if(edit_txCpf.getText().isEmpty())
					edit_txCpf.requestFocus();
				else if(edit_txEmail.getText().isEmpty())
					edit_txEmail.requestFocus();
				else if(edit_txTelefone.getText().isEmpty())
					edit_txTelefone.requestFocus();
				else if(edit_txCelular.getText().isEmpty())
					edit_txCelular.requestFocus();
				else{
					internalEditCliente.doDefaultCloseAction();
					update();
					refresh();
					btnInserir.setVisible(true);
					btnExcluir.setVisible(true);					
				}
			}
		});
		edit_btnSalvar.setIcon(new ImageIcon(TelaCliente.class.getResource("/br/edu/resources/database_save.png")));
		internalEditCliente.getContentPane().setLayout(null);
		internalEditCliente.getContentPane().add(edit_lblCelular);
		internalEditCliente.getContentPane().add(edit_lblTelefone);
		internalEditCliente.getContentPane().add(edit_lblEmail);
		internalEditCliente.getContentPane().add(edit_lNome);
		internalEditCliente.getContentPane().add(edit_lblCpf);
		internalEditCliente.getContentPane().add(edit_txCpf);
		internalEditCliente.getContentPane().add(edit_txNome);
		internalEditCliente.getContentPane().add(edit_txEmail);
		internalEditCliente.getContentPane().add(edit_txTelefone);
		internalEditCliente.getContentPane().add(edit_txCelular);
		internalEditCliente.getContentPane().add(edit_btnCancelar);
		internalEditCliente.getContentPane().add(edit_btnSalvar);
		
		/*
		 * FIM PAINEL EDITAR CLIENTE
		 ***************************
		 *
		 * INICIO PAINEL INSERIR CLIENTE
		 * 
		 * */
		
		
		internalInsertCliente = new JInternalFrame("Inserir Cliente");
		internalInsertCliente.setBounds(12, 0, 488, 258);
		internalInsertCliente.setVisible(false);
		contentPane.add(internalInsertCliente);
		
		lNome = new JLabel("Nome:");
		lNome.setBounds(120, 43, 45, 15);
		
		txNome = new JTextField();
		txNome.setBounds(177, 41, 190, 19);
		txNome.setColumns(10);
		
		lblCpf = new JLabel("CPF:");
		lblCpf.setBounds(134, 66, 31, 15);
		
		txCpf = new JFormattedTextField(maskCpf);
		txCpf.setBounds(177, 66, 114, 19);
		txCpf.setColumns(10);
		
		lblEmail = new JLabel("E-mail:");
		lblEmail.setBounds(118, 93, 47, 15);
		
		txEmail = new JTextField();
		txEmail.setBounds(177, 91, 190, 19);
		txEmail.setColumns(10);
		
		lblTelefone = new JLabel("Telefone:");
		lblTelefone.setBounds(98, 116, 67, 15);
		
		txTelefone = new JFormattedTextField(maskTelefone);
		txTelefone.setBounds(177, 116, 114, 19);
		
		lblCelular = new JLabel("Celular:");
		lblCelular.setBounds(110, 143, 55, 15);
		
		txCelular = new JFormattedTextField(maskCelular);
		txCelular.setBounds(177, 141, 114, 19);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(252, 188, 116, 26);
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				internalInsertCliente.doDefaultCloseAction();
				btnAlterar.setVisible(true);
				btnExcluir.setVisible(true);
			}
		});
		btnCancelar.setIcon(new ImageIcon(TelaCliente.class.getResource("/br/edu/resources/cancel.png")));
		
		btnSalvar = new JButton("Salvar");
		btnSalvar.setBounds(374, 188, 98, 26);
		btnSalvar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(txNome.getText().isEmpty())
					txNome.requestFocus();
				else if(txCpf.getText().isEmpty())
					txCpf.requestFocus();
				else if(txEmail.getText().isEmpty())
					txEmail.requestFocus();
				else if(txTelefone.getText().isEmpty())
					txTelefone.requestFocus();
				else if(txCelular.getText().isEmpty())
					txCelular.requestFocus();
				else{
					internalInsertCliente.doDefaultCloseAction();
					inserir();
					refresh();
					btnExcluir.setVisible(true);
					btnAlterar.setVisible(true);					
				}
			}
		});
		btnSalvar.setIcon(new ImageIcon(TelaCliente.class.getResource("/br/edu/resources/database_save.png")));
		internalInsertCliente.getContentPane().setLayout(null);
		internalInsertCliente.getContentPane().add(lblCelular);
		internalInsertCliente.getContentPane().add(lblTelefone);
		internalInsertCliente.getContentPane().add(lblEmail);
		internalInsertCliente.getContentPane().add(lNome);
		internalInsertCliente.getContentPane().add(lblCpf);
		internalInsertCliente.getContentPane().add(txCpf);
		internalInsertCliente.getContentPane().add(txNome);
		internalInsertCliente.getContentPane().add(txEmail);
		internalInsertCliente.getContentPane().add(txTelefone);
		internalInsertCliente.getContentPane().add(txCelular);
		internalInsertCliente.getContentPane().add(btnCancelar);
		internalInsertCliente.getContentPane().add(btnSalvar);
		
		panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(512, 67, 133, 115);
		contentPane.add(panel);
		
		btnInserir = new JButton("Inserir");
		btnInserir.setIcon(new ImageIcon(TelaCliente.class.getResource("/br/edu/resources/user_add.png")));
		btnInserir.setBounds(0, 0, 117, 25);
		btnInserir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				initInserir();
			}
		});
		panel.add(btnInserir);
		
		btnAlterar = new JButton("Alterar");
		btnAlterar.setIcon(new ImageIcon(TelaCliente.class.getResource("/br/edu/resources/cog_edit.png")));
		btnAlterar.setBounds(0, 37, 117, 25);
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(temp != null){
					initUpdate();
				}else{
					JOptionPane.showMessageDialog(null, "Favor escolher um Registro");
				}
			}
		});
		panel.add(btnAlterar);
		
		btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(temp != null){
					delete();
					refresh();
				}else{
					JOptionPane.showMessageDialog(null, "Favor escolher um Registro");
				}
			}
		});
		btnExcluir.setIcon(new ImageIcon(TelaCliente.class.getResource("/br/edu/resources/user_delete.png")));
		btnExcluir.setBounds(0, 74, 117, 25);
		panel.add(btnExcluir);
		
		table.setModel(tableModel);
		scroll = new JScrollPane(table);
		scroll.setBounds(12, 5, 488, 253);
		contentPane.add(scroll);
	}
	public void refresh(){
		tableModel.addRow();
		temp = null;
	}
	public void initInserir(){
		internalInsertCliente.setVisible(true);
		btnAlterar.setVisible(false);
		btnExcluir.setVisible(false);

		txNome.setText("");
		txCpf.setText("");
		txEmail.setText("");
		txTelefone.setText("");
		txCelular.setText("");
	}
	public void initUpdate(){
		internalEditCliente.setVisible(true);
		btnInserir.setVisible(false);
		btnExcluir.setVisible(false);
		
		edit_txNome.setText(temp.getNome());
		edit_txCpf.setText(temp.getCpf());
		edit_txEmail.setText(temp.getEmail());
		edit_txTelefone.setText(temp.getTelefone());
		edit_txCelular.setText(temp.getCelular());		
	}
	public void inserir(){
		String insert = "INSERT INTO Clientes "
				+ "(nome,cpf,email,telefone,celular,alugou) values (?,?,?,?,?,?)";
		try {
			ps = con.prepareStatement(insert);
			ps.setString(1, txNome.getText());
			ps.setString(2, txCpf.getText());
			ps.setString(3, txEmail.getText());
			ps.setString(4, txTelefone.getText());
			ps.setString(5, txCelular.getText());
			ps.setInt(6, 0);
			ps.execute();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void update(){
		String atualiza = "Update Clientes "
				+ "set nome = ?, cpf = ?, email = ?, telefone = ?, celular = ? where id = ?";
		try {
			ps = con.prepareStatement(atualiza);
			ps.setString(1, edit_txNome.getText());
			ps.setString(2, edit_txCpf.getText());
			ps.setString(3, edit_txEmail.getText());
			ps.setString(4, edit_txTelefone.getText());
			ps.setString(5, edit_txCelular.getText());
			ps.setInt(6, temp.getId_cliente());
			ps.executeUpdate();
			ps.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	public void delete(){
		if (JOptionPane.showConfirmDialog(null, "Deseja realmente Excluir?", "Confirmação", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			String remove = "delete from Clientes Where id = ?";
			try {
				ps = con.prepareStatement(remove);
				ps.setInt(1, temp.getId_cliente());
				ps.executeUpdate();
				ps.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
