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

import de.docfaust.vbb.service.VBBServices;

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
	private VBBServices services;

	/**
	 * Konstruktor für Testzwecke.
	 * 
	 * @param services
	 *            services Instanz
	 */
	public RegisterServlet(final VBBServices services) {
		this.services = services;
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
		String serverResponse = services.processRegistration(regid, userid);
		logger.info("Antwort: " + serverResponse);
		response.setContentType("text/html");
		response.setCharacterEncoding(request.getCharacterEncoding());

		PrintWriter pw = response.getWriter();
		pw.write(serverResponse);
		pw.flush();
		pw.close();
	}

}
