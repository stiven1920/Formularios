package com.example.funda.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class RespuestaDos {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column
	private boolean resultadoUno;
	
	@Column
	private boolean resultadoDos;
	
	@Column
	private boolean resultadoTres;
	
	@ManyToOne
	private Usuario usuario;
	
	@ManyToOne
	private Formulario formulario;
	
	@ManyToOne
	private PreguntaDos preguntaDos;
	
	@Transient
	private String ru;
	
	@Transient
	private String rd;
	
	@Transient
	private String rt;
	
	public RespuestaDos() {
		super();
		this.resultadoUno = false;
		this.resultadoDos = false;
		this.resultadoTres = false;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isResultadoUno() {
		return resultadoUno;
	}

	public void setResultadoUno(boolean resultadoUno) {
		this.resultadoUno = resultadoUno;
	}

	public boolean isResultadoDos() {
		return resultadoDos;
	}

	public void setResultadoDos(boolean resultadoDos) {
		this.resultadoDos = resultadoDos;
	}

	public boolean isResultadoTres() {
		return resultadoTres;
	}

	public void setResultadoTres(boolean resultadoTres) {
		this.resultadoTres = resultadoTres;
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

	public PreguntaDos getPreguntaDos() {
		return preguntaDos;
	}

	public void setPreguntaDos(PreguntaDos preguntaDos) {
		this.preguntaDos = preguntaDos;
	}

	public String getRu() {
		return ru;
	}

	public void setRu(String ru) {
		this.ru = ru;
	}

	public String getRd() {
		return rd;
	}

	public void setRd(String rd) {
		this.rd = rd;
	}

	public String getRt() {
		return rt;
	}

	public void setRt(String rt) {
		this.rt = rt;
	}
}
