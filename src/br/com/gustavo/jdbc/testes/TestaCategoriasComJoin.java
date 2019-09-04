package br.com.gustavo.jdbc.testes;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import br.com.gustavo.jdbc.dao.CategoriaDAO;
import br.com.gustavo.jdbc.models.BancoDataSource;
import br.com.gustavo.jdbc.models.Categoria;
import br.com.gustavo.jdbc.models.Produto;

public class TestaCategoriasComJoin {

	public static void main(String[] args) {
		
		
		try(Connection con = new BancoDataSource().getConnection()){
			
			CategoriaDAO categoriaDAO = new CategoriaDAO(con);
			List<Categoria> categorias = categoriaDAO.listaComProdutos();
			
			for (Categoria categoria : categorias) {
				System.out.println(categoria.getNome());
				
				for (Produto produto: categoria.getProdutos()) {
					System.out.println(produto);
				}
			}
			
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
	}

}
