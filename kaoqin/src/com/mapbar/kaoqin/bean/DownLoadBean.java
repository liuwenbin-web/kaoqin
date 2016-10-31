package com.mapbar.kaoqin.bean;

import java.io.Serializable;

public class DownLoadBean implements Serializable{
	private long downLoadTiaoxiu;
	private long downLoadJiaban;
	private long printTiaoxiu;
	private long printJiaban;
	public long getDownLoadTiaoxiu() {
		return downLoadTiaoxiu;
	}
	public void setDownLoadTiaoxiu(long downLoadTiaoxiu) {
		this.downLoadTiaoxiu = downLoadTiaoxiu;
	}
	public long getDownLoadJiaban() {
		return downLoadJiaban;
	}
	public void setDownLoadJiaban(long downLoadJiaban) {
		this.downLoadJiaban = downLoadJiaban;
	}
	public long getPrintTiaoxiu() {
		return printTiaoxiu;
	}
	public void setPrintTiaoxiu(long printTiaoxiu) {
		this.printTiaoxiu = printTiaoxiu;
	}
	public long getPrintJiaban() {
		return printJiaban;
	}
	public void setPrintJiaban(long printJiaban) {
		this.printJiaban = printJiaban;
	}
	
}
