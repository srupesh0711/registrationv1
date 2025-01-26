package com.api.Controller;

import com.api.Entity.Registration;
import com.api.Payload.RegistrationDto;
import com.api.Service.RegistrationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/registration1/") // base Api url exchange the data between 2 apps
// http://localhost:8080/api/v2/registration1
public class RegistrationController {
   private RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }




    @GetMapping()
    public ResponseEntity<List<RegistrationDto>>getAllRegistrations() {
        List<RegistrationDto> registrations = registrationService.GetRegistration();
        return new ResponseEntity<>(registrations, HttpStatus.OK);
    }



    // TODO: Implement other CRUD operations (create, update, delete)
    // http://localhost:8080/api/v2/registration1/create
    @PostMapping("/create")
    public ResponseEntity<?> createRegistration(
         @Valid @RequestBody RegistrationDto registrationDto,
         BindingResult result
         ) {
        if(result.hasErrors()){
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(), HttpStatus.CREATED);
        }
        RegistrationDto regDto = registrationService.CreateRegistration(registrationDto);
        return new ResponseEntity<>(regDto, HttpStatus.CREATED);
    }




    // http://localhost:8080/api/v2/registration1/{id}
    @GetMapping ("/{id}")
    public ResponseEntity<RegistrationDto> getRegistrationById(
            @PathVariable long id

    ){
        RegistrationDto registrationDto = registrationService.getRegistrationbyId(id);
        return new ResponseEntity<>(registrationDto, HttpStatus.OK);
    }




    // http://localhost:8080/api/v2/registration1/delete
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteRegistration(
           @RequestParam long id
    ){
        registrationService.DeleteRegistration(id);
        return new ResponseEntity<>("Registration deleted", HttpStatus.OK);
    }



    // http://localhost:8080/api/v2/registration1/update/{id}
    @PutMapping("/update/{id}")
    public ResponseEntity<Registration> updateRegistration(
            @PathVariable long id,
            @RequestBody Registration registration

    ){
        Registration updatedRegistration =  registrationService.UpdateRegistration(id, registration);
        return new ResponseEntity<>(updatedRegistration, HttpStatus.OK);
    }
}
