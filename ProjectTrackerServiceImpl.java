package com.redwoods.ProjectTracker.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.redwoods.ProjectTracker.dtos.ProjectTrackerResponseDto;
import com.redwoods.ProjectTracker.dtos.ProjectTrackerRequestDto;
import com.redwoods.ProjectTracker.exceptions.NotFoundException;
import com.redwoods.ProjectTracker.models.Address;
import com.redwoods.ProjectTracker.models.ProjectTracker;
import com.redwoods.ProjectTracker.models.ProjectTrackerContact;
import com.redwoods.ProjectTracker.models.ProjectTrackerData;
import com.redwoods.ProjectTracker.repos.ProjectTrackerRepository;
import ProjectTrackerService.service.ProjectTrackerServiceImpl;

@Service
public class ProjectTrackerServiceImpl implements ProjectTrackerServiceImpl {

   private ProjectTrackerRepository ProjectTrackerRepository;
   private ModelMapper modelMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectTrackerServiceImpl.class);

    public ProjectTrackerServiceImpl(ProjectTrackerRepository ProjectTrackerRepository, ModelMapper modelMapper) {
        this.ProjectTrackerRepository = ProjectTrackerRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ProjectTrackerResponseDto getProjectTracker(Long ProjectTrackerId) throws NotFoundException {
        try {
            Optional<ProjectTracker> optionalProjectTracker = ProjectTrackerRepository.findById(ProjectTrackerId);
            if (optionalProjectTracker.isEmpty()) {
                throw new NotFoundException("ProjectTracker Doesn't exist.");
            }

            return modelMapper.map(optionalProjectTracker.get(), ProjectTrackerResponseDto.class);
        } catch (Exception ex) {
            LOGGER.error("Error occurred while processing getProjectTracker", ex);
            throw new RuntimeException("Error occurred while processing getProjectTracker", ex);
        }
    }

    @Override
    public List<ProjectTrackerResponseDto> getProjectTrackers() {
        try {
            List<ProjectTracker> ProjectTrackers = ProjectTrackerRepository.findAll();
            List<ProjectTrackerResponseDto> ProjectTrackerResponseDtos = null;
            if (!ProjectTrackers.isEmpty()) {
                ProjectTrackerResponseDtos = new ArrayList<>();
                for (ProjectTracker ProjectTracker : ProjectTrackers) {
                    ProjectTrackerResponseDtos.add(modelMapper.map(ProjectTracker, ProjectTrackerResponseDto.class));
                }
            }
            return ProjectTrackerResponseDtos;
        } catch (Exception ex) {
            LOGGER.error("Error occurred while processing getProjectTrackers", ex);
            throw new RuntimeException("Error occurred while processing getProjectTrackers", ex);
        }
    }

    @Override
    public String deleteProjectTracker(Long ProjectTrackerId) {
        if (ProjectTrackerRepository.findById(ProjectTrackerId).isPresent()) {
            ProjectTrackerRepository.deleteById(ProjectTrackerId);
            return "ProjectTracker Deleted Successfully!!";
        }
        return "No ProjectTracker exists in the database with the provided id.";
    }

    @Override
    public ProjectTrackerResponseDto updateProjectTracker(Long ProjectTrackerId, ProjectTrackerRequestDto ProjectTrackerRequestDto) {
        try {
            Optional<ProjectTracker> optionalProjectTracker = ProjectTrackerRepository.findById(ProjectTrackerId);
            if (optionalProjectTracker.isPresent()) {
                ProjectTracker ProjectTracker = optionalProjectTracker.get();
                // Update ProjectTracker fields based on ProjectTrackerRequestDto

                if (ProjectTrackerRequestDto.getProjectTrackerContact() != null) {
                    ProjectTrackerContact ProjectTrackerContact = modelMapper.map(ProjectTrackerRequestDto.getProjectTrackerContact(), ProjectTrackerContact.class);
                    ProjectTracker.setProjectTrackerContact(ProjectTrackerContact);
                }

                // update address
                if (ProjectTrackerRequestDto.getAddress() != null) {
                    List<Address> addressList = List.of(modelMapper.map(ProjectTrackerRequestDto.getAddress(), Address[].class));
                    ProjectTracker.setAddress(addressList);
                }

                // update ProjectTracker data
                if (ProjectTrackerRequestDto.getProjectTrackerData() != null) {
                    List<ProjectTrackerData> ProjectTrackerDataList = List.of(modelMapper.map(ProjectTrackerRequestDto.getProjectTrackerData(), ProjectTrackerData[].class));
                    ProjectTracker.setProjectTrackerData(ProjectTrackerDataList);
                }

                // Update other fields
                // ...

                ProjectTracker.setLast_updated_by("admin");
                ProjectTracker.setLast_updated_on(System.currentTimeMillis());

                return modelMapper.map(ProjectTrackerRepository.save(ProjectTracker), ProjectTrackerResponseDto.class);
            } else {
                return null;
            }
        } catch (Exception ex) {
            LOGGER.error("Error occurred while processing updateProjectTrackers", ex);
            throw new RuntimeException("Error occurred while processing updateProjectTrackers", ex);
        }
    }

    @Override
    public Long addProjectTracker(ProjectTracker ProjectTracker) {
        try {
            ProjectTracker newProjectTracker = ProjectTrackerRepository.save(ProjectTracker);
            return newProjectTracker.getId();
        } catch (Exception ex) {
            LOGGER.error("Error occurred while processing addProjectTrackers", ex);
            throw new RuntimeException("Error occurred while processing addProjectTrackers", ex);
        }
    }
}
