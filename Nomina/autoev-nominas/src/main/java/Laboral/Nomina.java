/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Laboral;

/**
 *
 * @author Ernesto
 */
public class Nomina {
    
    private static final int SUELDO_BASE[]={50000, 70000, 90000, 110000, 130000,
                                            150000, 170000, 190000, 210000, 230000};
    
    public static int sueldo (Empleado emp){
        return SUELDO_BASE[emp.getCategoria()-1]+emp.anyos*5000;
    }
    
}
