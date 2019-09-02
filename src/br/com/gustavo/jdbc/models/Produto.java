package br.com.gustavo.jdbc.models;

public class Produto {
	
	private Integer id;
	private String nome;
	private String descricao;
	
	
	
	public Produto(String nome, String descricao) {
		this.nome = nome;
		this.descricao = descricao;
	}

	public String getNome() {
		return nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return String.format("[produto: ID:%d, Nome:%s, Descrição:%s]", this.id, this.nome, this.descricao );
	}
}
