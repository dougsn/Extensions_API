package com.extensions.domain.dto.wifi;

import com.extensions.domain.entity.Wifi;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Service
public class WifiDTOMapperList implements Function<List<Wifi>, List<WifiDTO>> {


    @Override
    public List<WifiDTO> apply(List<Wifi> wifis) {
        List<WifiDTO> dtoList = new ArrayList<>();
        wifis.forEach(wifi -> {
            WifiDTO dto = new WifiDTO(wifi.getId(), wifi.getIp(), wifi.getUsuario(), wifi.getSenhaBrowser(), wifi.getSsid(),
                    wifi.getSenhaWifi(), wifi.getSetor().getId(), wifi.getSetor().getNome());
            dtoList.add(dto);
        });
        return dtoList;

    }
}
