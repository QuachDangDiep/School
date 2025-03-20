package org.example.school.repository;

import org.example.school.entitis.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, Long> {
    @Query("SELECT c FROM Classroom c LEFT JOIN FETCH c.students WHERE c.id = :classId")
    Optional<Classroom> findByIdWithStudents(@Param("classId") Long classId);

}
