package com.api.Service;

import com.api.Entity.Registration;
import com.api.ExceptionHandle.ResourceNotFoundException;
import com.api.Payload.RegistrationDto;
import com.api.Repository.RegistrationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegistrationService {

    private RegistrationRepository registrationRepository;
    private ModelMapper modelMapper;


    public RegistrationService(RegistrationRepository registrationRepository, ModelMapper modelMapper) {
        this.registrationRepository = registrationRepository;
        this.modelMapper = modelMapper;
    }

    public List<RegistrationDto> GetRegistration(){
        List<Registration> registrations = registrationRepository.findAll();
        List<RegistrationDto> dtos = registrations.stream().map(r -> mapToDto(r)).collect(Collectors.toList());
        return dtos;
    }

    public RegistrationDto CreateRegistration(RegistrationDto registrationDto) {
        // copy dto to entity
        Registration registration = mapToEntity(registrationDto);
        Registration savedEntity = registrationRepository.save(registration);

        // copy entity to dto

        RegistrationDto dto = mapToDto(savedEntity);
        return dto;
    }
    Registration mapToEntity(RegistrationDto registrationdto){
        Registration registration = modelMapper.map(registrationdto, Registration.class);
//        Registration registration1 = new Registration();
//        registration1.setName(registrationdto.getName());
//        registration1.setEmail(registrationdto.getEmail());
//        registration1.setMobile(registrationdto.getMobile());
        return registration;
    }
    RegistrationDto mapToDto(Registration registration){
        RegistrationDto dto = modelMapper.map(registration, RegistrationDto.class);
//        RegistrationDto dto = new RegistrationDto();
//        dto.setName(registration.getName());
//        dto.setName(registration.getEmail());
//        dto.setMobile(registration.getMobile());
        return dto;
    }

    public void DeleteRegistration(long id) {
        registrationRepository.deleteById(id);
    }


    public Registration UpdateRegistration(long id, Registration registration) {
        Registration r = registrationRepository.findById(id).get();
        r.setName(registration.getName());
        r.setEmail(registration.getEmail());
        r.setMobile(registration.getMobile());
        Registration saved = registrationRepository.save(r);
        return saved;
        }

    public RegistrationDto getRegistrationbyId(long id) {
        Registration registration = registrationRepository.findById(id).orElseThrow(
                //supplier interface
                ()-> new ResourceNotFoundException("Record not found")
        );
       return mapToDto(registration);
    }
}
