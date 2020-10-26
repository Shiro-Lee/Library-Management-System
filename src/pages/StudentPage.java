package pages;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import bean.Book;
import bean.User;
import dao.BookDao;
import service.UserService;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class StudentPage extends JFrame {

	private JPanel contentPane;
	private User user;
	private JTextField attributeValue;//��������attribute��ͼ�����������attributeֵ
	private JTable bookTable;//ͼ��ݱ��
	private JScrollPane bookScrollPane;//����ͼ����Ĺ������
	private JPanel bookOperatePanel;//ͼ��ݹ������
	private DefaultTableModel bookDTM;//ͼ��ݱ��ģ��
	private JButton borrowBook;
	private JButton returnBook;
	private JButton self;//��ʾ�ѽ���ͼ�鰴ť
	private JButton library;//��ʾͼ��ݰ�ť
	
	/**
	 * Create the frame.
	 */
	public StudentPage(String number) {
		
		super("ѧ��ҳ��");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(400, 150, 700, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		ImageIcon studentIcon = new ImageIcon("src/icon/student.png");//�����ǩ����ͼ��
		
		user = UserService.findUser(number, true);
		JLabel studenTitle = new JLabel("��ӭ����"+user.getName()+"ͬѧ", studentIcon, JLabel.RIGHT);//�����ǩ
		studenTitle.setHorizontalAlignment(SwingConstants.CENTER);
		studenTitle.setFont(new Font("����", Font.PLAIN, 30));
		studenTitle.setBounds(180, 10, 322, 44);
		contentPane.add(studenTitle);
		
		JButton exit = new JButton("�˳���¼");
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new WelcomePage().setVisible(true);
			}
		});
		exit.setBounds(40, 10, 90, 23);
		contentPane.add(exit);
		
		
		// ------------------------------------------------ͼ��ݲ���------------------------------------------------
		
		library = new JButton("ͼ���");	//ͼ��ݰ�ť���л���δ���ĵ�ͼ����
		library.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				borrowBook.setVisible(true);//��ʾ���鰴ť
				borrowBook.setEnabled(false);//��ʼʱδѡ���У�ʹ����Ч
				returnBook.setVisible(false);//���ػ��鰴ť
				showBooks("all", null, "borrowable", null);//��ʾ��ǰѧ���ɽ��ĵ�ͼ��
				library.setEnabled(false);//ʹͼ��ݰ�ť������
				self.setEnabled(true);//ʹ�ѽ��İ�ť����
			}
		});
		library.setBounds(40, 62, 90, 23);
		library.setEnabled(false);
		contentPane.add(library);
		
		bookOperatePanel = new JPanel();	//���ͼ�顢ɾ��ͼ�顢����ͼ�鹦�����
		bookOperatePanel.setBounds(230, 40, 410, 45);
		contentPane.add(bookOperatePanel);
		bookOperatePanel.setLayout(null);
		
		attributeValue = new JTextField();//�������ݵ�����ֵ�����
		attributeValue.setBounds(268, 23, 80, 21);
		bookOperatePanel.add(attributeValue);
		attributeValue.setColumns(10);
		
		JComboBox<String> categorySelect = new JComboBox<String>();//ѡ�����
		categorySelect.setBounds(268, 23, 80, 21);
		bookOperatePanel.add(categorySelect);
		String[] categories = {"��ѧ��","����ѧ��","������","������","������",
				"��ѧ��","�������","������","����ѧ��","ҽѧ��","����ѧ��","��ʷ��"};
		for(String item: categories)
			categorySelect.addItem(item);
		categorySelect.setVisible(false);
		
		JComboBox<String> selectAttributes = new JComboBox<String>();//ѡ��������ݵ�����
		selectAttributes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(selectAttributes.getSelectedIndex()==4) {//��ѡ���������Ϊ���������ֵ�����Ϊ���������
					attributeValue.setVisible(false);
					categorySelect.setVisible(true);
				}
				else {
					attributeValue.setVisible(true);
					categorySelect.setVisible(false);
				}
				attributeValue.setText("");
			}
		});
		selectAttributes.setBounds(188, 22, 75, 23);
		bookOperatePanel.add(selectAttributes);
		selectAttributes.addItem("����");//��ӦJComboBox�±�0
		String []attributes = {"����","����","������","���"};//�������ԣ����ڽ���ѡ�񣬶�ӦJComboBox�±�1-4
		for(String attribute: attributes)
			selectAttributes.addItem(attribute);
		String []attributes_eng = {"all","name","author","press","category"};//Ӣ�����ԣ����ں�̨���ң���ӦJComboBox�±�0-4
		
		bookDTM = new DefaultTableModel(){//ͼ����ģ�壬ͨ����дisCellEditable����ʹ�䲻�ɱ༭
            public boolean isCellEditable(int row, int column){
                return false;
            }
		};
		bookDTM.setColumnIdentifiers(attributes);//���ñ������
		bookTable = new JTable(bookDTM);	
		bookTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int selected = bookTable.getSelectedRow();//��ȡѡ�����±�ֵ
				if(selected>=0 && selected<=bookTable.getRowCount()) {
					borrowBook.setEnabled(true);
					returnBook.setEnabled(true);
				}
				else {
					borrowBook.setEnabled(false);
					returnBook.setEnabled(false);
				}
			}
		});
		bookScrollPane = new JScrollPane(bookTable);//���ɱ��Ĺ������
		bookScrollPane.setBounds(40, 90, 600, 335);
		contentPane.add(bookScrollPane);
		showBooks("all", null, "borrowable", null);//��ʾ��ǰѧ���ɽ��ĵ�ͼ��
		
		JButton searchBook = new JButton("����");//ȷ�ϲ��Ұ�ť
		searchBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				returnBook.setEnabled(false);
				borrowBook.setEnabled(false);
				int attributeIndex = selectAttributes.getSelectedIndex();//��ȡ�������ݵ�������
				String attribute = attributes_eng[attributeIndex];
				String value;
				if(attributeIndex==4)//��ȡ��ȡ�������ݵ�����ֵ����Ϊ���������categorySelect����ȡֵ
					value = (String)categorySelect.getSelectedItem();
				else
					value = attributeValue.getText();
				if(value.equals("")&&selectAttributes.getSelectedIndex()!=0) {
					JOptionPane.showMessageDialog(null,"�������������ֵ");
					return;
				}
				String mode = (library.isEnabled()?"borrowed":"borrowable");//����ǰ��ʾ����ͼ��ݱ�����г��ɽ��ĵ�ͼ�飬�����г��ѽ��ĵ�ͼ��
				showBooks(attribute, value, mode, number);
			}
		});
		searchBook.setBounds(350, 22, 60, 23);
		bookOperatePanel.add(searchBook);
		
		borrowBook = new JButton("����");
		borrowBook.setEnabled(false);
		borrowBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selected = bookTable.getSelectedRow();//��ȡѡ�����±�ֵ
				String name = (String)bookTable.getValueAt(selected, 0);//��ȡ����
				int choice = JOptionPane.showConfirmDialog(null, "ȷ��Ҫ���ġ�" + name + "����","����ͼ��",JOptionPane.YES_NO_OPTION);
				if (choice==0) {
					UserService.borrowBook(number, name);
					JOptionPane.showMessageDialog(null,"���ĳɹ�");
					showBooks("all", null, "borrowable", null);//��ʾ��ǰѧ���ɽ��ĵ�ͼ��
				}
			}
		});
		borrowBook.setBounds(82, 22, 97, 23);
		borrowBook.setEnabled(false);
		bookOperatePanel.add(borrowBook);
		
		
		// -----------------------------------------------�����ѽ��Ĳ���------------------------------------------------
		
		self = new JButton("�ҵĽ���");
		self.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				borrowBook.setVisible(false);//���ؽ��鰴ť
				returnBook.setVisible(true);//��ʾ���鰴ť
				returnBook.setEnabled(false);//��ʼʱδѡ���У�ʹ����Ч
				bookDTM.setRowCount(0);//��ձ��
				showBooks("all", null, "borrowed", number);//��ʾ��ǰѧ���ѽ��ĵ�ͼ��
				library.setEnabled(true);//ʹͼ��ݰ�ť����
				self.setEnabled(false);//ʹ�ѽ��İ�ť������
			}
		});
		self.setBounds(130, 62, 90, 23);
		contentPane.add(self);
		
		returnBook = new JButton("����");
		returnBook.setEnabled(false);
		returnBook.setBounds(82, 22, 97, 23);
		returnBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selected = bookTable.getSelectedRow();//��ȡѡ�����±�ֵ
				String name = (String)bookTable.getValueAt(selected, 0);//��ȡ����
				int choice = JOptionPane.showConfirmDialog(null, "ȷ��Ҫ�黹��" + name + "����","�黹ͼ��",JOptionPane.YES_NO_OPTION);
				if (choice==0) {
					BookDao.returnBook(number, name);
					JOptionPane.showMessageDialog(null,"����ɹ�");
					showBooks("all", null, "borrowed", number);//��ʾ��ǰѧ���ɽ��ĵ�ͼ��
				}
			}
		});
		returnBook.setVisible(false);
		bookOperatePanel.add(returnBook);
		
	}
	
	//����������ʾͼ�飬attribute:���ԣ�value:����ֵ��mode:����Ա("admin")/�ɽ���("borrowable")/�ѽ���(borrowed)��number:ѧ��
	void showBooks(String attribute, String value, String mode, String number) {
		bookDTM.setRowCount(0);//��ձ��
		ArrayList<Book> bookResult = UserService.findBooks(attribute, value, mode, number);
		String[] bookItem = new String[4];
		for(int i=0; i<bookResult.size(); i++) {//�������ͼ������
			bookItem[0]=bookResult.get(i).getName();
			bookItem[1]=bookResult.get(i).getAuthor();
			bookItem[2]=bookResult.get(i).getPress();
			bookItem[3]=bookResult.get(i).getCategory();
			bookDTM.addRow(bookItem);
		}
	}
	
}
