package kz.bitlab.springdataapp.controller;

import kz.bitlab.springdataapp.model.ApplicationRequest;
import kz.bitlab.springdataapp.model.Course;
import kz.bitlab.springdataapp.repository.ApplicationRequestRepository;
import kz.bitlab.springdataapp.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {

    @Autowired
    private ApplicationRequestRepository applicationRequestRepository;

    @Autowired
    private CourseRepository courseRepository;

    @GetMapping("/")
    public String home(@RequestParam(value = "check", required = false) String checkValue,
                       Model model) {
        if ("handled".equals(checkValue)) {
            model.addAttribute("appRequests", applicationRequestRepository.findAllByHandled(true));
        } else if ("unhandled".equals(checkValue)) {
            model.addAttribute("appRequests", applicationRequestRepository.findAllByHandled(false));
        }else {
            model.addAttribute("appRequests", applicationRequestRepository.findAllByOrderByHandledAscIdDesc());
        }
        return "index";
    }

    @GetMapping("/newRequest")
    public String newRequest(Model model) {
        model.addAttribute("courses", courseRepository.findAll());
        return "newRequest";
    }

    @GetMapping("/newCourse")
    public String newCourse(Model model) {
        model.addAttribute("courses", courseRepository.findAll());
        return "newCourse";
    }

    @PostMapping("/addRequest")
    public String addRequest(@RequestParam("fullName") String username,
                             @RequestParam("course_id") Long courseId,
                             @RequestParam("comment") String comment,
                             @RequestParam("phoneNumber") String phoneNumber) {

        ApplicationRequest applicationRequest = new ApplicationRequest();
        applicationRequest.setCommentary(comment);
        applicationRequest.setPhone(phoneNumber);
        applicationRequest.setUserName(username);

        Course course = courseRepository.findById(courseId).orElse(null);
        applicationRequest.setCourse(course);
//        applicationRequest.setCourseName(courses);
        applicationRequest.setHandled(false);

        applicationRequestRepository.save(applicationRequest);
        return "redirect:/";
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

    @GetMapping("/details/{idshka}")
    public String details(@PathVariable Long idshka,
                          Model model) {
        ApplicationRequest applicationRequest = applicationRequestRepository.findById(idshka).orElse(null);
        model.addAttribute("appRequest", applicationRequest);
        return "details";
    }

    @PutMapping("/update/{idshka}")
    public String update(@PathVariable Long idshka,
                         @RequestParam("handled") boolean handled) {
        ApplicationRequest applicationRequest = applicationRequestRepository.findById(idshka).orElse(null);
        applicationRequest.setHandled(handled);
        applicationRequestRepository.save(applicationRequest);
        return "redirect:/details/"+idshka;
    }

    @GetMapping("detailsCourse/{id}")
    public String detailsCourse(@PathVariable("id") Long id, Model model){
        Course course = courseRepository.findById(id).orElse(null);
        if (course != null){
            model.addAttribute("course", course);
            return "detailsCourse";
        } else {
            return "redirect:/newCourse";
        }
    }

    @PostMapping("/updateCourse")
    public String editCourse(@ModelAttribute Course course){
        courseRepository.save(course);
        return "redirect:/newCourse";
    }

    @GetMapping("/deleteCourse/{id}")
    public String deleteCourse(@PathVariable("id") Long id){
        Course course = courseRepository.findById(id).orElse(null);
        if (course != null){
            courseRepository.delete(course);
        }
        return "redirect:/newCourse";
    }
}
