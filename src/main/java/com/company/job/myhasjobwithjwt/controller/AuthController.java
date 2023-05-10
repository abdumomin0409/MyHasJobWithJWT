package com.company.job.myhasjobwithjwt.controller;

import com.company.job.myhasjobwithjwt.config.security.SessionUser;
import com.company.job.myhasjobwithjwt.domains.Ads;
import com.company.job.myhasjobwithjwt.domains.User;
import com.company.job.myhasjobwithjwt.domains.enums.UserRole;
import com.company.job.myhasjobwithjwt.payload.ResponseDTO;
import com.company.job.myhasjobwithjwt.payload.auth.TokenResponse;
import com.company.job.myhasjobwithjwt.payload.user.ResponseUserDto;
import com.company.job.myhasjobwithjwt.service.AdsService;
import com.company.job.myhasjobwithjwt.service.auth.AuthService;
import com.company.job.myhasjobwithjwt.payload.auth.RefreshTokenRequest;
import com.company.job.myhasjobwithjwt.payload.user.UserSignInDto;
import com.company.job.myhasjobwithjwt.payload.user.UserSignUpDto;
import com.company.job.myhasjobwithjwt.payload.user.UserSmsDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.company.job.myhasjobwithjwt.utils.BaseUrls.*;

@RestController
@RequestMapping(AUTH_URL)
@RequiredArgsConstructor
@Tag(name = "Authenticate users", description = "This API is used for authenticate users")
public class AuthController {
    private final AuthService authService;
    private final SessionUser sessionUser;
    private final AdsService adsService;


    @Operation(summary = "This API is used for register users", responses = {
            @ApiResponse(responseCode = "201", description = "User Successfully Registered", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))
    })
    @PostMapping("/register")
    public ResponseEntity<ResponseDTO<User>> register(@Valid @RequestBody UserSignUpDto dto) {
        User user = authService.signUp(dto);
        return ResponseEntity.status(201).body(new ResponseDTO<>("Sms code successfully sent", user));
    }


    @Operation(summary = "This API is used for checked activate code user", responses = {
            @ApiResponse(responseCode = "200", description = "User Successfully Checked Activated Code", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))
    })
    @PostMapping("/activate")
    public ResponseEntity<ResponseDTO<String>> activate(@Valid @RequestBody UserSmsDto dto) {
        String activate = authService.activate(dto);
        return ResponseEntity.ok(new ResponseDTO<>(activate));
    }


    @Operation(summary = "This API is used for login user", responses = {
            @ApiResponse(responseCode = "200", description = "User Successfully Login", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))
    })
    @PostMapping("/get/accessToken")
    public ResponseEntity<ResponseDTO<TokenResponse>> getAccessToken(@Valid @RequestBody UserSignInDto dto) {
        TokenResponse login = authService.login(dto);
        return ResponseEntity.ok(new ResponseDTO<>(login));
    }


    @Operation(summary = "This API is used for refresh token generation", responses = {
            @ApiResponse(responseCode = "200", description = "Refresh Token Generated", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))
    })
    @PostMapping("/refresh/token")
    public ResponseEntity<ResponseDTO<TokenResponse>> refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        TokenResponse refreshedToken = this.authService.refreshToken(refreshTokenRequest);
        return ResponseEntity.ok(new ResponseDTO<>(refreshedToken));
    }

    @Operation(summary = "This API is used for user activating users through the activation code that was sent via SMS", responses = {
            @ApiResponse(responseCode = "200", description = "User activated", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @PostMapping("/code/resend")
    public ResponseEntity<ResponseDTO<String>> resendCode(@RequestParam String phoneNumber) {
        authService.resendCode(phoneNumber);
        return ResponseEntity.ok(new ResponseDTO<>("Sms code sent successfully"));
    }


    @Operation(summary = "This API is used for main menu user", responses = {
            @ApiResponse(responseCode = "200", description = "Users returned", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))
    })
    @GetMapping("/menu")
    public ResponseEntity<ResponseDTO<List<?>>> getMenu() {
        User user = sessionUser.user();
        if (user == null) {
            throw new RuntimeException("User not found");
        } else if (user.getRole().equals(UserRole.ADMIN)) {
            return ResponseEntity.ok(new ResponseDTO<>(new ArrayList<>()));
        } else if (user.getRole().equals(UserRole.USER) && user.getJob().getName().equals("Ish beruvchi")) {
            List<ResponseUserDto> allActive = authService.getAllActive();
            return ResponseEntity.ok(new ResponseDTO<>(allActive));
        } else if (user.getRole().equals(UserRole.USER)) {
            List<Ads> allActive = adsService.getAllActive();
            return ResponseEntity.ok(new ResponseDTO<>(allActive));
        }
        return ResponseEntity.ok(new ResponseDTO<>(new ArrayList<>()));
    }
}
