package br.edu.ifpr;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Driver;

public class FabricaConexao {

	private static final String URL = "jdbc:mysql://localhost/aluguel_veiculos";
	private static final String USUARIO = "root";
	private static final String SENHA = "Naointeressa!@#";

	public Connection getConnection() {
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			return DriverManager.getConnection(URL, USUARIO, SENHA);
		} catch (SQLException e) {
			System.out.println("Fabrica conexao com problema");
			throw new RuntimeException(e);
		}
	}
}