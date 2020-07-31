package com.BankApplication;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import java.sql.
//import ResultSet;

public class Bankapp extends HttpServlet{
	public void doGet(HttpServletRequest req,HttpServletResponse res) throws ServletException,IOException
	{
		//Statement st,st2;
		//PreparedStatement pst1,pst2,pst3;
		int amt = Integer.parseInt(req.getParameter("amnt"));
		int accNum = Integer.parseInt(req.getParameter("accnum"));
		String action = req.getParameter("action");
		PrintWriter out = res.getWriter();
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/saisumanth","root","");
			 Statement st = (Statement)conn.createStatement();
			 
			 ResultSet rs1 = st.executeQuery("select * from transactions2 order by id desc limit 1");
			 rs1.next();
			 int blnc = rs1.getInt(5);
			 
			 

				if("transfer".equals(action)){
					String type = "wthdrw";
					PreparedStatement pst1 = (PreparedStatement)conn.prepareStatement("insert into transactions2(AccNum,Type,Amnt,updbal) values(?,?,?,?)");
					pst1.setInt(1, accNum);
					pst1.setString(2, type);
					pst1.setInt(3, amt);
					int balance = blnc - amt;
					pst1.setInt(4, balance);
					int k = pst1.executeUpdate();
					out.println("ypur previous balance is"+blnc+"<br>");
					out.println("Transfer of amount "+amt+" is done Successfully\n"+"<br>");
					out.println("your balance is "+balance);
				}
				if("lasttrans".equals(action)){
				Statement st2 = (Statement)conn.createStatement();
					ResultSet rs2 = st2.executeQuery("select * from transactions2 order by id desc");
					out.println("your last 5 transactions are\n"+"<br>");
					out.println("AccNum"+"   "+"type"+"   "+"Amnount"+"    "+"Balance"+"<br>");
					while(rs2.next())
					out.println(rs2.getInt(2)+"   "+rs2.getString(3)+"   "+rs2.getInt(4)+"   "+rs2.getInt(5)+"<br>");
					
				}
		} 
	
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
		
		
		
	}

}
