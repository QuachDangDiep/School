package org.example.school.controllers;

import org.example.school.service.ScheduleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/schedule")
public class ScheduleController {
    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping("/{studentId}")
    public String viewSchedule(@PathVariable Long studentId, Model model) {
        model.addAttribute("schedules", scheduleService.getScheduleByStudent(studentId));
        return "schedule/list";
    }

    @PostMapping("/add")
    public String addSchedule(@RequestParam Long studentId, @RequestParam Long classId,
                              @RequestParam String startTime, @RequestParam String endTime) {
        boolean success = scheduleService.addSchedule(
                studentId, classId,
                LocalDateTime.parse(startTime),
                LocalDateTime.parse(endTime)
        );

        return success ? "redirect:/schedules/" + studentId : "redirect:/error";
    }
}
