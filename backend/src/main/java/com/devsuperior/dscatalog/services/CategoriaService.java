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

import com.devsuperior.dscatalog.dto.CategoriaDTO;
import com.devsuperior.dscatalog.entities.Categoria;
import com.devsuperior.dscatalog.repositories.CategoriaRepository;
import com.devsuperior.dscatalog.services.exceptions.DatabaseException;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;

@Service                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repository;
	
	@Transactional(readOnly = true)
	public Page<CategoriaDTO> findAllPaged(PageRequest pageRequest) {
		Page<Categoria> list = repository.findAll(pageRequest);
		return list.map(x -> new CategoriaDTO(x));
		
	}
	
	@Transactional(readOnly = true)
	public CategoriaDTO findById(Long id) {
		Optional<Categoria> obj = repository.findById(id);
		Categoria entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entidade não encontrada"));
		return new CategoriaDTO(entity);
	}

	@Transactional
	public CategoriaDTO insert(CategoriaDTO dto) {
		Categoria entity = new Categoria();
		entity.setNome(dto.getNome());
		entity =  repository.save(entity);
		return new CategoriaDTO(entity);
	}
	
	@Transactional
	public CategoriaDTO update(Long id, CategoriaDTO dto) {
		try {
		Categoria entity = repository .getOne(id);
		entity.setNome(dto.getNome());
		entity = repository.save(entity);
		return new CategoriaDTO(entity);
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