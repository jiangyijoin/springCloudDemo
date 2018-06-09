package com.chinaoly.frm.login.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chinaoly.frm.core.web.BaseController;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;


/** 
* @author : shul
* @date：2018年6月1日 下午15:29:15 
* @parameter   
* @return  
*/
@RestController
public class CheckImgController{
	private static final long serialVersionUID = 1L;
   

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		         // 禁止缓存
				 response.setHeader("Cache-Control", "no-cache");
				 response.setHeader("Pragma", "no-cache");
				 response.setDateHeader("Expires", -1);

				int width = 120;
				int height = 30;

				// 步骤一 绘制一张内存中图片
				BufferedImage bufferedImage = new BufferedImage(width, height,
						BufferedImage.TYPE_INT_RGB);

				// 步骤二 图片绘制背景颜色 ---通过绘图对象
				Graphics graphics = bufferedImage.getGraphics();// 得到画图对象 --- 画笔
				// 绘制任何图形之前 都必须指定一个颜色
				graphics.setColor(getRandColor(200, 250));
				graphics.fillRect(0, 0, width, height);

				// 步骤三 绘制边框
				graphics.setColor(Color.WHITE);
				graphics.drawRect(0, 0, width - 1, height - 1);

				// 步骤四 四个随机数字
				Graphics2D graphics2d = (Graphics2D) graphics;
				// 设置输出字体
				graphics2d.setFont(new Font("Times New Roman", Font.BOLD, 18));


				String word = getRandomCharAndNumr();// 获得组合的数字和英文

				Random random = new Random();// 生成随机数
				// 定义x坐标
				int x = 10;
				for (int i = 0; i < word.length(); i++) {
					// 随机颜色
					graphics2d.setColor(new Color(20 + random.nextInt(110), 20 + random
							.nextInt(110), 20 + random.nextInt(110)));
					// 旋转 -30 --- 30度
					int jiaodu = random.nextInt(60) - 30;
					// 换算弧度
					double theta = jiaodu * Math.PI / 180;

					// 获得字母数字
					char c = word.charAt(i);

					// 将c 输出到图片
					graphics2d.rotate(theta, x, 20);
					graphics2d.drawString(String.valueOf(c), x, 20);
					graphics2d.rotate(-theta, x, 20);
					x += 30;
				}
//				System.out.println("word--"+word);
				// 将验证码内容保存session
				request.getSession().setAttribute("checkcode_session", word);

				// 步骤五 绘制干扰线
				graphics.setColor(getRandColor(160, 200));
				int x1;
				int x2;
				int y1;
				int y2;
				for (int i = 0; i < 30; i++) {
					x1 = random.nextInt(width);
					x2 = random.nextInt(12);
					y1 = random.nextInt(height);
					y2 = random.nextInt(12);
					graphics.drawLine(x1, y1, x1 + x2, x2 + y2);
				}

				// 将上面图片输出到浏览器 ImageIO
				graphics.dispose();// 释放资源
				ImageIO.write(bufferedImage, "jpg", response.getOutputStream());


	}

	@ApiOperation(value = "生成验证码",httpMethod = "POST", notes = "check")
    @PostMapping("/login/check")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	

	/**
	 * 取其某一范围的color
	 *
	 * @param fc int 范围参数1
	 * @param bc int 范围参数2
	 * @return Color
	 */
	private Color getRandColor(int fc, int bc) {
		// 取其随机颜色
		Random random = new Random();
		if (fc > 255) {
			fc = 255;
		}
		if (bc > 255) {
			bc = 255;
		}
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}
	
	/**
	 * 获取随机字母数字组合
	 *
	 * @param
	 * @return str 随机数字和字母
	 */
	private String getRandomCharAndNumr() {
		String str = "";
		Random rand = new Random();
		for(int i=0;i<4;i++){
			int num = rand.nextInt(2);
			switch (num){
				case 0:
					char c1 = (char)(rand.nextInt(26)+'a');//生成随机小写字母
					str += c1;
					break;
				case 1:
					str +=rand.nextInt(10);//生成随机数字
			}
		}
		System.out.println("生成的4个随机验证码是:"+str);
		return str;
	}


}
