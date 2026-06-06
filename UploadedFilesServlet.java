package com;

import java.io.File;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UploadedFilesServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public UploadedFilesServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		handleRequest(request, response);
	}

	public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uploadDir = getServletContext().getRealPath("/") + "uploaded_files";
		File dir = new File(uploadDir);
		File[] files = dir.exists() ? dir.listFiles() : new File[0];
		if (files == null) {
			files = new File[0];
		}

		request.setAttribute("files", files);
		RequestDispatcher rd = request.getRequestDispatcher("/allfiles.jsp");
		rd.forward(request, response);
	}
}
