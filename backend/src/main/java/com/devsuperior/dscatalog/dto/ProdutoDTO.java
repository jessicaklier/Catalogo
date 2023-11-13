package com.devsuperior.dscatalog.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.devsuperior.dscatalog.entities.Categoria;
import com.devsuperior.dscatalog.entities.Produto;

public class ProdutoDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String nome;
	private String descricao;
	private Double preco;
	private String imagemUrl;
	private Instant data;

	private List<CategoriaDTO> categorias = new ArrayList<>();
	
	public ProdutoDTO() {
		
	}

	public ProdutoDTO(Long id, String nome, String descricao, Double preco, String imagemUrl, Instant data) {
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.preco = preco;
		this.imagemUrl = imagemUrl;
		this.data = data;
	}
	
	public ProdutoDTO(Produto entity) {
		this.id = entity.getId();
		this.nome = entity.getNome();
		this.descricao = entity.getDescricao();
		this.preco = entity.getPreco();
		this.imagemUrl = entity.getImagemUrl();
		this.data = entity.getData();
	
}
	public ProdutoDTO(Produto entity, Set<Categoria> categorias) {
		this(entity);
		categorias.forEach(cat -> this.categorias.add(new CategoriaDTO(cat)));
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public String getImagemUrl() {
		return imagemUrl;
	}

	public void setImagemUrl(String imagemUrl) {
		this.imagemUrl = imagemUrl;
	}

	public Instant getData() {
		return data;
	}

	public void setData(Instant data) {
		this.data = data;
	}

	public List<CategoriaDTO> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<CategoriaDTO> categorias) {
		this.categorias = categorias;
	}
}