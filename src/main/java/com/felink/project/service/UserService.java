package com.felink.project.service;

import com.felink.project.core.Service;
import com.felink.project.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends Service<User>, UserDetailsService{
}
