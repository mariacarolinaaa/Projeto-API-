package br.edu.atitus.apisample.repositores;

import java.util.UUID;

import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.atitus.apisample.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity , UUID>{
	
	boolean existsByEmail(String email);

}
