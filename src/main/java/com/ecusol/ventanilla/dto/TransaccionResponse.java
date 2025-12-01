package com.ecusol.ventanilla.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransaccionResponse {
    private String referencia; 
    private String estado;     
    private String mensaje;    
    private String fecha;      
    private BigDecimal monto;
}