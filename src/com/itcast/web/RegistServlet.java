package com.itcast.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.ParseConversionEvent;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;

import com.itcast.domain.User;
import com.itcast.service.UserService;
import com.itcast.utils.CommonsUtils;
import com.itcast.utils.MailUtils;

public class RegistServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//解决乱码
		request.setCharacterEncoding("UTF-8");
		//接收参数并封装
		Map<String, String[]> properties = request.getParameterMap();
		User user = new User();
		try {
			ConvertUtils.register(new Converter() {
				
				@Override
				public Object convert(Class clazz, Object value) {
					// TODO Auto-generated method stub
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					Date parse = null;
					try {
						parse = format.parse(value.toString());
					} catch (ParseException e) {
						e.printStackTrace();
					}
					return parse;
				}
			}, Date.class);
			BeanUtils.populate(user, properties);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		user.setTelephone(null); 
		user.setUid(CommonsUtils.getUUID()); 
		user.setState(0);
		String activeCode = CommonsUtils.getUUID();
		user.setCode(activeCode); 
		
		UserService service = new UserService();
		//传递数据到service层
		boolean isRigist = service.rigist(user);
		//判断注册是否成功
		if(isRigist){
			//注册成功
			String emailMsg = "恭喜您注册成功，请点击下面的链接进行激活账户"+"<a href='http://localhost:80080/HeimaShop/active?activeCode="+activeCode+"'>"+"http://localhost:80080/HeimaShop/active?activeCode="+activeCode+"</a>";
			try {
				MailUtils.sendMail(user.getEmail(), emailMsg);
			} catch (AddressException e) {
				e.printStackTrace();
			} catch (MessagingException e) {
				e.printStackTrace();
			}
			response.sendRedirect(request.getContextPath()+"/registerSuccess.jsp");
		}else{
			response.sendRedirect(request.getContextPath()+"/registerFail.jsp");		
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}