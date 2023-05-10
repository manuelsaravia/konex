package com.konex.project.main.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "medicamentos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Medicamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    @Column(unique = true)
    private String nombre;

    @Column
    private String laboratorio;

    @Column
    private int cantidad;

    @Column(name="fechaFab")
    private Date fechaFab;

    @Column(name="fechaVen")
    private Date fechaVen;

    @Column
    private double precio;

    @ManyToOne
    private Drogueria drogueria;
}
