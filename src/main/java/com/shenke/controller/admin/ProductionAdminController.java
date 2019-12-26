package com.shenke.controller.admin;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.shenke.entity.JiTai;
import com.shenke.entity.JitaiProductionAllot;
import com.shenke.entity.Log;
import com.shenke.entity.SaleListProduct;
import com.shenke.service.JiTaiService;
import com.shenke.service.JitaiProductionAllotService;
import com.shenke.service.LogService;
import com.shenke.service.SaleListProductService;

/**
 * 生产订单Controller
 * 
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/admin/production")
public class ProductionAdminController {

	@Resource
	private JiTaiService jiTaiService;

	@Resource
	private SaleListProductService saleListProductService;

	@Resource
	private LogService logService;

	@Resource
	private JitaiProductionAllotService jitaiProductionAllotService;

	/**
	 * 分配机台信息
	 * 
	 * @return
	 */
	@RequestMapping("/jitaiAllocation")
	public Map<String,Object> aa(Integer []ids,Integer jitai){
		Map<String,Object> map = new HashMap<>();
		JiTai jitai1 = jiTaiService.findById(jitai);
		String state = "分配机台：" + jitai1.getName();
		Long informNumber = this.getInformNumber();
		String iussueState = "未下发";
		saleListProductService.jitaiAllocation(ids,state,jitai1.getId(),informNumber,iussueState);
		map.put("success", true);
		return map;
	}




	/*@RequestMapping("/jitaiAllocation")
	public Map<String, Object> jitaiAllocation(String ids, Integer jitai) {
		Map<String, Object> map = new HashMap<String, Object>();

		JiTai jitai1 = jiTaiService.findById(jitai);
		String[] idarr = ids.split(",");
		List<Integer> idList = new ArrayList<Integer>();
		logService.save(new Log(Log.UPDATE_ACTION, "分配机台"));
		Long informNumber = this.getInformNumber();

		for (int i = 0; i < idarr.length; i++) {
			int id = Integer.parseInt(idarr[i]);
			idList.add(id);
			saleListProductService.auditFailure(id, "分配机台：" + jitai1.getName());
			saleListProductService.updateJitaiId(id, jitai1.getId());
			saleListProductService.updateInformNumber(informNumber, id);
			saleListProductService.updateIussueState("未下发", id);
		}

//		List<SaleListProduct> list = saleListProductService.fandAll(idList);
//
//		for (SaleListProduct saleListProduct : list) {
//			logService.save(new Log(Log.ADD_ACTION, "添加生产通知单"));
//			for (int i = 0; i < saleListProduct.getNum(); i++) {
//				JitaiProductionAllot jitaiProductionAllot = new JitaiProductionAllot();
//				jitaiProductionAllot.setJiTai(jiTaiService.findById(jitai));
//				jitaiProductionAllot.setInformNumber(informNumber);
//				jitaiProductionAllot.setSaleNumber(saleListProduct.getSaleList().getSaleNumber());
//				jitaiProductionAllot.setProductionMessage("幅宽： " + saleListProduct.getModel() + "，厚度："
//						+ saleListProduct.getPrice() + "，长度：" + saleListProduct.getLength() + "，颜色："
//						+ saleListProduct.getColor() + "，要求：" + saleListProduct.getDemand());
//				jitaiProductionAllot.setTaskQuantity(saleListProduct.getSumwight());
//				jitaiProductionAllot.setAllorTime(new Date(System.currentTimeMillis()));
//				jitaiProductionAllot.setAllotState(saleListProduct.getState());
//				jitaiProductionAllot.setIssueState("未下发");
//				jitaiProductionAllot.setSaleListProduct(saleListProduct);
//				Integer countSaleListProduct = jitaiProductionAllotService
//						.countBySaleListProductId(saleListProduct.getId());
//				jitaiProductionAllot.setNum(countSaleListProduct == null ? 0 : countSaleListProduct);
//				jitaiProductionAllotService.save(jitaiProductionAllot);
//				List<JitaiProductionAllot> jitaiProductionAllots = jitaiProductionAllotService
//						.findBySaleListProductId(saleListProduct.getId());
//				for (JitaiProductionAllot jitaiProductionAllo : jitaiProductionAllots) {
//					jitaiProductionAllotService.updateNum(countSaleListProduct,
//							jitaiProductionAllo.getSaleListProduct().getId());
//				}
//			}
//		}

		map.put("success", true);
		return map;
	}*/

	/**
	 * 生成通知单号
	 * 
	 * @return
	 */
	public Long getInformNumber() {
		if (saleListProductService.findMaxInfornNumber() != null) {
			return saleListProductService.findMaxInfornNumber() + 1;
		} else {
			return 1L;
		}
	}
}
