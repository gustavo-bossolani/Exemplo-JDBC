package br.com.gustavo.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.gustavo.jdbc.models.Categoria;
import br.com.gustavo.jdbc.models.Produto;

public class CategoriaDAO {


	private Connection con;

	public CategoriaDAO(Connection con) {
		this.con = con;
	}

	public List<Categoria> lista() throws SQLException{

		List<Categoria> categorias = new ArrayList<>();
		String sql = "select * from Categoria";

		try(PreparedStatement stmt = con.prepareStatement(sql)){
			stmt.execute();

			try(ResultSet resultSet = stmt.getResultSet()){
				while(resultSet.next()) {
					int id = resultSet.getInt("id");
					String nome = resultSet.getString("nome");
					Categoria categoria = new Categoria(id, nome);
					categorias.add(categoria);
				}
			}
		}
		return categorias;
	}

	public List<Categoria> listaComProdutos() throws SQLException{
		List<Categoria> categorias = new ArrayList<>();
		
		//Imprimindo sem ordenar os ids
//		String sql 
//				= "select c.id as c_id, c.nome as c_nome, p.id as p_id, p.nome as p_nome, p.descricao as p_descricao " + 
//				"from Categoria as c join Produto as p on p.categoria_id = c.id";
		
		String sql = 
				"select c.id as c_id, c.nome as c_nome, " + 
				"p.id as p_id, p.nome as p_nome, " + 
				"p.descricao as p_descricao from Categoria as c " + 
				"join Produto as p on p.categoria_id = c.id order by c.id";
		
		Categoria ultimaCategoria = null;

		try(PreparedStatement stmt = con.prepareStatement(sql)){
			stmt.execute();
			try(ResultSet result = stmt.getResultSet()){
				while(result.next()) {
					
					// Carregando os dados de todas as categorias
					int categoriaId = result.getInt("c_id");
					String categoriaNome = result.getString("c_nome");

					if(ultimaCategoria == null || !ultimaCategoria.getNome().equals(categoriaNome)) {
						Categoria categoria = new Categoria(categoriaId, categoriaNome);
						categorias.add(categoria);
						ultimaCategoria = categoria;
					}

					// Carregando os dados de todos os Produtos e criando o mesmo
					int produtoId = result.getInt("p_id");
					String produtoNome = result.getString("p_nome");
					String produtoDescricao = result.getString("p_descricao");
					Produto produto = new Produto(produtoNome, produtoDescricao);
					produto.setId(produtoId);
					ultimaCategoria.adiciona(produto);
				}
			}

		}
		return categorias;
	}

}
