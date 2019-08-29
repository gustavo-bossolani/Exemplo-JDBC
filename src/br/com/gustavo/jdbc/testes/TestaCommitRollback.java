package br.com.gustavo.jdbc.testes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import br.com.gustavo.jdbc.models.BancoDataSource;

public class TestaCommitRollback {

	public static void main(String[] args) throws SQLException {

		//Podemos criar uma conexão com o banco dentro de um try pq o mesmo implementa a
		//interface AutoCloseable, classes que implementam AutoCloseable pode ser inicializadas
		//dentro de um try, sendo fechadas automaticamente.

		try(Connection connection = new BancoDataSource().getConnection()){

			// O auto commit nos possibilita ter mais controle sob as querys executadas
			// Sendo necessário a chamada manual do método connection.commit() para de fato executar o comando
			connection.setAutoCommit(false);

			String sql = "insert into Produto(nome, descricao) values(?,?)";

			try(PreparedStatement statementPreparado = 
					connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

				adiciona("Bicicleta", "Uma bicicleta comum.", statementPreparado);
				adiciona("GameBoy", "Video game portatil.", statementPreparado);
				connection.commit(); //Comitando todas as transações em aberto
			}catch(Exception e) {
				e.printStackTrace();

				//Retornando o estado do banco
				//Podemos retornar o estado do banco antes de uma transação ser fechada
				connection.rollback();
				System.out.println("Erro encontrado, Rollback Efetuado.");
			}

		}
	}

	private static void adiciona(String nome, String descricao, PreparedStatement statementPreparado)
			throws SQLException {

		if(nome.equals("GameBoy")) {
			throw new IllegalArgumentException("Não é possível inserir Gameboy.");
		}

		statementPreparado.setString(1, nome);
		statementPreparado.setString(2, descricao);

		boolean resultado = statementPreparado.execute();
		System.out.println(resultado);

		System.out.println("Produto Inserido na base de Dados.");
		System.out.println("Linhas alteradas " + statementPreparado.getUpdateCount());

		ResultSet resultSet =  statementPreparado.getGeneratedKeys();
		while(resultSet.next()){
			String id = resultSet.getString("id");
			System.out.println("id: " + id + " gerado.");
		} 
		resultSet.close();
	}

}
