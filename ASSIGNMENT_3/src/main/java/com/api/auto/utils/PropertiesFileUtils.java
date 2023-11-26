package com.api.auto.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesFileUtils {
	// tạo 1 dường dẫn properties file trong folder configuration
	private static String CONFIG_PATH = "./configuration/configs.properties";
	private static String TOKEN_PATH = "./configuration/token.properties";

	// 2.Lấy ra 1 giá trị bất kỳ property bất kỳ theo key;
	public static String getProperty(String key) {
		Properties properties = new Properties();
		String value = null;
		FileInputStream fileInputStream = null;

		// Bắt expection
		try {
			fileInputStream = new FileInputStream(CONFIG_PATH);
			properties.load(fileInputStream);
			value = properties.getProperty(key);
			return value;
		} catch (Exception ex) {
			System.out.println("Xảy ra lỗi đọc giá trị của : " + key);
			ex.printStackTrace();
		} finally {
			// luôn luôn nhảy vào đây dù có xảy ra exception hay k
			// thực hiện đóng luồng đọc
			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
		return value;

	}

	// 3.save token vào file token.properties với key là “token”
	public void saveToken(String token) {
		Properties properties = new Properties();
		FileOutputStream fileOutputStream = null;
		try {
			// khi tạo 1 giá trị cho đối tượng FileOutpútStream
			fileOutputStream = new FileOutputStream(TOKEN_PATH);
			// Load properties file hiện tại và thực hiện mapping value
			// với key tương ứng
			properties.setProperty("token", token);
			// Lưu key và value vào properties file
			properties.store(fileOutputStream, "Set new value in properties");
			System.out.println("Set new value in file properties success.");
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			// luôn luôn nhảy vào đây dù có xảy ra exception hay k
			// thực hiện đóng luồng đọc
			if (fileOutputStream != null) {
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// 4 Đọc giá trị từ file token.properties
	public static String getToken(String token) {
		Properties properties = new Properties();
		String value = null;
		FileInputStream fileInputStream = null;

		// Bắt expection
		try {
			fileInputStream = new FileInputStream(TOKEN_PATH);
			properties.load(fileInputStream);
			value = properties.getProperty(token);
			return value;
		} catch (Exception ex) {
			System.out.println("Xảy ra lỗi đọc giá trị của token : ");
			ex.printStackTrace();
		} finally {
			// luôn luôn nhảy vào đây dù có xảy ra exception hay k
			// thực hiện đóng luồng đọc
			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
		return value;
	}
}
