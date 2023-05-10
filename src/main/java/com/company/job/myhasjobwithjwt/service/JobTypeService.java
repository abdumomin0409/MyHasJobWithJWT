package com.company.job.myhasjobwithjwt.service;

import com.company.job.myhasjobwithjwt.domains.JobType;
import com.company.job.myhasjobwithjwt.payload.jobType.JobDto;
import com.company.job.myhasjobwithjwt.payload.jobType.ResponseJobDto;
import com.company.job.myhasjobwithjwt.repository.JobTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JobTypeService {
    private final JobTypeRepository jobTypeRepository;


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

    public List<JobType> allJobType() {
        return jobTypeRepository.findAll();
    }

    public JobType update(JobDto jobUpdateDto) {
        JobType jobType = this.findByName(jobUpdateDto.getOldName());
        jobType.setName(jobUpdateDto.getNewName());
        return jobTypeRepository.save(jobType);
    }

    public String delete(Integer id) {
        JobType jobType = this.findById(id);
        jobType.setActive(false);
        jobTypeRepository.save(jobType);
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
