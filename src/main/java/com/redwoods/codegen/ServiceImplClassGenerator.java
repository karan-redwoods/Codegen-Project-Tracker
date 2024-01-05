package com.redwoods.codegen;

import com.redwoods.codegen.config.ApplicationConfig;
import org.springframework.stereotype.Component;

@Component
public class ServiceImplClassGenerator {

    private ApplicationConfig applicationConfig;
    private CodeWriter codeWriter;

    public ServiceImplClassGenerator(ApplicationConfig applicationConfig, CodeWriter codeWriter){
        this.applicationConfig = applicationConfig;
        this.codeWriter = codeWriter;
    }

    public void generateServiceImplClass(String entityName){

        String basePackage = applicationConfig.getPackageName();
        String serviceInterface = entityName+"Service";
        String serviceImplementation = entityName+"ServiceImpl";

        String content = String.format("package %s.service.impl;\n\n" +
                        "import java.util.ArrayList;\n" +
                        "import java.util.List;\n" +
                        "import java.util.Optional;\n\n" +
                        "import org.modelmapper.ModelMapper;\n" +
                        "import org.slf4j.Logger;\n" +
                        "import org.slf4j.LoggerFactory;\n" +
                        "import org.springframework.stereotype.Service;\n\n" +
                        "import %s.dtos.ProjectTrackerResponseDto;\n" +
                        "import %s.dtos.ProjectTrackerRequestDto;\n" +
                        "import %s.exceptions.NotFoundException;\n" +
                        "import %s.models.Address;\n" +
                        "import %s.models.ProjectTracker;\n" +
                        "import %s.models.ProjectTrackerContact;\n" +
                        "import %s.models.ProjectTrackerData;\n" +
                        "import %s.repos.ProjectTrackerRepository;\n" +
                        "import %s.service.%s;\n\n" +
                        "@Service\n" +
                        "public class %s implements %s {\n\n" +
                        "   private ProjectTrackerRepository ProjectTrackerRepository;\n" +
                        "   private ModelMapper modelMapper;\n" +
                        "    private static final Logger LOGGER = LoggerFactory.getLogger(%s.class);\n\n" +
                        "    public %s(ProjectTrackerRepository ProjectTrackerRepository, ModelMapper modelMapper) {\n" +
                        "        this.ProjectTrackerRepository = ProjectTrackerRepository;\n" +
                        "        this.modelMapper = modelMapper;\n" +
                        "    }\n\n" +
                        "    @Override\n" +
                        "    public ProjectTrackerResponseDto getProjectTracker(Long ProjectTrackerId) throws NotFoundException {\n" +
                        "        try {\n" +
                        "            Optional<ProjectTracker> optionalProjectTracker = ProjectTrackerRepository.findById(ProjectTrackerId);\n" +
                        "            if (optionalProjectTracker.isEmpty()) {\n" +
                        "                throw new NotFoundException(\"ProjectTracker Doesn't exist.\");\n" +
                        "            }\n\n" +
                        "            return modelMapper.map(optionalProjectTracker.get(), ProjectTrackerResponseDto.class);\n" +
                        "        } catch (Exception ex) {\n" +
                        "            LOGGER.error(\"Error occurred while processing getProjectTracker\", ex);\n" +
                        "            throw new RuntimeException(\"Error occurred while processing getProjectTracker\", ex);\n" +
                        "        }\n" +
                        "    }\n\n" +
                        "    @Override\n" +
                        "    public List<ProjectTrackerResponseDto> getProjectTrackers() {\n" +
                        "        try {\n" +
                        "            List<ProjectTracker> ProjectTrackers = ProjectTrackerRepository.findAll();\n" +
                        "            List<ProjectTrackerResponseDto> ProjectTrackerResponseDtos = null;\n" +
                        "            if (!ProjectTrackers.isEmpty()) {\n" +
                        "                ProjectTrackerResponseDtos = new ArrayList<>();\n" +
                        "                for (ProjectTracker ProjectTracker : ProjectTrackers) {\n" +
                        "                    ProjectTrackerResponseDtos.add(modelMapper.map(ProjectTracker, ProjectTrackerResponseDto.class));\n" +
                        "                }\n" +
                        "            }\n" +
                        "            return ProjectTrackerResponseDtos;\n" +
                        "        } catch (Exception ex) {\n" +
                        "            LOGGER.error(\"Error occurred while processing getProjectTrackers\", ex);\n" +
                        "            throw new RuntimeException(\"Error occurred while processing getProjectTrackers\", ex);\n" +
                        "        }\n" +
                        "    }\n\n" +
                        "    @Override\n" +
                        "    public String deleteProjectTracker(Long ProjectTrackerId) {\n" +
                        "        if (ProjectTrackerRepository.findById(ProjectTrackerId).isPresent()) {\n" +
                        "            ProjectTrackerRepository.deleteById(ProjectTrackerId);\n" +
                        "            return \"ProjectTracker Deleted Successfully!!\";\n" +
                        "        }\n" +
                        "        return \"No ProjectTracker exists in the database with the provided id.\";\n" +
                        "    }\n\n" +
                        "    @Override\n" +
                        "    public ProjectTrackerResponseDto updateProjectTracker(Long ProjectTrackerId, ProjectTrackerRequestDto ProjectTrackerRequestDto) {\n" +
                        "        try {\n" +
                        "            Optional<ProjectTracker> optionalProjectTracker = ProjectTrackerRepository.findById(ProjectTrackerId);\n" +
                        "            if (optionalProjectTracker.isPresent()) {\n" +
                        "                ProjectTracker ProjectTracker = optionalProjectTracker.get();\n" +
                        "                // Update ProjectTracker fields based on ProjectTrackerRequestDto\n\n" +
                        "                if (ProjectTrackerRequestDto.getProjectTrackerContact() != null) {\n" +
                        "                    ProjectTrackerContact ProjectTrackerContact = modelMapper.map(ProjectTrackerRequestDto.getProjectTrackerContact(), ProjectTrackerContact.class);\n" +
                        "                    ProjectTracker.setProjectTrackerContact(ProjectTrackerContact);\n" +
                        "                }\n\n" +
                        "                // update address\n" +
                        "                if (ProjectTrackerRequestDto.getAddress() != null) {\n" +
                        "                    List<Address> addressList = List.of(modelMapper.map(ProjectTrackerRequestDto.getAddress(), Address[].class));\n" +
                        "                    ProjectTracker.setAddress(addressList);\n" +
                        "                }\n\n" +
                        "                // update ProjectTracker data\n" +
                        "                if (ProjectTrackerRequestDto.getProjectTrackerData() != null) {\n" +
                        "                    List<ProjectTrackerData> ProjectTrackerDataList = List.of(modelMapper.map(ProjectTrackerRequestDto.getProjectTrackerData(), ProjectTrackerData[].class));\n" +
                        "                    ProjectTracker.setProjectTrackerData(ProjectTrackerDataList);\n" +
                        "                }\n\n" +
                        "                // Update other fields\n" +
                        "                // ...\n\n" +
                        "                ProjectTracker.setLast_updated_by(\"admin\");\n" +
                        "                ProjectTracker.setLast_updated_on(System.currentTimeMillis());\n\n" +
                        "                return modelMapper.map(ProjectTrackerRepository.save(ProjectTracker), ProjectTrackerResponseDto.class);\n" +
                        "            } else {\n" +
                        "                return null;\n" +
                        "            }\n" +
                        "        } catch (Exception ex) {\n" +
                        "            LOGGER.error(\"Error occurred while processing updateProjectTrackers\", ex);\n" +
                        "            throw new RuntimeException(\"Error occurred while processing updateProjectTrackers\", ex);\n" +
                        "        }\n" +
                        "    }\n\n" +
                        "    @Override\n" +
                        "    public Long addProjectTracker(ProjectTracker ProjectTracker) {\n" +
                        "        try {\n" +
                        "            ProjectTracker newProjectTracker = ProjectTrackerRepository.save(ProjectTracker);\n" +
                        "            return newProjectTracker.getId();\n" +
                        "        } catch (Exception ex) {\n" +
                        "            LOGGER.error(\"Error occurred while processing addProjectTrackers\", ex);\n" +
                        "            throw new RuntimeException(\"Error occurred while processing addProjectTrackers\", ex);\n" +
                        "        }\n" +
                        "    }\n" +
                        "}\n",
                basePackage, basePackage, basePackage, basePackage, basePackage, basePackage, basePackage, basePackage,
                basePackage, serviceInterface, serviceImplementation, serviceImplementation, serviceImplementation, serviceImplementation, serviceImplementation);

        codeWriter.writeToFile(entityName+"ServiceImpl" + ".java", content);
    }
}
