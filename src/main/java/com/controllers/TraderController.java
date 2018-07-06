package com.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.entity.Address;
import com.entity.AgriCategory;
import com.entity.Agriculture;
import com.entity.Commune;
import com.entity.Hamlet;
import com.entity.Sale;
import com.entity.Trader;
import com.entity.TraderCmt;
import com.entity.TraderNotification;
import com.entity.User;
import com.entity.UserCmt;
import com.models.PieChart;
import com.models.SaleScore;
import com.service.AddressService;
import com.service.AgricultureService;
import com.service.SaleService;
import com.service.TraderService;
import com.service.UserService;

/**
 * @author HiepLe
 * @version 1.0 Nov 2, 2017
 */

@Controller
@RequestMapping("/NhaBuon")
public class TraderController {

	@Autowired
	private AgricultureService agricultureService;

	@Autowired
	private SaleService saleService;

	@Autowired
	private TraderService traderService;

	@Autowired
	private UserService userService;
	
	@Autowired AddressService addressService;

	public int getTraderID() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName();
		return traderService.getTraderIdByPhoneNum(name);
	}

	public Trader getTrader() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName();
		return traderService.getTraderByPhoneNum(name);
	}
	
	public String getTraderName() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName();
		return traderService.getNameByPhoneNum(name);
	}

	public List<TraderNotification> getNotify() {
		return traderService.getNotify(getTraderID());
	}

	// handle my profile request
	@RequestMapping(value={"/",""})
	public String home(ModelMap m){
		int traderID = getTraderID();
		
		List<Sale> selecteds = saleService.getSaleSelectedForMaps(traderID);
		m.addAttribute("selecteds", selecteds);
		
		List<Sale> requests = saleService.getSaleRequestForMaps(traderID);
		m.addAttribute("requests", requests);
		
		List<Sale> sales = saleService.getSaleListByTrader(traderID);
		m.addAttribute("sales", sales);
		
		List<Object[]> agriCharts = saleService.getAgriForChar(traderID);
		m.addAttribute("agriCharts", agriCharts);
		
		List<Object[]> communeCharts = saleService.getCommuneForChar(traderID);
		m.addAttribute("communeCharts", communeCharts);
		
		List<PieChart> areas = saleService.getAreaForChar(traderID);
		m.addAttribute("areas", areas);
		
		m.addAttribute("notifies", getNotify());
		m.addAttribute("userName", getTraderName());
		return "trader/index";
	}

	@RequestMapping(value = { "/thong-tin-tai-khoan" })
	public String about(ModelMap m) {
		Trader trader = getTrader();
		m.addAttribute("trader", trader);

		List<TraderCmt> cmts = traderService.getTraderCmts(trader.getId());
		m.addAttribute("cmts", cmts);
		m.addAttribute("cmtSize", cmts.size());

		Address address = traderService.getAddress(trader.getId());
		m.addAttribute("address", address);
		
		List<String> tradingAgris = saleService.getTradingAgriName(getTraderID());
		m.addAttribute("tradingAgris", tradingAgris);
		
		m.addAttribute("notifies", getNotify());
		m.addAttribute("userName", getTraderName());
		return "trader/my-profile";
	}

	@RequestMapping(value = "/chinh-sua-thong-tin")
	public String getUpdatePage(ModelMap m) {

		// for address
		Trader trader = getTrader();
		m.addAttribute("trader", trader);
		
		Address address = addressService.getAddressByID(trader.getId());
		m.addAttribute("address", address);

		List<Commune> communes = addressService.getCommuneByDistrictID(677);
		m.addAttribute("communes", communes);

		int communeID = addressService.getCommuneIDByHamletID(address.getHamlet().getHamletID());
		m.addAttribute("communeID", communeID);

		List<Hamlet> hamlets = addressService.getHamletByCommnune(communeID);
		m.addAttribute("hamlets", hamlets);

		//for trading agri list
		List<Agriculture> agricultures = agricultureService.getAllAgri();
		m.addAttribute("agris", agricultures);

		List<AgriCategory> agriCategories = agricultureService.getAllAgriCategory();
		m.addAttribute("agriCategories", agriCategories);
		
		List<Integer> tradingAgri = saleService.getTradingAgriID(trader.getId());
		m.addAttribute("tradingList", tradingAgri);
		
		m.addAttribute("notifies", getNotify());
		m.addAttribute("userName", getTraderName());
		return "trader/profile-update";
	}

	@RequestMapping(value = "/cap-nhap-thong-tin")
	public String updateTrader(@ModelAttribute("trader") Trader trader, @RequestParam(value = "hamletID") int hamletID,
			@RequestParam(value = "tradingList") String tradingList) {
		
		Trader oldTrader = getTrader();
		traderService.updateProfile(trader, oldTrader,  hamletID, tradingList);
		return "redirect:/NhaBuon";
	}
	
	@RequestMapping(value = "/dang-nhap")
	public String login(ModelMap m) {
		return "login";
	}

	// end my profile request
	////////////////////////////////////
	// handle user profile request

	@RequestMapping(value = "/thong-tin-nd")
	public String getUserInfo(@RequestParam("id") int userID, ModelMap m) {
		User user = userService.getUserByID(userID);
		m.addAttribute("user", user);

		List<Integer> editCmts = userService.getIDCmtToShowEdit(userID, getTraderID());
		m.addAttribute("editCmts", editCmts);
		
		List<UserCmt> cmts = userService.getUserCmts(userID);
		m.addAttribute("cmts", cmts);
		m.addAttribute("cmtSize", cmts.size());

		Address address = userService.getAddress(userID);
		m.addAttribute("address", address);
		
		m.addAttribute("notifies", getNotify());
		m.addAttribute("userName", getTraderName());
		return "trader/user-profile";
	}

	@RequestMapping(value = "/kiem-tra-them-cmt")
	@ResponseBody
	public boolean checkCmt(@RequestParam("id") int userID, ModelMap m) {

		return traderService.checkConditionToAddCmt(userID, getTraderID());
	}

	@RequestMapping(value = "/them-nhan-xet")
	public String addCmt(@RequestParam("id") int userID, @RequestParam("startRating") int startRating,
			@RequestParam("cmtContent") String content) {
		userService.CreateCmtAboutUser(getTrader(), userID, startRating, content);
		String redirect = "redirect:/NhaBuon/thong-tin-nd?id=" + userID;
		return redirect;
	}
	
	@RequestMapping(value = "/cap-nhap-nhan-xet")
	public String editUserCmt(@RequestParam("traderID") int traderID, @RequestParam("ratingCount") int ratingCount,
			@RequestParam("cmtContent") String content, @RequestParam("cmtID") int cmtID) {
		userService.updateCmtAboutTrader(cmtID, getTrader(), traderID, ratingCount, content);
		String redirect = "redirect:/NhaBuon/thong-tin-nb?id=" + traderID;
		return redirect;
	}

	// end handle user profile request
	////////////////////////////////////////////////////////////
	// handle sale request

	@RequestMapping("/ds-tin-ban")
	public String saleList(ModelMap m) {
		List<SaleScore> minings = saleService.recommendSale(getTraderID());
		m.addAttribute("minings", minings);
		
		m.addAttribute("notifies", getNotify());
		m.addAttribute("userName", getTraderName());
		return "trader/sale-list";
	}

	@RequestMapping("/ds-yeu-cau")
	public String getSaleRequestList(ModelMap m) {
		List<Object[]> datas = saleService.getSaleRequestByTrader(getTraderID());
		m.addAttribute("datas", datas);

		m.addAttribute("notifies", getNotify());
		m.addAttribute("userName", getTraderName());
		return "trader/sale-request";
	}

	@RequestMapping("/tin-da-mua")
	public String getSelectedSale(ModelMap m) {
		List<Object[]> datas = saleService.getSaleSelectedByTrader(getTraderID());
		m.addAttribute("datas", datas);

		m.addAttribute("notifies", getNotify());
		m.addAttribute("userName", getTraderName());
		return "trader/sale-selected";
	}
	
	@RequestMapping("/chi-tiet-tin-ban")
	public String Detail(@RequestParam("id") int saleID, @RequestParam("state") int state, ModelMap m) {

		Sale sale = saleService.getSale(saleID);
		m.addAttribute("sale", sale);

		List<String> links = saleService.getLinkSale(saleID);
		m.addAttribute("links", links);

		Address address = saleService.getAddress(saleID);
		m.addAttribute("address", address);
		
		m.addAttribute("state", state);

		m.addAttribute("notifies", getNotify());
		m.addAttribute("userName", getTraderName());
		return "trader/sale-info";
	}
	
	@RequestMapping("/xoa-tin-da-chon")
	public String deleteSaleSelected(@RequestParam("id") int dataID){
		saleService.deleteSaleSelected(dataID, getTraderID());
		return "redirect:/NhaBuon";
	}

	@RequestMapping(value = "/gui-yeu-cau")
	@ResponseBody
	public void createSaleRequest(@RequestParam("id") int saleID, ModelMap m) {
		saleService.createSaleRequest(getTrader(), saleID);
	}
	
	@RequestMapping(value = "/huy-yeu-cau")
	public String cancelSaleRequest(@RequestParam("id") int dataID, ModelMap m) {
		saleService.cancelSaleRequest(dataID);
		
		return "redirect:/NhaBuon/ds-yeu-cau";
	}

	@RequestMapping(value = "/huy-thong-bao")
	@ResponseBody
	public boolean disableNotification(@ModelAttribute("notifyID") int notifyID) {
		traderService.disableNotification(notifyID);
		return true;
	}

	// end handle sale request
	//////////////////////////////
}
