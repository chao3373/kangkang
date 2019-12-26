package com.shenke.controller.admin;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shenke.entity.JiTai;
import com.shenke.service.JiTaiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 打印Controller
 * 
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/admin/print")
public class PrintAdminController {

	@Autowired
	private JiTaiService jiTaiService;
	/**
	 * 添加选中的属性到application
	 * 
	 * @return
	 */
	@RequestMapping("/printSet")
	public Map<String, Object> printSet(JiTai jiTai){
		Map<String, Object> map = new HashMap<>();
		System.out.println(jiTai.getId());
		System.out.println(jiTai);
		JiTai jiTaiServiceById = jiTaiService.findById(jiTai.getId());
		jiTaiServiceById.setColor(jiTai.getColor());
		jiTaiServiceById.setDao(jiTai.getDao());
		jiTaiServiceById.setClientName(jiTai.getClientName());
		jiTaiServiceById.setNonghu(jiTai.getNonghu());
		jiTaiServiceById.setWeight(jiTai.getWeight());
		jiTaiServiceById.setPrice(jiTai.getPrice());
		jiTaiServiceById.setXuhao(jiTai.getXuhao());
		jiTaiServiceById.setChangshang(jiTai.getChangshang());
		jiTaiServiceById.setChang(jiTai.getChang());
		jiTaiServiceById.setPrintnum(jiTai.getPrintnum());
		jiTaiService.save(jiTaiServiceById);
		map.put("success", true);
		return map;
	}
}
