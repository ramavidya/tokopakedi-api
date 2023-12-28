package com.enigma.tokopakedi.controller;

import com.enigma.tokopakedi.entity.UserCredential;
import com.enigma.tokopakedi.model.AuthRequest;
import com.enigma.tokopakedi.model.UserResponse;
import com.enigma.tokopakedi.model.WebResponse;
import com.enigma.tokopakedi.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping(path = "/register")
    public ResponseEntity<?> register(@RequestBody AuthRequest request) {
        UserResponse userResponse = authService.register(request);
        WebResponse<UserResponse> response = WebResponse.<UserResponse>builder()
                .status(HttpStatus.CREATED.getReasonPhrase())
                .message("successfully create new user")
                .data(userResponse)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping(path = "/register/admin")
    public ResponseEntity<?> registerAdmin(@RequestBody AuthRequest request) {
        UserResponse userResponse = authService.registerAdmin(request);
        WebResponse<UserResponse> response = WebResponse.<UserResponse>builder()
                .status(HttpStatus.CREATED.getReasonPhrase())
                .message("successfully create new user")
                .data(userResponse)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        String token = authService.login(request);
        WebResponse<String> response = WebResponse.<String>builder()
                .status(HttpStatus.CREATED.getReasonPhrase())
                .message("successfully create new user")
                .data(token)
                .build();
        return ResponseEntity.ok(response);
    }


//    @PostMapping(path = "/register")
//    public ResponseEntity<?> register(@RequestBody AuthRequest request){
//        UserResponse userCredential = authService.register(request);
//        WebResponse<UserResponse> response = WebResponse.<UserResponse>builder()
//                .status(HttpStatus.CREATED.getReasonPhrase())
//                .message("Create a new user success")
//                .data(userCredential)
//                .build();
//        return ResponseEntity.status(HttpStatus.CREATED).body(response);
//    }
//    @PostMapping(path = "/register/admin")
//    public ResponseEntity<?> loginAdmin(@RequestBody AuthRequest request){
//        UserResponse userCredential = authService.registerAdmin(request);
//        WebResponse<UserResponse> response = WebResponse.<UserResponse>builder()
//                .status(HttpStatus.CREATED.getReasonPhrase())
//                .message("Create a new user success")
//                .data(userCredential)
//                .build();
//        return ResponseEntity.status(HttpStatus.CREATED).body(response);
//    }
//    @PostMapping(path = "/login")
//    public ResponseEntity<?> login (@RequestBody AuthRequest request){
//        String token = authService.login(request);
//        WebResponse<String> response = WebResponse.<String>builder()
//                .status(HttpStatus.CREATED.getReasonPhrase())
//                .message("Create a new user success")
//                .data(token)
//                .build();
//        return null;
//    }
}
