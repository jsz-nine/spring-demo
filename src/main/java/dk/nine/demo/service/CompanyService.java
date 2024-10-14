package dk.nine.demo.service;

import dk.nine.demo.dto.lomboks.CompanyDto;
import dk.nine.demo.model.Company;
import dk.nine.demo.repository.CompanyRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CompanyService(CompanyRepository companyRepository, ModelMapper modelMapper) {
        this.companyRepository = companyRepository;
        this.modelMapper = modelMapper;
    }

    public CompanyDto createCompany(CompanyDto companyDto) {
        return modelMapper.map(
                companyRepository.save(
                        modelMapper.map(
                                companyDto, Company.class)
                ), CompanyDto.class
        );
    }

    public List<CompanyDto> getAllCompanies() {
        return companyRepository.findAll().stream()
                .map(company ->
                {
                    log.debug(String.format("company: %s, id: %s", company.getName(), company.getUuid()));
                    return modelMapper.map(company, CompanyDto.class);
                })
                .collect(Collectors.toList());
    }


    public CompanyDto getCompany(String name) {
        return companyRepository.findByName(name)
                .map(company -> modelMapper
                        .map(company, CompanyDto.class))
                .orElseThrow(
                        () -> new RuntimeException("user not found!"));
    }
}
