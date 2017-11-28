package uva.tds.pr4.raqlope.pruebasSecuencia;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import uva.tds.pr4.raqlope.clases.Asignatura;
import uva.tds.pr4.raqlope.clases.Prueba;

/**
 * Tests de Secuencia de la clase Asignatura
 * @author raqlope Raquel Lopez Martin
 *
 */
@SuppressWarnings("deprecation")
public class AsignaturaTestSecuencia {

	Asignatura asignatura;
	
	Prueba prueba1 = null;
	Prueba prueba2 = new Prueba(new Date(2017, 03, 11), "Parcial", "Segundo parcial de la asignatura", 10.0, 0.6);
	Prueba prueba3 = new Prueba(new Date(2017, 03, 11), "Parcial", "Segundo parcial de la asignatura", 10.0, 1.2);
	Prueba prueba4 = new Prueba(new Date(2017, 03, 11), "Parcial", "Segundo parcial de la asignatura", 10.0, 0.2);
	Prueba prueba5 = new Prueba(new Date(2017, 03, 11), "Parcial", "Segundo parcial de la asignatura", 10.0, 0.4);
	Prueba prueba6 = new Prueba(new Date(2017, 01, 01), "Parcial", "Segundo parcial de la asignatura", 10.0, 0.4);
	Prueba prueba7 = new Prueba(new Date(2017, 11, 14), "Parcial", "Segundo parcial de la asignatura", 10.0, 0.4);
	
	/**
	 * Preparamos antes de cada ejecucion la creacion de asignatura
	 */
	
	@Before
	public void preparar(){
		asignatura = new Asignatura("Tecnologias Desarrollo Software", "asignatura de 3� optativa",
				10.0, new Date(2017, 01, 06));
	}
	
	//Tests relacionados con la adicion de una prueba en Asignatura
	
		/**
		 * Comprueba que el peso de una prueba nueva sea menor de uno
		 */
		@Test
		public void testAsignaturaAyadePruebaPesoMenorUno(){
			assertTrue(asignatura.nuevaPrueba(prueba2));
		}
		
		/**
		 * Comprueba que la prueba no sea null
		 * El test da verde ya que Asignatura devuelve false como default
		 */
		@Test
		public void testAsignaturaAyadePruebaNull(){
			assertFalse(asignatura.nuevaPrueba(prueba1));
		}
		
		/**
		 * Comprueba que el peso de una prueba nueva no sea mayor que 1
		 * El test da verde ya que Asignatura devuelve false como default 
		 */
		@Test
		public void testAsignaturaAyadePruebaPesoMayorUno(){
			assertFalse(asignatura.nuevaPrueba(prueba3));
		}
		
		/**
		 * Comprueba que no permita una nueva prueba si el peso total va a ser mayor que 1
		 */
		@Test
		public void testAsignaturaAyadePruebaPesosSonMayorUno(){
			assertTrue(asignatura.nuevaPrueba(prueba2));
			assertFalse(asignatura.nuevaPrueba(prueba3));
		}
		
		/**
		 * Comprueba que a�ade una nueva prueba cuando el peso no supera 1
		 */
		@Test
		public void testAsignaturaAyadePruebaPesosSonMenorUno(){
			assertTrue(asignatura.nuevaPrueba(prueba2));
			assertTrue(asignatura.nuevaPrueba(prueba4));
		}
		
		/**
		 * Comprueba que a�ade una nueva prueba cuando el peso es igual 1
		 */
		@Test
		public void testAsignaturaAyadePruebaPesosSonIgualUno(){
			assertTrue(asignatura.nuevaPrueba(prueba2));
			assertTrue(asignatura.nuevaPrueba(prueba5));
		}
		
		/**
		 * Comprueba que a�ade una nueva prueba dentro del intervalo de fechas permitido
		 * por la Asignatura
		 */
		@Test
		public void testAsignaturaAyadePruebaFechaPermitida(){
			assertTrue(asignatura.nuevaPrueba(prueba2));
		}
		
		/**
		 * Comprueba que a�ade una nueva prueba fuera del intervalo de fechas permitido
		 * por la Asignatura, siendo la fecha menor que la fecha de comienzo
		 * El test da verde ya que la clase Asignatura devuelve false como default
		 */
		@Test
		public void testAsignaturaAyadePruebaFechaNoPermitidaMenor(){
			assertFalse(asignatura.nuevaPrueba(prueba6));
		}
		
		/**
		 * Comprueba que a�ade una nueva prueba fuera del intervalo de fechas permitido
		 * por la Asignatura, siendo la fecha mayor que la fecha de finalizacion
		 * El test da verde ya que la clase Asignatura devuelve false como default
		 */
		@Test
		public void testAsignaturaAyadePruebaFechaNoPermitidaMayor(){
			asignatura.setFechaFin(new Date(2017, 10, 12));
			assertFalse(asignatura.nuevaPrueba(prueba7));
		}
		
		
		//Tests de los pesos de una prueba en una Asignatura
		
		/**
		 * Comprueba que la suma final de pesos es igual a 1
		 */
		@Test
		public void testAsignaturaPesoFinalIgualUno(){
			Double peso = 1.0;
			asignatura.nuevaPrueba(prueba2);
			asignatura.nuevaPrueba(prueba5);
			assertEquals(asignatura.getSumaPesos(), peso);
		}
		
		/**
		 * Comprueba que la suma final de pesos no sea menor que 1 
		 */
		@Test
		public void testAsignaturaPesoFinalMenorUno(){
			Double peso = 0.8;
			asignatura.nuevaPrueba(prueba2);
			asignatura.nuevaPrueba(prueba4);
			assertEquals(asignatura.getSumaPesos(), peso);
		}
		
		/**
		 * Comprueba que la suma final de pesos no sea mayor que 1 
		 * Debido a tests anteriores, la prueba3 no sera añadida ya que el peso total seria mayor de uno
		 */
		@Test
		public void testAsignaturaPesoFinalMayorUno(){
			Double peso = 0.6;
			asignatura.nuevaPrueba(prueba2);
			asignatura.nuevaPrueba(prueba3);
			assertEquals(asignatura.getSumaPesos(), peso);
		}
}
