package ai.jobiak.shoppingcart;
import java.util.ArrayList;
import java.sql.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ShoppingCartServlet
 */
@WebServlet("/cart3")
public class ShoppingCartServlet3 extends HttpServlet implements Serializable {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShoppingCartServlet3() {
        super();
        // TODO Auto-generated constructor stub
    }

    private String[] getProductIds(){
    	String url="jdbc:mysql://localhost:3306/world";
    	String username="root";
    	String password="admin";
    	String productIds[]=new String[10];
    	try {
    		Class.forName("com.mysql.cj.jdbc.Driver");
    		Connection con=DriverManager.getConnection(url,username,password);
    		java.sql.Statement st= con.createStatement();
    		String sql="Select productId from products";
    		ResultSet rs=st.executeQuery(sql);
    		int i=0;
    		while(rs.next()) {
    			productIds[i]=rs.getString(1);
    			i++;
    		}
    		
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	return productIds;
    	
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	

		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		PrintWriter out=response.getWriter();
		response.setContentType("text/html");
		String productIds[]=getProductIds();
		for(int i=0;i<productIds.length;i++) {
		out.println("<a href='cart3?item="+productIds[i]+"'>Add Item"+productIds[i]+"</a><br/>");
		}
		//String queryStr=request.getQueryString();
		//String splitArray[]=queryStr.split("=");
		//String productId=splitArray[1];
		ArrayList<Product>itemsList=null;
		HttpSession shoppingCart=request.getSession();
		
		if(shoppingCart.isNew()) {
			itemsList=new ArrayList<>();
			shoppingCart.setAttribute("items",itemsList);
		}else {
			String queryStr=request.getQueryString();
			String splitArray[]=queryStr.split("=");
			String productId=splitArray[1];
			itemsList=(ArrayList<Product>)shoppingCart.getAttribute("items");
			itemsList.add(new Product(productId,"ABC",78.00));
			//Product p=new Product("AFJ10itr","BSDGH5",5287);
			//itemsList.add(p);
			shoppingCart.setAttribute("items",itemsList);
		}
			for(Product p:itemsList) {
				out.println(p.getProductId()+"::"+p.getDescription()+"::"+p.getPrice()+"<br/>");
			}
			out.println("<h3>Items in the cart#"+itemsList.size());
		}
		
	}


