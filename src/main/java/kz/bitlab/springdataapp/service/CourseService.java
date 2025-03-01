package kz.bitlab.springdataapp.service;

import kz.bitlab.springdataapp.model.ApplicationRequest;
import kz.bitlab.springdataapp.model.Course;
import kz.bitlab.springdataapp.repository.ApplicationRequestRepository;
import kz.bitlab.springdataapp.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ApplicationRequestRepository repository;

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Map<Long, Integer> getRequestsByCourseId() {
        List<Course> courses = getAllCourses();
        Map<Long, Integer> coursesMap = new HashMap<>(); // { ключ: 1(courseId), значение: 2 {RequestCount}}

        for (Course course : courses) {
            List<ApplicationRequest> allRequestsByCourseId = repository.getAllByCourseId(course.getId());
            coursesMap.put(course.getId(), allRequestsByCourseId.size());
        }
        return coursesMap;
    }

    public void addCourse(String name, String price, String description) {
        Course course = new Course();
        course.setName(name);
        course.setPrice(Integer.parseInt(price));
        course.setDescription(description);

        courseRepository.save(course);
    }
}
