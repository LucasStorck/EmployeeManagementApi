package com.lucas.employeeManagement.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Set;

@Entity
@Table(name = "employees")
@NoArgsConstructor
public class Employee {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "employee_id")
  private Long id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "job_title", nullable = false)
  private String jobTitle;

  @Column(name = "job_type", nullable = false)
  private String jobType;

  @Column(name = "team", nullable = false)
  private String team;

  @Column(name = "created_at")
  private Instant createdAt;

  @ManyToMany(mappedBy = "employees")
  private Set<User> users;

  public Employee(Long id, String name, String jobTitle, String jobType, String team, Instant createdAt, Set<User> users) {
    this.id = id;
    this.name = name;
    this.jobTitle = jobTitle;
    this.jobType = jobType;
    this.team = team;
    this.createdAt = createdAt;
    this.users = users;
  }

  public Employee(String name, String jobTitle, String jobType, String team) {
    this.name = name;
    this.jobTitle = jobTitle;
    this.jobType = jobType;
    this.team = team;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getJobTitle() {
    return jobTitle;
  }

  public void setJobTitle(String jobTitle) {
    this.jobTitle = jobTitle;
  }

  public String getJobType() {
    return jobType;
  }

  public void setJobType(String jobType) {
    this.jobType = jobType;
  }

  public String getTeam() {
    return team;
  }

  public void setTeam(String team) {
    this.team = team;
  }

  public Set<User> getUsers() {
    return users;
  }

  public void setUsers(Set<User> users) {
    this.users = users;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Instant createdAt) {
    this.createdAt = createdAt;
  }
}