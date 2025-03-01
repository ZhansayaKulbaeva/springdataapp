package kz.bitlab.springdataapp.controller;

import kz.bitlab.springdataapp.model.ApplicationRequest;
import kz.bitlab.springdataapp.model.Course;
import kz.bitlab.springdataapp.model.Operators;
import kz.bitlab.springdataapp.repository.ApplicationRequestRepository;
import kz.bitlab.springdataapp.repository.CourseRepository;
import kz.bitlab.springdataapp.repository.OperatorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private ApplicationRequestRepository applicationRequestRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private OperatorsRepository repository;

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

    @GetMapping("/details/{idshka}")
    public String details(@PathVariable Long idshka,
                          Model model) {
        ApplicationRequest applicationRequest = applicationRequestRepository.findById(idshka).orElse(null);
        model.addAttribute("appRequest", applicationRequest);
        List<Operators> operators = repository.findAll();
        model.addAttribute("operators", operators);
        return "details";
    }

    @PutMapping("/update/{idshka}")
    public String update(@PathVariable Long idshka,
                         @RequestParam("handled") boolean handled,
                         @RequestParam("operators") List<Long> listOperatorsId) {
        ApplicationRequest applicationRequest = applicationRequestRepository.findById(idshka).orElse(null);
        applicationRequest.setHandled(handled);

        if (listOperatorsId!=null){
            List<Operators> operators = new ArrayList<>();

            for (Long operatorId : listOperatorsId) {
                Operators operator = repository.findById(operatorId).orElse(null);
                operators.add(operator);
            }
            applicationRequest.setOperators(operators);
        }
        applicationRequestRepository.save(applicationRequest);
        return "redirect:/details/"+idshka;
    }

    @DeleteMapping("/deleteOperator/{appRequestId}")
    public String update(@PathVariable Long appRequestId,
                         @RequestParam("operatorId") Long operatorId) {

        ApplicationRequest applicationRequest = applicationRequestRepository.findById(appRequestId).orElse(null);

        if (operatorId!=null){
            Operators operator = repository.findById(operatorId).orElse(null);
            applicationRequest.getOperators().remove(operator);
        }
        applicationRequestRepository.save(applicationRequest);
        return "redirect:/details/"+appRequestId;
    }
}
