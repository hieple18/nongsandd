package com.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.constant.Constant;
import com.entity.Account;
import com.entity.Address;
import com.entity.Agriculture;
import com.entity.Sale;
import com.entity.Trader;
import com.entity.User;
import com.models.Response;
import com.reponsitory.AccountRepository;
import com.reponsitory.SaleRepository;
import com.reponsitory.TraderRepository;
import com.reponsitory.UserRepository;

/**
 * @author: HiepLe
 * @version: May 9, 2018
 */

@Service
public class AdminService {
	@Autowired
	private AccountRepository accountReponsitory;

	@Autowired
	private AddressService addressService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private SaleRepository saleRepository;

	@Autowired
	private TraderRepository traderRepository;

	@Autowired
	private SaleService saleService;

	public Account randomAccount(String phoneNum) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		Account account = new Account(phoneNum, encoder.encode("123456"), Constant.ROLE_USER, (short) 1);
		accountReponsitory.save(account);

		return account;
	}

	public double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

	public Address randomAddress(int hamletID) {
		int communeID = addressService.getCommuneIDByHamletID(hamletID);
		List<Double> map = Constant.GET_LNG(communeID);
		Random r = new Random();

		// map.get(0): max_lng, map.get(1): min_lng
		double lng = map.get(1) + r.nextDouble() * (map.get(0) - map.get(1));

		// map.get(2): max_lat, map.get(3): min_lat
		double lat = map.get(3) + r.nextDouble() * (map.get(2) - map.get(3));

		Address address = new Address(round(lat, 6), round(lng, 6));
		addressService.createAddress(address, hamletID);

		return address;
	}

	public void randomUser(MultipartFile file) throws IOException {
		InputStream stream = file.getInputStream();
		@SuppressWarnings("resource")
		XSSFWorkbook workbook = new XSSFWorkbook(stream);
		XSSFSheet sheet = workbook.getSheetAt(0);

		Iterator<Row> rowIterator = sheet.iterator();

		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			User user = new User();
			// random user property
			user.setName(row.getCell(0).toString());
			user.setPhoneNum(row.getCell(1).toString());
			java.sql.Date sqlDate = java.sql.Date.valueOf(row.getCell(2).toString());
			user.setDateCreate(sqlDate);

			// random account
			Account account = randomAccount(user.getPhoneNum());
			user.setAccount(account);

			// random address
			Random r = new Random();
			int hamletID = r.nextInt(101);
			Address address = randomAddress(hamletID);
			user.setAddress(address);

			userRepository.save(user);

			System.out.println(user.getId() + ", " + user.getName());
		}
	}

	public int getIntFromExcel(String s) {
		s = s.substring(0, s.length() - 2);
		return Integer.parseInt(s);
	}

	public void randomSale(MultipartFile file) throws IOException {
		InputStream stream = file.getInputStream();
		@SuppressWarnings("resource")
		XSSFWorkbook workbook = new XSSFWorkbook(stream);
		XSSFSheet sheet = workbook.getSheetAt(0);

		Iterator<Row> rowIterator = sheet.iterator();

		List<Integer> listUserID = userRepository.getListID();
		int listIDL = listUserID.size();
		User user = new User();

		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			Sale sale = new Sale();
			Random r = new Random();

			java.sql.Date sqlDate = java.sql.Date.valueOf(row.getCell(0).toString());
			sale.setDateCreate(sqlDate);

			int agriID = 1 + r.nextInt(10);

			Agriculture agriculture = new Agriculture();
			agriculture.setId(agriID);
			sale.setAgriculture(agriculture);

			int area = 1 + r.nextInt(9);
			sale.setArea(area);

			float percent = (80 + r.nextInt(40)) / 100;
			int quantity = (int) percent * area * Constant.GET_MULTI_AREA(area);
			sale.setQuantity(quantity);

			int hamletID = r.nextInt(101);
			Address address = randomAddress(hamletID);
			sale.setAddress(address);

			int index = r.nextInt(listIDL - 1);
			user.setId(listUserID.get(index));
			sale.setUser(user);

			sale.setStatus(Constant.ENABLE_STATE);
			saleRepository.save(sale);
		}
	}

	public List<Response> getSaleExcel() {
		List<Sale> sales = saleRepository.getSaleForExcel();
		ArrayList<Response> responses = new ArrayList<Response>();

		for (Sale sale : sales) {
			Response response = new Response();

			response.setSaleID(sale.getId());
			response.setAgriID(sale.getAgriculture().getId());
			response.setCommuneID(sale.getCommuneID());
			response.setArea(Math.round(sale.getArea()));
			response.setPrice(Math.round(sale.getPrice() / 10));
			response.setQuanlity(Math.round(sale.getQuantity() / 5));

			responses.add(response);
		}

		return responses;
	}

	public void randomRequest(MultipartFile file) throws IOException {
		InputStream stream = file.getInputStream();
		@SuppressWarnings("resource")
		XSSFWorkbook workbook = new XSSFWorkbook(stream);
		XSSFSheet sheet = workbook.getSheetAt(0);

		Iterator<Row> rowIterator = sheet.iterator();

		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();

			int traderID = getIntFromExcel(row.getCell(0).toString());
			int saleID = getIntFromExcel(row.getCell(1).toString());

			saleService.createSaleRequestForMining(traderID, saleID);
		}
	}

	public void updateMining(){
		List<Trader> traders = traderRepository.findAll();
		
		for (Trader trader : traders) {
			saleService.updateAllMining(trader.getId());
		}
	}
	
	public void updateCommuneID(){
		List<Object[]> objects = saleRepository.getSaleCommune();
		for (Object[] object : objects) {
			Sale sale = (Sale)object[0];
			int hamletID = (int)object[1];
			int communeID = addressService.getCommuneIDByHamletID(hamletID);
			sale.setCommuneID(communeID);
			
			saleRepository.save(sale);
		}
	}

}