package org.example.school.controllers;

import org.example.school.entitis.Classroom;
import org.example.school.entitis.Student;
import org.example.school.service.ClassroomService;
import org.example.school.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/classroom")
public class ClassroomController {
    private final ClassroomService classroomService;
    private final StudentService studentService; // ✅ Inject StudentService

    public ClassroomController(ClassroomService classroomService, StudentService studentService) {
        this.classroomService = classroomService;
        this.studentService = studentService;
    }

    // ✅ Lấy danh sách lớp học
    @GetMapping
    public String listClassrooms(Model model) {
        model.addAttribute("classrooms", classroomService.getAllClassrooms());
        return "classroom/list";
    }

    // ✅ Hiển thị form thêm lớp học
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("classroom", new Classroom());
        return "classroom/form";
    }

    // ✅ Xử lý thêm lớp học
    @PostMapping("/add")
    public String addClassroom(@ModelAttribute Classroom classroom) {
        classroomService.saveClassroom(classroom);
        return "redirect:/classroom";
    }

    // ✅ Hiển thị form cập nhật lớp học
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Classroom classroom = classroomService.getClassroomById(id);
        if (classroom != null) {
            model.addAttribute("classroom", classroom);
            return "classroom/form";
        }
        return "redirect:/classroom";
    }

    // ✅ Xử lý cập nhật lớp học
    @PostMapping("/update")
    public String updateClassroom(@ModelAttribute Classroom classroom) {
        classroomService.saveClassroom(classroom);
        return "redirect:/classroom";
    }

    // ✅ Xóa lớp học
    @GetMapping("/delete/{id}")
    public String deleteClassroom(@PathVariable Long id) {
        classroomService.deleteClassroom(id);
        return "redirect:/classroom";
    }

    // ✅ Hiển thị form chuyển sinh viên
    @GetMapping("/transfer/{id}")
    public String showTransferForm(Model model) {
        model.addAttribute("students", studentService.getAllStudents());
        model.addAttribute("classrooms", classroomService.getAllClassrooms());
        return "classroom/transfer"; // Form chuyển lớp
    }

    // ✅ Xử lý chuyển sinh viên giữa các lớp
    @PostMapping("/transfer")
    public String transferStudent(
            @RequestParam Long studentId,
            @RequestParam Long newClassId,
            Model model)
    {
        boolean success = studentService.transferStudent(studentId, newClassId);
        if (!success) {
            model.addAttribute("error", "Lớp học mới đã đầy, không thể chuyển!");
            return "classroom/transfer";
        }
        return "redirect:/classroom";
    }
}
