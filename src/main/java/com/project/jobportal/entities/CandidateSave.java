package com.project.jobportal.entities;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"userId", "job"}))
public class CandidateSave implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId", referencedColumnName = "user_account_id")
    private CandidateProfile userId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "job", referencedColumnName = "jobPostId")
    private JobPostActivity job;

    public CandidateSave() {
    }

    public CandidateSave(Integer id, CandidateProfile userId, JobPostActivity job) {
        this.id = id;
        this.userId = userId;
        this.job = job;
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

    @Override
    public String toString() {
        return "CandidateSave{" +
                "id=" + id +
                ", userId=" + userId.toString() +
                ", jobPostId=" + job.toString() +
                '}';
    }
}
