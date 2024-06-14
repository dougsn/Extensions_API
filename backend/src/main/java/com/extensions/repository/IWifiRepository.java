package com.extensions.repository;

import com.extensions.domain.entity.Wifi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface IWifiRepository extends JpaRepository<Wifi, String> {
    @Transactional(readOnly = true)
    List<Wifi> findBySetorId(String idSetor);

    @Transactional(readOnly = true)
    Optional<Wifi> findByIp(String ip);

    @Transactional(readOnly = true)
    Optional<Wifi> findBySsid(String ssid);

    @Transactional(readOnly = true)
    List<Wifi> findByIpLike(String ip);

    @Transactional(readOnly = true)
    List<Wifi> findBySsidLike(String ssid);
}

