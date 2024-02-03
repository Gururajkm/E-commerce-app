package com.ecommerce.ecom.controller;

import com.ecommerce.ecom.dto.AuthenticationRequest;
import com.ecommerce.ecom.dto.SignupRequest;
import com.ecommerce.ecom.dto.UserDto;
import com.ecommerce.ecom.entity.User;
import com.ecommerce.ecom.repository.UserRepository;
import com.ecommerce.ecom.services.auth.AuthService;
import com.ecommerce.ecom.utils.JwtUtil;
import com.fasterxml.jackson.databind.util.JSONPObject;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class AuthController {

    private static final String TOKEN_PREFIX = "Bearer";
    private static final String HEADER_STRING = "authorization";
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    private final UserRepository userRepository;

    private JwtUtil jwtUtil;

    private final AuthService authService;


    @PostMapping("/authenticate")
    public void createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest,
                                          HttpServletResponse response) throws IOException, JSONException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                    authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("incorrect username or password");
//           e.printStackTrace();
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        Optional<User> optionalUser = userRepository.findFirstByEmail(userDetails.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());

        if (optionalUser.isPresent()) {
            response.getWriter().write(new JSONObject()
                    .put("userId", optionalUser.get().getId())
                    .put("role", optionalUser.get().getRole())
                    .toString()
            );
            response.addHeader("Access-Control-Expose-Headers","Authorization");
            response.addHeader("Access-Control-Expose-Headers","Authorization,X-PINGOTHER,Origin," +
                    "X-Requested-With,Content-Type,Accept,X-Custom-header");
            response.addHeader(HEADER_STRING, TOKEN_PREFIX + jwt);
        }
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signupUser(@RequestBody SignupRequest signupRequest) {
        if (authService.hasUserWithEmail(signupRequest.getEmail())) {
            return new ResponseEntity<>("user Allready exists", HttpStatus.NOT_ACCEPTABLE);
        }
        UserDto userDto = authService.createUser(signupRequest);
        return  new ResponseEntity<>(userDto,HttpStatus.OK);
    }

}
