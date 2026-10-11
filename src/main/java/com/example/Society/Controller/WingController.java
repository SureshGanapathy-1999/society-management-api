package com.example.Society.Controller;

import com.example.Society.DTO.WingRequestDTO;
import com.example.Society.DTO.WingResponseDTO;
import com.example.Society.Service.WingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
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
    @PostMapping("/society/{societyId}")
    public WingResponseDTO addWingToSociety(@PathVariable Long societyId, @Valid @RequestBody WingRequestDTO wingRequestDTO){
        return service.addWingToSociety(societyId,wingRequestDTO);
    }

    //Get All
    @GetMapping
    public List<WingResponseDTO> getAll() {
        return service.getAll();
    }

    //Get By ID
    @GetMapping("/{id}")
    public WingResponseDTO getById(@PathVariable Long id){
        return service.getById(id);
    }

    //Delete Wing
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id){

        service.delete(id);

        return ResponseEntity.ok("Successfully deleted Wing with id: "+id);
    }

    // Update wing
    @PutMapping("/{id}")
    public WingResponseDTO update(
            @PathVariable Long id,
            @Valid @RequestBody WingRequestDTO wingRequestDTO) {
        return service.update(id, wingRequestDTO);
    }
}
