package com.sb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sb.entity.Sector;
import com.sb.repository.SectorRepository;

@Service
@Transactional
public class SectorServiceImpl implements SectorService {

    @Autowired
    SectorRepository sectorRepository;

    @Override
    public List<Sector> selectAll() {
        return sectorRepository.selectAll();
    }

}
