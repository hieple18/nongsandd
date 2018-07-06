package com.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.entity.Account;
import com.entity.Address;
import com.entity.Commune;
import com.entity.User;
import com.service.AddressService;
import com.service.UserService;

/**
 * @author: HiepLe
 * @version: Jun 24, 2018
 */

@Controller
@RequestMapping("/TempND")
public class TempUserController {  
    @Autowired
    private AddressService addressService;
    
    @Autowired
    private UserService userService;
    
    ////////////////////////////////////////////////////////////
    // handle user register request
    
    @RequestMapping(value="/dang-ki-tk")
    public String userRegister(ModelMap m){
    	List<Commune> communes = addressService.getCommuneByDistrictID(677);
        m.put("communes", communes);
        
        User user = new User();
        Address address = new Address();
        Account account = new Account();
        user.setAddress(address);
        user.setAccount(account);
        m.put("user", user);
        
        return "user/register";
    }
    
    @RequestMapping(value="/tao-tk")
    public String createUser(@ModelAttribute("user") User user,
    		@RequestParam(value = "hamletID") int hamletID, ModelMap m){
        int userID = userService.createTempUser(user, hamletID);
        if(userID == -1) // this phone number already used
        	return "user/404";
        
        m.addAttribute("id", userID);
        m.addAttribute("title", "Mã xác nhận đã được gửi về SDT: " + user.getPhoneNum());
        m.addAttribute("action", "/NongSanDD/TempND/xac-nhan-tk");
        m.addAttribute("success", "/NongSanDD/NguoiDung");
        return "user/code-verify";
    }
    
    @RequestMapping(value="/xac-nhan-tk")
    @ResponseBody
    public boolean verifyUser(@RequestParam(value = "id") int id,
    		@RequestParam(value = "code") int code){
        boolean result = userService.verifyUserNAccount(id, code);
        return result;
    }
    
    @RequestMapping(value="/gui-lai-ma-xn")
    @ResponseBody
    public boolean resendVerify(@RequestParam(value = "id") int id){
        userService.resendVerify(id);
        return true;
    }
    
    @RequestMapping(value = "/kiem-tra-sdt")
    @ResponseBody
    public boolean checkUserPhoneNum(@RequestParam("phoneNum") String phoneNum){
    	return userService.checkPhoneNum(phoneNum);
    }
    
    // handle account did not active
/*    @RequestMapping(value="/kich-hoat-tk")
    public String activeAccount(@RequestParam(value = "phone") String phoneNum, ModelMap m){
        int userID = userService.activeAccount(phoneNum);
        
        if(userID == -1)
        	return "404";
        
        m.addAttribute("id", userID);
        m.addAttribute("title", "Mã xác nhận đã được gửi về SDT: " + phoneNum);
        m.addAttribute("action", "/NongSanDD/TempND/xac-nhan-tk");
        m.addAttribute("success", "/NongSanDD/NguoiDung");
        return "user/code-verify";
    }
    
    @RequestMapping(value="/tk-chua-kh")
    public String viewAccountNotActive(@RequestParam(value = "phone") String phoneNum, ModelMap m){
    	int userID = userService.activeAccount(phoneNum);
        
        if(userID == -1)
        	return "404";
        
        m.addAttribute("id", userID);
        m.addAttribute("title", "Mã xác nhận đã được gửi về SDT: " + phoneNum + ". Để xem thông "
        		+ "tin tài khoản, vui lòng nhập mã xác nhận");
        m.addAttribute("action", "/NongSanDD/kiem-tra-code");
        m.addAttribute("success", "/NongSanDD/xn-tk-nd-chua-kh?phone=" + phoneNum);
        return "user/code-verify";
    }
    
    @RequestMapping(value="/kiem-tra-code")
    @ResponseBody
    public boolean verifyUserCode(@RequestParam(value = "id") int id,
    		@RequestParam(value = "code") int code){
        boolean result = userService.verifyUser(id, code);
        
        return result;
    }
    
    @RequestMapping(value="/xn-tk-chua-kh")
    public String updateUAccountNotActive(@RequestParam(value = "phone") String phone, ModelMap m){
        User user = userService.getUserNotActive(phone);
        if(user == null)
        	return "404";
        
        m.addAttribute("user", user);

		List<Commune> communes = addressService.getCommuneByDistrictID(677);
		m.addAttribute("communes", communes);

		List<Hamlet> hamlets = addressService.getHamletByCommnune(user.getCommune());
		m.addAttribute("hamlets", hamlets);

		int hamletID = addressService.getHamletByAddressID(user.getAddress().getId());
		m.addAttribute("hamletID", hamletID);

		return "profile-nd";
    }
    
    @RequestMapping(value = "/cap-nhap-tt")
	public String updateUser(@ModelAttribute("user") User user, 
			@RequestParam(value = "hamletID") int hamletID, ModelMap m) {
		int userID = userService.updateUserNotActive(user, hamletID);
		if(userID == -1)
			return "404";
		
		m.addAttribute("id", userID);
        m.addAttribute("title", "Mã xác nhận đã được gửi về SDT: " + user.getPhoneNum());
        m.addAttribute("action", "/NongSanDD/xac-nhan-tk-nguoidung");
        m.addAttribute("success", "/NongSanDD/NguoiDung");
        return "user/code-verify";
	}*/
    
    // handle forgot password user account
    @RequestMapping("/quen-mk")
    public String forgotUserPass(){
    	return "user/forgot-pass";
    }
    
    @RequestMapping(value = "/doi-mk", method=RequestMethod.POST)
    public String changeUserPass(@RequestParam("phone") String phone,
    		@RequestParam("pass") String pass, ModelMap m){
    	userService.changePass(phone, pass);
    	m.addAttribute("phone", phone);
    	
    	return "user/confirm-change-pass";
    }
    
    @RequestMapping(value = "/xac-nhan-doi-mk")
    @ResponseBody
    public boolean confirmUChangePass(@RequestParam("phone") String phone,
    		@RequestParam("code") String code){
    	return userService.confirmChangePass(phone, code);
    }
    
    // end handle user register request
}
