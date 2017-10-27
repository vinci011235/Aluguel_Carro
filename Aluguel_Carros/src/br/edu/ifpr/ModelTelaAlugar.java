package br.edu.ifpr;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class ModelTelaAlugar extends AbstractTableModel{
	
	private Connection con;
	PreparedStatement ps;
	ResultSet rs;

	private List<Carro> dados = new ArrayList<Carro>();
	private String[] colunas = {"ID","Nome","Placa"};
	
	public ModelTelaAlugar(){
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
				return dados.get(linha).getNome();
			case 2:
				return dados.get(linha).getPlaca();
		}
		return null;
	}
	public void addRow(){
		this.dados.clear();
		
		String select = "SELECT * FROM Carros WHERE disponivel = 1";
		try {
			ps = con.prepareStatement(select);
			rs = ps.executeQuery();
			while (rs.next()) {
				Carro t = new Carro();
				t.setId(rs.getInt("id_carro"));
				t.setNome(rs.getString("nome"));
				t.setCombustivel(rs.getString("combustivel"));
				t.setPotencia(rs.getInt("potencia"));
				t.setPlaca(rs.getString("placa"));
				t.setCor(rs.getString("cor"));
				t.setDisponivel(rs.getInt("disponivel"));
				this.dados.add(t);
			}
			ps.close();
			rs.close();
		} catch (Exception e) {
			System.out.println("addRow");
			System.out.println(e);
		}
		this.fireTableDataChanged();
	}
	public Object update(int row){
		return this.dados.get(row);
	}

}
