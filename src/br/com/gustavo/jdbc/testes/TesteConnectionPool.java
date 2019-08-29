package br.com.gustavo.jdbc.testes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import br.com.gustavo.jdbc.models.BancoDataSource;

public class TesteConnectionPool {

	public static void main(String[] args) throws SQLException {

		//A prática do pool de conexões consiste em deixar um número fixo ou dinâmico de conexões
		//abertas e reciclá-las utilizando em novas requisições.

		adicionandoComPool();
		adicionandoSemPool();
	}


	private static void adicionandoSemPool() throws SQLException {

		long inicio = System.currentTimeMillis();

		Connection connection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/loja-virtual", "SA", "");
		Statement statement = connection.createStatement();
		for(int i = 0; i <= 100; i++) {
			try{
				String nome = "produto " + i;
				String descricao = "descricao " + i;

				String sql = "INSERT INTO Produto (nome, descricao) values('" + nome +"','" + descricao +"')";
				statement.execute(sql);
			}catch(Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
				System.out.println("Erro encontrado, executando Rollback.");
				connection.rollback();
			}
		}
		connection.close();
		statement.close();

		long fim = System.currentTimeMillis();
		System.out.println("Tempo de execução SEM Pool: " + (fim - inicio));
	}


	private static void adicionandoComPool() throws SQLException {

		long inicio = System.currentTimeMillis();

		String sql = "INSERT INTO Produto (nome, descricao) values(?,?)";

		Connection connection = new BancoDataSource().getConnection();
		PreparedStatement statement = connection.prepareStatement(sql);
		for(int i = 0; i <= 100; i++) {
			try{
				adiciona("produto: " + i, "descricao " + i, statement);
			}catch(Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
				System.out.println("Erro encontrado, executando Rollback.");
				connection.rollback();
			}
		}
		connection.close();
		statement.close();

		long fim = System.currentTimeMillis();
		System.out.println("Tempo de execução COM Pool: " + (fim - inicio));
	}


	private static void adiciona(String nome, String descricao, PreparedStatement statementPreparado)
			throws SQLException {

		statementPreparado.setString(1, nome);
		statementPreparado.setString(2, descricao);
	}
}
