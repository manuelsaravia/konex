package com.konex.project.main.transfers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FacturaDTO implements Serializable {
    private Long consecutivo;
    private Date fecha;
    private String cliente;
    private List<VentaDTO> detalle;
    private double total;
}
