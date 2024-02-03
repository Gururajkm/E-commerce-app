package com.ecommerce.ecom.services.auth;

import com.ecommerce.ecom.dto.SignupRequest;
import com.ecommerce.ecom.dto.UserDto;

public interface AuthService {

    UserDto createUser(SignupRequest signupRequest);

    public Boolean hasUserWithEmail(String email);
}
