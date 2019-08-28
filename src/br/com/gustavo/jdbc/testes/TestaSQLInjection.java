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
		 * Para evitar SQLInjection � recomendado escrever a query em uma String antes 
		 * de passar para o m�todo Connection.prepareStatement(query) passando os values como '?'
		 * Connection.prepareStatement() ser� respons�vel por tratar os valores que est�o entrando
		 * no banco.
		 * 
		 * Depois que o m�todo Connection.prepareStatement() � chamado devemos setar os par�metros
		 * com o m�todo PreparedStatement.setString(pos, value) come�ando com a posi��o sempre do 1
		 * e especificando o tipo primitivo dos valores ex: PreparedStatement.SetInt().
		 * Para excutar a query j� tratada, basta apenas usar PreparedStatement.execute() sem par�metros.
		 * 
		 * Connection.prepareStatement(sql, param) este m�todo deve receber o segundo par�metro antes dos 
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
