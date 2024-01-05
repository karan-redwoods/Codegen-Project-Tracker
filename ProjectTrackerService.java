package com.redwoods.ProjectTracker.service;

import java.util.List;

import com.redwoods.ProjectTracker.dtos.ProjectTrackerResponseDto;
import com.redwoods.ProjectTracker.dtos.ProjectTrackerResponseDto;
import com.redwoods.ProjectTracker.models.ProjectTracker;

public interface ProjectTrackerInterface {

    ProjectTrackerResponseDto getProjectTracker(Long projecttrackerId);

    List<ProjectTrackerResponseDto> getProjectTrackers();

    String deleteProjectTracker(Long projecttrackerId);

    ProjectTrackerResponseDto updateProjectTracker(Long projecttrackerId, ProjectTrackerResponseDto projecttrackerRequest);

    Long addProjectTracker(ProjectTracker projecttracker);

}
