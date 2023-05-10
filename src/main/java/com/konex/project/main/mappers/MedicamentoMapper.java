package com.konex.project.main.mappers;

import com.konex.project.main.entities.Medicamento;
import com.konex.project.main.transfers.MedicamentoDTO;

public final class MedicamentoMapper {

    public static MedicamentoDTO entityToDto(Medicamento entity){
        MedicamentoDTO dto = new MedicamentoDTO();
        dto.setCodigo(entity.getCodigo());
        dto.setNombre(entity.getNombre());
        dto.setLaboratorio(entity.getLaboratorio());
        dto.setPrecio(entity.getPrecio());
        dto.setCantidad(entity.getCantidad());
        dto.setFechaFab(entity.getFechaFab());
        dto.setFechaVen(entity.getFechaVen());
        return dto;
    }

    public static Medicamento dtoToEntity(MedicamentoDTO dto){
        Medicamento entity = new Medicamento();
        entity.setCodigo(dto.getCodigo());
        entity.setNombre(dto.getNombre());
        entity.setLaboratorio(dto.getLaboratorio());
        entity.setPrecio(dto.getPrecio());
        entity.setCantidad(dto.getCantidad());
        entity.setFechaFab(dto.getFechaFab());
        entity.setFechaVen(dto.getFechaVen());
        return entity;
    }
}
