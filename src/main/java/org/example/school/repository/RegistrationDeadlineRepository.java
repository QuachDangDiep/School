package org.example.school.repository;

import org.example.school.entitis.RegistrationDeadline;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegistrationDeadlineRepository extends JpaRepository<RegistrationDeadline, Long> {
    Optional<RegistrationDeadline> findByClassroomId(Long classroomId);
}
