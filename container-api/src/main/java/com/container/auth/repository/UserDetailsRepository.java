package com.container.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.container.commons.entity.DefaultEntity;

public interface UserDetailsRepository extends JpaRepository<DefaultEntity, Long> {

	@Query(value = "SELECT "
			+ "			user_id, "
			+ "			user_name, "
			+ "			user_password, "
			+ "			user_status_id "
			+ "		FROM USER_CREDENTIALS uc "
			+ "		WHERE user_name = :username", nativeQuery = true)
	Object retrieveUserCredentials(@Param("username") String username);
	
	@Query(value = "SELECT r.acronym "
			+ " FROM USER_CREDENTIALS u "
			+ " INNER JOIN USER_ROLE ur ON u.USER_ID = ur.USER_ID "
			+ " INNER JOIN ROLE r ON ur.ROLE_ID = r.ROLE_ID "
			+ " where u.USER_NAME = :username", nativeQuery = true)
	Object[] retrieveUserRoles(@Param("username") String username);

}
