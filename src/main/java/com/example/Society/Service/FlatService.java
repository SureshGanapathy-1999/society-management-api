package com.example.Society.Service;

import com.example.Society.DTO.FlatRequestDTO;
import com.example.Society.DTO.FlatResponseDTO;
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

    public FlatResponseDTO createFlat(Long wingId, FlatRequestDTO flatRequestDTO) {
        Wing wing = wingRepository.findById(wingId).orElseThrow(() -> new RuntimeException("Wing not found"));

        Flat flat = new Flat();

        flat.setFlatNumber(flatRequestDTO.getFlatNumber());
        flat.setFlatSqft(flatRequestDTO.getFlatSqft());
        flat.setMaintenanceCost(flatRequestDTO.getMaintenanceCost());
        flat.setWing(wing);

        Flat savedFlat = repository.save(flat);

        return convertToFlatResponseDTO(savedFlat);
    }

    // Get all flats
    public List<FlatResponseDTO> getAllFlats(){
        return repository.findAll().stream().map(this::convertToFlatResponseDTO).toList();
    }

    // get Flat by ID
    public FlatResponseDTO getFlatById(Long flatId) {
        Flat flat = repository.findById(flatId).orElseThrow(() -> new RuntimeException("Flat not found"));

        return convertToFlatResponseDTO(flat);
    }


    public List<FlatResponseDTO> getFlatsByWing(Long wingId) {
        return repository.findByWingId(wingId).stream().map(this::convertToFlatResponseDTO).toList();

    }

    public String deleteFlat(Long flatId) {
        Flat flat = repository.findById(flatId).orElseThrow(() -> new RuntimeException("Flat not found"));

        repository.deleteById(flatId);
        return "Flat Deleted Successfully";
    }

    public FlatResponseDTO updateFlatInfo(Long flatId, FlatRequestDTO updatedFlat) {
        Flat flat = repository.findById(flatId).orElseThrow(() -> new RuntimeException("Flat not found"));

        flat.setFlatNumber(updatedFlat.getFlatNumber());
        flat.setFlatSqft(updatedFlat.getFlatSqft());
        flat.setMaintenanceCost(updatedFlat.getMaintenanceCost());

        Flat updated =  repository.save(flat);

        return convertToFlatResponseDTO(updated);
    }

    // Response DTO mapped class

    private FlatResponseDTO convertToFlatResponseDTO(Flat flat) {
        FlatResponseDTO responseDTO = new FlatResponseDTO();

        responseDTO.setId(flat.getId());
        responseDTO.setFlatNumber(flat.getFlatNumber());
        responseDTO.setFlatSqft(flat.getFlatSqft());
        responseDTO.setMaintenanceCost(flat.getMaintenanceCost());
        responseDTO.setWingId(flat.getWing().getId());
        responseDTO.setWingName(flat.getWing().getWingName());

        return responseDTO;
    }
}
