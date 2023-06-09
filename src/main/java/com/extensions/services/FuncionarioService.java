package com.extensions.services;

import com.extensions.DTO.funcionario.FuncionarioDTO;
import com.extensions.DTO.funcionario.FuncionarioDTOMapper;
import com.extensions.entity.Funcionario;
import com.extensions.entity.Setor;
import com.extensions.repository.IFuncionarioRepository;
import com.extensions.repository.ISetorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FuncionarioService {
    private final FuncionarioDTOMapper mapper;
    private final IFuncionarioRepository repository;
    private final ISetorRepository setorRepository;


    public List<FuncionarioDTO> findAll() {
        List<FuncionarioDTO> funcionarioDTO;
        funcionarioDTO = repository.findAll().stream().map(mapper).toList();
        return funcionarioDTO;
    }

    public List<FuncionarioDTO> findFuncionarioBySetor(String setor) {
        List<FuncionarioDTO> funcionarioDTO;
        funcionarioDTO = repository.findBySetor(setor).stream().map(mapper).toList();
        return funcionarioDTO;
    }


    public Optional<FuncionarioDTO> findById(Long id) {
        Optional<FuncionarioDTO> funcionarioDTO;
        funcionarioDTO = repository.findById(id).map(mapper);
        if (funcionarioDTO.isPresent()) {
            return funcionarioDTO;
        } else {
            return Optional.of(null);
        }
    }

    public Optional<FuncionarioDTO> add(FuncionarioDTO funcionarioDTO) {

        Optional<Setor> setorToFuncionario;

        setorToFuncionario = setorRepository.findById(funcionarioDTO.setor().id());

        if (funcionarioDTO == null || repository.findByName(funcionarioDTO.name()).isPresent()) {
            return Optional.of(null);
        } else {
            Funcionario funcionario = repository.saveAndFlush(
                    new Funcionario(
                            null,
                            funcionarioDTO.name(),
                            funcionarioDTO.ramal(),
                            funcionarioDTO.email(),
                            setorToFuncionario.get()
                    )
            );
            return Optional.of(mapper.apply(funcionario));
        }
    }

    public Optional<FuncionarioDTO> update(FuncionarioDTO funcionarioDTO) {

        Optional<Setor> setorToFuncionario;

        setorToFuncionario = setorRepository.findById(funcionarioDTO.setor().id());

        if (funcionarioDTO == null || setorRepository.findByName(funcionarioDTO.name()).isPresent()) {
            return Optional.of(null);
        } else {
            Funcionario funcionarioUpdate = new Funcionario(
                    funcionarioDTO.id(),
                    funcionarioDTO.name(),
                    funcionarioDTO.ramal(),
                    funcionarioDTO.email(),
                    setorToFuncionario.get()
            );
            return Optional.of(mapper.apply(repository.saveAndFlush(funcionarioUpdate)));
        }
    }

    public Boolean hardDelete(Long id) {
        if (repository.findById(id).isPresent()) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

}
