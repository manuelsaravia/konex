package com.konex.project.main.repositories;

import com.konex.project.main.entities.Medicamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicamentoRepository extends JpaRepository<Medicamento, Long> {

    @Query("SELECT m FROM Medicamento m WHERE m.drogueria.identificador=:identificador")
    List<Medicamento> findByDrogueria(@Param("identificador") Long identificador);
}
