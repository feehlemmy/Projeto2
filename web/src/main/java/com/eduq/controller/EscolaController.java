package com.eduq.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.eduq.model.entity.Aluno;
import com.eduq.model.entity.Aviso;
import com.eduq.model.entity.Classe;
import com.eduq.model.entity.DiaLetivo;
import com.eduq.model.entity.Email;
import com.eduq.model.entity.Escola;
import com.eduq.model.entity.Evento;
import com.eduq.model.entity.Pessoa;
import com.eduq.model.entity.Professor;
import com.eduq.model.service.AlunoService;
import com.eduq.model.service.AvisoService;
import com.eduq.model.service.ClasseService;
import com.eduq.model.service.EmailService;
import com.eduq.model.service.EscolaService;
import com.eduq.model.service.EventoService;
import com.eduq.model.service.ProfessorService;

@Controller
public class EscolaController {

	@Autowired
	private EscolaService service;
	
	@Autowired
	private ProfessorService professorService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private EventoService eventoService;
	
	@Autowired
	private AvisoService avisoService;
	
	@Autowired
	private ClasseService classeService;
	
	@Autowired
	private AlunoService alunoService;
	
	@PostMapping("/escola/cadastro")
	public String cadastrarEscola(Escola escola) {
		try {
			service.salvar(escola);	
			return "redirect:/aguardoConfirmacao";
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:/";
	}
	
	@PostMapping("/escola/completarPerfil")
	public String completarCadastro(Escola escola, RedirectAttributes attr) {
        Escola escolaAux = new Escola();
		try {
			escolaAux = service.buscarPorCodigo(escola.getCodigoMec());
			if(escolaAux != null) {
				service.completarCadastroEscola(escola);
				attr.addFlashAttribute("success", "Cadastro atualizado. Para entrar, preencha os dados abaixo.");
	        	return "redirect:/entrar";
			} else {
				attr.addFlashAttribute("fail", "Código de escola não encontrado. Tente novamente.");
	        	return "redirect:/completarCadastro";
			}
		} catch (Exception e) {
			attr.addFlashAttribute("fail", "Não foi possível atualizar seu cadastro. Tente novamente.");
			return "redirect:/completarCadastro";
		}
	}
	
	@PostMapping("/escola/entrar")
    public String entrar(Escola escola, HttpSession session, RedirectAttributes attr) throws Exception {
        Integer codigoMec = escola.getCodigoMec();
        String senha = escola.getSenha();        

        Escola sessaoatual = new Escola();
        sessaoatual = (Escola) session.getAttribute("escolaLogada");

        try {
            if(sessaoatual != null){
                session.removeAttribute("escolaLogada");
            }
        	sessaoatual = service.verifyLogin(codigoMec, senha);
        	session.setAttribute("escolaLogada", sessaoatual);
            return "redirect:/dashboardEscola";
		} catch (Exception e) {
			attr.addFlashAttribute("fail", "Código MEC ou senha incorretos. Tente novamente.");
			return "redirect:/entrar";
		}
    }
	

	@RequestMapping(value = "/dashboardEscola", method = RequestMethod.GET)
	public String getDashboardEscola(HttpSession session, ModelMap model) {
		List<Classe> classes = new ArrayList<Classe>(); 
		List<Evento> eventos = new ArrayList<Evento>(); 
		List<Aviso> avisos = new ArrayList<Aviso>(); 
        Calendar agora = Calendar.getInstance();
		Integer hora = agora.get(Calendar.HOUR_OF_DAY);
		String data = LocalDate.now().toString();
		Escola sessaoatual = new Escola();
		
        sessaoatual = (Escola) session.getAttribute("escolaLogada");
        eventos = eventoService.buscarPorDia(sessaoatual.getId(), data);
        avisos = avisoService.buscarPorData(sessaoatual.getId(), data);
        
        System.out.println(hora);
        
		if(hora <= 12) {
        	classes = classeService.buscarClassePorPeriodo(sessaoatual.getId(), "Matutino");
        } else {
        	classes = classeService.buscarClassePorPeriodo(sessaoatual.getId(), "Vespertino");
        }
		
		model.addAttribute("classes", classes);
		model.addAttribute("eventos", eventos);
		model.addAttribute("avisos", avisos);
		
		return "fragments/conteudo";
	}
	
	@RequestMapping(value = "/editarEscola/{id}", method = RequestMethod.GET)
	public String preEditar(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("escola", service.buscarPorId(id));
		return "escola/editarEscola";
	}

	@PostMapping("/editar")
	public String editar(Escola escola, HttpSession session, RedirectAttributes attr) {
		Escola sessaoatual = new Escola();
        sessaoatual = (Escola) session.getAttribute("escolaLogada");
        
        escola.setSenha(sessaoatual.getSenha());
        escola.setConfirmacao(sessaoatual.getConfirmacao());
        
        try {
        	service.editar(escola);
        	attr.addFlashAttribute("success", "Perfil editado!");
    		return "redirect:/editarEscola";
		} catch (Exception e) {
			attr.addFlashAttribute("fail", "Não foi possível editar seu perfil. Tente novamente.");
    		return "redirect:/editarEscola";
		}
	}
	
	@PostMapping("/escola/sair")
    public String sair(HttpSession session, RedirectAttributes attr){
        session.invalidate();
        
        attr.addFlashAttribute("success", "Até logo!");
        return "redirect:/entrar";
    }
	
	public String novaSenha(Escola escola) {
		Email email = new Email();
		
		try {
			service.editar(escola);
			emailService.salvar(email);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return "redirect:/";
	}
	
	@PostMapping("/escola/restaurarSenha")
	public String restaurarSenha(Escola escola, RedirectAttributes attr) {
		Email email = new Email();
		
		try {
			escola = service.buscarPorCodigo(escola.getCodigoMec());
			escola.setSenha(service.gerarSenhaAleatoria(escola));
			escola.setConfirmacao(service.gerarSenhaAleatoria(escola));
			
			email.setCodigoMec(escola.getCodigoMec());
			email.setNomeEscola(escola.getNome());
			email.setEmailEscola(escola.getEmail());
			email.setSenhaEscola(escola.getSenha());
			email.setCaracteristica("senha");
			
			service.updateSenhaEscola(escola);
			emailService.salvar(email);
			attr.addFlashAttribute("success", "Senha restaurada. Em breve você receberá um e-mail com a nova senha.");
		} catch (Exception e) {
			attr.addFlashAttribute("fail", "Não foi possível restaurar sua senha. Tente novamente.");
		}
		
		return "redirect:/resetSenha";
	}
	
	@PostMapping("/escola/alterarSenha")
	public String alterarSenhaEscola(Escola escola, HttpSession session, RedirectAttributes attr) {
		Escola sessaoatual = new Escola();
		String senhaAtual;
		
        sessaoatual = (Escola) session.getAttribute("escolaLogada");
        senhaAtual = sessaoatual.getSenha();
        
        if (escola.getSenha().intern().equals(senhaAtual.intern())) {
        	attr.addFlashAttribute("fail", "Sua nova senha não deve ser igual a atual. Tente novamente.");
        	return "redirect:/alterarSenhaEscola";
        } else if (!escola.getNome().intern().equals(senhaAtual.intern())) {
        	attr.addFlashAttribute("fail", "Necessário informar a senha atual. Tente novamente.");
        	return "redirect:/alterarSenhaEscola";
        } else if (!escola.getSenha().intern().equals(escola.getConfirmacao().intern())) {
        	attr.addFlashAttribute("fail", "Senha e confirmação devem ser iguais. Tente novamente.");
        	return "redirect:/alterarSenhaEscola";
		} else {
			escola.setNome(sessaoatual.getNome());
	        escola.setId(sessaoatual.getId());
	        
			try {
	        	service.updateSenhaEscola(escola);
	        	attr.addFlashAttribute("success", "Senha alterada!");
	    		return "redirect:/alterarSenhaEscola";
			} catch (Exception e) {
				attr.addFlashAttribute("fail", "Não foi possível alterar sua senha. Tente novamente.");
	    		return "redirect:/alterarSenhaEscola";
			}
		}
	}
	
	@RequestMapping(value = "/ajudaEscola", method = RequestMethod.GET)
	public String exibirAjuda(HttpSession session, Email email) {
		return "escola/ajudaEscola";
	}
	
	@PostMapping("/escola/enviarDuvida")
	public String enviarDuvida(Email email, HttpSession session, RedirectAttributes attr) {
		Escola sessaoatual = new Escola();
        
		sessaoatual = (Escola) session.getAttribute("escolaLogada");
        
		email.setEmailEscola(sessaoatual.getEmail());
		email.setNomeEscola(sessaoatual.getNome());
		email.setCaracteristica("duvida");
        
		try {
			emailService.salvar(email);
			attr.addFlashAttribute("success", "Dúvida enviada.");
		} catch (Exception e) {
			attr.addFlashAttribute("fail", "Não foi possível enviar sua dúvida. Tente novamente.");
		}
        
        return "redirect:/ajudaEscola";
	}
	
	@GetMapping("/editarProfessorEscola/{id}")
	public String preEditarProfessor(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("pessoa", professorService.buscarPorId(id));
		return "professor/cadastroProfessor";
	}
	
	@PostMapping("/editarProfessorEscola")
	public String editarProfessor(Pessoa pessoa, HttpSession session, RedirectAttributes attr) {
		Professor professor = new Professor();
		Escola escolaSessaoAtual = new Escola();
		
		escolaSessaoAtual = (Escola) session.getAttribute("escolaLogada");
		
		professor.setId(pessoa.getId());
		professor.setNome(pessoa.getNome());
		professor.setCpf(pessoa.getCpf());
		professor.setTelefone(pessoa.getTelefone());
		professor.setEmail(pessoa.getEmail());
		professor.setEndereco(pessoa.getEndereco());
		professor.setUf(pessoa.getUf());
		professor.setMunicipio(pessoa.getMunicipio());
		professor.setCep(pessoa.getCep());
		professor.setEscolaId(escolaSessaoAtual.getId());
		
		try {
			professorService.updateProfessor(professor);
			attr.addFlashAttribute("success", "Professor atualizado.");
		} catch (Exception e) {
			attr.addFlashAttribute("fail", "Não foi possível atualizar o professor solicitado.");
		}
		
		return "redirect:/cadastrarProfessor";
	}
	
	@RequestMapping(value = "/eventosEscola", method = RequestMethod.GET)
	public String getCadastrarEventos(Evento evento, ModelMap model, HttpSession session) {
		return "escola/cadastrarEventos";
	}
	
	@PostMapping(value = "/escola/cadastrarEvento")
	public String postCadastrarEventos(Evento evento, HttpSession session, RedirectAttributes attr) {
		Escola escolaSessaoAtual = new Escola();
		
		escolaSessaoAtual = (Escola) session.getAttribute("escolaLogada");
		evento.setEscolaId(escolaSessaoAtual.getId());
		
		try {
			eventoService.salvar(evento);
			attr.addFlashAttribute("success","Evento cadastrado.");
		} catch (Exception e) {
			attr.addFlashAttribute("fail","Não foi possível cadastrar o evento, tente novamente.");
		}
		
		return "redirect:/eventosEscola";
	}
	
	@PostMapping(value = "/escola/editarEvento")
	public String postEditarEventos(Evento evento, HttpSession session, RedirectAttributes attr) {
		Escola escolaSessaoAtual = new Escola();
		
		escolaSessaoAtual = (Escola) session.getAttribute("escolaLogada");
		evento.setEscolaId(escolaSessaoAtual.getId());
		
		try {
			eventoService.editar(evento);
			attr.addFlashAttribute("success","Evento cadastrado.");
		} catch (Exception e) {
			attr.addFlashAttribute("fail","Não foi possível cadastrar o evento, tente novamente.");
		}
		
		return "redirect:/getCalendarioEscola";
	}
	
	@PostMapping(value = "/escola/novoAviso")
	public String postEnviarAviso(Aviso aviso, HttpSession session, RedirectAttributes attr) {
		Escola escolaSessaoAtual = new Escola();
		
		escolaSessaoAtual = (Escola) session.getAttribute("escolaLogada");
		aviso.setEscolaId(escolaSessaoAtual.getId());
		
		try {
			avisoService.salvar(aviso);
			attr.addFlashAttribute("success","Aviso enviado.");
		} catch (Exception e) {
			attr.addFlashAttribute("fail","Não foi possível enviar o aviso, tente novamente.");
		}
		
		return "redirect:/getAvisosEscola";
	}
	
	@PostMapping(value = "/escola/editarAviso")
	public String postEditarAviso(Aviso aviso, HttpSession session, RedirectAttributes attr) {
		Escola escolaSessaoAtual = new Escola();
		
		escolaSessaoAtual = (Escola) session.getAttribute("escolaLogada");
		aviso.setEscolaId(escolaSessaoAtual.getId());
		
		try {
			avisoService.editar(aviso);;
			attr.addFlashAttribute("success","Aviso enviado.");
		} catch (Exception e) {
			attr.addFlashAttribute("fail","Não foi possível editar o aviso, tente novamente.");
		}
		
		return "redirect:/getAvisosEscola";
	}
	
	@GetMapping(value = "/escola/requisicoes")
	public String getRequisicoes(HttpSession session, ModelMap model) {
		Escola sessao = new Escola();
		Aluno aluno = null;
		Object[] result = null;
		Pessoa requisicao = null; // Objeto que será utilizado para armazenar a requisicao
		List<Pessoa> requisicoes = new ArrayList<Pessoa>();
		
		sessao = (Escola) session.getAttribute("escolaLogada");
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
		
		return "requisicao/listaRequisicoes";
	}
	
	@PostMapping("/importarCalendario")
	public String mapReapExcelDatatoDB(@RequestParam("file") MultipartFile reapExcelDataFile, HttpSession session, RedirectAttributes attr, ModelMap model) throws IOException {
		Escola sessao = new Escola();
		Evento evento = new Evento();
		List<DiaLetivo> diasLetivos = new ArrayList<DiaLetivo>();
		DiaLetivo dia = null;
		
		sessao = (Escola) session.getAttribute("escolaLogada");
	    XSSFWorkbook workbook = new XSSFWorkbook(reapExcelDataFile.getInputStream());
	    XSSFSheet worksheet = workbook.getSheetAt(0);

	    try {
		    for(int i=2; i < worksheet.getPhysicalNumberOfRows(); i++) {
		    	dia = new DiaLetivo();
		    	
		        XSSFRow row = worksheet.getRow(i);
		        dia.setDataAviso(row.getCell(0).toString());
		        dia.setEscolaId(sessao.getId());

		        diasLetivos.add(dia);
		    }
	    
			service.salvarDiasetivos(diasLetivos);
			attr.addFlashAttribute("success","Aviso enviado.");
		} catch (Exception e) {
			attr.addFlashAttribute("fail","Não foi possível editar o aviso, tente novamente.");
		}
	    
	    model.addAttribute("evento", evento);
	    
	    return "escola/calendarioEscola";
	}
	
	@GetMapping(value = "/escola/enviarAvisoAluno/{id}")
	public String getAvisoAluno(@PathVariable("id") Long id, ModelMap model) {
		Aviso aviso = new Aviso();
		
		aviso.setAlunoId(id);
		
		model.addAttribute("aluno", alunoService.buscarPorId(id));
		model.addAttribute("aviso", aviso);
		
		return "escola/enviarAvisoAluno";
	}
	
	@PostMapping(value = "/escola/novoAvisoAluno")
	public String postAvisoAluno(Aviso aviso, HttpSession session, RedirectAttributes attr) {
		Escola escolaSessaoAtual = new Escola();
		
		escolaSessaoAtual = (Escola) session.getAttribute("escolaLogada");
		aviso.setEscolaId(escolaSessaoAtual.getId());
		aviso.setAnoClasse("-");
		
		try {
			avisoService.salvar(aviso);
			attr.addFlashAttribute("success","Aviso enviado.");
		} catch (Exception e) {
			attr.addFlashAttribute("fail","Não foi possível enviar o aviso, tente novamente.");
		}
		
		return "redirect:/getAvisosEscola";
	}
}
