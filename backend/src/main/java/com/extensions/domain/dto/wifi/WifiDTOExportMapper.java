package com.extensions.domain.dto.wifi;

import com.extensions.domain.entity.Wifi;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class WifiDTOExportMapper implements Function<Wifi, WifiDTOExport> {


    @Override
    public WifiDTOExport apply(Wifi wifi) {
        return new WifiDTOExport(
                wifi.getSetor().getNome(),
                wifi.getIp(),
                wifi.getUsuario(),
                wifi.getSenhaBrowser(),
                wifi.getSsid(),
                wifi.getSenhaWifi()
        );

    }
}
