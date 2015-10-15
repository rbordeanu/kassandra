package com.kassandra.rest;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.kassandra.repository.IUserRepository;
import com.kassandra.repository.RepositoryException;
import com.kassandra.repository.model.User;

@Controller
@RequestMapping(value = "/user")
public class UserResource {

    @Autowired
    private final IUserRepository userRepository;

    @Inject
    UserResource(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = { "application/json" })
    public @ResponseBody User getUser(@PathVariable("id") String id) {
        try {
            return userRepository.getUser(id);
        } catch (RepositoryException ex) {
            throw new RuntimeException(ex);
        }
    }

    @RequestMapping(method = RequestMethod.PUT)
    public @ResponseBody boolean putUser(@RequestBody User user) {
        try {
            return userRepository.createUser(user);
        } catch (RepositoryException ex) {
            throw new RuntimeException(ex);
        }
    }

}
