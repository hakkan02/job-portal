package com.project.jobportal.services;

import com.project.jobportal.entities.CandidateApply;
import com.project.jobportal.entities.CandidateProfile;
import com.project.jobportal.entities.JobPostActivity;
import com.project.jobportal.repositories.CandidateApplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidateApplyService {

    private final CandidateApplyRepository candidateApplyRepository;

    @Autowired
    public CandidateApplyService (CandidateApplyRepository candidateApplyRepository) {
        this.candidateApplyRepository = candidateApplyRepository;
    }

    public List<CandidateApply> getCandidatesJobs(CandidateProfile userAccountId) {
        return candidateApplyRepository.findByUserId(userAccountId);
    }

    public List<CandidateApply>  getJobCandidates(JobPostActivity job) {
        return candidateApplyRepository.findByJob(job);
    }

    public void addNew(CandidateApply candidateApply) {
        candidateApplyRepository.save(candidateApply);
    }
}
