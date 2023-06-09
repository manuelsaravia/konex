package com.konex.project.main.transfers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VentaDTO implements Serializable {
    private Long identificador;
    private Long codigoMedicamento;
    private String nombreMedicamento;
    private int cantidad;
    private double valorUnitario;
    private double valorTotal;
}
