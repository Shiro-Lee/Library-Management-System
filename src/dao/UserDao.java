package dao;

import bean.User;
import util.JDBCUtils;

import java.sql.Types;
import java.util.ArrayList;

public class UserDao {

	// ע��
	public static boolean regist(String number, String password, String name, String class_name) {
		User user = findUser(number, true);
		if (user == null) {
			String sql = "INSERT INTO " + JDBCUtils.table[0] + "(number,password,name,class_name) VALUES (?,?,?,?)";
			JDBCUtils.setStmt(sql);
			JDBCUtils.setObject(1, number, Types.CHAR);
			JDBCUtils.setObject(2, password, Types.CHAR);
			JDBCUtils.setObject(3, name, Types.CHAR);
			JDBCUtils.setObject(4, class_name, Types.CHAR);
			JDBCUtils.executeUpdate();
			JDBCUtils.close();
			return true;
		}
		return false;
	}

	// ��½
	public static User login(String number, String password, boolean isStudent) {
		User user;
		user = findUser(number, isStudent);
		if (user != null && user.getPassword().equals(password) && user.getNumber().equals(number))
			return user;
		return null;
	}

	// ��ѯ�����û�
	public static User findUser(String number, boolean isStudent) {
		String sql;
		if (isStudent)
			sql = "SELECT * FROM " + JDBCUtils.table[0] + " WHERE number = ? and id != 1";
		else
			sql = "SELECT * FROM " + JDBCUtils.table[0] + " WHERE number = ? and id = 1 ";
		User user = null;
		JDBCUtils.setStmt(sql);
		JDBCUtils.setObject(1, number, Types.CHAR);
		JDBCUtils.executeQuery();
		if (JDBCUtils.next())
			user = new User(JDBCUtils.getString("number"), JDBCUtils.getString("password"), JDBCUtils.getString("name"),
					JDBCUtils.getString("class_name"));
		JDBCUtils.close();
		return user;
	}

	//��ȡ�����û���������Ա��
	public static ArrayList<User> getUsers(String character, String value) {
		String sql;
		User user = null;
		ArrayList<User> users = new ArrayList<>();
		if(character.equals("all"))//��������ѧ��
			sql = "SELECT * FROM " + JDBCUtils.table[0] + " WHERE id != 1";
		else //��������ѧ��
			sql = "SELECT * FROM " + JDBCUtils.table[0] + " WHERE id != 1 AND " + character + " = '" + value + "'" ;
		JDBCUtils.setStmt(sql);
		JDBCUtils.executeQuery();
		while (JDBCUtils.next()) {
			user = new User(JDBCUtils.getString("number"), 
							null, 
							JDBCUtils.getString("name"), 
							JDBCUtils.getString("class_name"));
			users.add(user);
		}
		JDBCUtils.close();
		return users;
	}

}
