package com.konex.project.main.transfers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DrogueriaDTO implements Serializable {
    private Long identificador;
    private String nombre;
}
