/*
 * Powered By sunchengrui
 * Since 2014 - 2015
 */package com.dap.cloud.upload.service;

import com.dap.cloud.base.service.BaseService;
import com.dap.cloud.constants.StringConstant;
import com.dap.cloud.exception.CRUDException;
import com.dap.cloud.upload.domain.Uploadfile;
import com.dap.cloud.utils.FileUtils;
import com.dap.cloud.utils.GeneratorUUID;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * @author sunchengrui
 * @version 1.0
 * @since 1.0
 */
@Service
public class NginxUploadfileService extends BaseService {



	/**
	 * 文件上传-本地上传
	 * @throws CRUDException
	 * @throws IOException
	 * @throws IllegalStateException
	 */
	public Uploadfile upload(String url, HttpServletRequest request) throws Exception {
		if(url==null || "".equals(url)) {
			url = "face\\";

		}
		String ctxPath =  StringConstant.NGINX_PATH+ StringConstant.UPLOAD_FILE_URL + url;
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		FileUtils.createDirectory(ctxPath);
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			// 上传文件名
			Uploadfile uploadFile = new Uploadfile();
			MultipartFile mf = entity.getValue();
			String fileName = mf.getOriginalFilename();
			String suffix = fileName.indexOf(".") != -1 ? fileName.substring(fileName.lastIndexOf("."), fileName.length()) : null;
			String UUID = GeneratorUUID.getId();
			String newFileName = UUID + suffix;// 构成新文件名。
			File realFile = new File(ctxPath + newFileName);
			try {
				FileCopyUtils.copy(mf.getBytes(), realFile);
				uploadFile.setId(UUID);
				uploadFile.setViewname(fileName);
				uploadFile.setRealname(newFileName);
				String fileUrl = StringConstant.UPLOAD_FILE_URL + (url==null?"":url);
				uploadFile.setUrl(fileUrl.replaceAll("\\\\", "/"));
				uploadFile.setRealurl(ctxPath.replaceAll("\\\\", "/"));
				uploadFile.setSuffix(suffix);
				uploadFile.setFilesize(mf.getSize()+"");
				return uploadFile;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;

	}


	/**
	 * layedit文件上传-本地上传
	 * @param uploadfile
	 * @throws CRUDException
	 * @throws IOException
	 * @throws IllegalStateException
	 */
	public Uploadfile uploadLayedit(Uploadfile uploadfile, HttpServletRequest request) throws CRUDException {
		String ctxPath = StringConstant.NGINX_PATH+ StringConstant.UPLOAD_FILE_URL;
//		MultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
//		MultipartHttpServletRequest multipartRequest = resolver.resolveMultipart(request);
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		FileUtils.createDirectory(ctxPath);
		String url = uploadfile.getUrl();
		if(url==null || "".equals(url)) {
			url = "temp";
		}
		ctxPath = ctxPath + url;
		FileUtils.createDirectory(ctxPath);
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			// 上传文件名
			Uploadfile uploadFile = new Uploadfile();
			MultipartFile mf = entity.getValue();
			String fileName = mf.getOriginalFilename();
//            String fName = fileName.indexOf(".") != -1 ? fileName.substring(0, fileName.lastIndexOf(".")) : fileName;
			String suffix = fileName.indexOf(".") != -1 ? fileName.substring(fileName.lastIndexOf("."), fileName.length()) : null;
			String UUID = GeneratorUUID.getId();
			String newFileName = UUID + suffix;// 构成新文件名。
			File realFile = new File(ctxPath + newFileName);
			try {
				uploadFile.setId(UUID);
				uploadFile.setViewname(fileName);
				uploadFile.setRealname(newFileName);
				String fileUrl = StringConstant.UPLOAD_FILE_URL + (url==null?"":url);
				uploadFile.setUrl(fileUrl.replaceAll("\\\\", "/"));
				uploadFile.setRealurl(ctxPath.replaceAll("\\\\", "/"));
				uploadFile.setSuffix(suffix);
				uploadFile.setFilesize(mf.getSize()+"");
				/*if(!(url==null?"temp":url).startsWith("temp")) {
					uploadfile.preInsert();
					uploadfileMapper.saveUploadfile(uploadFile);
				}*/
				FileCopyUtils.copy(mf.getBytes(), realFile);
				return uploadFile;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;

	}
}