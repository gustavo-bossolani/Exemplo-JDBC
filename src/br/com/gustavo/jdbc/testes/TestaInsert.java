package br.com.gustavo.jdbc.testes;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import br.com.gustavo.jdbc.models.Banco;

public class TestaInsert {

	public static void main(String[] args) {

		try {
			Connection con = Banco.getConnection();
			Statement state = con.createStatement();
			String sql = "insert into produto (nome, descricao) values ('Placa de Video','Uma poderosa placa de video para seus Games')";
			
			boolean retorno = state.execute(sql, Statement.RETURN_GENERATED_KEYS);
			
//			retorno será false caso o statement for um insert, caso for um select será sempre true
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
