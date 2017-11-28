package uva.tds.pr4.raqlope.pruebasAislamiento;


import org.easymock.Mock;
import org.junit.Before;
import org.junit.Test;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Date;
import java.util.Hashtable;

import uva.tds.pr4.raqlope.clases.Asignatura;
import uva.tds.pr4.raqlope.clases.Prueba;

/**
 * Tests de Aislamiento
 * Se aisla la clase Asignatura de la clase Prueba, usando EasyMock
 * @author raqlope Raquel Lopez Martin
 *
 */
@SuppressWarnings("deprecation")
public class AsignaturaTestAislamiento {

	Asignatura asignatura;
	
	@Mock
	private Prueba prueba1;
	private Prueba prueba2;
	
	@Before
	public void preparar(){
		asignatura = new Asignatura("Tecnologias Desarrollo Software", "asignatura de 3� optativa",
				10.0, new Date(2017, 01, 06));
		prueba1 = createMock(Prueba.class);
		prueba2 = createMock(Prueba.class);
	}
	
	/**
	 * Comprueba que obtiene la lista de calificaciones parcial para una asignatura 
	 */
	@Test
	public void testAsignaturaListaCalifParcial(){
		Hashtable<String, Double> pr1 = new Hashtable<String, Double>();
		Hashtable<String, Double> pr2 = new Hashtable<String, Double>();
		pr1.put("01Luis", 7.2);  pr1.put("02Pedro", 0.8);
		pr2.put("01Luis", 5.5);  pr2.put("02Pedro", 3.2);
		
		expect(prueba1.getCalificaciones()).andReturn(pr1).times(5);
		expect(prueba2.getCalificaciones()).andReturn(pr2).times(5);
		expect(prueba1.getFecha()).andReturn(new Date(2017, 03, 11)).times(2);
		expect(prueba2.getFecha()).andReturn(new Date(2017, 03, 11)).times(2);
		expect(prueba1.getPeso()).andReturn(0.6).times(5);
		expect(prueba2.getPeso()).andReturn(0.2).times(5);
		replay(prueba1); replay(prueba2);
				
		asignatura.nuevaPrueba(prueba1);
		asignatura.nuevaPrueba(prueba2);
		
		
		Hashtable<String, Double> califParcial = new Hashtable<String, Double>();
		califParcial.put("01Luis", 5.42); 
		califParcial.put("02Pedro", 1.12);
				
		assertEquals(califParcial, asignatura.getCalificaciones());
	}
	
	/**
	/**
	 * Comprueba que obtiene la lista de calificaciones final para una prueba 
	 */
	@Test
	public void testAsignaturaListaCalifFinal(){
		Hashtable<String, Double> pr1 = new Hashtable<String, Double>();
		Hashtable<String, Double> pr2 = new Hashtable<String, Double>();
		pr1.put("01Luis", 7.2);  pr1.put("02Pedro", 0.8);
		pr2.put("01Luis", 5.5);  pr2.put("02Pedro", 3.2);
		
		expect(prueba1.getCalificaciones()).andReturn(pr1).times(5);
		expect(prueba2.getCalificaciones()).andReturn(pr2).times(5);
		expect(prueba1.getFecha()).andReturn(new Date(2017, 03, 11)).times(1);
		expect(prueba2.getFecha()).andReturn(new Date(2017, 03, 11)).times(1);
		expect(prueba1.getPeso()).andReturn(0.6).times(10);
		expect(prueba2.getPeso()).andReturn(0.4).times(10);
		expect(prueba1.isCompletamenteCalificada()).andReturn(true).times(1);
		expect(prueba2.isCompletamenteCalificada()).andReturn(true).times(1);
		replay(prueba1); replay(prueba2);
			
		//creamos un Hashtable para comparar
		Hashtable<String, Double> califFinal = new Hashtable<String, Double>();
		califFinal.put("01Luis", 6.52);
		califFinal.put("02Pedro", 1.76);
		
		//a�adimos la prueba calificada en nuestra asignatura
		asignatura.nuevaPrueba(prueba1);
		asignatura.nuevaPrueba(prueba2);
		
		assertEquals(asignatura.getCalificacionesFinales(), califFinal);
		
	}
	
	/**
	 * Comprueba que no obtiene la lista de calificaciones final para una prueba 
	 * ya que el peso total de las pruebas de la asignatura es menor de uno
	 * El test da verde ya que la clase Asignatura devuelve null cuando no puede devolver calificaciones
	 */
	@Test
	public void testAsignaturaListaCalifFinalPesoMenor(){
		Hashtable<String, Double> pr1 = new Hashtable<String, Double>();
		Hashtable<String, Double> pr2 = new Hashtable<String, Double>();
		pr1.put("01Luis", 7.2);  pr1.put("02Pedro", 0.8);
		pr2.put("01Luis", 5.5);  pr2.put("02Pedro", 3.2);
		
		expect(prueba1.getCalificaciones()).andReturn(pr1).times(5);
		expect(prueba2.getCalificaciones()).andReturn(pr2).times(5);
		expect(prueba1.getFecha()).andReturn(new Date(2017, 03, 11)).times(1);
		expect(prueba2.getFecha()).andReturn(new Date(2017, 03, 11)).times(1);
		expect(prueba1.getPeso()).andReturn(0.6).times(10);
		expect(prueba2.getPeso()).andReturn(0.2).times(10);
		expect(prueba1.isCompletamenteCalificada()).andReturn(true).times(1);
		expect(prueba2.isCompletamenteCalificada()).andReturn(true).times(1);
		replay(prueba1); replay(prueba2);
		
		//creamos un Hashtable para comparar
		Hashtable<String, Double> califFinal = new Hashtable<String, Double>();
		califFinal.put("01Luis", 5.42);
		califFinal.put("02Pedro", 1.12);
		
		//a�adimos la prueba calificada en nuestra asignatura
		asignatura.nuevaPrueba(prueba1);
		asignatura.nuevaPrueba(prueba2);
		
		assertNull(asignatura.getCalificacionesFinales());
	}
	
	/**
	 * Comprueba que no obtiene la lista de calificaciones final para una prueba 
	 * ya que hay pruebas que no han sido calificadas
	 * El test da verde ya que la clase Asignatura devuelve null cuando no puede devolver calificaciones
	 */
	@Test
	public void testAsignaturaListaCalifFinalNoCalif(){
		Hashtable<String, Double> pr1 = new Hashtable<String, Double>();
		Hashtable<String, Double> pr2 = new Hashtable<String, Double>();
		pr1.put("01Luis", 7.2);  pr1.put("02Pedro", 0.8);
		pr2.put("01Luis", 5.5);  pr2.put("02Pedro", 3.2);
		
		expect(prueba1.getCalificaciones()).andReturn(pr1).times(5);
		expect(prueba2.getCalificaciones()).andReturn(pr2).times(5);
		expect(prueba1.getFecha()).andReturn(new Date(2017, 03, 11)).times(1);
		expect(prueba2.getFecha()).andReturn(new Date(2017, 03, 11)).times(1);
		expect(prueba1.getPeso()).andReturn(0.6).times(10);
		expect(prueba2.getPeso()).andReturn(0.4).times(10);
		expect(prueba1.isCompletamenteCalificada()).andReturn(true).times(1);
		expect(prueba2.isCompletamenteCalificada()).andReturn(false).times(1);
		replay(prueba1); replay(prueba2);
		
		//creamos un Hashtable para comparar
		Hashtable<String, Double> califFinal = new Hashtable<String, Double>();
		califFinal.put("01Luis", 6.52);
		califFinal.put("02Pedro", 1.76);
		
		//a�adimos la prueba calificada en nuestra asignatura
		asignatura.nuevaPrueba(prueba1);
		asignatura.nuevaPrueba(prueba2);
		
		assertNull(asignatura.getCalificacionesFinales());
	}
	
	
	
	//Tests de las calificaciones de un alumno
	
	/**
	 * Comprobamos que lo que nos devuelve las calificaciones del alumno es igual a lo esperado
	 */
	@Test
	public void testAsignaturaCalifAlumno(){
		Hashtable<String, Double> pr1 = new Hashtable<String, Double>();
		Hashtable<String, Double> pr2 = new Hashtable<String, Double>();
		pr1.put("01Luis", 7.2);  pr2.put("01Luis", 5.5);  
		
		expect(prueba1.getCalificaciones()).andReturn(pr1).times(5);
		expect(prueba2.getCalificaciones()).andReturn(pr2).times(5);
		expect(prueba1.getFecha()).andReturn(new Date(2017, 03, 11)).times(2);
		expect(prueba2.getFecha()).andReturn(new Date(2017, 03, 11)).times(2);
		expect(prueba1.getPeso()).andReturn(0.6).times(5);
		expect(prueba2.getPeso()).andReturn(0.4).times(5);
		replay(prueba1); replay(prueba2);
		
		//Creamos el double con el que vamos a comparar
		Double calif = 6.52;
		
		//a�adimos la prueba calificada en nuestra asignatura
		asignatura.nuevaPrueba(prueba1);
		asignatura.nuevaPrueba(prueba2);
		
		assertEquals(asignatura.getCalificacionesAlumno("01Luis"), calif);
	}
	
	/**
	 * Comprobamos que no permite la calificacion no es valida porque es mayor a la permitida por la asignatura
	 * El test da verde ya que la clase Asignatura devuelve null cuando no puede devolver calificaciones
	 */
	@Test
	public void testAsignaturaCalifAlumnoMayorNota(){
		Hashtable<String, Double> pr1 = new Hashtable<String, Double>();
		Hashtable<String, Double> pr2 = new Hashtable<String, Double>();
		pr1.put("01Luis", 7.2);  pr2.put("01Luis", 100.0);  
		
		expect(prueba1.getCalificaciones()).andReturn(pr1).times(5);
		expect(prueba2.getCalificaciones()).andReturn(pr2).times(5);
		expect(prueba1.getFecha()).andReturn(new Date(2017, 03, 11)).times(2);
		expect(prueba2.getFecha()).andReturn(new Date(2017, 03, 11)).times(2);
		expect(prueba1.getPeso()).andReturn(0.6).times(5);
		expect(prueba2.getPeso()).andReturn(0.4).times(5);
		replay(prueba1); replay(prueba2);
		
		
		//a�adimos la prueba calificada en nuestra asignatura
		asignatura.nuevaPrueba(prueba1);
		asignatura.nuevaPrueba(prueba2);
		
		assertNull(asignatura.getCalificacionesAlumno("01Luis"));
	}
	
}
