package com.eduq.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.eduq.model.entity.Aviso;
import com.eduq.model.entity.DiaLetivo;
import com.eduq.model.entity.Escola;
import com.eduq.model.entity.Evento;
import com.eduq.model.entity.Professor;
import com.eduq.model.service.AvisoService;
import com.eduq.model.service.EscolaService;
import com.eduq.model.service.EventoService;

@RestController
public class ApplicationRestController {

	@Autowired
	private EventoService eventoService;
	
	@Autowired
	private AvisoService avisoService;
	
	@Autowired
	private EscolaService escolaService;
	
	@RequestMapping("/getEventos")
	public List<Evento> getEventosEscola(HttpSession session){
		Escola escola = new Escola();
		List<Evento> eventos = new ArrayList<>();
		
		escola = (Escola) session.getAttribute("escolaLogada");
		
		eventos = eventoService.buscarPorEscola(escola.getId());
		
		return eventos;
	}
	
	@RequestMapping("/getEventosProfessor")
	public List<Evento> getEventosProfessor(HttpSession session){
		Professor professor = new Professor();
		List<Evento> eventos = new ArrayList<>();
		
		professor = (Professor) session.getAttribute("professorLogado");
		
		eventos = eventoService.buscarPorEscola(professor.getEscolaId());
		
		return eventos;
	}
	
	@RequestMapping("/escola/getDiasLetivos")
	public List<DiaLetivo> getDiasLetivos(HttpSession session){
		Escola escola = new Escola();
		List<DiaLetivo> diasLetivos = new ArrayList<>();
		List<Object[]> listAux = new ArrayList<>();
		DiaLetivo dia = null;
		Object[] aux = null;
		
		escola = (Escola) session.getAttribute("escolaLogada");
		
		listAux = escolaService.getDiasLetivos(escola.getId());
		
		for(int i = 0; i < listAux.size(); i++) {
			dia = new DiaLetivo();
			
			aux = listAux.get(i);
			dia.setId(Long.parseLong(aux[0].toString()));
			dia.setEscolaId(Long.parseLong(aux[1].toString()));
			
			String dataArray[] = aux[2].toString().split("-");
			String data;
			
			switch (dataArray[1]) {
			case "jan":
				data = "" + dataArray[2] + "/01/" + dataArray[0] + "";
				break;
				
			case "fev":
				data = "" + dataArray[2] + "/02/" + dataArray[0] + "";
				break;
				
			case "mar":
				data = "" + dataArray[2] + "/03/" + dataArray[0] + "";
				break;
				
			case "abr":
				data = "" + dataArray[2] + "/04/" + dataArray[0] + "";
				break;
				
			case "mai":
				data = "" + dataArray[2] + "/05/" + dataArray[0] + "";
				break;
				
			case "jun":
				data = "" + dataArray[2] + "/06/" + dataArray[0] + "";
				break;
				
			case "jul":
				data = "" + dataArray[2] + "/07/" + dataArray[0] + "";
				break;
				
			case "ago":
				data = "" + dataArray[2] + "/08/" + dataArray[0] + "";
				break;
				
			case "set":
				data = "" + dataArray[2] + "/09/" + dataArray[0] + "";
				break;
				
			case "out":
				data = "" + dataArray[2] + "/10/" + dataArray[0] + "";
				break;
				
			case "nov":
				data = "" + dataArray[2] + "/11/" + dataArray[0] + "";
				break;

			default:
				data = "" + dataArray[2] + "/12/" + dataArray[0] + "";
				break;
			}
			
			dia.setDataAviso(data);
			diasLetivos.add(dia);
		}
		
		return diasLetivos;
	}
	
	@RequestMapping("/professor/getDiasLetivos")
	public List<DiaLetivo> getDiasLetivosProfessor(HttpSession session){
		Professor professor = new Professor();
		List<DiaLetivo> diasLetivos = new ArrayList<>();
		List<Object[]> listAux = new ArrayList<>();
		DiaLetivo dia = null;
		Object[] aux = null;
		
		professor = (Professor) session.getAttribute("professorLogado");
		
		listAux = escolaService.getDiasLetivos(professor.getEscolaId());
		
		for(int i = 0; i < listAux.size(); i++) {
			dia = new DiaLetivo();
			
			aux = listAux.get(i);
			dia.setId(Long.parseLong(aux[0].toString()));
			dia.setEscolaId(Long.parseLong(aux[1].toString()));
			
			String dataArray[] = aux[2].toString().split("-");
			String data;
			
			switch (dataArray[1]) {
			case "jan":
				data = "" + dataArray[2] + "/01/" + dataArray[0] + "";
				break;
				
			case "fev":
				data = "" + dataArray[2] + "/02/" + dataArray[0] + "";
				break;
				
			case "mar":
				data = "" + dataArray[2] + "/03/" + dataArray[0] + "";
				break;
				
			case "abr":
				data = "" + dataArray[2] + "/04/" + dataArray[0] + "";
				break;
				
			case "mai":
				data = "" + dataArray[2] + "/05/" + dataArray[0] + "";
				break;
				
			case "jun":
				data = "" + dataArray[2] + "/06/" + dataArray[0] + "";
				break;
				
			case "jul":
				data = "" + dataArray[2] + "/07/" + dataArray[0] + "";
				break;
				
			case "ago":
				data = "" + dataArray[2] + "/08/" + dataArray[0] + "";
				break;
				
			case "set":
				data = "" + dataArray[2] + "/09/" + dataArray[0] + "";
				break;
				
			case "out":
				data = "" + dataArray[2] + "/10/" + dataArray[0] + "";
				break;
				
			case "nov":
				data = "" + dataArray[2] + "/11/" + dataArray[0] + "";
				break;

			default:
				data = "" + dataArray[2] + "/12/" + dataArray[0] + "";
				break;
			}
			
			dia.setDataAviso(data);
			diasLetivos.add(dia);
		}
		
		return diasLetivos;
	}
	
	@RequestMapping(value = "/getAvisos", method = RequestMethod.GET)
	public List<Aviso> getAvisos(HttpSession session) {
		Escola escola = new Escola();
		List<Object[]> listAux = new ArrayList<>();
		List<Aviso> avisos = new ArrayList<>();
		Aviso aviso = null;
		Object[] result = null;
		
		escola = (Escola) session.getAttribute("escolaLogada");
		listAux = avisoService.buscarPorEscola(escola.getId());
		
		for(int i = 0; i < listAux.size(); i++) {
			aviso = new Aviso();
			
			result = listAux.get(i);
			
			String dataArray[] = result[3].toString().split("-");
			String data = "" + dataArray[2] + "/" + dataArray[1] + "/" + dataArray[0] + "";
			
			aviso.setId(Long.parseLong(result[0].toString()));
			aviso.setAnoClasse(result[1].toString());
			aviso.setConteudo(result[2].toString());
			aviso.setDataAviso(data);
			aviso.setTipoAviso(result[5].toString());
			aviso.setTituloAviso(result[6].toString());
			
			avisos.add(aviso);
		}
				
		return avisos;
	}
	
	@RequestMapping(value = "/getAvisosProfessor", method = RequestMethod.GET)
	public List<Aviso> getAvisosProfessor(HttpSession session) {
		Professor professorSessaoAtual = new Professor();
		List<Object[]> listAux = new ArrayList<>();
		List<Aviso> avisos = new ArrayList<>();
		Aviso aviso = null;
		Object[] result = null;
		
		professorSessaoAtual = (Professor) session.getAttribute("professorLogado");
		listAux = avisoService.buscarPorProfessor(professorSessaoAtual.getEscolaId());
		
		for(int i = 0; i < listAux.size(); i++) {
			aviso = new Aviso();
			
			result = listAux.get(i);
			
			String dataArray[] = result[3].toString().split("-");
			String data = "" + dataArray[2] + "/" + dataArray[1] + "/" + dataArray[0] + "";
			
			aviso.setId(Long.parseLong(result[0].toString()));
			aviso.setAnoClasse(result[1].toString());
			aviso.setConteudo(result[2].toString());
			aviso.setDataAviso(data);
			aviso.setTipoAviso(result[5].toString());
			aviso.setTituloAviso(result[6].toString());
			
			avisos.add(aviso);
		}
				
		return avisos;
	}
	
	@RequestMapping("/getNomeEscola")
	public String getNomeEscola(HttpSession session){
		Escola escola = new Escola();
		String nome;
		
		escola = (Escola) session.getAttribute("escolaLogada");
		nome = escola.getNome();
		
		return nome;
	}
	
	@RequestMapping("/getNomeProfessor")
	public String getNomeProfessor(HttpSession session){
		Professor professorSessaoAtual = new Professor();
		String nome;
		
		professorSessaoAtual = (Professor) session.getAttribute("professorLogado");
		nome = professorSessaoAtual.getNome();
		
		return nome;
	}
}
