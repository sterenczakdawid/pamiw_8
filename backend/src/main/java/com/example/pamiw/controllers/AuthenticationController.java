package com.example.pamiw.controllers;

import com.example.pamiw.model.AuthenticationRequest;
import com.example.pamiw.model.AuthenticationResponse;
import com.example.pamiw.model.ServiceResponse;
import com.example.pamiw.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
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
