package oracledb;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class Insert_To_Book_Rate_Run {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connection connection = null;
		final String connectionUrl = """
				jdbc:oracle:thin:@10.10.108.127:1521/xe
				""";
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			connection = DriverManager.getConnection(connectionUrl, "BOOKRATE", "bookrate");
			final String insert_sql = """
					INSERT INTO book(bookid, 책이름, 책순위, 책가격)\s
					VALUES(?, ?, ?, ?)
					""";
			final InputStream inputStream = new FileInputStream("./Book_tabel_NoIndex.csv");
			final Reader reader = new InputStreamReader(inputStream);
			final BufferedReader bufferedReader = new BufferedReader(reader);
			int index = 0;
			PreparedStatement preparedStatement = null;			
			while(true) {
				String book_rate_str = bufferedReader.readLine(); //한줄을 통채로 읽어 오겠다.
				if(book_rate_str == null) break; //다 읽은 경우 빠져나오기
				String book_rate = null;
				String book_name = null;
				String book_price = null;
													
				String[] patterns;
				final String[] patterns1 = book_rate_str.split(",");
				patterns = new String[patterns1.length];
				for(int i = 0; i < patterns1.length; ++i) {
					patterns[i] = patterns1[i];
				}										
					book_rate = patterns[1]; //책 순위, 여기에서 문자열로 받아와버리니까 안되네
					book_name = patterns[0]; //책 이름
					book_price = patterns[2]; //책 가격
					
					int book_rate_num = Integer.parseInt(book_rate);
					int book_price_num = Integer.parseInt(book_price);
					
					preparedStatement = connection.prepareStatement(insert_sql);
					preparedStatement.setInt(1, ++index);
					preparedStatement.setString(2, book_name);
					preparedStatement.setInt(3, book_rate_num);
					preparedStatement.setInt(4, book_price_num);					
					final int row = preparedStatement.executeUpdate();
					System.out.println("저장된 index : " + row);
								
				}			
				
			
			
			
			preparedStatement.close();
			bufferedReader.close(); //이것만 해줘도 다 닫아준다.
			reader.close();
			inputStream.close();			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
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