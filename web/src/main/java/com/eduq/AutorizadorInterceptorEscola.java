package com.eduq;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AutorizadorInterceptorEscola extends HandlerInterceptorAdapter {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		String uri = request.getRequestURI();
		
		if(uri.endsWith("entrar") ||
				uri.endsWith("entrarProfessor") ||
				uri.endsWith("editarProfessor") ||
				uri.endsWith("professor/resetSenha") ||
				uri.endsWith("cadastrarEscola") ||
				uri.endsWith("resetSenha") ||
				uri.endsWith("aguardoConfirmacao") ||
				uri.endsWith("completarCadastro") ||
				uri.endsWith("/") ||
					uri.contains("css") ||
					uri.contains("js") ||
					uri.contains("img") ||
                uri.endsWith("/escola/entrar") ||
                uri.endsWith("/escola/restaurarSenha") ||
                uri.endsWith("/escola/completarPerfil") ||
                uri.endsWith("/escola/cadastro") ||
                uri.endsWith("/professor/resetSenha") ||
                uri.endsWith("/escola/enviarDuvida/index") ||
                        uri.contains("resources")){
            return true;
        }
		
		if(request.getSession().getAttribute("escolaLogada") == null) {
			if (request.getSession().getAttribute("professorLogado") != null){
				return true;
			}
		}else {
			return true;
		}
		
		response.sendRedirect("/entrar");
		return false;
    } 
}
