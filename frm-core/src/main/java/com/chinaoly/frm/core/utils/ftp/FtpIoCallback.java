package com.chinaoly.frm.core.utils.ftp;

import java.io.InputStream;

/**
 * 异步 ftp io流操作回调
 *
 * @author jiangyi
 * @Date 2018.5.11
 */
public interface FtpIoCallback {
	public void run(boolean success, InputStream is, Exception e);
}
