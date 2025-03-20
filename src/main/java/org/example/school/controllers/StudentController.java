package org.example.school.controllers;

import org.example.school.entitis.Student;
import org.example.school.service.ClassroomService;
import org.example.school.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;
    private final ClassroomService classroomService;

    public StudentController(StudentService studentService, ClassroomService classroomService) {
        this.studentService = studentService;
        this.classroomService = classroomService;
    }

    @GetMapping
    public String listStudents(Model model) {
        model.addAttribute("students", studentService.getAllStudents()); // ✅ Đúng
        return "student/list";
    }


    // ✅ Hiển thị form thêm sinh viên
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("classrooms", classroomService.getAllClassrooms());
        return "student/form";
    }

    @PostMapping("/add")
    public String addStudent(@ModelAttribute Student student, Model model) {
        boolean isAdded = studentService.addStudentWithCheck(student);
        if (!isAdded) {
            model.addAttribute("error", "Lớp học đã đủ số lượng sinh viên!");
            model.addAttribute("student", student);
            model.addAttribute("classrooms", classroomService.getAllClassrooms());
            return "student/form"; // Quay lại form nếu lỗi
        }
        return "redirect:/student";
    }


    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Student student = studentService.getStudentById(id);
        if (student != null) {
            model.addAttribute("student", student);
            model.addAttribute("classrooms", classroomService.getAllClassrooms()); // 🛠️ Thêm danh sách lớp học
            return "student/form";
        }
        return "redirect:/student";
    }


    @PostMapping("/update")
    public String updateStudent(@ModelAttribute Student student) {
        studentService.saveStudent(student); // Đảm bảo phương thức này cập nhật cả `classroom`
        return "redirect:/student"; // 🛠️ Sửa đường dẫn đúng
    }


    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return "redirect:/student"; // ✅ Sửa "/students" -> "/student"
    }
}
