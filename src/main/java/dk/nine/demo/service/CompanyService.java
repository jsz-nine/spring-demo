package dk.nine.demo.service;

import dk.nine.demo.dto.company.CompanyDto;
import dk.nine.demo.model.Company;
import dk.nine.demo.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    @Autowired
    private final ModelMapper modelMapper;


    public CompanyDto createCompany(CompanyDto companyDto) {
        return modelMapper.map(companyRepository.save(modelMapper.map(companyDto, Company.class)), CompanyDto.class);


    }

    public List<CompanyDto> getAllCompanies() {
        return companyRepository.findAll().stream()
                .map(company -> modelMapper.map(company, CompanyDto.class))
                .collect(Collectors.toList());
    }


    public Set<CompanyDto> findCompaniesByName(String query) {
        return companyRepository.findCompaniesByNameContainingIgnoreCase(query).stream()
                .map(company -> modelMapper.map(company, CompanyDto.class)).collect(Collectors.toSet());
    }
}
