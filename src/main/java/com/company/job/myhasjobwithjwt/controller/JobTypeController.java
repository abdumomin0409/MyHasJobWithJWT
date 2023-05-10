package com.company.job.myhasjobwithjwt.controller;

import com.company.job.myhasjobwithjwt.domains.JobType;
import com.company.job.myhasjobwithjwt.payload.ResponseDTO;
import com.company.job.myhasjobwithjwt.payload.jobType.JobDto;
import com.company.job.myhasjobwithjwt.payload.jobType.ResponseJobDto;
import com.company.job.myhasjobwithjwt.service.JobTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.company.job.myhasjobwithjwt.utils.BaseUrls.JOB_TYPE_URL;

@RestController
@RequestMapping(JOB_TYPE_URL)
@RequiredArgsConstructor
@Tag(name = "Job type", description = "Job type API")
public class JobTypeController {

    private final JobTypeService jobTypeService;

    @Operation(summary = "This API is used for saving job type", responses = {
            @ApiResponse(responseCode = "201", description = "Job type saved", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @PostMapping("/save")
    public ResponseEntity<ResponseDTO<JobType>> save(@RequestParam String jobName) {
        JobType save = jobTypeService.save(jobName);
        return ResponseEntity.status(201).body(new ResponseDTO<>(save));
    }


    @Operation(summary = "This API is used for returning the job type", responses = {
            @ApiResponse(responseCode = "200", description = "Job type returned", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @GetMapping("/find/name")
    public ResponseEntity<ResponseDTO<ResponseJobDto>> findByName(@RequestParam String jobName) {
        JobType byName = jobTypeService.findByName(jobName);
        ResponseJobDto body = new ResponseJobDto(byName.getId(), byName.getName());
        return ResponseEntity.ok(new ResponseDTO<>(body));
    }


    @Operation(summary = "This API is used for returning the job type", responses = {
            @ApiResponse(responseCode = "200", description = "Job type returned", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @GetMapping("/find/id/{id}")
    public ResponseEntity<ResponseDTO<ResponseJobDto>> findById(@PathVariable Integer id) {
        JobType byId = jobTypeService.findById(id);
        ResponseJobDto dto = new ResponseJobDto(byId.getId(), byId.getName());
        return ResponseEntity.ok(new ResponseDTO<>(dto));
    }


    @Operation(summary = "This API is used for returning all job type", responses = {
            @ApiResponse(responseCode = "200", description = "Job types returned", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @GetMapping("/find/all")
    public ResponseEntity<ResponseDTO<List<ResponseJobDto>>> findAll() {
        List<ResponseJobDto> all = jobTypeService.findAll();
        return ResponseEntity.ok(new ResponseDTO<>(all));
    }


    @Operation(summary = "This API is used for returning all job type", responses = {
            @ApiResponse(responseCode = "200", description = "Job types returned", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @GetMapping("/find/all/fixed")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO<List<JobType>>> allJobType() {
        List<JobType> all = jobTypeService.allJobType();
        return ResponseEntity.ok(new ResponseDTO<>(all));
    }


    @Operation(summary = "This API is used for updating job type", responses = {
            @ApiResponse(responseCode = "200", description = "Job type updated", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @PutMapping("/update")
    public ResponseEntity<ResponseDTO<JobType>> update(@RequestBody JobDto jobUpdateDto) {
        JobType update = jobTypeService.update(jobUpdateDto);
        return ResponseEntity.ok(new ResponseDTO<>("Job type updated successfully", update));
    }


    @Operation(summary = "This API is used for deleting job type", responses = {
            @ApiResponse(responseCode = "200", description = "Job type deleted", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDTO<String>> delete(@PathVariable Integer id) {
        String delete = jobTypeService.delete(id);
        return ResponseEntity.ok(new ResponseDTO<>("Job type deleted successfully", delete));
    }

}