package com.eduq.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.eduq.model.entity.Aluno;
import com.eduq.model.entity.Aviso;
import com.eduq.model.entity.Chamada;
import com.eduq.model.entity.Classe;
import com.eduq.model.entity.ComponenteCurricular;
import com.eduq.model.entity.Email;
import com.eduq.model.entity.Escola;
import com.eduq.model.entity.Evento;
import com.eduq.model.entity.Pessoa;
import com.eduq.model.entity.Professor;
import com.eduq.model.service.ChamadaService;
import com.eduq.model.service.ClasseService;
import com.eduq.model.service.EmailService;
import com.eduq.model.service.EscolaService;
import com.eduq.model.service.ProfessorService;

@Controller
public class NavigationController {
	
	@Autowired
	private EscolaService escolaService;
	
	@Autowired
	private ProfessorService professorService;
	
	@Autowired
	private ClasseService classeService;
	
	@Autowired
	private ChamadaService chamadaService;
	
	@Autowired
	private EmailService emailService;
			
	@GetMapping("/")
	public String getIndex(ModelMap model) {
		Email email = new Email();
		model.addAttribute("email", email);
		return "index";
	}
	
	// Métodos de navegação Escola
	@RequestMapping(value = "/entrar", method = RequestMethod.GET)
	public String getEntrar(Escola escola) {
		return "entrar";
	}
	
	@RequestMapping(value = "/cadastrarEscola", method = RequestMethod.GET)
	public String getCadastroEscola(Escola escola) {
		return "escola/cadastroEscola";
	}
	
	@RequestMapping(value = "/aguardoConfirmacao", method = RequestMethod.GET)
	public String getAguardoConfirmacaoEscola(Escola escola) {
		return "escola/aguardoConfirmacao";
	}
	
	@RequestMapping(value = "/completarCadastro", method = RequestMethod.GET)
	public String getCompletarCadastro(Escola escola) {
		return "escola/completarPerfilEscola";
	}
	
	@RequestMapping(value = "/editarEscola", method = RequestMethod.GET)
	public String getEditarEscola(Escola escola, ModelMap model, HttpSession session) throws Exception {
		Escola escolaSessaoAtual = new Escola();
		
		escolaSessaoAtual = (Escola) session.getAttribute("escolaLogada");
        
		escola = escolaService.buscarPorCodigo(escolaSessaoAtual.getCodigoMec());
		model.addAttribute("escola", escola);
		return "escola/editarPerfil";
	}
	
	@RequestMapping(value = "/resetSenha", method = RequestMethod.GET)
	public String getResetSenha(Escola escola) {
		return "escola/resetSenha";
	}
	
	@RequestMapping(value = "/novaSenha", method = RequestMethod.GET)
	public String getNovaSenha(Escola escola) {
		return "escola/novaSenha";
	}
	
	@RequestMapping(value = "/alterarSenhaEscola", method = RequestMethod.GET)
	public String getAlterarSenha(ModelMap model, HttpSession session) {
		Escola sessaoatual = new Escola();
		
        sessaoatual = (Escola) session.getAttribute("escolaLogada");
       
		model.addAttribute("escola", sessaoatual);
		
		return "escola/trocarSenhaEscola";
	}
	
	@RequestMapping(value = "/getCalendarioEscola", method = RequestMethod.GET)
	public String getCalendarioEscola(Evento evento, ModelMap model, HttpSession session) throws Exception{
		model.addAttribute("evento", evento);
		
		return "escola/calendarioEscola";
	}
	
	@GetMapping("/getTemplateCalendario")
	public ResponseEntity<InputStreamResource> generateTemplate(HttpServletResponse response) throws IOException {
		Workbook workbook = new XSSFWorkbook();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		 
		Sheet sheet = workbook.createSheet("Dias Letivos");
		
		Row info = sheet.createRow(0);
		Row header = sheet.createRow(1);
		
		DataFormat fmt = workbook.createDataFormat();
		CellStyle data = workbook.createCellStyle();
		data.setDataFormat(fmt.getFormat("dd/MM/yyyy"));
		sheet.setDefaultColumnStyle(0, data);		
		
		CellStyle headerStyle = workbook.createCellStyle();
		headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
		
		XSSFFont font = ((XSSFWorkbook) workbook).createFont();
		font.setFontName("Calibri");
		font.setFontHeightInPoints((short) 12);
		font.setBold(true);
		headerStyle.setFont(font);
		
		Cell infoCell = info.createCell(0);
		infoCell.setCellValue("Informe os dias letivos na coluna 'Data' com o formato DD/MM/AAAA.");
		infoCell.setCellStyle(headerStyle);
		
		Cell headerCell = header.createCell(0);
		headerCell.setCellValue("Data");
		headerCell.setCellStyle(headerStyle);

		workbook.write(out);
		
		ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
		
		return ResponseEntity.ok()
				.header("Content-Disposition", "attachment; filename=template.xlsx")
				.body(new InputStreamResource(in));
	}
	
	@PostMapping("/escola/enviarDuvida/index")
	public String enviarDuvidaIndex(Email email, ModelMap model, RedirectAttributes attr) {
		try {
			email.setCaracteristica("duvida");
			emailService.salvar(email);
			attr.addFlashAttribute("success", "Sua dúvida foi enviada. Em breve iremos te responder.");
		} catch (Exception e) {
			attr.addFlashAttribute("fail", "Não foi possível enviar sua dúvida. Tente novamente.");
		}
		email = new Email();
		model.addAttribute("email", email);
		
		return "redirect:/";
	}
		
	// Métodos de navegação Professor
	@RequestMapping(value = "/cadastrarProfessor", method = RequestMethod.GET)
	public String getCadastroProfessor(Pessoa pessoa) {
		return "professor/cadastroProfessor";
	}
	
	@RequestMapping(value = "/entrarProfessor", method = RequestMethod.GET)
	public String getEntrarProfessor(Professor professor) {
		return "loginProfessor";
	}
	
	@RequestMapping(value = "/editarProfessor", method = RequestMethod.GET)
	public String getEditarProfessor(Pessoa pessoa, HttpSession session, ModelMap model) {
		Professor sessaoatual = new Professor();
        sessaoatual = (Professor) session.getAttribute("professorLogado");
		model.addAttribute("pessoa", professorService.buscarPorId(Long.parseLong(sessaoatual.getCep())));

		return "professor/editarProfessor";
	}
	
	@RequestMapping(value = "/professor/resetSenha", method = RequestMethod.GET)
	public String getResetSenhaProfessor(Professor professor) {
		return "professor/resetSenha";
	}
	
	@RequestMapping(value = "/alterarSenhaProfessor", method = RequestMethod.GET)
	public String getAlterarSenhaProfessor(ModelMap model, HttpSession session) {
		Professor sessaoatual = new Professor();
        sessaoatual = (Professor) session.getAttribute("professorLogado");
		model.addAttribute("professor", professorService.buscarPorId(Long.parseLong(sessaoatual.getCep())));
		
		return "professor/novaSenhaProfessor";
	}
	
	@RequestMapping(value = "/getCalendarioProfessor", method = RequestMethod.GET)
	public String getCalendarioProfessor(Evento evento, ModelMap model, HttpSession session) throws Exception{
		model.addAttribute("evento", evento);
		
		return "professor/calendarioProfessor";
	}
	
	@RequestMapping(value = "/professor/getAvisos", method = RequestMethod.GET)
	public String getAvisosProfessor(HttpSession session, Aviso aviso, ModelMap model) {
		model.addAttribute("aviso", aviso);
		return "professor/avisosProfessor";
	}
	
	// Métodos de navegação Classe
	@RequestMapping(value = "/cadastrarClasse", method = RequestMethod.GET)
	public String getCadastroClasse(Classe classe, ModelMap model, HttpSession session) {
		Escola escolaSessaoAtual = new Escola();
		List<Pessoa> professores = new ArrayList<Pessoa>();
		
		escolaSessaoAtual = (Escola) session.getAttribute("escolaLogada");
		
		professores = professorService.buscarProfessorPorEscola(escolaSessaoAtual.getId());
		model.addAttribute("professores", professores);
		
		return "classe/cadastroClasse";
	}
	
	// Métodos de navegação Componente Curricular
	@RequestMapping(value = "/cadastrarComponente", method = RequestMethod.GET)
	public String getCadastroComponente(ComponenteCurricular componente) {
		return "componente/cadastroComponente";
	}
	
	// Métodos de navegação Aluno
	@RequestMapping(value = "/cadastrarAluno", method = RequestMethod.GET)
	public String getCadastrarAluno(Aluno aluno, ModelMap model, HttpSession session) {
		
		return "aluno/cadastroAluno";
	}
	
	//Método de navegação Aviso
	@RequestMapping(value = "/getAvisosEscola", method = RequestMethod.GET)
	public String getAvisos(Aviso aviso, ModelMap model) {
		model.addAttribute("aviso", aviso);
		
		return "escola/listaAvisos";
	}
	
	//Método de navegação Chamada
	@RequestMapping(value = "/getChamadasEscola", method = RequestMethod.GET)
	public String getChamada(Chamada chamada, ModelMap model, HttpSession session) {
		List<Classe> classes = new ArrayList<Classe>(); 
		List<Object[]> listAux = new ArrayList<Object[]>();
		List<Pessoa> chamadas = new ArrayList<Pessoa>();
		Pessoa aux = null;
		Escola escolaSessaoAtual = new Escola();
		Object[] result = null;
		
		escolaSessaoAtual = (Escola) session.getAttribute("escolaLogada");
		
		classes = classeService.findByEscola(escolaSessaoAtual.getId());
		listAux = chamadaService.buscarPorEscola(escolaSessaoAtual.getId());
		
		for(int i = 0; i < listAux.size(); i++) {
			aux = new Pessoa();
			
			result = listAux.get(i);
			
			String dataArray[] = result[1].toString().split("-");
			String data = "" + dataArray[2] + "/" + dataArray[1] + "/" + dataArray[0] + "";
			
			aux.setId(Long.parseLong(result[0].toString())); // ID Chamada
			aux.setCpf(data); 								 // Data Chamada
			aux.setNome(result[2].toString());				 // Nome Classe
			aux.setCep(result[3].toString());				 // Ano Classe
			aux.setTelefone(result[4].toString());			 // ID Classe
			aux.setEmail(result[5].toString());				 // Status Chamada
			
			chamadas.add(aux);
		}
		
		model.addAttribute("classes", classes);
		model.addAttribute("chamada", chamada);
		model.addAttribute("chamadas", chamadas);
		
		return "chamada/listaChamadas";
	}
	
	@RequestMapping(value = "/getChamadasProfessor", method = RequestMethod.GET)
	public String getChamadaProfessor(Chamada chamada, ModelMap model, HttpSession session) {
		List<Classe> classes = new ArrayList<Classe>(); 
		List<Object[]> listAux = new ArrayList<Object[]>();
		List<Pessoa> chamadas = new ArrayList<Pessoa>();
		Professor professorSessaoAtual = new Professor();
		Pessoa aux = null;
		Object[] result = null;
		
		professorSessaoAtual = (Professor) session.getAttribute("professorLogado");
		
		classes = classeService.findByProfessor(professorSessaoAtual.getEscolaId(), professorSessaoAtual.getId());
		listAux = chamadaService.buscarPorProfessor(professorSessaoAtual.getEscolaId());
		
		for(int i = 0; i < listAux.size(); i++) {
			aux = new Pessoa();
			
			result = listAux.get(i);
			
			String dataArray[] = result[1].toString().split("-");
			String data = "" + dataArray[2] + "/" + dataArray[1] + "/" + dataArray[0] + "";
			
			aux.setId(Long.parseLong(result[0].toString())); // ID Chamada
			aux.setCpf(data); 								 // Data Chamada
			aux.setNome(result[2].toString());				 // Nome Classe
			aux.setCep(result[3].toString());				 // Ano Classe
			aux.setTelefone(result[4].toString());			 // ID Classe
			aux.setEmail(result[5].toString());				 // Status Chamada
			
			chamadas.add(aux);
		}
		
		model.addAttribute("classes", classes);
		model.addAttribute("chamada", chamada);
		model.addAttribute("chamadas", chamadas);
		
		return "chamada/listaChamadasProfessor";
	}
	
	@RequestMapping(value = "/getChamadasNaoRealizadasEscola", method = RequestMethod.GET)
	public String getChamadaEscolaNaoRealizada(Chamada chamada, ModelMap model, HttpSession session) {
		List<Classe> classes = new ArrayList<Classe>(); 
		List<Object[]> listAux = new ArrayList<Object[]>();
		List<Pessoa> chamadas = new ArrayList<Pessoa>();
		Escola escolaSessaoAtual = new Escola();
		Pessoa aux = null;
		Object[] result = null;
		
		escolaSessaoAtual = (Escola) session.getAttribute("escolaLogada");
		
		classes = classeService.findByEscola(escolaSessaoAtual.getId());
		listAux = chamadaService.buscarPorEscolaNaoRealizada(escolaSessaoAtual.getId());
		
		for(int i = 0; i < listAux.size(); i++) {
			aux = new Pessoa();
			
			result = listAux.get(i);
			
			String dataArray[] = result[1].toString().split("-");
			String data = "" + dataArray[2] + "/" + dataArray[1] + "/" + dataArray[0] + "";
			
			aux.setId(Long.parseLong(result[0].toString())); // ID Chamada
			aux.setCpf(data); 								 // Data Chamada
			aux.setNome(result[2].toString());				 // Nome Classe
			aux.setCep(result[3].toString());				 // Ano Classe
			aux.setTelefone(result[4].toString());			 // ID Classe
			aux.setEmail(result[5].toString());				 // Status Chamada
			
			chamadas.add(aux);
		}
		
		model.addAttribute("classes", classes);
		model.addAttribute("chamada", chamada);
		model.addAttribute("chamadas", chamadas);
		
		return "chamada/listaChamadasNaoRealizadasEscola";
	}
	
	@RequestMapping(value = "/getChamadasNaoRealizadasProfessor", method = RequestMethod.GET)
	public String getChamadaProfessorNaoRealizada(Chamada chamada, ModelMap model, HttpSession session) {
		List<Classe> classes = new ArrayList<Classe>(); 
		List<Object[]> listAux = new ArrayList<Object[]>();
		List<Pessoa> chamadas = new ArrayList<Pessoa>();
		Professor professorSessaoAtual = new Professor();
		Pessoa aux = null;
		Object[] result = null;
		
		professorSessaoAtual = (Professor) session.getAttribute("professorLogado");
		
		classes = classeService.findByProfessor(professorSessaoAtual.getEscolaId(), professorSessaoAtual.getId());
		listAux = chamadaService.buscarPorProfessorNaoRealizada(professorSessaoAtual.getId());
		
		for(int i = 0; i < listAux.size(); i++) {
			aux = new Pessoa();
			
			result = listAux.get(i);
			
			String dataArray[] = result[1].toString().split("-");
			String data = "" + dataArray[2] + "/" + dataArray[1] + "/" + dataArray[0] + "";
			
			aux.setId(Long.parseLong(result[0].toString())); // ID Chamada
			aux.setCpf(data); 								 // Data Chamada
			aux.setNome(result[2].toString());				 // Nome Classe
			aux.setCep(result[3].toString());				 // Ano Classe
			aux.setTelefone(result[4].toString());			 // ID Classe
			aux.setEmail(result[5].toString());				 // Status Chamada
			
			chamadas.add(aux);
		}
		
		model.addAttribute("classes", classes);
		model.addAttribute("chamada", chamada);
		model.addAttribute("chamadas", chamadas);
		
		return "chamada/listaChamadasNaoRealizadasProfessor";
	}
}
