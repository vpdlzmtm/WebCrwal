package oracledb;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class Book_Rate_Run {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final String select_sql = """
				select BOOKID,책이름, 책순위, 책가격\s
				from BOOK
				""";
		Connection connection = null;
		final String connectionURL = """
				jdbc:oracle:thin:@10.10.108.182:1521/xe
				""";
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			connection = DriverManager.getConnection(connectionURL, "BOOKRATE", "bookrate");
			final PreparedStatement preparedStatement = connection.prepareStatement(select_sql);
			final ResultSet resultSet = preparedStatement.executeQuery();
			final OutputStream outputStream = new FileOutputStream("./oracle.text");
			final Writer writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
			while(resultSet.next()) {
				final Book_Rate book_rate = new Book_Rate(resultSet.getInt(1),
						resultSet.getString(2),
						resultSet.getString(3),
						resultSet.getString(4));
				writer.write(String.valueOf(book_rate));
				writer.write("\r\n");
				writer.flush();
				System.out.println(book_rate);
			}
			writer.close();
			resultSet.close();
			preparedStatement.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// 쓴 다음 try 캐치문
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}