package org.example.school.service;

import org.example.school.entitis.Classroom;
import org.example.school.repository.ClassroomRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClassroomService {
    private final ClassroomRepository classroomRepository;

    public ClassroomService(ClassroomRepository classroomRepository) {
        this.classroomRepository = classroomRepository;
    }

    public List<Classroom> getAllClassrooms() {
        return classroomRepository.findAll();
    }

    public Classroom getClassroomById(Long id) {
        return classroomRepository.findById(id).orElse(null);
    }

    public Classroom saveClassroom(Classroom classroom) {
        return classroomRepository.save(classroom);
    }

    public void deleteClassroom(Long id) {
        classroomRepository.deleteById(id);
    }

        public List<Classroom> findClassesWithAvailableSlots(Long excludeClassId) {
            return classroomRepository.findAll().stream()
                    .filter(c -> !c.getId().equals(excludeClassId) && c.getStudents().size() < c.getMaxStudents())
                    .collect(Collectors.toList());
        }

}
