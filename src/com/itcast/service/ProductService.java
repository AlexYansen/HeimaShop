package com.itcast.service;

import java.sql.SQLException;
import java.util.List;

import com.itcast.dao.ProductDao;
import com.itcast.domain.Category;
import com.itcast.domain.PageBean;
import com.itcast.domain.Product;


public class ProductService {

	public List<Product> getHotProduct() {
		ProductDao dao = new ProductDao();
		// TODO Auto-generated method stub
		List<Product> hotProduct = null;
		try {
			hotProduct = dao.getHotProduct();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return hotProduct;
	}

	public List<Product> getNewProduct() {
		ProductDao dao = new ProductDao();
		// TODO Auto-generated method stub
		List<Product> newProduct = null;
		try {
			newProduct = dao.getNewProduct();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newProduct;
	}

	public List<Category> getCategory() {
		ProductDao dao = new ProductDao();
		List<Category> category = null;
		try {
			category = dao.getCetegory();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return category;
	}

	public PageBean getProductListByCid(String cid,int currentCount,int currentPage) {
		PageBean pageBean = new PageBean();
		
		pageBean.setCurrentPage(currentPage);
		pageBean.setCurrentCount(currentCount);
		ProductDao dao = new ProductDao();
		int totalCount = 0;
		try {
			totalCount = (int) dao.getTotalCount(cid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		pageBean.setTotalCount(totalCount);
		int totalPage = (int) Math.ceil(1.0*totalCount/currentCount);
		pageBean.setTotalPage(totalPage);
		int index = (currentPage-1)*currentCount;
		List<Product> list= null;
		try {
			list = dao.getCurrentProductList(cid,index,currentCount);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pageBean.setList(list);
		
		
		return pageBean;
	}

	public Product findProductByPid(String pid) {
		ProductDao dao = new ProductDao();
		Product product = null;
		try {
			product = dao .findProductByPid(pid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return product;
	}


}
