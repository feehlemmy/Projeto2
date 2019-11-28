package com.eduq.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.eduq.model.entity.ComponenteCurricular;
import com.eduq.model.entity.Escola;
import com.eduq.model.service.ComponenteService;

@Controller
public class ComponenteController {
	@Autowired
	private ComponenteService service;
	
	@PostMapping("/componente/cadastro")
	public String cadastrarComponente(ComponenteCurricular componente, HttpSession session, RedirectAttributes attr) {
		Escola escolaSessaoAtual = new Escola();
		
		escolaSessaoAtual = (Escola) session.getAttribute("escolaLogada");
		componente.setEscolaId(escolaSessaoAtual.getId());
		
		try {
			service.salvar(componente);
			attr.addFlashAttribute("success", "Componente cadastrado.");
			return "redirect:/cadastrarComponente";
		} catch (Exception e) {
			attr.addFlashAttribute("fail", "Não foi possível cadastrar o componente solicitado.");
		}
		
		return "redirect:/";
	}
	
	@RequestMapping(value = "/listarComponentes", method = RequestMethod.GET)
	public String listarComponentes(ModelMap model, HttpSession session) {
		List<ComponenteCurricular> componentes = new ArrayList<ComponenteCurricular>(); 
		Escola escolaSessaoAtual = new Escola();
		
		escolaSessaoAtual = (Escola) session.getAttribute("escolaLogada");
		
		componentes = service.buscarPorEscola(escolaSessaoAtual.getId());
		
		model.addAttribute("componentes", componentes);
		
		return "componente/listaComponentes";
	}
	
	@GetMapping("/componentes/excluir/{id}")
	public String excluirComponente(@PathVariable("id") Long id, ModelMap model, HttpSession session) {
		try {
			service.excluir(id);
			model.addAttribute("success", "Componente removido.");
		} catch (Exception e) {
			model.addAttribute("fail", "Não foi possível remover o componente solicitado.");
		}
		
		return listarComponentes(model, session);
	}
	
	@GetMapping("/editarComponente/{id}")
	public String preEditarComponente(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("componenteCurricular", service.buscarPorId(id));
		return "componente/cadastroComponente";
	}
	
	@PostMapping("/editarComponente")
	public String editarComponente(ComponenteCurricular componente, HttpSession session, RedirectAttributes attr) {
		Escola escolaSessaoAtual = new Escola();
		
		escolaSessaoAtual = (Escola) session.getAttribute("escolaLogada");
		componente.setEscolaId(escolaSessaoAtual.getId());
		
		try {
			service.editar(componente);
			attr.addFlashAttribute("success", "Componente atualizado.");
		} catch (Exception e) {
			attr.addFlashAttribute("fail", "Não foi possível atualizar o componente solicitado.");
		}
		
		return "redirect:/cadastrarComponente";
	}
}
