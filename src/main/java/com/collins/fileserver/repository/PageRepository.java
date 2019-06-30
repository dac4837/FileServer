package com.collins.fileserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.collins.fileserver.domain.Page;

@Repository
public interface PageRepository extends JpaRepository<Page, Long> {
	Page findByDirectory(String name);

}
