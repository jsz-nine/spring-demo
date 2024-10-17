package dk.nine.demo.repository;

import dk.nine.demo.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;


public interface CompanyRepository extends JpaRepository<Company, UUID> {
    Set<Company> findCompaniesByNameContainingIgnoreCase(String name);

}
