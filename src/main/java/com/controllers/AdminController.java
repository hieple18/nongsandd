package com.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.entity.AgriCategory;
import com.entity.Agriculture;
import com.entity.Trader;
import com.entity.User;
import com.models.PriceList;
import com.models.PriceU;
import com.models.Response;
import com.service.AddressService;
import com.service.AdminService;
import com.service.AgricultureService;
import com.service.SaleService;
import com.service.TraderService;
import com.service.UserService;
import com.view.ExcelReportView;

/**
 * @author: HiepLe
 * @version: May 5, 2018
 */

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private SaleService saleService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TraderService traderService;
	
	@Autowired
    private AgricultureService agricultureService;
	
	@Autowired 
	private AdminService adminService;
	
	@Autowired
	private AddressService addressService;
	
	@RequestMapping("")
	public String home(ModelMap m){
		List<Object[]> sales = saleService.getInfoForMaps();
		m.addAttribute("sales", sales);
		
		List<Object[]> users = userService.getInfoForMaps();
		m.addAttribute("users", users);
		
		List<Object[]> traders = traderService.getInfoForMaps();
		m.addAttribute("traders", traders);
		return "admin/main";
	}
	
	@RequestMapping("/agri")
	public String getAgri(ModelMap m){
		List<Agriculture> agricultures = agricultureService.getAllAgri();
		m.addAttribute("agris", agricultures);
		
		List<AgriCategory> agriCategories = agricultureService.getAllAgriCategory();
		m.addAttribute("agriCategories", agriCategories);
		
		return "admin/agricultures";
	}
	
	@RequestMapping("/add-agri")
	public String addAgri(@RequestParam("categoryID") int cID,
			@RequestParam("name") String name){
		
		agricultureService.AddAgri(cID, name);
		return "redirect:/admin/agri";
	}
	
	@RequestMapping("/update-agri")
	public String updateAgri(@RequestParam("categoryID") int cID, @RequestParam("id") int id,
			@RequestParam("name") String name){
		
		agricultureService.updateAgri(id, cID, name);
		return "redirect:/admin/agri";
	}
	
	@RequestMapping("/price-add")
	public String addPrice(ModelMap m){
		if(agricultureService.checkExistsPriceToday()){
			return "redirect:/admin/price-update";
		}
		
		List<Agriculture> agricultures = agricultureService.getAllAgri();
		m.addAttribute("agricultures", agricultures);
		
		PriceList priceList = agricultureService.convertToPriceR(agricultures);
		m.addAttribute("priceList", priceList);
		
		return "admin/price-add";
	}
	
	@RequestMapping("/create-price")
	public String createPrice(@ModelAttribute("price") PriceList list){
		
		agricultureService.createPrice(list);
		return "redirect:/admin/price-update";
	}
	
	@RequestMapping("/price-update")
	public String updatePrice(ModelMap m){
		PriceU priceList = agricultureService.getPriceUpdate();
		m.addAttribute("priceList", priceList);
		
		return "admin/price-update";
	}
	
	@RequestMapping("/save-update-price")
	public String saveUpdatePrice(@ModelAttribute("priceList") PriceU list){
		agricultureService.saveUpdatePrice(list);
		
		return "redirect:/admin/agri";
	}
	
	@RequestMapping("/check-delete-agri")
	@ResponseBody
	public boolean checkDeleteAgri(@RequestParam("id") int id){
		return saleService.checkDeleteAgri(id);
	}
	
	@RequestMapping("/delete-agri")
	public String deleteAgri(@RequestParam("id") int id){
		agricultureService.deleteAgri(id);
		
		return "redirect:/admin/agri";
	}
	
	@RequestMapping("/random")
	public String random(){
		return "admin/random";
	}
	
	@RequestMapping(value = "/random-user", method = RequestMethod.POST)
	public String randomUser(@RequestParam("file") MultipartFile file) throws IOException{
		
		adminService.randomUser(file);
		return "redirect:/admin";
	}
	
	@RequestMapping(value = "/random-sale", method = RequestMethod.POST)
	public String randomSale(@RequestParam("file") MultipartFile file) throws IOException{
		
		adminService.randomSale(file);
		return "redirect:/admin";
	}
	
	@RequestMapping("/user-list")
	public String listUser(ModelMap m){
		
		List<User> users = userService.getListUser();
		m.addAttribute("users", users);
		
		return "admin/list-user";
	}
	
	@RequestMapping("/user-info")
	public String userInfo(@RequestParam("id") int id, ModelMap m){
		
		List<User> users = userService.getListUser();
		m.addAttribute("users", users);
		
		return "admin/list-user";
	}
	
	@RequestMapping("/trader-list")
	public String listTrader(ModelMap m){
		
		List<User> users = userService.getListUser();
		m.addAttribute("users", users);
		
		return "admin/list-trader";
	}
	
	@RequestMapping("/trader-info")
	public String traderInfo(@RequestParam("id") int id, ModelMap m){
		
		List<User> users = userService.getListUser();
		m.addAttribute("users", users);
		
		return "admin/list-user";
	}
	
	@RequestMapping("/sale-list")
	public String listSale(ModelMap m){
		
		List<User> users = userService.getListUser();
		m.addAttribute("users", users);
		
		return "admin/sale-list";
	}
	
	@RequestMapping("/sale-info")
	public String saleInfo(@RequestParam("id") int id, ModelMap m){
		
		List<User> users = userService.getListUser();
		m.addAttribute("users", users);
		
		return "admin/list-user";
	}
	
	@RequestMapping("/sale-expired")
	public String updateSaleExpired(){
		
		saleService.updateSaleExpired();
		return "redirect:/admin";
	}
	
	@RequestMapping("/update-address")
	public String updateAddess(){
		
		addressService.updateAddress();
		return "redirect:/admin";
	}
	
	@RequestMapping("/update-mining")
	public String updateMinig(){
		
		adminService.updateMining();
		return "redirect:/admin";
	}
	
	@RequestMapping("/update-commune")
	public String updateCommune(){
		
		adminService.updateCommuneID();
		return "redirect:/admin";
	}
	
	@RequestMapping("/get-excel")
    public ModelAndView getExcel(){
           List<Response> saleList = adminService.getSaleExcel();
           return new ModelAndView(new ExcelReportView(), "saleList", saleList);
    }
	
	@RequestMapping(value = "/random-request", method = RequestMethod.POST)
	public String randomRequest(@RequestParam("file") MultipartFile file) throws IOException{
		
		adminService.randomRequest(file);
		return "redirect:/admin";
	}
	
	@RequestMapping("/add-trader-phone")
	public String addTraderPhone(){
		return "admin/add-trader-phone";
	}
	
	@RequestMapping("/create-trader-phone")
	public String createTraderPhone(@RequestParam("phone") String phone){
		traderService.verifyTraderByAdmin(phone);
		return "redirect:/admin";
	}
	
	@RequestMapping("/update-trader-phone")
	public String updateTraderPhone(@RequestParam("phone") String phone, @RequestParam("id") int id){
		traderService.updateTraderByAdmin(id, phone);
		return "redirect:/admin/wait-to-register";
	}
	
	@RequestMapping("/delete-trader-phone")
	public String deleteTraderPhone(@RequestParam("id") int id){
		traderService.deleteTraderByAdmin(id);
		return "redirect:/admin/wait-to-register";
	}
	
	@RequestMapping("/wait-to-register")
	public String phoneNotRegister(ModelMap m){
		List<Trader> traders = traderService.getTraderWait();
		m.addAttribute("traders", traders);
		return "admin/wait-to-register";
	}
	
	@RequestMapping("/create-admin")
	public String createAdmin(){
		adminService.createAdmin();
		return "redirect:/admin";
	}
	
}