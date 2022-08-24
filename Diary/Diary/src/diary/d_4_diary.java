package diary;

import java.awt.*;

import java.awt.event.*;
import java.io.*;
import java.nio.file.*;
import java.text.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class d_4_diary extends JPanel {
	private JLabel[] dayLa = new JLabel[31];

	private String[] yeardd = new String[100];
	private String[] monthdd = new String[12];
	private String[] daydd = new String[31];
	private Calendar now = Calendar.getInstance();
	private int year = now.get(Calendar.YEAR);
	private int month = now.get(Calendar.MONTH);
	private int day = now.get(Calendar.DAY_OF_MONTH);
	private int week = now.get(Calendar.DAY_OF_WEEK);

	private Calendar setmonthCal = Calendar.getInstance();

	private JComboBox<String> yearComboBox = new JComboBox<String>();
	private JComboBox<String> monthComboBox = new JComboBox<String>();
	private JComboBox<String> dayComboBox = new JComboBox<String>();

	private JPanel comboPanel = new JPanel();

	private int selectYear = now.get(Calendar.YEAR);
	private int selectMonth = now.get(Calendar.MONTH);
	private int selectDay = now.get(Calendar.DATE);

	private String id = "";
	private String password = "";

	private JPanel calendarPanel = new JPanel();

	public d_4_diary(String id, String password) {

		setId(id);
		setPassword(password);
		setLayout(null);
		createWeekLabel();

		// ������ ��ܿ� �ߴ� ���õ� ��¥ ��
		JLabel dayLabel = new JLabel(selectYear + "�� " + (selectMonth + 1) + "�� " + selectDay + "��");
		dayLabel.setFont(new Font("���", Font.PLAIN, 15));
		dayLabel.setBounds(540, 10, 150, 30);
		add(dayLabel);

		// �ؽ�Ʈ����
		JTextArea dta = new JTextArea();
		dta.setBorder(new LineBorder(Color.LIGHT_GRAY));
		dta.setBounds(485, 50, 230, 350);
		dta.setVisible(true);

		// �޸� ��������
		try {
			InputStream is = new FileInputStream("C:/JavaSF/workspace/Project/���̾/" + getId() + "," + getPassword()
					+ "/" + selectYear + "." + selectMonth + "." + selectDay + ".txt");
			Reader reader = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(reader);
			String line = "";
			String str = "";
			while ((line = br.readLine()) != null) {
				str += line + "\r\n";
			}
			dta.setText(str);
			reader.close();
			is.close();
		} catch (Exception e1) {
			dta.setText(null);
			e1.getStackTrace();
		}
		add(dta);

		// �� �޺��ڽ�

		// �⵵ 2000~
		for (int i = 0; i < yeardd.length; i++) {
			yeardd[i] = "" + (2000 + i);
			yearComboBox.addItem(yeardd[i]);
		}

		// �� �޺��ڽ� �̺�Ʈ. ������ �⵵�� ����
		yearComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox<String> cb = (JComboBox<String>) e.getSource();
				selectYear = cb.getSelectedIndex() + 2000;
				dayLabel.setText(selectYear + "�� " + (selectMonth + 1) + "�� " + selectDay + "��");
			}
		});

		// �� 1~12 �޺��ڽ��� �ֱ�
		for (int i = 0; i < monthdd.length; i++) {
			monthdd[i] = i + 1 + "";
			monthComboBox.addItem(monthdd[i]);
		}

		// �� �޺��ڽ�
		monthComboBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				JComboBox<String> cb = (JComboBox<String>) e.getSource();
				dayComboBox.removeAllItems();
				selectMonth = cb.getSelectedIndex();
				Calendar setDayCal = Calendar.getInstance();
				setDayCal.set(selectYear, selectMonth, 1);
				int day2 = setDayCal.getActualMaximum(Calendar.DAY_OF_MONTH);

				daydd = new String[day2];
				for (int j = 0; j < daydd.length; j++) {
					daydd[j] = j + 1 + "";
					dayComboBox.addItem(daydd[j]);
				}
				dayLabel.setText(selectYear + "�� " + (selectMonth + 1) + "�� " + selectDay + "��");

				// ���� Ķ���� �г� �����
				calendarPanel.removeAll();

				// ���� ���õ� ��¥�� �´� Ķ���� �����
				createCalendar(selectYear, selectMonth);
				add(calendarPanel);
			}
		});

		// �� �޺��ڽ�
		dayComboBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				dta.setText("");
				JComboBox<String> cb = (JComboBox<String>) e.getSource();
				selectDay = cb.getSelectedIndex() + 1;
				dayLabel.setText(selectYear + "�� " + (selectMonth + 1) + "�� " + selectDay + "��");
				try {
					InputStream is = new FileInputStream("C:/JavaSF/workspace/Project/���̾/" + getId() + ","
							+ getPassword() + "/" + selectYear + "." + (selectMonth + 1) + "." + selectDay + ".txt");
					Reader reader = new InputStreamReader(is);
					BufferedReader br = new BufferedReader(reader);
					String line = "";
					String str = "";

					// ������ �����ϸ� �� ���� �����ϱ�
					dayLa[selectDay - 1].setForeground(Color.MAGENTA);
					while ((line = br.readLine()) != null) {
						str += line + "\r\n";
					}
					dta.setText(str);
					reader.close();
					is.close();
				} catch (Exception e1) {
				}
			}
		});

		index();

		yearComboBox.setBounds(0, 0, 20, 40);
		monthComboBox.setBounds(20, 0, 20, 40);
		dayComboBox.setBounds(40, 0, 20, 40);

		comboPanel.add(yearComboBox);
		comboPanel.add(monthComboBox);
		comboPanel.add(dayComboBox);

		comboPanel.setBounds(0, 0, 30, 50);

		// ����
		JToolBar toolBar = new JToolBar("Menu");

		// ���� ��¥�� ���ư��� ��ư
		JButton todayBtn = new JButton("����");
		todayBtn.setBounds(180, 30, 63, 23);

		todayBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				index();
			}
		});

		// �� ����
		JButton myPage = new JButton("�� ����");
		myPage.setBounds(391, 30, 71, 23);

		myPage.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String str5 = "";
				// ������
				try {
					InputStream is = new FileInputStream(
							"C:/JavaSF/workspace/Project/���̾/" + getId() + "," + getPassword() + "/���������.txt");
					Reader reader = new InputStreamReader(is);
					BufferedReader br = new BufferedReader(reader);
					String line = "";
					String str = "";
					while ((line = br.readLine()) != null) {
						str += line + "\r\n";
					}
					reader.close();
					is.close();

					String[] options = { "ID", "PASSWORD", "NAME", "TEL", "Ż��" };
					String reId = "";
					String rePassword = "";
					String reName = "";
					String reTel = "";

					str5 = str;

					// �� ���� ����
					int result = JOptionPane.showOptionDialog(calendarPanel, str5 + "\n ������ �����Ͻðڽ��ϱ�?", "My Page",
							JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

					// id����
					if (result == 0) {
						boolean isUserID = true;

						while (isUserID) {
							reId = JOptionPane.showInputDialog("���̵� �Է�", getId());
							File diary = new File("C:/JavaSF/workspace/Project/���̾/");

							for (String f : diary.list()) {
								String[] str1 = f.split(",");
								f = str1[0];
								// id ������ ������
								if (reId.equals(f)) {
									JOptionPane.showMessageDialog(calendarPanel, "�̹� ��ϵ� ID�Դϴ�.", "���� ����",
											JOptionPane.WARNING_MESSAGE);
									isUserID = true;
									break;
								} else {
									isUserID = false;
								}
							}
						}
						
						// id ��ϰ���
						if (isUserID == false && reId != null) {
							// ���� ����� ���� ��������
							InputStream is1 = new FileInputStream(
									"C:/JavaSF/workspace/Project/���̾/" + getId() + "," + getPassword() + "/���������.txt");

							Reader reader1 = new InputStreamReader(is1);
							BufferedReader br1 = new BufferedReader(reader1);
							line = "";
							str = "ID : " + reId + "\n";
							for (int i = 0; i < 4; i++) {
								line = br1.readLine();
								if (i > 0) {
									str += line + "\n";
								}
							}
							reader1.close();
							is1.close();

							// ���ο� ����� ������ ����
							File oldfile = new File(
									"C:/JavaSF/workspace/Project/���̾/" + getId() + "," + getPassword());
							setId(reId);
							File newfile = new File(
									"C:/JavaSF/workspace/Project/���̾/" + getId() + "," + getPassword());

							// ���� �ű�� �������� ����
							copyDirectory(oldfile, newfile);
							deleteFile(oldfile);
							oldfile.delete();

							// �ٲ� ���������
							userInfo(str);
						}
					}

					// ��й�ȣ ����
					if (result == 1) {
						rePassword = JOptionPane.showInputDialog("��й�ȣ �Է�", getPassword());
						if (rePassword != null) {
							InputStream is1 = new FileInputStream(
									"C:/JavaSF/workspace/Project/���̾/" + getId() + "," + getPassword() + "/���������.txt");
							Reader reader1 = new InputStreamReader(is1);
							BufferedReader br1 = new BufferedReader(reader1);
							line = "";
							str = "PASSWORD : " + rePassword + "\n";
							for (int i = 0; i < 4; i++) {
								line = br1.readLine();
								if (i == 0) {
									str = line + "\n" + str;
								}
								if (i > 1) {
									str += line + "\n";
								}
							}
							reader1.close();
							is1.close();

							// ���ο� ����� ������ ����
							File oldfile3 = new File(
									"C:/JavaSF/workspace/Project/���̾/" + getId() + "," + getPassword());
							setPassword(rePassword);
							File newfile3 = new File(
									"C:/JavaSF/workspace/Project/���̾/" + getId() + "," + getPassword());
							// ���� �ű�� �������� ����
							copyDirectory(oldfile3, newfile3);
							deleteFile(oldfile3);
							oldfile3.delete();

							// �ٲ� ���������
							userInfo(str);
						}
					}
					
					// �̸� ����
					if (result == 2) {
						reName = JOptionPane.showInputDialog("�̸� �Է�", null);
						if (reName != null) {
							InputStream is1 = new FileInputStream(
									"C:/JavaSF/workspace/Project/���̾/" + getId() + "," + getPassword() + "/���������.txt");

							Reader reader1 = new InputStreamReader(is1);
							BufferedReader br1 = new BufferedReader(reader1);
							line = "";
							String reStr = "";
							str = "NAME : " + reName + "\n";
							for (int i = 0; i < 4; i++) {
								line = br1.readLine();
								if (i < 2) {
									reStr += line + "\n";
								}
								if (i == 2) {
									str = reStr + str;
								}
								if (i > 2) {
									str += line + "\n";
								}
							}
							reader1.close();
							is1.close();

							// �ٲ� ���������
							userInfo(str);
						}
					}
					
					// ��ȭ��ȣ ����
					if (result == 3) {
						reTel = JOptionPane.showInputDialog("��ȭ��ȣ �Է�", null);
						if (reTel != null) {
							InputStream is1 = new FileInputStream(
									"C:/JavaSF/workspace/Project/���̾/" + getId() + "," + getPassword() + "/���������.txt");

							Reader reader1 = new InputStreamReader(is1);
							BufferedReader br1 = new BufferedReader(reader1);
							line = "";
							String reStr = "";
							str = "TEL : " + reTel + "\n";
							for (int i = 0; i < 4; i++) {
								line = br1.readLine();
								if (i < 3) {
									reStr += line + "\n";
								} else {
									str = reStr + str;
								}
							}
							reader1.close();
							is1.close();

							// �ٲ� ���������
							userInfo(str);
						}
					}
					
					//Ż�� ��ư
					if (result == 4) {
						int n = JOptionPane.showOptionDialog(calendarPanel,
								"���� Ż���Ͻðڽ��ϱ�? \n ( Ż��� ��� ������ �������, ���α׷��� ����˴ϴ�. )", "Ż��", JOptionPane.YES_NO_OPTION,
								JOptionPane.INFORMATION_MESSAGE, null, null, null);
						// ���� �����
						if (n == 0) {
							File oldfile1 = new File(
									"C:/JavaSF/workspace/Project/���̾/" + getId() + "," + getPassword());
							deleteFile(oldfile1);
							oldfile1.delete();
							System.exit(0);
						}
					}
				} catch (Exception e1) {
				}
			}
		});

		toolBar.add(comboPanel);
		toolBar.addSeparator();

		// ���� �ؽ�Ʈ�ʵ�
		JTextField today = new JTextField("������ �Է��ϼ���");
		try {
			InputStream is = new FileInputStream(
					"C:/JavaSF/workspace/Project/���̾/" + getId() + "," + getPassword() + "/����.txt");
			Reader reader = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(reader);
			String line = "";
			String str = "";
			while ((line = br.readLine()) != null) {
				str += line;
			}
			today.setText(str);
			reader.close();
			is.close();
		} catch (Exception e1) {
		}

		// ���� ����
		JButton save = new JButton("����");
		save.setBounds(391, 30, 71, 23);

		save.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					OutputStream os = new FileOutputStream(
							"C:/JavaSF/workspace/Project/���̾/" + getId() + "," + getPassword() + "/����.txt");
					Writer writer = new OutputStreamWriter(os);
					String memo = today.getText();
					writer.write(memo);
					writer.flush();
					writer.close();
				} catch (Exception e1) {
				}
			}
		});

		toolBar.add(todayBtn);
		toolBar.addSeparator();
		toolBar.add(today);
		toolBar.addSeparator();
		toolBar.add(save);
		toolBar.addSeparator();
		toolBar.add(myPage);
		toolBar.setBounds(0, 0, 470, 40);
		add(toolBar);

		// �޸� �����ϱ� ��ư
		JButton tbn = new JButton("����");
		tbn.setBounds(550, 410, 100, 30);
		tbn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!dta.getText().equals("")) {
					try {
						OutputStream os = new FileOutputStream(
								"C:/JavaSF/workspace/Project/���̾/" + getId() + "," + getPassword() + "/" + selectYear
										+ "." + (selectMonth + 1) + "." + selectDay + ".txt");
						// ����� �� ��ȭ
						dayLa[selectDay - 1].setForeground(Color.MAGENTA);
						Writer writer = new OutputStreamWriter(os);
						String memo = dta.getText();
						writer.write(memo);
						writer.flush();
						writer.close();
					} catch (Exception e1) {
					}
				} else {
					// ���� ����
					File file = new File("C:/JavaSF/workspace/Project/���̾/" + getId() + "," + getPassword() + "/"
							+ selectYear + "." + (selectMonth + 1) + "." + selectDay + ".txt");
					if (file.exists()) {
						file.delete();
					}
					colorChange();
				}
			}
		});
		add(tbn);

		// ȭ��ũ��
		setSize(new Dimension(750, 500));
		Dimension dimen = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension dimen1 = getSize();
		int xpos = (int) (dimen.getWidth() / 2 - dimen1.getWidth() / 2);
		int ypos = (int) (dimen.getHeight() / 2 - dimen1.getHeight() / 2);

		setLocation(xpos, ypos);
		setVisible(true);
	}

	// Ķ���� ȭ�� ����� �޼ҵ�
	void createCalendar(int year, int month) {

		calendarPanel.setBounds(12, 80, 460, 360);
		setmonthCal.set(year, month, 1);
		// ù��° ����
		int firstday = setmonthCal.get(Calendar.DAY_OF_WEEK);
		// ��ĥ���� �ִ���
		int lastday = setmonthCal.getActualMaximum(Calendar.DAY_OF_MONTH);

		int num = 42;
		if ((firstday == 6 && lastday == 31) || firstday == 7) {
			calendarPanel.setLayout(new GridLayout(6, 7));
		} else {
			num = 35;
			calendarPanel.setLayout(new GridLayout(5, 7));
		}

		// 1�� ���� ��
		for (int i = 0; i < firstday - 1; i++) {
			JLabel la = new JLabel("");
			la.setSize(30, 50);
			la.setBorder(new LineBorder(Color.lightGray));
			calendarPanel.add(la);
		}
		// 1~30
		for (int i = 0; i < lastday; i++) {
			dayLa[i] = new JLabel("  " + (i + 1));
			dayLa[i].setBorder(new LineBorder(Color.lightGray));
			dayLa[i].setVerticalAlignment(JLabel.NORTH);
			dayLa[i].setSize(30, 50);

			// ����� �Ͽ��� ���� ����
			setmonthCal.set(year, month, i + 1);
			int week = setmonthCal.get(Calendar.DAY_OF_WEEK);
			if (week == 1) {
				dayLa[i].setForeground(Color.red);
			} else if (week == 7) {
				dayLa[i].setForeground(Color.blue);
			}

			// 1~30 �� ���ý�
			dayLa[i].addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					JLabel label = (JLabel) e.getSource();
					selectDay = Integer.parseInt(label.getText().trim());
					changeIndex();
				}
			});
			calendarPanel.add(dayLa[i]);
		}

		// ���󺯰�
		colorChange();

		// ���� ���� ĭ////
		for (int i = 0; i <= num - lastday - firstday; i++) {
			JLabel la = new JLabel("");
			la.setSize(30, 50);
			la.setBorder(new LineBorder(Color.lightGray));
			calendarPanel.add(la);
		}
		calendarPanel.setVisible(true);
	}

	// ���󺯰� �޼ҵ�
	void colorChange() {
		for (int j = 0; j < dayLa.length; j++) {
			File file = new File("C:/JavaSF/workspace/Project/���̾/" + getId() + "," + getPassword() + "/" + selectYear
					+ "." + (selectMonth + 1) + "." + (j + 1) + ".txt");
			if (file.exists()) {
				dayLa[j].setForeground(Color.MAGENTA);
			} else {
				setmonthCal.set(selectYear, selectMonth, selectDay);
				int week = setmonthCal.get(Calendar.DAY_OF_WEEK);
				if (week == 1) {
					dayLa[selectDay - 1].setForeground(Color.red);
				} else if (week == 7) {
					dayLa[selectDay - 1].setForeground(Color.blue);
				} else {
					dayLa[selectDay - 1].setForeground(Color.black);
				}
			}
		}
	}

	// ��ȭ�����
	private void createWeekLabel() {
		JPanel weekPa = new JPanel();
		weekPa.setLayout(new GridLayout(1, 7, 0, 0));
		JLabel[] day = new JLabel[7];
		String[] daystr1 = { " ��", " ��", " ȭ", " ��", " ��", " ��", " ��" };
		for (int i = 0; i < daystr1.length; i++) {
			day[i] = new JLabel(daystr1[i]);
			if (i == 0)
				day[i].setForeground(Color.red);
			if (i == 6)
				day[i].setForeground(Color.blue);
			day[i].setBorder(new LineBorder(Color.LIGHT_GRAY));
			weekPa.add(day[i]);
		}
		weekPa.setBounds(12, 50, 460, 30);
		add(weekPa);
	}

	// ������ ���� �����
	void copyDirectory(File oldfolder, File newfolder) throws IOException {
		if (!newfolder.exists()) {
			newfolder.mkdir();
		}
		for (String f : oldfolder.list()) {
			copyDirectory2(new File(oldfolder, f), new File(newfolder, f));
		}
	}

	// ������ ����, �����̸� ���� ���� ȣ��
	void copyDirectory2(File oldfiles, File newfiles) throws IOException {
		if (oldfiles.isDirectory()) {
			copyDirectory(oldfiles, newfiles);
		} else {
			copyFile(oldfiles, newfiles);
		}
	}

	// ���� ����
	void copyFile(File oldfile, File newfile) throws IOException {
		try (InputStream in = new FileInputStream(oldfile); OutputStream out = new FileOutputStream(newfile)) {
			byte[] buf = new byte[1024];
			int length;
			while ((length = in.read(buf)) > 0) {
				out.write(buf, 0, length);
			}
		}
	}

	// ���� ����
	void deleteFile(File deleteFile) {
		File[] userFiles = deleteFile.listFiles();
		if (userFiles != null) {
			for (File file : userFiles) {
				deleteFile(file);
				file.delete();
			}
		}
	}

	//����� ���� ����
	void userInfo(String str) {
		try {
			OutputStream os = new FileOutputStream(
					"C:/JavaSF/workspace/Project/���̾/" + getId() + "," + getPassword() + "/���������.txt");
			Writer writer = new OutputStreamWriter(os);
			writer.write(str);
			writer.flush();
			writer.close();
		} catch (Exception e) {
		}

	}

	// ���� ��¥�� �޺��ڽ� ����
	void index() {
		yearComboBox.setSelectedIndex(year - 2000);
		monthComboBox.setSelectedIndex(month);
		dayComboBox.setSelectedIndex(day - 1);
	}

	// ������ ��¥�� �޺��ڽ� ����
	void changeIndex() {
		yearComboBox.setSelectedIndex(selectYear - 2000);
		monthComboBox.setSelectedIndex(selectMonth);
		dayComboBox.setSelectedIndex(selectDay - 1);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}