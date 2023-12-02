package com.example.pamiw.controllers;

import com.example.pamiw.model.AuthenticationRequest;
import com.example.pamiw.model.AuthenticationResponse;
import com.example.pamiw.model.ServiceResponse;
import com.example.pamiw.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ServiceResponse<AuthenticationResponse> register(@RequestBody AuthenticationRequest request){
        return new ServiceResponse<>(this.authenticationService.register(request),true,"");

    }
    @PostMapping("/authenticate")
    public ServiceResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        return new ServiceResponse<>(this.authenticationService.authenticate(request),true,"");
    }
}
