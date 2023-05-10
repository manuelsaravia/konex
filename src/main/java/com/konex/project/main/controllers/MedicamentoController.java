package com.konex.project.main.controllers;

import com.konex.project.main.services.MedicamentoService;
import com.konex.project.main.transfers.DrogueriaDTO;
import com.konex.project.main.transfers.MedicamentoDTO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Api(tags="Controlador Medicamento")
@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping(path="api/medicamento")
public class MedicamentoController {

    @Autowired
    MedicamentoService service;

    @GetMapping()
    public ResponseEntity<?> getAll(){
        try{
            Map<String, Object> message = new HashMap<>();
            message.put("mensaje", "Consulta Exitosa");
            message.put("list", service.getAll());
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Exception e){
            Map<String, Object> message = new HashMap<>();
            message.put("list", new ArrayList<>());
            switch (e.getMessage()){
                case "Empty":
                    message.put("mensaje", "No se han encontrado medicamentos");
                    return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
                case "Failed":
                    message.put("mensaje", "Ha Ocurrido un error consultando los medicamentos");
                    return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
                default:
                    message.put("mensaje", "Ha ocurrido un error inesperado");
                    return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @GetMapping("drogueria/{idDrogueria}")
    public ResponseEntity<?> getAllByDrogueria(@PathVariable("idDrogueria") int idDrogueria){
        try{
            Map<String, Object> message = new HashMap<>();
            message.put("mensaje", "Consulta Exitosa");
            message.put("list", service.getByDrogueria(idDrogueria));
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Exception e){
            Map<String, Object> message = new HashMap<>();
            message.put("list", new ArrayList<>());
            switch (e.getMessage()){
                case "Empty Drogueria":
                    message.put("mensaje", "No se ha encontrado la drogueria");
                    return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
                case "Failed Drogueria":
                    message.put("mensaje", "Ha Ocurrido un error consultando la drogueria");
                    return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
                case "Default Drogueria":
                    message.put("mensaje", "Ha Ocurrido un error inesperado con la drogueria");
                    return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
                case "Empty":
                    message.put("mensaje", "No se han encontrado medicamentos");
                    return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
                case "Failed":
                    message.put("mensaje", "Ha Ocurrido un error consultando los medicamentos");
                    return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
                default:
                    message.put("mensaje", "Ha ocurrido un error inesperado");
                    return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable("id") int id){
        try{
            Map<String, Object> message = new HashMap<>();
            message.put("mensaje", "Consulta Exitosa");
            message.put("medicamento", service.getById(id));
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Exception e){
            Map<String, Object> message = new HashMap<>();
            message.put("medicamento", null);
            switch (e.getMessage()){
                case "Empty":
                    message.put("mensaje", "No se ha encontrado el medicamento");
                    return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
                case "Failed":
                    message.put("mensaje", "Ha Ocurrido un error consultando el medicamento");
                    return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
                default:
                    message.put("mensaje", "Ha ocurrido un error inesperado");
                    return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @PostMapping("drogueria/{idDrogueria}")
    public ResponseEntity<?> post(@RequestBody MedicamentoDTO dto,@PathVariable("idDrogueria") int idDrogueria){
        try{
            Map<String, Object> message = new HashMap<>();
            message.put("mensaje", "Creacion Exitosa");
            message.put("medicamento", service.save(dto,idDrogueria));
            return new ResponseEntity<>(message, HttpStatus.OK);
        }catch (Exception e){
            Map<String, Object> message = new HashMap<>();
            message.put("medicamento", null);
            switch (e.getMessage()){
                case "Empty Drogueria":
                    message.put("mensaje", "No se ha encontrado la drogueria");
                    return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
                case "Failed Drogueria":
                    message.put("mensaje", "Ha Ocurrido un error consultando la drogueria");
                    return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
                case "Default Drogueria":
                    message.put("mensaje", "Ha Ocurrido un error inesperado con la drogueria");
                    return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
                case "Double":
                    message.put("mensaje", "El Medicamento ya existe");
                    return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
                case "Unsaved":
                    message.put("mensaje", "No se ha podido guardar el Medicamento");
                    return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
                case "Failed":
                    message.put("mensaje", "Ha Ocurrido un error guardando el Medicamento");
                    return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
                default:
                    message.put("mensaje", "Ha ocurrido un error inesperado");
                    return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @PutMapping("{id}/drogueria/{idDrogueria}")
    public ResponseEntity<?> put(@RequestBody MedicamentoDTO dto, @PathVariable("id") int id,@PathVariable("idDrogueria") int idDrogueria){
        try{
            dto.setCodigo((long) id);
            Map<String, Object> message = new HashMap<>();
            message.put("mensaje", "Actualizacion Exitosa");
            message.put("medicamento", service.save(dto,idDrogueria));
            return new ResponseEntity<>(message, HttpStatus.OK);
        }catch (Exception e){
            Map<String, Object> message = new HashMap<>();
            message.put("medicamento", null);
            switch (e.getMessage()){
                case "Empty Drogueria":
                    message.put("mensaje", "No se ha encontrado la drogueria");
                    return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
                case "Failed Drogueria":
                    message.put("mensaje", "Ha Ocurrido un error consultando la drogueria");
                    return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
                case "Default Drogueria":
                    message.put("mensaje", "Ha Ocurrido un error inesperado con la drogueria");
                    return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
                case "Double":
                    message.put("mensaje", "El Medicamento ya existe");
                    return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
                case "Unsaved":
                    message.put("mensaje", "No se ha podido guardar el Medicamento");
                    return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
                case "Failed":
                    message.put("mensaje", "Ha Ocurrido un error guardando el Medicamento");
                    return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
                default:
                    message.put("mensaje", "Ha ocurrido un error inesperado");
                    return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id){
        try{
            Map<String, Object> message = new HashMap<>();
            message.put("mensaje", "Eliminacion Exitosa");
            message.put("medicamento", service.delete(id));
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Exception e){
            Map<String, Object> message = new HashMap<>();
            message.put("medicamento", null);
            switch (e.getMessage()){
                case "Empty":
                    message.put("mensaje", "No se ha encontrado el medicamento");
                    return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
                case "Failed":
                    message.put("mensaje", "Ha Ocurrido un error consultando el medicamento");
                    return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
                default:
                    message.put("mensaje", "Ha ocurrido un error inesperado");
                    return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }


}
