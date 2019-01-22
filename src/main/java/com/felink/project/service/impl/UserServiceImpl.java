package com.felink.project.service.impl;

import com.felink.project.core.AbstractService;
import com.felink.project.dao.UserMapper;
import com.felink.project.model.User;
import com.felink.project.service.UserService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by CodeGenerator on 2018/11/22.
 */
@Service
@Transactional
public class UserServiceImpl extends AbstractService<User> implements UserService {
    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Resource
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User selectUser = new User();
        selectUser.setUsername(s);
        List<User> user = userMapper.select(selectUser);
        if(user == null || user.isEmpty()) {
            logger.info("User not exists: ", s);
            throw new UsernameNotFoundException("username is not exists");
        }
        logger.info("User exists: " + user.get(0).getUsername() + " " + user.get(0).getPassword());
        return user.get(0);
    }
}
