package de.docfaust.vbb.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.docfaust.vbb.service.RegisterService;

/**
 * Servlet implementation class JSONServlet.
 */
@WebServlet(description = "Servlet zur Kommunikation via JSON", urlPatterns = { "/register" })
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String USERID = "userid";

	private static final String REGID = "regid";

	@Inject
	private Logger logger;

	@Inject
	private RegisterService registerService;

	/**
	 * Konstruktor für Testzwecke.
	 * @param registerService RegisterService from JUnit
	 * 
	 */
	public RegisterServlet(final RegisterService registerService) {
		this.registerService = registerService;
		this.logger = LoggerFactory.getLogger(getClass());
	}

	/**
	 * Leerer Konstruktor für Servlet initialisierung.
	 */
	public RegisterServlet() {
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 * 
	 * @param request
	 *            Request
	 * @param response
	 *            Response
	 * @throws ServletException
	 *             ServletException
	 * @throws IOException
	 *             IOException
	 */
	@Override
	protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		String regid = request.getParameter(REGID);
		String userid = request.getParameter(USERID);
		String serverResponse = registerService.processRegistration(regid, userid);
		logger.info("Antwort: " + serverResponse);
		response.setContentType("text/html");
		response.setCharacterEncoding(request.getCharacterEncoding());

		PrintWriter pw = response.getWriter();
		pw.write(serverResponse);
		pw.flush();
		pw.close();
	}

}
