package kz.bitlab.springdataapp.repository;

import kz.bitlab.springdataapp.model.ApplicationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRequestRepository extends JpaRepository<ApplicationRequest,Long> {

}
