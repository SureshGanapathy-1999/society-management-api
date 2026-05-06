package com.example.Society.Controller;

import com.example.Society.Model.Wing;
import com.example.Society.Service.WingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wing")
public class WingController {

    private final WingService service;

    public WingController(WingService service) {
        this.service = service;
    }

    //Add wing for society
    @PostMapping("/society/{SocietyId}")
    public Wing addWingToSociety(@PathVariable Long SocietyId, @RequestBody Wing wing){
        return service.addWingToSociety(SocietyId,wing);
    }

    //create
    @PostMapping
    public Wing create(@RequestBody Wing wing) {
        return service.create(wing);
    }

    //Get All
    @GetMapping
    public List<Wing> getAll() {
        return service.getAll();
    }

    //Get By ID
    @GetMapping("/{id}")
    public Wing getById(@PathVariable long id){
        return service.getById(id);
    }

    //Delete Wing
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable long id){
        service.delete(id);
    }
}
