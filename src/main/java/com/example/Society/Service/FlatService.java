package com.example.Society.Service;

import com.example.Society.Model.Flat;
import com.example.Society.Model.Wing;
import com.example.Society.Repository.FlatRepository;
import com.example.Society.Repository.WingRepository;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class FlatService {

    private final FlatRepository repository;
    private final WingRepository wingRepository;

    public FlatService(FlatRepository repository, WingRepository wingRepository) {
        this.repository = repository;
        this.wingRepository = wingRepository;
    }

    //create flat inside wing

    public Flat createFlat(Long wingId, Flat flat) {
        Wing wing = wingRepository.findById(wingId).orElseThrow(() -> new RuntimeException("Wing not found"));
        flat.setWing(wing);

        return repository.save(flat);
    }

    // get All flats
    public List<Flat> getAllFlats(){
        return repository.findAll();
    }

    // get Flat by ID
    public Flat getFlatById(Long flatId) {
        return repository.findById(flatId).orElseThrow(() -> new RuntimeException("Flat not found"));
    }


    public List<Flat> getFlatsByWing(Long wingId) {
        return repository.findByWingId(wingId);
    }

    public String deleteFlat(Long flatId) {
        Flat flat = repository.findById(flatId).orElseThrow(() -> new RuntimeException("Flat not found"));

        repository.deleteById(flatId);
        return "Flat Deleted SuccessFully";
    }

    public Flat updateFlatInfo(Long flatId, Flat updatedFlat) {
        Flat flat = repository.findById(flatId).orElseThrow(() -> new RuntimeException("Flat not found"));

        flat.setFlatNumber(updatedFlat.getFlatNumber());
        flat.setFlatSqft(updatedFlat.getFlatSqft());
        flat.setMaintenanceCost(updatedFlat.getMaintenanceCost());

        return repository.save(flat);
    }
}
