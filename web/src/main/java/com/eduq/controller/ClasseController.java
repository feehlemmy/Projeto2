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

import com.eduq.model.entity.Aluno;
import com.eduq.model.entity.Classe;
import com.eduq.model.entity.Escola;
import com.eduq.model.entity.Pessoa;
import com.eduq.model.entity.Professor;
import com.eduq.model.service.AlunoService;
import com.eduq.model.service.ClasseService;
import com.eduq.model.service.ProfessorService;

@Controller
public class ClasseController {
	
	@Autowired
	private ClasseService service;
	
	@Autowired
	private ProfessorService professorService;
	
	@Autowired
	private AlunoService alunoService;
	
	@PostMapping("/classe/cadastro")
	public String cadastrarClasse(Classe classe, HttpSession session, RedirectAttributes attr) {
		Escola escolaSessaoAtual = new Escola();
		
		escolaSessaoAtual = (Escola) session.getAttribute("escolaLogada");
		classe.setEscolaId(escolaSessaoAtual.getId());
		
		try {
			service.save(classe);	
			attr.addFlashAttribute("success", "Classe cadastrada.");
			return "redirect:/cadastrarClasse";
		} catch (Exception e) {
			attr.addFlashAttribute("fail", "Não foi possível cadastrar a classe solicitada.");
		}
		
		return "redirect:/";
	}
	
	@RequestMapping(value = "/listarClasses", method = RequestMethod.GET)
	public String listarClasses(ModelMap model, HttpSession session) {
		List<Classe> classes = new ArrayList<Classe>(); 
		Escola escolaSessaoAtual = new Escola();
		
		escolaSessaoAtual = (Escola) session.getAttribute("escolaLogada");
		
		classes = service.findByEscola(escolaSessaoAtual.getId());
		
		model.addAttribute("classes", classes);
		
		return "classe/listaClasses";
	}
	
	@RequestMapping(value = "/listarClassesProfessor", method = RequestMethod.GET)
	public String listarClassesProfessor(ModelMap model, HttpSession session) {
		List<Classe> classes = new ArrayList<Classe>(); 
		Professor professorSessaoAtual = new Professor();
		
		professorSessaoAtual = (Professor) session.getAttribute("professorLogado");
		
		classes = service.findByProfessor(professorSessaoAtual.getEscolaId(), professorSessaoAtual.getId());
		
		model.addAttribute("classes", classes);
		
		return "classe/listaClassesProfessor";
	}
	
	@GetMapping("/classes/excluir/{id}")
	public String excluirClasse(@PathVariable("id") Long id, ModelMap model, HttpSession session) {
		
		try {
			service.delete(id);
			model.addAttribute("success", "Classe removida.");
		} catch (Exception e) {
			model.addAttribute("fail", "Não foi possível remover a classe solicitada.");
		}
		
		
		return listarClasses(model, session);
	}
	
	@GetMapping("/editarClasse/{id}")
	public String preEditarClasse(@PathVariable("id") Long id, HttpSession session, ModelMap model) {
		List<Pessoa> professores = new ArrayList<Pessoa>();
		Escola escolaSessaoAtual = new Escola();
		
		escolaSessaoAtual = (Escola) session.getAttribute("escolaLogada");
		professores = professorService.buscarProfessorPorEscola(escolaSessaoAtual.getId());
		
		model.addAttribute("classe", service.findById(id));
		model.addAttribute("professores", professores);
		
		return "classe/cadastroClasse";
	}
	
	@PostMapping("/editarClasse")
	public String editarClasse(Classe classe, HttpSession session, RedirectAttributes attr) {
		Escola escolaSessaoAtual = new Escola();
		
		escolaSessaoAtual = (Escola) session.getAttribute("escolaLogada");
		classe.setEscolaId(escolaSessaoAtual.getId());
		
		try {
			service.update(classe);
			attr.addFlashAttribute("success", "Classe atualizada.");
		} catch (Exception e) {
			attr.addFlashAttribute("fail", "Não foi possível atualizar a classe solicitado.");
		}
		
		return "redirect:/cadastrarClasse";
	}
	
	@RequestMapping(value = "/listarAlunosClasse/{id}", method = RequestMethod.GET)
	public String listarAlunos(@PathVariable("id") Long id, ModelMap model, HttpSession session) {
		List<Aluno> alunos = new ArrayList<Aluno>(); 
				
		alunos = alunoService.buscarPorClasse(id);
		
		model.addAttribute("alunos", alunos);
		
		return "classe/listaAlunosClasse";
	}
	
	@RequestMapping(value = "/listarAlunosClasseProfessor/{id}", method = RequestMethod.GET)
	public String listarAlunosProfessor(@PathVariable("id") Long id, ModelMap model, HttpSession session) {
		List<Aluno> alunos = new ArrayList<Aluno>(); 
				
		alunos = alunoService.buscarPorClasse(id);
		
		model.addAttribute("alunos", alunos);
		
		return "classe/listaAlunosClasseProfessor";
	}
	
	@GetMapping("/adicionarAlunosClasse/{id}")
	public String getAdicionarAlunosClasse(@PathVariable("id") Long id, ModelMap model, HttpSession session) {
		Escola escolaSessaoAtual = new Escola();
		
		escolaSessaoAtual = (Escola) session.getAttribute("escolaLogada");
		
		model.addAttribute("classe", service.findById(id));
		model.addAttribute("alunos", alunoService.buscarPorSemClasse(escolaSessaoAtual.getId()));
				
		return "classe/alunoClasse";
	}
	
	@PostMapping("/addAlunosClasse")
	public String postAdicionarAlunosClasse(Classe classe, HttpSession session, RedirectAttributes attr, ModelMap model) {
		Escola escolaSessaoAtual = new Escola();
		
		escolaSessaoAtual = (Escola) session.getAttribute("escolaLogada");
		
		service.updateAlunos(classe);
		
		model.addAttribute("classe", classe);
		model.addAttribute("alunos", alunoService.buscarPorSemClasse(escolaSessaoAtual.getId()));
		model.addAttribute("success", "Classe atualizada.");
		
		return "classe/alunoClasse";
	}
	
	@GetMapping("/classe/removerAluno/{id}")
	public String removerAlunoClasse(@PathVariable("id") Long id, ModelMap model, HttpSession session) {
		
		try {
			service.removeAluno(id);
			model.addAttribute("success", "Aluno removido.");
		} catch (Exception e) {
			model.addAttribute("fail", "Não foi possível remover o aluno solicitado.");
		}
		
		return listarClasses(model, session);
	}
}
