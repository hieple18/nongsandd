/**
 * 
 */
package com.constant;

import java.sql.Date;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * @author 14520278
 *
 */
public final class Constant {

	/** The Constant COLUMN_LENGTH_LIMIT. */
	public static final int COLUMN_LENGTH_10_LIMIT = 10;

	/** The Constant COLUMN_LENGTH_20_LIMIT. */
	public static final int COLUMN_LENGTH_20_LIMIT = 20;

	/** The Constant COLUMN_LENGTH_30_LIMIT. */
	public static final int COLUMN_LENGTH_30_LIMIT = 30;

	/** The Constant COLUMN_LENGTH_50_LIMIT. */
	public static final int COLUMN_LENGTH_50_LIMIT = 50;

	/** The Constant COLUMN_LENGTH_100_LIMIT. */
	public static final int COLUMN_LENGTH_100_LIMIT = 100;

	/** The Constant COLUMN_LENGTH_200_LIMIT. */
	public static final int COLUMN_LENGTH_200_LIMIT = 200;

	/** The Constant COLUMN_LENGTH_200_LIMIT. */
	public static final int COLUMN_LENGTH_300_LIMIT = 300;

	/** The Constant COLUMN_LENGTH_500_LIMIT. */
	public static final int COLUMN_LENGTH_500_LIMIT = 500;
	
	public static final int SALE_EXPERID = 30;

	public static final int NOT_VERIFY = 0;
	public static final int VERIFIED = 1;
	public static final int WAIT_TO_REGISTER = -1;
	
	// for mining
	public static final int AGRI_M = 1;
	public static final int AREA_M = 2;
	public static final int COMMUNE_M = 3;
	
	// STATE
	public static final int DELETE_STATE = -1;
	public static final int DISABLE_STATE = 0;
	public static final int ENABLE_STATE = 1;
	public static final int SELECTED_STATE = 2;

	// trader notification status
	public static final int T_CONFIRM_REQUEST = 1;
	public static final int T_UPDATE_REQUEST = 2;
	public static final int T_UPDATE_SELECTED = 3;
	public static final int T_USER_CMT = 4;
	public static final int T_SALE_RESTORE = 5;

	// user notification status
	public static final int U_SALE_REQUEST = 1;
	public static final int U_SALE_REMOVE = 2;
	public static final int U_TRADER_CMT = 3;

	public static final String ROLE_USER = "ROLE_USER";
	public static final String ROLE_TRADER = "ROLE_TRADER";
	public static final String ROLE_ADMIN = "ROLE_ADMIN";
	
	public static final int SALE_LIMIT = 3;

	// USER
	public static final String GET_USER_NOTIF_CONTENT(int type, String name) {
		switch (type) {
		case U_SALE_REQUEST:
			return "Bạn có một yêu cầu mới từ " + name;
		case U_SALE_REMOVE:
			return name + " đã xóa tin bán của bạn khỏi danh sách của họ";
		case U_TRADER_CMT:
			return name + " đã nhận xét về bạn";
		default:
			return "";
		}
	}

	// USER
	public static final String GET_USER_NOTIF_LINK(int type, int id) {
		switch (type) {
		case U_SALE_REQUEST:
			return "/NongSanDD/NguoiDung/ds-yeu-cau?id=" + id;
		case U_SALE_REMOVE:
			return "/NongSanDD/NguoiDung/khoi-phuc-tin-ban?id=" + id;
		case U_TRADER_CMT:
			return "/NongSanDD/NguoiDung/thong-tin-tai-khoan";
		default:
			return " ";
		}
	}

	// TRADER
	public static final String GET_TRADER_NOTIF_CONTENT(int type, String name) {
		switch (type) {
		case T_CONFIRM_REQUEST:
			return name + " đã xác nhận yêu cầu của bạn";
		case T_UPDATE_REQUEST:
			return name + " đã chỉnh sửa tin bán mà bạn đã gửi yêu cầu";
		case T_UPDATE_SELECTED:
			return name + " đã chỉnh sửa tin bán mà bạn đã mua";
		case T_USER_CMT:
			return name + " đã nhận xét về bạn";
		case T_SALE_RESTORE:
			return name + " đã khôi phục lại tin bán mà bạn đã gửi yêu cầu";
		default:
			return " ";
		}
	}

	public static final String GET_TRADER_NOTIF_LINK(int type, int id) {
		switch (type) {
		case T_CONFIRM_REQUEST:
			return "/NongSanDD/NhaBuon/tin-da-mua";
		case T_UPDATE_REQUEST:
			return "/NongSanDD/NhaBuon/chi-tiet-tin-ban?id=" + id + "&state=2";
		case T_UPDATE_SELECTED:
			return "/NongSanDD/NhaBuon/chi-tiet-tin-ban?id=" + id + "&state=3";
		case T_USER_CMT:
			return "/NongSanDD/NhaBuon/thong-tin-tai-khoan";
		case T_SALE_RESTORE:
			return "/NongSanDD/NhaBuon/chi-tiet-tin-ban?id=" + id + "&state=2";
		default:
			return " ";
		}
	}

	public static final Date CURRENT_DATE() {
		Calendar currentTime = Calendar.getInstance();
		Date dateCreate = new Date((currentTime.getTime()).getTime());
		return dateCreate;
	}
	
	public static final int CURRENT_MONTH() {
    	Calendar cal = Calendar.getInstance();
    	return cal.get(Calendar.MONTH) + 1;
	}

	public static final Long DATE_IN_MILLISECONDS() {
		Calendar calendar = Calendar.getInstance();
		return calendar.getTimeInMillis();
	}

	public static final List<Double> GET_LNG(int communeID) {
		switch (communeID) {
		case 0:
			return Arrays.asList(108.667142, 108.561398, 11.970091, 11.78576);
		case 1:
			return Arrays.asList(108.512348, 108.465657, 11.805624, 11.736723);
		case 2:
			return Arrays.asList(108.700444, 108.526036, 11.850871, 11.714083);
		case 3:
			return Arrays.asList(108.50144, 108.424683, 11.829712, 11.737829);
		case 4:
			return Arrays.asList(108.549594, 108.484533, 11.818594, 11.766194);
		case 5:
			return Arrays.asList(108.704564, 108.50475, 11.779295, 11.668699);
		case 6:
			return Arrays.asList(108.547926, 108.49411, 11.76704, 11.725696);
		case 7:
			return Arrays.asList(108.534107, 108.46424, 11.744012, 11.666017);
		case 8:
			return Arrays.asList(108.510047, 108.39042, 11.748034, 11.654373);
		case 9:
			return Arrays.asList(108.682248, 108.51196, 11.734173, 11.641044);
		default:
			return null;
		}
	}
	
	public static final int GET_MULTI_AREA(int agriID){
		switch (agriID) {
		case 1:
			return 4;
		case 2:
			return 3;
		case 3:
			return 3;
		case 4:
			return 4;
		case 6:
			return 0;
		case 7:
			return 6;
		case 8:
			return 6;
		case 9: 
			return 6;
		case 10:
			return 0;
		case 11:
			return 0;
		default:
			return 0;
		}
	}
	
	// twilio api
	public static final String ACCOUNT_SID = "ACf1d685441305f7894555f4b0de1a590e";
    public static final String AUTH_TOKEN = "125933e2dee320c0b8fdbc40660ab88e";
    public static final String TWILIO_NUMBER = "+19704102720";
    
    public static String GET_COMMUNE_SHORT_NAME(int id){
    	switch (id) {
		case 0:
			return "TT Dran";
		case 1:
			return  "TT TM";
		case 2:
			return  "Lạc Xuân";
		case 3:
			return  "Đạ Ròn";
		case 4:
			return  "Lạc Lâm";
		case 5:
			return  "Ka Đô";
		case 6:
			return  "Q Lập";
		case 7:
			return  "KaDon";
		case 8:
			return  "Tu Tra";
		case 9:
			return  "Pro";
		default:
			return "";
		}
    }
}
