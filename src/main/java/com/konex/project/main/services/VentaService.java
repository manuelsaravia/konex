package com.konex.project.main.services;

import com.konex.project.main.transfers.CompraDTO;
import com.konex.project.main.transfers.DetalleCompraDTO;
import com.konex.project.main.transfers.FacturaDTO;

import java.util.List;

public interface VentaService {
    FacturaDTO vender(CompraDTO dto);
    FacturaDTO getById(int id);
    List<FacturaDTO> getAll();
    List<FacturaDTO> getByFechas(String fechaIni, String fechaFin);
}
