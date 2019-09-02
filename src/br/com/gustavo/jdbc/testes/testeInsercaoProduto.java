package br.com.gustavo.jdbc.testes;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import br.com.gustavo.jdbc.dao.ProdutoDAO;
import br.com.gustavo.jdbc.models.BancoDataSource;
import br.com.gustavo.jdbc.models.Produto;

public class testeInsercaoProduto {

	public static void main(String[] args) throws SQLException {
		
		Produto produto = new Produto("GameBoy SP", "clássico portatil Nintendo.");
		
		try(Connection con = new BancoDataSource().getConnection()){
			ProdutoDAO produtoDAO = new ProdutoDAO(con);
			
//			produtoDAO.salva(produto);
			
			Produto buscaPorID = produtoDAO.buscaPorID(2);
			System.out.println(buscaPorID);
			
			List<Produto> produtos = produtoDAO.lista();
			for (Produto produto2 : produtos) {
				System.out.println(produto2);
			}
		}
		
	}

}
