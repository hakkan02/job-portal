package com.project.jobportal.repositories;

import com.project.jobportal.entities.CandidateProfile;
import com.project.jobportal.entities.CandidateSave;
import com.project.jobportal.entities.JobPostActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidateSaveRepository extends JpaRepository<CandidateSave, Integer> {

    List<CandidateSave> findByUserId(CandidateProfile userAccountId);

    List<CandidateSave> findByJob(JobPostActivity job);

}
