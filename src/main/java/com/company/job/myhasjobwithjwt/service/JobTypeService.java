package com.company.job.myhasjobwithjwt.service;

import com.company.job.myhasjobwithjwt.domains.JobType;
import com.company.job.myhasjobwithjwt.domains.User;
import com.company.job.myhasjobwithjwt.payload.jobType.JobDto;
import com.company.job.myhasjobwithjwt.payload.jobType.ResponseJobDto;
import com.company.job.myhasjobwithjwt.repository.JobTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class JobTypeService {
    private final JobTypeRepository jobTypeRepository;
    private final UserService userService;


    public JobType save(String jobName) {
        if (jobTypeRepository.existsByIsActiveTrueAndName(jobName)) {
            throw new RuntimeException("Job type already exists");
        }
        JobType jobType = JobType.builder()
                .name(jobName)
                .isActive(true)
                .build();
        return jobTypeRepository.save(jobType);
    }

    public List<ResponseJobDto> findAll() {
        List<ResponseJobDto> all = new ArrayList<>();
        List<JobType> allByIsActiveTrue = jobTypeRepository.findAllByIsActiveTrue();
        allByIsActiveTrue.forEach(jobType -> {
            all.add(ResponseJobDto.builder()
                    .id(jobType.getId())
                    .name(jobType.getName())
                    .build());
        });
        return all;
    }

    public Page<JobType> allJobType(Pageable pageable) {
        return jobTypeRepository.findAll(pageable);
    }

    public JobType update(JobDto jobUpdateDto) {
        this.findByName(jobUpdateDto.getOldName());
        jobTypeRepository.updateByActiveTrueAndName(jobUpdateDto.getNewName(), jobUpdateDto.getOldName());
        return this.findByName(jobUpdateDto.getNewName());
    }

    public String delete(Integer id) {
        JobType jobType = this.findById(id);
        User user = this.userService.findJobType(jobType);
        if (!Objects.isNull(user)) {
            throw new RuntimeException("This job type is used by user");
        }
        jobTypeRepository.deleteById(id);
        return "Job type successfully deleted";
    }

    public JobType findByName(String jobName) {
        return jobTypeRepository.findByName(jobName)
                .orElseThrow(() -> new RuntimeException("Job type not found"));
    }

    public JobType findById(Integer id) {
        return jobTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job type not found"));
    }

}
