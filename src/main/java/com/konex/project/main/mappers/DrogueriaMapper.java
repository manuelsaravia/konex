package com.konex.project.main.mappers;

import com.konex.project.main.entities.Drogueria;
import com.konex.project.main.transfers.DrogueriaDTO;

public final class DrogueriaMapper {

    public static DrogueriaDTO entityToDto(Drogueria entity){
        DrogueriaDTO dto = new DrogueriaDTO();
        dto.setIdentificador(entity.getIdentificador());
        dto.setNombre(entity.getNombre());
        return dto;
    }

    public static Drogueria dtoToEntity(DrogueriaDTO dto){
        Drogueria entity = new Drogueria();
        entity.setIdentificador(dto.getIdentificador());
        entity.setNombre(dto.getNombre());
        return entity;
    }
}
