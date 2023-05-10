package com.konex.project.main.transfers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompraDTO implements Serializable {
    private int idDrogueria;
    private String identificacionCliente;
    private List<DetalleCompraDTO> detalle;
}
