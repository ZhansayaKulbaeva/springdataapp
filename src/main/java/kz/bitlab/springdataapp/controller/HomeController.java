package kz.bitlab.springdataapp.controller;

import kz.bitlab.springdataapp.model.ApplicationRequest;
import kz.bitlab.springdataapp.repository.ApplicationRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private ApplicationRequestRepository applicationRequestRepository;

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
    public String newRequest() {
        return "newRequest";
    }

    @PostMapping("/addRequest")
    public String addRequest(@RequestParam("fullName") String username,
                             @RequestParam("course") String courses,
                             @RequestParam("comment") String comment,
                             @RequestParam("phoneNumber") String phoneNumber) {

        ApplicationRequest applicationRequest = new ApplicationRequest();
        applicationRequest.setCommentary(comment);
        applicationRequest.setPhone(phoneNumber);
        applicationRequest.setUserName(username);
        applicationRequest.setCourseName(courses);
        applicationRequest.setHandled(false);

        applicationRequestRepository.save(applicationRequest);
        return "redirect:/";
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
}
