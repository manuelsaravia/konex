package com.konex.project.main.services;

import com.konex.project.main.entities.Drogueria;
import com.konex.project.main.transfers.DrogueriaDTO;

import java.util.List;

public interface DrogueriaService {
    DrogueriaDTO getById(int id);
    List<DrogueriaDTO> getAll();
    DrogueriaDTO save(DrogueriaDTO dto);
}
