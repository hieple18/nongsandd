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
import com.entity.User;
import com.models.PriceList;
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
	
	@RequestMapping("/add-price")
	public String addPrice(ModelMap m){
		List<Agriculture> agricultures = agricultureService.getAllAgri();
		m.addAttribute("agricultures", agricultures);
		
		PriceList priceList = agricultureService.convertToPriceR(agricultures);
		m.addAttribute("priceList", priceList);
		
		return "admin/price-add";
	}
	
	@RequestMapping("/create-price")
	public String createPrice(@ModelAttribute("price") PriceList list){
		
		agricultureService.createPrice(list);
		return "redirect:/admin/agri";
	}
	
	@RequestMapping("/check-delete-agri")
	@ResponseBody
	public boolean checkDeleteAgri(@RequestParam("id") int id){
		return saleService.checkDeleteAgri(id);
	}
	
	@RequestMapping("/delete-agri")
	public String deleteAgri(@RequestParam("id") int id){
		if(saleService.checkDeleteAgri(id)){
			agricultureService.deleteAgri(id);
		}
		
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
	
	@RequestMapping("/list-user")
	public String listUser(ModelMap m){
		
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
}