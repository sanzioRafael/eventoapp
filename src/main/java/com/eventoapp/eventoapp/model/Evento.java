package com.eventoapp.eventoapp.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;

@Data
@Entity
public class Evento implements Serializable {
	
	private static final long serialVersionUID = -7998395328248723755L;

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long codigo;
	
	@NotEmpty
	private String nome;
	
	@NotEmpty
	private String local;
	
	@NotEmpty
	private String data;
	
	@NotEmpty
	private String horario;
	
	@OneToMany
	private List<Convidado> convidados;
	
}
