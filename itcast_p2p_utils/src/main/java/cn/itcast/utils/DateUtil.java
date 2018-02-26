package cn.itcast.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;


/**
 * 
 * 日期实用工具类用于处理日期
 * 
 */
public class DateUtil {

	public static final Logger log = Logger.getLogger(DateUtil.class);
	
	/**
	 * 返回当前日期
	 * 
	 * @return String
	 */
	public static String getDate() {
		return DateFormatUtils.format(new Date(), "yyyy-MM-dd");
	}
	
	/**
	 * 返回日期
	 * 
	 * @return String
	 */
	public static String getStringDate(java.util.Date date) {
		return DateFormatUtils.format(date, "yyyy-MM-dd");
	}
	
	/**
	 * 返回日期
	 * 
	 * @return String
	 */
	public static String getStringDate(java.sql.Date date) {
		return DateFormatUtils.format(date, "yyyy-MM-dd");
	}
	
	/**
	 * 返回日期
	 * 
	 * @return String
	 */
	public static java.sql.Date getSqlDate(java.util.Date date) {
		return new java.sql.Date(date.getTime());
	}
	
	/**
	 * 返回日期
	 * 
	 * @return String
	 */
	public static java.util.Date getSqlDate(java.sql.Date date) {
		return new java.util.Date (date.getTime());
	}
	
	public static String getNextDate() {
		Calendar cal=Calendar.getInstance();//使用日历类
		cal.add(Calendar.DAY_OF_MONTH, 1);
		return DateFormatUtils.format(cal, "yyyy-MM-dd");
		/*String nextDate = DateFormatUtils.format(new Date(), "yyyy-MM-dd");
		return nextDate;*/
	}

	/**
	 * 返回当前日期
	 * 
	 * @return String
	 */
	public static String getFormatDate() {
		return DateFormatUtils.format(new Date(), "yyyyMMdd");
	}
	
	/**
	 * 返回日期yyyy-MM-dd
	 * 
	 * @return String
	 */
	public static String getDate(Date date) {
		return DateFormatUtils.format(date, "yyyy-MM-dd",TimeZone.getTimeZone("GMT+8"));
	}
	
	/**
	 * 
	 * @param date
	 * @return  返回日期yyyy-MM-dd HH:mm:ss
	 */
	public static String getDateFormat(Date date) {
		return DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss",TimeZone.getTimeZone("GMT+8"));
	}
	/**
	 * 返回当前日期时间
	 * 
	 * @return String
	 */
	public static String getDateTime() {
		return DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss",TimeZone.getTimeZone("GMT+8"));
	}

	/**
	 * 返回当前日期时间
	 * libeibei
	 * 
	 * @return String
	 */
	public static String getDateTimeNear() {
		return DateFormatUtils.format(new Date(), "yyyyMMddHHmmss",TimeZone.getTimeZone("GMT+8"));
	}

	/**
	 * 返回当前日期时间
	 * 
	 * @return String
	 */
	public static String getDateRoundMinute() {
		return DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm",TimeZone.getTimeZone("GMT+8"));
	}
	/**
	 * 获取日期200-10-5
	 * 
	 * @param str
	 *            原日期类型yyyy-MM-dd HH:mm:ss.S
	 * @return
	 */
	public static String getDateTime(String str) {
		if (str == null || str.equals(""))
			return "";
		DateFormat df = DateFormat.getDateInstance();
		Date d = null;
		try {
			d = df.parse(str);
		} catch (Exception e) {
			return null;
		}
		return DateFormatUtils.format(d, "yyyy-MM-dd");
	}
	
	/**
	 * 取得当前时间戳
	 * @return 日期格式
	 */
	public static Timestamp getCurrentTimestamp() {
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
		String time = df.format(new Date());   
		return Timestamp.valueOf(time);   
	}
	
	/**
	 * 字符串转为日期
	 * @param str 字符串日期
	 * @return 日期格式
	 */
	public static Date toDate(String str) {
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = format.parse(str);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			date = calendar.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * 字符串转为日期
	 * @param str 字符串日期
	 * @return 日期格式
	 */
	public static Date toDates(String str) {
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	/**
	 * 字符串转为日期
	 * @param str 字符串日期
	 * @return 日期格式
	 * @author yangqingquan 
	 */
	public static Date StrToDate(String str) {
		  
		   SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		   Date date = null;
		   try {
		    date = format.parse(str);
		   } catch (ParseException e) {
		    e.printStackTrace();
		   }
		   return date;
		}
	
	
	
	/**
	 * 返回当前时间
	 * 
	 * @return String
	 */
	public static String getTime() {
		
		return DateFormatUtils.format(new Date(), "HHmmssSSS");
	}

	/**
	 * 返回当前时间
	 * 
	 * @return String
	 */
	public static String getCurrentTime() {
		return DateFormatUtils.format(new Date(), "HH:mm:ss");
	}
	/**
	 * 获取月的实现
	 * 
	 * @param date
	 * @return String
	 */
	public static String getEndTime(Date date, int month, boolean endmonth) {
		String ds = null;
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, month);
		if (endmonth) {
			int i = c.getActualMaximum(Calendar.DAY_OF_MONTH);
			c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), i, 23, 59, 59);
		}
		ds = DateFormatUtils.format(c.getTime(), "yyyy-MM-dd HH:mm:ss");
		return ds;

	}

	/**
	 * 获取天的时限
	 * 
	 * @param date
	 * @return String
	 */
	public static String getEndTime(Date date, int days) {
		String ds = null;
		Calendar c = Calendar.getInstance();
		c.setTime(date);

		c.add(Calendar.DATE, days);
		ds = DateFormatUtils.format(c.getTime(), "yyyy-MM-dd HH:mm:ss");
		return ds;
	}

	/**
	 * 返回中文日期
	 * 
	 * @return String
	 */
	public static String getGBDate(String dateStr) {
		DateFormat df = DateFormat.getDateInstance();
		String gbdate = null;
		try {
			Date date = df.parse(dateStr);
			gbdate = DateFormatUtils.format(date, "yyyy年MM月dd日");
		} catch (ParseException pe) {
			log.info(pe.getMessage());
		}
		return gbdate;
	}

	/**
	 * 返回日期
	 * 
	 * @return String
	 */
	public static String getStringDate(String dateStr, String pattern) {
		DateFormat df = DateFormat.getDateInstance();
		String gbdate = null;
		try {
			Date date = df.parse(dateStr);
			gbdate = DateFormatUtils.format(date, pattern);
		} catch (ParseException pe) {
			log.info(pe.getMessage());
		}
		return gbdate;
	}

	/**
	 * 返回中文日期时间
	 * 
	 * @return String
	 */
	public static String getGBDateTime(String dateStr) {
		DateFormat df = DateFormat.getDateInstance();
		String gbdate = null;
		try {
			Date date = df.parse(dateStr);
			gbdate = DateFormatUtils.format(date, "yyyy年MM月dd日 HH时mm分ss秒");
		} catch (ParseException pe) {
			log.info(pe.getMessage());
		}
		return gbdate;
	}

	/**
	 * 返回日期格式
	 * 
	 * @return Date
	 */
	public static Date getDate(String dateStr) {
		DateFormat df = DateFormat.getDateInstance();
		try {
			Date date = df.parse(dateStr);
			return date;
		} catch (Exception e) {
			return null;
		}
		
	}

	public static String getShortDate(String dateStr) {
		if (dateStr == null || "".equals(dateStr))
			return null;
		DateFormat df = DateFormat.getDateInstance();
		try {
			Date date = df.parse(dateStr);
			return DateFormatUtils.format(date, "yyyy-MM-dd");
		} catch (Exception e) {
			log.error("日期格式有误" + e.getMessage());
		}
		return null;
	}
      
	
	public static Date getDate(String dateStr, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			Date date = sdf.parse(dateStr);
			return date;
		} catch (Exception e) {
		
		}
		return null;
	}

	/**
	 * 返回日期格式
	 * 
	 * @return String
	 */
	public static String getDateStr(Date date) {
		String dateStr = null;
		try {
			dateStr = DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss.S");
			return dateStr;
		} catch (Exception e) {
			
		}
		return null;
	}
	
	/**
	 * 返回日期格式
	 * 
	 * @return String
	 */
	public static String getDateStr(Date date, String pattern) {
		String dateStr = null;
		try {
			dateStr = DateFormatUtils.format(date, pattern);
			return dateStr;
		} catch (Exception e) {
			
		}
		return "";
	}

	/**
	 * 返回时间类型
	 * 
	 * @param aValue
	 * @return
	 */
	public static java.sql.Timestamp parseTimestampFromFormats(String aValue) {
		if (StringUtils.isEmpty(aValue))
			return null;
		DateFormat df = DateFormat.getDateTimeInstance();
		if (aValue.indexOf(":") == -1) {
			aValue = " 00:00:00";
		} else if (aValue.indexOf(":") != -1
				&& aValue.indexOf(":") == aValue.lastIndexOf(":")) {
			aValue = aValue + ":00";
		}
		try {
			Date date = df.parse(aValue);
			return new java.sql.Timestamp(date.getTime());
		} catch (Exception e) {
		
		}

		return null;
	}

	public static java.sql.Timestamp now() {
		return new java.sql.Timestamp(new java.util.Date().getTime());
	}

	/**
	 * 获取服务器的系统时间
	 * 
	 * @return Date 日期 格式：yyyy-MM-dd HH:mm:ss
	 */
	public static String getCurrentServerTime() {
		return getDateTime();
	}

	/**
	 * 把时间格式转换成到分钟
	 * 
	 * @param String
	 *            日期 格式：yyyy-MM-dd HH:mm:ss
	 * @return String 日期 格式：yyyy-MM-dd HH:mm
	 */
	public static String getDateTimeStringToMinute(String date) {
		if (date == null || date.equals(""))
			return "";
		try {
			String dateStr = DateFormatUtils.format(DateFormat
					.getDateTimeInstance().parse(date), "yyyy-MM-dd HH:mm");
			return dateStr;
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 获取时间到秒
	 * 
	 * @param date
	 *            日期
	 * @return
	 */
	public static String getDateStrToSecond(Date date) {
		if (date == null)
			return "";
		String dateStr = null;
		try {
			dateStr = DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss");
			return dateStr;
		} catch (Exception e) {
			log.info(e.getMessage());
			return null;
		}
	}

	/**
	 * 获取时间到分
	 * 
	 * @param date
	 *            日期
	 * @return
	 */
	public static String getDateStrToMinute(Date date) {
		if (date == null)
			return "";
		String dateStr = null;
		try {
			dateStr = DateFormatUtils.format(date, "yyyy-MM-dd HH:mm");
			return dateStr;
		} catch (Exception e) {

			return "";
		}
	}

	/**
	 * 获取时间到日
	 * 
	 * @param date
	 *            日期
	 * @return
	 */
	public static String getDateStrToDay(Date date) {
		if (date == null)
			return "";
		String dateStr = null;
		try {
			dateStr = DateFormatUtils.format(date, "yyyy-MM-dd");
			return dateStr;
		} catch (Exception e) {
			
			log.info(e.getMessage());
			return null;
		}
	}

	public static boolean getLeapYear(Date date) {
		String da = new SimpleDateFormat("yyyy-MM-dd").format(date);
		String[] str = da.split("-");
		if(str[1].equals("02")) {
			
		}
		return false;
		
	}
	
	public static Date getDateFromString(String dateStr) throws Exception {
		DateFormat df = DateFormat.getDateInstance();
		Date date = df.parse(dateStr);
		return date;

	}

	public static Date getDateTimeFromString(String dateStr) throws Exception {
		DateFormat df = DateFormat.getDateTimeInstance();
		Date date = df.parse(dateStr);
		return date;
	}

	//libeibei
	public static Date getDateTimeString(String dateStr) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date=null;
		try {
			date = df.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	//libeibei
	public static String getDateTimeToString(Date dateStr) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String date="";
		try {
			date = df.format(dateStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}
	//libeibei
	public static Date getDateStringMinute(String dateStr) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date=null;
		try {
			date = df.parse(dateStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}
	
	//libeibei
	public static Date getDateString(String dateStr){
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date date=null;
			try {
				date = df.parse(dateStr);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return date;
		}
	
	//李贝贝
	public static String formatMinChinese(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
		String format = df.format(date);
		return format;
	}
	
	//李贝贝
	public static Date getTimeForSecound() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = df.format(new Date());
		Date parse = null;
		try {
			parse = df.parse(format);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return parse;
	}
	


	//libeibei 获取本月第一天
	public static String getMonthFirstDay() {  
        Calendar calendar = Calendar.getInstance();  
        calendar.set(Calendar.DAY_OF_MONTH, calendar  
                .getActualMinimum(Calendar.DAY_OF_MONTH));
        SimpleDateFormat simpleFormate = new SimpleDateFormat("yyyy-MM-dd");
        return simpleFormate.format(calendar.getTime());  
    }
	
	//libeibei
	public static String getTimeForMin(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date=new Date();
		String format = df.format(date);
		return format;
	}
	
	//libeibei
	public static String getTimeSecoundFromDate(Date date){
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String format = df.format(date);
			return format;
	}
    
	public static void main(String[] args) {
		String timeSecoundFromDate = getTimeSecoundFromDate(new Date());
		System.out.println(timeSecoundFromDate);
	}
	
	//lbb
	public static String getDateDay(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
		Date date=new Date();
		String format = df.format(date);
		return format;
	}  
	
	/**
	 * 得到日期到小时
	 * @param date
	 * @return
	 */
	public static String getTimeHour(Date date){
		SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日 HH时");
		String format = df.format(date);
		return format;
	}  
	
	
	//lbb
	public static String getDateDaySecound(){
			SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
			Date date=new Date();
			String format = df.format(date);
			return format;
	} 
	//lbb
	public static String getDateStr(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date dateD=new Date();
		String date=null;
		try {
			date = df.format(dateD);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
		
	}
	
	
		/**
		 * lyu
		 * 返回 yyyy-MM-dd 形式
		 */
		public static String toDateString(Date date){
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				String format = df.format(date);
				return format;
		} 
	/**
	 * 获取昨天日期
	 * lbb
	 * @return
	 */
	public static String getYesterday(){
		  Calendar cal =Calendar.getInstance();
		  cal.add(Calendar.DATE,   -1);
		  String yesterday = new SimpleDateFormat( "yyyy-MM-dd").format(cal.getTime());
		  return yesterday;
	}
	
	/**
	 * 获取10天前的时间
	 * @return
	 */
   public static String getBeforeTenTime(){
	    Calendar   c   =   Calendar.getInstance();   
	    c.add(Calendar.DATE, -10); 
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	    String mDateTime=formatter.format(c.getTime());  
		return mDateTime;
   }
   /**
	 * 字符串转为日期
	 * @param str 字符串日期
	 * @return 日期格式 年月日是否秒毫秒
	 * @author yangqingquan 
	 */
   public static Date stringToDate(String str){
	   DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
	   Date date = null;
	   try {
		   
		date = format.parse(str);
	} catch (ParseException e) {
		e.printStackTrace();
	}
	   
	    System.out.println(date);
	   return date;
   }
   
   
   
   
   
   
   
   
//	/**
//	 * 检查文本框中的日期时间格式
//	 * 
//	 * @param source
//	 *            Text
//	 * 
//	 * @return
//	 */
//	public static boolean checkDateTime(Text text) {
//
//		if (text.getText() != null && text.getText().trim().length() > 0) {
//			String str = text.getText().trim();
//			Date date = null;
//			try {
//				date = changeDate(str);
//				String sdate = DateUtil.getDateStrToMinute(date);
//				text.setText(sdate);
//			} catch (Exception e1) {
//				MessageDialog.showMessage("时间格式错误，请重新输入，正确格式为：2006-06-06 6:6");
//			
//				text.setText("");
//				
//				return false;
//			}
//		}
//		return true;
//	}
//	/**
//	 * 检查文本框中的日期时间格式
//	 * 
//	 * @param source
//	 *            Text
//	 * 
//	 * @return
//	 */
//	public static boolean checkDateTime(WText text) {
//
//		if (text.getText() != null && text.getText().trim().length() > 0) {
//			String str = text.getText().trim();
//			Date date = null;
//			try {
//				date = changeDate(str);
//				String sdate = DateUtil.getDateStrToMinute(date);
//				text.setText(sdate);
//			} catch (Exception e1) {
//				MessageDialog.showMessage("时间格式错误，请重新输入，正确格式为：2006-06-06 6:6");
//			
//				text.setText("");
//				
//				return false;
//			}
//		}
//		return true;
//	}
//	/**
//	 * 检查文本框中的日期时间格式
//	 * 
//	 * @param source
//	 *            StyledText
//	 * 
//	 * @return
//	 */
//	public static boolean checkDateTime(StyledText text) {
//
//		if (text.getText() != null && text.getText().trim().length() > 0) {
//			String str = text.getText().trim();
//			Date date = null;
//			try {
//				date = changeDate(str);
//				String sdate = DateUtil.getDateStrToMinute(date);
//				text.setText(sdate);
//			} catch (Exception e1) {
//				MessageDialog.showMessage("时间格式错误，请重新输入，正确格式为：2006-06-06 6:6");
//	
//				text.setText("");
//				return false;
//			}
//		}
//		return true;
//
//	}
//
//	/**
//	 * 检查文本框中的日期格式
//	 * 
//	 * @param source
//	 *            Text
//	 * 
//	 * @return
//	 */
//	public static boolean checkDate(Text text) {
//
//		if (text.getText() != null && text.getText().trim().length() > 0) {
//			String str = text.getText().trim();
//			Date date = null;
//			try {
//				date = changeDate(str);
//				String sdate = DateUtil.getDateStrToDay(date);
//				text.setText(sdate);
//			} catch (Exception e1) {
//				MessageDialog.showMessage("时间格式错误，请重新输入，正确格式为：2006-06-06");
//	
//				text.setText("");
//				return false;
//			}
//		}
//		return true;
//	}
//	/**
//	 * 检查文本框中的日期格式
//	 * 
//	 * @param source
//	 *            Text
//	 * 
//	 * @return
//	 */
//	public static boolean checkDate(WText text) {
//
//		if (text.getText() != null && text.getText().trim().length() > 0) {
//			String str = text.getText().trim();
//			Date date = null;
//			try {
//				date = changeDate(str);
//				String sdate = DateUtil.getDateStrToDay(date);
//				text.setText(sdate);
//			} catch (Exception e1) {
//				MessageDialog.showMessage("时间格式错误，请重新输入，正确格式为：2006-06-06");
//		
//				text.setText("");
//				return false;
//			}
//		}
//		return true;
//	}
//	/**
//	 * 检查日期输入
//	 * @param text
//	 * @return
//	 */
//	public static boolean checkDateInput(Text text) {
//
//		if (text.getText() != null && text.getText().trim().length() > 0) {
//			String str = text.getText().trim();
//			Date date = null;
//			try {
//				date = changeDate(str);
//				String sdate = DateUtil.getDateStrToDay(date);
//				text.setText(sdate);
//			} catch (Exception e1) {
//				text.setText("");
//				text.setFocus();
//				MessageDialog.showMessage("时间格式错误，请重新输入，正确格式为：2006-06-06");
//				return false;
//			}
//		}
//		return true;
//	}
//
//	/**
//	 * 检查文本框中的日期格式
//	 * 
//	 * @param source
//	 *            StyledText
//	 * 
//	 * @return
//	 */
//	public static void checkDate(StyledText source) {
//
//		source.addFocusListener(new FocusAdapter() {
//
//			public void focusLost(FocusEvent e) {
//				StyledText text = (StyledText) e.getSource();
//				if (text.getText() != null
//						&& text.getText().trim().length() > 0) {
//					String str = text.getText().trim();
//					Date date = null;
//					try {
//						date = changeDate(str);
//						String sdate = DateUtil.getDateStrToDay(date);
//						text.setText(sdate);
//					} catch (Exception e1) {
//						MessageDialog
//								.showMessage("时间格式错误，请重新输入，正确格式为：2006-06-06");
//						
//						text.setText("");
//					}
//				}
//			}
//		});
//	}

	private static Date changeDate(String str) throws Exception {
		Date date = null;
		if (str.indexOf(" ") >= 0) {
			String[] strArray = str.split(":");
			if (strArray != null) {
				if (strArray.length == 2) {
					str = str + ":00";
				}
			}
			date = DateUtil.getDateTimeFromString(str);

		} else {

			date = DateUtil.getDateFromString(str);

		}

		return date;
	}

	/**
	 * 获取限时急件时间
	 * 
	 * @throws ParseException
	 */
	public static String getLimitDispatchTime() {
		try {
			String dateTime = DateUtil.getCurrentServerTime();
			DateFormat datef = DateFormat.getDateTimeInstance();

			SimpleDateFormat m_DateFormatter = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm");
			Date sDate = datef.parse(dateTime);
			Calendar cal = Calendar.getInstance();
			cal.setTime(sDate);
			cal.add(Calendar.HOUR, 2);
			Date eDate = cal.getTime();
			String str = m_DateFormatter.format(eDate);

			return str;
		} catch (Exception e) {
			log.info(e.getMessage());
			return null;
		}

	}

	/**
	 * 获取特急件时间
	 * 
	 * @throws ParseException
	 */

	public static String getEspecialDispatchTime() {
		try {
			String dateTime = DateUtil.getCurrentServerTime();
			DateFormat datef = DateFormat.getDateTimeInstance();

			SimpleDateFormat m_DateFormatter = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm");
			Date sDate = datef.parse(dateTime);
			Calendar cal = Calendar.getInstance();
			cal.setTime(sDate);
			cal.add(Calendar.HOUR, 8);
			Date eDate = cal.getTime();
			String str = m_DateFormatter.format(eDate);

			return str;
		} catch (Exception e) {
			log.info(e.getMessage());
			return null;
		}

	}

	// 获取时间到分
	public static String getTimeMinute() {
		try {
			String dateTime = DateUtil.getCurrentServerTime();
			DateFormat datef = DateFormat.getDateTimeInstance();

			SimpleDateFormat m_DateFormatter = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm");
			Date sDate = datef.parse(dateTime);
			Calendar cal = Calendar.getInstance();
			cal.setTime(sDate);
			Date eDate = cal.getTime();
			String str = m_DateFormatter.format(eDate);

			return str;
		} catch (Exception e) {
			log.info(e.getMessage());
			return null;
		}

	}
	
		    

	public static boolean isYesterdayORtoday(String newsCreatTime)
	{	  
		try
		{
			String today = DateUtil.getDate();
			newsCreatTime = DateUtil.getDateTime(newsCreatTime);
			String yesterday = getYesterday(newsCreatTime);
			if(today.equals(newsCreatTime) || today.equals(yesterday)){
				return true;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;  
	}
	
	/**
	 * 获取上一天的信息
	 * @param nowDate format:2006-06-17
	 * @return
	 */
	public static String getYesterday(String nowDate)
	{
		String yesterday = "";
		int year = 0;
		int month = 0;
		int day = 0;
		try
		{
			year = Integer.parseInt(nowDate.substring(0,nowDate.indexOf("-")));
			month = Integer.parseInt(nowDate.substring(nowDate.indexOf("-")+1,nowDate.lastIndexOf("-")));
			day = Integer.parseInt(nowDate.substring(nowDate.lastIndexOf("-")+1));
			
			
			day = day +1;
			if(day == 0)
			{
				month = month - 1;
				if(month == 0)
				{
					//January
					month = 12;
					day = 31;
					year = year - 1;
				}
				else
				{
					//not Jan.
					switch(month)
					{
					//1|3|5|7|8|10|12) day=31;;
					case 1:
						day = 31;
						break;
					case 3:
						day = 31;
						break;
					case 5:
						day = 31;
						break;
					case 7:
						day = 31;
						break;
					case 8:
						day = 31;
						break;
					case 10:
						day = 31;
						break;
					case 12:
						day = 31;
						break;
						//4|6|9|11) day=30;;
					case 4:
						day = 30;
						break;
					case 6:
						day = 30;
						break;
					case 9:
						day = 30;
						break;
					case 11:
						day = 30;
						break;
					case 2:
						if(year % 4 ==0 && year % 100 !=0 || year % 400 ==0) 
						{
							//leap year
							day = 29;
						}
						else day = 28;
					}
				}
			}
			         
			String monthStr = "";
			String dayStr = "";
			         
			if(month < 10)
			{
				monthStr = "0" + String.valueOf(month);
			}
			else
			{
				monthStr = String.valueOf(month);
			}
			   
			if(day < 10)
			{
				dayStr = "0" + String.valueOf(day);
			}
			else
			{
				dayStr = String.valueOf(day);
			}
			         
			yesterday = String.valueOf(year) + "-" + monthStr + "-" + dayStr;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return yesterday;
	}
	
	public static int getLastDateOfMonth(Date date){
		int lastDate = 0;
		Calendar cal = Calendar.getInstance();
		cal.set(date.getYear(), date.getMonth()+1,-1);
		lastDate = cal.get(cal.DATE);
		return lastDate+1;
	}
	
	
	public static Date parseStringToDate(String usedate,String hour,String minute){
		Date date=null;
		SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			date = sd.parse(usedate+" "+hour+":"+minute);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * 输入日期模式，例如：yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static Date getLongDate() {
		return	getDate(DateUtil.getDateTime(),"yyyy-MM-dd HH:mm:ss");
	}
	
	
	/**
	 * 返回日期格式
	 * 
	 * @return String
	 */
	public static String getDateYearM(Date date) {
		String dateStr = null;
		try {
			dateStr = DateFormatUtils.format(date, "yyyy-MM");
			return dateStr;
		} catch (Exception e) {
			
		}
		return null;
	}
	
	public static Map<String, Date> extendDate(String date) {
		Map<String, Date> dateMap = new HashMap<String, Date>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date startDate = null;
		Date endDate = null;
		try {
			startDate = format.parse(date + " 00:00:00");
			endDate = format.parse(date + " 23:59:59");
		} catch (ParseException e) {
			log.error("转换日期失败", e);
		}
		dateMap.put("startDate", startDate);
		dateMap.put("endDate", endDate);
		return dateMap;
	}

//	public static Map<String, Date> getStartEndDate() {
//		Map<String, Date> dateMap = new HashMap<String, Date>();
//		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//		String date=format.format(new Date());
//		System.out.println("date"+date);
//		Date startDate = null;
//		Date endDate = null;
//		try {
//			System.out.println("start="+date + " 00:00:00");
//			System.out.println("end="+date + " 23:59:59");
//			startDate = format.parse(date + " 00:00:00");
//			endDate = format.parse(date + " 23:59:59");
//		} catch (ParseException e) {
//			log.error("转换日期失败", e);
//		}
//		dateMap.put("startDate", startDate);
//		dateMap.put("endDate", endDate);
//		return dateMap;
//	}
	
	public static Map<String, Object> getStartEndDate() {
		Map<String, Object> dateMap = new HashMap<String, Object>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date startDate = new Date();
		startDate.setHours(00);
		startDate.setMinutes(00);
		startDate.setSeconds(00);
		String strStartDate = format.format(startDate);
		
		Date endDate = new Date();
		endDate.setHours(23);
		endDate.setMinutes(59);
		endDate.setSeconds(59);
		String strEndDate = format.format(endDate);
	    
		dateMap.put("startDate", strStartDate);
		dateMap.put("endDate", strEndDate);
		
		return dateMap;
	}
	
	public static Date getEndDate(String date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date endDate = DateUtil.toDate(date);
		endDate.setHours(23);
		endDate.setMinutes(59);
		endDate.setSeconds(59);
		//String strEndDate = format.format(endDate);
		return endDate;
	}
	
	public static Date getStartDate(String date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date startDate = DateUtil.toDate(date);
		startDate.setHours(00);
		startDate.setMinutes(00);
		startDate.setSeconds(00);
		//String strStartDate = format.format(startDate);
		return startDate;
	}
	
	//当前日期加N分钟
	public static Date addMinute(Date date,int minute) {    
	    Calendar calendar = Calendar.getInstance();    
	    calendar.setTime(date);    
	    calendar.add(Calendar.MINUTE, minute);    
	    return calendar.getTime();    
	} 
	//当前日期加1天
		public static String addDay(String specifiedDay) {    
			
			Calendar c = Calendar.getInstance(); 
			Date date=null; 
			try { 
			date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay); 
			} catch (ParseException e) { 
			e.printStackTrace(); 
			} 
			c.setTime(date); 
			int day=c.get(Calendar.DATE); 
			c.set(Calendar.DATE,day+1); 

			String dayAfter=new SimpleDateFormat("yyyy-MM-dd").format(c.getTime()); 
			return dayAfter; 

		} 
	 public static int compare_date(String DATE1, String DATE2) {
	        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	        try {
	            Date dt1 = df.parse(DATE1);
	            Date dt2 = df.parse(DATE2);
	            if (dt1.getTime() > dt2.getTime()) {
	                System.out.println("dt1 在dt2前");
	                return 1;
	            } else if (dt1.getTime() < dt2.getTime()) {
	                System.out.println("dt1在dt2后");
	                return -1;
	            } else {
	                return 0;
	            }
	        } catch (Exception exception) {
	            exception.printStackTrace();
	        }
	        return 0;
	    }
	 public static int compare_date(String DATE1, String DATE2,DateFormat df) {
	        try {
	            Date dt1 = df.parse(DATE1);
	            Date dt2 = df.parse(DATE2);
	            if (dt1.getTime() > dt2.getTime()) {
	                System.out.println("dt1 在dt2前");
	                return 1;
	            } else if (dt1.getTime() < dt2.getTime()) {
	                System.out.println("dt1在dt2后");
	                return -1;
	            } else {
	                return 0;
	            }
	        } catch (Exception exception) {
	            exception.printStackTrace();
	        }
	        return 0;
	    }
		public static String processMoneyFormat(String money)
		{
			String lb,rb,lrb;
			String ss="";		
//			String b="881180.10";
			int ins=money.indexOf(".");
			System.out.println("iss"+ins);
			if(ins != -1)
			{
				String[] strings=money.split("\\.");
				lb=strings[0];
				rb=strings[1];
				System.out.println("lb:"+lb+" rb:"+rb);			
				int irb=rb.length();
				if(irb == 1)
					rb=strings[1]+0;
				else
				    rb=strings[1];	
				ss=lb+"."+rb;
			}else {
				ss=money+".00";
			}
			System.out.println("$$$$$$$$$$$$ss:"+ss);
			return ss;
		}
	
	public static int getDayByDate(String date)
	{
		Integer day=Integer.valueOf(date.substring(5,7));
		if (day==1) {
			day=1;
		}else {
			day=day-1;
		}
		return day;
	}
	
	/**
	 * 计算当月最后一天
	 * @return dd
	 */
	public static int getDefaultDay(Date settleDate) {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("dd");
	
		Calendar lastDate = Calendar.getInstance();	
		lastDate.setTime(settleDate);
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1号	
		lastDate.add(Calendar.MONTH, 1);// 加一个月，变为下月的1号
		lastDate.add(Calendar.DATE, -1);// 减去一天，变为当月最后一天
	
		str = sdf.format(lastDate.getTime());
		return Integer.valueOf(str);
	}
	
	/**
	* 得到现在日期
	* @return dd
	*/
	public static int getStringToday() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd");
		String dateString = formatter.format(currentTime);
		return Integer.valueOf(dateString);
	}
	
	/**
	* 获得时间的日
	* @return dd
	*/
	public static int getDateDay(Date date) {
	    Calendar cal=Calendar.getInstance();  
	    cal.setTime(date); 
	    cal.get(Calendar.DAY_OF_MONTH);
		return 	cal.get(Calendar.DAY_OF_MONTH);
	}
	
	/**
	* 时间减N天
	* @return dd
	*/
	public static int getIntMinusDay(Date date,int day) {
       Calendar calendar = Calendar.getInstance();      
       calendar.setTime(date);      
       calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - day);  
       Date mDate = calendar.getTime();
       return  getDateDay(mDate);
	}
	/**
	* 时间减N天
	* @return Date
	*/
	public static Date getDateMinusDay(Date date,int day) {
       Calendar calendar = Calendar.getInstance();      
       calendar.setTime(date);      
       calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - day);  
       Date mDate = calendar.getTime();
       return  mDate;
	}
	
	/**
	* 时间加N个月
	* @return Date
	*/
	public static Date getDateNextMonth(Date date,int month) {
       Calendar calendar = Calendar.getInstance();      
       calendar.setTime(date);      
       calendar.add(Calendar.MONTH, month);
       Date mDate = calendar.getTime();
       return  mDate;
	}
	
	public static int getMonthOfLastDay(Date date)
	{
		String lastday="";
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DATE, 1);
		cal.add(Calendar.MONTH, 1);// 加一个月，变为下月的1号
		cal.add(Calendar.DATE, -1);// 减去一天，变为当月最后一天
		SimpleDateFormat sdf = new SimpleDateFormat("d");
		lastday = sdf.format(cal.getTime());
		return Integer.valueOf(lastday);
	}
	
 
	//指定日期加N小时
	public static Date addHour(Date date,int hours) {    
	    Calendar calendar = Calendar.getInstance();    
	    calendar.setTime(date);    
	    calendar.add(Calendar.HOUR, hours);    
	    return calendar.getTime();    
	} 	

	
	/**
	 * lbb                    没有用
	 * 两个时间之间相差的月数
	 * @param date1 :开始时间
	 * @param date2 ：结束时间
	 * @return  ：相差月数
	 * @throws ParseException
	 */
	public static int countMonths(String date1,String date2) throws ParseException{
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        
        Calendar c1=Calendar.getInstance();
        Calendar c2=Calendar.getInstance();
        
        c1.setTime(sdf.parse(date1));
        c2.setTime(sdf.parse(date2));
        
        int year =c2.get(Calendar.YEAR)-c1.get(Calendar.YEAR);
        int year2 = c2.get(Calendar.YEAR); //c2的年
        int month2 = c2.get(Calendar.MONTH);//c2的月
        int day2 = c2.get(Calendar.DATE);//c2的天
        int year1 = c1.get(Calendar.YEAR); //c1的年
        int month1 = c1.get(Calendar.MONTH);//c1的月
        int day1 = c1.get(Calendar.DATE);//c1的天
        int result=0;
     
        //
        int data= c2.get(Calendar.DATE)-c1.get(Calendar.DATE);
        int month11=month1+1;
        int month22=month2+1;
/*        System.out.println(day1+" "+month11+" "+year1);
        System.out.println(day2+" "+month22+" "+year2);
        boolean lastDay1 = isLastDay(day1,month11,year1);
        boolean lastDay2 = isLastDay(day2,month22,year2);
        System.out.println("pppp");
        System.out.println(lastDay1);
        System.out.println(lastDay2);*/
        //时间1的最后一天，时间2的最后一天相等，算一个月（主要是2月）
        if(isLastDay(day1,month11,year1) && isLastDay(day2,month22,year2)){
        	System.out.println("000000");
      	  result= year*12+c2.get(Calendar.MONTH)-c1.get(Calendar.MONTH);
      	  return result;
        }else{
        	  //开始日期若小月结束日期
            if(data<0){
         	  
         		System.out.println("-----");
         		System.out.println(year*12+c2.get(Calendar.MONTH)-c1.get(Calendar.MONTH)-1);
         		return year*12+c2.get(Calendar.MONTH)-c1.get(Calendar.MONTH)-1;
         	}
            if(data==0){
         		return year*12+c2.get(Calendar.MONTH)-c1.get(Calendar.MONTH);
         	}else{
         		return year*12+c2.get(Calendar.MONTH)-c1.get(Calendar.MONTH);
         	}
        }
      
        	
        }
       
        
	
	//两个时间相差的天数：只管天，月，不管年      没有用
	public static int countDays(String date1,String date2) throws ParseException{
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        
        Calendar c1=Calendar.getInstance();
        Calendar c2=Calendar.getInstance();
        
        c1.setTime(sdf.parse(date1));
        c2.setTime(sdf.parse(date2));
        int days=0;
      //  int year =c2.get(Calendar.YEAR)-c1.get(Calendar.YEAR);
        
        //
     //   int  month= c2.get(Calendar.MONTH)-c1.get(Calendar.MONTH);
        
       // System.out.println("yyyyyy"+);
       
        int year2 = c2.get(Calendar.YEAR); //c2的年
        int month2 = c2.get(Calendar.MONTH);//c2的月
        int day2 = c2.get(Calendar.DATE);//c2的天
        int year1 = c1.get(Calendar.YEAR); //c1的年
        int month1 = c1.get(Calendar.MONTH);//c1的月
        int day1 = c1.get(Calendar.DATE);//c1的天
        
        int day= day2-day1;
        if(day ==0){
        	days=0;
        }
        else if(day<0){
        	
            	int maxDaysOfMonth = getMaxDaysOfMonth(month2,year2);
            	days=maxDaysOfMonth-day1+day2;
            	System.out.println(days);
            
        }else{
        	//int i = c2.get(Calendar.MONTH);
        	//int maxDaysOfMonth = getMaxDaysOfMonth(i,2016);
        	
        	days=day2-day1;
        	System.out.println("kkkk"+days);
        	
        }
        
        
     //   System.out.println(day);
       
        return days;
        
    }
	
	
	/**
	 * 获取一个月最大天数,考虑年份是否为润年
	 * @param date
	 * @return
	 */
	private static int getMaxDaysOfMonth(int month,int year) {
		/*int month = date.get(Calendar.MONTH);*/
		int maxDays = 0;
		switch (month-1) {
		case Calendar.JANUARY:
		case Calendar.MARCH:
		case Calendar.MAY:
		case Calendar.JULY:
		case Calendar.AUGUST:
		case Calendar.OCTOBER:
		case Calendar.DECEMBER:
			maxDays = 31;
			break;
		case Calendar.APRIL:
		case Calendar.JUNE:
		case Calendar.SEPTEMBER:
		case Calendar.NOVEMBER:
			maxDays = 30;
			break;
		case Calendar.FEBRUARY:
			if (isLeapYear(year)) {
				maxDays = 29;
			} else {
				maxDays = 28;
			}
			break;
		}
		return maxDays;
	}

	/**
	 * 是否是闰年
	 * @param year
	 * @return
	 */
	public static boolean isLeapYear(int year){
		if(year % 4 == 0 && year % 100 != 0 || year % 400 == 0){
		    return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 判断是否是当月最后一天
	 * @param date
	 * @param month
	 * @param year
	 * @return
	 */
	public static boolean isLastDay(int date,int month,int year){
		int maxDaysOfMonth = getMaxDaysOfMonth(month, year);
		if(maxDaysOfMonth == date){
			return true;
		}else{
			return false;
		}
		
		
	}
	
/*	public static void main(String[] args) throws ParseException {
		int countMonths = countMonths("2000-1-31","2000-3-29");
		int countDays = countDays("2000-1-31","2000-3-29");
		boolean lastDay = isLastDay(29,2,2000);
		System.out.println(lastDay);
		System.out.println(countMonths+"---------"+countDays);
		int months = getMonths("2000-1-20","2000-2-15");
		Map<String, Object> days = getDays("2000-1-20","2000-2-15");
		System.out.println(days);
		System.out.println(months);
		
	}*/

	/**
	 * lbb 获取月数（和chp那边一样）
	 * @param date1 :开始日期
	 * @param date2 ：结束日期
	 * @return
	 * @throws ParseException
	 */
   public static int getMonths(String date1,String date2) throws ParseException{
	   SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
      
       Calendar c1=Calendar.getInstance();
       Calendar c2=Calendar.getInstance();
       
       c1.setTime(sdf.parse(date1));
       c2.setTime(sdf.parse(date2));

       int year2 = c2.get(Calendar.YEAR); //c2的年
      // int month2 = c2.get(Calendar.MONTH)+1;//c2的月
       int day2 = c2.get(Calendar.DATE);//c2的天
       int year1 = c1.get(Calendar.YEAR); //c1的年
      // int month1 = c1.get(Calendar.MONTH)+1;//c1的月
       int day1 = c1.get(Calendar.DATE);//c1的天
       
       int year =year2-year1;
       if(day2-day1>=0){
    		  return year*12+c2.get(Calendar.MONTH)-c1.get(Calendar.MONTH);
       }else{
    		 return year*12+c2.get(Calendar.MONTH)-c1.get(Calendar.MONTH)-1;
       }
    	   
       
	   
   }
   
   /**
    * lbb返回 两个月相差的天
    * @param date1 ：开始日期
    * @param date2 ：结束日期
    * @return
    * @throws ParseException
    */
   public static Map<String,Object> getDays(String date1,String date2) throws ParseException{
	   SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	   Map<String,Object>map=new HashMap<String, Object>();
       Calendar c1=Calendar.getInstance();
       Calendar c2=Calendar.getInstance();
       
       c1.setTime(sdf.parse(date1));
       c2.setTime(sdf.parse(date2));

       int year2 = c2.get(Calendar.YEAR); //c2的年
       int month2 = c2.get(Calendar.MONTH)+1;//c2的月
       int day2 = c2.get(Calendar.DATE);//c2的天
       int year1 = c1.get(Calendar.YEAR); //c1的年
       int month1 = c1.get(Calendar.MONTH)+1;//c1的月
       int day1 = c1.get(Calendar.DATE);//c1的天
       int day=day2-day1;
       
       if(day>=0){
    	   System.out.println(day);
    	   map.put(month2+"", day);
    	   return map;
       }else{
    	   int maxDaysOfMonth1=0;
    	   int day11=0;
    	   if(month2==1){
    		   maxDaysOfMonth1 = getMaxDaysOfMonth(12,year1);
    		   day11=maxDaysOfMonth1-day1;
    		   map.put(12+"", day11);
    	   }else{
    		   maxDaysOfMonth1 = getMaxDaysOfMonth(month2-1,year1);
    		   day11=maxDaysOfMonth1-day1;
    		   map.put((month2-1)+"", day11);
    	   }
    	  
    	  
    	   map.put(month2+"", day2);
    	   System.out.println(map);
    	   return map;
       }
       
	   
   }
   
   /**
    * 获取的利息：
    * @param account 投资金额
    * @param rate  月利率
    * @param date1 起始日期
    * @param date2 结束日期
    * @return
    * @throws ParseException
    */
   //获取总利息：月利息+天利息
   public static  double getInvestRate(String account,String rate,String date1,String date2 ) throws ParseException{
	   SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
       Calendar c2=Calendar.getInstance();
       
       c2.setTime(sdf.parse(date2));

       int year2 = c2.get(Calendar.YEAR); //c2的年
	   int months = getMonths( date1,date2); //月数
	   double invest=0;
	   Map<String, Object> dayMap = getDays(date1,date2);//天数
	   Set<String> keySet = dayMap.keySet();
	   Iterator<String> its = keySet.iterator();
	   String keys = null;
	   double rateD = Double.parseDouble(rate);
	   double accountD = Double.parseDouble(account);
	   //利息
	   //invest=accountD*(rateD/100)*months;
	   invest=BigDecimalUtil.getMonthRateInvest(accountD,rateD,months);
	   while(its.hasNext()){
		   keys = its.next();
		   int object = Integer.parseInt(String.valueOf(dayMap.get(keys)));
		   //某个月的天数
		   int maxDaysOfMonth = getMaxDaysOfMonth(Integer.parseInt(keys), year2);
		   //rate*months+rate/maxDaysOfMonth*天数
		  // invest+=accountD*(rateD/100/maxDaysOfMonth)*object;
		   invest +=BigDecimalUtil.getDayRateInvest(accountD, rateD, maxDaysOfMonth, object);
	   }
	   
	   return invest;
   }
	
	/**
	 * 传入0获得当前日期，传入1/2……，标示当前日期的后几个月
	 * 
	 * @param month
	 * @return
	 */
	public static Date getDateByMonth(int month) {

		Date date = null;

		Calendar cal = Calendar.getInstance();

		if (month == 0) {
			date = getDateByMonth();
		} else {
			cal.add(Calendar.MONTH, month);
			date = cal.getTime();
		}
		return date;

	}

	public static Date getDateByMonth() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, 0);
		return cal.getTime();
	}
   
}
