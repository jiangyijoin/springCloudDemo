package com.chinaoly.frm.core.utils.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.io.CopyStreamEvent;
import org.apache.commons.net.io.Util;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

/**
 * FTP操作工具，支持ftp以及sftp
 *
 * @author jiangyi
 * @Date 2018.5.11
 */
public class FtpUtil implements Cloneable {
	private final Log logger = LogFactory.getLog(getClass());

	public static enum FTP_TYPE {
		FTP, SFTP;
	};

	private FTP_TYPE type;

	private String username; // FTP 登录用户名

	private String password; // FTP 登录密码

	private String ip; // FTP 服务器地址IP地址

	private int port; // FTP 端口

	private String remoteDir = "./"; // 远程文件目录

	private FTPClient ftpClient = null; // FTP 客户端代理

	private ChannelSftp sftp = null;// sftp操作

	private boolean async = false;// 是否异步

	private FtpUtil asyncUtil = null;// 异步实际操作对象

	public FtpUtil(FTP_TYPE type, String username, String password, String ip,
			int port) {
		this.type = type;
		this.username = username;
		this.password = password;
		this.ip = ip;
		this.port = port;
	}

	public FtpUtil(FTP_TYPE type, String username, String password, String ip,
			int port, boolean async, String remoteDir) {
		this.type = type;
		this.username = username;
		this.password = password;
		this.ip = ip;
		this.port = port;
		this.remoteDir = remoteDir;
		setAsync(async);
	}

	public void setAsync(boolean async) {
		this.async = async;
		if (async && this.asyncUtil == null) {
			try {
				asyncUtil = (FtpUtil) this.clone();
				asyncUtil.setAsync(false);
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		}
	}

	private void connectSftp() {
		try {
			if (sftp == null) {
				JSch jsch = new JSch();
				Session sshSession = null;
				if (sshSession == null) {
					sshSession = jsch.getSession(username, ip, port);
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
			} else {
				if (sftp.isConnected()) {
					sftp.connect();
				}
			}
			logger.info("Connected to " + ip + ".");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void close() {
		if (this.type == FTP_TYPE.FTP) {
			if (ftpClient != null) {
				try {
					ftpClient.disconnect();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			if (sftp != null) {
				try {
					sftp.getSession().disconnect();
				} catch (JSchException e) {
					e.printStackTrace();
				}
				sftp.exit();
			}
		}
	}

	private void connectFtp() {
		int reply;
		try {
			if (ftpClient == null) {
				ftpClient = new FTPClient();
			}
			ftpClient.setControlEncoding("UTF-8"); // 文件名乱码,默认ISO8859-1，不支持中文
			ftpClient.setDefaultPort(port);
			ftpClient.connect(ip);
			ftpClient.login(username, password);
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			logger.info(ftpClient.getReplyString());
			reply = ftpClient.getReplyCode();
			ftpClient.setDataTimeout(120000);

			if (!FTPReply.isPositiveCompletion(reply)) {
				ftpClient.disconnect();
				logger.error("FTP server refused connection.");
			}
			logger.info("Connected to " + ip + ".");
		} catch (SocketException e) {
			e.printStackTrace();
			logger.error("登录ftp服务器 " + ip + " 失败,连接超时！");
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("登录ftp服务器 " + ip + " 失败，FTP服务器无法打开！");
		}
		logger.info("登陆ftp服务器" + ip + "成功");
	}

	public boolean upload(File f, String fileName, String remoteDir,
			FtpCallback callback) throws FileNotFoundException {
		return upload(new FileInputStream(f), fileName, remoteDir, callback);
	}

	public boolean upload(File f) throws FileNotFoundException {
		return upload(new FileInputStream(f), f.getName(), this.remoteDir, null);
	}

	public boolean upload(File f, String remoteDir, FtpCallback callback)
			throws FileNotFoundException {
		return upload(new FileInputStream(f), f.getName(), remoteDir, callback);
	}

	public boolean upload(InputStream is, String fileName, FtpCallback callback) {
		return upload(is, fileName, this.remoteDir, callback);
	}

	public boolean upload(final InputStream is, final String fileName,
			final String remoteDir, final FtpCallback callback) {
		if (this.async) {
			asyncRun(new Runnable() {
				@Override
				public void run() {
					asyncUtil.upload(is, fileName, remoteDir, callback);
				}
			});
			return true;
		} else {
			boolean flag = false;
			Exception _e = null;
			logger.info("----------- FILE UPLOAD START -------------");
			logger.info("directory: " + remoteDir);
			logger.info("fileName: " + fileName);
			if (type == FTP_TYPE.FTP) {
				connectFtp();
				ftpClient.enterLocalPassiveMode();
				try {
					ftpClient.changeWorkingDirectory(remoteDir);
					flag = ftpClient.storeFile(fileName, is);
					flag = true;
					logger.info("Upload complete!");
				} catch (IOException e) {
					e.printStackTrace();
					_e = e;
				} finally {
					try {
						close();
						is.close();
					} catch (IOException e) {
						e.printStackTrace();
						_e = e;
					}
				}
			} else {
				connectSftp();
				try {
					sftp.cd(remoteDir);
					sftp.put(is, fileName);
					flag = true;
					logger.info("Upload complete!");
				} catch (SftpException e) {
					e.printStackTrace();
					_e = e;
				} finally {
					close();
					try {
						is.close();
					} catch (IOException e) {
						e.printStackTrace();
						_e = e;
					}
				}
			}
			logger.info("----------- FILE UPLOAD END -------------");
			if (callback != null) {
				callback.run(flag, _e);
			}
			return flag;
		}
	}

	public void download(String fileName, FtpIoCallback callback) {
		download(fileName, this.remoteDir, callback);
	}

	public void download(String fileName, String remoteDir,
			FtpIoCallback callback) {
		logger.info("----------- FILE DOWNLOAD START -------------");
		logger.info("directory: " + remoteDir);
		logger.info("fileName: " + fileName);
		InputStream is = null;
		Exception _e = null;
		if (this.type == FTP_TYPE.FTP) {
			connectFtp();
			ftpClient.enterLocalPassiveMode();
			try {
				if (ftpClient.changeWorkingDirectory(remoteDir)) {
					
					is = ftpClient.retrieveFileStream(fileName);
				} else {
					logger.error("切换文件夹失败!");
				}
			} catch (IOException e) {
				e.printStackTrace();
				_e = e;
			} finally {
			}
		} else {
			connectSftp();
			try {
				sftp.cd(remoteDir);
				is = sftp.get(fileName);
			} catch (SftpException e) {
				e.printStackTrace();
				_e = e;
			} finally {
			}
		}
		logger.info("----------- FILE DOWNLOAD END -------------");
		if (callback != null) {
			callback.run(is != null, is, _e);
		}
		close();
		try {
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void download(String fileName, File saveFile) {
		download(fileName, saveFile, this.remoteDir, null);
	}

	public void download(String fileName, File saveFile, FtpCallback callback) {
		download(fileName, saveFile, this.remoteDir, callback);
	}

	public void download(final String fileName, final File saveFile,
			final String remoteDir, final FtpCallback callback) {
		if (this.async) {
			asyncRun(new Runnable() {
				@Override
				public void run() {
					asyncUtil.download(fileName, saveFile, remoteDir, callback);
				}
			});
		} else {
			download(fileName, remoteDir, new FtpIoCallback() {
				@Override
				public void run(boolean success, InputStream is, Exception _e) {
					boolean flag = false;
					if (success && is != null) {
						if (!saveFile.exists()) {
							try {
								saveFile.createNewFile();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
						try {
							OutputStream os = new FileOutputStream(saveFile);
							Util.copyStream(is, os, 1024,
									CopyStreamEvent.UNKNOWN_STREAM_SIZE, null,
									false);
							os.close();
							flag = true;
						} catch (FileNotFoundException e) {
							e.printStackTrace();
							_e = e;
						} catch (IOException e) {
							e.printStackTrace();
							_e = e;
						} finally {
							try {
								close();
								is.close();
							} catch (IOException e) {
								e.printStackTrace();
								_e = e;
							}
						}
					}
					if (callback != null) {
						callback.run(flag, _e);
					}
				}
			});

		}
	}

	public boolean delete(String fileName) {
		return delete(fileName, this.remoteDir, null);
	}

	public boolean delete(String fileName, FtpCallback callback) {
		return delete(fileName, this.remoteDir, callback);
	}

	public boolean delete(final String fileName, final String remoteDir,
			final FtpCallback callback) {
		if (this.async) {
			asyncRun(new Runnable() {
				@Override
				public void run() {
					asyncUtil.delete(fileName, remoteDir, callback);
				}
			});
			return true;
		} else {
			boolean flag = false;
			Exception _e = null;
			if (this.type == FTP_TYPE.FTP) {
				connectFtp();
				try {
					ftpClient.changeWorkingDirectory(remoteDir);
					flag = ftpClient.deleteFile(fileName);
				} catch (IOException e) {
					e.printStackTrace();
					_e = e;
				} finally {
					close();
				}
			} else {
				connectSftp();
				try {
					sftp.cd(remoteDir);
					sftp.rm(fileName);
				} catch (SftpException e) {
					e.printStackTrace();
					_e = e;
				} finally {
					close();
				}
			}
			if (callback != null) {
				callback.run(flag, _e);
			}
			return flag;
		}
	}

	public boolean mkdir(String dirName) {
		return mkdir(dirName, remoteDir, null);
	}

	public boolean mkdir(String dirName, FtpCallback callback) {
		return mkdir(dirName, remoteDir, callback);
	}

	public boolean mkdir(final String dirName, final String remoteDir,
			final FtpCallback callback) {
		if (this.async) {
			asyncRun(new Runnable() {
				@Override
				public void run() {
					asyncUtil.mkdir(dirName, remoteDir, callback);
				}
			});
			return true;
		} else {
			boolean flag = false;
			Exception _e = null;
			if (this.type == FTP_TYPE.FTP) {
				connectFtp();
				try {
					ftpClient.changeWorkingDirectory(remoteDir);
					ftpClient.makeDirectory(dirName);
				} catch (IOException e) {
					e.printStackTrace();
					_e = e;
				} finally {
					close();
				}
			} else {
				connectSftp();
				try {
					sftp.cd(remoteDir);
					sftp.mkdir(dirName);
				} catch (SftpException e) {
					e.printStackTrace();
					_e = e;
				} finally {
					close();
				}
			}
			if (callback != null) {
				callback.run(flag, _e);
			}
			return flag;
		}
	}

	/**
	 * 异步执行
	 * 
	 * @param target
	 */
	public void asyncRun(Runnable target) {
		Thread t = new Thread(target);
		t.start();
	}

}
