package dk.nine.demo.view;

import dk.nine.demo.dto.company.CompanyDto;
import dk.nine.demo.model.Company;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompanyMapper {
    CompanyDto toDto(Company company);
    Company toEntity(CompanyDto companyDto);
}
