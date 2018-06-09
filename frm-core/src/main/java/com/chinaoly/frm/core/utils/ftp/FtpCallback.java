package com.chinaoly.frm.core.utils.ftp;

/**
 * 异步 ftp操作回调
 *
 * @author jiangyi
 * @Date 2018.5.11
 */

public interface FtpCallback {
	public void run(boolean success, Exception e);
}
