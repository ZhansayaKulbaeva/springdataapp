package kz.bitlab.springdataapp.controller;

import kz.bitlab.springdataapp.db.StudentRepository;
import kz.bitlab.springdataapp.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/students")
    public String students(Model model) {
        List<Student> students = studentRepository.findAll();
        model.addAttribute("students_list", students);
        return "students";
    }

    @GetMapping("/details/{id}")
    public String details(Model model,
                          @PathVariable Long id) {
        Student student = studentRepository.findById(id).orElse(null);
        model.addAttribute("student", student);
        return "details";
    }

    @PostMapping("/addStudent")
    public String addStudent(@RequestParam("fullName") String fullName,
                             @RequestParam("exam") int exam) {

        Student student = new Student();
        student.setFullName(fullName);
        student.setExam(exam);

        studentRepository.save(student);

        return "redirect:/students";
    }

    @PutMapping("/update")
    public String update(@RequestParam("fullName") String fullName,
                         @RequestParam("id") Long id,
                         @RequestParam("exam") int exam) {

        Student student = studentRepository.findById(id).orElse(null);

        if (student != null) {
            student.setFullName(fullName);
            student.setExam(exam);
        }

        studentRepository.save(student);

        return "redirect:/students";
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam("id") Long id) {
        studentRepository.deleteById(id);
        return "redirect:/students";
    }
}
