package com.project.jobportal.services;

import com.project.jobportal.entities.CandidateProfile;
import com.project.jobportal.entities.Users;
import com.project.jobportal.repositories.CandidateProfileRepository;
import com.project.jobportal.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CandidateProfileService {

    private final CandidateProfileRepository candidateProfileRepository;
    private final UsersRepository usersRepository;

    @Autowired
    public CandidateProfileService (CandidateProfileRepository candidateProfileRepository, UsersRepository usersRepository) {
        this.candidateProfileRepository = candidateProfileRepository;
        this.usersRepository = usersRepository;
    }

    public Optional<CandidateProfile> getOne(Integer id) {
        return candidateProfileRepository.findById(id);
    }

    public CandidateProfile addNew(CandidateProfile candidateProfile) {
        return candidateProfileRepository.save(candidateProfile);
    }

    public CandidateProfile getCurrentCandidateProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUsername = authentication.getName();
            Users users = usersRepository.findByEmail(currentUsername).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            Optional<CandidateProfile> candidateProfile = getOne(users.getUserId());
            return candidateProfile.orElse(null);
        }
        else return null;
    }
}
