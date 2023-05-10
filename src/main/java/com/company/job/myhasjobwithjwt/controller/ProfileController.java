package com.company.job.myhasjobwithjwt.controller;


import com.company.job.myhasjobwithjwt.domains.User;
import com.company.job.myhasjobwithjwt.payload.ResponseDTO;
import com.company.job.myhasjobwithjwt.payload.user.ResponseUserDto;
import com.company.job.myhasjobwithjwt.payload.user.UserUpdateDto;
import com.company.job.myhasjobwithjwt.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.company.job.myhasjobwithjwt.utils.BaseUrls.PROFILE_URL;

@RestController
@RequiredArgsConstructor
@RequestMapping(PROFILE_URL)
public class ProfileController {
    private final UserService userService;

    @Operation(summary = "This API is used for returning user profile", responses = {
            @ApiResponse(responseCode = "200", description = "User returned", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @GetMapping("/get")
    public ResponseEntity<ResponseDTO<ResponseUserDto>> getProfile() {
        ResponseUserDto dto = userService.getProfile();
        return ResponseEntity.ok(new ResponseDTO<>(dto));
    }


    @Operation(summary = "This API is used for updating user profile", responses = {
            @ApiResponse(responseCode = "200", description = "User profile updated", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @PostMapping("/update")
    public ResponseEntity<ResponseDTO<User>> updateProfile(@RequestBody UserUpdateDto dto) {
        User user = userService.update(dto);
        return ResponseEntity.ok(new ResponseDTO<>("User profile updated", user));
    }


}
