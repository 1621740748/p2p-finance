package cn.itcast.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
/**
 * 类描述：计算两个日期相差多少个月零几天
 */
public class CalcuDateUtil {

	private long differenceOfMonths;// 月份差值
	private long differenceOfDays;// 天数差值

	static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	public static CalcuDateUtil calculate(String startdate, String endDate) {
		try {
			return calculate(dateFormat.parse(startdate),
					dateFormat.parse(endDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 计算差值,注意 endDate > startDate
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static CalcuDateUtil calculate(Date startDate, Date endDate) {
		if (startDate.after(endDate))
			return null;
		CalcuDateUtil dataCalculate = new CalcuDateUtil();

		Calendar firstDay = Calendar.getInstance();
		Calendar lastDay = Calendar.getInstance();
		firstDay.setTime(startDate);
		lastDay.setTime(endDate);

		// 算出天数总差值
		long allDays = ((lastDay.getTimeInMillis()) - (firstDay
				.getTimeInMillis())) / (1000 * 24 * 60 * 60);

		Calendar loopEndDay = calculateLoopEndOfDate(firstDay, lastDay);
		dataCalculate.setDifferenceOfDays(0);
		dataCalculate.setDifferenceOfMonths(0);

		int month = firstDay.get(Calendar.MONTH);
		while (!firstDay.equals(loopEndDay)) {
			firstDay.add(Calendar.DAY_OF_MONTH, 1);
			allDays--;
			if (month != firstDay.get(Calendar.MONTH)) {
				month = firstDay.get(Calendar.MONTH);
				dataCalculate.setDifferenceOfMonths(dataCalculate
						.getDifferenceOfMonths() + 1);
			}
		}
		dataCalculate.setDifferenceOfDays(allDays);
		return dataCalculate;

	}

	/**
	 * 计算循环终止日期 例如:开始日：2011-03-17 结束日 2012-02-13 ,循环终止日期 2012-01-17;
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	private static Calendar calculateLoopEndOfDate(Calendar startDate,
			Calendar endDate) {
		int year = endDate.get(Calendar.YEAR);
		int month = endDate.get(Calendar.MONTH);
		int day = startDate.get(Calendar.DAY_OF_MONTH);
		int maxDaysInMonth = getMaxDaysOfMonth(new GregorianCalendar(year,
				month, 1));

		if (year > startDate.get(Calendar.YEAR)) {
			if (month == Calendar.JANUARY) {
				year -= 1;
				month = Calendar.DECEMBER;
			} else {
				if (day > maxDaysInMonth) {
					month -= 1;
					endDate.set(year, month, 1);
					day = getMaxDaysOfMonth(new GregorianCalendar(year, month,
							1));
				} else {
					if (day > endDate.get(Calendar.DAY_OF_MONTH)) {
						month -= 1;
						endDate.set(year, month, 1);
						maxDaysInMonth = getMaxDaysOfMonth(new GregorianCalendar(
								year, month, 1));
						;
						if (day > maxDaysInMonth) {
							day = maxDaysInMonth;
						}
					}
				}
			}
		} else {
			if (day > maxDaysInMonth) {
				month -= 1;
				endDate.set(year, month, 1);
				day = getMaxDaysOfMonth(new GregorianCalendar(year, month, 1));
			} else {
				if (day > endDate.get(Calendar.DAY_OF_MONTH)) {
					month -= 1;
					endDate.set(year, month, 1);
					maxDaysInMonth = getMaxDaysOfMonth(new GregorianCalendar(
							year, month, 1));
					if (day > maxDaysInMonth) {
						day = maxDaysInMonth;
					}
				}
			}
		}
		return new GregorianCalendar(year, month, day);
	}

	/**
	 * 获取一月最大天数,考虑年份是否为润年
	 * @param date
	 * @return
	 */
	private static int getMaxDaysOfMonth(GregorianCalendar date) {
		int month = date.get(Calendar.MONTH);
		int maxDays = 0;
		switch (month) {
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
			if (date.isLeapYear(date.get(Calendar.YEAR))) {
				maxDays = 29;
			} else {
				maxDays = 28;
			}
			break;
		}
		return maxDays;
	}

	public long getDifferenceOfMonths() {
		return differenceOfMonths;
	}

	public void setDifferenceOfMonths(long differenceOfmonths) {
		this.differenceOfMonths = differenceOfmonths;
	}

	public long getDifferenceOfDays() {
		return differenceOfDays;
	}

	public void setDifferenceOfDays(long differenceOfDays) {
		this.differenceOfDays = differenceOfDays;
	}
	
	/**
	 * 方法描述：获得两个日期之间差多少个月 如："2011-03-17"到2011-04-18 返回：1；"2011-03-17"到2011-04-16 返回：0
	 * @param startDate
	 * @param endDate
	 * @return long 
	 */
	public static int getFewMonths(Date startDate,Date endDate){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		CalcuDateUtil dateCalculate = CalcuDateUtil.calculate(sdf.format(startDate),sdf.format(endDate));
		Long l =dateCalculate.getDifferenceOfMonths();
		System.out.println("yue:"+l);
		return l.intValue();
	}
	/**
	 * 方法描述：获得两个日期之间差多少个月 如："2011-03-17"到2011-04-18 返回：1；"2011-03-17"到2011-04-16 返回：0
	 * @param startDate
	 * @param endDate
	 * @return long 
	 * 
	 */
	public static int getFewMonths(String startDate,String endDate){
		CalcuDateUtil dateCalculate = CalcuDateUtil.calculate(startDate,endDate);
		Long l =dateCalculate.getDifferenceOfMonths();
		return l.intValue();
	}
	
	/**
	 * 方法描述：获得两个日期之间差多少个天 注：将整月 除去"2011-03-17"到2011-04-20 返回：3天；"2011-03-17"到2011-04-16 返回：30
	 * @param startDate
	 * @param endDate
	 * @return long
	 */
	public static int getFewDays(String startDate,String endDate){
		CalcuDateUtil dateCalculate = CalcuDateUtil.calculate(startDate,endDate);
		Long l= dateCalculate.getDifferenceOfDays();
		return l.intValue();
	}
	
	/**
	 * 方法描述：获得两个日期之间差多少个天 注：将整月 除去"2011-03-17"到2011-04-20 返回：3天；"2011-03-17"到2011-04-16 返回：30
	 * @param startDate
	 * @param endDate
	 * @return long
	 */
	public static int getFewDays(Date startDate,Date endDate){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		CalcuDateUtil dateCalculate = CalcuDateUtil.calculate(sdf.format(startDate),sdf.format(endDate));
		Long l= dateCalculate.getDifferenceOfDays();
		System.out.println("tian:"+l);
		return l.intValue();
	}
	
	/**
	 * 根据“还款日”得到“应还款日期”
	 * @param intDay
	 * @return Date
	 */
	public static Date getReturnDate(int intDay) {
		
		System.out.println("intDay="+intDay);
		Date date=null;
		
		Calendar cal = Calendar.getInstance();	//实例化日历类
		int year=cal.get(Calendar.YEAR);		//当前年
		int month=cal.get(Calendar.MONTH);	//当前月
		int dayNow=cal.get(Calendar.DAY_OF_MONTH);	//当前日
		
		if (dayNow<intDay) 
		{
			// 设置Calendar月份数为当前月  
			int end=cal.getActualMaximum(cal.DAY_OF_MONTH);
			if (end<intDay) {
				//设置这个月底那天为“应还日期”
				cal.set(year,month,end);
				date=cal.getTime();
			}else {
				//设置还款日那天为“应还日期”
				cal.set(year,month,intDay);
				date=cal.getTime();
				return date;
			}
			
		}else if(dayNow>=intDay)
		{
			//需要判断下个月最后一天是否大于还款日，如果大于则还款日那天还款，否则下月最后一天还款
			// 设置Calendar月份数为下一个月  
			cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
			int end=cal.getActualMaximum(cal.DAY_OF_MONTH);
		
			if (end<intDay) {
				cal.set(year,month+1,end);
				date=cal.getTime();
			}else {
				//下个月，还款日那天还款
				cal.set(year,month+1,intDay);
				date=cal.getTime();
			}
		}
		return date;
	}
	
	public static void main(String[] args) {
//		CalcuDateUtil dateCalculate = CalcuDateUtil.calculate("2011-03-17","2012-05-18");
//		System.out.println("月差为: " + dateCalculate.getDifferenceOfMonths());
//		System.out.println("天差为: " + dateCalculate.getDifferenceOfDays());
//		System.out.println();
		//int m = CalcuDateUtil.getFewMonths("2014-11-27","2015-01-28");
		//System.out.println(m);
		//此时是2010年8月25日15时18分18秒
		
//		cal.set(2015,03,12);
//		cal.getTime();
//		String aString=cal.toString();
//		System.out.println("设置日期="+aString);
//		System.out.println("YEAR: "+cal.get(Calendar.YEAR));

//		Calendar cal = Calendar.getInstance();	//实例化日历类
//		int monthNow=cal.get(Calendar.MONTH);
//		int monthNext=monthNow+1;
		
//		System.out.println("MONTH: "+(int)cal.get(Calendar.MONTH));
//		System.out.println("DAY=: "+cal.get(Calendar.DAY_OF_MONTH));
//		System.out.println("HOUR_OF_DAY : "+cal.get(Calendar.HOUR_OF_DAY));
//		
//		// 设置Calendar月份数为下一个月  
//		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
//		int end=cal.getActualMaximum(cal.DAY_OF_MONTH);
//		System.out.println("next month last day="+end);
//		Date date=CalcuDateUtil.getReturnDate(31);
//		System.out.println("还款日为7时"+DateUtil.getDate(date));
	}

}
