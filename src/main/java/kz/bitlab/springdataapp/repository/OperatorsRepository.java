package kz.bitlab.springdataapp.repository;

import kz.bitlab.springdataapp.model.Operators;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperatorsRepository extends JpaRepository<Operators,Long> {
}
