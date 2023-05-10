package com.konex.project.main.repositories;

import com.konex.project.main.entities.Drogueria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DrogueriaRepository extends JpaRepository<Drogueria, Long> {
}
