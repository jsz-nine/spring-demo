package dk.nine.demo.controller;

import dk.nine.demo.dto.company.CompanyDto;
import dk.nine.demo.service.CompanyService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class CompanyController {

    @Autowired
    private final CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping("/company/create")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CompanyDto> createCompany(@RequestBody CompanyDto companyDto) {
        try {
            CompanyDto createdCompany = companyService.createCompany(companyDto);
            return new ResponseEntity<>(createdCompany, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/companies")
    @Operation(summary = "Get all companies", description = "Returns a list of persons")
    public List<CompanyDto> getAllCompanies() {
        return companyService.getAllCompanies();
    }

}
