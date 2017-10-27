package br.edu.ifpr;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class ModelHistory extends AbstractTableModel{
	
	private Connection con;
	PreparedStatement ps;
	ResultSet rs;

	private List<Aluguel> dados = new ArrayList<Aluguel>();
	private String[] colunas = {"ID","Carro","Placa","Cliente"};
	
	public ModelHistory(){
		try{
			con = new FabricaConexao().getConnection();
		}catch (Exception e) {
			System.out.println("Impossivel Conectar ao BD");
		}
	}
	@Override
	public String getColumnName(int column) {
		return colunas[column];
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
				return dados.get(linha).getId();
			case 1:
				return dados.get(linha).getNomeVeiculo();
			case 2:
				return dados.get(linha).getPlaca();
			case 3:
				return dados.get(linha).getNomeCliente();
		}
		return null;
	}
	public void addRow(){
		this.dados.clear();
		
		String select = "SELECT Aluguel.id_aluguel, Aluguel.id_carro, Carros.nome as 'Carro', "
				+ "Carros.placa, Clientes.nome, Clientes.id "
				+ "FROM Aluguel, Carros, Clientes "
				+ "WHERE Aluguel.id_cliente = Clientes.id "
				+ "AND Aluguel.id_carro = Carros.id_carro;";
		try {
			ps = con.prepareStatement(select);
			rs = ps.executeQuery();
			while (rs.next()) {
				Aluguel t = new Aluguel();
				t.setId(rs.getInt("id_aluguel"));
				t.setId_cliente(rs.getInt("id"));
				t.setId_veiculo(rs.getInt("id_carro"));
				t.setNomeVeiculo(rs.getString("Carro"));
				t.setPlaca(rs.getString("placa"));
				t.setNomeCliente(rs.getString("nome"));
				this.dados.add(t);
			}
			ps.close();
			rs.close();
		} catch (Exception e) {
			System.out.println("addRowHistory");
			System.out.println(e);
		}
		this.fireTableDataChanged();
	}
	public Object update(int row){
		return this.dados.get(row);
	}
}
