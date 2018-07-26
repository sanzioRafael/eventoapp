package com.eventoapp.eventoapp.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.eventoapp.eventoapp.model.Convidado;
import com.eventoapp.eventoapp.model.Evento;

public interface ConvidadoRepository extends CrudRepository<Convidado, String> {
	
	public List<Convidado> findByEvento(Evento evento);
	public Convidado findByRg(String rg);
	
}
