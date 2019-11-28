package com.eduq.controller;

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
import com.eduq.model.entity.AlunoChamada;
import com.eduq.model.entity.Chamada;
import com.eduq.model.entity.Classe;
import com.eduq.model.entity.Escola;
import com.eduq.model.entity.Professor;
import com.eduq.model.service.AlunoService;
import com.eduq.model.service.ChamadaService;

@Controller
public class ChamadaController {
	
	@Autowired
	private ChamadaService service;
	
	@Autowired
	private AlunoService alunoService;
	
	@Autowired
	private FirebaseDAO firebase;
	
	@PostMapping("/escola/novaChamada")
	public String cadastrarChamada(Chamada chamada, HttpSession session, RedirectAttributes attr) {
		Escola escolaSessaoAtual = new Escola();
		
		escolaSessaoAtual = (Escola) session.getAttribute("escolaLogada");
		chamada.setIdEscola(escolaSessaoAtual.getId());
		chamada.setRealizada("Não");
		
		try {
			service.salvar(chamada);
			attr.addFlashAttribute("success", "Chamada cadastrada.");
			return "redirect:/getChamadasEscola";
		} catch (Exception e) {
			attr.addFlashAttribute("fail", "Não foi possível cadastrar a chamada. Tente novamente.");
		}
		
		return "redirect:/getChamadasEscola";
	}
	
	@PostMapping("/professor/novaChamada")
	public String cadastrarChamadaProfessor(Chamada chamada, HttpSession session, RedirectAttributes attr) {
		Professor professorSessaoAtual = new Professor();
		
		professorSessaoAtual = (Professor) session.getAttribute("professorLogado");
		chamada.setIdEscola(professorSessaoAtual.getEscolaId());
		chamada.setRealizada("Não");
		
		try {
			service.salvar(chamada);
			attr.addFlashAttribute("success", "Chamada cadastrada.");
			return "redirect:/getChamadasNaoRealizadasProfessor";
		} catch (Exception e) {
			attr.addFlashAttribute("fail", "Não foi possível cadastrar a chamada. Tente novamente.");
		}
		
		return "redirect:/getChamadasProfessor";
	}
	
	@GetMapping("/realizarChamada/{id}")
	public String preRealizarChamada(@PathVariable("id") Long id, ModelMap model) {
		List<Aluno> alunos = new ArrayList<Aluno>();
		Chamada chamada = new Chamada();
		Classe classe = new Classe();
		
		chamada = service.buscarPorId(id);
		classe.setId(chamada.getIdClasse());
		alunos = alunoService.buscarPorClasse(classe.getId());
		
		model.addAttribute("chamada", chamada);
		model.addAttribute("alunos", alunos);
		
		return "chamada/realizarChamada";
	}
	
	@GetMapping("/realizarChamadaProfessor/{id}")
	public String preRealizarChamadaProfessor(@PathVariable("id") Long id, ModelMap model) {
		List<Aluno> alunos = new ArrayList<Aluno>();
		Chamada chamada = new Chamada();
		
		chamada = service.buscarPorId(id);
		alunos = alunoService.buscarPorClasse(chamada.getIdClasse());
		
		model.addAttribute("chamada", chamada);
		model.addAttribute("alunos", alunos);
		
		return "chamada/realizarChamadaProfessor";
	}
	
	@PostMapping("/salvarChamada")
	@ResponseBody
	public void salvarChamada(@RequestBody String ary, Chamada chamada, HttpSession session, RedirectAttributes attr, ModelMap model) throws JSONException {
		ArrayList<String> stringArray = new ArrayList<String>();
		JSONArray jsonArray = new JSONArray(ary);
		JSONObject obj = null;
		AlunoChamada aluno = null;
		List<AlunoChamada> listaAlunos = new ArrayList<AlunoChamada>();
		String presenca, idChamada, idAluno = null;
		
		for (int i = 0; i < jsonArray.length(); i++) {
	        aluno = new AlunoChamada();
			stringArray.add(jsonArray.getString(i));
	        obj = jsonArray.optJSONObject(i);
	        idChamada = obj.getString("Id");
	        idAluno = obj.getString("Name");
	        presenca = obj.getString("Value");
	        
	        if(presenca.intern() == "Ausente") {
	        	aluno.setAlunoId(Long.parseLong(idAluno));
	        	aluno.setChamada_id(Long.parseLong(idChamada));
	        	aluno.setFalta(1);
	        	aluno.setStatus("Ausente");
	        	chamada.setId(Long.parseLong(idChamada));
	        } else {
	        	aluno.setAlunoId(Long.parseLong(idAluno));
	        	aluno.setChamada_id(Long.parseLong(idChamada));
	        	aluno.setFalta(0);
	        	aluno.setStatus("Presente");
	        	chamada.setId(Long.parseLong(idChamada));
	        }
	        
	        listaAlunos.add(aluno);
	    }
		
		try {
			service.salvarTodos(listaAlunos);
			chamada = service.buscarPorId(chamada.getId());
			chamada.setRealizada("Sim");
			firebase.saveChamada(chamada);
			service.finalizarChamada(chamada.getId());
			attr.addFlashAttribute("success", "Chamada realizada.");
		} catch (Exception e) {
			attr.addFlashAttribute("fail", "Não foi possível cadastrar a chamada. Tente novamente.");
		}
	}
	
	@PostMapping(value = "/escola/editarChamada")
	public String postEditarChamada(Chamada chamada, HttpSession session, RedirectAttributes attr) {
		Escola escolaSessaoAtual = new Escola();
		Chamada aux = new Chamada();
		
		escolaSessaoAtual = (Escola) session.getAttribute("escolaLogada");
		aux = service.buscarPorId(chamada.getId());
		
		chamada.setIdEscola(escolaSessaoAtual.getId());
		chamada.setRealizada(aux.getRealizada());
		
		try {
			service.editar(chamada);
			attr.addFlashAttribute("success","Chamada salva.");
		} catch (Exception e) {
			attr.addFlashAttribute("fail","Não foi possível editar a chamada, tente novamente.");
		}
		
		return "redirect:/getChamadasEscola";
	}
	
	@PostMapping(value = "/professor/editarChamada")
	public String postEditarChamadaProfessor(Chamada chamada, HttpSession session, RedirectAttributes attr) {
		Professor professorSessaoAtual = new Professor();
		Chamada aux = new Chamada();
		
		professorSessaoAtual = (Professor) session.getAttribute("professorLogado");
		aux = service.buscarPorId(chamada.getId());
		
		chamada.setIdEscola(professorSessaoAtual.getEscolaId());
		chamada.setRealizada(aux.getRealizada());
		
		try {
			service.editar(chamada);
			attr.addFlashAttribute("success","Chamada salva.");
		} catch (Exception e) {
			attr.addFlashAttribute("fail","Não foi possível editar a chamada, tente novamente.");
		}
		
		return "redirect:/getChamadasProfessor";
	}
	
	@GetMapping(value = "/chamadas/excluir/{id}")
	public String excluirChamada(@PathVariable("id") Long id, RedirectAttributes attr, HttpSession session) {
		
		try {
			service.excluir(id);
			attr.addFlashAttribute("success", "Chamada excluída!");
		} catch (Exception e) {
			attr.addFlashAttribute("fail", "Não foi possível excluir a chamada. Tente novamente.");
		}
		
		return "redirect:/getChamadasEscola";
	}
	
	@RequestMapping(value = "/chamada/vizualizarChamada/{id}", method = RequestMethod.GET)
	public String visualizarChamada(@PathVariable("id") Long id, RedirectAttributes attr, ModelMap model) {
		List<Object[]> aux = service.buscarPorChamada(id);
		List<Aluno> alunos = new ArrayList<>();
		Aluno aluno = null;
        Object[] result;
        
		try {
			for(int i = 0; i < aux.size(); i++) {
				aluno = new Aluno();
				result = aux.get(i);
				
				aluno.setCodigoGeradoEscola(result[0].toString()); // ID Aluno_Chamada
				aluno.setId(Long.parseLong(result[1].toString())); // ID Aluno
				aluno.setNome(result[2].toString()); // Nome Aluno
				aluno.setNomeMae(result[3].toString()); // Status na Chamada
				
				alunos.add(aluno);
			}
			
			model.addAttribute("alunos", alunos);
			
			return "chamada/visualizarChamada";
		} catch (Exception e) {
			attr.addFlashAttribute("fail", "Não foi possível visualizar a chamada. Tente novamente.");
			return "redirect:/getChamadasEscola";
		}
	}
	
	@RequestMapping(value = "/chamada/vizualizarChamadaProfessor/{id}", method = RequestMethod.GET)
	public String visualizarChamadaProfessor(@PathVariable("id") Long id, RedirectAttributes attr, ModelMap model) {
		List<Object[]> aux = service.buscarPorChamada(id);
		List<Aluno> alunos = new ArrayList<>();
		Aluno aluno = null;
        Object[] result;
        
		try {
			for(int i = 0; i < aux.size(); i++) {
				aluno = new Aluno();
				result = aux.get(i);
				
				aluno.setCodigoGeradoEscola(result[0].toString()); // ID Aluno_Chamada
				aluno.setId(Long.parseLong(result[1].toString())); // ID Aluno
				aluno.setNome(result[2].toString()); // Nome Aluno
				aluno.setNomeMae(result[3].toString()); // Status na Chamada
				
				alunos.add(aluno);
			}
			
			model.addAttribute("alunos", alunos);
			
			return "chamada/visualizarChamadaProfessor";
		} catch (Exception e) {
			attr.addFlashAttribute("fail", "Não foi possível visualizar a chamada. Tente novamente.");
			return "redirect:/getChamadasProfessor";
		}
	}
	
	@RequestMapping(value = "/chamada/editarPresenca/{id}", method = RequestMethod.GET)
	public String getEditarPresenca(@PathVariable("id") Long id, RedirectAttributes attr, ModelMap model, HttpSession session) {
		Aluno aluno = new Aluno();
		List<Object[]> aux = service.buscarAlunoChamada(id);
		Object[] result = aux.get(0);
		
		aluno.setId(Long.parseLong(result[0].toString())); // ID Aluno Chamada
		aluno.setNome(result[1].toString()); // Nome Aluno
		aluno.setDataNascimento(result[2].toString()); // Data Chamada
		aluno.setEndereco(result[3].toString()); // Status Chamada
		
		try {
			model.addAttribute("aluno", aluno);
			return "chamada/editarPresenca";
		} catch (Exception e) {
			attr.addFlashAttribute("fail", "Não foi possível realizar esta operação. Tente novamente.");
			return "redirect:/getChamadasEscola";
		}
	}
	
	@RequestMapping(value = "/chamada/editarPresencaProfessor/{id}", method = RequestMethod.GET)
	public String getEditarPresencaProfessor(@PathVariable("id") Long id, RedirectAttributes attr, ModelMap model, HttpSession session) {
		Aluno aluno = new Aluno();
		List<Object[]> aux = service.buscarAlunoChamada(id);
		Object[] result = aux.get(0);
		
		aluno.setId(Long.parseLong(result[0].toString())); // ID Aluno Chamada
		aluno.setNome(result[1].toString()); // Nome Aluno
		aluno.setDataNascimento(result[2].toString()); // Data Chamada
		aluno.setEndereco(result[3].toString()); // Status Chamada
		
		try {
			model.addAttribute("aluno", aluno);
			return "chamada/editarPresencaProfessor";
		} catch (Exception e) {
			attr.addFlashAttribute("fail", "Não foi possível realizar esta operação. Tente novamente.");
			return "redirect:/getChamadasProfessor";
		}
	}
	
	@PostMapping(value = "/editarPresencaEscola")
	public String postEditarPresenca(Aluno aluno, RedirectAttributes attr, ModelMap model) {
		AlunoChamada alunoChamada = new AlunoChamada();

		alunoChamada.setId(aluno.getId());
		alunoChamada.setStatus(aluno.getEndereco());
		
		if(aluno.getEndereco().intern() == "Ausente"){
			alunoChamada.setFalta(1);
		} else {
			alunoChamada.setFalta(0);
		}
		
		try {
			service.editarPresenca(alunoChamada);
			attr.addFlashAttribute("success", "Presença do aluno alterada.");
			return "redirect:/getChamadasEscola";
		} catch (Exception e) {
			attr.addFlashAttribute("fail", "Não foi possível realizar esta operação. Tente novamente.");
			return "redirect:/getChamadasEscola";
		}
	}
	
	@PostMapping(value = "/editarPresencaProfessor")
	public String postEditarPresencaProfessor(Aluno aluno, RedirectAttributes attr, ModelMap model) {
		AlunoChamada alunoChamada = new AlunoChamada();

		alunoChamada.setId(aluno.getId());
		alunoChamada.setStatus(aluno.getEndereco());
		
		if(aluno.getEndereco().intern() == "Ausente"){
			alunoChamada.setFalta(1);
		} else {
			alunoChamada.setFalta(0);
		}
		
		try {
			service.editarPresenca(alunoChamada);
			attr.addFlashAttribute("success", "Presença do aluno alterada.");
			return "redirect:/getChamadasProfessor";
		} catch (Exception e) {
			attr.addFlashAttribute("fail", "Não foi possível realizar esta operação. Tente novamente.");
			return "redirect:/getChamadasProfessor";
		}
	}
}
