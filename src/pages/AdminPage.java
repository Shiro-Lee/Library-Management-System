package pages;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import bean.Book;
import bean.User;
import service.UserService;


public class AdminPage extends JFrame {

	private JPanel contentPane;//�����
	private JTextField attributeValue;//��������attribute��ͼ�����������attributeֵ
	private JTable bookTable;//ͼ����
	private JTable studentTable;//ѧ�����
	private JScrollPane bookScrollPane;//����ͼ����Ĺ������
	private JScrollPane studentScrollPane;//����ѧ�����Ĺ������
	private DefaultTableModel bookDTM;//ͼ����ģ��
	private DefaultTableModel studentDTM;//ѧ�����ģ��
	private JButton addBook;//���ͼ�鰴ť
	private JButton deleteBook;//ɾ��ͼ�鰴ť
	private JButton showStudents;//��ʾѧ����ť
	private JButton showBooks;//��ʾͼ�鰴ť
	private JComboBox<String> selectAttributes;//ѡ��ͼ����ҵ��������Ը�ѡ��
	private JComboBox<String> selectCategory;//ѡ��ͼ�����ѡ��
	private JComboBox<String> selectCharacter;//ѡ��ѧ�����ҵ����ݵ����Ը�ѡ��
	private JComboBox<String> selectClass;//ѡ��༶��ѡ��
	private JButton searchBooks;//���Ұ�ť
	private JPanel bookOperatePanel;//ͼ��������
	private JPanel studentOperatePanel;//ѧ���������
	private JButton searchStudent;//����ѧ����ť
	private JTextField characterValue;//��������character��ͼ�����������characterֵ
	private JFrame addBookPage;//���ͼ���Ӵ���
	
	/**
	 * Create the frame.
	 */
	public AdminPage(String number) {
		
		super("����Աҳ��");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(400, 150, 700, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		ImageIcon adminIcon = new ImageIcon("src/icon/admin.png");//�����ǩ����ͼ��
		
		JLabel adminTitle = new JLabel("��ӭ��������Ա",adminIcon,JLabel.RIGHT);//�����ǩ
		adminTitle.setHorizontalAlignment(SwingConstants.CENTER);
		adminTitle.setFont(new Font("����", Font.PLAIN, 30));
		adminTitle.setBounds(230, 10, 280, 44);
		contentPane.add(adminTitle);
		
		JButton exit = new JButton("�˳���¼");
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new WelcomePage().setVisible(true);
			}
		});
		exit.setBounds(40, 10, 90, 23);
		contentPane.add(exit);
		
		
		// ------------------------------------------------ͼ�鲿��------------------------------------------------
		
		bookOperatePanel = new JPanel();
		bookOperatePanel.setBounds(230, 35, 410, 50);
		contentPane.add(bookOperatePanel);
		bookOperatePanel.setLayout(null);
		
		addBook = new JButton("���ͼ��");
		addBook.setBounds(0, 27, 90, 23);
		AdminPage self = this;
		addBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addBookPage = new AddBookPage(self);
				addBookPage.setVisible(true);
			}
		});
		bookOperatePanel.add(addBook);
		
		deleteBook = new JButton("ɾ��ͼ��");
		deleteBook.setBounds(90, 27, 90, 23);
		bookOperatePanel.add(deleteBook);
		deleteBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selected = bookTable.getSelectedRow();//��ȡѡ�����±�ֵ	
				String name = (String)bookTable.getValueAt(selected, 0);//��ȡ����
				int choice = JOptionPane.showConfirmDialog(null, "ȷ��Ҫɾ����" + name + "����","ɾ��ͼ��",JOptionPane.YES_NO_OPTION);
				if (choice==0) {
					UserService.deleteBook(name);
					JOptionPane.showMessageDialog(null,"ɾ���ɹ�");
					showBooks("all", null, "admin", null);
				}
			}
		});
		deleteBook.setEnabled(false);
		
		selectAttributes = new JComboBox<>();
		selectAttributes.setBounds(190, 27, 65, 23);
		selectAttributes.addItem("����");//��ӦJComboBox�±�0
		String []attributes = {"����","����","������","���","������ѧ��"};//�������ԣ����ڽ���ѡ�񣬶�ӦJComboBox�±�1-5
		String []attributes_eng = {"all","name","author","press","category","reader","borrowed","borrowable"};//Ӣ�����ԣ����ں�̨���ң���ӦJComboBox�±�0-7
		for(String attribute: attributes)
			selectAttributes.addItem(attribute);
		selectAttributes.addItem("�ѽ���");
		selectAttributes.addItem("δ����");
		selectAttributes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(selectAttributes.getSelectedIndex()==4) {//��ѡ���������Ϊ���������ֵ�����Ϊ���������
					attributeValue.setVisible(false);
					selectCategory.setVisible(true);
				}
				else {
					attributeValue.setVisible(true);
					selectCategory.setVisible(false);
				}
			}
		});
		bookOperatePanel.add(selectAttributes);
		
		attributeValue = new JTextField();
		attributeValue.setBounds(258, 27, 90, 21);
		bookOperatePanel.add(attributeValue);
		attributeValue.setColumns(10);
		
		selectCategory = new JComboBox<>();
		selectCategory.setBounds(268, 27, 80, 21);
		bookOperatePanel.add(selectCategory);
		selectCategory.setVisible(false);
		
		showBooks = new JButton("�鿴ͼ��");	//�鿴ͼ�鰴ť���л���ͼ�鲿��
		showBooks.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bookScrollPane.setVisible(true);
				studentScrollPane.setVisible(false);
				showBooks.setEnabled(false);//ʹ��ʾͼ�鰴ť������
				showStudents.setEnabled(true);//ʹ��ʾѧ����ť����
				bookOperatePanel.setVisible(true);//��ʾͼ��������
				studentOperatePanel.setVisible(false);
			}
		});
		showBooks.setBounds(40, 62, 90, 23);
		showBooks.setEnabled(false);
		contentPane.add(showBooks);
		String[] categorys = {"��ѧ��","����ѧ��","������","������","������",
				"��ѧ��","�������","������","����ѧ��","ҽѧ��","����ѧ��","��ʷ��"};
		for(String item: categorys)
			selectCategory.addItem(item);
		
		
		searchBooks = new JButton("����");
		searchBooks.setBounds(350, 27, 60, 23);
		searchBooks.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteBook.setEnabled(false);
				int attributeIndex = selectAttributes.getSelectedIndex();//��ȡ�������ݵ�������
				String attribute = attributes_eng[attributeIndex];
				String value;
				if(attributeIndex==4)//��ȡ��ȡ�������ݵ�����ֵ����Ϊ���������categorySelect����ȡֵ
					value = (String)selectCategory.getSelectedItem();
				else
					value = attributeValue.getText();
				int selectedIndex = selectAttributes.getSelectedIndex();
				if(value.equals("")&&selectedIndex!=0&&selectedIndex!=6&&selectedIndex!=7) {
					JOptionPane.showMessageDialog(null,"�������������ֵ");
					return;
				}
				showBooks(attribute, value, "admin", null);
			}
		});
		bookOperatePanel.add(searchBooks);
		
		
		bookDTM = new DefaultTableModel(){//ͼ����ģ�壬ͨ����дisCellEditable����ʹ�䲻�ɱ༭
            public boolean isCellEditable(int row, int column){
                return false;
            }
		};
		bookDTM.setColumnIdentifiers(attributes);//���ñ������
		bookTable = new JTable(bookDTM);	
		bookScrollPane = new JScrollPane(bookTable);//���ɱ��Ĺ������
		bookScrollPane.setBounds(40, 90, 600, 335);
		contentPane.add(bookScrollPane);
		showBooks("all", null, "admin", null);//��ʼ�����
		bookTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int selected = bookTable.getSelectedRow();//��ȡѡ�����±�ֵ
				boolean borrowable = (bookTable.getValueAt(selected, 4))==null;
				if(selected>=0 && selected<=bookTable.getRowCount()&&borrowable)
					deleteBook.setEnabled(true);
				else deleteBook.setEnabled(false);
			}
		});
		bookScrollPane.setVisible(true);
		

		// ------------------------------------------------ѧ������------------------------------------------------
		
		showStudents = new JButton("�鿴ѧ��");	//�鿴ѧ����ť���л���ѧ������
		showStudents.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bookScrollPane.setVisible(false);
				studentScrollPane.setVisible(true);
				showBooks.setEnabled(true);
				showStudents.setEnabled(false);
				bookOperatePanel.setVisible(false);
				studentOperatePanel.setVisible(true);
			}
		});
		showStudents.setBounds(130, 62, 90, 23);
		contentPane.add(showStudents);
		
		studentOperatePanel = new JPanel();
		studentOperatePanel.setBounds(420, 35, 220, 50);
		contentPane.add(studentOperatePanel);
		studentOperatePanel.setLayout(null);
		
		selectCharacter = new JComboBox<>();
		selectCharacter.setBounds(0, 27, 65, 23);
		selectCharacter.addItem("����");//��ӦJComboBox�±�0
		String[] characters = {"ѧ��","����","�༶"};//ѧ�����ԣ����ڽ���ѡ�񣬶�ӦJComboBox�±�1-3
		String[] character_eng = {"all","number","name","class_name"};//Ӣ�����ԣ����ں�̨���ң���ӦJComboBox�±�0-3
		for(int i=0; i<3; i++)
			selectCharacter.addItem(characters[i]);
		selectCharacter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(selectCharacter.getSelectedIndex()==3) {//��ѡ���������Ϊ�༶��������ֵ�����Ϊ�༶������
					characterValue.setVisible(false);
					selectClass.setVisible(true);
				}
				else {
					characterValue.setVisible(true);
					selectClass.setVisible(false);
				}
			}
		});
		studentOperatePanel.add(selectCharacter);
		
		selectClass = new JComboBox<>();
		String[] classes = { "ʦ��1��", "�ƿ�2��", "�ƿ�3��", "�ƿ�4��", "�ƿ�5��", "����6��", "����7��" };
		for (int i = 0; i < 7; i++)
			selectClass.addItem(classes[i]);
		selectClass.setBounds(70, 27, 90, 21);
		selectClass.setVisible(false);
		studentOperatePanel.add(selectClass);
		
		characterValue = new JTextField();
		characterValue.setBounds(68, 27, 90, 21);
		studentOperatePanel.add(characterValue);
		characterValue.setColumns(10);
		
		searchStudent = new JButton("����");
		searchStudent.setBounds(160, 27, 60, 23);
		searchStudent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int characterIndex = selectCharacter.getSelectedIndex();//��ȡ�������ݵ�������
				String character = character_eng[characterIndex];
				String value;
				if(characterIndex==3)//��ȡ��ȡ�������ݵ�����ֵ����Ϊ�༶���selectClass����ȡֵ
					value = (String)selectClass.getSelectedItem();
				else
					value = characterValue.getText();
				if(value.equals("")&&selectCharacter.getSelectedIndex()!=0) {
					JOptionPane.showMessageDialog(null,"�������������ֵ");
					return;
				}
				showStudents(character, value);
			}
		});
		studentOperatePanel.add(searchStudent);
		
		studentDTM = new DefaultTableModel(){//ѧ�����ģ�壬ͨ����дisCellEditable����ʹ�䲻�ɱ༭
            public boolean isCellEditable(int row, int column){
                return false;
            }
		};
		studentScrollPane = new JScrollPane();
		studentScrollPane.setBounds(40, 90, 600, 335);
		contentPane.add(studentScrollPane);
		studentDTM.setColumnIdentifiers(characters);//���ñ������
		studentTable = new JTable(studentDTM);	
		showStudents("all",null);
		studentScrollPane.setViewportView(studentTable);
		studentScrollPane.setVisible(false);
		
	}
	
	
	//����������ʾͼ�飬attribute:���ԣ�value:����ֵ��mode:����Ա("admin")/�ɽ���("borrowable")/�ѽ���(borrowed)��number:ѧ��
	public void showBooks(String attribute, String value, String mode, String number) {
		bookDTM.setRowCount(0);//��ձ��
		ArrayList<Book> bookResult = UserService.findBooks(attribute, value, mode, number);
		String[] bookItem = new String[5];
		for(int i=0; i<bookResult.size(); i++) {//�������ͼ������
			bookItem[0]=bookResult.get(i).getName();
			bookItem[1]=bookResult.get(i).getAuthor();
			bookItem[2]=bookResult.get(i).getPress();
			bookItem[3]=bookResult.get(i).getCategory();
			bookItem[4]=bookResult.get(i).getReader();
			bookDTM.addRow(bookItem);
		}
	}
	
	public void showStudents(String character, String value) {
		studentDTM.setRowCount(0);//��ձ��
		ArrayList<User> studentResult = UserService.getUsers(character,value);
		String[] studentItem = new String[3];
		for(int i=0; i<studentResult.size(); i++) {//�������ѧ������
			studentItem[0]=studentResult.get(i).getNumber();
			studentItem[1]=studentResult.get(i).getName();
			studentItem[2]=studentResult.get(i).getClass_name();
			studentDTM.addRow(studentItem);
		}
	}
}
