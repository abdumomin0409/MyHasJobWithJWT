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

import java.util.List;

import static com.company.job.myhasjobwithjwt.utils.BaseUrls.JOB_TYPE_URL;

@RestController
@RequestMapping(JOB_TYPE_URL)
//@RequiredArgsConstructor
@Tag(name = "Job type", description = "Job type API")
public class JobTypeController {

    private final JobTypeService jobTypeService;

    public JobTypeController(@Lazy JobTypeService jobTypeService) {
        this.jobTypeService = jobTypeService;
    }


    @Operation(summary = "This API is used for saving job type", responses = {
            @ApiResponse(responseCode = "201", description = "Job type saved", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @PreAuthorize("hasRole('ADMIN' || 'USER')")
    @PostMapping("/create")
    public ResponseEntity<ResponseDTO<JobType>> save(@RequestParam String jobName) {
        JobType save = this.jobTypeService.save(jobName);
        return ResponseEntity.status(201).body(new ResponseDTO<>(save));
    }


    @Operation(summary = "This API is used for returning the job type", responses = {
            @ApiResponse(responseCode = "200", description = "Job type returned", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @PreAuthorize("hasRole('ADMIN' || 'USER')")
    @GetMapping("/find/name")
    public ResponseEntity<ResponseDTO<ResponseJobDto>> findByName(@RequestParam String jobName) {
        JobType byName = this.jobTypeService.findByName(jobName);
        ResponseJobDto body = new ResponseJobDto(byName.getId(), byName.getName());
        return ResponseEntity.ok(new ResponseDTO<>(body));
    }


    @Operation(summary = "This API is used for returning the job type", responses = {
            @ApiResponse(responseCode = "200", description = "Job type returned", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @PreAuthorize("hasRole('ADMIN' || 'USER')")
    @GetMapping("/find/id/{id}")
    public ResponseEntity<ResponseDTO<ResponseJobDto>> findById(@PathVariable Integer id) {
        JobType byId = this.jobTypeService.findById(id);
        ResponseJobDto dto = new ResponseJobDto(byId.getId(), byId.getName());
        return ResponseEntity.ok(new ResponseDTO<>(dto));
    }


    @Operation(summary = "This API is used for returning all job type", responses = {
            @ApiResponse(responseCode = "200", description = "Job types returned", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @PreAuthorize("hasRole('ADMIN' || 'USER')")
    @GetMapping("/find/all")
    public ResponseEntity<ResponseDTO<List<ResponseJobDto>>> findAll() {
        List<ResponseJobDto> all = this.jobTypeService.findAll();
        return ResponseEntity.ok(new ResponseDTO<>(all));
    }


    @Operation(summary = "This API is used for returning all job type", responses = {
            @ApiResponse(responseCode = "200", description = "Job types returned", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/find/all/fixed")
    public ResponseEntity<ResponseDTO<Page<JobType>>> allJobType(@RequestParam(required = false, defaultValue = "10") Integer size,
                                                                 @RequestParam(required = false, defaultValue = "1") @Min(value = 1) Integer page) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<JobType> all = this.jobTypeService.allJobType(pageable);
        return ResponseEntity.ok(new ResponseDTO<>(all));
    }


    @Operation(summary = "This API is used for updating job type", responses = {
            @ApiResponse(responseCode = "200", description = "Job type updated", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @PreAuthorize("hasRole('ADMIN' || 'USER')")
    @PutMapping("/update")
    public ResponseEntity<ResponseDTO<JobType>> update(@RequestBody JobDto jobUpdateDto) {
        JobType update = this.jobTypeService.update(jobUpdateDto);
        return ResponseEntity.ok(new ResponseDTO<>("Job type updated successfully", update));
    }


    @Operation(summary = "This API is used for deleting job type", responses = {
            @ApiResponse(responseCode = "200", description = "Job type deleted", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @PreAuthorize("hasRole('ADMIN' || 'USER')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDTO<Void>> delete(@PathVariable Integer id) {
        String delete = this.jobTypeService.delete(id);
        return ResponseEntity.ok(new ResponseDTO<>(delete, null));
    }

}
