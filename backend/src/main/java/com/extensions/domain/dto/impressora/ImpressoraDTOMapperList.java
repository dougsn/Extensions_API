package com.extensions.domain.dto.impressora;

import com.extensions.domain.entity.Impressora;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Service
public class ImpressoraDTOMapperList implements Function<List<Impressora>, List<ImpressoraDTO>> {


    @Override
    public List<ImpressoraDTO> apply(List<Impressora> impressoras) {
        List<ImpressoraDTO> dtoList = new ArrayList<>();
        impressoras.forEach(imp -> {
            ImpressoraDTO dto = new ImpressoraDTO(imp.getId(), imp.getMarca(), imp.getModelo(), imp.getIp(), imp.getTonner(),
                    imp.getObservacao(), imp.getSetor().getId(), imp.getSetor().getNome());
            dtoList.add(dto);
        });
        return dtoList;

    }
}
