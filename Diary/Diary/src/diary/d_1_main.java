package diary;

import java.awt.*;

import java.awt.event.*;
import java.io.*;

import javax.swing.*;

public class d_1_main extends JFrame {

	private JLabel first = new JLabel("My Diary");
	private JLabel idLa = new JLabel("ID");
	private JTextField idTf = new JTextField(15);
	private JLabel passLa = new JLabel("PASSWORD");
	private JTextField passTf = new JTextField(10);
	private JButton login = new JButton("로그인");
	private JButton signUp = new JButton("회원 가입");
	private JPanel loginPanel = new JPanel();

	public d_1_main() {

		setSize(new Dimension(500, 500));

		// diary 처음 실행할때 뜨는 화면
		setTitle("My Diary");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		loginPanel.setLayout(null);
		first.setFont(new Font("고딕", Font.BOLD, 55));
		first.setSize(250, 80);
		first.setLocation(getWidth() / 2 - first.getWidth() / 2, 50);
		loginPanel.add(first);

		// 아이디 라벨, 텍스트필드
		idLa.setFont(new Font("고딕", Font.PLAIN, 16));
		idLa.setBounds(95, 180, 100, 35);
		loginPanel.add(idLa);

		idTf.setBounds(200, 180, 170, 35);
		loginPanel.add(idTf);

		// 패스워드 라벨, 텍스트필드
		passLa.setFont(new Font("고딕", Font.PLAIN, 16));
		passLa.setBounds(95, 230, 100, 35);
		loginPanel.add(passLa);

		passTf.setBounds(200, 230, 170, 35);
		loginPanel.add(passTf);

		// 로그인 버튼
		login.setBounds(180, 310, 130, 40);
		login.setFont(new Font("고딕", Font.PLAIN, 16));

		login.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				d_3_LoginDialog logindialog = new d_3_LoginDialog(null, "로그인");
				if (logindialog.login(idTf.getText(), passTf.getText()) == "No") {
				} else {

					// 로그인 후 화면
					d_4_diary mPanel = new d_4_diary(idTf.getText(), passTf.getText());
					loginPanel.setVisible(false);
					logindialog.setVisible(false);
					add(mPanel);
					setSize(new Dimension(750, 500));
					mPanel.setVisible(true);
				}
			}
		});

		loginPanel.add(login);

		// 회원가입 버튼
		signUp.setSize(110, 30);
		signUp.setFont(new Font("고딕", Font.PLAIN, 16));
		signUp.setLocation(350, 400);

		// 회원가입 누르면 d_2_SignUpDialog 실행
		signUp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				d_2_SignUpDialog signUpdialog = new d_2_SignUpDialog(null, "회원가입");
				signUpdialog.setVisible(true);
			}
		});

		loginPanel.add(signUp);
		add(loginPanel);

		// 프레임 화면 중간에 위치 시키기
		Dimension dimen = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension dimen1 = getSize();
		int xpos = (int) (dimen.getWidth() / 2 - dimen1.getWidth() / 2);
		int ypos = (int) (dimen.getHeight() / 2 - dimen1.getHeight() / 2);

		setLocation(xpos, ypos);
		setResizable(false); // 화면 크기 조정 못하게
		setVisible(true);

	}

	public static void main(String[] args) throws Exception {
		new d_1_main();
	}

}
