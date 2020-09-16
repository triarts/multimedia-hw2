package com.shared.RTPpaket;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class RTPPacket implements Serializable {
	int sequence;
	int totalseq;
	String data;
	long timestamp;
	String dataName;
	
	private static final long serialVersionUID = 0656555L;
	
	public void set(int seq, byte[] data) {
		this.sequence = seq;
//		this.data = new byte[data.length];
//		System.arraycopy(this.data,0,data,0,this.data.length);
		
		this.data = Base64.getEncoder().encodeToString(data);
		timestamp = System.currentTimeMillis();	
	}
	
	public void set(int seq, byte[] data, String dataName,int totalSquence) {
		this.sequence = seq;
//		this.data = new byte[data.length];
//		System.arraycopy(this.data,0,data,0,this.data.length);
		
		this.data = Base64.getEncoder().encodeToString(data);
		timestamp = System.currentTimeMillis();	
		this.dataName = dataName;
	}
	
//	public void set(int seq) {
//		this.sequence = seq;
//		timestamp = System.currentTimeMillis();	
//	}

	public byte[] getData() {
		//return this.data.getBytes(StandardCharsets.UTF_8);
		return Base64.getDecoder().decode(this.data);
	}
	
	public int getTotalseq() {
		return totalseq;
	}
//	public String getData() {
//		return data;
//	}
	
	public String getDataName() {
		return dataName;
	}
	public int getSequence() {
		return sequence;
	}
	public long getTimestamp() {
		return timestamp;
	}

}
