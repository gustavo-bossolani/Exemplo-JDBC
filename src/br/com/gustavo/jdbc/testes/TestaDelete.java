package br.com.gustavo.jdbc.testes;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import br.com.gustavo.jdbc.models.BancoDataSource;

public class TestaDelete {

	public static void main(String[] args) {

		try {
			Connection con = new BancoDataSource().getConnection();
			Statement state = con.createStatement();
			String sql = "delete from Produto where id > 3";
			
			boolean retorno = state.execute(sql);
			System.out.println(retorno);
			
//			Conta a quantidade de linhas que foram alteradas
			int count = state.getUpdateCount();
			System.out.println("Deletado");
			System.out.println("Numero de deleções: " + count);
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
	}

}
