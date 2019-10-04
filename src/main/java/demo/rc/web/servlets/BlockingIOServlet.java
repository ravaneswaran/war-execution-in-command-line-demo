package demo.rc.web.servlets;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BlockingIOServlet extends AbstractServlet {

	private static final long serialVersionUID = 1L;
	
	public static final Logger logger = Logger.getLogger(BlockingIOServlet.class.getName());

	@Override
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().print("This is blocking IO servlet, must wait till i finish my work");
	}

}
