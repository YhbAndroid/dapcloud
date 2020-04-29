/*
 * Powered By sunchengrui
 * Since 2014 - 2015
 */
package com.dap.cloud.upload.controller;

import com.dap.cloud.base.controller.BaseController;
import com.dap.cloud.constants.StringConstant;
import com.dap.cloud.upload.domain.FileUrl;
import com.dap.cloud.upload.domain.Uploadfile;
import com.dap.cloud.upload.service.NginxUploadfileService;
import com.dap.cloud.utils.GeneratorUUID;
import com.dap.cloud.utils.RequestResponseUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sunchengrui
 * @version 1.0
 * @since 1.0
 */
@Controller
public class UploadfileController extends BaseController {

	@Autowired
	private NginxUploadfileService uploadfileService;

	/**
	 * 保存新增的数据
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/uploadFile")
	@ResponseBody
	public Object uploadFile(HttpSession session,
							 HttpServletResponse response, HttpServletRequest request,
							 ModelMap modelMap, String url) throws Exception{
		try {
			Uploadfile uf = uploadfileService.upload(url, request);
			Map<String, String> result = new HashMap<String, String>();
			result.put("url", uf.getUrl());
			result.put("realname", uf.getRealname());
			result.put("size", uf.getFilesize());
			JSONObject jsonObject = JSONObject.fromObject(result);
			RequestResponseUtil.putResponseStr(session, response, request, jsonObject.toString());
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 富文本图片上传
	 * @param session
	 * @param response
	 * @param request
	 * @param modelMap
	 * @param url
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/layuiEditUpload")
	@ResponseBody
	public Object layuiEditUpload(HttpSession session,
								  HttpServletResponse response, HttpServletRequest request,
								  ModelMap modelMap, String url) throws Exception{
		Uploadfile uf = uploadfileService.upload(url, request);
		Map map = new HashMap();
		//返回图片上传之后的路径需要加上当前的项目名
		map.put("src",   "/resources/" + StringConstant.UPLOAD_FILE_URL + "face/" +  uf.getRealname());
		Map result= new HashMap();
		result.put("code", 0);
		result.put("data", map);
		JSONObject jsonObject = JSONObject.fromObject(result);
		RequestResponseUtil.putResponseStr(session, response, request, jsonObject.toString());
		return null;
	}
	/**
	 * 原生图片上传方法
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/uploadNginx")
	@ResponseBody
	public Object uploadNginx(@RequestParam(value="file",required=false)MultipartFile file, HttpSession session,
							  HttpServletResponse response, HttpServletRequest request,
							  ModelMap modelMap) throws Exception{
		Map<String, String> result = new HashMap<String, String>();
		try {
			File targetFile=null;
			String url="";//返回存储路径
			String fileName=file.getOriginalFilename();//获取文件名加后缀
			if(fileName!=null&&fileName!=""){
				String path =  StringConstant.NGINX_PATH+ StringConstant.UPLOAD_FILE_URL;
				String fileF = fileName.substring(fileName.lastIndexOf("."), fileName.length());//文件后缀
				fileName= GeneratorUUID.getId()+fileF;//新的文件名
				//获取文件夹路径
				File file1 =new File(path);
				//如果文件夹不存在则创建
				if(!file1 .exists()  && !file1 .isDirectory()){
					file1 .mkdir();
				}
				//将图片存入文件夹
				targetFile = new File(file1, fileName);
				try {
					//将上传的文件写到服务器上指定的文件。
					file.transferTo(targetFile);
					result.put("url", StringConstant.UPLOAD_FILE_URL + "face/");
					result.put("realname", fileName);
					result.put("success", "T");
					JSONObject jsonObject = JSONObject.fromObject(result);
					RequestResponseUtil.putResponseStr(session, response, request, jsonObject.toString());
				} catch (Exception e) {
					e.printStackTrace();
					result.put("success", "F");
					JSONObject jsonObject = JSONObject.fromObject(result);
					RequestResponseUtil.putResponseStr(session, response, request, jsonObject.toString());
				}
			}
		}catch (Exception e){
			e.printStackTrace();
			result.put("success", "F");
			JSONObject jsonObject = JSONObject.fromObject(result);
			RequestResponseUtil.putResponseStr(session, response, request, jsonObject.toString());
		}
		return null;
	}

	/**
	 * 文件上传-本地上传
	 * @param session
	 * @param response
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/UploadServlet")
	@ResponseBody
	public Object UploadServlet(HttpSession session,
								HttpServletResponse response, HttpServletRequest request,
								ModelMap modelMap) throws Exception {
		Map map = new HashMap();
		List<FileUrl> Files = new ArrayList<FileUrl>();
		MultipartHttpServletRequest Murequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> files = Murequest.getFileMap();//得到文件map对象
		String upaloadUrl =  StringConstant.NGINX_PATH+ StringConstant.UPLOAD_FILE_URL;
		File dir = new File(upaloadUrl);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		for (MultipartFile file : files.values()) {
			String fileName = file.getOriginalFilename();
			//
			String suffix = fileName.indexOf(".") != -1 ? fileName.substring(fileName.lastIndexOf("."), fileName.length()) : null;
			String UUID = GeneratorUUID.getId();
			String newFileName = UUID + suffix;// 构成新文件名。
			File tagetFile = new File(upaloadUrl + newFileName);//创建文件对象
			if (!tagetFile.exists()) {//文件名不存在 则新建文件，并将文件复制到新建文件中
				try {
					//将原图上传到指定目录
					tagetFile.createNewFile();
					file.transferTo(tagetFile.getAbsoluteFile());
					FileUrl f = new FileUrl();
					f.setUrl(StringConstant.UPLOAD_FILE_URL +newFileName);
					Files.add(f);
				} catch (Exception e) {
					e.printStackTrace();
					map.put("success", "F");
					map.put("message", "图片上传失败");
					map.put("data", Files);
					return map;
				}
			}
		}
		map.put("success", "T");
		map.put("message", "图片上传成功");
		map.put("data", Files);
		return map;
	}
}
