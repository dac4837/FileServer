package com.collins.fileserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.collins.fileserver.domain.Privilege;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
	
	Privilege findByName(String name);

}
