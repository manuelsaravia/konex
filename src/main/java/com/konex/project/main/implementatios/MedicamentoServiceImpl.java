package com.konex.project.main.implementatios;

import com.konex.project.main.entities.Drogueria;
import com.konex.project.main.entities.Medicamento;
import com.konex.project.main.mappers.DrogueriaMapper;
import com.konex.project.main.mappers.MedicamentoMapper;
import com.konex.project.main.repositories.MedicamentoRepository;
import com.konex.project.main.services.DrogueriaService;
import com.konex.project.main.services.MedicamentoService;
import com.konex.project.main.transfers.DrogueriaDTO;
import com.konex.project.main.transfers.MedicamentoDTO;
import com.konex.project.main.transfers.MedicamentoDrogueriaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MedicamentoServiceImpl implements MedicamentoService {

    @Autowired
    MedicamentoRepository repository;

    @Autowired
    DrogueriaService drogueriaService;

    @Override
    public MedicamentoDTO getById(int id) {
        try{
            Optional<Medicamento> o = repository.findById((long) id);
            if(!o.isPresent())
                throw new RuntimeException("Empty");
            return MedicamentoMapper.entityToDto(o.get());
        } catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<MedicamentoDrogueriaDTO> getAll() {
        try{
            List<Medicamento> all = repository.findAll();
            if(all == null || all.isEmpty())
                throw new RuntimeException("Empty");
            Map<Long, MedicamentoDrogueriaDTO> map = new HashMap<>();
            for(Medicamento entity: all){
                if(!map.containsKey(entity.getDrogueria().getIdentificador())){
                    MedicamentoDrogueriaDTO dto = new MedicamentoDrogueriaDTO();
                    dto.setDrogueria(DrogueriaMapper.entityToDto(entity.getDrogueria()));
                    dto.setMedicamentos(new ArrayList<>());
                    map.put(entity.getDrogueria().getIdentificador(), dto);
                }
                map.get(entity.getDrogueria().getIdentificador()).getMedicamentos().add(MedicamentoMapper.entityToDto(entity));
            }

            return map.values().stream().map(dto -> {return dto;}).collect(Collectors.toList());
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public MedicamentoDrogueriaDTO getByDrogueria(int idDrogueria) {
        try{
            DrogueriaDTO drogueria = null;
            try {
                drogueria = drogueriaService.getById(idDrogueria);
            }catch (Exception e){
                switch (e.getMessage()){
                    case "Empty":
                        throw new RuntimeException("Empty Drogueria");
                    case "Failed":
                        throw new RuntimeException("Failed Drogueria");
                    default:
                        throw new RuntimeException("Default Drogueria");
                }
            }

            List<Medicamento> all = repository.findByDrogueria(drogueria.getIdentificador());
            if(all == null || all.isEmpty())
                throw new RuntimeException("Empty");

            MedicamentoDrogueriaDTO dto = new MedicamentoDrogueriaDTO();
            dto.setDrogueria(DrogueriaMapper.entityToDto(all.get(0).getDrogueria()));
            dto.setMedicamentos(new ArrayList<>());
            dto.getMedicamentos().addAll(all.stream().map(entity -> MedicamentoMapper.entityToDto(entity)).collect(Collectors.toList()));

            return dto;
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public MedicamentoDTO save(MedicamentoDTO dtoMed, int idDrogueria) {
        try{
            DrogueriaDTO drogueria = null;
            try {
                drogueria = drogueriaService.getById(idDrogueria);
            }catch (Exception e){
                switch (e.getMessage()){
                    case "Empty":
                        throw new RuntimeException("Empty Drogueria");
                    case "Failed":
                        throw new RuntimeException("Failed Drogueria");
                    default:
                        throw new RuntimeException("Default Drogueria");
                }
            }

            Medicamento toSave = MedicamentoMapper.dtoToEntity(dtoMed);
            toSave.setDrogueria(DrogueriaMapper.dtoToEntity(drogueria));


            Medicamento saved = repository.save(toSave);
            if (saved == null)
                throw new RuntimeException("Unsaved");
            return MedicamentoMapper.entityToDto(saved);
        }
        catch (DataIntegrityViolationException v){
            throw new RuntimeException("Double");
        }
        catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public MedicamentoDTO delete(int id) {
        try{
            Optional<Medicamento> o = repository.findById((long) id);
            if(!o.isPresent())
                throw new RuntimeException("Empty");
            repository.delete(o.get());
            return MedicamentoMapper.entityToDto(o.get());
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
