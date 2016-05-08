/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejercicio14;

/**
 *
 * @author Adrian
 */
public class Mascota 
{
    int codigo;
    String alias;
    String especie;
    String raza;
    String color_de_pelo;
    String fecha_nacimiento;
    int[] peso_ultimas_visitas;
    int[] peso_actual;
    Historial historial;
    
    
    public Mascota(int codigoEnt, String aliasEnt, String razaEnt, String color_de_peloEnt, String fecha_nacimientoEnt)
    {
        
    }
    
    /**
     * Indica el peso actual y, si no es una rectificación, lo añade a últimas visitas.
     * @param pesoActual Peso actual de la mascota.
     * @param rectificarAnotado Indica si se está añadiendo un nuevo peso o rectificando el existente.
     */
    public void SetPesoActual(int pesoActual, boolean rectificarAnotado)
    {
        
    }
}
