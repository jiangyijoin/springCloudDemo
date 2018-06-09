package com.chinaoly.frm.common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 汉字进行拼音简化，全拼，简拼，缩写
 * @author jiangyi
 *
 */
public final class CnChar2PinyinUtil {
	/**
	 * 全拼 0x20
	 */
	public final static String F0X20 = "0x20";

	public final static String F0X10 = "0x10";

	public final static String F0X08 = "0x08";

	/**
	 * 模糊拼 0x04
	 */
	public final static String F0X04 = "0x04";

	public final static String F0X02 = "0x02";

	/**
	 * 简拼 0x01
	 */
	public final static String F0X01 = "0x01";

	//
	//
	private final static int I0X20 = 0x20;

	private final static int I0X10 = 0x10;

	private final static int I0X08 = 0x08;

	private final static int I0X04 = 0x04;

	private final static int I0X02 = 0x02;

	private final static int I0X01 = 0x01;

	private CnChar2PinyinUtil() {
	}

	/**
	 * 获取单个汉字的拼音组情况（自定义输出格式【参考HanyuPinyinOutputFormat】）
	 * 
	 * @param c
	 *            汉字
	 * @param choiceFlag
	 *            拼音输出内容（用8位二进制格式的整数表示，每一位表示相应的格式取舍，取值范围“0~63”。如取值63，其二进制数“00111111
	 *            ”表示 “精确全拼&全拼去翘&全拼去鼻&全拼去翘去鼻&精确短拼&短拼去翘”.）
	 * @return
	 */
	private static String[][] getSingPinyinGroup(char c, int choiceFlag) {
		String[][] pins = new String[0][];
		if (choiceFlag < 0 || choiceFlag > 63)
			return pins;
		HanyuPinyinOutputFormat hanYuPinyinOutputFormat = new HanyuPinyinOutputFormat();
		// 小写、拼音字母格式、无音标方式等
		hanYuPinyinOutputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		// 无声调
		hanYuPinyinOutputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		hanYuPinyinOutputFormat.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);
		try {
			// 获得每个汉字的多音字拼音组
			String[] mulititoneArray = delSameTune(PinyinHelper.toHanyuPinyinStringArray(c, hanYuPinyinOutputFormat));
			// 以下开始组拼汉字串的拼音情况
			// 精确全拼
			String allPinPricise = null;
			// 全拼去翘
			String allPinNoRetroflex = null;
			// 全拼去鼻
			String allPinNoNasal = null;
			// 全拼去翘去鼻
			String allPinNoRetroflexNoNasal = null;
			// 精确短拼
			String shortPinPricise = null;
			// 短拼去翘
			String shortPinNoRetroflex = null;
			List<String[]> pinyinCompList = new ArrayList<String[]>();
			for (int j = 0; j < mulititoneArray.length; j++) {
				// 精确全拼
				allPinPricise = mulititoneArray[j];
				// 是否翘舌音、是否后鼻音(排除ang)
				int len = allPinPricise.length();
				boolean isRetroflex = allPinPricise.indexOf("h") == 1;
				boolean isNasal = allPinPricise.endsWith("g") && len >= 3 && allPinPricise.charAt(allPinPricise.length() - 3) != 'a';
				// 方案一:
				if (isNasal) {
					// 全拼去鼻
					allPinNoNasal = allPinPricise.substring(0, len - 1);
				} else {
					// 全拼去鼻
					allPinNoNasal = allPinPricise.substring(0, len);
				}
				if (isRetroflex) {
					// 全拼去翘
					allPinNoRetroflex = allPinPricise.substring(0, 1) + allPinPricise.substring(2, len);
					// 全拼去翘去鼻
					allPinNoRetroflexNoNasal = allPinNoNasal.substring(0, 1) + allPinNoNasal.substring(2, allPinNoNasal.length());
					// 精确短拼
					shortPinPricise = allPinPricise.substring(0, 2);
					// 短拼去翘
					shortPinNoRetroflex = allPinPricise.substring(0, 1);
				} else {
					// 全拼去翘
					allPinNoRetroflex = allPinPricise;
					// 全拼去翘去鼻
					allPinNoRetroflexNoNasal = allPinNoNasal;
					// 精确短拼
					shortPinPricise = allPinPricise.substring(0, 1);
					// 短拼去翘
					shortPinNoRetroflex = allPinPricise.substring(0, 1);
				}
				// 精确全拼
				if (((choiceFlag & I0X20) == 32))
					if (allPinPricise != null)
						pinyinCompList.add(new String[] { allPinPricise, F0X20 });
				// 全拼去翘
				if (((choiceFlag & I0X10) == 16))
					if (allPinNoRetroflex != null)
						pinyinCompList.add(new String[] { allPinNoRetroflex, F0X10 });
				// else
				// pinyinCompList.add(new String[] { allPinPricise, F0X10 });
				// 全拼去鼻
				if (((choiceFlag & I0X08) == 8))
					if (allPinNoNasal != null)
						pinyinCompList.add(new String[] { allPinNoNasal, F0X08 });
				// else
				// pinyinCompList.add(new String[] { allPinPricise, F0X08 });
				// 全拼去翘去鼻
				if (((choiceFlag & I0X04) == 4))
					if (allPinNoRetroflexNoNasal != null)
						pinyinCompList.add(new String[] { allPinNoRetroflexNoNasal, F0X04 });
				// else
				// pinyinCompList.add(new String[] { allPinPricise, F0X04 });
				// 精确短拼
				if (((choiceFlag & I0X02) == 2))
					if (shortPinPricise != null)
						pinyinCompList.add(new String[] { shortPinPricise, F0X02 });
				// else
				// pinyinCompList.add(new String[] { allPinPricise, F0X02 });
				// 短拼去翘
				// 每个汉字只取第一个拼音
				if (j == 0) {
					if (((choiceFlag & I0X01) == 1))
						if (shortPinNoRetroflex != null)
							pinyinCompList.add(new String[] { shortPinNoRetroflex, F0X01 });
				}
				// else
				// pinyinCompList.add(new String[] { allPinPricise, F0X01 });
			}
			int len = pinyinCompList.size();
			pins = new String[len][];
			for (int k = 0; k < len; k++) {
				pins[k] = pinyinCompList.get(k);
			}

		} catch (BadHanyuPinyinOutputFormatCombination e) {
			e.printStackTrace();
		}
		return pins;
	}

	/**
	 * 对于多音字因声调不同而造成的重复拼音问题，必须在拼音组唯一化
	 * 
	 * @param pinyinS
	 * @return
	 */
	private static String[] delSameTune(String[] pinyinS) {
		int cnt = 0;
		for (int i = 0; i < pinyinS.length - 1; i++) {
			String tmpSi = pinyinS[i];
			if (tmpSi == null)
				continue;
			for (int j = i + 1; j < pinyinS.length; j++) {
				String tmpSj = pinyinS[j];
				if (tmpSi.equals(tmpSj)) {
					pinyinS[j] = null;
					cnt++;
				}
			}
		}
		String[] s = new String[pinyinS.length - cnt];
		cnt = 0;
		for (int i = 0; i < pinyinS.length; i++) {
			if (pinyinS[i] != null)
				s[cnt++] = pinyinS[i];
		}
		return s;
	}

	/**
	 * /** 获取拼音集合（以字符串方式返回所有的排列情况，分隔符为“|”
	 * 
	 * @param src
	 * @param choiceFlag
	 * @return
	 */
	private static String getPinyinStr(String src, int choiceFlag) {
		StringBuffer sb = new StringBuffer();
		String[][] s = getPinyinArray(src, choiceFlag);
		for (int i = 0; i < s.length - 1; i++) {
			sb.append(s[i][0]).append(" | ");
		}
		if (s.length >= 1)
			sb.append(s[s.length - 1][0]);
		return sb.toString();
	}

	/**
	 * /** 获取拼音集合（以字符串方式返回所有的排列情况，分隔符为“|”
	 * 
	 * @param src
	 * @param choiceFlag
	 * @return
	 */
	private static String getPinyinStr(String src) {
		return getPinyinStr(src, 37);
	}

	/**
	 * 获取拼音集合（以数组方式返回所有的排列情况）
	 * 
	 * @param src
	 * @param choiceFlag
	 * @return
	 */
	private static String[][] getPinyinArray(String src, int choiceFlag) {
		String[][] rsltPinyinArray = new String[0][0];
		if (src != null && !src.trim().equalsIgnoreCase("")) {
			char[] srcChar;
			srcChar = src.toCharArray();
			int charLen = srcChar.length;
			// 汉语拼音输出格式类
			HanyuPinyinOutputFormat hanYuPinyinOutputFormat = new HanyuPinyinOutputFormat();

			// 小写、拼音字母格式、无音标方式等
			hanYuPinyinOutputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
			// 无声调
			hanYuPinyinOutputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
			hanYuPinyinOutputFormat.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);
			// 按照顺序装载各汉字拼音，对非汉字字符串则原文载入（注意是两个汉字之间的连续串）
			List<String[][]> pinyinList = new ArrayList<String[][]>();
			// 用来临时缓存非汉字串
			StringBuffer tmpSb = new StringBuffer();
			//
			for (int i = 0; i < charLen; i++) {
				char c = srcChar[i];
				// 是中文
				if (String.valueOf(c).matches("[\\u4E00-\\u9FA5]+")) {
					if (tmpSb.length() > 0) {
						pinyinList.add(new String[][] { { tmpSb.toString(), "0x00" } });
						// 注意清除临时缓存
						tmpSb.delete(0, tmpSb.length());
					}
					String[][] simplePins = getSingPinyinGroup(c, choiceFlag);
					// 多音字的多个拼音合并在一起。比如“家”：格式如“全拼[ jia gu jie ]、短拼[ j g j ]"）
					String[][] complexPins = getComplexPinyinGroup(simplePins, choiceFlag);
					pinyinList.add(complexPins);
					// pinyinList.add(simplePins);
				} else {
					// 其它字符直接汇合
					tmpSb.append(c);
					// 到了字符串末尾，不捡起就没机会了
					if (i + 1 == charLen && tmpSb.length() > 0) {
						pinyinList.add(new String[][] { { tmpSb.toString(), "0x00" } });
					}
				}
			}
			rsltPinyinArray = doArrange(pinyinList);
		}
		return rsltPinyinArray;

	}

	/**
	 * 将同类拼写的多音字拼音进行合并。格式如"家"[ jiagujie ]、[ j g j ]。下列参数将举例说明
	 * 
	 * @param simplePins
	 *            举例：{{"jia",F0X20}{"j",F0X01}{"gu",F0X20}{"g",F0X01}{"jie",F0X20
	 *            }{"j",F0X01}}
	 * @param choiceFlag
	 *            举例：00100001--->33
	 * @return {{"[ jiagujie ]",F0X20}{"[ j g j ]",F0X01}}
	 */
	private static String[][] getComplexPinyinGroup(String[][] simplePins, int choiceFlag) {
		int len = 0;
		// 根据choiceFlag计算一共有几种拼写要求，以便将同类拼写的多音字拼音进行合并。格式如"家"[ jia gu jie ]、[ j g
		// j ]
		// 精确全拼
		if (((choiceFlag & I0X20) == 32))
			len++;
		// 全拼去翘
		if (((choiceFlag & I0X10) == 16))
			len++;
		// 全拼去鼻
		if (((choiceFlag & I0X08) == 8))
			len++;
		// 全拼去翘去鼻
		if (((choiceFlag & I0X04) == 4))
			len++;
		// 精确短拼
		if (((choiceFlag & I0X02) == 2))
			len++;
		// 短拼去翘
		if (((choiceFlag & I0X01) == 1))
			len++;
		//
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < simplePins.length; i++) {
			String s = map.get(simplePins[i][1]);
			if (s == null) {
				s = simplePins[i][0];
			} else {
				if ((simplePins[i][1].equalsIgnoreCase(F0X01)))
					s += " " + simplePins[i][0];
				else
					s += simplePins[i][0];
			}
			map.put(simplePins[i][1], s);
		}
		Iterator keyItr = map.keySet().iterator();
		String[][] tmp = new String[map.size()][];
		int i = 0;
		while (keyItr.hasNext()) {
			String key = (String) keyItr.next();
			String val = map.get(key);
			// if (key.equals(F0X01))
			// val = "[ " + val + " ]";
			// else
			val = " " + val;
			tmp[i] = new String[2];
			tmp[i][0] = val;
			tmp[i][1] = key;
			i++;
		}
		return tmp;

	}

	/**
	 * 获取拼音集合（以数组方式返回所有的排列情况）
	 * 
	 * @param src
	 * @return
	 */
	private static String[][] getPinyinArray(String srcg) {
		return getPinyinArray(srcg, 37);

	}

	/**
	 * 编排(排列组合)
	 * 
	 * @param pinyinList
	 *            【List<String[][]>二维数组解释： 1、各类拼音；
	 *            2、拼音习惯（0x00、I0X20、I0X10、I0X08、I0X04、I0X02、I0X01），
	 *            分别表示（非拼音、精确全拼、全拼去翘、全拼去鼻、全拼去翘去鼻、精确短拼、短拼去翘）】
	 * @param sameHabit
	 *            是否统一习惯
	 * @return
	 */
	private static String[][] doArrange(List<String[][]> pinyinList) {
		int size = pinyinList.size();
		String[] rsltS = new String[0];
		if (size >= 2) {
			for (int i = 0; i < size - 1; i++) {

				String[][] s1 = pinyinList.remove(0);
				String[][] s2 = pinyinList.remove(0);
				List<String[]> tmpL = arrangeChg_1(s1, s2);
				String[][] swapS = new String[tmpL.size()][2];
				for (int k = 0; k < tmpL.size(); k++) {
					swapS[k][0] = tmpL.get(k)[0];
					swapS[k][1] = tmpL.get(k)[1];
				}
				pinyinList.add(0, swapS);
			}
		}

		String[][] rsltSS = pinyinList.get(0);
		return rsltSS;
	}

	/**
	 * 编排。取精确全拼0x20、简化全拼0x04、简化声母0x01 三种情况，进行习惯相通排列
	 * 
	 * @param s1
	 * @param s2
	 * @return
	 */
	private static List<String[]> arrangeChg_1(String[][] s1, String[][] s2) {
		List<String[]> tmpL = new ArrayList<String[]>();
		for (int j = 0; j < s1.length; j++) {
			for (int k = 0; k < s2.length; k++) {
				String[] tmpS = new String[2];

				if (s1[j][1].equals("0x00") || s2[k][1].equals("0x00") || s1[j][1].equals(s2[k][1])) {
					if (s1[j][1].equals("0x00") && !s2[k][1].equals("0x00")) {
						if (s2[k][1].equals(F0X20) || s2[k][1].equals(F0X04) || s2[k][1].equals(F0X01)) {
							tmpS[0] = s1[j][0] + " " + s2[k][0];
							tmpS[1] = s2[k][1];
							tmpL.add(tmpS);
						}
					} else if (!s1[j][1].equals("0x00") && s2[k][1].equals("0x00")) {
						if (s1[j][1].equals(F0X20) || s1[j][1].equals(F0X04) || s1[j][1].equals(F0X01)) {
							tmpS[0] = s1[j][0] + " " + s2[k][0];
							tmpS[1] = s1[j][1];
							tmpL.add(tmpS);
						}
					} else {
						if (s2[k][1].equals(F0X20) || s2[k][1].equals(F0X04) || s2[k][1].equals(F0X01) || s2[k][1].equals("0x00")) {
							tmpS[0] = s1[j][0] + "" + s2[k][0];
							tmpS[1] = s2[k][1];
							tmpL.add(tmpS);
						}
					}

				}
			}
		}
		return tmpL;
	}

	/**
	 * 获取拼音集合（以数组方式返回所有的排列情况）
	 * 
	 * @param src
	 * @return 结果以名值对方式反馈拼音组拼情况【key（I0X20：全拼；I0X04：模糊拼；I0X01：简拼）;
	 *         value（对应各拼音组拼）】
	 */
	public static Map<String, String> getPinyinMap(String srcg) {
		String[][] s = getPinyinArray(srcg, 37);
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < s.length; i++) {
			String key = s[i][1];
			String val = s[i][0];
			map.put(key, val);
		}
		return map;
	}

	public static void main(String[] args) {
		// String str = "载可";
		// String str = "成";
		// String str = "陈";
		// String str = "岑";
		String str = "aaaaa普通dsa用户";
		//String str = "呱呱坠地";
		Map<String, String> map = CnChar2PinyinUtil.getPinyinMap(str);
		// 精确全拼0x20
		String s0x20 = map.get(CnChar2PinyinUtil.F0X20);
		System.out.println(s0x20);
		// 简化全拼0x04
		String s0x04 = map.get(CnChar2PinyinUtil.F0X04);
		System.out.println(s0x04);
		// 简化声母0x01
		String s0x01 = map.get(CnChar2PinyinUtil.F0X01);
		System.out.println(s0x01);

		// 生成拼音组的字符串格式
		// 或者调用getPinyinMap生成拼音组的map格式。详见方法说明。

	}
}
