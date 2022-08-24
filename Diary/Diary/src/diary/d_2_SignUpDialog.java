package diary;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class d_2_SignUpDialog extends JDialog {

	private String id = "";
	private String password = "";
	private String name = "";
	private String tel = "";

	private JTextField nameTf = new JTextField(15);
	private JTextField idTf = new JTextField(15);
	private JTextField passTf = new JTextField(10);
	private JTextField telTf = new JTextField(10);

	private JLabel nameErr = new JLabel("이름을 입력하세요!");
	private JLabel idErr = new JLabel("아이디를 입력하세요!");
	private JLabel passErr = new JLabel("비밀번호를 입력하세요!");
	private JLabel telErr = new JLabel("전화번호를 입력하세요!");

	public d_2_SignUpDialog(JFrame frame, String title) {
		super(frame, title);
		setSize(new Dimension(380, 380));

		Container c = getContentPane();
		c.setLayout(null);
		// 이름
		JLabel nameLa = new JLabel("NAME");
		nameLa.setFont(new Font("고딕", Font.BOLD, 15));
		nameLa.setBounds(70, 30, 100, 30);
		c.add(nameLa);
		nameTf.setBounds(160, 30, 130, 30);
		c.add(nameTf);

		// 아이디
		JLabel idLa = new JLabel("ID");
		idLa.setFont(new Font("고딕", Font.BOLD, 15));
		idLa.setBounds(70, 80, 100, 30);
		c.add(idLa);

		idTf.setBounds(160, 80, 130, 30);
		c.add(idTf);

		// 비밀번호
		JLabel passLa = new JLabel("PASSWORD");
		passLa.setFont(new Font("고딕", Font.BOLD, 15));
		passLa.setBounds(70, 130, 100, 30);
		c.add(passLa);

		passTf.setBounds(160, 130, 130, 30);
		c.add(passTf);

		// 전화번호
		JLabel telLa = new JLabel("TEL");
		telLa.setFont(new Font("고딕", Font.BOLD, 15));
		telLa.setBounds(70, 180, 100, 30);
		c.add(telLa);

		telTf.setBounds(160, 180, 130, 30);
		c.add(telTf);

		// 빈칸 에러라벨
		nameErr.setFont(new Font("고딕", Font.PLAIN, 11));
		nameErr.setBounds(165, 60, 120, 20);
		add(nameErr);
		nameErr.setVisible(false);
		idErr.setFont(new Font("고딕", Font.PLAIN, 11));
		idErr.setBounds(165, 110, 120, 20);
		add(idErr);
		idErr.setVisible(false);
		passErr.setFont(new Font("고딕", Font.PLAIN, 11));
		passErr.setBounds(165, 160, 120, 20);
		add(passErr);
		passErr.setVisible(false);
		telErr.setFont(new Font("고딕", Font.PLAIN, 11));
		telErr.setBounds(165, 210, 120, 20);
		add(telErr);
		telErr.setVisible(false);

		// 확인버튼
		JButton okBtn = new JButton("확인");
		okBtn.setBounds(130, 230, 110, 30);
		// 빈칸에러
		okBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Container cn = getContentPane();

				// 기존에 등록된 id인지 확인
				boolean is = true; // (true : 등록가능 , false : 등록불가)
				File dairy = new File("C:/JavaSF/workspace/Project/다이어리/");
				for (String f : dairy.list()) {
					String[] str = f.split(",");
					f = str[0];
					// 기존에 있으면
					if (idTf.getText().equals(f)) {
						JOptionPane.showMessageDialog(c, "이미 등록된 ID입니다.", "회원가입", JOptionPane.WARNING_MESSAGE);
						is = false;
						break;
					}
				}
				// 빈칸
				if (nameTf.getText().isEmpty() || idTf.getText().isEmpty() || passTf.getText().isEmpty()
						|| telTf.getText().isEmpty() == true) {
					if (nameTf.getText().isEmpty()) {
						nameErr.setVisible(true);
						cn.repaint();
					} else {
						nameErr.setVisible(false);
					}
					if (idTf.getText().isEmpty()) {
						idErr.setVisible(true);
						cn.repaint();
					} else {
						idErr.setVisible(false);
					}
					if (passTf.getText().isEmpty()) {
						passErr.setVisible(true);
						cn.repaint();
					} else {
						passErr.setVisible(false);
					}
					if (telTf.getText().isEmpty()) {
						telErr.setVisible(true);
						cn.repaint();
					} else {
						telErr.setVisible(false);
					}
				} else {
					// 빈칸 없을때
					if (is == true) {
						signup();
						setVisible(false);
					}
				}

			}
		});
		c.add(okBtn);

		// 취소 버튼
		JButton cancelBtn = new JButton("취소");
		cancelBtn.setBounds(130, 280, 110, 30);
		cancelBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setVisible(false);
			}
		});
		c.add(cancelBtn);

		// 화면 중앙에 위치
		Dimension dimen = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension dimen1 = getSize();
		int xpos = (int) (dimen.getWidth() / 2 - dimen1.getWidth() / 2);
		int ypos = (int) (dimen.getHeight() / 2 - dimen1.getHeight() / 2);
		setLocation(xpos, ypos);
		setVisible(true);
	}

	// 회원가입 메소드
	void signup() {

		name = nameTf.getText().trim();
		id = idTf.getText().trim();
		password = passTf.getText().trim();
		tel = telTf.getText().trim();

		// 파일에 저장
		File user = new File("C:/JavaSF/workspace/Project/다이어리/" + id + "," + password);
		if (!user.exists()) {
			user.mkdirs();
		}
		try {
			OutputStream os = new FileOutputStream(
					"C:/JavaSF/workspace/Project/다이어리/" + id + "," + password + "/사용자정보.txt");
			Writer writer = new OutputStreamWriter(os);

			writer.write("ID : " + id + "\n");
			writer.write("PASSWORD : " + password + "\n");
			writer.write("이름 : " + name + "\n");
			writer.write("Tel : " + tel + "\n");

			writer.flush();
			writer.close();
		} catch (Exception e) {
		}
	}

}
