package dao;

import bean.Book;
import util.JDBCUtils;

import java.sql.Types;
import java.util.ArrayList;

public class BookDao {

	public static Book findBook(String name) {
		String sql = "SELECT * FROM " + JDBCUtils.table[1] + " WHERE name = ? ";
		Book book = null;
		JDBCUtils.setStmt(sql);
		JDBCUtils.setObject(1, name, Types.CHAR);
		JDBCUtils.executeQuery();
		if (JDBCUtils.next()) {
			book = new Book(JDBCUtils.getString("name"), 
							JDBCUtils.getString("author"),
							JDBCUtils.getString("press"), 
							JDBCUtils.getString("category"),
							JDBCUtils.getString("reader"));
		}
		JDBCUtils.close();
		return book;
	}

	public static ArrayList<Book> findBooks(String attribute, String value, String mode, String number) { // ��������attributeֵΪvalue���飬modeָ������
		String sql = null;
		ArrayList<Book> books = new ArrayList<>();
		Book book = null;
		
		if (mode.equals("admin")) {// ����Ա����ͼ��
			if (attribute.equals("all"))// ������
				sql = "SELECT * FROM " + JDBCUtils.table[1];
			else if(attribute.equals("borrowed"))//����Ա�����ѽ��ͼ��
				sql = "SELECT * FROM " + JDBCUtils.table[1] + " WHERE reader IS NOT NULL ";
			else if(attribute.equals("borrowable"))//����Ա����δ���ͼ��
				sql = "SELECT * FROM " + JDBCUtils.table[1] + " WHERE reader IS NULL ";
			else//������
				sql = "SELECT * FROM " + JDBCUtils.table[1] + " WHERE " + attribute + " = '" + value + "'";
		}
		
		else {
			if (mode.equals("borrowed")) { // ѧ�������Լ����ĵ��飨�ֶ�readerΪNULL��
				if(attribute.equals("all"))//������
					sql = "SELECT * FROM " + JDBCUtils.table[1] + " WHERE reader = " + number;
				else //������
					sql = "SELECT * FROM " + JDBCUtils.table[1] + " WHERE reader = " + number + " AND " + attribute + " = '" + value + "'";
			}
			if (mode.equals("borrowable")) {// ѧ�����ҿɽ��ĵ�ͼ��
				if (attribute.equals("all"))//������
					sql = "SELECT * FROM " + JDBCUtils.table[1] + " WHERE reader IS NULL ";
				else //������
					sql = "SELECT * FROM " + JDBCUtils.table[1] + " WHERE reader IS NULL AND " + attribute + " = '" + value + "'";
			}
		}
			
			
		JDBCUtils.setStmt(sql);
		JDBCUtils.executeQuery();
		while (JDBCUtils.next()) {
			book = new Book(JDBCUtils.getString("name"), 
							JDBCUtils.getString("author"),
							JDBCUtils.getString("press"), 
							JDBCUtils.getString("category"), 
							JDBCUtils.getString("reader"));
			books.add(book);
		}
		JDBCUtils.close();
		return books;
	}

	public static boolean borrowBook(String number, String bookName) {
		String sql = "UPDATE " + JDBCUtils.table[1] + " SET reader = " + number + " WHERE name = '" + bookName + "'";
		JDBCUtils.setStmt(sql);
		int result = JDBCUtils.executeUpdate();
		JDBCUtils.close();
		if (result != 0)
			return true;
		return false;
	}

	public static boolean returnBook(String number, String bookName) {
		String sql = "UPDATE  " + JDBCUtils.table[1] + " SET reader = null WHERE name = '" + bookName + "'";
		JDBCUtils.setStmt(sql);
		int result = JDBCUtils.executeUpdate();
		JDBCUtils.close();
		if (result != 0)
			return true;
		return false;
	}

	public static boolean addBook(String name, String author, String press, String category) {
		Book book = findBook(name);
		if (book == null) {
			String sql = "INSERT INTO " + JDBCUtils.table[1] + "(name, author, press, category) VALUES (?,?,?,?)";
			JDBCUtils.setStmt(sql);
			JDBCUtils.setObject(1, name, Types.CHAR);
			JDBCUtils.setObject(2, author, Types.CHAR);
			JDBCUtils.setObject(3, press, Types.CHAR);
			JDBCUtils.setObject(4, category, Types.CHAR);
			int result = JDBCUtils.executeUpdate();
			JDBCUtils.close();
			if (result != 0)
				return true;
		}
		return false;
	}

	public static boolean delete(String bookName) {
		String sql = "DELETE FROM " + JDBCUtils.table[1] + " WHERE name = '" + bookName +"'";
		JDBCUtils.setStmt(sql);
		int result = JDBCUtils.executeUpdate();
		JDBCUtils.close();
		if (result != 0)
			return true;
		return false;
	}

}
