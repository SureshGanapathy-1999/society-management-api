package com.example.Society.Service;

import com.example.Society.Model.Society;
import com.example.Society.Repository.SocietyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SocietyService {

    private final SocietyRepository repository;

    public SocietyService(SocietyRepository repository) {
        this.repository = repository;
    }

    //Create

    public Society create(Society society) {
        return repository.save(society);
    }

    // Get All
    public List<Society> getAll() {
        return repository.findAll();
    }

    // Get by ID
    public Society getById(long id) {
        return repository.getById(id);
    }

    // Update
    public Society update(long id, Society updated) {
        Society society = repository.getById(id);

        society.setSocietyName(updated.getSocietyName());
        society.setCity(updated.getCity());
        society.setAddress(updated.getAddress());
        society.setState(updated.getState());
        society.setPinCode(updated.getPinCode());

        return repository.save(society);
    }

    //Delete
    public void delete(long id){
        repository.deleteById(id);
    }
}
