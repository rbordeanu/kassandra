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
import com.kassandra.rest.model.AuthenticationBean;
import com.kassandra.rest.model.TokenOutput;

@Controller
public class SignIn {
    public static final String SECRET_CLIENT = "c3VwZXJHaWdpQXJlTWVyZQ==";
    public static final byte[] ENCODED_SECRET = Base64.encodeBase64(SECRET_CLIENT.getBytes());
    private final IUserRepository userRepository;

    @Autowired
    public SignIn(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/signin",
        method = RequestMethod.POST,
        produces = { "application/json" })
    public @ResponseBody TokenOutput authenticate(@RequestBody AuthenticationBean userDetails) {

        try {
            boolean isValid = userRepository.validateLogin(userDetails.getUsername(),
                    userDetails.getPassword());

            if (isValid) {
                JWTSigner jwtSigner = new JWTSigner(SECRET_CLIENT);
                Map<String, Object> claims = new HashMap<String, Object>();
                String issuer = "http://" + InetAddress.getLocalHost().getHostName()
                        + ":8080/kassandra/signin";
                claims.put("username", userDetails.getUsername());
                claims.put("iss", issuer);
                claims.put("sub", userDetails.getUsername());

                JWTSigner.Options signOptions = new JWTSigner.Options().setIssuedAt(true)
                        .setExpirySeconds(600).setJwtId(true);

                return new TokenOutput(true, userDetails.getUsername(), jwtSigner.sign(claims,
                        signOptions));
            } else {
                return new TokenOutput(false, "invalid credentials", "");
            }
        } catch (UnknownHostException e) {
            return new TokenOutput(false, "invalid credentials", "");
        }

    }
}
