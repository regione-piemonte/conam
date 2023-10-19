/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.vo.verbale;

import it.csi.conam.conambl.vo.ParentVO;

public class PaginatorVO extends ParentVO {

	private static final long serialVersionUID = -7714298531166937914L;

	private int pageReq;
	private int maxLineReq;
	
	private int pageResp;
	private int lineRes;
	private int totalLineResp;
	
	
	public int getPageReq() {
		return pageReq;
	}
	public void setPageReq(int pageReq) {
		this.pageReq = pageReq;
	}
	
	public int getMaxLineReq() {
		return maxLineReq;
	}
	public void setMaxLineReq(int maxLineReq) {
		this.maxLineReq = maxLineReq;
	}
	
	public int getPageResp() {
		return pageResp;
	}
	public void setPageResp(int pageResp) {
		this.pageResp = pageResp;
	}
	
	public int getTotalLineResp() {
		return totalLineResp;
	}
	public void setTotalLineResp(int totalLineResp) {
		this.totalLineResp = totalLineResp;
	}

	public int getLineRes() {
		return lineRes;
	}
	public void setLineRes(int lineRes) {
		this.lineRes = lineRes;
	}
	
	
		
}
