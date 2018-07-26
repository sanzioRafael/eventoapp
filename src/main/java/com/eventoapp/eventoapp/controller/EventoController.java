package com.eventoapp.eventoapp.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.eventoapp.eventoapp.model.Convidado;
import com.eventoapp.eventoapp.model.Evento;
import com.eventoapp.eventoapp.repository.ConvidadoRepository;
import com.eventoapp.eventoapp.repository.EventoRepository;

@Controller
public class EventoController {

	@Autowired
	private EventoRepository eventoRepository;

	@Autowired
	private ConvidadoRepository convidadoRepository;

	@RequestMapping(value = "/cadastrarEvento", method = RequestMethod.GET)
	public String form() {
		return "evento/formEvento";
	}

	@RequestMapping(value = "/cadastrarEvento", method = RequestMethod.POST)
	public String form(@Valid Evento evento, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos!");
		} else {
			eventoRepository.save(evento);
			attributes.addFlashAttribute("mensagem", "Evento cadastrado com sucesso!");
		}

		return "redirect:/cadastrarEvento";
	}

	@RequestMapping("/eventos")
	public ModelAndView listaEventos() {
		ModelAndView mv = new ModelAndView("index");
		Iterable<Evento> eventos = eventoRepository.findAll();

		mv.addObject("eventos", eventos);

		return mv;
	}

	@RequestMapping(value = "/{codigo}", method = RequestMethod.GET)
	public ModelAndView detalhesEvento(@PathVariable("codigo") Long codigo) {
		Evento evento = eventoRepository.findByCodigo(codigo);
		List<Convidado> convidados = convidadoRepository.findByEvento(evento);
		ModelAndView mv = new ModelAndView("evento/detalhesEvento");

		mv.addObject("evento", evento);
		mv.addObject("convidados", convidados);
		System.out.println(convidados != null ? convidados.size() : 0);

		return mv;
	}

	@RequestMapping("/deletarEvento")
	public String deletarEvento(Long codigo) {
		Evento evento = eventoRepository.findByCodigo(codigo);

		eventoRepository.delete(evento);

		return "redirect:/eventos";
	}

	@RequestMapping(value = "/{codigo}", method = RequestMethod.POST)
	public String detalhesEventoPost(@PathVariable("codigo") Long codigo, @Valid Convidado convidado,
			BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos!");
		} else {
			Evento evento = eventoRepository.findByCodigo(codigo);

			convidado.setEvento(evento);
			convidadoRepository.save(convidado);
			attributes.addFlashAttribute("mensagem", "Convidado Adicionado com sucesso.");
		}

		return "redirect:/{codigo}";
	}

	@RequestMapping("/deletarConvidado")
	public String deletarConvidado(String rg) {
		Convidado convidado = convidadoRepository.findByRg(rg);
		
		convidadoRepository.delete(convidado);
		
		return "redirect:/" + convidado.getEvento().getCodigo();
	}

}
