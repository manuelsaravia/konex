package com.konex.project.main.implementatios;

import com.konex.project.main.entities.Drogueria;
import com.konex.project.main.mappers.DrogueriaMapper;
import com.konex.project.main.repositories.DrogueriaRepository;
import com.konex.project.main.services.DrogueriaService;
import com.konex.project.main.transfers.DrogueriaDTO;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DrogueriaServiceImpl implements DrogueriaService {
    @Autowired
    DrogueriaRepository repository;

    @Override
    public DrogueriaDTO getById(int id) {
        try{
            Optional<Drogueria> o = repository.findById((long) id);
            if(!o.isPresent())
                throw new RuntimeException("Empty");
            return DrogueriaMapper.entityToDto(o.get());
        } catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<DrogueriaDTO> getAll() {
        try{
            List<Drogueria> all = repository.findAll();
            if(all == null || all.isEmpty())
                throw new RuntimeException("Empty");
            return all.stream().map(entity -> DrogueriaMapper.entityToDto(entity)).collect(Collectors.toList());
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public DrogueriaDTO save(DrogueriaDTO dto) {
        try{
            Drogueria saved = repository.save(DrogueriaMapper.dtoToEntity(dto));
            if (saved == null)
                throw new RuntimeException("Unsaved");
            return DrogueriaMapper.entityToDto(saved);
        }
        catch (DataIntegrityViolationException v){
            throw new RuntimeException("Double");
        }
        catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
