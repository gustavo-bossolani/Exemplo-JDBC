package br.com.gustavo.jdbc.models;

import java.util.ArrayList;
import java.util.List;

public class Categoria {

	private Integer id;
	private String nome;
	private List<Produto> produtos = new ArrayList<>();
	
	public Categoria(Integer id, String nome) {
		this.id = id;
		this.nome = nome;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public List<Produto> getProdutos() {
		return produtos;
	}
	
	public void adiciona(Produto produto) {
		this.produtos.add(produto);
	}

	@Override
	public String toString() {
		return String.format("[ Categoria: id: %d, Nome: %s ]", this.id, this.nome);
	}
}
