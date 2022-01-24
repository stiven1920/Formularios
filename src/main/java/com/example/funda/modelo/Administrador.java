package com.example.funda.modelo;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;


@Entity
@NamedQuery(name="Administrador.findAll", query="SELECT a FROM Administrador a")
public class Administrador implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Length(min = 2, max = 40, message="El nombre acepta datos desde los 2 hasta los 40 caracteres")
	@NotBlank(message = "El nombre es obligatorio")
	private String nombre;

	@Length(min = 3, max = 40, message="El apellido acepta datos desde los 3 hasta los 40 caracteres")
	@NotBlank(message = "El apellido es obligatorio")
	private String apellido;

	@NotBlank(message = "El email es obligatorio")
	@Pattern(regexp = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@[a-z0-9-]+(.[a-z0-9-]+)*(.[a-z]{2,4})$", message = "El email debe tener un formato parecido a abc@MAIL.xyz")
	@Column(unique = true)
	private String correo;

	@NotBlank(message = "La direccion es obligatoria")
	@Length(min = 5, max = 35, message = "En la direccion solo se permite una leve descripcion desde 5 caracteres hasta 35")
	private String direccion;

	@Column(name = "fecha_nacimiento")
	@NotNull(message = "La fecha es obligatoria")
	@DateTimeFormat(pattern = "dd/mm/yyyy")
	private String fechaNacimiento;

	@Length(min = 7, max = 10, message="Ingresa un numero de telefono valido, se aceptan fijos (7 cifras) o mobiles (10 cifras)")
	@NotNull(message = "El telefono es obligatorio")
	private String telefono;

	@Length(min = 6)
	@NotBlank(message = "La contrasena es obligatoria")
	@Column(length = 255)
	private String contrasena;
	
	@OneToMany(mappedBy = "administradorBean", cascade = CascadeType.REMOVE)
	private List<Formulario> formularios;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "authorities_administradores", joinColumns = @JoinColumn(name = "admin_id"), inverseJoinColumns = @JoinColumn(name = "authority_id"))
	private Set<Authority> authority;

	public Administrador() {
		super();
	}
	
	public Administrador(Usuario usuario) {
		this.setId(usuario.getId());
		this.setNombre(usuario.getNombre());
		this.setApellido(usuario.getApellido());
		this.setTelefono(usuario.getTelefono());
		this.setCorreo(usuario.getCorreo());
		this.setDireccion(usuario.getDireccion());
		this.setFechaNacimiento(usuario.getFechaNacimiento());
		this.setContrasena(usuario.getContrasena());
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	
	public List<Formulario> getFormularios() {
		return formularios;
	}

	public void setFormularios(List<Formulario> formularios) {
		this.formularios = formularios;
	}

	public Set<Authority> getAuthority() {
		return authority;
	}

	public void setAuthority(Set<Authority> authority) {
		this.authority = authority;
	}
}