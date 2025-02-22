package kz.bitlab.springdataapp.repository;

import kz.bitlab.springdataapp.model.ApplicationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRequestRepository extends JpaRepository<ApplicationRequest,Long> {

    List<ApplicationRequest> findAllByHandled(boolean check);
    List<ApplicationRequest> findAllByOrderByHandledAscIdDesc();
}
