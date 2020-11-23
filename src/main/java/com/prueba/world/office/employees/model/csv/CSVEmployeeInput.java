package com.prueba.world.office.employees.model.csv;

import com.opencsv.bean.CsvBindByPosition;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@ToString
public class CSVEmployeeInput {
    @CsvBindByPosition(position = 0)
    private String Nombre;
    @CsvBindByPosition(position = 1)
    private String Cargo;
    @CsvBindByPosition(position = 2)
    private Double Salario;
    @CsvBindByPosition(position = 3)
    private Boolean TiempoCompleto;
    @CsvBindByPosition(position = 4)
    private String Departamento;
}
