package com.chinaoly.frm.common.utils;

import com.jcraft.jsch.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.util.Properties;
import java.util.Vector;

/**
 * sftp操作工具
 * ClassName: SftpHelper
 * @author jiangyi
 * @date 2018.5.29
 */

public class SftpHelper {
	private final Log logger = LogFactory.getLog(getClass());

	private String userName; // SFTP 登录用户名

	private String password; // SFTP 登录密码

	private String ip; // SFTP 服务器地址IP地址

	private int port; // SFTP 端口

	private String remoteDir; // 远程文件目录

	private Session sshSession = null;

	public SftpHelper(String userName, String password, String ip, int port,
                      String remoteDir) {
		this.userName = userName;
		this.password = password;
		this.ip = ip;
		this.port = port;
		this.remoteDir = remoteDir;
	}

	/**
	 * 连接sftp服务器
	 *
	 * @return
	 */
	public ChannelSftp connectServer() {
		ChannelSftp sftp = null;
		try {
			JSch jsch = new JSch();
			// jsch.getSession(userName, ip, port);
			logger.info("Session:" + sshSession);
			if (sshSession == null) {
				sshSession = jsch.getSession(userName, ip, port);
				logger.info("Session created.");
				sshSession.setPassword(password);
				Properties sshConfig = new Properties();
				sshConfig.put("StrictHostKeyChecking", "no");
				sshSession.setConfig(sshConfig);
				sshSession.connect();
			}
			logger.info("Session connected.");
			logger.info("Opening Channel.");
			Channel channel = sshSession.openChannel("sftp");
			channel.connect();
			sftp = (ChannelSftp) channel;
			logger.info("Connected to " + ip + ".");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sftp;
	}

	/**
	 * 上传文件
	 *
	 *            上传的目录
	 * @param filename
	 *            文件名
	 * @param uploadFile
	 *            要上传的文件
	 * @param sftp
	 */
	public void upload(String filename, String uploadFile, ChannelSftp sftp) {
		try {
			System.out.println(remoteDir);
			sftp.cd(remoteDir);
			File file = new File(uploadFile);
			sftp.put(new FileInputStream(file), filename);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 上传文件
	 *
	 * @param filename
	 *            上传文件名称
	 * @param ins
	 *            文件流
	 * @param sftp
	 *            sftp对象
	 */
	public void upload(String filename, InputStream ins, ChannelSftp sftp) {
		try {
			sftp.cd(remoteDir);
			sftp.put(ins, filename);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 下载文件
	 *
	 * @param downloadFile
	 *            下载的文件
	 * @param saveFile
	 *            存在本地的路径
	 * @param sftp
	 */
	public void download(String downloadFile, String saveFile, ChannelSftp sftp) {
		try {
			sftp.cd(remoteDir);
			File file = new File(saveFile);
			sftp.get(downloadFile, new FileOutputStream(file));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public InputStream downFile(String downloadFile, ChannelSftp sftp)
			throws IOException {
		try {
			sftp.cd(remoteDir);
			return sftp.get(downloadFile);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IOException(e.getMessage());
		}
	}

	/**
	 * 删除文件
	 *
	 * @param deleteFile
	 *            要删除的文件
	 * @param sftp
	 */
	public void delete(String deleteFile, ChannelSftp sftp) {
		try {
			sftp.cd(remoteDir);
			sftp.rm(deleteFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 列出目录下的文件
	 *
	 * @param directory
	 *            要列出的目录
	 * @param sftp
	 * @return
	 * @throws SftpException
	 */
	public Vector listFiles(String directory, ChannelSftp sftp)
			throws SftpException {
		return sftp.ls(directory);
	}

	public static void main(String[] args) {
		String host = "10.218.209.104";
		int port = 22;
		String username = "ecis";
		String password = "mk)1aW4d";
		String directory = "/home/ecis/ecis_advice";
		String uploadFile = "E:\\test.jpg";
		String downloadFile = "honghu.jpg";
		String saveFile = "D:\\download1.jpg";
		String deleteFile = "delete.txt";
		SftpHelper sf = new SftpHelper(username, password, host, port,
				directory);
		ChannelSftp sftp = sf.connectServer();
		sf.upload(downloadFile, uploadFile, sftp);
		// sf.download(downloadFile, saveFile, sftp);
		// sf.delete(downloadFile, sftp);
		// try {
		// sftp.cd(directory);
		// sftp.mkdir("ss");
		System.out.println("finished");
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		sftp.quit();
		sftp.exit();
		sftp.disconnect();
	}
}
