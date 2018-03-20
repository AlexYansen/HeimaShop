package com.itcast.domain;

public class CartItem {
	
	private Product product;
	private Integer buyNum;
	private Double subtotal;
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Integer getBuyNum() {
		return buyNum;
	}
	public void setBuyNum(Integer buyNum) {
		this.buyNum = buyNum;
	}
	public Double getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(Double subtotal) {
		this.subtotal = subtotal;
	}
}
