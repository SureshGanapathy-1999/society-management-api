package com.example.Society.Controller;

import com.example.Society.DTO.SocietyRequestDTO;
import com.example.Society.DTO.SocietyResponseDTO;
import com.example.Society.Service.SocietyService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/societies")
public class SocietyController {
    private final SocietyService service;

    public SocietyController(SocietyService service) {
        this.service = service;
    }

    //Create
    @PostMapping
    public SocietyResponseDTO create(@Valid @RequestBody SocietyRequestDTO requestDTO) {
        return service.create(requestDTO);
    }

    //GetALL Societies
    @GetMapping
    public List<SocietyResponseDTO> getAll() {
        return service.getAll();
    }

    //GetBy ID
    @GetMapping("/{id}")
    public SocietyResponseDTO getById(@PathVariable long id){
        return service.findById(id);
    }

    //Update
    @PutMapping("/{id}")
    public SocietyResponseDTO update(@PathVariable long id,@Valid @RequestBody SocietyRequestDTO societyRequestDTO) {
        return service.update(id,societyRequestDTO);
    }

    //Delete

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        service.delete(id);
    }

}
