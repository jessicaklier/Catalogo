package com.devsuperior.dscatalog.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscatalog.dto.ProdutoDTO;
import com.devsuperior.dscatalog.entities.Produto;
import com.devsuperior.dscatalog.repositories.ProdutoRepository;
import com.devsuperior.dscatalog.services.exceptions.DatabaseException;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;

@Service                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository repository;
	
	@Transactional(readOnly = true)
	public Page<ProdutoDTO> findAllPaged(PageRequest pageRequest) {
		Page<Produto> list = repository.findAll(pageRequest);
		return list.map(x -> new ProdutoDTO(x));
		
	}
	
	@Transactional(readOnly = true)
	public ProdutoDTO findById(Long id) {
		Optional<Produto> obj = repository.findById(id);
		Produto entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entidade não encontrada"));
		return new ProdutoDTO(entity, entity.getCategorias());
	}

	@Transactional
	public ProdutoDTO insert(ProdutoDTO dto) {
		Produto entity = new Produto();
		//entity.setNome(dto.getNome());
		entity =  repository.save(entity);
		return new ProdutoDTO(entity);
	}
	
	@Transactional
	public ProdutoDTO update(Long id, ProdutoDTO dto) {
		try {
		Produto entity = repository .getOne(id);
		//entity.setNome(dto.getNome());
		entity = repository.save(entity);
		return new ProdutoDTO(entity);
	}
		catch(EntityNotFoundException e) {
		throw new ResourceNotFoundException("Identificação não encontrada" + id);
		}
	}

	public void delete(Long id) {
		try {
		repository.deleteById(id);
		
	}
		catch(EmptyResultDataAccessException e)
		{
			throw new ResourceNotFoundException("Identificação não encontrada" + id);
		}
		catch(DataIntegrityViolationException e) {
			throw new DatabaseException("Violação de Integridade");
		}
	}
}