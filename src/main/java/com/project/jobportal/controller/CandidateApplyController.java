package com.project.jobportal.controller;

import com.project.jobportal.entities.*;
import com.project.jobportal.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class CandidateApplyController {

    private final JobPostActivityService jobPostActivityService;
    private final UsersService usersService;
    private final CandidateApplyService candidateApplyService;
    private final CandidateSaveService candidateSaveService;
    private final CandidateProfileService candidateProfileService;
    private final RecruiterProfileService recruiterProfileService;

    @Autowired
    public CandidateApplyController(JobPostActivityService jobPostActivityService, UsersService usersService, CandidateApplyService candidateApplyService, CandidateSaveService candidateSaveService, CandidateProfileService candidateProfileService, RecruiterProfileService recruiterProfileService) {
        this.jobPostActivityService = jobPostActivityService;
        this.usersService = usersService;
        this.candidateApplyService = candidateApplyService;
        this.candidateSaveService = candidateSaveService;
        this.candidateProfileService = candidateProfileService;
        this.recruiterProfileService = recruiterProfileService;
    }

    @GetMapping("/job-details-apply/{id}")
    public String display(@PathVariable("id") int id, Model model) {

        JobPostActivity jobDetails = jobPostActivityService.getOne(id);
        List<CandidateApply> candidateApplyList = candidateApplyService.getJobCandidates(jobDetails);
        List<CandidateSave> candidateSaveList = candidateSaveService.getJobCanidates(jobDetails);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUsername = authentication.getName();
            Users currentUser = usersService.findByEmail(currentUsername);

            if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("Recruiter"))) {
                RecruiterProfile currentRecruiterProfile = recruiterProfileService.getCurrentRecruiterProfile();
                if (currentRecruiterProfile != null) {
                    model.addAttribute("applyList", candidateApplyList);
                }
            } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("Candidate"))) {
                CandidateProfile currentCandidateProfile = candidateProfileService.getCurrentCandidateProfile();
                if (currentCandidateProfile != null) {
                    boolean exists = candidateApplyList.stream()
                            .anyMatch(apply -> apply.getUserId().getUserAccountId() == currentCandidateProfile.getUserAccountId());
                    boolean saved = candidateSaveList.stream()
                            .anyMatch(save -> save.getUserId().getUserAccountId() == currentCandidateProfile.getUserAccountId());

                    model.addAttribute("alreadyApplied", exists);
                    model.addAttribute("alreadySaved", saved);
                }
            }
        }

        CandidateApply candidateApply = new CandidateApply();
        model.addAttribute("applyJob", candidateApply);
        model.addAttribute("jobDetails", jobDetails);
        model.addAttribute("user", usersService.getCurrentUserProfile());

        return "job-details";
    }


    @PostMapping("/job-details/apply/{id}")
    public String apply(@PathVariable("id") int id, CandidateApply candidateApply) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUsername = authentication.getName();
            Users user = usersService.findByEmail(currentUsername);
            Optional<CandidateProfile> candidateProfile = candidateProfileService.getOne(user.getUserId());
            JobPostActivity jobPostActivity = jobPostActivityService.getOne(id);
            if (candidateProfile.isPresent() && jobPostActivity != null) {
                candidateApply = new CandidateApply();
                candidateApply.setUserId(candidateProfile.get());
                candidateApply.setJob(jobPostActivity);
                candidateApply.setApplyDate(new Date());
            }else {
                throw new RuntimeException("User not found");
            }
            candidateApplyService.addNew(candidateApply);
        }
        return "redirect:/dashboard/";
    }
}
