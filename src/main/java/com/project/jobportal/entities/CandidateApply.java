package com.project.jobportal.entities;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"userId", "job"})})
public class CandidateApply implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId", referencedColumnName = "user_account_id")
    private CandidateProfile userId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "job", referencedColumnName = "jobPostId")
    private JobPostActivity job;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date appliedDate;

    private String coverLetter;


    public CandidateApply() {
    }

    public CandidateApply(Integer id, CandidateProfile userId, JobPostActivity job, Date appliedDate, String coverLetter) {
        this.id = id;
        this.userId = userId;
        this.job = job;
        this.appliedDate = appliedDate;
        this.coverLetter = coverLetter;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CandidateProfile getUserId() {
        return userId;
    }

    public void setUserId(CandidateProfile userId) {
        this.userId = userId;
    }

    public JobPostActivity getJob() {
        return job;
    }

    public void setJob(JobPostActivity job) {
        this.job = job;
    }

    public Date getApplyDate() {
        return appliedDate;
    }

    public void setApplyDate(Date appliedDate) {
        this.appliedDate = appliedDate;
    }

    public String getCoverLetter() {
        return coverLetter;
    }

    public void setCoverLetter(String coverLetter) {
        this.coverLetter = coverLetter;
    }

    @Override
    public String toString() {
        return "CandidateApply{" +
                "id=" + id +
                ", userId=" + userId +
                ", job=" + job +
                ", applyDate=" + appliedDate +
                ", coverLetter='" + coverLetter + '\'' +
                '}';
    }
}
