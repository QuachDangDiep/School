package org.example.school.repository;

import org.example.school.entitis.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    int countByClassroomId(Long classId);
}
