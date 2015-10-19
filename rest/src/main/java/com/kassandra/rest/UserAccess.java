package com.kassandra.rest;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.auth0.jwt.JWTSigner;
import com.kassandra.repository.IUserRepository;
import com.kassandra.repository.RepositoryException;
import com.kassandra.repository.model.User;
import com.kassandra.rest.model.AuthenticationBean;
import com.kassandra.rest.model.TokenOutput;

@Controller
public class UserAccess {
    public static final String SECRET_CLIENT = "c3VwZXJHaWdpQXJlTWVyZQ==";
    public static final byte[] ENCODED_SECRET = Base64.encodeBase64(SECRET_CLIENT.getBytes());
    private final IUserRepository userRepository;

    @Autowired
    public UserAccess(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/signin",
        method = RequestMethod.POST,
        produces = { "application/json" })
    public @ResponseBody TokenOutput authenticate(@RequestBody AuthenticationBean userDetails) {

        try {
            String userId = userRepository.validateLogin(userDetails.getEmail(),
                    userDetails.getPassword());

            JWTSigner jwtSigner = new JWTSigner(SECRET_CLIENT);
            Map<String, Object> claims = new HashMap<>();
            String issuer = "http://" + InetAddress.getLocalHost().getHostName()
                    + ":8080/kassandra/signin";
            claims.put("email", userDetails.getEmail());
            claims.put("iss", issuer);
            claims.put("sub", userDetails.getEmail());

            JWTSigner.Options signOptions = new JWTSigner.Options().setIssuedAt(true)
                    .setExpirySeconds(3600).setJwtId(true);

            return new TokenOutput(true, userId, jwtSigner.sign(claims, signOptions));
        } catch (RepositoryException | UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(value = "/signinFacebook",
        method = RequestMethod.POST,
        produces = { "application/json" })
    public @ResponseBody TokenOutput authenticateFacebook(@RequestBody String userID) {
        try {
            JWTSigner jwtSigner = new JWTSigner(SECRET_CLIENT);
            Map<String, Object> claims = new HashMap<>();
            String issuer = "http://" + InetAddress.getLocalHost().getHostName()
                    + ":8080/kassandra/signin";
            claims.put("email", userID);
            claims.put("iss", issuer);
            claims.put("sub", userID);

            JWTSigner.Options signOptions = new JWTSigner.Options().setIssuedAt(true)
                    .setExpirySeconds(3600).setJwtId(true);

            return new TokenOutput(true, userID, jwtSigner.sign(claims, signOptions));
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public @ResponseBody boolean putUser(@RequestBody User user) {
        try {
            return userRepository.createUser(user);
        } catch (RepositoryException ex) {
            throw new RuntimeException(ex);
        }
    }
}
