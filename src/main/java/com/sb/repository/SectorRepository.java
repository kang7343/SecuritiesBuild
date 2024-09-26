package com.sb.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sb.entity.Sector;

@Mapper
public interface SectorRepository {

    public List<Sector> selectAll();
}
