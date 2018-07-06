package com.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.entity.Account;
import com.entity.Address;
import com.entity.AgriCategory;
import com.entity.Agriculture;
import com.entity.Commune;
import com.entity.Hamlet;
import com.entity.Trader;
import com.service.AddressService;
import com.service.AgricultureService;
import com.service.SaleService;
import com.service.TraderService;

/**
 * @author: HiepLe
 * @version: Jun 24, 2018
 */

@Controller
@RequestMapping("/TempNB")
public class TempTraderController {

	@Autowired
	private AgricultureService agricultureService;

	@Autowired
	private AddressService addressService;

	@Autowired
	private TraderService traderService;

	@Autowired
	private SaleService saleService;

	///////////////////////////////////////////////
	// handle trader register request
	
	
	@RequestMapping(value = "/yc-xac-thuc-sdt")
    public String verifyPhone(){
		return "trader/phone-verify";
    }

	@RequestMapping(value = "/xac-thuc-sdt")
	@ResponseBody
	public boolean checkPhoneVerify(@RequestParam("phone") String phoneNum) {
		return traderService.checkVerifyTraderByAdmin(phoneNum);
	}

	@RequestMapping(value = "/dang-ki-tk")
	public String tradeRegister(@RequestParam("phone") String phoneNum, ModelMap m) {
		if(!traderService.checkVerifyTraderByAdmin(phoneNum))
			return "trader/404";
		
		List<Commune> communes = addressService.getCommuneByDistrictID(677);
		m.put("communes", communes);

		List<Agriculture> agricultures = agricultureService.getAllAgri();
		m.addAttribute("agris", agricultures);

		List<AgriCategory> agriCategories = agricultureService.getAllAgriCategory();
		m.addAttribute("agriCategories", agriCategories);

		Trader trader = new Trader();
		Address address = new Address();
		Account account = new Account();
		trader.setAddress(address);
		trader.setAccount(account);
		m.put("trader", trader);

		m.addAttribute("phone", phoneNum);
		return "trader/register";
	}

	@RequestMapping(value = "/tao-tk")
	public String createTrader(@ModelAttribute("trader") Trader trader, @RequestParam(value = "hamletID") int hamletID,
			@RequestParam(value = "tradingList") String tradingList, ModelMap m) {

		int traderID = traderService.checkToCreateTrader(trader, hamletID, tradingList);
		if(traderID == -1) // this phone number already used
			return "404";
		
		m.addAttribute("id", traderID);
        m.addAttribute("title", "Mã xác nhận đã được gửi về SDT: " + trader.getPhoneNum());
        m.addAttribute("action", "/NongSanDD/TempNB/xac-nhan-tk");
        m.addAttribute("success", "/NongSanDD/NhaBuon");
        return "user/code-verify";
	}
	
	@RequestMapping(value = "/kiem-tra-sdt")
    @ResponseBody
    public int checkTraderPhoneNum(@RequestParam("phoneNum") String phoneNum){
    	return traderService.checkPhoneNum(phoneNum);
    }
	
	@RequestMapping(value = "/kiem-tra-xt-sdt")
    @ResponseBody
    public int checkVerifyTraderPhoneNum(@RequestParam("phoneNum") String phoneNum){
    	return traderService.checkVerifyPhoneNum(phoneNum);
    }
	
	@RequestMapping(value="/xac-nhan-tk")
    @ResponseBody
    public boolean verifyUser(@RequestParam(value = "id") int id,
    		@RequestParam(value = "code") int code){
        boolean result = traderService.verifyAccount(id, code);
        return result;
    }
	
	@RequestMapping(value="/gui-lai-ma-xn")
    @ResponseBody
    public boolean resendVerify(@RequestParam(value = "id") int id){
        traderService.resendVerify(id);
        return true;
    }
	// end handle trader register request
	// handle account did not active
	
	@RequestMapping(value="/kich-hoat-tk")
    public String activeAccount(@RequestParam(value = "phone") String phoneNum, ModelMap m){
        int traderID = traderService.activeAccount(phoneNum);
        
        if(traderID == -1)
        	return "404";
        
        m.addAttribute("id", traderID);
        m.addAttribute("title", "Mã xác nhận đã được gửi về SDT: " + phoneNum);
        m.addAttribute("action", "/NongSanDD/TempNB/xac-nhan-tk");
        m.addAttribute("success", "/NongSanDD/NhaBuon");
        return "trader/code-verify";
    }
    
    @RequestMapping(value="/tk-chua-kh")
    public String viewAccountNotActive(@RequestParam(value = "phone") String phoneNum, ModelMap m){
    	int userID = traderService.activeAccount(phoneNum);
        
        if(userID == -1)
        	return "404";
        
        m.addAttribute("id", userID);
        m.addAttribute("title", "Mã xác nhận đã được gửi về SDT: " + phoneNum + ". Để xem thông "
        		+ "tin tài khoản, vui lòng nhập mã xác nhận");
        m.addAttribute("action", "/NongSanDD/TempNB/kiem-tra-code");
        m.addAttribute("success", "/NongSanDD/TempNB/xn-tk-chua-kh?phone=" + phoneNum);
        return "trader/code-verify";
    }
    
    @RequestMapping(value="/kiem-tra-code")
    @ResponseBody
    public boolean verifyUserCode(@RequestParam(value = "id") int id,
    		@RequestParam(value = "code") int code){
        boolean result = traderService.verifyTrader(id, code);
        
        return result;
    }
    
    @RequestMapping(value="/xn-tk-chua-kh")
    public String updateUAccountNotActive(@RequestParam(value = "phone") String phone, ModelMap m){
        Trader trader = traderService.getTraderNotActive(phone);
        if(trader == null)
        	return "404";
        
        m.addAttribute("trader", trader);

		List<Commune> communes = addressService.getCommuneByDistrictID(677);
		m.addAttribute("communes", communes);

		List<Hamlet> hamlets = addressService.getHamletByCommnune(trader.getCommune());
		m.addAttribute("hamlets", hamlets);
		
		int hamletID = addressService.getHamletByAddressID(trader.getAddress().getId());
		m.addAttribute("hamletID", hamletID);
		
		//for trading agri list
		List<Agriculture> agricultures = agricultureService.getAllAgri();
		m.addAttribute("agris", agricultures);

		List<AgriCategory> agriCategories = agricultureService.getAllAgriCategory();
		m.addAttribute("agriCategories", agriCategories);
		
		List<Integer> tradingAgri = saleService.getTradingAgriID(trader.getId());
		m.addAttribute("tradingList", tradingAgri);

		return "profile-nb";
    }
    
    @RequestMapping(value = "/cap-nhap-tt")
	public String updateUser(@ModelAttribute("trader") Trader trader, @RequestParam(value = "hamletID") int hamletID,
			@RequestParam(value = "tradingList") String tradingList, ModelMap m) {
		int userID = traderService.updateTraderNotActive(trader, hamletID, tradingList);
		if(userID == -1)
			return "404";
		
		m.addAttribute("id", userID);
        m.addAttribute("title", "Mã xác nhận đã được gửi về SDT: " + trader.getPhoneNum());
        m.addAttribute("action", "/NongSanDD/TempNB/xac-nhan-tk");
        m.addAttribute("success", "/NongSanDD/NhaBuon");
        return "user/code-verify";
	}
    ////////////////////////////////////////////////////////////

	@RequestMapping(value = "/quen-mk")
	public String passChageRequest(ModelMap m) {
		return "trader/forgot-pass";
	}
	
	@RequestMapping(value = "/doi-mk")
	public String changePass(@RequestParam("phone") String phone, @RequestParam("pass") String pass, ModelMap m) {
		traderService.changePass(phone, pass);
		m.addAttribute("phone", phone);
    	
    	return "trader/confirm-change-pass";
	}

	@RequestMapping(value = "/xac-nhan-doi-mk")
    @ResponseBody
    public boolean confirmUChangePass(@RequestParam("phone") String phone,
    		@RequestParam("code") int code){
    	return traderService.confirmChangePass(phone, code);
    }
	
}
