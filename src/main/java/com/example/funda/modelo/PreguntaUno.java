package com.example.funda.modelo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@Entity
public class PreguntaUno implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String pregunta;

	private String respuestaUno;
	
	private Boolean respuestaUnov;

	private String respuestaDos;
	
	private Boolean respuestaDosv;

	private String respuestaTres;
	
	private Boolean respuestaTresv;
	
	@Transient
	private int opcion;
	
	@OneToMany(mappedBy = "preguntaUnoBean", cascade = CascadeType.REMOVE)
	private List<Formulario> formulario;

	public PreguntaUno() {
		this.respuestaUnov = false;
		this.respuestaDosv = false;
		this.respuestaTresv = false;
		this.opcion = 0;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPregunta() {
		return pregunta;
	}

	public void setPregunta(String pregunta) {
		this.pregunta = pregunta;
	}

	public String getRespuestaUno() {
		return respuestaUno;
	}

	public void setRespuestaUno(String respuestaUno) {
		this.respuestaUno = respuestaUno;
	}

	public String getRespuestaDos() {
		return respuestaDos;
	}

	public void setRespuestaDos(String respuestaDos) {
		this.respuestaDos = respuestaDos;
	}

	public String getRespuestaTres() {
		return respuestaTres;
	}

	public void setRespuestaTres(String respuestaTres) {
		this.respuestaTres = respuestaTres;
	}

	public Boolean getRespuestaUnov() {
		return respuestaUnov;
	}

	public void setRespuestaUnov(Boolean respuestaUnov) {
		this.respuestaUnov = respuestaUnov;
	}

	public Boolean getRespuestaDosv() {
		return respuestaDosv;
	}

	public void setRespuestaDosv(Boolean respuestaDosv) {
		this.respuestaDosv = respuestaDosv;
	}

	public Boolean getRespuestaTresv() {
		return respuestaTresv;
	}

	public void setRespuestaTresv(Boolean respuestaTresv) {
		this.respuestaTresv = respuestaTresv;
	}

	public List<Formulario> getFormulario() {
		return formulario;
	}

	public void setFormularios(List<Formulario> formulario) {
		this.formulario = formulario;
	}

	public int getOpcion() {
		return opcion;
	}

	public void setOpcion(int opcion) {
		this.opcion = opcion;
	}
}
