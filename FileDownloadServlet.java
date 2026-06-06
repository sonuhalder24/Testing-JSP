package com;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FileDownloadServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	public static int BUFFER_SIZE = 1024 * 100;

	public FileDownloadServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		handleRequest(request, response);
	}

	public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String fileName = request.getParameter("fileName");
		if (fileName == null || fileName.isEmpty()) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "fileName parameter is required");
			return;
		}

		String uploadDir = getServletContext().getRealPath("/") + "uploaded_files";
		File file = new File(uploadDir, new File(fileName).getName());

		if (!file.exists()) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found");
			return;
		}

		response.setContentType("application/octet-stream");
		response.setContentLength((int) file.length());
		response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");

		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			OutputStream os = response.getOutputStream();
			byte[] buffer = new byte[BUFFER_SIZE];
			int bytesRead;
			while ((bytesRead = fis.read(buffer)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
			os.flush();
		} finally {
			if (fis != null) try { fis.close(); } catch (IOException e) {}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
