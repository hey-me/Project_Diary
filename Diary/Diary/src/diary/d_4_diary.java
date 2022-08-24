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

		// 오른쪽 상단에 뜨는 선택된 날짜 라벨
		JLabel dayLabel = new JLabel(selectYear + "년 " + (selectMonth + 1) + "월 " + selectDay + "일");
		dayLabel.setFont(new Font("고딕", Font.PLAIN, 15));
		dayLabel.setBounds(540, 10, 150, 30);
		add(dayLabel);

		// 텍스트영역
		JTextArea dta = new JTextArea();
		dta.setBorder(new LineBorder(Color.LIGHT_GRAY));
		dta.setBounds(485, 50, 230, 350);
		dta.setVisible(true);

		// 메모 가져오기
		try {
			InputStream is = new FileInputStream("C:/JavaSF/workspace/Project/다이어리/" + getId() + "," + getPassword()
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

		// 연 콤보박스

		// 년도 2000~
		for (int i = 0; i < yeardd.length; i++) {
			yeardd[i] = "" + (2000 + i);
			yearComboBox.addItem(yeardd[i]);
		}

		// 연 콤보박스 이벤트. 선택한 년도로 변경
		yearComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox<String> cb = (JComboBox<String>) e.getSource();
				selectYear = cb.getSelectedIndex() + 2000;
				dayLabel.setText(selectYear + "년 " + (selectMonth + 1) + "월 " + selectDay + "일");
			}
		});

		// 월 1~12 콤보박스에 넣기
		for (int i = 0; i < monthdd.length; i++) {
			monthdd[i] = i + 1 + "";
			monthComboBox.addItem(monthdd[i]);
		}

		// 달 콤보박스
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
				dayLabel.setText(selectYear + "년 " + (selectMonth + 1) + "월 " + selectDay + "일");

				// 기존 캘린더 패널 지우고
				calendarPanel.removeAll();

				// 새로 선택된 날짜에 맞는 캘린더 만들기
				createCalendar(selectYear, selectMonth);
				add(calendarPanel);
			}
		});

		// 일 콤보박스
		dayComboBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				dta.setText("");
				JComboBox<String> cb = (JComboBox<String>) e.getSource();
				selectDay = cb.getSelectedIndex() + 1;
				dayLabel.setText(selectYear + "년 " + (selectMonth + 1) + "월 " + selectDay + "일");
				try {
					InputStream is = new FileInputStream("C:/JavaSF/workspace/Project/다이어리/" + getId() + ","
							+ getPassword() + "/" + selectYear + "." + (selectMonth + 1) + "." + selectDay + ".txt");
					Reader reader = new InputStreamReader(is);
					BufferedReader br = new BufferedReader(reader);
					String line = "";
					String str = "";

					// 데이터 저장하면 라벨 색상 변경하기
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

		// 툴바
		JToolBar toolBar = new JToolBar("Menu");

		// 오늘 날짜로 돌아가는 버튼
		JButton todayBtn = new JButton("오늘");
		todayBtn.setBounds(180, 30, 63, 23);

		todayBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				index();
			}
		});

		// 내 정보
		JButton myPage = new JButton("내 정보");
		myPage.setBounds(391, 30, 71, 23);

		myPage.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String str5 = "";
				// 내정보
				try {
					InputStream is = new FileInputStream(
							"C:/JavaSF/workspace/Project/다이어리/" + getId() + "," + getPassword() + "/사용자정보.txt");
					Reader reader = new InputStreamReader(is);
					BufferedReader br = new BufferedReader(reader);
					String line = "";
					String str = "";
					while ((line = br.readLine()) != null) {
						str += line + "\r\n";
					}
					reader.close();
					is.close();

					String[] options = { "ID", "PASSWORD", "NAME", "TEL", "탈퇴" };
					String reId = "";
					String rePassword = "";
					String reName = "";
					String reTel = "";

					str5 = str;

					// 내 정보 변경
					int result = JOptionPane.showOptionDialog(calendarPanel, str5 + "\n 정보를 변경하시겠습니까?", "My Page",
							JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

					// id변경
					if (result == 0) {
						boolean isUserID = true;

						while (isUserID) {
							reId = JOptionPane.showInputDialog("아이디 입력", getId());
							File diary = new File("C:/JavaSF/workspace/Project/다이어리/");

							for (String f : diary.list()) {
								String[] str1 = f.split(",");
								f = str1[0];
								// id 기존에 있으면
								if (reId.equals(f)) {
									JOptionPane.showMessageDialog(calendarPanel, "이미 등록된 ID입니다.", "정보 변경",
											JOptionPane.WARNING_MESSAGE);
									isUserID = true;
									break;
								} else {
									isUserID = false;
								}
							}
						}
						
						// id 등록가능
						if (isUserID == false && reId != null) {
							// 기존 사용자 정보 가져오기
							InputStream is1 = new FileInputStream(
									"C:/JavaSF/workspace/Project/다이어리/" + getId() + "," + getPassword() + "/사용자정보.txt");

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

							// 새로운 사용자 폴더로 변경
							File oldfile = new File(
									"C:/JavaSF/workspace/Project/다이어리/" + getId() + "," + getPassword());
							setId(reId);
							File newfile = new File(
									"C:/JavaSF/workspace/Project/다이어리/" + getId() + "," + getPassword());

							// 파일 옮기고 기존파일 삭제
							copyDirectory(oldfile, newfile);
							deleteFile(oldfile);
							oldfile.delete();

							// 바뀐 사용자정보
							userInfo(str);
						}
					}

					// 비밀번호 변경
					if (result == 1) {
						rePassword = JOptionPane.showInputDialog("비밀번호 입력", getPassword());
						if (rePassword != null) {
							InputStream is1 = new FileInputStream(
									"C:/JavaSF/workspace/Project/다이어리/" + getId() + "," + getPassword() + "/사용자정보.txt");
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

							// 새로운 사용자 폴더로 변경
							File oldfile3 = new File(
									"C:/JavaSF/workspace/Project/다이어리/" + getId() + "," + getPassword());
							setPassword(rePassword);
							File newfile3 = new File(
									"C:/JavaSF/workspace/Project/다이어리/" + getId() + "," + getPassword());
							// 파일 옮기고 기존파일 삭제
							copyDirectory(oldfile3, newfile3);
							deleteFile(oldfile3);
							oldfile3.delete();

							// 바뀐 사용자정보
							userInfo(str);
						}
					}
					
					// 이름 변경
					if (result == 2) {
						reName = JOptionPane.showInputDialog("이름 입력", null);
						if (reName != null) {
							InputStream is1 = new FileInputStream(
									"C:/JavaSF/workspace/Project/다이어리/" + getId() + "," + getPassword() + "/사용자정보.txt");

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

							// 바뀐 사용자정보
							userInfo(str);
						}
					}
					
					// 전화번호 변경
					if (result == 3) {
						reTel = JOptionPane.showInputDialog("전화번호 입력", null);
						if (reTel != null) {
							InputStream is1 = new FileInputStream(
									"C:/JavaSF/workspace/Project/다이어리/" + getId() + "," + getPassword() + "/사용자정보.txt");

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

							// 바뀐 사용자정보
							userInfo(str);
						}
					}
					
					//탈퇴 버튼
					if (result == 4) {
						int n = JOptionPane.showOptionDialog(calendarPanel,
								"정말 탈퇴하시겠습니까? \n ( 탈퇴시 모든 정보가 사라지고, 프로그램은 종료됩니다. )", "탈퇴", JOptionPane.YES_NO_OPTION,
								JOptionPane.INFORMATION_MESSAGE, null, null, null);
						// 파일 지우기
						if (n == 0) {
							File oldfile1 = new File(
									"C:/JavaSF/workspace/Project/다이어리/" + getId() + "," + getPassword());
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

		// 문구 텍스트필드
		JTextField today = new JTextField("문구를 입력하세요");
		try {
			InputStream is = new FileInputStream(
					"C:/JavaSF/workspace/Project/다이어리/" + getId() + "," + getPassword() + "/문구.txt");
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

		// 문구 저장
		JButton save = new JButton("저장");
		save.setBounds(391, 30, 71, 23);

		save.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					OutputStream os = new FileOutputStream(
							"C:/JavaSF/workspace/Project/다이어리/" + getId() + "," + getPassword() + "/문구.txt");
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

		// 메모 저장하기 버튼
		JButton tbn = new JButton("저장");
		tbn.setBounds(550, 410, 100, 30);
		tbn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!dta.getText().equals("")) {
					try {
						OutputStream os = new FileOutputStream(
								"C:/JavaSF/workspace/Project/다이어리/" + getId() + "," + getPassword() + "/" + selectYear
										+ "." + (selectMonth + 1) + "." + selectDay + ".txt");
						// 저장시 색 변화
						dayLa[selectDay - 1].setForeground(Color.MAGENTA);
						Writer writer = new OutputStreamWriter(os);
						String memo = dta.getText();
						writer.write(memo);
						writer.flush();
						writer.close();
					} catch (Exception e1) {
					}
				} else {
					// 파일 삭제
					File file = new File("C:/JavaSF/workspace/Project/다이어리/" + getId() + "," + getPassword() + "/"
							+ selectYear + "." + (selectMonth + 1) + "." + selectDay + ".txt");
					if (file.exists()) {
						file.delete();
					}
					colorChange();
				}
			}
		});
		add(tbn);

		// 화면크기
		setSize(new Dimension(750, 500));
		Dimension dimen = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension dimen1 = getSize();
		int xpos = (int) (dimen.getWidth() / 2 - dimen1.getWidth() / 2);
		int ypos = (int) (dimen.getHeight() / 2 - dimen1.getHeight() / 2);

		setLocation(xpos, ypos);
		setVisible(true);
	}

	// 캘린더 화면 만드는 메소드
	void createCalendar(int year, int month) {

		calendarPanel.setBounds(12, 80, 460, 360);
		setmonthCal.set(year, month, 1);
		// 첫번째 요일
		int firstday = setmonthCal.get(Calendar.DAY_OF_WEEK);
		// 며칠까지 있는지
		int lastday = setmonthCal.getActualMaximum(Calendar.DAY_OF_MONTH);

		int num = 42;
		if ((firstday == 6 && lastday == 31) || firstday == 7) {
			calendarPanel.setLayout(new GridLayout(6, 7));
		} else {
			num = 35;
			calendarPanel.setLayout(new GridLayout(5, 7));
		}

		// 1일 시작 전
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

			// 토요일 일요일 색상 지정
			setmonthCal.set(year, month, i + 1);
			int week = setmonthCal.get(Calendar.DAY_OF_WEEK);
			if (week == 1) {
				dayLa[i].setForeground(Color.red);
			} else if (week == 7) {
				dayLa[i].setForeground(Color.blue);
			}

			// 1~30 라벨 선택시
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

		// 색상변경
		colorChange();

		// 이후 남은 칸////
		for (int i = 0; i <= num - lastday - firstday; i++) {
			JLabel la = new JLabel("");
			la.setSize(30, 50);
			la.setBorder(new LineBorder(Color.lightGray));
			calendarPanel.add(la);
		}
		calendarPanel.setVisible(true);
	}

	// 색상변경 메소드
	void colorChange() {
		for (int j = 0; j < dayLa.length; j++) {
			File file = new File("C:/JavaSF/workspace/Project/다이어리/" + getId() + "," + getPassword() + "/" + selectYear
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

	// 월화수목금
	private void createWeekLabel() {
		JPanel weekPa = new JPanel();
		weekPa.setLayout(new GridLayout(1, 7, 0, 0));
		JLabel[] day = new JLabel[7];
		String[] daystr1 = { " 일", " 월", " 화", " 수", " 목", " 금", " 토" };
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

	// 복사할 폴더 만들기
	void copyDirectory(File oldfolder, File newfolder) throws IOException {
		if (!newfolder.exists()) {
			newfolder.mkdir();
		}
		for (String f : oldfolder.list()) {
			copyDirectory2(new File(oldfolder, f), new File(newfolder, f));
		}
	}

	// 폴더면 폴더, 파일이면 파일 복사 호출
	void copyDirectory2(File oldfiles, File newfiles) throws IOException {
		if (oldfiles.isDirectory()) {
			copyDirectory(oldfiles, newfiles);
		} else {
			copyFile(oldfiles, newfiles);
		}
	}

	// 파일 복사
	void copyFile(File oldfile, File newfile) throws IOException {
		try (InputStream in = new FileInputStream(oldfile); OutputStream out = new FileOutputStream(newfile)) {
			byte[] buf = new byte[1024];
			int length;
			while ((length = in.read(buf)) > 0) {
				out.write(buf, 0, length);
			}
		}
	}

	// 파일 삭제
	void deleteFile(File deleteFile) {
		File[] userFiles = deleteFile.listFiles();
		if (userFiles != null) {
			for (File file : userFiles) {
				deleteFile(file);
				file.delete();
			}
		}
	}

	//사용자 정보 변경
	void userInfo(String str) {
		try {
			OutputStream os = new FileOutputStream(
					"C:/JavaSF/workspace/Project/다이어리/" + getId() + "," + getPassword() + "/사용자정보.txt");
			Writer writer = new OutputStreamWriter(os);
			writer.write(str);
			writer.flush();
			writer.close();
		} catch (Exception e) {
		}

	}

	// 오늘 날짜로 콤보박스 변경
	void index() {
		yearComboBox.setSelectedIndex(year - 2000);
		monthComboBox.setSelectedIndex(month);
		dayComboBox.setSelectedIndex(day - 1);
	}

	// 선택한 날짜로 콤보박스 변경
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