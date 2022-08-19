package JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PayRoll {
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

	public static void addDataUsingPreparedStatement(int id,String name,int salary,String startdate,String gender, int phonenum,String address,String department,int basicpay,int deduction,int taxablepay,int incometax,int netpay ) throws SQLException {

		String insert = "INSERT INTO employeepayroll(`id`,`name`,`salary`,`startdate`,`gender`,`phonenum`,`address`,`department`,`basicpay`,`deduction`,`taxablepay`,`incometax`,`netpay`) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		System.out.println(insert);
		Connection connection = null;
		try {
			connection = getConnection();
			connection.setAutoCommit(false);
			PreparedStatement statement = connection.prepareStatement(insert);
			statement.setLong(1,id);
			statement.setString(2,name);
			statement.setLong(3,salary);
			statement.setString(4,startdate);
			statement.setString(5,gender);
			statement.setLong(6,phonenum);
			statement.setString(7,address);
			statement.setString(8,department);
			statement.setLong(9,basicpay);
			statement.setLong(10,deduction);
			statement.setLong(11,taxablepay);
			statement.setLong(12,incometax);
			statement.setLong(13,netpay);
			int rowEffected = statement.executeUpdate();
			System.out.println(rowEffected + " records inserted");
			connection.commit();
			System.out.println("Transaction is commited.");
		} catch (Exception e) {
			e.printStackTrace();
			connection.rollback();
		}
	}

	public static void addNewTableDataUsingPreparedStatement(int id,int salary,String startdate,int basicpay,int deduction,int taxablepay,int incometax,int netpay ) throws SQLException {		
		int deduction1 = (int) (salary * 0.2);
		int taxablepay1 = salary - deduction1;
		int incometax1 = (int) (taxablepay1 * 0.1);
		int netpay1 = salary - incometax1;
		String insert = "INSERT INTO payroll(`id`,`salary`,`startdate`,`basicpay`,`deduction`,`taxablepay`,`incometax`,`netpay`) values(?,?,?,?,?,?,?,?)";
		System.out.println(insert);
		Connection connection = null;
		try {

			connection = getConnection();
			connection.setAutoCommit(false);
			PreparedStatement statement = connection.prepareStatement(insert);
			statement.setLong(1,id);
			statement.setLong(2,salary);
			statement.setString(3,startdate);
			statement.setLong(4,basicpay);
			statement.setLong(5,deduction1);
			statement.setLong(6,taxablepay1);
			statement.setLong(7,incometax1);
			statement.setLong(8,netpay1);
			int rowEffected = statement.executeUpdate();
			System.out.println(rowEffected + " records inserted");
			connection.commit();
			System.out.println("Transaction is commited.");
		} catch (Exception e) {
			e.printStackTrace();
			connection.rollback();
		}
	}

	public static void main(String[] args) throws SQLException {
		connectToMysql();
		// prepared statement
		//addDataUsingPreparedStatement(8,"mrunu",10000,"2022-08-17","M",123456789,"Pune","HR",8000,1500,300,200,100);
		
		//in new table add record
		addNewTableDataUsingPreparedStatement(1,10000,"2022-08-17",8000,1500,300,200,100);

	}
}