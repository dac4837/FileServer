package com.collins.fileserver.domain;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name="pages")
public class Page {
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;
	
	@NotNull
	@NotEmpty
	@Length(min=3, max=20)
    @Column(unique = true)
	private String name;
	
    @Column(unique = true)
	private String directory;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
		
		if(this.directory ==null ) {
			this.directory = formatString(name);
		}
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getDirectory() {
		return directory;
	}
	public void setDirectory(String directory) {
		this.directory = directory;
	}
	@Override
	public String toString() {
		return "Page [id=" + id + ", name=" + name + "]";
	}
	
	//TODO update logic
	private String formatString(String string) {
		return string.toLowerCase();
	}
	
	
	
	

}
