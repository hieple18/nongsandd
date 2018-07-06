package com.controllers;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.entity.Address;
import com.entity.Agriculture;
import com.entity.Commune;
import com.entity.Hamlet;
import com.entity.ImgLink;
import com.entity.Sale;
import com.entity.Trader;
import com.entity.TraderCmt;
import com.entity.TradingData;
import com.entity.User;
import com.entity.UserCmt;
import com.entity.UserNotification;
import com.service.AddressService;
import com.service.AgricultureService;
import com.service.SaleService;
import com.service.TraderService;
import com.service.UserService;

/**
 * @author HiepLe
 * @version 1.0 Nov 3, 2017
 */

@Controller
@RequestMapping("/NguoiDung")
public class UserController {

	@Autowired
	private AgricultureService agricultureService;

	@Autowired
	private AddressService addressService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private TraderService traderService;
	
	@Autowired
	private SaleService saleService;

	public int getUserID() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName();
		return userService.getUserIdByPhoneNum(name);
	}

	public User getUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName();
		return userService.getUserByPhoneNum(name);
	}
	
	public String getName() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName();
		return userService.getNameByPhoneNum(name);
	}

	public List<UserNotification> getNotify() {
		return userService.getNotify(getUserID());
	}
	
	// handle my profile request

	@RequestMapping(value = { "", "/" })
	public String home(ModelMap m) {
		List<Sale> sales = saleService.getListSaleByUser(getUserID());
		m.addAttribute("sales", sales);

		m.addAttribute("notifies", getNotify());
		m.addAttribute("userName", getName());
		return "user/index";
	}

	@RequestMapping(value = { "/thong-tin-tai-khoan" })
	public String about(ModelMap m) {
		User user = getUser();
		m.addAttribute("user", user);
		
		List<UserCmt> cmts = userService.getUserCmts(user.getId());
		m.addAttribute("cmts", cmts);
		m.addAttribute("cmtSize", cmts.size());
		
		Address address = userService.getAddress(user.getId());
		m.addAttribute("address", address);

		m.addAttribute("notifies", getNotify());
		m.addAttribute("userName", getName());
		return "user/my-profile";
	}
	
	@RequestMapping(value = "/chinh-sua-thong-tin")
	public String getUpdatePage(ModelMap m) {
		User user = getUser();
		m.addAttribute("user", user);
		
		Address address = addressService.getAddressByID(user.getId());
		m.addAttribute("address", address);

		List<Commune> communes = addressService.getCommuneByDistrictID(677);
		m.addAttribute("communes", communes);

		int communeID = addressService.getCommuneIDByHamletID(address.getHamlet().getHamletID());
		m.addAttribute("communeID", communeID);

		List<Hamlet> hamlets = addressService.getHamletByCommnune(communeID);
		m.addAttribute("hamlets", hamlets);
		
		m.addAttribute("notifies", getNotify());
		m.addAttribute("userName", getName());
		return "user/profile-update";
	}
	
	@RequestMapping(value = "/cap-nhap-thong-tin")
	public String updateUser(@ModelAttribute("user") User user, @RequestParam(value = "hamletID") int hamletID) {
		User oldUser = getUser();
		userService.updateUser(user, oldUser, hamletID);
		return "redirect:/NguoiDung/thong-tin-tai-khoan";
	}
	
	// end handle my profile request
	//////////////////////////////////////////////////
	// handle trader profile request
	
	@RequestMapping(value = "/thong-tin-nb")
	public String getUserInfo(@RequestParam("id") int traderID, ModelMap m) {
		Trader trader = traderService.getTrader(traderID);
		m.addAttribute("trader", trader);
		
		List<Integer> editCmts = traderService.getIDCmtToShowEdit(getUserID(), traderID);
		m.addAttribute("editCmts", editCmts);
		
		List<TraderCmt> cmts = traderService.getTraderCmts(traderID);
		m.addAttribute("cmts", cmts);
		m.addAttribute("cmtSize", cmts.size());
		
		Address address = traderService.getAddress(traderID);
		m.addAttribute("address", address);
		
		List<String> tradingAgris = saleService.getTradingAgriName(traderID);
		m.addAttribute("tradingAgris", tradingAgris);
		
		m.addAttribute("notifies", getNotify());
		m.addAttribute("userName", getName());
		return "user/trader-profile";
	}

	@RequestMapping(value = "/kiem-tra-them-cmt")
	@ResponseBody
	public boolean checkCmt(@RequestParam("id") int traderID, ModelMap m) {

		return userService.checkConditionToAddCmt(getUserID(), traderID);
	}

	@RequestMapping(value = "/them-nhan-xet")
	public String addUserCmt(@RequestParam("id") int traderID, @RequestParam("ratingCount") int ratingCount,
			@RequestParam("cmtContent") String content) {
		traderService.CreateCmtAboutTrader(getUser(), traderID, ratingCount, content);
		String redirect = "redirect:/NguoiDung/thong-tin-nb?id=" + traderID;
		return redirect;
	}
	
	@RequestMapping(value = "/cap-nhap-nhan-xet")
	public String editUserCmt(@RequestParam("traderID") int traderID, @RequestParam("ratingCount") int ratingCount,
			@RequestParam("cmtContent") String content, @RequestParam("cmtID") int cmtID) {
		traderService.updateCmtAboutTrader(cmtID, getUser(), traderID, ratingCount, content);
		String redirect = "redirect:/NguoiDung/thong-tin-nb?id=" + traderID;
		return redirect;
	}
	
	@RequestMapping(value = "/xoa-nhan-xet")
	public String deleteUserCmt(@RequestParam("id") int id) {
		traderService.deleteCmtAboutTrader(getUser(), id);
		return "redirect:/NguoiDung/";
	}
	
	// end handle trader profile request
	/////////////////////////////////////////////////////
	// handle sale request

	@RequestMapping(value = "/dang-tin")
	public String uploadSaleInfo(ModelMap m) {
		boolean creatable = saleService.checkToCreateSale(getUserID());
		m.addAttribute("creatable", creatable);
		
		Address address = addressService.getAddressByID(getUser().getAddress().getId());
		m.addAttribute("address", address);

		List<Commune> communes = addressService.getCommuneByDistrictID(677);
		m.addAttribute("communes", communes);

		int communeID = addressService.getCommuneIDByHamletID(address.getHamlet().getHamletID());
		m.addAttribute("communeID", communeID);

		List<Hamlet> hamlets = addressService.getHamletByCommnune(communeID);
		m.addAttribute("hamlets", hamlets);

		List<Agriculture> agricultures = agricultureService.getAllAgri();
		m.addAttribute("agris", agricultures);

		Sale sale = new Sale();
		sale.setAddress(new Address());
		m.addAttribute("sale", sale);
		m.addAttribute("userID", getUserID());

		m.addAttribute("notifies", getNotify());
		m.addAttribute("userName", getName());
		return "user/upload-sale-info";
	}

	@RequestMapping(value = "/create-sale")
	public String createSale(@ModelAttribute("sale") Sale sale, @RequestParam(value = "hamletID") int hamletID,
			@RequestParam(value = "links[]") List<String> links) {
		boolean creatable = saleService.checkToCreateSale(getUserID());
		if(creatable){
			saleService.createSale(sale, links, hamletID, getUser());
			return "redirect:/NguoiDung/";
		}
		
		return "trader/404";
	}
	
	@RequestMapping(value = "/xoa-tin-ban")
	public String deleteSale(@RequestParam(value = "id") int saleID){
		
		saleService.deleteSale(saleID, getUser());
		return "redirect:/NguoiDung/";
	}

	@RequestMapping(value = "/huy-thong-bao")
	@ResponseBody
	public boolean disableNotification(@ModelAttribute("notifyID") int notifyID) {

		userService.disableNotification(notifyID);
		return true;
	}

	@RequestMapping(value = "/ds-yeu-cau")
	public String getSaleRequest(@RequestParam(value = "id") int saleID, ModelMap m) {
		List<TradingData> requests = saleService.getSaleRequest(saleID);
		m.addAttribute("requests", requests);

		m.addAttribute("notifies", getNotify());
		m.addAttribute("userName", getName());
		return "user/sale-request";
	}
	
	@RequestMapping(value="ds-tin-ban")
	public String getSaleSelected(ModelMap m){
		List<Object[]> datas = saleService.getSaleSelectedByUser(getUserID());
		m.addAttribute("datas", datas);

		m.addAttribute("notifies", getNotify());
		m.addAttribute("userName", getName());
		return "user/sale-selected";
	}

	@RequestMapping(value = "/xac-nhan-yeu-cau")
	public String confirmSaleRequest(@RequestParam(value = "id") int requestID, ModelMap m) {
		saleService.confirmRequest(requestID, getUser());
		return "redirect:/NguoiDung/ds-tin-ban";
	}
	
	@RequestMapping(value = "/khoi-phuc-tin-ban")
	public String restoreSaleRemoved(@RequestParam(value = "id") int saleID, ModelMap m) {
		saleService.restoreRequest(saleID, getUser());

		return "redirect:/NguoiDung/ds-yeu-cau?id=" + saleID;
	}

	@RequestMapping(value = "/chi-tiet-tin-ban")
	public String getSaleDetail(@RequestParam(value = "id") int saleID, ModelMap m) {

		Sale sale = saleService.getSale(saleID);
		m.addAttribute("sale", sale);
		m.addAttribute("userName", getUser().getName());

		List<String> links = saleService.getLinkSale(saleID);
		m.addAttribute("links", links);
		
		Address address = saleService.getAddress(saleID);
		m.addAttribute("address", address);

		m.addAttribute("notifies", getNotify());
		m.addAttribute("userName", getName());
		return "user/sale-info";
	}

	@RequestMapping(value = "/chinh-sua-tin-ban")
	public String getUpdateSalePage(@RequestParam(value = "id") int saleID, ModelMap m) {
		Sale sale = saleService.getSale(saleID);
		m.addAttribute("sale", sale);

		// for address
		Address address = saleService.getAddress(saleID);
		m.addAttribute("address", address);

		List<Commune> communes = addressService.getCommuneByDistrictID(677);
		m.addAttribute("communes", communes);

		int communeID = addressService.getCommuneIDByHamletID(address.getHamlet().getHamletID());
		m.addAttribute("communeID", communeID);

		List<Hamlet> hamlets = addressService.getHamletByCommnune(communeID);
		m.addAttribute("hamlets", hamlets);

		// for link
		List<ImgLink> links = saleService.getImgLink(saleID);
		m.addAttribute("links", links);
		m.addAttribute("linksSize", links.size());

		// for agriculture
		List<Agriculture> agricultures = agricultureService.getAllAgri();
		m.addAttribute("agris", agricultures);

		m.addAttribute("notifies", getNotify());
		m.addAttribute("userName", getName());
		return "user/sale-update";
	}

	@RequestMapping(value = "/cap-nhap-tin-ban")
	public String UpdateSale(@ModelAttribute("sale") Sale sale, @RequestParam(value = "hamletID") int hamletID,
			@RequestParam(value = "addlinks[]", required = false) List<String> addLinks,
			@RequestParam(value = "dellinks[]", required = false) List<String> delLinks) {

		saleService.updateSale(getUser(), sale, addLinks, delLinks, hamletID);

		return "redirect:/NguoiDung/";
	}

	@RequestMapping(value = "/lay-link-tin-ban")
	public String getLinkSale(@RequestParam(value = "saleID") int saleID, ModelMap m) {
		List<String> links = saleService.getLinkSale(saleID);
		m.addAttribute("links", links);

		m.addAttribute("notifies", getNotify());
		m.addAttribute("userName", getName());
		return "user/img-popup";
	}
	
	

	//////////////////////////////
}
