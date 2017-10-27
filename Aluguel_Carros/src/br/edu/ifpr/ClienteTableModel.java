package br.edu.ifpr;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class ClienteTableModel extends AbstractTableModel{
	private Connection con;
	PreparedStatement ps;
	ResultSet rs;

	private List<Cliente> dados = new ArrayList<Cliente>();
	private String[] colunas = {"","","","","","",""};
	
	public ClienteTableModel(){
		try{
			con = new FabricaConexao().getConnection();
		}catch (Exception e) {
			System.out.println("Impossivel Conectar ao BD");
		}
		addRow();
	}
	
	@Override
	public String getColumnName(int column) {
	
		String select = "SELECT * FROM Clientes";
		try {
			ps = con.prepareStatement(select);
			rs = ps.executeQuery();
			
			ResultSetMetaData rsmd = rs.getMetaData();
			
			ps.close();
			rs.close();
			return colunas[column] = rsmd.getColumnName(column+1);
		} catch (SQLException e) {
			System.out.println("404 Column name");
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public int getColumnCount() {
		return colunas.length;
	}

	@Override
	public int getRowCount() {
		return dados.size();
	}

	@Override
	public Object getValueAt(int linha, int coluna) {
		
		switch(coluna){
			case 0:
				return dados.get(linha).getId_cliente();
			case 1:
				return dados.get(linha).getNome();
			case 2:
				return dados.get(linha).getCpf();
			case 3:
				return dados.get(linha).getEmail();
			case 4:
				return dados.get(linha).getTelefone();
			case 5:
				return dados.get(linha).getCelular();
			case 6:
				if(dados.get(linha).getAlugou() == 1)
					return '✔';
				else
					return '✘';
		}
		return null;
	}
	
	public void addRow(){
		this.dados.clear();
		
		String select = "SELECT * FROM Clientes";
		try {
			ps = con.prepareStatement(select);
			rs = ps.executeQuery();
			while (rs.next()) {
				Cliente t = new Cliente();
				t.setId_cliente(rs.getInt("id"));
				t.setNome(rs.getString("nome"));
				t.setCpf(rs.getString("cpf"));
				t.setEmail(rs.getString("email"));
				t.setTelefone(rs.getString("telefone"));
				t.setCelular(rs.getString("celular"));
				t.setAlugou(rs.getInt("alugou"));
				this.dados.add(t);
			}
			ps.close();
			rs.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		this.fireTableDataChanged();
	}
	public Object update(int row){
		return this.dados.get(row);
	}
}
