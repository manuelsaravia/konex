package com.konex.project.main.services;


import com.konex.project.main.transfers.DrogueriaDTO;
import com.konex.project.main.transfers.MedicamentoDTO;
import com.konex.project.main.transfers.MedicamentoDrogueriaDTO;

import java.util.List;

public interface MedicamentoService {
    MedicamentoDTO getById(int id);
    List<MedicamentoDrogueriaDTO> getAll();
    MedicamentoDrogueriaDTO getByDrogueria(int idDrogueria);
    MedicamentoDTO save(MedicamentoDTO dtoMed, int idDrogueria);
    MedicamentoDTO delete(int id);
}
