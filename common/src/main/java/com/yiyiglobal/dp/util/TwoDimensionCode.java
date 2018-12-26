package com.yiyiglobal.dp.util;

import com.swetake.util.Qrcode;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * 生成二维码
 * @author huazai
 *
 */
public class TwoDimensionCode {

	private static Integer SIZE = 10;
	
	private static BufferedImage qRCodeCommon(String content, String imgType){
		BufferedImage bufImg = null;
		try {
			Qrcode qrcodeHandler = new Qrcode();
			qrcodeHandler.setQrcodeErrorCorrect('M');
			qrcodeHandler.setQrcodeEncodeMode('B');
			qrcodeHandler.setQrcodeVersion(SIZE);
			byte[] contentBytes = content.getBytes("utf-8");
			
			int imgSize = 67 + 12 * (SIZE - 1);
			bufImg = new BufferedImage(imgSize, imgSize, BufferedImage.TYPE_INT_RGB);
			Graphics2D gs = bufImg.createGraphics();
			
			gs.setBackground(Color.WHITE);
			gs.clearRect(0, 0, imgSize, imgSize);
			
			gs.setColor(Color.BLACK);
			
			int pixoff = 2;
			
			if(contentBytes.length > 0 && contentBytes.length < 800){
				boolean[][] codeOut = qrcodeHandler.calQrcode(contentBytes);
				for(int i=0; i< codeOut.length; i++){
					for(int j=0; j< codeOut.length; j++){
						if(codeOut[j][i]){
							gs.fillRect(j*3 + pixoff, i*3+pixoff, 3, 3);
						}
					}
				}
			}else{
				throw new Exception("QRCode content bytes length = " + contentBytes.length + " not in [0, 800]");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bufImg;
	}
	
	/**
	 * 返回二维码
	 * @param url
	 * @return
	 */
	public static String imageToBase64(String url) {
		BASE64Encoder encoder = new BASE64Encoder();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		BufferedImage buf = qRCodeCommon(url, "png");
		try {
			ImageIO.write(buf, "png", baos);
			byte[] bytes = baos.toByteArray();
			String result = encoder.encodeBuffer(bytes).trim();
			result = result.replaceAll("[\n\r]", "");
			return result;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
