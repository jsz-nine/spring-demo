package dk.nine.demo.service;

import dk.nine.demo.dto.company.CompanyDto;
import dk.nine.demo.repository.CompanyRepository;
import dk.nine.demo.view.CompanyMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CompanyService {
    private final CompanyRepository companyRepository;

    private final CompanyMapper companyMapper;

    @Autowired
    public CompanyService(CompanyRepository companyRepository, CompanyMapper companyMapper) {
        this.companyRepository = companyRepository;
        this.companyMapper = companyMapper;
    }

    public CompanyDto createCompany(CompanyDto companyDto) {
        return companyMapper.toDto(companyRepository.save(companyMapper.toEntity(companyDto)));


    }

    public List<CompanyDto> getAllCompanies() {
        return companyRepository.findAll().stream()
                .map(companyMapper::toDto)
                .collect(Collectors.toList());
    }


    public CompanyDto getCompany(String name) {
        return companyRepository.findByName(name)
                .map(companyMapper::toDto)
                .orElseThrow(
                        () -> new RuntimeException("user not found!"));
    }
}
