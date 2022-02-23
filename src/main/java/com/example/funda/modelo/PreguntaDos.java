package com.example.funda.modelo;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class PreguntaDos {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column
	private String informacion;

	@Column
	private String mPreguntaUno;
	
	@Lob 
	@Basic(fetch = FetchType.LAZY) 
	@Column
	private byte[] respuestaUno;

	@Column
	private String mPreguntaDos;
	
	@Lob 
	@Basic(fetch = FetchType.LAZY) 
	@Column
	private byte[] respuestaDos;

	@Column
	private String mPreguntaTres;
	
	@Lob 
	@Basic(fetch = FetchType.LAZY) 
	@Column
	private byte[] respuestaTres;
	
	@OneToMany(mappedBy = "preguntaDosB", cascade = CascadeType.REMOVE)
	private List<Formulario> formulario;

	public PreguntaDos() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getInformacion() {
		return informacion;
	}

	public void setInformacion(String informacion) {
		this.informacion = informacion;
	}

	public String getmPreguntaUno() {
		return mPreguntaUno;
	}

	public void setmPreguntaUno(String mPreguntaUno) {
		this.mPreguntaUno = mPreguntaUno;
	}

	public byte[] getRespuestaUno() {
		return respuestaUno;
	}

	public void setRespuestaUno(byte[] respuestaUno) {
		this.respuestaUno = respuestaUno;
	}

	public String getmPreguntaDos() {
		return mPreguntaDos;
	}

	public void setmPreguntaDos(String mPreguntaDos) {
		this.mPreguntaDos = mPreguntaDos;
	}

	public byte[] getRespuestaDos() {
		return respuestaDos;
	}

	public void setRespuestaDos(byte[] respuestaDos) {
		this.respuestaDos = respuestaDos;
	}

	public String getmPreguntaTres() {
		return mPreguntaTres;
	}

	public void setmPreguntaTres(String mPreguntaTres) {
		this.mPreguntaTres = mPreguntaTres;
	}

	public byte[] getRespuestaTres() {
		return respuestaTres;
	}

	public void setRespuestaTres(byte[] respuestaTres) {
		this.respuestaTres = respuestaTres;
	}

	public List<Formulario> getFormulario() {
		return formulario;
	}

	public void setFormulario(List<Formulario> formulario) {
		this.formulario = formulario;
	}
}
