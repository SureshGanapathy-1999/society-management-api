package com.example.Society.Service;

import com.example.Society.Model.Society;
import com.example.Society.Model.Wing;
import com.example.Society.Repository.SocietyRepository;
import com.example.Society.Repository.WingRepository;
import jakarta.persistence.Access;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WingService {

    private final SocietyRepository  societyRepository;
    private final WingRepository repository;

    public WingService(SocietyRepository societyRepository,WingRepository repository){
        this.societyRepository = societyRepository;
        this.repository = repository;
    }

    //Add wing to Society
    public Wing addWingToSociety(Long societyId ,Wing wing){
        Society society = societyRepository.findById(societyId).orElseThrow(() -> new RuntimeException("society not found"));
        wing.setSociety(society);
        return repository.save(wing);
    }

    //Create
    public Wing create(Wing wing) {
        return repository.save(wing);
    }

    //Get All Wings
    public List<Wing> getAll() {
        return repository.findAll();
    }

    //Get By ID
    public Wing getById(Long id) {
        return repository.findById(id).orElseThrow( () -> new RuntimeException("Wing not found") );
    }

    //Delete
    public void delete(Long id){
        repository.deleteById(id);
    }


}
