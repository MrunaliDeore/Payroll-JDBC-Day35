package JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class PayRoll_InsertIntoBothTable {
	
		public static void connectToMysql() {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				System.out.println("Driver loaded");
				Connection connection = DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/payrollservice",
						"root",
						"Mrunali@17");

				System.out.println("Successfully connected to MySQL database test" + connection);
			} catch (SQLException e) {
				System.out.println("An error occurred while connecting MySQL databse");
				e.getMessage();
			} catch (ClassNotFoundException e) {
				System.out.println("cannot find the driver in the class path !");
				e.getMessage();
			}

		}

		private static Connection getConnection() throws SQLException {
			Connection connection = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/payrollservice",
					"root",
					"Mrunali@17");

			System.out.println("Successfully connected to MySQL database test" + connection);
			return connection;
		}

		public static void addDataUsingPreparedStatement(int cid,int id,String name,String startdate,String gender, int phonenum,String address) throws SQLException {

			String insert = "INSERT INTO company(`cid`,`id`,`name`,`startdate`,`gender`,`phonenum`,`address`) values(?,?,?,?,?,?,?)";
			System.out.println(insert);
			Connection connection = null;
			try {
				connection = getConnection();
				connection.setAutoCommit(false);
				PreparedStatement statement = connection.prepareStatement(insert);
				statement.setLong(1,cid);
				statement.setLong(2,id);
				statement.setString(3,name);
				statement.setString(4,startdate);
				statement.setString(5,gender);
				statement.setLong(6,phonenum);
				statement.setString(7,address);
				int rowEffected = statement.executeUpdate();
				System.out.println(rowEffected + " records inserted");
				connection.commit();
				System.out.println("Transaction is commited.");
			} catch (Exception e) {
				e.printStackTrace();
				connection.rollback();
			}
		}

		public static void addNewTableDataUsingPreparedStatement(int pid,int cid,int id,int salary,String startdate,int basicpay,int deduction,int taxablepay,int incometax,int netpay ) throws SQLException {		
			int deduction1 = (int) (salary * 0.2);
			int taxablepay1 = salary - deduction1;
			int incometax1 = (int) (taxablepay1 * 0.1);
			int netpay1 = salary - incometax1;
			String insert = "INSERT INTO payroll(`pid`,`cid`,`id`,`salary`,`startdate`,`basicpay`,`deduction`,`taxablepay`,`incometax`,`netpay`) values(?,?,?,?,?,?,?,?,?,?)";
			System.out.println(insert);
			Connection connection = null;
			try {

				connection = getConnection();
				connection.setAutoCommit(false);
				PreparedStatement statement = connection.prepareStatement(insert);
				statement.setLong(1,pid);
				statement.setLong(2,cid);
				statement.setLong(3,id);
				statement.setLong(4,salary);
				statement.setString(5,startdate);
				statement.setLong(6,basicpay);
				statement.setLong(7,deduction1);
				statement.setLong(8,taxablepay1);
				statement.setLong(9,incometax1);
				statement.setLong(10,netpay1);
				int rowEffected = statement.executeUpdate();
				System.out.println(rowEffected + " records inserted");
				connection.commit();
				System.out.println("Transaction is commited.");
			} catch (Exception e) {
				e.printStackTrace();
				connection.rollback();
			}
		}
		
		public static void deleteDataUsingPreparedStatemnt() throws SQLException {
			System.out.println("\nDelete one table data");
			String delete = "DELETE FROM company where cid=101";
			System.out.println(delete);
			Connection connection = null;
			try {
				connection = getConnection();
				connection.setAutoCommit(false);
				PreparedStatement statement = connection.prepareStatement(delete);
				int resultSet = statement.executeUpdate();			
				connection.commit();
				System.out.println("Transaction is committed.");
			} catch (Exception e) {
				e.printStackTrace();
				connection.rollback();
			}
		}
		

		public static void main(String[] args) throws SQLException {
			connectToMysql();
			// prepared statement
//			addDataUsingPreparedStatement(101,2,"mona","2022-08-20","M",123456789,"Pune");
			
			//in new table add record
//			addNewTableDataUsingPreparedStatement(11,101,2,10000,"2022-08-17",8000,1500,300,200,100);
			
			//delete record
			deleteDataUsingPreparedStatemnt();
		}
	}