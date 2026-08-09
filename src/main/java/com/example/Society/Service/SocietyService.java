package com.example.Society.Service;

import com.example.Society.DTO.SocietyRequestDTO;
import com.example.Society.DTO.SocietyResponseDTO;
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

    // Converts SocietyRequestDTO into Society entity
    // This avoids repeating the same mapping logic in multiple methods
    private Society mapToEntity(SocietyRequestDTO requestDTO) {

        Society society = new Society();

        society.setSocietyCode(requestDTO.getSocietyCode());
        society.setSocietyName(requestDTO.getSocietyName());
        society.setCity(requestDTO.getCity());
        society.setAddress(requestDTO.getAddress());
        society.setState(requestDTO.getState());
        society.setPinCode(requestDTO.getPinCode());

        return society;
    }

    // Converts Society entity into SocietyResponseDTO
    // This avoids repeating the same mapping logic in multiple methods
    private SocietyResponseDTO mapToResponseDTO(Society society) {

        SocietyResponseDTO responseDTO = new SocietyResponseDTO();

        responseDTO.setId(society.getId());
        responseDTO.setSocietyCode(society.getSocietyCode());
        responseDTO.setSocietyName(society.getSocietyName());
        responseDTO.setCity(society.getCity());
        responseDTO.setAddress(society.getAddress());
        responseDTO.setState(society.getState());
        responseDTO.setPinCode(society.getPinCode());

        return responseDTO;
    }

    //Create

    public SocietyResponseDTO create(SocietyRequestDTO societyRequestDTO) {

        // Create a new Society entity
        Society society = mapToEntity(societyRequestDTO);

        // Save the Society entity into the database
        // The database generates the Society ID
        Society savedSociety = repository.save(society);

        // Return the Response DTO to the Controller
        return mapToResponseDTO(savedSociety);
    }

    // Get All

    // Retrieve all Society entities from the database
    public List<SocietyResponseDTO> getAll() {
        return repository.findAll().stream()

                // Convert each Society entity into a SocietyResponseDTO
                .map(society -> {   return mapToResponseDTO(society);
        })
                // Convert the Stream into a List of Response DTOs
                .toList();
    }

    // Get by ID
    public SocietyResponseDTO findById(long id) {
        Society  society = repository.findById(id).orElseThrow(()-> new RuntimeException("Society Not Found"));

        return mapToResponseDTO(society);
    }

    // Update

    // Find the existing Society using the provided ID
    // If it does not exist, throw an exception
    public SocietyResponseDTO update(long id, SocietyRequestDTO updated) {
        Society society = repository.findById(id).orElseThrow(() -> new RuntimeException("Society Not Found"));

        // Update the editable Society details
        society.setSocietyName(updated.getSocietyName());
        society.setCity(updated.getCity());
        society.setAddress(updated.getAddress());
        society.setState(updated.getState());
        society.setPinCode(updated.getPinCode());

        // Society Code is intentionally not updated
        // because it is the unique business identifier

        // Save the updated Society entity into the database
        Society updatedSociety = repository.save(society);

        return mapToResponseDTO(updatedSociety);
    }

    //Delete

    // Find the Society before deleting it
    // This ensures we return an error if the Society does not exist
    public void delete(long id){
        Society society = repository.findById(id).orElseThrow(() -> new RuntimeException("Society Not Found"));

        // Delete the Society from the database
        repository.delete(society);
    }
}
