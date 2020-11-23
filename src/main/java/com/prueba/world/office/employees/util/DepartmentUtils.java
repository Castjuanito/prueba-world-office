package com.prueba.world.office.employees.util;

public class DepartmentUtils {
    public static String shortCode(String departmentName) {
        String formattedDepartmentName;
        formattedDepartmentName = departmentName.replaceAll("[^\\pL\\s]", "");
        formattedDepartmentName = formattedDepartmentName.toUpperCase();
        formattedDepartmentName = formattedDepartmentName.replaceAll("[ ]", "_");
        return formattedDepartmentName;
    }
}
