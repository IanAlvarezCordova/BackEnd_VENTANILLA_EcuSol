package com.ecusol.ventanilla.controller;

import com.ecusol.ventanilla.dto.*;
import com.ecusol.ventanilla.service.VentanillaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/ventanilla") 
@CrossOrigin(origins = "*")
@Tag(name = "Ventanilla", description = "Operaciones de caja")
public class VentanillaController {

    @Autowired private VentanillaService service;
    @Operation(summary = "Consultar estado de cuenta")
    @GetMapping("/cuentas/{numero}")
    public ResponseEntity<InfoCuentaDTO> validarCuenta(@PathVariable String numero) {
        InfoCuentaDTO info = service.validarCuenta(numero);
        if (info == null) {
            throw new RuntimeException("Cuenta no encontrada o no existe");
        }

        return ResponseEntity.ok(info);
    }

    @Operation(summary = "Realizar Transacción (Depósito/Retiro)")
    @PostMapping("/transacciones")
    public ResponseEntity<TransaccionResponse> realizarTransaccion(@RequestBody TransaccionCajaRequest request) {
        TransaccionResponse respuesta = service.realizarTransaccion(request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{referencia}")
                .buildAndExpand(respuesta.getReferencia())
                .toUri();

        return ResponseEntity.created(location).body(respuesta);
    }
    @Operation(summary = "Buscar Cliente por Cédula")
    @GetMapping("/clientes/{cedula}")
    public ResponseEntity<ResumenClienteDTO> buscarCliente(@PathVariable String cedula) {
        ResumenClienteDTO cliente = service.buscarCliente(cedula);
        if (cliente == null) {
            throw new RuntimeException("Cliente no encontrado en el sistema");
        }
        return ResponseEntity.ok(cliente);
    }
    @Operation(summary = "Cambiar Estado de Cuenta")
    @PostMapping("/cuenta/estado")
    public ResponseEntity<String> cambiarEstadoCuenta(@RequestParam String cuenta, @RequestParam String estado) {
        service.cambiarEstadoCuenta(cuenta, estado);
        return ResponseEntity.ok("Estado actualizado");
    }
    @PostMapping("/activar-cuenta/{cuenta}")
    public ResponseEntity<String> activarCuenta(@PathVariable String cuenta) {
        service.activarCuenta(cuenta);
        return ResponseEntity.ok("Cuenta activada");
    }

    @PostMapping("/cliente/estado")
    public ResponseEntity<String> cambiarEstadoCliente(@RequestParam String cedula, @RequestParam String estado) {
        service.cambiarEstadoCliente(cedula, estado);
        return ResponseEntity.ok("Estado cliente actualizado");
    }

    @DeleteMapping("/cuenta/{cuenta}")
    public ResponseEntity<String> eliminarCuenta(@PathVariable String cuenta) {
        service.eliminarCuenta(cuenta);
        return ResponseEntity.ok("Cuenta eliminada");
    }
}