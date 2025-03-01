package kz.bitlab.springdataapp.controller;

import kz.bitlab.springdataapp.model.ApplicationRequest;
import kz.bitlab.springdataapp.model.Course;
import kz.bitlab.springdataapp.repository.ApplicationRequestRepository;
import kz.bitlab.springdataapp.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ApplicationRequestRepository repository;

    @GetMapping("/newCourse")
    public String newCourse(Model model) {
        List<Course> allcourses = courseRepository.findAll(); // Java, Python

        Map<Long, Integer> coursesMap = new HashMap<>(); // { ключ: 1(courseId), значение: 2 {RequestCount}}

        for (Course course : allcourses) {
            List<ApplicationRequest> allRequestsByCourseId = repository.getAllByCourseId(course.getId());
            coursesMap.put(course.getId(), allRequestsByCourseId.size());
        }

        model.addAttribute("courses", allcourses);
        model.addAttribute("coursesMap", coursesMap);
        return "newCourse";
    }

    @PostMapping("/addCourse")
    public String addCourse(@RequestParam("name") String name,
                            @RequestParam("price") String price,
                            @RequestParam("desc") String desc) {
        Course course = new Course();
        course.setName(name);
        course.setPrice(Integer.parseInt(price));
        course.setDescription(desc);

        courseRepository.save(course);

        return "redirect:/newCourse";
    }
}
