package com.konex.project.main.controllers;

import com.konex.project.main.services.VentaService;
import com.konex.project.main.transfers.CompraDTO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Api(tags="Controlador de Ventas")
@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping(path="api/venta")
public class VentaController {

    @Autowired
    VentaService service;

    @PostMapping("")
    public ResponseEntity<?> vender(@RequestBody CompraDTO dto){
        try{
            Map<String, Object> message = new HashMap<>();
            message.put("mensaje", "Venta Exitosa");
            message.put("factura", service.vender(dto));
            return new ResponseEntity<>(message, HttpStatus.OK);
        }catch (Exception e){
            Map<String, Object> message = new HashMap<>();
            message.put("factura", null);
            if(e.getMessage().startsWith("Cantidad")){
                String x = e.getMessage().replace("Cantidad ", "");
                message.put("mensaje", "El medicamento "+x+" no tiene suficientes elementos para ser vendido");
                return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
            }
            else {
                switch (e.getMessage()) {
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
                        message.put("mensaje", "No se han encontrado el medicamento");
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




}
