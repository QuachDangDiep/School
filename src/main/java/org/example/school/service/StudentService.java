package org.example.school.service;

import org.example.school.entitis.Classroom;
import org.example.school.entitis.Student;
import org.example.school.repository.ClassroomRepository;
import org.example.school.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final ClassroomService classroomService;
    @Autowired
    private final ClassroomRepository classroomRepository;

    public StudentService(StudentRepository studentRepository, ClassroomService classroomService, ClassroomRepository classroomRepository) {
        this.studentRepository = studentRepository;
        this.classroomService = classroomService;
        this.classroomRepository = classroomRepository;
    }

    public List<Student> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        System.out.println("Danh sách sinh viên: " + students); // Debug log
        return students;
    }


    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    public boolean addStudentWithCheck(Student student) {
        // Lấy lớp học từ database
        Classroom classroom = classroomRepository.findById(student.getClassroom().getId()).orElse(null);
        if (classroom == null) return false; // Nếu lớp không tồn tại, không thêm sinh viên

        // Kiểm tra số lượng sinh viên hiện tại
        int currentStudents = studentRepository.countByClassroomId(classroom.getId());
        if (currentStudents >= classroom.getMaxStudents()) {
            return false; // Lớp đã đủ sinh viên
        }

        // Nếu còn chỗ, lưu sinh viên
        studentRepository.save(student);
        return true;
    }

    public boolean transferStudent(Long studentId, Long newClassId) {
        Student student = studentRepository.findById(studentId).orElse(null);
        Classroom newClass = classroomRepository.findById(newClassId).orElse(null);

        if (student == null || newClass == null) {
            return false;
        }

        // Kiểm tra số lượng sinh viên hiện tại trong lớp mới
        int studentCount = studentRepository.countByClassroomId(newClassId);
        if (studentCount >= newClass.getMaxStudents()) {
            return false; // Lớp đã đầy, không thể chuyển
        }

        student.setClassroom(newClass);
        studentRepository.save(student);
        return true;
    }
}
