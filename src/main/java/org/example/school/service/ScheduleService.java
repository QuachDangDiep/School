package org.example.school.service;

import org.example.school.entitis.Classroom;
import org.example.school.entitis.RegistrationDeadline;
import org.example.school.entitis.Schedule;
import org.example.school.entitis.Student;
import org.example.school.repository.ClassroomRepository;
import org.example.school.repository.RegistrationDeadlineRepository;
import org.example.school.repository.ScheduleRepository;
import org.example.school.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final RegistrationDeadlineRepository deadlineRepository;
    private final ClassroomRepository classroomRepository;
    private final StudentRepository studentRepository;


    public ScheduleService(ScheduleRepository scheduleRepository, RegistrationDeadlineRepository deadlineRepository, ClassroomRepository classroomRepository, StudentRepository studentRepository) {
        this.scheduleRepository = scheduleRepository;
        this.deadlineRepository = deadlineRepository;
        this.classroomRepository = classroomRepository;
        this.studentRepository = studentRepository;
    }

    public List<Schedule> getScheduleByStudent(Long studentId) {
        return scheduleRepository.findByStudentId(studentId);
    }

    public boolean addSchedule(Long studentId, Long classId, LocalDateTime startTime, LocalDateTime endTime) {
        Student student = studentRepository.findById(studentId).orElse(null);
        Classroom classroom = classroomRepository.findById(classId).orElse(null);

        if (student == null || classroom == null) return false;

        // Kiểm tra số lượng sinh viên tối đa
        if (classroom.getStudents().size() >= classroom.getMaxStudents()) {
            return false;
        }

        // Kiểm tra hạn chót đăng ký
        Optional<RegistrationDeadline> deadline = deadlineRepository.findByClassroomId(classId);
        if (deadline.isPresent() && LocalDateTime.now().isAfter(deadline.get().getDeadline())) {
            return false;
        }

        Schedule schedule = new Schedule(null, student, classroom, startTime, endTime);
        scheduleRepository.save(schedule);
        return true;
    }
}
