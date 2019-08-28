package br.com.gustavo.jdbc.testes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestaSelect {

	public static void main(String[] args) {

		try {
			Connection conector = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/loja-virtual", "SA", "");
			//Criando um Statement
			Statement statement = conector.createStatement();
			boolean resultado = statement.execute("select * from Produto");
			System.out.println(resultado);
			
			ResultSet resultSet = statement.getResultSet();
			
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String nome = resultSet.getString("nome");
				String descricao = resultSet.getString("descricao");

				System.out.println("id: " + id + " nome: " + nome + " descricao: " + descricao);
			}

			resultSet.close();
			statement.close();
			conector.close();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

	}

}
