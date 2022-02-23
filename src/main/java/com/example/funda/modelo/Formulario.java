package com.example.funda.modelo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

@Entity
@NamedQuery(name="Formulario.findAll", query="SELECT f FROM Formulario f")
public class Formulario implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name="administrador")
	private Administrador administradorBean;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "formulario_preguntaUno", joinColumns = @JoinColumn(name = "formulario_id"), inverseJoinColumns = @JoinColumn(name = "preguntaUno_id"))
	private List<PreguntaUno> preguntaUnoB;
	
	@ManyToOne
	@JoinColumn(name="PreguntaDos")
	private PreguntaDos preguntaDosB;
	
	@Column
	private boolean previos;

	public Formulario() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Administrador getAdministradorBean() {
		return administradorBean;
	}

	public void setAdministradorBean(Administrador administradorBean) {
		this.administradorBean = administradorBean;
	}

	public List<PreguntaUno> getPreguntaUnoB() {
		return preguntaUnoB;
	}

	public void setPreguntaUnoB(List<PreguntaUno> preguntaUnoB) {
		this.preguntaUnoB = preguntaUnoB;
	}

	public PreguntaDos getPreguntaDosB() {
		return preguntaDosB;
	}

	public void setPreguntaDosB(PreguntaDos preguntaDosB) {
		this.preguntaDosB = preguntaDosB;
	}

	public boolean isPrevios() {
		return previos;
	}

	public void setPrevios(boolean previos) {
		this.previos = previos;
	}
}
