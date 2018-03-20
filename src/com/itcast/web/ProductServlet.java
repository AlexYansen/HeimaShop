package com.itcast.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.itcast.domain.Cart;
import com.itcast.domain.CartItem;
import com.itcast.domain.Category;
import com.itcast.domain.PageBean;
import com.itcast.domain.Product;
import com.itcast.service.ProductService;

public class ProductServlet extends BaseServlet {

//	public void doGet(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		String method = request.getParameter("method");
//		if("category".equals(method)){
//			category(request, response);
//		} else if("index".equals(method)){
//			index(request, response);
//		} else if("productListByCid".equals(method)){
//			productListByCid(request, response);
//		} else if("findProductByPid".equals(method)){
//			findProductByPid(request, response);
//		}
//		
//	}
//
//	public void doPost(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		// TODO Auto-generated method stub
//		doGet(request, response);
//	}
	public void clearCart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.removeAttribute("cart");
		response.sendRedirect(request.getContextPath());
	}
	
	
	public void delProFromCart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String pid = request.getParameter("pid");
		HttpSession session = request.getSession();
		Cart cart = (Cart) session.getAttribute("cart");
		if(cart!=null){
			Map<String, CartItem> cartItems = cart.getCartItems();
			cart.setTotal(cart.getTotal()-cartItems.get(pid).getSubtotal());
			cartItems.remove(pid);
		}
		session.setAttribute("cart", cart);
		response.sendRedirect(request.getContextPath()+"/cart.jsp");
	}
	
	
	public void addProductToCart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		String pid = request.getParameter("pid");
		String buyNumStr = request.getParameter("buyNum");
		int buyNum = Integer.parseInt(buyNumStr);
		ProductService service = new ProductService();
		Product product = service.findProductByPid(pid);
		double subtotal = buyNum*product.getShop_price();
		CartItem cartItem = new CartItem();
		cartItem.setBuyNum(buyNum);
		cartItem.setProduct(product);
		cartItem.setSubtotal(subtotal);
		Cart cart = (Cart) session.getAttribute("cart");
		if(cart==null){
			cart=new Cart();
		}
		Map<String, CartItem> cartItems = cart.getCartItems();

		double newsubtotal = 0.0;

		if(cartItems.containsKey(pid)){
			//取出原有商品的数量
			CartItem item = cartItems.get(pid);
			int oldBuyNum = item.getBuyNum();
			oldBuyNum+=buyNum;
			item.setBuyNum(oldBuyNum);
			cart.setCartItems(cartItems);
			//修改小计
			//原来该商品的小计
			double oldsubtotal = item.getSubtotal();
			//新买的商品的小计
			newsubtotal = buyNum*product.getShop_price();
			item.setSubtotal(oldsubtotal+newsubtotal);

		}else{
			//如果车中没有该商品
			cart.getCartItems().put(product.getPid(), cartItem);
			newsubtotal = buyNum*product.getShop_price();
		}

		//计算总计
		double total = cart.getTotal()+newsubtotal;
		cart.setTotal(total);


		//将车再次访问session
		session.setAttribute("cart", cart);

//		request.getRequestDispatcher("/cart.jsp").forward(request, response);
		response.sendRedirect(request.getContextPath()+"/cart.jsp");
		
	}
	
	public void category(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ProductService service = new ProductService();
		List<Category> cetegory = service.getCategory();
		response.setContentType("text/html;charset=UTF-8");
		Gson gson = new Gson();
		String categoryList = gson.toJson(cetegory);
//		System.out.println(categoryList);
		response.getWriter().write(categoryList);
	}
	public void index(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ProductService service = new ProductService();
//		List<Category> cetegory = service.getCategory();
		List<Product> hotProduct = service.getHotProduct();
		List<Product> newProduct = service.getNewProduct();
		request.setAttribute("hotProductList", hotProduct);
		request.setAttribute("newProductList", newProduct);
		
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}
	public void productListByCid(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String currentPageString = request.getParameter("currentPage");
		if(currentPageString==null){
			currentPageString="1";
		}
		int currentPage = Integer.parseInt(currentPageString);
		int currentCount =12;
		String cid = request.getParameter("cid");
		ProductService service = new ProductService();
		PageBean pageBean = service.getProductListByCid(cid,currentCount,currentPage);
		request.setAttribute("pageBean", pageBean);
		request.setAttribute("cid", cid);
		List<Product> historyProductList = new ArrayList<Product>();
		Cookie[] cookies = request.getCookies();
		if(cookies!=null){			
			for(Cookie cookie:cookies){
				if("pids".equals(cookie.getName())){
					String pids = cookie.getValue();
					String[] split = pids.split("-");
					for(String pid:split){
						Product pro = service.findProductByPid(pid);
						historyProductList.add(pro);
					}
				}
			}
		}
		request.setAttribute("historyProductList", historyProductList);
		request.getRequestDispatcher("/product_list.jsp").forward(request, response);
	}
	
	public void findProductByPid(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String pid = request.getParameter("pid");
		String cid = request.getParameter("cid");
		String currentPage = request.getParameter("currentPage"); 
		ProductService service = new ProductService();
		Product product = service .findProductByPid(pid);
		request.setAttribute("product", product);
		request.setAttribute("cid", cid);
		request.setAttribute("currentPage", currentPage);
		
		String pids = pid;
		Cookie[] cookies = request.getCookies();
		if(cookies!=null){
			for(Cookie cookie:cookies){
				if("pids".equals(cookie.getName())){
					pids = cookie.getValue();
					String[] split = pids.split("-");
					List<String> asList = Arrays.asList(split);
					LinkedList<String> list = new LinkedList<String>(asList);
					if(list.contains(pid)){
						list.remove(pid);
					}
					list.addFirst(pid);
					StringBuffer sb = new StringBuffer();
					for(int i=0;i<list.size()& i<7;i++){
						sb.append(list.get(i));
						sb.append("-");
					}
					pids = sb.substring(0, sb.length()-1);
				}
			}
		}
		Cookie cookie_new = new Cookie("pids", pids);
		response.addCookie(cookie_new);
		
		
		request.getRequestDispatcher("/product_info.jsp").forward(request, response);
	}

}