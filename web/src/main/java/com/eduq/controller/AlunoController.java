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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.eduq.model.entity.Aluno;
import com.eduq.model.entity.Escola;
import com.eduq.model.entity.Professor;
import com.eduq.model.service.AlunoService;

@Controller
public class AlunoController {
	
	@Autowired
	private AlunoService service;
	
	@PostMapping("/aluno/cadastro")
	public String cadastrarAluno(Aluno aluno, HttpSession session, RedirectAttributes attr) {
		Escola escolaSessaoAtual = new Escola();
		String codigoGeradoEscola;
		
		escolaSessaoAtual = (Escola) session.getAttribute("escolaLogada");
		codigoGeradoEscola = service.gerarCodigoEscola(escolaSessaoAtual, aluno.getCpf());

		aluno.setEscolaId(escolaSessaoAtual.getId());
		aluno.setCodigoGeradoEscola(codigoGeradoEscola);

		try {
			service.salvar(aluno);
			attr.addFlashAttribute("success", "Aluno matriculado.");
			return "redirect:/cadastrarAluno";
		} catch (Exception e) {
			attr.addFlashAttribute("fail", "Não foi possível matricular este aluno.");
			return "redirect:/cadastrarAluno";
		}
	}
	
	@RequestMapping(value = "/listarAlunos", method = RequestMethod.GET)
	public String listarAlunos(ModelMap model, HttpSession session) {
		List<Aluno> alunos = new ArrayList<Aluno>(); 
		Escola escolaSessaoAtual = new Escola();
		
		escolaSessaoAtual = (Escola) session.getAttribute("escolaLogada");
		
		alunos = service.buscarPorEscola(escolaSessaoAtual.getId());
		
		model.addAttribute("alunos", alunos);
		
		return "aluno/listaAlunos";
	}
	
	@GetMapping("/editarAluno/{id}")
	public String preEditarAluno(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("aluno", service.buscarPorId(id));
		return "aluno/cadastroAluno";
	}
	
	@PostMapping("/editarAluno")
	public String editarAluno(Aluno aluno, HttpSession session, RedirectAttributes attr) {
	
		try {
			service.updateAluno(aluno);
			attr.addFlashAttribute("success", "Aluno atualizado.");
		} catch (Exception e) {
			attr.addFlashAttribute("fail", "Não foi possível atualizar o aluno solicitado.");
		}
		
		return "redirect:/cadastrarAluno";
	}
	
	@GetMapping("/alunos/excluir/{id}")
	public String excluirAluno(@PathVariable("id") Long id, ModelMap model, HttpSession session) {
		try {
			service.excluir(id);
			model.addAttribute("success", "Aluno removido.");
		} catch (Exception e) {
			model.addAttribute("fail", "Não foi possível remover o aluno solicitado.");
		}
		
		return listarAlunos(model, session);
	}
	
	@GetMapping("/alunos/buscar/nome")
	public String buscarAluno(@RequestParam("nome") String nome, ModelMap model, HttpSession session) {
		Escola escolaSessaoAtual = new Escola();
		
		escolaSessaoAtual = (Escola) session.getAttribute("escolaLogada");
		
		model.addAttribute("alunos", service.buscarPorNome(nome, escolaSessaoAtual.getId()));
		
		return "aluno/listaAlunos";
	}
	
	@GetMapping("/getAlunosProfessor")
	public String listaAlunosProfessor(ModelMap model, HttpSession session) {
		List<Aluno> alunos = new ArrayList<Aluno>();
		Professor sessaoAtual = new Professor();
		
		sessaoAtual = (Professor) session.getAttribute("professorLogado");
		
		alunos = service.buscarPorProfessor(sessaoAtual.getId());
		
		model.addAttribute("alunos", alunos);
		
		return "aluno/listaAlunosProfessor";
	}
	
	@GetMapping("/alunos/professor/buscar/nome")
	public String buscarAlunoProfessor(@RequestParam("nome") String nome, ModelMap model, HttpSession session) {
		Professor sessaoAtual = new Professor();
		
		sessaoAtual = (Professor) session.getAttribute("professorLogado");
		
		model.addAttribute("alunos", service.buscarPorNomeProfessor(nome, sessaoAtual.getId()));
		
		return "aluno/listaAlunosProfessor";
	}
}
