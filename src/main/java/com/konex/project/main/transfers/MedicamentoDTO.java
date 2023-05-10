package com.konex.project.main.transfers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicamentoDTO implements Serializable {

    private Long codigo;
    private String nombre;
    private String laboratorio;
    private Date fechaFab;
    private Date fechaVen;
    private int cantidad;
    private double precio;
}
