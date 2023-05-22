package com.company.job.myhasjobwithjwt.controller;


import com.company.job.myhasjobwithjwt.config.security.SessionUser;
import com.company.job.myhasjobwithjwt.domains.Ads;
import com.company.job.myhasjobwithjwt.domains.User;
import com.company.job.myhasjobwithjwt.domains.enums.UserRole;
import com.company.job.myhasjobwithjwt.payload.ResponseDTO;
import com.company.job.myhasjobwithjwt.payload.user.ResponseUserDto;
import com.company.job.myhasjobwithjwt.payload.user.UserUpdateDto;
import com.company.job.myhasjobwithjwt.service.AdsService;
import com.company.job.myhasjobwithjwt.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.company.job.myhasjobwithjwt.utils.BaseUrls.PROFILE_URL;

@RestController
//@RequiredArgsConstructor
@RequestMapping(PROFILE_URL)
@Tag(name = "Profile", description = "Profile API")
public class ProfileController {
    private final UserService userService;
    private final AdsService adsService;

    public ProfileController(@Lazy UserService userService, AdsService adsService, SessionUser sessionUser) {
        this.userService = userService;
        this.adsService = adsService;
        this.sessionUser = sessionUser;
    }

    private final SessionUser sessionUser;

    @Operation(summary = "This API is used for returning user profile", responses = {
            @ApiResponse(responseCode = "200", description = "User returned", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @PreAuthorize("hasRole('ADMIN' || 'USER')")
    @GetMapping("/get")
    public ResponseEntity<ResponseDTO<ResponseUserDto>> getProfile() {
        ResponseUserDto dto = this.userService.getProfile();
        return ResponseEntity.ok(new ResponseDTO<>(dto));
    }


    @Operation(summary = "This API is used for updating user profile", responses = {
            @ApiResponse(responseCode = "200", description = "User profile updated", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @PreAuthorize("hasRole('ADMIN' || 'USER')")
    @PutMapping("/update")
    public ResponseEntity<ResponseDTO<User>> updateProfile(@RequestBody UserUpdateDto dto) {
        User user = this.userService.update(dto);
        return ResponseEntity.ok(new ResponseDTO<>("User profile updated", user));
    }


    @Operation(summary = "This API is used for get all users ", responses = {
            @ApiResponse(responseCode = "200", description = "Returned all users", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get/all")
    public ResponseEntity<ResponseDTO<Page<User>>> getAll(@RequestParam(required = false, defaultValue = "10") Integer size,
                                                          @RequestParam(required = false, defaultValue = "1") @Min(value = 1) Integer page) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<User> all = this.userService.getAll(pageable);
        return ResponseEntity.ok(new ResponseDTO<>(all));
    }


    @Operation(summary = "This API is used for main menu user", responses = {
            @ApiResponse(responseCode = "200", description = "Users returned", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))
    })
    @PreAuthorize("hasRole('ADMIN' || 'USER')")
    @GetMapping("/menu")
    public ResponseEntity<ResponseDTO<Page<?>>> getMenu(@RequestParam(required = false, defaultValue = "10") Integer size,
                                                        @RequestParam(required = false, defaultValue = "1") @Min(value = 1) Integer page) {
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
            Page<User> allActive = this.userService.getAllActive(pageable);
            return ResponseEntity.ok(new ResponseDTO<>(allActive));
        }
        if (user.getRole().equals(UserRole.USER)) {
            Page<Ads> allActive = this.adsService.getAllActive(pageable);
            return ResponseEntity.ok(new ResponseDTO<>(allActive));
        }
        return ResponseEntity.ok(new ResponseDTO<>(Page.empty()));
    }

}
