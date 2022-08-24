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
	private JLabel la = new JLabel("id�� password�� Ȯ���ϼ���.");
	private JLabel bel = new JLabel("������ �ٽ� Ȯ���ϼ���.");
	private JButton okBn = new JButton("Ȯ��");
	Container c = getContentPane();

	// �α��� ���̾�α�
	public d_3_LoginDialog(JFrame frame, String title) {
		super(frame, title);
		setTitle("�α���");
		c.setLayout(null);

		// Ȯ�ι�ư
		okBn.setBounds(50, 100, 80, 30);
		okBn.setFont(new Font("���", Font.PLAIN, 15));
		c.add(okBn);

		okBn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});

		// ȭ�� ũ��
		setSize(new Dimension(200, 200));
		Dimension dimen = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension dimen1 = getSize();
		int xpos = (int) (dimen.getWidth() / 2 - dimen1.getWidth() / 2);
		int ypos = (int) (dimen.getHeight() / 2 - dimen1.getHeight() / 2);

		setLocation(xpos, ypos);
		setVisible(true);
	}

	// �α��� �޼ҵ�
	String login(String id, String password) {

		String retStr = "";
		if (id == null || password == null) {
			la.setBounds(50, 10, 100, 30);
			la.setFont(new Font("���", Font.PLAIN, 13));
			la.setHorizontalAlignment(JLabel.CENTER);
			c.add(la);
			return "No";

		} else {
			File file = new File("C:/JavaSF/workspace/Project/���̾/" + id + "," + password + "/");
			if (!file.exists()) {
				str = "����";
				bel.setBounds(20, 22, 150, 70);
				bel.setFont(new Font("���", Font.PLAIN, 13));
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
