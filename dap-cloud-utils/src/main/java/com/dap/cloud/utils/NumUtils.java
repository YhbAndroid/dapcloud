package com.dap.cloud.utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class NumUtils {
	
	
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	static DecimalFormat df = new DecimalFormat("00000");

	/**
	 * 生成年月日时分秒加四位随机数
	 * @return
	 */
	public static String getFlowNum(){
		//生成年月日时分秒
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String serialnumber = sdf.format(date);
		//返回流水号
		return serialnumber;
	}

	/**
	 * 订单号
	 * @return
	 */
	public static String getRandomNums(){
		String str = System.currentTimeMillis()+"";
		return "D"+str.substring(6, str.length()-1);
	}


	/**
	 * 订单号
	 * @return
	 */
	public static String getRandomNums(int count){
		StringBuffer sb = new StringBuffer();
		String str = "0123456789";
		Random r = new Random();
		for(int i=0;i<count;i++){
			int num = r.nextInt(str.length());
			sb.append(str.charAt(num));
			str = str.replace((str.charAt(num)+""), "");
		}
		return sb.toString();
	}

	/**
	 * 退款单号
	 * @return
	 */
	public static String getRefundNums(){
		String str = System.currentTimeMillis()+"";
		return "T"+str.substring(6, str.length()-1);
	}

	/**
	 * 获取六位流水号
	 * @return
	 */
	public static String getSexRandomNum(){
		String str = System.currentTimeMillis()+"";
		return str.substring(6, str.length()-1);
	}

	/**
	 * 获取四位流水号
	 * @return
	 */
	public static String getFourRandomNum(){
		String str = System.currentTimeMillis()+"";
		return str.substring(4, str.length()-1);
	}
	/**
	 * 功能：获取结算单号
	 * @return
	 * @throws Exception 
	 */
	public static String getSettleNum() throws Exception{
		String sql = "select seq_settle_num.nextval as num from dual";  //会员序列
		List<Map<Object,Object>> list = DBUtils.executeQuery(sql, false);
		String num = "";
		for (Map<Object, Object> map : list) {
			num = (String) map.get("NUM")+"";
		}
		DecimalFormat de = new DecimalFormat("0000");
		return "ZF"+sdf.format(new Date())+de.format(Integer.parseInt(num)); 
	}
	/**
	 * 功能：获取采购申请单号
	 * @return
	 * @throws Exception 
	 */
	public static String getApplyNum() throws Exception{
		String sql = "select seq_apply_num.nextval as num from dual";  //会员序列
		List<Map<Object,Object>> list = DBUtils.executeQuery(sql, false);
		String num = "";
		for (Map<Object, Object> map : list) {
			num = (String) map.get("NUM")+"";
		}
		DecimalFormat de = new DecimalFormat("0000");
		return "CG"+sdf.format(new Date())+de.format(Integer.parseInt(num)); 
	}
	/**
	 * 功能：获取采购执行单号
	 * @return
	 * @throws Exception 
	 */
	public static String getExecNum() throws Exception{
		String sql = "select seq_exec_num.nextval as num from dual";  //会员序列
		List<Map<Object,Object>> list = DBUtils.executeQuery(sql, false);
		String num = "";
		for (Map<Object, Object> map : list) {
			num = (String) map.get("NUM")+"";
		}
		DecimalFormat de = new DecimalFormat("0000");
		return "ZX"+sdf.format(new Date())+de.format(Integer.parseInt(num)); 
	}
	/**
	 * 功能：获取入库单号
	 * @return
	 * @throws Exception 
	 */
	public static String getIntakeNum() throws Exception{
		String sql = "select seq_intake_num.nextval as num from dual";  //会员序列
		List<Map<Object,Object>> list = DBUtils.executeQuery(sql, false);
		String num = "";
		for (Map<Object, Object> map : list) {
			num = (String) map.get("NUM")+"";
		}
		DecimalFormat de = new DecimalFormat("0000");
		return "IN"+sdf.format(new Date())+de.format(Integer.parseInt(num)); 
	}
	/**
	 * 功能：获取出库单号
	 * @return
	 * @throws Exception 
	 */
	public static String getOuttakeNum() throws Exception{
		String sql = "select seq_outtake_num.nextval as num from dual";  //会员序列
		List<Map<Object,Object>> list = DBUtils.executeQuery(sql, false);
		String num = "";
		for (Map<Object, Object> map : list) {
			num = (String) map.get("NUM")+"";
		}
		DecimalFormat de = new DecimalFormat("0000");
		return "OUT"+sdf.format(new Date())+de.format(Integer.parseInt(num)); 
	}
	/**
	 * 功能：获取盘点单号
	 * @return
	 * @throws Exception 
	 */
	public static String getStockingNum() throws Exception{
		String sql = "select seq_stocking_num.nextval as num from dual";  //会员序列
		List<Map<Object,Object>> list = DBUtils.executeQuery(sql, false);
		String num = "";
		for (Map<Object, Object> map : list) {
			num = (String) map.get("NUM")+"";
		}
		DecimalFormat de = new DecimalFormat("0000");
		return "PD"+sdf.format(new Date())+de.format(Integer.parseInt(num)); 
	}
	/**
	 * 功能：获取盈亏单号
	 * @return
	 * @throws Exception 
	 */
	public static String getProfitNum() throws Exception{
		String sql = "select seq_profit_num.nextval as num from dual";  //会员序列
		List<Map<Object,Object>> list = DBUtils.executeQuery(sql, false);
		String num = "";
		for (Map<Object, Object> map : list) {
			num = (String) map.get("NUM")+"";
		}
		DecimalFormat de = new DecimalFormat("0000");
		return "YK"+sdf.format(new Date())+de.format(Integer.parseInt(num)); 
	}
}
