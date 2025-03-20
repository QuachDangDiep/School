package org.example.school.service;

import org.example.school.entitis.Classroom;
import org.example.school.entitis.RegistrationDeadline;
import org.example.school.repository.ClassroomRepository;
import org.example.school.repository.RegistrationDeadlineRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RegistrationDeadlineService {
    private final RegistrationDeadlineRepository deadlineRepository;
    private final ClassroomRepository classroomRepository;

    public RegistrationDeadlineService(RegistrationDeadlineRepository deadlineRepository, ClassroomRepository classroomRepository) {
        this.deadlineRepository = deadlineRepository;
        this.classroomRepository = classroomRepository;
    }

    public boolean setDeadline(Long classId, LocalDateTime deadline) {
        Classroom classroom = classroomRepository.findById(classId).orElse(null);
        if (classroom == null) return false;

        RegistrationDeadline registrationDeadline = new RegistrationDeadline(null, classroom, deadline);
        deadlineRepository.save(registrationDeadline);
        return true;
    }
}
