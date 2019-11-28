package com.eduq.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.eduq.model.dao.FirebaseDAO;
import com.eduq.model.entity.Aluno;
import com.eduq.model.entity.Boletim;
import com.eduq.model.entity.BoletimAluno;
import com.eduq.model.entity.BoletimAlunoComponente;
import com.eduq.model.entity.BoletimComponente;
import com.eduq.model.entity.BoletimComponenteFirebase;
import com.eduq.model.entity.BoletimFirebase;
import com.eduq.model.entity.Classe;
import com.eduq.model.entity.ComponenteCurricular;
import com.eduq.model.entity.Escola;
import com.eduq.model.entity.Professor;
import com.eduq.model.service.AlunoService;
import com.eduq.model.service.BoletimService;
import com.eduq.model.service.ChamadaService;
import com.eduq.model.service.ClasseService;
import com.eduq.model.service.ComponenteService;

@Controller
public class BoletimController {
	@Autowired
	private BoletimService service;
	
	@Autowired
	private ClasseService classeService;
	
	@Autowired
	private AlunoService alunoService;
	
	@Autowired
	private ComponenteService componenteService;
	
	@Autowired
	private ChamadaService chamadaService;
	
	@Autowired
	private FirebaseDAO firebase;
	
	@RequestMapping(value = "/getCriarBoletim", method = RequestMethod.GET)
	public String getCriarBoletim(Boletim boletim, HttpSession session, ModelMap model) {
		Escola escolaSessaoAtual = new Escola();
		List<Classe> classes = new ArrayList<Classe>();
		
		escolaSessaoAtual = (Escola) session.getAttribute("escolaLogada");
		classes = classeService.findByEscola(escolaSessaoAtual.getId());
		
		model.addAttribute("boletim", boletim);
		model.addAttribute("classes", classes);
		
		return "boletim/criarBoletim";
	}
	
	@PostMapping(value = "/criarBoletim")
	public String postCriarBoletim(Boletim boletim, HttpSession session, RedirectAttributes attr) {
		Escola escolaSessaoAtual = new Escola();
		String data = LocalDate.now().toString();
		
		escolaSessaoAtual = (Escola) session.getAttribute("escolaLogada");
		boletim.setIdEscola(escolaSessaoAtual.getId());
		boletim.setDataBoletim(data);
		boletim.setSituacao("Cursando");
		boletim.setIndividualCerado("Não");
		
		try {
			service.save(boletim);
			attr.addFlashAttribute("success","Boletim criado.");
		} catch (Exception e) {
			attr.addFlashAttribute("fail","Não foi possível criar o boletim, tente novamente.");
		}
		
		return "redirect:/getCriarBoletim";
	}
	
	@RequestMapping(value = "/getBoletinsEscola", method = RequestMethod.GET)
	public String getBoletinsEscola(HttpSession session, ModelMap model) {
		List<Boletim> boletins = new ArrayList<Boletim>(); 
		Escola escolaSessaoAtual = new Escola();
		
		escolaSessaoAtual = (Escola) session.getAttribute("escolaLogada");
		
		boletins = service.findByEscola(escolaSessaoAtual.getId());
		
		model.addAttribute("boletins", boletins);
		
		return "boletim/listaBoletins";
	}
	
	@GetMapping("/adicionarComponentes/{id}")
	public String getAdicionarComponente(@PathVariable("id") Long id, HttpSession session, ModelMap model) {
		Escola escolaSessaoAtual = new Escola();
		BoletimComponente boletimComponente = new BoletimComponente();
		Classe classe = new Classe();
		
		List<ComponenteCurricular> componentes = new ArrayList<ComponenteCurricular>();
		Boletim boletim = service.findById(id);
		
		escolaSessaoAtual = (Escola) session.getAttribute("escolaLogada");
		componentes = componenteService.buscarPorEscola(escolaSessaoAtual.getId());
//		componentes = componenteService.findByNotBoletim(escolaSessaoAtual.getId(), boletim.getId());
		classe = classeService.findById(boletim.getIdClasse());
		boletimComponente.setBoletimId(id);
		
		model.addAttribute("componentes", componentes);
		model.addAttribute("boletim", boletim);
		model.addAttribute("classe", classe);
		model.addAttribute("boletimComponente", boletimComponente);
		
		return "boletim/adicionarComponente";
	}
	
	@PostMapping("/addComponenteBoletim")
	public String postAdicionarComponente(BoletimComponente boletimComponente, RedirectAttributes attr) {
		
		try {
			service.saveComponente(boletimComponente);
			attr.addFlashAttribute("success","Componente adicionado.");
		} catch (Exception e) {
			attr.addFlashAttribute("fail","Não foi possível adicionar o componente, tente novamente.");
		}
		
		return "redirect:/getBoletinsEscola";
	}
	
	@GetMapping("/listarComponentes/{id}")
	public String getComponentesBoletim(@PathVariable("id") Long id, ModelMap model) {
		List<ComponenteCurricular> componentes = new ArrayList<ComponenteCurricular>();
		
		componentes = service.findByBoletim(id);
		
		model.addAttribute("componentes", componentes);
		
		return "boletim/listarComponentes";
	}
	
	@GetMapping("/boletins/removerComponente/{id}")
	public String removerComponenteBoletim(@PathVariable("id") Long id, ModelMap model, HttpSession session) {
		
		try {
			service.removeComponente(id);
			model.addAttribute("success", "Componente removido.");
		} catch (Exception e) {
			model.addAttribute("fail", "Não foi possível remover o componente solicitado.");
		}
		
		return getBoletinsEscola(session, model);
	}
	
	@GetMapping("/editarBoletim/{id}")
	public String preEditarBoletim(@PathVariable("id") Long id, HttpSession session, ModelMap model) {
		Escola escolaSessaoAtual = new Escola();
		Boletim boletim = new Boletim();
		List<Classe> classes = new ArrayList<Classe>();
		
		escolaSessaoAtual = (Escola) session.getAttribute("escolaLogada");
		classes = classeService.findByEscola(escolaSessaoAtual.getId());
		boletim = service.findById(id);
		
		model.addAttribute("classes", classes);
		model.addAttribute("boletim", boletim);
		
		return "boletim/criarBoletim";
	}
	
	@PostMapping("/editarBoletim")
	public String editarBoletim(Boletim boletim, ModelMap model, HttpSession session, RedirectAttributes attr) {
		
		try {
			service.updateBoletim(boletim);
			attr.addFlashAttribute("success", "Boletim atualizado.");
		} catch (Exception e) {
			attr.addFlashAttribute("success", "Não foi possível editar o boletim. Tente novamente.");
		}
		
		return "redirect:/getCriarBoletim";
	}
	
	@GetMapping("/boletins/excluir/{id}")
	public String excluirBoletim(@PathVariable("id") Long id, RedirectAttributes attr) {
		
		try {
			service.delete(id);
			attr.addFlashAttribute("success", "Boletim removido.");
		} catch (Exception e) {
			attr.addFlashAttribute("fail", "Não foi possível remover o boletim solicitado.");
		}
		
		return "redirect:/getBoletinsEscola";
	}
	
	@GetMapping("/aplicarBoletins/{id}")
	public String aplicarBoletins(@PathVariable("id") Long id, RedirectAttributes attr, HttpSession session) {
		Boletim boletim = service.findById(id);
		List<Object[]> alunos = alunoService.buscarPorClasseBoletim(boletim.getIdClasse());
		BoletimAluno boletimAluno = null;
		List<BoletimAluno> boletins = new ArrayList<BoletimAluno>();
		
		for (int i = 0; i < alunos.size(); i++) {
			boletimAluno = new BoletimAluno();
			Object[] result = alunos.get(i);
			
			boletimAluno.setBoletimId(id);
			boletimAluno.setAlunoId(Long.parseLong(result[0].toString()));
			
			boletins.add(boletimAluno);
		}
		
		try {
			service.saveAll(boletins);
			gerarComponentesIndividuais(boletim.getId());
			service.generatedBoletim(boletim.getId());
			attr.addFlashAttribute("success", "Boletins criados.");
		} catch (Exception e) {
			attr.addFlashAttribute("fail", "Não foi possível criar os boletins. Tente novamente.");
		}
		
		return "redirect:/getBoletinsEscola";
	}
	
	public void gerarComponentesIndividuais(Long boletimId) {
		List<Object[]> alunos = service.findBoletimAlunos(boletimId);
		List<Object[]> componentes = service.findComponentesBoletim(boletimId);
		List<BoletimAlunoComponente> boletinsIndividuais = new ArrayList<BoletimAlunoComponente>();
		BoletimAlunoComponente boletimAluno = null;
		BoletimAluno aluno = null;
		Object[] result, aux = null;
		
		for (int i = 0; i < alunos.size(); i++) {
			aluno = new BoletimAluno();
			
			result = alunos.get(i);
					
			aluno.setId(Long.parseLong(result[0].toString()));
			aluno.setBoletimId(Long.parseLong(result[1].toString()));
			aluno.setAlunoId(Long.parseLong(result[2].toString()));
			
			for (int j = 0; j < componentes.size(); j++) {
				boletimAluno = new BoletimAlunoComponente();
				
				aux = componentes.get(j);
				
				boletimAluno.setBoletimAlunoId(aluno.getId());
				boletimAluno.setBoletimComponenteId(Long.parseLong(aux[0].toString()));
				boletimAluno.setConceito("");
				boletimAluno.setFaltas(0);
				
				boletinsIndividuais.add(boletimAluno);
			}
		}
		
		service.saveAllBoletins(boletinsIndividuais);
	}
	
	@GetMapping("/getBoletimAluno/{id}")
	public String getBoletimAluno(@PathVariable("id") Long id, ModelMap model) {
		Aluno aluno = new Aluno();
		Classe classe = new Classe();
		ComponenteCurricular componente = null;
		Object[] result = null;
		List<ComponenteCurricular> componentes = new ArrayList<ComponenteCurricular>();
		
		aluno = alunoService.buscarPorId(id);
		classe = classeService.findByAluno(id);
		List<Object[]> faltas = chamadaService.getFaltasAluno(id);
		List<Object[]> aux = service.findAllComponentesBoletim(aluno.getId());
		
		for(int i = 0; i < aux.size(); i++) {
			componente = new ComponenteCurricular();
			
			result = aux.get(i);
			componente.setId(Long.parseLong(result[0].toString()));
			componente.setNome(result[2].toString());
			componente.setTipoAvaliacao(result[3].toString());
			componente.setCargaHoraria(result[4].toString());
			
			componentes.add(componente);
		}
		
		model.addAttribute("faltas", faltas);
		model.addAttribute("classe", classe);
		model.addAttribute("aluno", aluno);
		model.addAttribute("componentes", componentes);
		
		return "boletim/boletimAluno";
	}
	
	@PostMapping("/salvarBoletim")
	@ResponseBody
	public void salvarBoletim(@RequestBody String ary, HttpSession session, RedirectAttributes attr, ModelMap model) throws JSONException {
		ArrayList<String> stringArray = new ArrayList<String>();
		JSONArray jsonArray = new JSONArray(ary);
		JSONObject obj = null;
		BoletimAlunoComponente alunoComponente = null;
		List<BoletimAlunoComponente> listaAlunos = new ArrayList<BoletimAlunoComponente>();
		String id, notaAtual, nota = null;
		Double aux1, aux2, soma = null;
		
		for (int i = 0; i < jsonArray.length(); i++) {
			alunoComponente = new BoletimAlunoComponente();
			
			stringArray.add(jsonArray.getString(i));
	        obj = jsonArray.optJSONObject(i);
	        id = obj.getString("Id");
	        notaAtual = obj.getString("Name");
	        nota = obj.getString("Value");
	        
	        alunoComponente.setId(Long.parseLong(id));
	        
	        if(notaAtual.equals("MB") || notaAtual.equals("B") || notaAtual.equals("R")) {
		        alunoComponente.setConceito(notaAtual);
	        } else if(notaAtual.isEmpty()) {
	        	alunoComponente.setConceito(nota);
	        } else {
	        	aux1 = Double.parseDouble(notaAtual);
	        	aux2 = Double.parseDouble(nota);
	        	soma = aux1 + aux2;
	        	
	        	alunoComponente.setConceito(soma.toString());
	        }
	        
	        listaAlunos.add(alunoComponente);
	    }
		
		try {
			service.updateComponenteAlunoBoletins(listaAlunos);
			gerarBoletimFirebase(listaAlunos);
			attr.addFlashAttribute("success", "Boletim atualizado.");
		} catch (Exception e) {
			attr.addFlashAttribute("fail", "Não foi possível atualizar o boletim. Tente novamente.");
		}
	}
	
	private void gerarBoletimFirebase(List<BoletimAlunoComponente> listaAlunos) {
		BoletimAlunoComponente bac = listaAlunos.get(0);
		Object[] result, result2 = null;
		BoletimFirebase boletimFirebase = new BoletimFirebase();
		List<BoletimComponenteFirebase> listaComponentes = new ArrayList<BoletimComponenteFirebase>();
		BoletimComponenteFirebase bcf = null;
		
		List<Object[]> aux = service.findHalfBoletimFirebase(bac.getId());
		
		result = aux.get(0);
		
		boletimFirebase.setNomeAluno(result[0].toString());
		boletimFirebase.setMatrícula(result[1].toString());
		boletimFirebase.setBoletimId(Long.parseLong(result[2].toString()));
		boletimFirebase.setSituacao(result[3].toString());
		boletimFirebase.setBoletimAlunoId(Long.parseLong(result[4].toString()));
		boletimFirebase.setAlunoId(Long.parseLong(result[5].toString()));
		
		List<Object[]> aux2 = service.findCompleteBoletimFirebase(boletimFirebase.getBoletimAlunoId());
		
		for(int i = 0; i < aux2.size(); i++) {
			bcf = new BoletimComponenteFirebase(); 
			result2 = aux2.get(i);
			
			bcf.setNomeComponente(result2[0].toString());
			bcf.setMediaAprovacao(result2[1].toString());
			bcf.setConceito(result2[2].toString());
			
			listaComponentes.add(bcf);
		}
		
		boletimFirebase.setComponentes(listaComponentes);
		
		firebase.saveBoletim(boletimFirebase);
	}
	
	@GetMapping("/getBoletimAlunoProfessor/{id}")
	public String getBoletimAlunoProfessor(@PathVariable("id") Long id, ModelMap model, HttpSession session) {
		Aluno aluno = new Aluno();
		Classe classe = new Classe();
		ComponenteCurricular componente = null;
		Object[] result = null;
		List<ComponenteCurricular> componentes = new ArrayList<ComponenteCurricular>();
		
		aluno = alunoService.buscarPorId(id);
		classe = classeService.findByAluno(id);
		List<Object[]> faltas = chamadaService.getFaltasAluno(id);
		List<Object[]> aux = service.findAllComponentesBoletim(aluno.getId());
		
		for(int i = 0; i < aux.size(); i++) {
			componente = new ComponenteCurricular();
			
			result = aux.get(i);
			componente.setId(Long.parseLong(result[0].toString()));
			componente.setNome(result[2].toString());
			componente.setTipoAvaliacao(result[3].toString());
			componente.setCargaHoraria(result[4].toString());
			
			componentes.add(componente);
		}
		
		model.addAttribute("faltas", faltas);
		model.addAttribute("classe", classe);
		model.addAttribute("aluno", aluno);
		model.addAttribute("componentes", componentes);
		
		return "boletim/boletimAlunoProfessor";
	}
	
	@PostMapping("/salvarBoletimProfessor")
	@ResponseBody
	public void salvarBoletimProfessor(@RequestBody String ary, RedirectAttributes attr) throws JSONException {
		ArrayList<String> stringArray = new ArrayList<String>();
		JSONArray jsonArray = new JSONArray(ary);
		JSONObject obj = null;
		BoletimAlunoComponente alunoComponente = null;
		List<BoletimAlunoComponente> listaAlunos = new ArrayList<BoletimAlunoComponente>();
		String id, notaAtual, nota = null;
		Double aux1, aux2, soma = null;
		
		for (int i = 0; i < jsonArray.length(); i++) {
			alunoComponente = new BoletimAlunoComponente();
			
			stringArray.add(jsonArray.getString(i));
	        obj = jsonArray.optJSONObject(i);
	        id = obj.getString("Id");
	        notaAtual = obj.getString("Name");
	        nota = obj.getString("Value");
	        
	        alunoComponente.setId(Long.parseLong(id));
	        
	        if(notaAtual.equals("MB") || notaAtual.equals("B") || notaAtual.equals("R")) {
		        alunoComponente.setConceito(notaAtual);
	        } else if(notaAtual.isEmpty()) {
	        	alunoComponente.setConceito(nota);
	        } else {
	        	aux1 = Double.parseDouble(notaAtual);
	        	aux2 = Double.parseDouble(nota);
	        	soma = aux1 + aux2;
	        	
	        	alunoComponente.setConceito(soma.toString());
	        }
	        
	        listaAlunos.add(alunoComponente);
	    }
		
		try {
			service.updateComponenteAlunoBoletins(listaAlunos);
			gerarBoletimFirebase(listaAlunos);
			attr.addFlashAttribute("success", "Boletim atualizado.");
		} catch (Exception e) {
			attr.addFlashAttribute("fail", "Não foi possível atualizar o boletim. Tente novamente.");
		}
	}
	
	@RequestMapping(value = "/getBoletinsProfessor", method = RequestMethod.GET)
	public String getBoletinsProfessor(HttpSession session, ModelMap model) {
		List<Boletim> boletins = new ArrayList<Boletim>(); 
		Professor sessaoAtual = new Professor();
		
		sessaoAtual = (Professor) session.getAttribute("professorLogado");
		
		boletins = service.findByEscola(sessaoAtual.getEscolaId());
		
		model.addAttribute("boletins", boletins);
		
		return "boletim/listaBoletinsProfessor";
	}
	
	@GetMapping("/listarComponentesProfessor/{id}")
	public String getComponentesBoletimProfessor(@PathVariable("id") Long id, ModelMap model) {
		List<ComponenteCurricular> componentes = new ArrayList<ComponenteCurricular>();
		
		componentes = service.findByBoletim(id);
		
		model.addAttribute("componentes", componentes);
		
		return "boletim/listarComponentesProfessor";
	}
}
