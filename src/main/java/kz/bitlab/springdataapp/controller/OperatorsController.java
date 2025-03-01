package kz.bitlab.springdataapp.controller;

import kz.bitlab.springdataapp.model.ApplicationRequest;
import kz.bitlab.springdataapp.model.Course;
import kz.bitlab.springdataapp.model.Operators;
import kz.bitlab.springdataapp.repository.ApplicationRequestRepository;
import kz.bitlab.springdataapp.repository.OperatorsRepository;
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
public class OperatorsController {

    @Autowired
    private OperatorsRepository repository;

    @Autowired
    private ApplicationRequestRepository applicationRequestRepository;

    @GetMapping("/operators")
    public String operators(Model model) {
        List<Operators> allOperators = repository.findAll(); // Java, Python

        Map<Long, Integer> operatorsMap = new HashMap<>(); // { ключ: 1(courseId), значение: 2 {RequestCount}}

        for (Operators op : allOperators) {
            List<ApplicationRequest> allRequestsByCourseId =
                    applicationRequestRepository.getAllByOperatorsId(op.getId());
            operatorsMap.put(op.getId(), allRequestsByCourseId.size());
        }

        model.addAttribute("operatorsmap", operatorsMap);
        model.addAttribute("operators", repository.findAll());
        return "operators";
    }

    @PostMapping("/addOperator")
    public String addCourse(@RequestParam("name") String name,
                            @RequestParam("surname") String surname,
                            @RequestParam("department") String department) {

        Operators operator = new Operators();
        operator.setName(name);
        operator.setSurname(surname);
        operator.setDepartment(department);

        repository.save(operator);

        return "redirect:/operators";
    }
}
