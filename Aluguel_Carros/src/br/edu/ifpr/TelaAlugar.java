package br.edu.ifpr;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.SynchronousQueue;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;
import java.awt.Panel;
import javax.swing.Box;
import javax.swing.JSeparator;
import javax.swing.ImageIcon;

public class TelaAlugar extends JFrame {

	Carro temp = null;
	Aluguel temp1 = null;
	ModelTelaAlugar tableModel = new ModelTelaAlugar();
	ModelHistory tableHistoryModel = new ModelHistory();
	private Connection con;
	PreparedStatement ps;
	ResultSet rs;
	private JPanel contentPane;
	private DefaultComboBoxModel defaultComboBoxCliente;
	private DefaultComboBoxModel defaultComboBoxCarros;
	private JComboBox cbCliente;
	
	private List<String> clientes = new ArrayList<String>();
	private JScrollPane scrollPane;
	private JButton btnGravar;
	private JTable table;
	private JTable table_history;
	private JButton btnReceber;
	/**
	 * Create the frame.
	 */
	public TelaAlugar() {
		try{
			con = new FabricaConexao().getConnection();
		}catch (Exception e) {
			System.out.println("Impossivel Conectar ao BD");
		}
		refreshClientes();
		refreshTable();
	
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 563, 307);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblCliente = new JLabel("Cliente:");
		lblCliente.setBounds(12, 9, 70, 15);
		contentPane.add(lblCliente);
		
		defaultComboBoxCliente = new DefaultComboBoxModel(clientes.toArray());
		cbCliente = new JComboBox();
		cbCliente.setModel(defaultComboBoxCliente);
		cbCliente.setBounds(74, 4, 170, 24);
		contentPane.add(cbCliente);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				temp = (Carro) tableModel.update(table.getSelectedRow()); //retorna o objeto selecionado
			}
		});
		table.setModel(tableModel);
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(12, 36, 258, 195);
		contentPane.add(scrollPane);
		
		btnGravar = new JButton("Gravar");
		btnGravar.setIcon(new ImageIcon(TelaAlugar.class.getResource("/br/edu/resources/database_save.png")));
		
		if(clientes.isEmpty()){
			JOptionPane.showMessageDialog(null, "Você não possui clientes cadastrados");
			btnGravar.setVisible(false);
		}else{
			btnGravar.setVisible(true);
		}
		
		btnGravar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(temp == null){
					JOptionPane.showMessageDialog(null, "Selecione um veiculo");
				}else{
					inserir();
					refreshTable();
				}
			}
		});
		btnGravar.setBounds(85, 246, 116, 19);
		contentPane.add(btnGravar);
		
		
		btnReceber = new JButton("Receber");
		btnReceber.setIcon(new ImageIcon(TelaAlugar.class.getResource("/br/edu/resources/cart_remove.png")));
		btnReceber.setVisible(false);
		btnReceber.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
					receber();
					refreshTable();
					btnReceber.setVisible(false);
			}
		});
		btnReceber.setBounds(390, 246, 116, 19);
		contentPane.add(btnReceber);
		
		table_history = new JTable();
		table_history.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				temp1 = (Aluguel) tableHistoryModel.update(table_history.getSelectedRow()); //retorna o objeto selecionado
				btnReceber.setVisible(true);
			}
		});
		table_history.setModel(tableHistoryModel);
		JScrollPane historyPane = new JScrollPane(table_history);
		historyPane.setBounds(306, 4, 236, 227);
		contentPane.add(historyPane);
		
		this.setResizable(false);
		this.setTitle("Alugar");
		
	}
	public void refreshTable(){
		tableModel.addRow();
		tableHistoryModel.addRow();
		temp = null;
		temp1 = null;
	}
	public void refreshClientes(){
		String select = "SELECT nome FROM Clientes";
		try {
			ps = con.prepareStatement(select);
			rs = ps.executeQuery();
			while (rs.next()) {
				clientes.add(rs.getString("nome"));
			}
		} catch (Exception e) {
			System.out.println("refreshClientes");
			System.out.println(e);
		}
	}
	public int idCliente(){
		String nome = cbCliente.getSelectedItem().toString();
		int id = -1;
		String consulta = "Select id from Clientes where nome="+"\""+nome+"\""+";";
		
		try {
			ps = con.prepareStatement(consulta);
			rs = ps.executeQuery();
			rs.next();
			id = rs.getInt(("id"));
		} catch (Exception e) {
			System.out.println("idCliente");
			System.out.println(e);
		}
		return id;
	}
	public void inserir(){
		String insert = "INSERT INTO Aluguel "
				+ "(id_cliente,id_carro) values (?,?)";
		int id_cliente = idCliente();
		try {
			ps = con.prepareStatement(insert);
			ps.setInt(1, id_cliente);
			ps.setInt(2, temp.getId());
			ps.execute();
			update(idCliente(), temp.getId());
		} catch (SQLException e) {
			System.out.println("inserir");
			System.out.println(e);
		}
	}
	public void update(int id_cliente, int id_carro){
		String atualizaCliente = "Update Clientes "
				+ "set alugou = ? where id = ?";
		try {
			ps = con.prepareStatement(atualizaCliente);
			ps.setInt(1, 1);
			ps.setInt(2, id_cliente);
			ps.executeUpdate();
		} catch (Exception e) {
			System.out.println("update");
			System.out.println(e);
		}
		
		String atualizaCarro = "Update Carros "
				+ "set disponivel = ? where id_carro = ?";
		
		try {
			ps = con.prepareStatement(atualizaCarro);
			ps.setInt(1, 0);
			ps.setInt(2, id_carro);
			ps.executeUpdate();
			ps.close();
			rs.close();
		} catch (Exception e) {
			System.out.println("atualizaCarro");
			System.out.println(e);
		}
	}
	public void receber(){
		if (JOptionPane.showConfirmDialog(null, "Deseja realmente Receber esse veículo?", "Confirmação", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			String remove = "delete from Aluguel Where id_aluguel = ?";
			try {
				ps = con.prepareStatement(remove);
				ps.setInt(1, temp1.getId());
				ps.executeUpdate();
				updateHistory(temp1.getId_cliente(), temp1.getId_veiculo());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	//refatorar
	public void updateHistory(int id_cliente, int id_carro){
		String atualizaCliente = "Update Clientes "
				+ "set alugou = ? where id = ?";
		try {
			ps = con.prepareStatement(atualizaCliente);
			ps.setInt(1, 0);
			ps.setInt(2, id_cliente);
			ps.executeUpdate();
		} catch (Exception e) {
			System.out.println("update");
			System.out.println(e);
		}
		
		String atualizaCarro = "Update Carros "
				+ "set disponivel = ? where id_carro = ?";
		
		try {
			ps = con.prepareStatement(atualizaCarro);
			ps.setInt(1, 1);
			ps.setInt(2, id_carro);
			ps.executeUpdate();
			ps.close();
			rs.close();
		} catch (Exception e) {
			System.out.println("atualizaCarro");
			System.out.println(e);
		}
	}
}
