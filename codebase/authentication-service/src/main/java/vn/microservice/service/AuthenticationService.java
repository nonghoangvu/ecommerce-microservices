package vn.microservice.service;

import jakarta.servlet.http.HttpServletRequest;
import vn.microservice.dto.request.LoginRequest;
import vn.microservice.dto.response.TokenResponse;

public interface AuthenticationService {

    TokenResponse createAccessToken(LoginRequest request);

    TokenResponse createRefreshToken(HttpServletRequest request);
}
