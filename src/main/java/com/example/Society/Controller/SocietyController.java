package com.example.Society.Controller;

import com.example.Society.Model.Society;
import com.example.Society.Service.SocietyService;
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
    public Society create(@RequestBody Society society) {
        return service.create(society);
    }

    //GetALL Societies
    @GetMapping
    public List<Society> getAll() {
        return service.getAll();
    }

    //GetBy ID
    @GetMapping("/{id}")
    public Society getById(@PathVariable long id){
        return service.getById(id);
    }

    //Update
    @PutMapping("/{id}")
    public Society update(@PathVariable long id, @RequestBody Society society) {
        return service.update(id,society);
    }

    //Delete

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        service.delete(id);
    }

}
