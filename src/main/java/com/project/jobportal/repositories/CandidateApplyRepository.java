package com.project.jobportal.repositories;


import com.project.jobportal.entities.CandidateApply;
import com.project.jobportal.entities.CandidateProfile;
import com.project.jobportal.entities.JobPostActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidateApplyRepository extends JpaRepository<CandidateApply, Integer> {

    List<CandidateApply> findByUserId(CandidateProfile userAccountId);

    List<CandidateApply> findByJob(JobPostActivity job);
}
