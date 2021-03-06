package Controlador;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Modelo.Administrador;
import ModeloDAO.AdministradorDAO;

@WebServlet("/SInicio")
public class Servlet_Inicio extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	Administrador admin = new Administrador();
	
    public Servlet_Inicio() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		admin.setUserName(request.getParameter("Usuario"));
    	admin.setPassword(request.getParameter("Password"));
    	
    	AdministradorDAO admindao = new AdministradorDAO();
    	HttpSession sesion_user = request.getSession(true);
    	
    	if(admindao.autenticacion(admin)) {
    		
    		sesion_user.setAttribute("usuario",admin.getUserName() );
    		
    		response.setContentType("text/html");
    		response.setCharacterEncoding("UTF-8");
    		//response.sendRedirect("Vistas/views/Inicio.jsp");
    		response.getWriter().write("true");
    		
    		
    	}else {
    		sesion_user.setAttribute("usuario", null);
    		
    		response.setContentType("text/html");
    		response.setCharacterEncoding("UTF-8");
    		//response.sendRedirect("index.jsp");
    		response.getWriter().write("false");
    		
    		
    	}
    	
	}

}
