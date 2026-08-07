package com.example.Society.Controller;

import com.example.Society.Model.Flat;
import com.example.Society.Service.FlatService;
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
    public Flat wing(@PathVariable Long wingId, @RequestBody Flat flat) {
        return flatService.createFlat(wingId,flat);
    }

    //Get All flats
    @GetMapping
    public List<Flat> getAllFlats() {
        return flatService.getAllFlats();
    }

    //get flat by id
    @GetMapping("/{flatId}")
    public Flat getFlatById(@PathVariable Long flatId) {
        return flatService.getFlatById(flatId);
    }

    //Get Flats By Wing
    @GetMapping("/wing/{wingId}")
    public List<Flat> getFlatsByWingId(@PathVariable Long wingId) {
        return flatService.getFlatsByWing(wingId);
    }

    //Delete By ID
    @DeleteMapping("/{flatId}")
    public void deleteFlatById(@PathVariable Long flatId) {
        flatService.deleteFlat(flatId);
    }

    //Update Flat by ID
    @PutMapping("/{flatId}")
    public Flat updateFlat(@PathVariable Long flatId, @RequestBody Flat flat) {
        return flatService.updateFlatInfo(flatId,flat);
    }

}
