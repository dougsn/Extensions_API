package com.extensions.domain.dto.computador;

import com.extensions.domain.entity.Computador;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Service
public class ComputadorDTOMapperList implements Function<List<Computador>, List<ComputadorDTO>> {


    @Override
    public List<ComputadorDTO> apply(List<Computador> computadors) {
        List<ComputadorDTO> dtoList = new ArrayList<>();
        computadors.forEach(comp -> {
            ComputadorDTO dto = new ComputadorDTO(comp.getId(), comp.getHostname(), comp.getModelo(), comp.getCpu(),
                    comp.getMemoria(), comp.getDisco(), comp.getSistemaOperacional(), comp.getObservacao(),
                    comp.getSetor().getId(), comp.getSetor().getNome());
            dtoList.add(dto);
        });
        return dtoList;

    }
}
