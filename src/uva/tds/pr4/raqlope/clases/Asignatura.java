package uva.tds.pr4.raqlope.clases;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import uva.tds.pr4.raqlope.clases.Prueba;

import uva.tds.pr4.raqlope.interfaces.*;


/**
 * Clase Asignatura, implementada desde AsignaturaInter
 * @author raqlope Raquel Lopez Martin
 * 
 * nombre: nombre de la asignatura
 * califMax: calificacion maxima que puede tener la asignatura
 * descripcion: descripcion de la asignatura
 * fechaIni: fecha de inicio de la asignatura
 * fechaFin: fecha final de la asignatura
 * pruebas: lista de pruebas de la clase Prueba
 */
public class Asignatura implements AsignaturaInterface {
	
	private String nombre;
	private Double califMax;
	private String descripcion;
	private Date fechaIni;
	private Date fechaFin;
	private ArrayList<Prueba> pruebas = new ArrayList<Prueba>();
	
	/**
	 * Constructor de Asignatura
	 * @param nombre
	 * @param descripcion
	 * @param califMax
	 * @param fechaIni
	 * @throws IllegalArgumentException si nombre, descripcion, califMax o fechaIni es null
	 */
	public Asignatura(String nombre, String descripcion, Double califMax, Date fechaIni){
		if (nombre!=null){
			this.nombre = nombre;
		}else{
			throw new IllegalArgumentException();
		}
		if (descripcion!=null){
			this.descripcion = descripcion;
		}else{
			throw new IllegalArgumentException();
		}
		if (califMax!=null){
			this.califMax = califMax;
		}else{
			throw new IllegalArgumentException();
		}
		if (fechaIni!=null){
			this.fechaIni = fechaIni;
		}else{
			throw new IllegalArgumentException();
		}
		this.fechaFin = null;
	}

	/**
	 * Getter de nombre
	 * @return nombre
	 */
	public String getNombre() {
		return this.nombre;
	}

	/**
	 * Getter de descripcion
	 * @return descripcion
	 */
	public String getDescripcion() {
		return this.descripcion;
	}

	/**
	 * Getter de calificacion maxima
	 * @return califMax
	 */
	public Double getCalifMax() {
		return this.califMax;
	}

	/**
	 * Getter de la fecha de inicio 
	 * @return fechaIni
	 */
	public Date getFechaIni() {
		return this.fechaIni;
	}
	
	/**
	 * Getter de la fecha final
	 * @return fechaFin
	 */
	public Date getFechaFin() {
		return this.fechaFin;
	}
	
	/**
	 * Getter de la lista de pruebas de la asignatura
	 * @return pruebas
	 */
	public ArrayList<Prueba> getListaPruebas() {
		return pruebas;
	}
	

	/**
	 * Setter de fecha final
	 * @param fecha
	 * @throws IllegalArgumentException si la fecha que se quiere introducir o cambiar es null
	 */
	public void setFechaFin(Date fecha) {
		if (fecha!= null){
			this.fechaFin = fecha;
		}else{
			throw new IllegalArgumentException();
		}
		
	}

	/**
	 * A�ade una nueva prueba a Asignatura, devolviendo true cuando se ha a�adido correctamente
	 * @return boolean
	 */
	public boolean nuevaPrueba(Prueba prueba) {
		if (prueba!=null && prueba.getPeso()<=1.0 && prueba.getFecha().after(fechaIni)){
			Double sum = getSumaPesos()+prueba.getPeso();
			if (this.getFechaFin()==null && sum<=1.0){
				pruebas.add(prueba);
				return true;
			}else if (this.getFechaFin()!=null && prueba.getFecha().before(fechaFin)){
				pruebas.add(prueba);
				return true;			
			}else{
				return false;
			}
		}else{
			return false;
		}
	}

	
	/**
	 * Devuelve la suma de todos los pesos de las pruebas de la asignatura
	 * @return sumPesos: Double
	 */
	public Double getSumaPesos(){
		Double sumPesos=0.0;
		for (int i=0; i < pruebas.size(); i++){
			sumPesos = sumPesos + pruebas.get(i).getPeso();
		}
		return sumPesos;
	}

	
	/**
	 * Devuelve una tabla con los alumnos y la suma total de sus calificaciones en diferentes pruebas
	 * @return calificaciones: Hashtable<String, Double>
	 */
	public Hashtable<String, Double> getCalificaciones(){
		//creo el Hashtable que voy a devolver con la primera prueba que tenga Asignatura
		Hashtable<String, Double> calif= new Hashtable<String, Double>();
		String str; 
		//Recorre todas las pruebas que tiene la asignatura 
		for (int i = 0; i< getListaPruebas().size(); i++){
			Set<String> keys = getListaPruebas().get(i).getCalificaciones().keySet();
			Iterator<String> itr = keys.iterator();
			while(itr.hasNext()){
				str = itr.next();
				//Si calif ya tiene un identificador igual, tenemos que eliminarlo para añadir el nuevo
				if (calif.containsKey(str) && 
						getListaPruebas().get(i).getCalificaciones().containsKey(str)){
					
					double aux = calcNota(calif.get(str).doubleValue(),
							getListaPruebas().get(i).getCalificaciones().get(str), 
							getListaPruebas().get(i).getPeso());
					calif.remove(str);
					calif.put(str, aux);
				
				//Si no lo contiene, añadimos a calif la calificacion calculada
				}else if(getListaPruebas().get(i).getCalificaciones().containsKey(str)){
					
					double aux = calcNota(0.0, getListaPruebas().get(i).getCalificaciones().get(str),
							getListaPruebas().get(i).getPeso());
					calif.put(str, aux);
				}
				
			}
		}
		return calif;
	}
	
	/**
	 * Devuelve una tabla con los alumnos y la suma total de sus calificaciones en diferentes pruebas
	 * Solo cuando la Asignatura haya terminado y todas las pruebas hayan sido totalmente calificadas
	 * @return calificaciones: Hashtable<String, Double>
	 */
	public Hashtable<String, Double> getCalificacionesFinales(){
		//creo el Hashtable que voy a devolver con la primera prueba que tenga Asignatura
		Hashtable<String, Double> califFinales = null;
		Boolean check = false;
		for (int i = 0; i< pruebas.size(); i++){
			if(pruebas.get(i).isCompletamenteCalificada() && getSumaPesos()==1.0){
				check = true;
			}else{
				check = false;
			}
		}
		if (check == true){
			califFinales = getCalificaciones();
		}
		return califFinales;
	}
	
	/**
	 * Devuelve la calificacion de un alumno
	 * @param id :String identificador del alumno
	 * @return calif: calificacion total de un alumno
	 */
	public Double getCalificacionesAlumno(String id){
		Double califAlumno = 0.0;
		for(int i = 0; i<pruebas.size(); i++){
			if (getListaPruebas().get(i).getCalificaciones().containsKey(id)){
				
				califAlumno = calcNota(califAlumno,getListaPruebas().get(i).getCalificaciones().get(id),
						getListaPruebas().get(i).getPeso());
			}
		}
		
		if (califAlumno > getCalifMax()){
			califAlumno = null;
		}
		return califAlumno;
		
	}
	
	private Double calcNota(Double califTotal, Double califPrueba, Double peso){
		//Calcula una nota a partir de la calificacion acumulada, la calificacion en dicha prueba
		//y el peso de la prueba
		
		BigDecimal valor1 = new BigDecimal(califPrueba);
		BigDecimal valor2 = new BigDecimal(peso);
		
		double aux1 = valor1.multiply(valor2).doubleValue();
		double aux2 = califTotal.doubleValue();
		
		BigDecimal valor3 = new BigDecimal(aux1+aux2);
		
		return  valor3.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
}