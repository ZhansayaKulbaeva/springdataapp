package kz.bitlab.springdataapp.controller;

import kz.bitlab.springdataapp.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping("/newCourse")
    public String newCourse(Model model) {
        model.addAttribute("courses", courseService.getAllCourses());
        model.addAttribute("coursesMap", courseService.getRequestsByCourseId());
        return "newCourse";
    }

    @PostMapping("/addCourse")
    public String addCourse(@RequestParam("name") String name,
                            @RequestParam("price") String price,
                            @RequestParam("desc") String desc) {

        courseService.addCourse(name, price, desc);

        return "redirect:/newCourse";
    }
}
