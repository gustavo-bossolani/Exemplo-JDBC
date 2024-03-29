package br.com.gustavo.jdbc.testes;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import br.com.gustavo.jdbc.models.BancoDataSource;

public class TestaInsert {

	public static void main(String[] args) {

		try {
			Connection con = new BancoDataSource().getConnection();
			Statement state = con.createStatement();
			String sql = 
					"insert into produto (nome, descricao) values ('Geladeira','geladeira frost free.')";
			
			boolean retorno = state.execute(sql, Statement.RETURN_GENERATED_KEYS);
			
//			retorno ser� false caso o statement for um insert, caso for um select ser� sempre true
			if (!retorno) {
				System.out.println("Executando Insert");
			}else {
				System.out.println("Executando um Select");
			}
			
			ResultSet resultSet = state.getGeneratedKeys();
			while(resultSet.next()) {
				int id = resultSet.getInt("id");
				System.out.println("id gerado: " + id);
				
			}
			
			resultSet.close();
			state.close();
			con.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

}
