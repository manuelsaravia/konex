package com.konex.project.main.controllers;

import com.konex.project.main.services.DrogueriaService;
import com.konex.project.main.transfers.DrogueriaDTO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@Api(tags="Controlador Drogueria")
@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping(path="api/drogueria")
public class DrogueriaController {

    @Autowired
    DrogueriaService service;

    @GetMapping()
    public ResponseEntity<?> getAll(){
        try{
            Map<String, Object> message = new HashMap<>();
            message.put("mensaje", "Consulta Exitosa");
            message.put("droguerias", service.getAll());
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Exception e){
            Map<String, Object> message = new HashMap<>();
            message.put("droguerias", new ArrayList<>());
            switch (e.getMessage()){
                case "Empty":
                    message.put("mensaje", "No se han encontrado droguerias");
                    return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
                case "Failed":
                    message.put("mensaje", "Ha Ocurrido un error consultando las droguerias");
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
            message.put("drogueria", service.getById(id));
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Exception e){
            Map<String, Object> message = new HashMap<>();
            message.put("drogueria", null);
            switch (e.getMessage()){
                case "Empty":
                    message.put("mensaje", "No se ha encontrado la drogueria");
                    return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
                case "Failed":
                    message.put("mensaje", "Ha Ocurrido un error consultando la drogueria");
                    return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
                default:
                    message.put("mensaje", "Ha ocurrido un error inesperado");
                    return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @PostMapping()
    public ResponseEntity<?> post(@RequestBody DrogueriaDTO dto){
        try{
            Map<String, Object> message = new HashMap<>();
            message.put("mensaje", "Creacion Exitosa");
            message.put("drogueria", service.save(dto));
            return new ResponseEntity<>(message, HttpStatus.OK);
        }catch (Exception e){
            Map<String, Object> message = new HashMap<>();
            message.put("drogueria", null);
            switch (e.getMessage()){
                case "Double":
                    message.put("mensaje", "La drogueria ya existe");
                    return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
                case "Unsaved":
                    message.put("mensaje", "No se ha podido guardar la drogueria");
                    return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
                case "Failed":
                    message.put("mensaje", "Ha Ocurrido un error guardando la drogueria");
                    return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
                default:
                    message.put("mensaje", "Ha ocurrido un error inesperado");
                    return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> put(@RequestBody DrogueriaDTO dto, @PathVariable("id") int id){
        try{
            dto.setIdentificador((long) id);
            Map<String, Object> message = new HashMap<>();
            message.put("mensaje", "Actualizacion Exitosa");
            message.put("drogueria", service.save(dto));
            return new ResponseEntity<>(message, HttpStatus.OK);
        }catch (Exception e){
            Map<String, Object> message = new HashMap<>();
            message.put("drogueria", null);
            switch (e.getMessage()){
                case "Double":
                    message.put("mensaje", "La drogueria ya existe");
                    return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
                case "Unsaved":
                    message.put("mensaje", "No se ha podido actualizar la drogueria");
                    return new ResponseEntity<>(message, HttpStatus.NO_CONTENT);
                case "Failed":
                    message.put("mensaje", "Ha Ocurrido un error actualizando la drogueria");
                    return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
                default:
                    message.put("mensaje", "Ha ocurrido un error inesperado");
                    return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }
}
