package com.redwoods.ProjectTracker.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.redwoods.ProjectTracker.models.ProjectTracker;

public interface ProjectTrackerRepository extends JpaRepository<ProjectTracker, Long> {
}
