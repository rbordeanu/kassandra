package com.kassandra.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kassandra.repository.IUserRepository;
import com.kassandra.repository.RepositoryException;
import com.kassandra.repository.model.User;

@Controller
@RequestMapping(value = "/api/user")
public class UserResource {
    private final IUserRepository userRepository;

    @Autowired
    public UserResource(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST, produces = { "application/json" })
    public @ResponseBody
            User getUser(@PathVariable("id") String id) {
        try {
            return userRepository.getUser(id);
        } catch (RepositoryException ex) {
            throw new RuntimeException(ex);
        }
    }
}
