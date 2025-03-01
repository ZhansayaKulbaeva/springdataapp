package kz.bitlab.springdataapp.repository;

import kz.bitlab.springdataapp.model.ApplicationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRequestRepository extends JpaRepository<ApplicationRequest,Long> {

    List<ApplicationRequest> findAllByHandled(boolean check);
    List<ApplicationRequest> findAllByOrderByHandledAscIdDesc();

    //JPQL
    @Query(value = "SELECT a FROM ApplicationRequest a " +
            "WHERE a.course.id=:courseId")
    List<ApplicationRequest> getAllByCourseId(@Param("courseId") Long courseId);

    //SQL
    @Query(value = "SELECT a.* FROM application_request a " +
            "INNER JOIN application_request_operators aro " +
            "on a.id = aro.application_request_id " +
            "WHERE aro.operators_id = :operatorId ", nativeQuery = true)
    List<ApplicationRequest> getAllByOperatorsId(@Param("operatorId") Long op);
}
