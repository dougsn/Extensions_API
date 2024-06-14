package com.extensions.services;

import com.extensions.controller.WifiController;
import com.extensions.domain.dto.wifi.WifiDTO;
import com.extensions.domain.dto.wifi.WifiDTOMapper;
import com.extensions.domain.dto.wifi.WifiUpdateDTO;
import com.extensions.domain.entity.Setor;
import com.extensions.domain.entity.Wifi;
import com.extensions.repository.ISetorRepository;
import com.extensions.repository.IWifiRepository;
import com.extensions.services.exceptions.DataIntegratyViolationException;
import com.extensions.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class WifiService {
    private final Logger logger = Logger.getLogger(WifiService.class.getName());
    @Autowired
    private WifiDTOMapper mapper;
    @Autowired
    private IWifiRepository repository;
    @Autowired
    private ISetorRepository setorRepository;
    @Autowired
    PagedResourcesAssembler<WifiDTO> assembler;

    @Transactional(readOnly = true)
    public PagedModel<EntityModel<WifiDTO>> findAll(Pageable pageable) {
        logger.info("Buscando todos os wifi's!");
        var wifis = repository.findAll(pageable);

        var dtoList = wifis.map(f -> mapper.apply(f));
        dtoList.forEach(f -> f.add(linkTo(methodOn(WifiController.class).findById(f.getId())).withSelfRel()));

        Link link = linkTo(methodOn(WifiController.class)
                .findAll(pageable.getPageNumber(), pageable.getPageSize(), "asc")).withSelfRel();

        return assembler.toModel(dtoList, link);
    }

    @Transactional(readOnly = true)
    public List<WifiDTO> findWifiBySetor(String idSetor) {
        logger.info("Buscando wifi do setor de ID: " + idSetor);
        return repository.findBySetorId(idSetor).stream()
                .map(mapper)
                .collect(Collectors.toList());
    }

    public List<WifiDTO> findWifiBySsid(String ssid) {
        logger.info("Buscando wifi de ssid: " + ssid);
        return repository.findBySsidLike("%" + ssid + "%").stream()
                .map(mapper)
                .collect(Collectors.toList());
    }


    public WifiDTO findById(String id) {
        logger.info("Buscando wifi de id: " + id);
        var wifi = repository.findById(id)
                .map(mapper)
                .orElseThrow(() -> new ObjectNotFoundException("Wifi de id: " + id + " não encontrado"));
        wifi.add(linkTo(methodOn(WifiController.class).findById(id)).withSelfRel());

        return wifi;
    }

    public WifiDTO add(WifiDTO data) {
        logger.info("Adicionando um novo wifi.");
        checkingWifiWithTheSameName(data);
        Setor setor = setorRepository.findById(data.getIdSetor())
                .orElseThrow(() -> new ObjectNotFoundException("Setor de ID: " + data.getIdSetor() + " não encontrado."));

        Wifi newWifi = repository.save(new Wifi(null, data.getIp(), data.getUsuario(), data.getSenhaBrowser(), data.getSsid(),
                data.getSenhaWifi(), setor));

        return mapper.apply(newWifi)
                .add(linkTo(methodOn(WifiController.class).findById(newWifi.getId())).withSelfRel());
    }

    @Transactional
    public WifiDTO update(WifiUpdateDTO data) {
        logger.info("Atualizando wifi de id" + data.getId());
        checkingWifiWithTheSameNameDuringUpdate(data);

        Setor setor = setorRepository.findById(data.getIdSetor())
                .orElseThrow(() -> new ObjectNotFoundException("Setor de ID: " + data.getIdSetor() + " não encontrado."));

        Wifi updatedWifi = new Wifi(data.getId(), data.getIp(), data.getUsuario(), data.getSenhaBrowser(), data.getSsid(),
                data.getSenhaWifi(), setor);

        return mapper.apply(repository.save(updatedWifi))
                .add(linkTo(methodOn(WifiController.class).findById(data.getId())).withSelfRel());
    }

    @Transactional
    public Boolean delete(String id) {
        logger.info("Deletando wifi de id" + id);
        if (repository.findById(id).isPresent()) {
            repository.deleteById(id);
            return true;
        }
        throw new ObjectNotFoundException("Wifi de id: " + id + " não encontrado.");
    }

    @Transactional(readOnly = true)
    public void checkingWifiWithTheSameNameDuringUpdate(WifiUpdateDTO data) {
        var wifiByIp = repository.findByIp(data.getIp());
        var wifiBySsid = repository.findBySsid(data.getSsid());
        if (wifiByIp.isPresent() && !wifiByIp.get().getId().equals(data.getId())) {
            logger.info("O wifi com de ip: " + data.getIp() + " já existe!");
            throw new DataIntegratyViolationException("O wifi com de ip: " + data.getIp() + " já existe!");
        }
        if (wifiBySsid.isPresent() && !wifiBySsid.get().getId().equals(data.getId())) {
            logger.info("O wifi com de ssid: " + data.getSsid() + " já existe!");
            throw new DataIntegratyViolationException("O wifi com de ssid: " + data.getSsid() + " já existe!");
        }
    }

    @Transactional(readOnly = true)
    public void checkingWifiWithTheSameName(WifiDTO data) {
        if (repository.findByIp(data.getIp()).isPresent()) {
            logger.info("O wifi com de ip: " + data.getIp() + " já existe!");
            throw new DataIntegratyViolationException("O wifi com de ip: " + data.getIp() + " já existe!");
        }
        if (repository.findBySsid(data.getSsid()).isPresent()) {
            logger.info("O wifi com de ssid: " + data.getSsid() + " já existe!");
            throw new DataIntegratyViolationException("O wifi com de ssid: " + data.getSsid() + " já existe!");
        }

    }

}
