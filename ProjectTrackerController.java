package com.redwoods.ProjectTracker.controllers;

import java.util.List;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.redwoods.ProjectTracker.dtos.ProjectTrackerResponseDto;
import com.redwoods.ProjectTracker.dtos.ProjectTrackerResponseDto;
import com.redwoods.ProjectTracker.models.ProjectTracker;
import com.redwoods.ProjectTracker.service.ProjectTrackerService;

@CrossOrigin
@RestController
@RequestMapping("/api/ProjectTrackerManagement")
public class ProjectTrackerController {

    private ProjectTrackerService projecttrackerService;
    private ModelMapper modelMapper;
    private final Logger LOGGER = LoggerFactory.getLogger(ProjectTrackerController.class);

    public ProjectTrackerController(ProjectTrackerService projecttrackerService, ModelMapper modelMapper) {
        this.projecttrackerService = projecttrackerService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/{projecttrackerId}")
    public ResponseEntity<?> getProjectTracker(@PathVariable("projecttrackerId") Long projecttrackerId) {
        try {
            ProjectTrackerResponseDto ProjecttrackerresponsedtoDto = projecttrackerService.getProjectTracker(projecttrackerId);
            return new ResponseEntity<>(ProjecttrackerresponsedtoDto, HttpStatus.OK);
        } catch (Exception ex) {
            LOGGER.error("Error occurred while processing /ProjectTrackerManagement/" + projecttrackerId, ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("")
    public ResponseEntity<?> getAllProjectTrackers() {
        try{
            List<ProjectTrackerResponseDto> ProjecttrackerresponsedtoDto = projecttrackerService.getProjectTrackers();
            if (ProjecttrackerresponsedtoDto != null) {
                return new ResponseEntity<>(ProjecttrackerresponsedtoDto, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("No ProjectTrackers available!!", HttpStatus.OK);
            }
        } catch (Exception ex) {
            LOGGER.error("Error occurred while processing /ProjectTrackerManagement", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("")
    public ResponseEntity<String> addProjectTracker(@RequestBody ProjectTrackerResponseDto ProjecttrackerresponsedtoDto) {
        try{
            ProjectTracker projecttracker = modelMapper.map(ProjecttrackerresponsedtoDto, ProjectTracker.class);
            Long projecttrackerId = projecttrackerService.addProjectTracker(projecttracker);
            return new ResponseEntity<>(String.format("New ProjectTracker added %s successfully.", projecttrackerId), HttpStatus.CREATED);
        } catch (Exception ex) {
            LOGGER.error("Error occurred while processing /ProjectTrackerManagement", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{projecttrackerId}")
    public ResponseEntity<?> updateProjectTracker(@PathVariable("projecttrackerId") Long projecttrackerId, @RequestBody ProjectTrackerResponseDto ProjecttrackerresponsedtoDto) {
        try{
            ProjectTrackerResponseDto ProjecttrackerresponsedtoDto = projecttrackerService.updateProjectTracker(projecttrackerId, ProjecttrackerresponsedtoDto);
            if (ProjecttrackerresponsedtoDto != null)
                return new ResponseEntity<>(ProjecttrackerresponsedtoDto, HttpStatus.CREATED);
            else
                return new ResponseEntity<>(String.format("No ProjectTracker exist in the database with provided id %s.", projecttrackerId), HttpStatus.OK);
        } catch (Exception ex) {
            LOGGER.error("Error occurred while processing /ProjectTrackerManagement/" + projecttrackerId, ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{projecttrackerId}")
    public ResponseEntity<String> deleteProjectTracker(@PathVariable("projecttrackerId") Long projecttrackerId) {
        String message = projecttrackerService.deleteProjectTracker(projecttrackerId);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
