package com.project.jobportal.repositories;

import com.project.jobportal.entities.CandidateProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateProfileRepository extends JpaRepository<CandidateProfile, Integer> {

}
