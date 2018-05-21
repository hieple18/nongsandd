package com.controllers;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.entity.Account;
import com.entity.Address;
import com.entity.AgriCategory;
import com.entity.Agriculture;
import com.entity.Commune;
import com.entity.Trader;
import com.entity.TraderCmt;
import com.entity.User;
import com.service.AddressService;
import com.service.AgricultureService;
import com.service.SaleService;
import com.service.TraderService;
import com.service.UserService;

/**
*@author HiepLe
*@version 1.0 Sep 24, 2017
*/

@Controller
public class HomeController {
    
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
    
    @RequestMapping(value="/")
    public String welcome(ModelMap m){
        return "redirect:/gia-hom-nay"; 
    }
    
    ////////////////////////////////////////////////////////////
    // handle user register request
    
    @RequestMapping(value="/dang-ki-tk-nguoidung")
    public String userRegister(ModelMap m){
    	List<Commune> communes = addressService.getCommuneByDistrictID(677);
        m.put("communes", communes);
        
        User user = new User();
        Address address = new Address();
        Account account = new Account();
        user.setAddress(address);
        user.setAccount(account);
        m.put("user", new User());
        
        m.addAttribute("action", "/NongSanDD/tao-tk-nguoidung");
        
        return "user/register";
    }
    
    @RequestMapping(value="/tao-tk-nguoidung")
    public String createUser(@ModelAttribute("user") User user,
    		@RequestParam(value = "hamletID") int hamletID){
        userService.createUser(user, hamletID);
        return "redirect:/NguoiDung";
    }
    
    @RequestMapping(value = "/kiem-tra-sdt-nd")
    @ResponseBody
    public boolean checkUserPhoneNum(@RequestParam("phoneNum") String phoneNum){
    	return userService.checkPhoneNum(phoneNum);
    }
    
    // end handle user register request
    ///////////////////////////////////////////////
    // handle trader request
    
    @RequestMapping(value="/dang-ki-tk-nhabuon")
    public String tradeRegister(ModelMap m){
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
        m.put("user", new User());
        
        m.addAttribute("action", "/NongSanDD/tao-tk-nhabuon");
        
        return "trader/register";
    }
    
    @RequestMapping(value="/tao-tk-nhabuon")
    public String createUser(@ModelAttribute("trader") Trader trader,
    		@RequestParam(value = "hamletID") int hamletID,
    		@RequestParam(value = "tradingList") String tradingList){
    	
    	traderService.createTrader(trader, hamletID, tradingList);
        return "redirect:/NhaBuon";
    }
    
    @RequestMapping(value="/ds-nha-buon")
    public String getListTrader(ModelMap m){
    	
    	List<Trader> traders = traderService.getListTrader();
    	m.addAttribute("traders", traders);
        return "list-trader";
    }
    
    @RequestMapping(value = "/thong-tin-nb")
	public String getTraderInfo(@RequestParam("id") int traderID, ModelMap m) {
		Trader trader = traderService.getTrader(traderID);
		m.addAttribute("trader", trader);
		
		List<TraderCmt> cmts = traderService.getTraderCmts(traderID);
		m.addAttribute("cmts", cmts);
		m.addAttribute("cmtSize", cmts.size());
		
		List<String> tradingAgris = saleService.getTradingAgriName(traderID);
		m.addAttribute("tradingAgris", tradingAgris);
		
		Address address = traderService.getAddress(traderID);
		m.addAttribute("address", address);

		return "trader-profile";
	}
    
    // end handle trader request
    ///////////////////////////////////////////////////////
    
    @RequestMapping(value="/dang-nhap")
    public String login(ModelMap m){
    	return "login";
    }
    
    @RequestMapping(value="/dang-xuat", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response, ModelMap m) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){    
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        
        return "redirect:/";
    }
    
    // end handle register request
    ////////////////////////////////////////////////////////////
    // handle price request
    
    @RequestMapping(value = "/create-random-price")
    @ResponseBody
    public boolean createRandomPrice() throws IOException {
        agricultureService.randomPrice();
        return true;
    }
    
    // get today price
    @RequestMapping(value="/gia-hom-nay")
    public String getPriceList(ModelMap m){
    	List<AgriCategory> agriCategories = agricultureService.getAllAgriCategory();
    	m.addAttribute("agriCategories", agriCategories); 
    	
        List<Object[]> agriPrices = agricultureService.getPriceToday();
        m.addAttribute("agriPrices", agriPrices); 
        
        Date minDate = agricultureService.getMinDatePrice();
        m.addAttribute("minDate", minDate);
        
        m.addAttribute("title", "Hôm Nay");
        return "price-list";
        
    }
    
    // get price by date
    @RequestMapping(value="/gia-ngay-truoc")
    public String getPriceListByDate(@RequestParam("date") String date, ModelMap m){
    	List<AgriCategory> agriCategories = agricultureService.getAllAgriCategory();
    	m.addAttribute("agriCategories", agriCategories);
    	
        List<Object[]> agriPrices = agricultureService.getPriceByday(date);
        m.addAttribute("agriPrices", agriPrices);
        
        Date minDate = agricultureService.getMinDatePrice();
        m.addAttribute("minDate", minDate);
        
        String title = "Ngày " + date.toString();
        m.addAttribute("title", title);
        return "price-list";
    }
    
    // price chart
    @RequestMapping(value="/bieu-do-gia")
    public String getPriceChart(@RequestParam("id") int id, ModelMap m){
    	List<Float> agriPrices = agricultureService.getPriceChart(id);
    	m.addAttribute("agriPrices", agriPrices);
    	
    	List<Agriculture> agricultures = agricultureService.getAllAgri();
		m.addAttribute("agris", agricultures);
		
		List<AgriCategory> agriCategories = agricultureService.getAllAgriCategory();
		m.addAttribute("agriCategories", agriCategories);
    	
    	String currAgriName = agricultures.get(id - 1).getName();
    	agricultures.remove(id - 1);
    	m.addAttribute("currAgriName", currAgriName);
    	
        return "price-chart";
    }
    
    // get list price by agricultureID to compare
    @RequestMapping(value="/lay-ds-gia")
    @ResponseBody
    public List<Float> getListPrice(@RequestParam("id") int id){
    	List<Float> agriPrices = agricultureService.getPriceChart(id);
        return agriPrices;
    }
    
    @RequestMapping(value = "/lay-ds-thon")
    @ResponseBody
    public List<Object[]> GetThonBylstmaxa(@RequestParam("lstmaxa") int lstmaxa, ModelMap m) {
        List<Object[]> hamlets = addressService.getHamletNameByCommuneID(lstmaxa); 
        return hamlets;
    }
    
    //end handle price request
    /////////////////////////////////////////////////////
  
}
