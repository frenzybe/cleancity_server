package ru.frenzybe.server.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.hibernate.PropertyValueException;
import org.springframework.stereotype.Service;
import ru.frenzybe.server.dto.urn.UrnCreateDTO;
import ru.frenzybe.server.entities.Urn;
import ru.frenzybe.server.repositories.UrnRepository;
import ru.frenzybe.server.repositories.UserRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UrnService {
    private final UrnRepository urnRepository;

    public Urn create(UrnCreateDTO urnCreateDTO) {
        return urnRepository.save(Urn.builder()
                .number(urnCreateDTO.getNumber())
                .location(urnCreateDTO.getLocation())
                .build());
    }

    public List<Urn> getAll() {
        return urnRepository.findAll();
    }

    public Urn getByNumber(Long number) {
        return urnRepository.findByNumber(number);
    }

    public Urn getById(Long id) {
        return urnRepository.findById(id).orElse(null);
    }

    public Urn updateUrnLocation(Long id, UrnCreateDTO urnDTO) {
        Urn urn = urnRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Урна не найдена"));
        urn.setLocation(urnDTO.getLocation());
        return urnRepository.save(urn);
    }


    public void delete(Long id) {
        if (!urnRepository.existsById(id)) {
            throw new EntityNotFoundException("Urn not found for id: " + id);
        }
        urnRepository.deleteById(id);
    }
}
