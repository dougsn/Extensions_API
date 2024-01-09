package com.extensions.services;

import com.extensions.domain.dto.funcionario.FuncionarioDTO;
import com.extensions.domain.dto.funcionario.FuncionarioDTOMapper;
import com.extensions.domain.entity.Funcionario;
import com.extensions.domain.entity.Setor;
import com.extensions.repository.IFuncionarioRepository;
import com.extensions.repository.ISetorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FuncionarioService {
    @Autowired
    private FuncionarioDTOMapper mapper;
    @Autowired
    private IFuncionarioRepository repository;
    @Autowired
    private ISetorRepository setorRepository;


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

        setorToFuncionario = setorRepository.findById(funcionarioDTO.setor().getId());

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

        setorToFuncionario = setorRepository.findById(funcionarioDTO.setor().getId());

        if (funcionarioDTO == null || setorRepository.findByNome(funcionarioDTO.name()).isPresent()) {
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
