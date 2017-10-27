package br.edu.ifpr;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class CarroTableModel extends AbstractTableModel{

	private Connection con;
	PreparedStatement ps;
	ResultSet rs;

	private List<Carro> dados = new ArrayList<Carro>();
	private String[] colunas = {"","","","","","",""};
	
	public CarroTableModel(){
		try{
			con = new FabricaConexao().getConnection();
		}catch (Exception e) {
			System.out.println("Impossivel Conectar ao BD");
		}
		addRow();
	}
	
	@Override
	public String getColumnName(int column) {
	
		String select = "SELECT * FROM Carros";
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
				return dados.get(linha).getId();
			case 1:
				return dados.get(linha).getNome();
			case 2:
				return dados.get(linha).getCombustivel();
			case 3:
				return dados.get(linha).getPotencia();
			case 4:
				return dados.get(linha).getPlaca();
			case 5:
				return dados.get(linha).getCor();
			case 6:
				if(dados.get(linha).getDisponivel() == 1){
					return '✔';
				}else{
					return '✘';
				}
		}
		return null;
	}
	
	public void addRow(){
		this.dados.clear();
		
		String select = "SELECT * FROM Carros";
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
			// TODO: handle exception
		}
		this.fireTableDataChanged();
	}
	public Object update(int row){
		return this.dados.get(row);
	}
}
