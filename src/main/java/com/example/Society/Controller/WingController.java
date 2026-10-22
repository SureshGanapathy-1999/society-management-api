package com.example.Society.Controller;

import com.example.Society.DTO.WingRequestDTO;
import com.example.Society.DTO.WingResponseDTO;
import com.example.Society.Service.WingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wing")
public class WingController {

    private final WingService service;

    public WingController(WingService service) {
        this.service = service;
    }

    //Create wing for society
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/society/{societyId}")
    public WingResponseDTO addWingToSociety(@PathVariable Long societyId, @Valid @RequestBody WingRequestDTO wingRequestDTO){
        return service.addWingToSociety(societyId,wingRequestDTO);
    }

    //Get All
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<WingResponseDTO> getAll() {
        return service.getAll();
    }

    //Get By ID
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public WingResponseDTO getById(@PathVariable Long id){
        return service.getById(id);
    }

    //Delete Wing
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id){

        service.delete(id);

        return ResponseEntity.ok("Successfully deleted Wing with id: "+id);
    }

    // Update wing
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public WingResponseDTO update(
            @PathVariable Long id,
            @Valid @RequestBody WingRequestDTO wingRequestDTO) {
        return service.update(id, wingRequestDTO);
    }
}
