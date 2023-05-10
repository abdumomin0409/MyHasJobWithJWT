package com.company.job.myhasjobwithjwt.controller;

import com.company.job.myhasjobwithjwt.domains.Ads;
import com.company.job.myhasjobwithjwt.payload.ResponseDTO;
import com.company.job.myhasjobwithjwt.payload.ads.AdsCreateDto;
import com.company.job.myhasjobwithjwt.payload.ads.AdsUpdateDto;
import com.company.job.myhasjobwithjwt.service.AdsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.company.job.myhasjobwithjwt.utils.BaseUrls.ADS_URL;

@RestController
@RequiredArgsConstructor
@RequestMapping(ADS_URL)
@Tag(name = "Ads", description = "This API is used for ads")
public class AdsController {
    private final AdsService adsService;


    @Operation(summary = "This API is used for create ads", responses = {
            @ApiResponse(responseCode = "200", description = "Ads Created", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @PostMapping("/create")
    public ResponseEntity<ResponseDTO<Ads>> save(@Valid @RequestBody AdsCreateDto dto) {
        Ads ads = adsService.save(dto);
        return ResponseEntity.ok(new ResponseDTO<>(ads));
    }


    @Operation(summary = "This API is used for get ads by id", responses = {
            @ApiResponse(responseCode = "200", description = "Ads found", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Ads not found", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @GetMapping("/get/id/{id}")
    public ResponseEntity<ResponseDTO<Ads>> getById(@PathVariable String id) {
        Ads ads = adsService.getById(id);
        return ResponseEntity.ok(new ResponseDTO<>(ads));
    }


    @Operation(summary = "This API is used for get all ads", responses = {
            @ApiResponse(responseCode = "200", description = "Ads found", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Ads not found", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @GetMapping("/get/all/active")
    public ResponseEntity<ResponseDTO<List<Ads>>> getAllActive() {
        List<Ads> ads = adsService.getAllActive();
        return ResponseEntity.ok(new ResponseDTO<>(ads));
    }


    @Operation(summary = "This API is used for get all fixed ads", responses = {
            @ApiResponse(responseCode = "200", description = "Ads found", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Ads not found", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @GetMapping("/get/all/fixed")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO<List<Ads>>> getAll() {
        List<Ads> ads = adsService.getAllFixed();
        return ResponseEntity.ok(new ResponseDTO<>(ads));
    }


    @Operation(summary = "This API is used for update ads", responses = {
            @ApiResponse(responseCode = "200", description = "Ads updated", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Ads not found", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @PutMapping("/update")
    public ResponseEntity<ResponseDTO<Ads>> update(@RequestBody AdsUpdateDto dto) {
        Ads ads = adsService.update(dto);
        return ResponseEntity.ok(new ResponseDTO<>(ads));
    }


    @Operation(summary = "This API is used for delete ads", responses = {
            @ApiResponse(responseCode = "200", description = "Ads deleted", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Ads not found", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDTO<String>> delete(@PathVariable String id) {
        String delete = adsService.delete(id);
        return ResponseEntity.ok(new ResponseDTO<>(delete));
    }

}
