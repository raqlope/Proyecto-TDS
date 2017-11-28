package uva.tds.pr4.raqlope.interfaces;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

import uva.tds.pr4.raqlope.clases.Prueba;

/**
 * Interfaz de la clase Asignatura
 * @author raqlope Raquel Lopez Martin
 *
 */
public interface AsignaturaInterface {
	
	/**
	 * Getter de nombre
	 * @return nombre
	 */
	String getNombre();
	
	/**
	 * Getter de descripcion
	 * @return descripcion
	 */
	String getDescripcion();
	
	/**
	 * Getter de calificacion maxima
	 * @return califMax
	 */
	Double getCalifMax();
	
	/**
	 * Getter de la fecha de inicio 
	 * @return fechaIni
	 */
	Date getFechaIni();
	
	/**
	 * Getter de la fecha final
	 * @return fechaFin
	 */
	Date getFechaFin();
	
	/**
	 * Getter de la lista de pruebas de la asignatura
	 * @return pruebas
	 */
	ArrayList<Prueba> getListaPruebas();
	
	
	/**
	 * Setter de fecha final
	 * @param fecha
	 */
	void setFechaFin(Date fecha);
	
	/**
	 * Aï¿½ade una nueva prueba a Asignatura, devolviendo true cuando se ha aï¿½adido correctamente
	 * @return boolean
	 */
	boolean nuevaPrueba(Prueba prueba);
	
	
	/**
	 * Devuelve la suma de todos los pesos de las pruebas de la asignatura
	 * @return sumPesos: Double
	 */
	Double getSumaPesos();
	
	/**
	 * Devuelve una tabla con los alumnos y la suma total de sus calificaciones en diferentes pruebas
	 * @return calificaciones: Hashtable<String, Double>
	 */
	Hashtable<String, Double> getCalificaciones();
	
	/**
	 * Devuelve la calificacion de un alumno
	 * @param id :String identificador del alumno
	 * @return calif: calificacion total de un alumno
	 */
	Double getCalificacionesAlumno(String id);
	
}
