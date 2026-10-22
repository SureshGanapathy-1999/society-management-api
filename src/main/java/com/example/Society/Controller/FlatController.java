package com.example.Society.Controller;

import com.example.Society.DTO.FlatRequestDTO;
import com.example.Society.DTO.FlatResponseDTO;
import com.example.Society.Service.FlatService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flats")

public class FlatController {

    private final FlatService flatService;
    public FlatController(FlatService flatService) {
        this.flatService = flatService;
    }

    //Create flat inside wing
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/wing/{wingId}")
    public FlatResponseDTO createFlat(@PathVariable Long wingId, @Valid @RequestBody FlatRequestDTO flatRequestDTO) {
        return flatService.createFlat(wingId,flatRequestDTO);
    }

    //Get all flats
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<FlatResponseDTO> getAllFlats() {
        return flatService.getAllFlats();
    }

    //Get flat by id
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{flatId}")
    public FlatResponseDTO getFlatById(@PathVariable Long flatId) {
        return flatService.getFlatById(flatId);
    }

    //Get flats by wing
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/wing/{wingId}")
    public List<FlatResponseDTO> getFlatsByWingId(@PathVariable Long wingId) {
        return flatService.getFlatsByWing(wingId);
    }

    //Delete by ID
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{flatId}")
    public String deleteFlatById(@PathVariable Long flatId) {
        return flatService.deleteFlat(flatId);
    }

    //Update flat by ID
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{flatId}")
    public FlatResponseDTO updateFlat(@PathVariable Long flatId, @Valid @RequestBody FlatRequestDTO flatRequestDTO) {
        return flatService.updateFlatInfo(flatId,flatRequestDTO);
    }

}
