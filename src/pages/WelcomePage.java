package pages;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import service.UserService;
import bean.User;

public class WelcomePage extends JFrame {
	private JTextField number; // ��¼����û�������ѧ��
	private JPasswordField password; // ��¼�������
	private JRadioButton student; // ��¼���ѧ����ѡ��ť
	private JRadioButton admin; // ��¼������Ա��ѡ��ť
	private JTextField regist_number; // ע�����ѧ��
	private JTextField regist_name;// ע���������
	private JPasswordField regist_password; // ע���������
	private JComboBox<String> selectClass; // ע�����ѡ��༶������
	private JPanel registPanel; // ��½���
	private JPanel loginPanel; // ע�����
	Font font = new Font("����", Font.PLAIN, 12);// ����

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WelcomePage frame = new WelcomePage();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public WelcomePage() {

		super("��ӭ����17���ƿư༶ͼ�����ϵͳ");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(550, 150, 450, 320);
		Container c = getContentPane();
		c.setLayout(null);
		ButtonGroup character = new ButtonGroup(); // ��ѡ��ť��
		ImageIcon bookIcon = new ImageIcon("src/icon/book.png"); // ����ͼ��

		registPanel = new JPanel();
		registPanel.setBounds(45, 60, 350, 220);
		c.add(registPanel);
		registPanel.setLayout(null);

		loginPanel = new JPanel();
		loginPanel.setBounds(45, 60, 350, 210);
		c.add(loginPanel);
		loginPanel.setLayout(null);

		JLabel title = new JLabel("ͼ�����ϵͳ", bookIcon, JLabel.RIGHT); // ����
		title.setFont(new Font("����", Font.PLAIN, 30));
		title.setBounds(85, 22, 240, 44);
		c.add(title);

		// ------------------------------------------------ע�����------------------------------------------------

		JPanel registTextPanel = new JPanel(); // ע��������������
		registTextPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		registTextPanel.setBounds(40, 5, 280, 150);
		registPanel.add(registTextPanel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 88, 108, 0, 0 };
		gbl_panel.rowHeights = new int[] { 36, 29, 32, 32, 35, 25, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 1.0, 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		registTextPanel.setLayout(gbl_panel);

		JLabel regist_numberLabel = new JLabel("ѧ�ţ�"); // ѧ�ű�ǩ
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 1;
		registTextPanel.add(regist_numberLabel, gbc_lblNewLabel);

		regist_number = new JTextField(); // ѧ�������
		GridBagConstraints gbc_regist_number = new GridBagConstraints();
		gbc_regist_number.insets = new Insets(0, 0, 5, 5);
		gbc_regist_number.fill = GridBagConstraints.HORIZONTAL;
		gbc_regist_number.gridx = 1;
		gbc_regist_number.gridy = 1;
		registTextPanel.add(regist_number, gbc_regist_number);
		regist_number.setColumns(10);

		JLabel regist_nameLabel = new JLabel("������"); // ������ǩ
		GridBagConstraints gbc_regist_nameLabel = new GridBagConstraints();
		gbc_regist_nameLabel.anchor = GridBagConstraints.EAST;
		gbc_regist_nameLabel.insets = new Insets(0, 0, 5, 5);
		gbc_regist_nameLabel.gridx = 0;
		gbc_regist_nameLabel.gridy = 2;
		registTextPanel.add(regist_nameLabel, gbc_regist_nameLabel);

		regist_name = new JTextField();
		GridBagConstraints gbc_regist_name = new GridBagConstraints(); // ���������
		gbc_regist_name.insets = new Insets(0, 0, 5, 5);
		gbc_regist_name.fill = GridBagConstraints.HORIZONTAL;
		gbc_regist_name.gridx = 1;
		gbc_regist_name.gridy = 2;
		registTextPanel.add(regist_name, gbc_regist_name);
		regist_name.setColumns(10);

		JLabel regist_classLabel = new JLabel("�༶��"); // �༶��ǩ
		GridBagConstraints gbc_regist_classLabel = new GridBagConstraints();
		gbc_regist_classLabel.anchor = GridBagConstraints.EAST;
		gbc_regist_classLabel.insets = new Insets(0, 0, 5, 5);
		gbc_regist_classLabel.gridx = 0;
		gbc_regist_classLabel.gridy = 3;
		registTextPanel.add(regist_classLabel, gbc_regist_classLabel);

		selectClass = new JComboBox<String>(); // ����ѡ��༶
		GridBagConstraints gbc_classSelect = new GridBagConstraints();
		gbc_classSelect.insets = new Insets(0, 0, 5, 5);
		gbc_classSelect.fill = GridBagConstraints.HORIZONTAL;
		gbc_classSelect.gridx = 1;
		gbc_classSelect.gridy = 3;
		String[] classes = { "ʦ��1��", "�ƿ�2��", "�ƿ�3��", "�ƿ�4��", "�ƿ�5��", "����6��", "����7��" };
		for (int i = 0; i < 7; i++)
			selectClass.addItem(classes[i]);
		registTextPanel.add(selectClass, gbc_classSelect);

		JLabel regist_passwordLabel = new JLabel("���룺"); // �����ǩ
		GridBagConstraints gbc_regist_passwordLabel = new GridBagConstraints();
		gbc_regist_passwordLabel.anchor = GridBagConstraints.EAST;
		gbc_regist_passwordLabel.insets = new Insets(0, 0, 5, 5);
		gbc_regist_passwordLabel.gridx = 0;
		gbc_regist_passwordLabel.gridy = 4;
		registTextPanel.add(regist_passwordLabel, gbc_regist_passwordLabel);

		regist_password = new JPasswordField(); // ���������
		GridBagConstraints gbc_regist_password = new GridBagConstraints();
		gbc_regist_password.insets = new Insets(0, 0, 5, 5);
		gbc_regist_password.fill = GridBagConstraints.HORIZONTAL;
		gbc_regist_password.gridx = 1;
		gbc_regist_password.gridy = 4;
		registTextPanel.add(regist_password, gbc_regist_password);

		JButton registNow = new JButton("����ע��"); // ����ע�ᰴť
		registNow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				registActionPerformed(e);
			}
		});
		registNow.setFont(new Font("����", Font.PLAIN, 15));
		registNow.setBounds(120, 160, 120, 30);
		registPanel.add(registNow);

		JLabel returnLogin = new JLabel("���ص�¼ҳ");
		returnLogin.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				registPanel.setVisible(false);
				loginPanel.setVisible(true);
			}
		});
		returnLogin.setBounds(145, 200, 65, 15);
		returnLogin.setForeground(Color.BLUE);
		registPanel.add(returnLogin);

		registPanel.setVisible(false); // ��ʼʱ��Ϊ���ɼ�

		// ------------------------------------------------��¼���------------------------------------------------

		JPanel loginTextPanel = new JPanel(); // ��¼������������
		loginTextPanel.setBounds(40, 25, 280, 80);
		loginPanel.add(loginTextPanel);
		loginTextPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		GridBagLayout gbl_loginTextPanel = new GridBagLayout();
		gbl_loginTextPanel.columnWidths = new int[] { 80, 108, 0, 0 };
		gbl_loginTextPanel.rowHeights = new int[] { 36, 0, 38, 22, 0 };
		gbl_loginTextPanel.columnWeights = new double[] { 0.0, 1.0, 1.0, Double.MIN_VALUE };
		gbl_loginTextPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		loginTextPanel.setLayout(gbl_loginTextPanel);

		JLabel numberLabel = new JLabel("ѧ�ţ�"); // ѧ�����������ǩ
		GridBagConstraints gbc_numberLabel = new GridBagConstraints();
		gbc_numberLabel.insets = new Insets(0, 0, 5, 5);
		gbc_numberLabel.anchor = GridBagConstraints.EAST;
		gbc_numberLabel.gridx = 0;
		gbc_numberLabel.gridy = 1;
		loginTextPanel.add(numberLabel, gbc_numberLabel);

		number = new JTextField(); // ѧ�������
		GridBagConstraints gbc_number = new GridBagConstraints();
		gbc_number.insets = new Insets(0, 0, 5, 5);
		gbc_number.fill = GridBagConstraints.HORIZONTAL;
		gbc_number.gridx = 1;
		gbc_number.gridy = 1;
		loginTextPanel.add(number, gbc_number);
		number.setColumns(10);

		JLabel passwordLabel = new JLabel("���룺"); // �������������ǩ
		GridBagConstraints gbc_passwordLabel = new GridBagConstraints();
		gbc_passwordLabel.insets = new Insets(0, 0, 5, 5);
		gbc_passwordLabel.anchor = GridBagConstraints.EAST;
		gbc_passwordLabel.gridx = 0;
		gbc_passwordLabel.gridy = 2;
		loginTextPanel.add(passwordLabel, gbc_passwordLabel);

		password = new JPasswordField(); // ���������
		GridBagConstraints gbc_password = new GridBagConstraints();
		gbc_password.insets = new Insets(0, 0, 5, 5);
		gbc_password.fill = GridBagConstraints.HORIZONTAL;
		gbc_password.gridx = 1;
		gbc_password.gridy = 2;
		loginTextPanel.add(password, gbc_password);

		JButton regist = new JButton("ע��"); // ע�ᰴť
		regist.setBounds(60, 170, 100, 25);
		loginPanel.add(regist);
		regist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loginPanel.setVisible(false);
				registPanel.setVisible(true);
			}
		});

		JButton login = new JButton("��¼"); // ��¼��ť
		login.setBounds(190, 170, 100, 25);
		loginPanel.add(login);
		login.addActionListener(new ActionListener() { // ��֤�û��������Ƿ���ȷ��Ȼ����е�¼
			public void actionPerformed(ActionEvent e) {
				loginActionPerformed(e);
			}
		});

		JPanel characterPanel = new JPanel(); // ��ѡ��ť���
		characterPanel.setBounds(60, 115, 230, 44);
		loginPanel.add(characterPanel);
		characterPanel.setLayout(new GridLayout(0, 3, 0, 0));

		JLabel characterLabel = new JLabel("�û����ͣ�"); // ѡ���û����ͱ�ǩ
		characterLabel.setFont(font);
		characterLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		characterPanel.add(characterLabel);

		student = new JRadioButton("ѧ��"); // ѧ����ѡ��ť
		student.setFont(font);
		student.setSelected(true);
		student.setHorizontalAlignment(SwingConstants.CENTER);
		characterPanel.add(student);
		character.add(student);

		admin = new JRadioButton("����Ա"); // ����Ա��ѡ��ť
		admin.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) { // ѡ�����Ա����ʱʹע�ᰴť�����ã���ָ̨��һ������Ա��
				if (e.getStateChange() == ItemEvent.SELECTED)
					regist.setEnabled(false);
				else
					regist.setEnabled(true);
			}
		});
		admin.setFont(font);
		admin.setHorizontalAlignment(SwingConstants.CENTER);
		characterPanel.add(admin);
		character.add(admin);

		JLabel gradeLabel = new JLabel("17���ƿ�");
		gradeLabel.setFont(new Font("����", Font.PLAIN, 15));
		gradeLabel.setBounds(190, 10, 70, 15);
		getContentPane().add(gradeLabel);

	}

	// У��ע������������
	WelcomePage page = this;
	public void registActionPerformed(ActionEvent e) {
		String regist_number = this.regist_number.getText();
		String regist_password = new String(this.regist_password.getPassword());
		String regist_class = (String) this.selectClass.getSelectedItem();
		String regist_name = this.regist_name.getText();
		if (regist_number.equals("")) {
			JOptionPane.showMessageDialog(null, "ѧ�Ų���Ϊ��");
			return;
		}
		if (regist_name.equals("")) {
			JOptionPane.showMessageDialog(null, "��������Ϊ��");
			return;
		}
		if (regist_password.equals("")) {
			JOptionPane.showMessageDialog(null, "���벻��Ϊ��");
			return;
		}

		// ע�᷽����
		boolean flag = UserService.regist(regist_number, regist_password, regist_name, regist_class);
		try {
			if (flag) {
				JOptionPane.showMessageDialog(null, "ע��ɹ�");
				registPanel.setVisible(false);
				loginPanel.setVisible(true);
			} else
				JOptionPane.showMessageDialog(null, "�û��Ѵ���");
			page.regist_number.setText("");
			page.regist_password.setText("");
			page.regist_name.setText("");
			page.selectClass.setSelectedIndex(0);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	// У���¼����������
	public void loginActionPerformed(ActionEvent e) {
		String number = this.number.getText();
		String password = new String(this.password.getPassword());
		if (number.equals("")) {
			JOptionPane.showMessageDialog(null, "�û�������Ϊ��");
			return;
		}
		if (password.equals("")) {
			JOptionPane.showMessageDialog(null, "���벻��Ϊ��");
			return;
		}

		// ��¼������
		boolean selectStudent = student.isSelected();
		User user = UserService.login(number, password, selectStudent);
		try {
			if (user != null) {
				if (selectStudent)
					new StudentPage(number).setVisible(true); // ѧ����¼
				else
					new AdminPage(number).setVisible(true); // ����Ա��¼
				dispose(); // �ͷŵ�ǰ������Դ
			} else
				JOptionPane.showMessageDialog(null, "�û������������");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
