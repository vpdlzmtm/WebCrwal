package oracledb;

public record Book_Rate(int bookid, String book_name, String book_rate, String book_price) {

}

/*public record Book(int bookid,
		String book_name, String book_publisher, int book_price) {

}//내부적으로 get set 이 다 자동적으로 만들어짐.*/
