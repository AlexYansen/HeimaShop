package com.itcast.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.itcast.domain.Category;
import com.itcast.domain.Product;
import com.itcast.utils.DataSourceUtils;
import com.sun.org.apache.bcel.internal.generic.NEW;

public class ProductDao {

	public List<Product> getHotProduct() throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product where is_hot = ? limit ?,?";
		return runner.query(sql, new BeanListHandler<Product>(Product.class),1,0,9);
		
	}

	public List<Product> getNewProduct() throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product order by pdate desc limit ?,?";
		return runner.query(sql, new BeanListHandler<Product>(Product.class),0,9);
	}

	public List<Category> getCetegory() throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from category";
		return runner.query(sql, new BeanListHandler<Category>(Category.class));
	}

	public long getTotalCount(String cid) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select count(*) from product where cid=?";
		Long count = (Long) runner.query(sql, new ScalarHandler(),cid);
		return count.intValue();
	}

	public List<Product> getCurrentProductList(String cid, int index,
			int currentCount) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product where cid=? limit ?,?";
		List<Product> list = runner.query(sql, new BeanListHandler<Product>(Product.class),cid,index,currentCount);
		return list;
	}

	public Product findProductByPid(String pid) throws SQLException {
		// TODO Auto-generated method stub
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product where pid=?";
		Product product = runner.query(sql, new BeanHandler<Product>(Product.class),pid);
		return product;
	}

}
