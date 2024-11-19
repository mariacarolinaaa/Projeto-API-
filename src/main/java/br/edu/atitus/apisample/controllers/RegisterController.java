package br.edu.atitus.apisample.controllers;

import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.message.Message;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.atitus.apisample.dtos.RegisterDTO;
import br.edu.atitus.apisample.entities.RegisterEntity;
import br.edu.atitus.apisample.entities.UserEntity;
import br.edu.atitus.apisample.services.RegisterService;
import br.edu.atitus.apisample.services.UserService;

@RestController
@RequestMapping("/registers")
public class RegisterController {
	
	private final RegisterService service;
	private final UserService userService;

	public RegisterController(RegisterService service, UserService userService) {
		super();
		this.service = service;
		this.userService = userService;
	}
	
	@PostMapping
	public ResponseEntity<RegisterEntity> createRegister(@RequestBody RegisterDTO registerDTO) throws Exception{
		//Converter DTO to Entity
		RegisterEntity newRegister = new RegisterEntity();
		BeanUtils.copyProperties(registerDTO, newRegister);
		//buscar um usuario cadastrado
		//quando a autenticação estiver funcionando, pega-se o usuario autenticado
		UserEntity user = userService.findAll().get(0);
		
		newRegister.setUser(user);
		//Invocar metodo save da camad service
		service.save(newRegister);
		//retornar a entidade salva
		return ResponseEntity.status(HttpStatus.CREATED).body(newRegister);
	}
	
	
	@GetMapping
	public ResponseEntity<List<RegisterEntity>> getRegisters() throws Exception {
		var registers = service.findAll();
		
		return ResponseEntity.ok(registers);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<RegisterEntity> getOneRegister(@PathVariable UUID id) throws Exception {
		var registers = service.findByid(id);
		return ResponseEntity.ok(registers);
	}

	@PutMapping("/{id}")
	public ResponseEntity<RegisterEntity> putRegister(@PathVariable UUID id, @RequestBody RegisterDTO dto) throws Exception {
		//converter DTO2Entity
		RegisterEntity register = service.findByid(id);
		BeanUtils.copyProperties(dto, register);
		
		//Invocar método save da camada service
		service.save(register);
		//retornar a entidade salva
		return ResponseEntity.ok(register);
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteRegister(@PathVariable UUID id) throws Exception {
		service.deleteById(id);
		return ResponseEntity.ok("Registro deletado!");
	}
			//
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handlerException(Exception ex){
		String menssage = ex.getMessage().replaceAll("\r\n", "");
		return ResponseEntity.badRequest().body(menssage);
	}
}
