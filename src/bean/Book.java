package bean;

public class Book {
	private String name;// ����
	private String author;// ����
	private String press;// ������
	private String category;// ����
	private String reader = null;// ������

	public Book(String name, String author, String press, String category, String reader) {
		this.name = name;
		this.author = author;
		this.press = press;
		this.category = category;
		this.reader = reader;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPress() {
		return press;
	}

	public void setPress(String press) {
		this.press = press;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getReader() {
		return reader;
	}

	public void setReader(String reader) {
		this.reader = reader;
	}
}
