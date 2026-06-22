package com.bah.engine.service;

import com.bah.engine.model.AuthorizationResponseDto;
import com.bah.engine.model.LoginDto;

public interface AuthorizationService {

    AuthorizationResponseDto login(LoginDto loginDto);

}
