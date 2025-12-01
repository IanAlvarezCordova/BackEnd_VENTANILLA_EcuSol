package com.ecusol.ventanilla.dto;

import lombok.Data;
import java.math.BigDecimal;
// Importamos validaciones estándar (javax.validation o jakarta.validation según tu versión de Spring)
// import javax.validation.constraints.NotNull; 

@Data
public class TransaccionCajaRequest {
    private String tipoOperacion;
    private String cuentaOrigen;  
    private String cuentaDestino; 
    private BigDecimal monto;
    private String descripcion;
}