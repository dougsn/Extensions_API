package com.extensions.services;

import com.extensions.DTO.setor.SetorDTO;
import com.extensions.DTO.setor.SetorDTOMapper;
import com.extensions.entity.Setor;
import com.extensions.repository.ISetorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SetorService{

    private final SetorDTOMapper setorDTOMapper;
    private final ISetorRepository setorRepository;

    public List<SetorDTO> findAll() {
        List<SetorDTO> setorDTO;
        setorDTO = setorRepository.findAll().stream().map(setorDTOMapper).toList();
        return setorDTO;
    }


    public Optional<SetorDTO> findById(Long id) {
        Optional<SetorDTO> setorDTO;
        setorDTO = setorRepository.findById(id).map(setorDTOMapper);
        if (setorDTO.isPresent()) {
            return setorDTO;
        } else {
            return Optional.of(null);
        }
    }

    public Optional<SetorDTO> add(SetorDTO setorDTO) {
        if (setorDTO == null || setorRepository.findByName(setorDTO.name()).isPresent()) {
            return Optional.of(null);
        } else {
            Setor setor = setorRepository.saveAndFlush(
                    new Setor(
                            null,
                            setorDTO.name()
                    )
            );
            return Optional.of(setorDTOMapper.apply(setor));
        }
    }

    public Optional<SetorDTO> update(SetorDTO setorDTO) {
        if (setorDTO == null || setorRepository.findByName(setorDTO.name()).isPresent()) {
            return Optional.of(null);
        } else {
            Setor setorUpdate = new Setor(
                    setorDTO.id(),
                    setorDTO.name()
            );
            return Optional.of(setorDTOMapper.apply(setorRepository.saveAndFlush(setorUpdate)));
        }
    }

    public Boolean hardDelete(Long id) {
        if (setorRepository.findById(id).isPresent()) {
            setorRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
