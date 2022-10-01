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
public class Persona {
    public String nombre, dni;
    public char sexo;

    public Persona(String nombre, String dni, char sexo) {
        this.nombre = nombre;
        this.dni = dni;
        this.sexo = sexo;
    }

    public Persona(String nombre, String dni) {
        this.nombre = nombre;
        this.dni = dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }
    
    public String imprime(){
        return "Nombre :" +nombre + " , DNI: " + dni +" ,Sexo: " +sexo;
    }
    
    
    
    
}
