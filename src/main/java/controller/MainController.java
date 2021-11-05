package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.dao.PassagemDAO;
import model.Passagem;

@WebServlet(urlPatterns = {"/MainController", "/main", "/insert", "/findPassagem"})
public class MainController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public MainController() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();
		
		switch (action) {
		case "/main":
			passagens(request, response);
			
			break;
		case "/insert":
			novaPassagem(request, response);
			response.sendRedirect("main");
			
			break;
		case "/findPassagem":
			procurarPassagem(request, response);
			System.out.println(action);
			
			break;
		default:
			response.sendRedirect("index.html");
			
			break;
		}
	}
	
	//Listar passagens
	protected void passagens(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Passagem> passagens = PassagemDAO.findAll();
		
		//Encaminhar lista para o documento "passagem.jsp"
		request.setAttribute("passagens", passagens);
		RequestDispatcher rd = request.getRequestDispatcher("passagens.jsp");
		rd.forward(request, response);
	}
	
	//Nova Passagem
	private void novaPassagem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Criando um objeto Passagem
		Passagem passagem = new Passagem(null,
				request.getParameter("nome"),
				request.getParameter("origem"),
				request.getParameter("destino"),
				Double.valueOf(request.getParameter("valor")));
		
		System.out.println(passagem);
		
		PassagemDAO.insert(passagem);
	}
	
	private Passagem procurarPassagem(HttpServletRequest request, HttpServletResponse response) {
		//Recebimento do id do contato que ser� editado
		Integer id = Integer.valueOf(request.getParameter("id"));
		
		return PassagemDAO.findById(id);
	}
	
}
