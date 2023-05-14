package com.company.job.myhasjobwithjwt.controller;

import com.company.job.myhasjobwithjwt.config.security.SessionUser;
import com.company.job.myhasjobwithjwt.domains.Ads;
import com.company.job.myhasjobwithjwt.domains.User;
import com.company.job.myhasjobwithjwt.domains.enums.SmsCodeType;
import com.company.job.myhasjobwithjwt.domains.enums.UserRole;
import com.company.job.myhasjobwithjwt.payload.ResponseDTO;
import com.company.job.myhasjobwithjwt.payload.auth.TokenResponse;
import com.company.job.myhasjobwithjwt.payload.user.*;
import com.company.job.myhasjobwithjwt.service.AdsService;
import com.company.job.myhasjobwithjwt.service.auth.AuthService;
import com.company.job.myhasjobwithjwt.payload.auth.RefreshTokenRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
        TokenResponse login = this.authService.getAccessToken(dto);
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
    public ResponseEntity<ResponseDTO<Void>> resendCode(@RequestParam String phoneNumber) {
        this.authService.resendCode(phoneNumber, SmsCodeType.ACTIVATION);
        return ResponseEntity.ok(new ResponseDTO<>("Sms code sent successfully", null));
    }


    @Operation(summary = "This API is used for user that forgot password ", responses = {
            @ApiResponse(responseCode = "200", description = "Sms code sent", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @PostMapping("/forget/password")
    public ResponseEntity<ResponseDTO<Void>> forgetPasswordRequest(@RequestParam String phoneNumber) {
        this.authService.resendCode(phoneNumber, SmsCodeType.FORGET_PASSWORD);
        return ResponseEntity.ok(new ResponseDTO<>("Sms code sent successfully", null));
    }


    @Operation(summary = "This API is used for user ", responses = {
            @ApiResponse(responseCode = "200", description = "Changed user's password ", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @PostMapping("/forget/password/activate")
    public ResponseEntity<ResponseDTO<Void>> forgetPasswordActivate(@Valid @RequestBody UserResetPasswordDTO dto) {
        this.authService.forgetPasswordActivate(dto);
        return ResponseEntity.ok(new ResponseDTO<>("Password changed successfully", null));
    }


    @Operation(summary = "This API is used for get all users ", responses = {
            @ApiResponse(responseCode = "200", description = "Returned all users", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get/all")
    public ResponseEntity<ResponseDTO<Page<User>>> getAll(@RequestParam(required = false, defaultValue = "10") Integer size,
                                                          @RequestParam(required = false, defaultValue = "0") @Min(value = 1) Integer page) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<User> all = this.authService.getAll(pageable);
        return ResponseEntity.ok(new ResponseDTO<>(all));
    }


    @Operation(summary = "This API is used for main menu user", responses = {
            @ApiResponse(responseCode = "200", description = "Users returned", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))
    })
    @GetMapping("/menu")
    public ResponseEntity<ResponseDTO<Page<?>>> getMenu(@RequestParam(required = false, defaultValue = "10") Integer size,
                                                        @RequestParam(required = false, defaultValue = "0") @Min(value = 1) Integer page) {
        User user = sessionUser.user();
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        if (user.getRole().equals(UserRole.ADMIN)) {
            return ResponseEntity.ok(new ResponseDTO<>(Page.empty()));
        }
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        if (user.getRole().equals(UserRole.USER) && user.getJob().getName().equals("Ish beruvchi")) {
            Page<User> allActive = authService.getAllActive(pageable);
            return ResponseEntity.ok(new ResponseDTO<>(allActive));
        }
        if (user.getRole().equals(UserRole.USER)) {
            Page<Ads> allActive = adsService.getAllActive(pageable);
            return ResponseEntity.ok(new ResponseDTO<>(allActive));
        }
        return ResponseEntity.ok(new ResponseDTO<>(Page.empty()));
    }
}
