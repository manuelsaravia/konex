package com.konex.project.main.repositories;

import com.konex.project.main.entities.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {

    @Query("SELECT v FROM Venta v WHERE v.factura.fecha between :ini and :fin")
    List<Venta> findBetweenDates(@Param("ini") Date ini, @Param("fin") Date fin);
}
