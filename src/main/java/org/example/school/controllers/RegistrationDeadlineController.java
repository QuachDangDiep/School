package org.example.school.controllers;

import org.example.school.service.RegistrationDeadlineService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/registration_deadline")
public class RegistrationDeadlineController {
    private final RegistrationDeadlineService deadlineService;

    public RegistrationDeadlineController(RegistrationDeadlineService deadlineService) {
        this.deadlineService = deadlineService;
    }

    @PostMapping("/set")
    public String setDeadline(@RequestParam Long classId, @RequestParam String deadline) {
        boolean success = deadlineService.setDeadline(classId, LocalDateTime.parse(deadline));
        return success ? "redirect:/classrooms" : "redirect:/error";
    }
}
