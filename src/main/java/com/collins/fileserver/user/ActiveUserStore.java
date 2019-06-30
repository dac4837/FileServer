package com.collins.fileserver.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class ActiveUserStore {

	public List<String> users;
	 
    public ActiveUserStore() {
        users = new ArrayList<String>();
    }
    
}
