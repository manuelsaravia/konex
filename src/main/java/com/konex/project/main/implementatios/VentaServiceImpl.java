package com.konex.project.main.implementatios;

import com.konex.project.main.entities.Factura;
import com.konex.project.main.entities.Medicamento;
import com.konex.project.main.entities.Venta;
import com.konex.project.main.mappers.DrogueriaMapper;
import com.konex.project.main.mappers.MedicamentoMapper;
import com.konex.project.main.repositories.FacturaRepository;
import com.konex.project.main.repositories.VentaRepository;
import com.konex.project.main.services.DrogueriaService;
import com.konex.project.main.services.MedicamentoService;
import com.konex.project.main.services.VentaService;
import com.konex.project.main.transfers.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class VentaServiceImpl implements VentaService {

    @Autowired
    MedicamentoService medicamentoService;

    @Autowired
    DrogueriaService drogueriaService;

    @Autowired
    VentaRepository ventaRepository;

    @Autowired
    FacturaRepository facturaRepository;

    @Override
    @Transactional(rollbackOn = { Exception.class })
    public FacturaDTO vender(CompraDTO dto) {
        try{
            DrogueriaDTO drogueria = null;
            try {
                drogueria = drogueriaService.getById(dto.getIdDrogueria());
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

            Factura factura = new Factura();
            factura.setCliente(dto.getIdentificacionCliente());
            factura.setDrogueria(DrogueriaMapper.dtoToEntity(drogueria));
            factura.setFecha(new Date());

            Factura saved = facturaRepository.save(factura);

            FacturaDTO facturaDTO = new FacturaDTO();
            facturaDTO.setConsecutivo(saved.getConsecutivo());
            facturaDTO.setCliente(factura.getCliente());
            facturaDTO.setFecha(factura.getFecha());
            facturaDTO.setTotalAPagar(0);

            List<VentaDTO> ventasDTO = new ArrayList<>();

            for(DetalleCompraDTO detalle: dto.getDetalle()){
                try {
                    MedicamentoDTO medicamentoDTO = medicamentoService.getById(detalle.getCodigoMedicamento());
                    if(medicamentoDTO.getCantidad()<detalle.getCantidad())
                        throw new RuntimeException("Cantidad "+medicamentoDTO.getNombre());
                    medicamentoDTO.setCantidad(medicamentoDTO.getCantidad() - detalle.getCantidad());

                    Venta venta = new Venta();
                    venta.setCantidad(detalle.getCantidad());
                    venta.setFactura(saved);
                    venta.setMedicamento(MedicamentoMapper.dtoToEntity(medicamentoDTO));

                    Venta sold = ventaRepository.save(venta);
                    MedicamentoDTO medicamentoSold = medicamentoService.save(medicamentoDTO, dto.getIdDrogueria());

                    VentaDTO ventaDTO = new VentaDTO();
                    ventaDTO.setIdentificador(sold.getId());
                    ventaDTO.setCodigoMedicamento(medicamentoSold.getCodigo());
                    ventaDTO.setNombreMedicamento(medicamentoSold.getNombre());
                    ventaDTO.setCantidad(detalle.getCantidad());
                    ventaDTO.setValorUnitario(medicamentoSold.getPrecio());
                    ventaDTO.setValorTotal(detalle.getCantidad() * medicamentoSold.getPrecio());

                    ventasDTO.add(ventaDTO);
                    facturaDTO.setTotalAPagar(facturaDTO.getTotalAPagar()+ventaDTO.getValorTotal());

                }catch (Exception e){
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    throw new RuntimeException(e.getMessage());
                }
            }
            facturaDTO.setDetalle(ventasDTO);
            return facturaDTO;
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public FacturaDTO getById(int id) {
        return null;
    }

    @Override
    public List<FacturaDTO> getAll() {
        return null;
    }

    @Override
    public List<FacturaDTO> getByFechas(String fechaIni, String fechaFin) {
       return null;
    }
}
