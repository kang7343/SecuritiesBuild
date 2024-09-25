package com.kis.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kis.entity.Sector;

@Mapper
public interface SectorRepository {

    public List<Sector> selectAll();
}
