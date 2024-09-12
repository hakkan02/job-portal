package com.project.jobportal.services;

import com.project.jobportal.entities.CandidateProfile;
import com.project.jobportal.entities.CandidateSave;
import com.project.jobportal.entities.JobPostActivity;
import com.project.jobportal.repositories.CandidateSaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidateSaveService {

    private final CandidateSaveRepository candidateSaveRepository;

    @Autowired
    public CandidateSaveService (CandidateSaveRepository candidateSaveRepository) {
        this.candidateSaveRepository = candidateSaveRepository;
    }

    public List<CandidateSave> getCandidateJobs(CandidateProfile userAccountId) {
        return candidateSaveRepository.findByUserId(userAccountId);
    }

    public List<CandidateSave> getJobCanidates(JobPostActivity job) {
        return candidateSaveRepository.findByJob(job);
    }

    public void addNew(CandidateSave candidateSave) {
        candidateSaveRepository.save(candidateSave);
    }
}
