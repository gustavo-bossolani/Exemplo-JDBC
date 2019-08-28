package br.com.gustavo.jdbc.testes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import br.com.gustavo.jdbc.models.Banco;

public class TestaSQLInjection {


	public static void main(String[] args) {

		/*
		 * Para evitar SQLInjection é recomendado escrever a query em uma String antes 
		 * de passar para o método Connection.prepareStatement(query) passando os values como '?'
		 * Connection.prepareStatement() será responsável por tratar os valores que estão entrando
		 * no banco.
		 * 
		 * Depois que o método Connection.prepareStatement() é chamado devemos setar os parâmetros
		 * com o método PreparedStatement.setString(pos, value) começando com a posição sempre do 1
		 * e especificando o tipo primitivo dos valores ex: PreparedStatement.SetInt().
		 * Para excutar a query já tratada, basta apenas usar PreparedStatement.execute() sem parâmetros.
		 * 
		 * Connection.prepareStatement(sql, param) este método deve receber o segundo parâmetro antes dos 
		 * valores serem Setados com PreparedStatement.setString(pos, value).
		 */
		
		String sql = "insert into Produto(nome, descricao) values(?,?)";
		String nome = "Combo Playstation 3";
		String descricao = "Video game PS3 com 3 jogos.";
		PreparedStatement statementPreparado;

		try {

			Connection connection = Banco.getConnection();
			statementPreparado = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			statementPreparado.setString(1, nome);
			statementPreparado.setString(2, descricao);
			boolean resultado = statementPreparado.execute();

			if (!resultado) {
				System.out.println("Produto Inserido na base de Dados.");
				int linhasEditadas = statementPreparado.getUpdateCount();
				System.out.println("Linhas alteradas " + linhasEditadas);
				
				ResultSet resultSet =  statementPreparado.getGeneratedKeys();
				while(resultSet.next()){
					String id = resultSet.getString("id");
					System.out.println(id + " gerado.");
				}
				resultSet.close();
			} 

			statementPreparado.close();
	        connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

	}

}
