package com.eduq.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.eduq.model.entity.Aviso;
import com.eduq.model.entity.Chamada;
import com.eduq.model.entity.Classe;
import com.eduq.model.entity.Email;
import com.eduq.model.entity.Escola;
import com.eduq.model.entity.Evento;
import com.eduq.model.entity.Pessoa;
import com.eduq.model.entity.Professor;
import com.eduq.model.service.AlunoService;
import com.eduq.model.service.AvisoService;
import com.eduq.model.service.ChamadaService;
import com.eduq.model.service.ClasseService;
import com.eduq.model.service.EmailService;
import com.eduq.model.service.EventoService;
import com.eduq.model.service.ProfessorService;

@Controller
public class ProfessorController {
	
	@Autowired
	private ProfessorService service;
	
	@Autowired
	private ClasseService classeService;
	
	@Autowired
	private EventoService eventoService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private AvisoService avisoService;
	
	@Autowired
	private ChamadaService chamadaService;
	
	@Autowired
	private AlunoService alunoService;
	
	@PostMapping("/professor/cadastro")
	public String cadastrarProfessor(Pessoa pessoa, HttpSession session, RedirectAttributes attr) {
		Professor professor = new Professor();
		Email email = new Email();
		Escola escolaSessaoAtual = new Escola();
		List<Professor> listProfessores = new ArrayList<>();

		String codigoGeradoEscola;
		String senhaAleatoria;
		String cpfProfessor = pessoa.getCpf();
		
		escolaSessaoAtual = (Escola) session.getAttribute("escolaLogada");
		codigoGeradoEscola = service.gerarCodigoEscola(escolaSessaoAtual, cpfProfessor);
		senhaAleatoria = service.gerarSenhaAleatoria();
		
		professor.setNome(pessoa.getNome());
		professor.setCpf(pessoa.getCpf());
		professor.setTelefone(pessoa.getTelefone());
		professor.setEndereco(pessoa.getEndereco());
		professor.setUf(pessoa.getUf());
		professor.setMunicipio(pessoa.getMunicipio());
		professor.setCep(pessoa.getCep());
		professor.setEmail(pessoa.getEmail());
		professor.setSenha(senhaAleatoria);
		professor.setSenhaGerada(senhaAleatoria);
		professor.setCodigoGeradoEscola(codigoGeradoEscola);
		professor.setEscolaId(escolaSessaoAtual.getId());
		
		email.setCodigoProfessor(codigoGeradoEscola);
		email.setSenhaProfessor(senhaAleatoria);
		email.setEmailProfessor(professor.getEmail());
		email.setEmailEscola(escolaSessaoAtual.getEmail());
		email.setNomeEscola(escolaSessaoAtual.getNome());
		email.setNomeProfessor(professor.getNome());
		email.setCodigoMec(escolaSessaoAtual.getCodigoMec());
		email.setCaracteristica("cadastro");
		
		listProfessores.add(professor);
		
		try {
			service.salvar(professor);
			emailService.salvar(email);
			attr.addFlashAttribute("success", "Professor cadastrado.");
			return "redirect:/cadastrarProfessor";
		} catch (Exception e) {
			attr.addFlashAttribute("fail", "Não foi possível cadastrar o professor solicitado.");
			return "redirect:/";
		}
	}
	
	@PostMapping("/professor/entrar")
    public String entrarProfessor(Professor professor, HttpSession session, RedirectAttributes attr) throws Exception {
        String codigoGerado = professor.getCodigoGeradoEscola();
        String senha = professor.getSenhaGerada();
        

        Professor sessaoatual = new Professor();
        sessaoatual = (Professor) session.getAttribute("professorLogado");
        
        try {
        	if(sessaoatual != null){
                session.removeAttribute("professorLogado");
            }
        	
        	sessaoatual = service.verifyLogin(codigoGerado, senha);
        	session.setAttribute("professorLogado", sessaoatual);
            return "redirect:/dashboardProfessor";
		} catch (Exception e) {
			attr.addFlashAttribute("fail", "Código de acesso ou senha incorretos. Tente novamente.");
			return "redirect:/entrarProfessor";
		}
    }
	
	@RequestMapping(value = "/dashboardProfessor", method = RequestMethod.GET)
	public String getDashboardProfessor(HttpSession session, ModelMap model) {
		List<Evento> eventos = new ArrayList<Evento>(); 
		List<Aviso> avisos = new ArrayList<Aviso>();
		List<Chamada> chamadas = new ArrayList<Chamada>();
		Calendar agora = Calendar.getInstance();
		Integer hora = agora.get(Calendar.HOUR_OF_DAY);
		String data = LocalDate.now().toString();
		
		Professor sessaoAtual = new Professor();
		sessaoAtual = (Professor) session.getAttribute("professorLogado");
		
		eventos = eventoService.buscarPorDia(sessaoAtual.getEscolaId(), data);
        avisos = avisoService.buscarPorData(sessaoAtual.getEscolaId(), data);
        
        if (hora <= 12) {
        	chamadas = chamadaService.buscarPorPeriodo(sessaoAtual.getEscolaId(), data, "Matutino");
        } else {
        	chamadas = chamadaService.buscarPorPeriodo(sessaoAtual.getEscolaId(), data, "Vespertino");
        }
        
        model.addAttribute("eventos", eventos);
		model.addAttribute("avisos", avisos);
		model.addAttribute("chamadas", chamadas);
		
		return "fragments/conteudoProfessor";
	}
	
	@PostMapping("/professor/sair")
    public String sair(HttpSession session, RedirectAttributes attr){
        session.invalidate();
        
        attr.addFlashAttribute("success", "Até logo!");
        return "redirect:/entrarProfessor";
    }
	
	@PostMapping("/professor/resetSenha")
	public String restaurarSenha(Professor professor, RedirectAttributes attr) {
		String codigoGeradoEscola = professor.getCodigoGeradoEscola();
		Email email = new Email();
		String senhaGerada;
		
		try {
			professor = service.buscarPorCodigo(codigoGeradoEscola);
			senhaGerada = service.generateSecurityKeySenha();
			professor.setSenhaGerada(senhaGerada);
			
			email.setCodigoProfessor(professor.getCodigoGeradoEscola());
			email.setNomeProfessor(professor.getNome());
			email.setSenhaProfessor(professor.getSenhaGerada());
			email.setEmailProfessor(professor.getEmail());
			email.setCaracteristica("senha");
			
			service.updateSenhaProfessor(professor);
			emailService.salvar(email);
			attr.addFlashAttribute("success", "Senha restaurada. Em breve você receberá um e-mail com a nova senha.");
		} catch (Exception e) {
			attr.addFlashAttribute("fail", "Não foi possível restaurar sua senha. Tente novamente.");
		}
		
		return "redirect:/professor/resetSenha";
	}
	
	@RequestMapping(value = "/listarProfessores", method = RequestMethod.GET)
	public String listarProfessores(ModelMap model, HttpSession session) {
		List<Pessoa> professores = new ArrayList<Pessoa>(); 
		Escola escolaSessaoAtual = new Escola();
		
		escolaSessaoAtual = (Escola) session.getAttribute("escolaLogada");
		
		professores = service.buscarProfessorPorEscola(escolaSessaoAtual.getId());
		
		model.addAttribute("professores", professores);
		
		return "professor/listaProfessores";
	}
	
	@GetMapping("/professores/excluir/{id}")
	public String excluirProfessor(@PathVariable("id") Long id, ModelMap model, HttpSession session) {
		Professor professor = service.buscarPorId(id);
		List<Classe> classesProfessor = new ArrayList<>(); 
		classesProfessor = classeService.findByProfessor(professor.getEscolaId(), professor.getId()); 
		if(classesProfessor.isEmpty()) {
			try {
				service.excluir(id);
				model.addAttribute("success", "Professor removido.");
			} catch (Exception e) {
				model.addAttribute("fail", "Não foi possível remover o professor solicitado.");
			}
			
		} else {
			model.addAttribute("fail", "Não foi possível remover o professor pois ele é resonsável por uma classe.");
		}
		
		return listarProfessores(model, session);
	}
	
	@PostMapping("/editarProfessor")
	public String editarProfessor(Pessoa pessoa, HttpSession session, RedirectAttributes attr) {
		Professor professor = new Professor();
		Professor sessaoatual = new Professor();
		
        sessaoatual = (Professor) session.getAttribute("professorLogado");
		
		professor.setId(Long.parseLong(sessaoatual.getCep()));// ID Pessoa
		professor.setNome(pessoa.getNome());
		professor.setCpf(pessoa.getCpf());
		professor.setTelefone(pessoa.getTelefone());
		professor.setEmail(pessoa.getEmail());
		professor.setEndereco(pessoa.getEndereco());
		professor.setUf(pessoa.getUf());
		professor.setMunicipio(pessoa.getMunicipio());
		professor.setCep(pessoa.getCep());
		
		try {
			service.updateProfessor(professor);
			attr.addFlashAttribute("success", "Perfil atualizado.");
		} catch (Exception e) {
			attr.addFlashAttribute("fail", "Não foi possível atualizar o perfil. Tente novamente.");
		}
		
		return "redirect:/editarProfessor";
	}
	
	@PostMapping("/professor/alterarSenha")
	public String alterarSenhaProfessor(Professor professor, HttpSession session, RedirectAttributes attr) {
		Professor sessaoatual = new Professor();
		String senhaAtual;
		String senhaProfessor;
		
        sessaoatual = (Professor) session.getAttribute("professorLogado");
        senhaAtual = sessaoatual.getSenhaGerada();
        senhaProfessor = professor.getPessoa().getEmail();
        
        if (professor.getSenhaGerada().intern().equals(senhaAtual.intern())) {
        	attr.addFlashAttribute("fail", "Sua nova senha não deve ser igual a atual. Tente novamente.");
        	return "redirect:/alterarSenhaProfessor";
        } else if (!senhaProfessor.intern().equals(senhaAtual.intern())) {
        	attr.addFlashAttribute("fail", "Necessário informar a senha atual. Tente novamente.");
        	return "redirect:/alterarSenhaProfessor";
        } else if (!professor.getSenhaGerada().intern().equals(professor.getPessoa().getNome().intern())) {
        	attr.addFlashAttribute("fail", "Senha e confirmação devem ser iguais. Tente novamente.");
        	return "redirect:/alterarSenhaProfessor";
		} else {
			try {
				professor.setCodigoGeradoEscola(sessaoatual.getCodigoGeradoEscola());
	        	service.updateSenhaProfessor(professor);
	        	attr.addFlashAttribute("success", "Senha alterada!");
	    		return "redirect:/alterarSenhaProfessor";
			} catch (Exception e) {
				attr.addFlashAttribute("fail", "Não foi possível alterar sua senha. Tente novamente.");
	    		return "redirect:/alterarSenhaProfessor";
			}
		}
	}
	
	@RequestMapping(value = "/novoEventoProfessor", method = RequestMethod.GET)
	public String getCadastrarEventos(Evento evento, ModelMap model, HttpSession session) {
		return "professor/cadastrarEventoProfessor";
	}
	
	@PostMapping(value = "/professor/cadastrarEvento")
	public String postCadastrarEventos(Evento evento, HttpSession session, RedirectAttributes attr) {
		Professor professorSessaoAtual = new Professor();
		
		professorSessaoAtual = (Professor) session.getAttribute("professorLogado");
		evento.setEscolaId(professorSessaoAtual.getEscolaId());
		
		try {
			eventoService.salvar(evento);
			attr.addFlashAttribute("success","Evento cadastrado.");
		} catch (Exception e) {
			attr.addFlashAttribute("fail","Não foi possível cadastrar o evento, tente novamente.");
		}
		
		return "redirect:/novoEventoProfessor";
	}
	
	@PostMapping(value = "/professor/editarEvento")
	public String postEditarEventos(Evento evento, HttpSession session, RedirectAttributes attr) {
		Professor professorSessaoAtual = new Professor();
		
		professorSessaoAtual = (Professor) session.getAttribute("professorLogado");
		evento.setEscolaId(professorSessaoAtual.getEscolaId());
		
		try {
			eventoService.editar(evento);
			attr.addFlashAttribute("success","Evento atualizado.");
		} catch (Exception e) {
			attr.addFlashAttribute("fail","Não foi possível cadastrar o evento, tente novamente.");
		}
		
		return "redirect:/getCalendarioProfessor";
	}
	
	@PostMapping(value = "/professor/novoAviso")
	public String postEnviarAviso(Aviso aviso, HttpSession session, RedirectAttributes attr) {
		Professor professorSessaoAtual = new Professor();
		
		professorSessaoAtual = (Professor) session.getAttribute("professorLogado");
		aviso.setEscolaId(professorSessaoAtual.getEscolaId());
		
		try {
			avisoService.salvar(aviso);
			attr.addFlashAttribute("success","Aviso enviado.");
		} catch (Exception e) {
			attr.addFlashAttribute("fail","Não foi possível enviar o aviso, tente novamente.");
		}
		
		return "redirect:/professor/getAvisos";
	}
	
	@PostMapping(value = "/professor/editarAviso")
	public String postEditarAviso(Aviso aviso, HttpSession session, RedirectAttributes attr) {
		Professor professorSessaoAtual = new Professor();
		
		professorSessaoAtual = (Professor) session.getAttribute("professorLogado");
		aviso.setEscolaId(professorSessaoAtual.getEscolaId());
		
		try {
			avisoService.editar(aviso);;
			attr.addFlashAttribute("success","Aviso enviado.");
		} catch (Exception e) {
			attr.addFlashAttribute("fail","Não foi possível editar o aviso, tente novamente.");
		}
		
		return "redirect:/professor/getAvisos";
	}
	
	@GetMapping(value = "/professor/requisicoes")
	public String getRequisicoes(HttpSession session, ModelMap model) {
		Professor sessao = new Professor();
		Pessoa requisicao = null; // Objeto que será utilizado para armazenar a requisicao
		Aluno aluno = null;
		Object[] result = null;
		List<Pessoa> requisicoes = new ArrayList<Pessoa>();
		
		sessao = (Professor) session.getAttribute("professorLogado");
		List<Object[]> aux = service.buscarRequisicoes(sessao.getId());
		
		for(int i = 0; i < aux.size(); i++) {
			aluno = new Aluno();
			requisicao = new Pessoa();
			result = aux.get(i);
			
			requisicao.setId(Long.parseLong(result[0].toString())); // ID da Requisição
			requisicao.setNome(result[1].toString()); // Nome do Responsável
			requisicao.setSenha(result[2].toString()); // Tipo da Requisição
			requisicao.setEndereco(result[4].toString()); // Descrição da Requisição
			
			aluno = alunoService.buscarPorId(Long.parseLong(result[3].toString()));			
			requisicao.setMunicipio(aluno.getNome()); // Nome do Aluno 
			
			requisicoes.add(requisicao);
		}
		
		model.addAttribute("requisicoes", requisicoes);
		
		return "requisicao/listaRequisicoesProfessor";
	}
	
	@RequestMapping(value = "/ajudaProfessor", method = RequestMethod.GET)
	public String exibirAjuda(HttpSession session, Email email) {
		return "professor/ajudaProfessor";
	}
	
	@PostMapping("/professor/enviarDuvida")
	public String enviarDuvida(Email email, HttpSession session, RedirectAttributes attr) {
		Professor sessao = new Professor();
		
		sessao = (Professor) session.getAttribute("professorLogado");
        
		email.setEmailProfessor(sessao.getEmail());
		email.setNomeProfessor(sessao.getNome());
		email.setCaracteristica("duvida");
        
		try {
			emailService.salvar(email);
			attr.addFlashAttribute("success", "Dúvida enviada.");
		} catch (Exception e) {
			attr.addFlashAttribute("fail", "Não foi possível enviar sua dúvida. Tente novamente.");
		}
        
        return "redirect:/ajudaProfessor";
	}
	
	@GetMapping("/professores/getDadosAluno/{id}")
	public String getDadosAluno(@PathVariable("id") Long id, ModelMap model) {
		Aluno aluno = new Aluno();
		Pessoa responsavel = new Pessoa();
		
		aluno = alunoService.buscarPorId(id);
		responsavel = alunoService.buscarDadosResponsavel(aluno.getCodigoGeradoEscola());
		
		model.addAttribute("aluno", aluno);
		model.addAttribute("responsavel", responsavel);
		
		return "aluno/dadosAluno";
	}
	
	@GetMapping(value = "/professor/enviarAvisoAluno/{id}")
	public String getAvisoAluno(@PathVariable("id") Long id, ModelMap model) {
		Aviso aviso = new Aviso();
		
		aviso.setAlunoId(id);
		
		model.addAttribute("aluno", alunoService.buscarPorId(id));
		model.addAttribute("aviso", aviso);
		
		return "professor/enviarAvisoAluno";
	}
	
	@PostMapping(value = "/professor/novoAvisoAluno")
	public String postAvisoAluno(Aviso aviso, HttpSession session, RedirectAttributes attr) {
		Professor sessaoAtual = new Professor();
		
		sessaoAtual = (Professor) session.getAttribute("professorLogado");
		aviso.setEscolaId(sessaoAtual.getEscolaId());
		aviso.setAnoClasse("-");
		
		try {
			avisoService.salvar(aviso);
			attr.addFlashAttribute("success","Aviso enviado.");
		} catch (Exception e) {
			attr.addFlashAttribute("fail","Não foi possível enviar o aviso, tente novamente.");
		}
		
		return "redirect:/professor/getAvisos";
	}
}
