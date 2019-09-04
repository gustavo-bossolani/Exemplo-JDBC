package br.com.gustavo.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.gustavo.jdbc.models.Categoria;
import br.com.gustavo.jdbc.models.Produto;

public class ProdutoDAO {

	private Connection con;

	public ProdutoDAO(Connection con) {
		this.con = con;
	}

	public void salva(Produto produto) throws SQLException {
		
		/*
		 * Para preparar uma inserção no banco e setar id em um obj devemos:
		 * 
		 * Definir o comando sql;
		 * Preparar o Statement e indicar para o mesmo retornar os IDs gerados;
		 * Setar os valores e terminar a regra SQL;
		 * Executar o comando;
		 * Receber o id gerado através de um ResultSet.getGeneratedKeys;
		 * Posicionar o cursor do ResultSet para o primeiro objeto;
		 */
		
		String sql = "insert into Produto(nome, descricao) values (?,?)";
		try (PreparedStatement state = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

			state.setString(1, produto.getNome());
			state.setString(2, produto.getDescricao());
			state.execute();

			try (ResultSet resultSet = state.getGeneratedKeys()) {
				if (resultSet.next()) {
					produto.setId(resultSet.getInt("id"));
				}
			}
		}
	}

	
	// Exemplo de busca com chave estrangeira, sem evitar o problema de N+1
	
//	public List<Produto> busca(Categoria categoria) throws SQLException{
//		List<Produto> produtos = new ArrayList<>();
//		String sql = "select * from Produto where categoria_id = ?";
//		
//		try(PreparedStatement stmt = this.con.prepareStatement(sql)){
//			stmt.setInt(1, categoria.getId());
//			stmt.execute();
//			
//			transformaResultadoEmProdutos(produtos, stmt);
//		}
//		return produtos;
//	}
	
	public Produto buscaPorID(int id) throws SQLException {
		
		String sql = "select * from Produto where id = ?";
		
		int idProduto = 0;
		String nomeProduto = "";
		String descricaoProduto = "";
		
		try(PreparedStatement state = con.prepareStatement(sql)){
			state.setInt(1, id);
			state.execute();
			
			try(ResultSet resultSet = state.getResultSet()){
				if(resultSet.next()) {
					idProduto = resultSet.getInt("id");
					nomeProduto = resultSet.getString("nome");
					descricaoProduto = resultSet.getString("descricao");
				}
			}
		}
		
		Produto produto = new Produto(nomeProduto, descricaoProduto);
		produto.setId(idProduto);
		return produto;
	}

	public List<Produto> lista() throws SQLException{
		String sql = "select * from Produto";
		List<Produto> produtos = new ArrayList<>();
		
		try(PreparedStatement state = con.prepareStatement(sql)){
			state.execute();
			transformaResultadoEmProdutos(produtos, state);
		}
		return produtos;
	}

	private void transformaResultadoEmProdutos(List<Produto> produtos, PreparedStatement state) throws SQLException {
		try(ResultSet result = state.getResultSet()){
			while(result.next()) {
				int id = result.getInt("id");
				String nome = result.getString("nome");
				String descricao = result.getString("descricao");
				Produto produto = new Produto(nome,descricao);
				produto.setId(id);
				produtos.add(produto);
			}
		}
	}


}
