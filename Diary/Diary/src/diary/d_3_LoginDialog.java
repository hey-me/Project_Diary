package diary;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

public class d_3_LoginDialog extends JDialog {

	private String name = "";
	private String id = "";
	private String password = "";
	private String tel = "";
	private String str = "";
	private JLabel la = new JLabel("id와 password를 확인하세요.");
	private JLabel bel = new JLabel("정보를 다시 확인하세요.");
	private JButton okBn = new JButton("확인");
	Container c = getContentPane();

	// 로그인 다이얼로그
	public d_3_LoginDialog(JFrame frame, String title) {
		super(frame, title);
		setTitle("로그인");
		c.setLayout(null);

		// 확인버튼
		okBn.setBounds(50, 100, 80, 30);
		okBn.setFont(new Font("고딕", Font.PLAIN, 15));
		c.add(okBn);

		okBn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});

		// 화면 크기
		setSize(new Dimension(200, 200));
		Dimension dimen = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension dimen1 = getSize();
		int xpos = (int) (dimen.getWidth() / 2 - dimen1.getWidth() / 2);
		int ypos = (int) (dimen.getHeight() / 2 - dimen1.getHeight() / 2);

		setLocation(xpos, ypos);
		setVisible(true);
	}

	// 로그인 메소드
	String login(String id, String password) {

		String retStr = "";
		if (id == null || password == null) {
			la.setBounds(50, 10, 100, 30);
			la.setFont(new Font("고딕", Font.PLAIN, 13));
			la.setHorizontalAlignment(JLabel.CENTER);
			c.add(la);
			return "No";

		} else {
			File file = new File("C:/JavaSF/workspace/Project/다이어리/" + id + "," + password + "/");
			if (!file.exists()) {
				str = "없음";
				bel.setBounds(20, 22, 150, 70);
				bel.setFont(new Font("고딕", Font.PLAIN, 13));
				bel.setHorizontalAlignment(JLabel.CENTER);
				c.add(bel);
				return "No";
			} else {
				setId(id);
				setPassword(password);
			}
			return retStr;
		}
	}

	public String getId() {
		return id;
	}

	public String getPassword() {
		return password;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
