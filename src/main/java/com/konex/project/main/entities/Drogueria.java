package com.konex.project.main.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name ="droguerias")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Drogueria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long identificador;

    @Column(name = "nombre", unique = true)
    private String nombre;
}
