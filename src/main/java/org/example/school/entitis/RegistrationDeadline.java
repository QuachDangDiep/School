package org.example.school.entitis;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "registration_deadline ")
public class RegistrationDeadline {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "class_id", nullable = false)
    private Classroom classroom;
    @Column(nullable = false)
    private LocalDateTime deadline;

    public RegistrationDeadline() {
    }

    public RegistrationDeadline(Long id, Classroom classroom, LocalDateTime deadline) {
        this.id = id;
        this.classroom = classroom;
        this.deadline = deadline;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }
}
