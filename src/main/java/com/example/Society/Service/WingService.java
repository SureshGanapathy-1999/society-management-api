package com.example.Society.Service;

import com.example.Society.DTO.WingRequestDTO;
import com.example.Society.DTO.WingResponseDTO;
import com.example.Society.Model.Society;
import com.example.Society.Model.Wing;
import com.example.Society.Repository.SocietyRepository;
import com.example.Society.Repository.WingRepository;
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

    //Create wing
    public WingResponseDTO addWingToSociety(Long societyId , WingRequestDTO wingRequestDTO){

        Society society = societyRepository.findById(societyId).orElseThrow(() -> new RuntimeException("society not found"));

        Wing newWing = new Wing();

        newWing.setWingName(wingRequestDTO.getWingName());
        newWing.setSociety(society);

        Wing savedWing = repository.save(newWing);

        return convertTOResponseDTO(savedWing);
    }


    //Get All Wings
    public List<WingResponseDTO> getAll() {
        return repository.findAll().stream().map(this::convertTOResponseDTO).toList();
    }

    //Get By ID
    public WingResponseDTO getById(Long id) {

        Wing wing = repository.findById(id).orElseThrow( () -> new RuntimeException("Wing not found") );

        return convertTOResponseDTO(wing);
    }

    //Delete
    public void delete(Long id) {
        Wing wing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Wing not found"));

        repository.delete(wing);
    }

    // Converting Entity into Response DTO
    private WingResponseDTO convertTOResponseDTO(Wing wing){

        WingResponseDTO responseDTO = new WingResponseDTO();

        responseDTO.setId(wing.getId());
        responseDTO.setWingName(wing.getWingName());
        responseDTO.setSocietyId(wing.getSociety().getId());
        responseDTO.setSocietyName(wing.getSociety().getSocietyName());

        return responseDTO;
    }

}
