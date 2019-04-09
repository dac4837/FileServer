package com.collins.fileserver.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.collins.fileserver.domain.Privilege;
import com.collins.fileserver.domain.Role;
import com.collins.fileserver.domain.User;
import com.collins.fileserver.repository.PrivilegeRepository;
import com.collins.fileserver.repository.RoleRepository;
import com.collins.fileserver.repository.UserRepository;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {
	

    private boolean alreadySetup = false;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private SecurityProperties securityProperties;

    // API

    @Override
    @Transactional
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }

        // == create initial privileges
        final Privilege readPrivilege = createPrivilegeIfNotFound("PRIVILEGE_READ");
        final Privilege writePrivilege = createPrivilegeIfNotFound("PRIVILEGE_WRITE");
        final Privilege deletePrivilege = createPrivilegeIfNotFound("PRIVILEGE_DELETE");
        final Privilege manageUsersPrivilege = createPrivilegeIfNotFound("PRIVILEGE_MANAGE_USERS");
        final Privilege loginPrivilege = createPrivilegeIfNotFound("PRIVILEGE_LOGIN_PRIVILEGE");
        
        // == create initial roles
        final List<Privilege> adminPrivileges = new ArrayList<Privilege>(Arrays.asList(loginPrivilege, readPrivilege, writePrivilege, deletePrivilege, manageUsersPrivilege));
        final List<Privilege> writerPrivileges = new ArrayList<Privilege>(Arrays.asList(loginPrivilege, readPrivilege, writePrivilege));
        final List<Privilege> readerPrivileges = new ArrayList<Privilege>(Arrays.asList(loginPrivilege, readPrivilege));
        final List<Privilege> loginPrivileges = new ArrayList<Privilege>(Arrays.asList(loginPrivilege));
        
        final Role adminRole = createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
        createRoleIfNotFound("ROLE_WRITER", writerPrivileges);
        createRoleIfNotFound("ROLE_READER", readerPrivileges);
        createRoleIfNotFound("ROLE_LOGIN", loginPrivileges);
 
        // == create initial user
        createUserIfNotFound(securityProperties.getDefaultUser(), securityProperties.getDefaultUser(), securityProperties.getDefaultPassword(), new ArrayList<Role>(Arrays.asList(adminRole)));

        alreadySetup = true;
    }

    @Transactional
    private final Privilege createPrivilegeIfNotFound(final String name) {
        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege(name);
            privilege = privilegeRepository.save(privilege);
        }
        return privilege;
    }

    @Transactional
    private final Role createRoleIfNotFound(final String name, final Collection<Privilege> privileges) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
        }
        role.setPrivileges(privileges);
        role = roleRepository.save(role);
        return role;
    }

    @Transactional
    private final User createUserIfNotFound(final String username, final String displayName, final String password, final Collection<Role> roles) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            user = new User();
            user.setDisplayName(displayName);
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(password));
      
        }
        user.setRoles(roles);
        user = userRepository.save(user);
        return user;
    }

}