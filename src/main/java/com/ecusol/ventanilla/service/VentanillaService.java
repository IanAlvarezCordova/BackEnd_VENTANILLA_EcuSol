package com.ecusol.ventanilla.service;

import com.ecusol.ventanilla.client.CoreClient;
import com.ecusol.ventanilla.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class VentanillaService {

    @Autowired private CoreClient coreClient;
    public InfoCuentaDTO validarCuenta(String numero) {
        return coreClient.validarCuenta(numero);
    }

public TransaccionResponse realizarTransaccion(TransaccionCajaRequest request) {

    String respuestaCore = coreClient.operar(request); 

    TransaccionResponse response = new TransaccionResponse();
    response.setReferencia(extraerReferencia(respuestaCore)); 
    response.setEstado("APROBADA");
    response.setMensaje(respuestaCore);
    response.setFecha(LocalDateTime.now().toString());
    
    response.setMonto(request.getMonto()); 

    return response;
}
    private String extraerReferencia(String respuestaCore) {
        if (respuestaCore != null && respuestaCore.contains(":")) {
            String[] partes = respuestaCore.split(":");
            return partes.length > 1 ? partes[1].trim() : UUID.randomUUID().toString();
        }
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
    public ResumenClienteDTO buscarCliente(String cedula) {
        return coreClient.buscarCliente(cedula);
    }
    public void cambiarEstadoCuenta(String cuenta, String estado) {
        coreClient.cambiarEstadoCuenta(cuenta, estado);
    }

    public void activarCuenta(String cuenta) {
        coreClient.cambiarEstadoCuenta(cuenta, "ACTIVA");
    }
    
    public void cambiarEstadoCliente(String cedula, String estado) {
        coreClient.cambiarEstadoCliente(cedula, estado);
    }

    public void eliminarCuenta(String cuenta) {
        coreClient.eliminarCuenta(cuenta); 
    }
}