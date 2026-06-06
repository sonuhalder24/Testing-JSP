package com;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@MultipartConfig
public class FileUploadServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		handleRequest(request, response);
	}

	public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uploadDir = getServletContext().getRealPath("/") + "uploaded_files";
		File dir = new File(uploadDir);
		if (!dir.exists()) {
			dir.mkdirs();
		}

		Part filePart = request.getPart("fileAttachment");
		String fileName = getFileName(filePart);

		if (fileName != null && !fileName.isEmpty()) {
			File outputFile = new File(dir, fileName);
			InputStream is = null;
			FileOutputStream fos = null;
			try {
				is = filePart.getInputStream();
				fos = new FileOutputStream(outputFile);
				byte[] buffer = new byte[1024];
				int bytesRead;
				while ((bytesRead = is.read(buffer)) != -1) {
					fos.write(buffer, 0, bytesRead);
				}
			} finally {
				if (is != null) try { is.close(); } catch (IOException e) {}
				if (fos != null) try { fos.close(); } catch (IOException e) {}
			}
		}

		response.sendRedirect("fileuploadresponse.jsp");
	}

	private String getFileName(Part part) {
		String contentDisp = part.getHeader("content-disposition");
		for (String cd : contentDisp.split(";")) {
			if (cd.trim().startsWith("filename")) {
				return cd.substring(cd.indexOf('"') + 1, cd.lastIndexOf('"'));
			}
		}
		return null;
	}
}
