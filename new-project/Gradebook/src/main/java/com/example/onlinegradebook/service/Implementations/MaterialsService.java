package com.example.onlinegradebook.service.Implementations;

import com.example.onlinegradebook.model.binding.MaterialPageViewModel;
import com.example.onlinegradebook.model.binding.admin.AdminGetJsonMaterial;
import com.example.onlinegradebook.model.binding.json.MaterialImportBindingModel;
import com.example.onlinegradebook.model.entity.ClassesSchool;
import com.example.onlinegradebook.model.entity.Material;
import com.example.onlinegradebook.model.entity.MaterialSchool;
import com.example.onlinegradebook.model.entity.User;
import com.example.onlinegradebook.repository.MaterialRepository;
import com.example.onlinegradebook.repository.MaterialSchoolRepository;
import com.example.onlinegradebook.service.ClassService;
import com.example.onlinegradebook.service.MaterialService;
import com.example.onlinegradebook.service.SubjectService;
import com.example.onlinegradebook.service.UserService;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Service
public class MaterialsService implements MaterialService {

    private final MaterialRepository materialRepository;
    private final SubjectService subjectService;
    private final ClassService classService;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final MaterialSchoolRepository materialSchoolRepository;
    public MaterialsService(MaterialRepository materialRepository, SubjectService subjectService, ClassService classService, UserService userService, ModelMapper modelMapper, Gson gson, MaterialSchoolRepository materialSchoolRepository) {
        this.materialRepository = materialRepository;
        this.subjectService = subjectService;
        this.classService = classService;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.materialSchoolRepository = materialSchoolRepository;
    }

    @Override
    public void saveMaterials(AdminGetJsonMaterial test) {
        MaterialImportBindingModel[] materials = gson.fromJson(test.getTest(), MaterialImportBindingModel[].class);
        Stream.of(materials).forEach(m -> {
            if(m.getMaterial()!=null) {
                Material material = materialRepository.findByName(m.getMaterial()).orElse(null);
                if (material == null) {
                    material = new Material();
                    material.setSubjects(subjectService.getSubjectByName(m.getSubject()));
                    material.setName(m.getMaterial());
                    System.out.println();
                    materialRepository.saveAndFlush(material);
                }
                String[] token = m.getClasses().split("\\s+");
                if (token.length != 2) {
                    token[1] = String.format("%s %s", token[1], token[2]);
                }
                System.out.println();
                List<ClassesSchool> classBySpecialityAndClass = classService.getClassBySpecialityAndClassAndSchool(token[1], token[0], userService.getUser().getSchool());

                if (classBySpecialityAndClass.size() != 1) {

                    Material finalMaterial = material;

                    classBySpecialityAndClass.forEach(c -> {

                        MaterialSchool materialSchool = new MaterialSchool();

                        materialSchool.setUser(null);

                        materialSchool.setTaken(false);

                        materialSchool.setMaterial(finalMaterial);

                        materialSchool.setSubject(subjectService.getSubjectByName(m.getSubject()));

                        materialSchool.setSchool(userService.getUser().getSchool());

                        materialSchool.setDate(LocalDateTime.now());

                        materialSchool.setClasses(c);

                        materialSchoolRepository.saveAndFlush(materialSchool);

                    });
                } else {
                    MaterialSchool materialSchool = new MaterialSchool();

                    materialSchool.setUser(null);

                    materialSchool.setTaken(false);

                    materialSchool.setMaterial(material);

                    materialSchool.setSubject(subjectService.getSubjectByName(m.getSubject()));

                    materialSchool.setSchool(userService.getUser().getSchool());

                    materialSchool.setDate(LocalDateTime.now());

                    materialSchool.setClasses(classBySpecialityAndClass.get(0));

                    materialSchoolRepository.saveAndFlush(materialSchool);

                }
            }
        });
    }

    @Override
    public List<MaterialPageViewModel> getMaterialsByIfItsTaken(boolean taken) {
        List<MaterialPageViewModel> materialList=new ArrayList<>();

        materialSchoolRepository.getAllBySchoolAndTaken(userService.getUser().getSchool(), taken).forEach(m-> {

            MaterialPageViewModel model=new MaterialPageViewModel();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            model.setDate(m.getDate().format(formatter));

            model.setId(m.getId());

            model.setSubject(m.getSubject().getName());

            model.setName(m.getMaterial().getName());

            model.setClasses(String.format("%s%s",m.getClasses().getClasses().getClassNumber(),m.getClasses().getLetter()));

            if (m.getUser()!=null)

                model.setTeacher(String.format("%s %s",m.getUser().getFirstName(),m.getUser().getLastName()));


            materialList.add(model);
        });
        return materialList;
    }

    @Override
    public void takeMaterial(String id, boolean b, User user) {
        materialSchoolRepository.takeMaterial(b, user, id);
    }

    @Override
    public void removeMaterial(String id) {
        materialSchoolRepository.deleteById(id);
    }
}
