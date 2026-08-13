package com.example.Society.Controller;

import com.example.Society.DTO.FlatRequestDTO;
import com.example.Society.DTO.FlatResponseDTO;
import com.example.Society.Service.FlatService;
import jakarta.validation.Valid;
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
    @PostMapping("/wing/{wingId}")
    public FlatResponseDTO createFlat(@PathVariable Long wingId, @Valid @RequestBody FlatRequestDTO flatRequestDTO) {
        return flatService.createFlat(wingId,flatRequestDTO);
    }

    //Get all flats
    @GetMapping
    public List<FlatResponseDTO> getAllFlats() {
        return flatService.getAllFlats();
    }

    //Get flat by id
    @GetMapping("/{flatId}")
    public FlatResponseDTO getFlatById(@PathVariable Long flatId) {
        return flatService.getFlatById(flatId);
    }

    //Get flats by wing
    @GetMapping("/wing/{wingId}")
    public List<FlatResponseDTO> getFlatsByWingId(@PathVariable Long wingId) {
        return flatService.getFlatsByWing(wingId);
    }

    //Delete by ID
    @DeleteMapping("/{flatId}")
    public void deleteFlatById(@PathVariable Long flatId) {
        flatService.deleteFlat(flatId);
    }

    //Update flat by ID
    @PutMapping("/{flatId}")
    public FlatResponseDTO updateFlat(@PathVariable Long flatId, @Valid @RequestBody FlatRequestDTO flatRequestDTO) {
        return flatService.updateFlatInfo(flatId,flatRequestDTO);
    }

}
