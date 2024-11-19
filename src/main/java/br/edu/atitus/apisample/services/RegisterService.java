package br.edu.atitus.apisample.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.edu.atitus.apisample.entities.RegisterEntity;
import br.edu.atitus.apisample.repositores.RegisterRepository;

@Service
public class RegisterService {

	private final RegisterRepository repository;

	public RegisterService(RegisterRepository repository) {
		super();
		this.repository = repository;
	}
	
	//metodo save
	public RegisterEntity save(RegisterEntity newRegister) throws Exception {
		//validaçãoes de regra de negocio
		if(newRegister.getUser() == null || newRegister.getUser().getId() == null)
			throw new Exception("Usuario não informado");
		if(newRegister.getLatitude() < -90 || newRegister.getLatitude() > 90)
			throw new Exception("Latitude inválida");
		if(newRegister.getLongitude() < -180 || newRegister.getLatitude() > 180)
			throw new Exception("Longitude inválida");
		
		//invocar metodo save da camada repository
		repository.save(newRegister);
		
		//retorna o objeto salvo
		return newRegister;
	}
	
	//metodo findAll
	public List<RegisterEntity> findAll() throws Exception {
		List<RegisterEntity> registers = repository.findAll();
		return registers;
	}
	//metodo findById
	public RegisterEntity findByid(UUID id) throws Exception {
		RegisterEntity register = repository.findById(id)
				.orElseThrow(() -> new Exception("Registro Não encontrado com este Id"));
		return register;
	}
	//metodo deleteById
	public void deleteById(UUID id) throws Exception {
		this.findByid(id);
		repository.deleteById(id);
	}
}
