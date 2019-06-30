package com.collins.fileserver.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.collins.fileserver.domain.File;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {

	List<File> findByPageId(Long pageId);
	File findByPageIdAndName(Long pageId, String name);
}
