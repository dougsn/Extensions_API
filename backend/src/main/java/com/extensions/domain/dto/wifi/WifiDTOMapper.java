package com.extensions.domain.dto.wifi;

import com.extensions.domain.entity.Wifi;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class WifiDTOMapper implements Function<Wifi, WifiDTO> {


    @Override
    public WifiDTO apply(Wifi wifi) {
        return new WifiDTO(
                wifi.getId(),
                wifi.getIp(),
                wifi.getUsuario(),
                wifi.getSenhaBrowser(),
                wifi.getSsid(),
                wifi.getSenhaWifi(),
                wifi.getSetor().getId(),
                wifi.getSetor().getNome()
        );

    }
}
