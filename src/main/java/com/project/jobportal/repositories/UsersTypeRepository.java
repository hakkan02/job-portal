package com.project.jobportal.repositories;

import com.project.jobportal.entities.CandidateApply;
import com.project.jobportal.entities.CandidateProfile;
import com.project.jobportal.entities.JobPostActivity;
import com.project.jobportal.entities.UsersType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersTypeRepository extends JpaRepository<UsersType, Integer> {

}
