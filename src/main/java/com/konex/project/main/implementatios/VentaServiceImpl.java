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
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

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
        try{
            SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
            Date ini = formatter.parse(fechaIni);
            Date fin = formatter.parse(fechaFin);
            List<Venta> ventas = ventaRepository.findBetweenDates(ini, fin);
            if(ventas != null && !ventas.isEmpty()){
                Map<Long, FacturaDTO> map = new HashMap<>();
                for(Venta venta: ventas){
                    if(!map.containsKey(venta.getFactura().getConsecutivo())){
                        FacturaDTO facturaDTO = new FacturaDTO();
                        facturaDTO.setConsecutivo(venta.getFactura().getConsecutivo());
                        facturaDTO.setFecha(venta.getFactura().getFecha());
                        facturaDTO.setCliente(venta.getFactura().getCliente());
                        facturaDTO.setDetalle(new ArrayList<>());
                        facturaDTO.setTotalAPagar(0);
                        map.put(venta.getFactura().getConsecutivo(), facturaDTO);
                    }
                    VentaDTO ventaDTO = new VentaDTO();
                    ventaDTO.setIdentificador(venta.getId());
                    ventaDTO.setCodigoMedicamento(venta.getMedicamento().getCodigo());
                    ventaDTO.setNombreMedicamento(venta.getMedicamento().getNombre());
                    ventaDTO.setCantidad(venta.getCantidad());
                    ventaDTO.setValorUnitario(venta.getMedicamento().getPrecio());
                    ventaDTO.setValorTotal(venta.getCantidad() * venta.getMedicamento().getPrecio());

                    FacturaDTO aux = map.get(venta.getFactura().getConsecutivo());

                    aux.getDetalle().add(ventaDTO);
                    aux.setTotalAPagar(aux.getTotalAPagar()+ventaDTO.getValorTotal());
                    map.put(venta.getFactura().getConsecutivo(), aux);
                }

                return map.values().stream().map(dto -> {return dto;}).collect(Collectors.toList());
            }
            return new ArrayList<>();
        }catch(Exception e){
            return null;
        }
    }
}
