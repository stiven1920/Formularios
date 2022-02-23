package com.example.funda.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class RespuestaUno implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column
	private String respuesta;

	@Column
	private boolean resultado;
	
	@Transient
	private int opcion;
	
	@ManyToOne
	private Usuario usuario;
	
	@ManyToOne
	private Formulario formulario;
	
	@ManyToOne
	private PreguntaUno preguntaUno;

	public RespuestaUno() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	public boolean isResultado() {
		return resultado;
	}

	public void setResultado(boolean resultado) {
		this.resultado = resultado;
	}

	public int getOpcion() {
		return opcion;
	}

	public void setOpcion(int opcion) {
		this.opcion = opcion;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Formulario getFormulario() {
		return formulario;
	}

	public void setFormulario(Formulario formulario) {
		this.formulario = formulario;
	}

	public PreguntaUno getPreguntaUno() {
		return preguntaUno;
	}

	public void setPreguntaUno(PreguntaUno preguntaUno) {
		this.preguntaUno = preguntaUno;
	}

	@Override
	public String toString() {
		return "RespuestaUno [id=" + id + ", respuesta=" + respuesta + ", resultado=" + resultado + ", opcion=" + opcion
				+ ", usuario=" + usuario + ", formulario=" + formulario + ", preguntaUno=" + preguntaUno + "]";
	}
	
}