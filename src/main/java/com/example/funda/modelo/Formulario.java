package com.example.funda.modelo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery(name="Formulario.findAll", query="SELECT f FROM Formulario f")
public class Formulario implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name="preguntaUno")
	private PreguntaUno preguntaUnoBean;
	
	@ManyToOne
	@JoinColumn(name="administrador")
	private Administrador administradorBean;

	public Formulario() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public PreguntaUno getPreguntaUnoBean() {
		return preguntaUnoBean;
	}

	public void setPreguntaUnoBean(PreguntaUno preguntaUnoBean) {
		this.preguntaUnoBean = preguntaUnoBean;
	}

	public Administrador getAdministradorBean() {
		return administradorBean;
	}

	public void setAdministradorBean(Administrador administradorBean) {
		this.administradorBean = administradorBean;
	}
}
