package com.project.jobportal.controller;

import com.project.jobportal.entities.CandidateProfile;
import com.project.jobportal.entities.CandidateSave;
import com.project.jobportal.entities.JobPostActivity;
import com.project.jobportal.entities.Users;
import com.project.jobportal.services.CandidateProfileService;
import com.project.jobportal.services.CandidateSaveService;
import com.project.jobportal.services.JobPostActivityService;
import com.project.jobportal.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class CandidateSaveController {

    private final UsersService usersService;
    private final CandidateProfileService candidateProfileService;
    private final JobPostActivityService jobPostActivityService;
    private final CandidateSaveService candidateSaveService;

    @Autowired
    public CandidateSaveController (UsersService usersService, CandidateProfileService candidateProfileService, JobPostActivityService jobPostActivityService,CandidateSaveService candidateSaveService) {
        this.usersService = usersService;
        this.candidateProfileService = candidateProfileService;
        this.jobPostActivityService = jobPostActivityService;
        this.candidateSaveService = candidateSaveService;
    }

    @PostMapping("/job-details/save/{id}")
    public String save(@PathVariable("id") int id, CandidateSave candidateSave) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUsername = authentication.getName();
            Users user = usersService.findByEmail(currentUsername);
            Optional<CandidateProfile> candidateProfile = candidateProfileService.getOne(user.getUserId());
            JobPostActivity jobPostActivity = jobPostActivityService.getOne(id);
            if(candidateProfile.isPresent() && jobPostActivity != null) {
                candidateSave.setJob(jobPostActivity);
                candidateSave.setUserId(candidateProfile.get());
            } else {
                throw new RuntimeException("User not found");
            }
            candidateSaveService.addNew(candidateSave);
        }
        return "redirect:/dashboard/";
    }

    @GetMapping("saved-jobs/")
    public String savedJobs(Model model) {

        List<JobPostActivity> jobPost = new ArrayList<>();
        Object currentUserProfile = usersService.getCurrentUserProfile();

        List<CandidateSave> candidateSaveList = candidateSaveService.getCandidateJobs((CandidateProfile) currentUserProfile);
        for(CandidateSave candidateSave : candidateSaveList) {
            jobPost.add(candidateSave.getJob());
        }

        model.addAttribute("jobPost", jobPost);
        model.addAttribute("user", currentUserProfile);

        return "saved-jobs";
    }

}
