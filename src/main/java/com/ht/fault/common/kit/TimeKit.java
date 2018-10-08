package com.ht.fault.common.kit;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.jfinal.template.stat.ParseException;

public class TimeKit {

	
	/**
	   * 获取现在时间
	   * 
	   * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
	   */
	public static String getStringDate() {
	   Date currentTime = new Date();
	   SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	   String dateString = formatter.format(currentTime);
	   return dateString;
	}
	/**
	   * 获取现在时间
	   * 
	   * @return 返回短时间字符串格式yyyy-MM-dd
	   */
	public static String getStringDateShort() {
	   Date currentTime = new Date();
	   SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	   String dateString = formatter.format(currentTime);
	   return dateString;
	}
	/**
	   * 获取时间 小时:分;秒 HH:mm:ss
	   * 
	   * @return
	   */
	public static String getTimeShort() {
	   SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
	   Date currentTime = new Date();
	   String dateString = formatter.format(currentTime);
	   return dateString;
	}
	/**
	   * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
	   * 
	   * @param strDate
	   * @return
	   */
	public static Date strToDateLong(String strDate) {
	   SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	   ParsePosition pos = new ParsePosition(0);
	   Date strtodate = formatter.parse(strDate, pos);
	   return strtodate;
	}
	/**
	   * 将长时间格式时间转换为字符串 yyyy-MM-dd HH:mm:ss
	   * 
	   * @param dateDate
	   * @return
	   */
	public static String dateToStrLong(java.util.Date dateDate) {
	   SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	   String dateString = formatter.format(dateDate);
	   return dateString;
	}
	/**
	   * 将短时间格式时间转换为字符串 yyyy-MM-dd
	   * 
	   * @param dateDate
	   * @param k
	   * @return
	   */
	public static String dateToStr(java.util.Date dateDate) {
	   SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	   String dateString = formatter.format(dateDate);
	   return dateString;
	}
	/**
	   * 将短时间格式字符串转换为时间 yyyy-MM-dd 
	   * 
	   * @param strDate
	   * @return
	   */
	public static Date strToDate(String strDate) {
	   SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	   ParsePosition pos = new ParsePosition(0);
	   Date strtodate = formatter.parse(strDate, pos);
	   return strtodate;
	}
	/**
	   * 得到现在时间
	   * 
	   * @return
	   */
	public static Date getNow() {
	   Date currentTime = new Date();
	   return currentTime;
	}
	/**
	   * 提取一个月中的最后一天
	   * 
	   * @param day
	   * @return
	   */
	public static Date getLastDate(long day) {
	   Date date = new Date();
	   long date_3_hm = date.getTime() - 3600000 * 34 * day;
	   Date date_3_hm_date = new Date(date_3_hm);
	   return date_3_hm_date;
	}
	/**
	   * 得到现在时间
	   * 
	   * @return 字符串 yyyyMMdd HHmmss
	   */
	public static String getStringToday() {
	   Date currentTime = new Date();
	   SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd HHmmss");
	   String dateString = formatter.format(currentTime);
	   return dateString;
	}
	/**
	   * 得到现在小时
	   */
	public static String getHour() {
	   Date currentTime = new Date();
	   SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	   String dateString = formatter.format(currentTime);
	   String hour;
	   hour = dateString.substring(11, 13);
	   return hour;
	}
	/**
	   * 得到现在分钟
	   * 
	   * @return
	   */
	public static String getTime() {
	   Date currentTime = new Date();
	   SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	   String dateString = formatter.format(currentTime);
	   String min;
	   min = dateString.substring(14, 16);
	   return min;
	}
	/**
	   * 根据用户传入的时间表示格式，返回当前时间的格式 如果是yyyyMMdd，注意字母y不能大写。
	   * 
	   * @param sformat
	   *             yyyyMMddhhmmss
	   * @return
	   */
	public static String getUserDate(String sformat) {
	   Date currentTime = new Date();
	   SimpleDateFormat formatter = new SimpleDateFormat(sformat);
	   String dateString = formatter.format(currentTime);
	   return dateString;
	}
	
	
	/**
	 * next_day:当前日期推算，format:日期格式化，calendar_int：获取日期类型 如：Calendar.DATE
	 * @param next_day，format，calendar_int
	 * @return String time="2017-12-29 02:24:10";
	 * */
		public static String getDate(int next_day,String format,int calendar_int){
			Calendar   cal   =   Calendar.getInstance();
			cal.add(calendar_int,next_day);
			String time = new SimpleDateFormat(format).format(cal.getTime());//昨天			
			return time;		
		}


		/**
		 * @return 从今天算起，计算30天前的日期时间,并转化成DT15的格式
		 * */
		public static String getThirtyDaysAgo() {
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd'T'000000");
			Calendar calendar = new GregorianCalendar();
			calendar.add(Calendar.DATE, -30);
			String beforDate = format.format(calendar.getTime());
			return beforDate;
		}
		/**
		 * @param Date(date)
		 * @return 是否是在工作时间，判断标准为：早8点-晚10点。
		 * */
		public static Boolean getWorkDay(Date date) throws Exception{
			SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd 08:00:00");
			SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd 22:00:00");
			  DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			  long firstTime = df.parse(df1.format(date)).getTime();
			  long lastTime = df.parse(df2.format(date)).getTime();  
			  if(lastTime-date.getTime()>=0&&date.getTime()-firstTime>=0){
				  //上班时间
				  return true;	
			  }else {
				  return false;	
			  }
		}
		


		public static String DT15toStr(String str) {
			if (str.length() == 15) {
				str = str.substring(4, 6) + "月" + str.substring(6, 8) + "日  " + str.substring(9, 11) + ":"
						+ str.substring(11, 13);
			}
			return str;

		}

		/**
		 * 
		 * 
		 * @return 获取本周开始时间
		 */
		public static Date getNowWeekBegin() {
			int mondayPlus;
			Calendar c = Calendar.getInstance();
			// 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
			int dayOfWeek = c.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以这里减1
			if (dayOfWeek == 1) {
				mondayPlus = 0;
			} else {
				mondayPlus = 1 - dayOfWeek;
			}
			c.add(Calendar.DAY_OF_MONTH, mondayPlus);
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			return c.getTime();
		}

		public static List<String> getThisWeedDate() {
			List<String> list = new LinkedList<String>();
			Date weekBegin = TimeKit.getNowWeekBegin();
			Calendar c = Calendar.getInstance();
			c.setTime(weekBegin);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
			for (int i = 0; i < 7; i++) {
				c.set(Calendar.HOUR_OF_DAY, 0);
				c.set(Calendar.MINUTE, 0);
				c.set(Calendar.SECOND, 0);
				String begin = sdf.format(c.getTime());
				c.set(Calendar.HOUR_OF_DAY, 23);
				c.set(Calendar.MINUTE, 59);
				c.set(Calendar.SECOND, 59);
				list.add(begin + "_" + sdf.format(c.getTime()));
				c.add(Calendar.DAY_OF_MONTH, 1);
			}
			return list;
		}

		/**
		 * 
		 * 获取最近日期串
		 * 
		 * @param preOrNext
		 *            -1往前推，1往后推
		 * @param day
		 *            天数
		 * @param hasToday
		 *            是否包含今天
		 * @param DT8_DT15_pattern
		 *            日期格式
		 * @param dateStrCount
		 *            日期个数 1或2
		 * @param split
		 *            分隔符
		 * @return
		 */
		public static List<String> getNiberWeedDate(
				int preOrNext, int day, boolean hasToday, String DT8_DT15_pattern, int dateStrCount, String split) {
			// 初始化preOrNext
			if (preOrNext != 1 && preOrNext != -1) {
				System.out.println("请输入正确的值");
				preOrNext = 1;
			}
			List<String> list = new LinkedList<String>();
			Calendar c = Calendar.getInstance();
			c.setTime(new Date());
			// 如果不包含当天，时间移动一个单位
			if (!hasToday)
				c.add(Calendar.DAY_OF_MONTH, preOrNext);
			String pattern = DT8_DT15_pattern;
			// 获取日期转换格式
			if (DT8_DT15_pattern.equals("DT8")) {
				pattern = "yyyyMMdd";
			} else if (DT8_DT15_pattern.equals("DT15")) {
				pattern = "yyyyMMdd'T'HHmmss";
			}
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			for (int i = 0; i < day; i++) {
				if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {// 跳过周六
					if (preOrNext == -1)
						c.add(Calendar.DAY_OF_MONTH, -1);// 向前推，往前移动1天
					else
						c.add(Calendar.DAY_OF_MONTH, 2);// 向后推，往后移动2天
				} else if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {// 跳过周日
					if (preOrNext == -1)
						c.add(Calendar.DAY_OF_MONTH, -2);// 向前推，往前移动2天
					else
						c.add(Calendar.DAY_OF_MONTH, 1);// 向后推，往后移动1天
				}
				// 按照pattern格式化时间得到字符串，
				c.set(Calendar.HOUR_OF_DAY, 0);
				c.set(Calendar.MINUTE, 0);
				c.set(Calendar.SECOND, 0);
				String begin = sdf.format(c.getTime());
				if (dateStrCount == 1) {
					list.add(begin);
				} else {
					c.set(Calendar.HOUR_OF_DAY, 23);
					c.set(Calendar.MINUTE, 59);
					c.set(Calendar.SECOND, 59);
					split = split == null ? "_" : split;
					list.add(begin + split.trim() + sdf.format(c.getTime()));
				}
				c.add(Calendar.DAY_OF_MONTH, preOrNext);
			}
			// 如果向前推，将列表反转
			if (preOrNext == -1) {
				Collections.reverse(list);
			}
			return list;
		}

		/**
		 * 获取当天起止时间
		 * 
		 * @return
		 */
		public static Map<String, Date> buildTodayStart_end() {
			Map<String, Date> map = new HashMap<String, Date>();
			Calendar c = Calendar.getInstance();
			c.setTime(new Date());
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			Date today_start = c.getTime();
			c.set(Calendar.HOUR_OF_DAY, 23);
			c.set(Calendar.MINUTE, 59);
			c.set(Calendar.SECOND, 59);
			Date today_end = c.getTime();
			map.put("start", today_start);
			map.put("end", today_end);
			return map;
		}
		

		/**
		 * 获取当天的前一天的起止时间
		 * 
		 * @return
		 */
		public static Map<String, Date> beforTodayStart_end() {
			Map<String, Date> map = new HashMap<String, Date>();
			Calendar c = Calendar.getInstance();
			c.setTime(new Date());
			c.set(Calendar.DATE,c.get(Calendar.DATE)-3);
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			Date today_start = c.getTime();
			c.set(Calendar.HOUR_OF_DAY, 23);
			c.set(Calendar.MINUTE, 59);
			c.set(Calendar.SECOND, 59);
			Date today_end = c.getTime();
			map.put("start", today_start);
			map.put("end", today_end);
			return map;
		}
		

		public static String getTodayStart(String pattern) {
			Calendar c = Calendar.getInstance();
			c.setTime(new Date());
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			SimpleDateFormat sdf;
			sdf = new SimpleDateFormat(pattern);
			return sdf.format(c.getTime());
		}

		public static Date getTodayStart() {
			Calendar c = Calendar.getInstance();
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			return c.getTime();
		}

		public static Date getTodayEnd() {
			Calendar c = Calendar.getInstance();
			c.set(Calendar.HOUR_OF_DAY, 23);
			c.set(Calendar.MINUTE, 59);
			c.set(Calendar.SECOND, 59);
			return c.getTime();
		}

		public static String getTodayEnd(String pattern) {
			Calendar c = Calendar.getInstance();
			c.set(Calendar.HOUR_OF_DAY, 23);
			c.set(Calendar.MINUTE, 59);
			c.set(Calendar.SECOND, 59);
			SimpleDateFormat sdf;
			sdf = new SimpleDateFormat(pattern);
			return sdf.format(c.getTime());
		}

		/**
		 * 获取指定日期起止时间
		 * 
		 * @return
		 */
		public static Map<String, Date> buildDateStart_end(Date date) {
			Map<String, Date> map = new HashMap<String, Date>();
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			Date today_start = c.getTime();
			c.set(Calendar.HOUR_OF_DAY, 23);
			c.set(Calendar.MINUTE, 59);
			c.set(Calendar.SECOND, 59);
			Date today_end = c.getTime();
			map.put("start", today_start);
			map.put("end", today_end);
			return map;
		}

		/**
		 * 转换时间
		 * 
		 * @param dateStr
		 * @param srcPattern
		 * @param destPattern
		 * @throws Exception 
		 */
		public static String format(String dateStr, String srcPattern, String destPattern) throws ParseException, Exception {
			SimpleDateFormat sdf = new SimpleDateFormat(srcPattern);
			sdf.applyPattern(srcPattern);
			Date date = sdf.parse(dateStr);
			sdf.applyPattern(destPattern);
			return sdf.format(date);
		}

		public static String format(String dateStr, String srcPattern, String destPattern, Long addTime)
				throws ParseException, Exception {
			SimpleDateFormat sdf = new SimpleDateFormat(srcPattern);
			sdf.applyPattern(srcPattern);
			Date date = new Date(sdf.parse(dateStr).getTime() + addTime);
			sdf.applyPattern(destPattern);
			return sdf.format(date);
		}

		/**
		 * 获取月份第一天
		 */
		public static Calendar getNextMonthFirstDay(int next) {
			Calendar c = Calendar.getInstance();
			c.set(Calendar.DAY_OF_MONTH, 1);
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			c.add(Calendar.MONTH, next);
			return c;
		}

		/**
		 * 获取月份最后一天
		 */
		public static Calendar getNextMonthLastDay(int next) {
			Calendar c = Calendar.getInstance();
			c.set(Calendar.DAY_OF_MONTH, 1);
			c.add(Calendar.MONTH, next + 1);
			c.add(Calendar.DAY_OF_MONTH, -1);
			return c;
		}

		/**
		 * 获取月份天数
		 */
		public static int getNextMonthDayCount(int next) {
			Calendar first = Calendar.getInstance();
			first.set(Calendar.DAY_OF_MONTH, 1);
			first.add(Calendar.MONTH, next);
			Calendar last = Calendar.getInstance();
			last.set(Calendar.DAY_OF_MONTH, 1);
			last.add(Calendar.MONTH, next + 1);
			last.add(Calendar.DAY_OF_MONTH, -1);
			return last.get(Calendar.DAY_OF_MONTH) - first.get(Calendar.DAY_OF_MONTH) + 1;
		}

		/**
		 * 获取两个日期的0点之间相差天数<br/>
		 * eg:12月1日 到 12月3日 结果为2
		 * 
		 * @param start
		 * @param end
		 * @return
		 */
		public static int getBetweenDay(Date start, Date end) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(start);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			Long startTime = calendar.getTimeInMillis();
			calendar.setTime(end);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			Long endTime = calendar.getTimeInMillis();
			Long times = (endTime - startTime) / (24 * 3600 * 1000);
			return times.intValue();
		}

		/**
		 * 计算两个日期之间相差的天数
		 * 
		 * @param minuend
		 *            被减数
		 * @param meiosis
		 *            减数
		 * @return 相差天数
		 * @throws ParseException
		 * @throws Exception 
		 */
		public static int daysBetween(Date minuend, Date meiosis) throws ParseException, Exception {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			minuend = sdf.parse(sdf.format(minuend));
			meiosis = sdf.parse(sdf.format(meiosis));
			Calendar cal = Calendar.getInstance();
			cal.setTime(minuend);
			long time1 = cal.getTimeInMillis();
			cal.setTime(meiosis);
			long time2 = cal.getTimeInMillis();
			long between_days = (time2 - time1) / (1000 * 3600 * 24);
			return Integer.parseInt(String.valueOf(between_days));
		}

		/**
		 * 字符串的日期格式的计算
		 */
		public static int daysBetween(String minuend, String meiosis) throws ParseException, Exception {
			minuend = minuend.substring(0, 4) + "-" + minuend.substring(4, 6) + "-" + minuend.substring(6, 8)
					+ minuend.substring(8, 9).replace("T", " ") + minuend.substring(9, 11) + ":"
					+ minuend.substring(11, 13) + ":" + minuend.substring(13, 15);
			meiosis = meiosis.substring(0, 4) + "-" + meiosis.substring(4, 6) + "-" + meiosis.substring(6, 8)
					+ meiosis.substring(8, 9).replace("T", " ") + meiosis.substring(9, 11) + ":"
					+ meiosis.substring(11, 13) + ":" + meiosis.substring(13, 15);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Calendar cal = Calendar.getInstance();
			cal.setTime(sdf.parse(minuend));
			long time1 = cal.getTimeInMillis();
			cal.setTime(sdf.parse(meiosis));
			long time2 = cal.getTimeInMillis();
			long time = time2 - time1;
			long between_days = (time) / (1000 * 3600 * 24);
			return Integer.parseInt(String.valueOf(between_days));
		}

		/**
		 * 18位String日期格式转换成Date后比较大小，返回最大的String日期
		 * */

		public static String dayMax(String adateTime, String time) throws ParseException , Exception{
			Date time_1 = null;
			Date time_2 = null;
			String max = "";
			adateTime = adateTime.substring(0, 4) + "-" + adateTime.substring(4, 6) + "-" + adateTime.substring(6, 8) + " "
					+ adateTime.substring(9, 11) + ":" + adateTime.substring(11, 13) + ":" + adateTime.substring(13, 15);
			time = time.substring(0, 4) + "-" + time.substring(4, 6) + "-" + time.substring(6, 8) + " "
					+ time.substring(9, 11) + ":" + time.substring(11, 13) + ":" + time.substring(13, 15);
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd'T'hhmmss");
			time_1 = format.parse(adateTime);
			time_2 = format.parse(time);
			boolean flag = time_1.before(time_2);
			if (flag) {
				max = sdf.format(time_2);
			} else {
				max = sdf.format(time_1);
			}
			return max;
		}

		/**
		 * @return 将String型存储的时间戳格式转换成DT15的String格式
		 * 
		 * */
		public static String timeStampturnDT15(String s) throws Exception {
			String res;
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
			long lt = new Long(s);
			Date date = new Date(lt);
			res = simpleDateFormat.format(date);
			return res;
		}

		/**
		 * @param String ,String
		 * @return 1,-1,0
		 * */
		public static int compare_date(String DATE1, String DATE2) {
	        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	        try {
	            Date dt1 = df.parse(DATE1);
	            Date dt2 = df.parse(DATE2);
	            if (dt1.getTime() > dt2.getTime()) {
	                return 1;
	            } else {               
	                return -1;
	            }
	        } catch (Exception exception) {
	            exception.printStackTrace();
	        }
	        return 0;
	    }
	
}
