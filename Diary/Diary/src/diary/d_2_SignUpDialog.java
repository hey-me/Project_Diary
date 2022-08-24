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

	private JLabel nameErr = new JLabel("�̸��� �Է��ϼ���!");
	private JLabel idErr = new JLabel("���̵� �Է��ϼ���!");
	private JLabel passErr = new JLabel("��й�ȣ�� �Է��ϼ���!");
	private JLabel telErr = new JLabel("��ȭ��ȣ�� �Է��ϼ���!");

	public d_2_SignUpDialog(JFrame frame, String title) {
		super(frame, title);
		setSize(new Dimension(380, 380));

		Container c = getContentPane();
		c.setLayout(null);
		// �̸�
		JLabel nameLa = new JLabel("NAME");
		nameLa.setFont(new Font("���", Font.BOLD, 15));
		nameLa.setBounds(70, 30, 100, 30);
		c.add(nameLa);
		nameTf.setBounds(160, 30, 130, 30);
		c.add(nameTf);

		// ���̵�
		JLabel idLa = new JLabel("ID");
		idLa.setFont(new Font("���", Font.BOLD, 15));
		idLa.setBounds(70, 80, 100, 30);
		c.add(idLa);

		idTf.setBounds(160, 80, 130, 30);
		c.add(idTf);

		// ��й�ȣ
		JLabel passLa = new JLabel("PASSWORD");
		passLa.setFont(new Font("���", Font.BOLD, 15));
		passLa.setBounds(70, 130, 100, 30);
		c.add(passLa);

		passTf.setBounds(160, 130, 130, 30);
		c.add(passTf);

		// ��ȭ��ȣ
		JLabel telLa = new JLabel("TEL");
		telLa.setFont(new Font("���", Font.BOLD, 15));
		telLa.setBounds(70, 180, 100, 30);
		c.add(telLa);

		telTf.setBounds(160, 180, 130, 30);
		c.add(telTf);

		// ��ĭ ������
		nameErr.setFont(new Font("���", Font.PLAIN, 11));
		nameErr.setBounds(165, 60, 120, 20);
		add(nameErr);
		nameErr.setVisible(false);
		idErr.setFont(new Font("���", Font.PLAIN, 11));
		idErr.setBounds(165, 110, 120, 20);
		add(idErr);
		idErr.setVisible(false);
		passErr.setFont(new Font("���", Font.PLAIN, 11));
		passErr.setBounds(165, 160, 120, 20);
		add(passErr);
		passErr.setVisible(false);
		telErr.setFont(new Font("���", Font.PLAIN, 11));
		telErr.setBounds(165, 210, 120, 20);
		add(telErr);
		telErr.setVisible(false);

		// Ȯ�ι�ư
		JButton okBtn = new JButton("Ȯ��");
		okBtn.setBounds(130, 230, 110, 30);
		// ��ĭ����
		okBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Container cn = getContentPane();

				// ������ ��ϵ� id���� Ȯ��
				boolean is = true; // (true : ��ϰ��� , false : ��ϺҰ�)
				File dairy = new File("C:/JavaSF/workspace/Project/���̾/");
				for (String f : dairy.list()) {
					String[] str = f.split(",");
					f = str[0];
					// ������ ������
					if (idTf.getText().equals(f)) {
						JOptionPane.showMessageDialog(c, "�̹� ��ϵ� ID�Դϴ�.", "ȸ������", JOptionPane.WARNING_MESSAGE);
						is = false;
						break;
					}
				}
				// ��ĭ
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
					// ��ĭ ������
					if (is == true) {
						signup();
						setVisible(false);
					}
				}

			}
		});
		c.add(okBtn);

		// ��� ��ư
		JButton cancelBtn = new JButton("���");
		cancelBtn.setBounds(130, 280, 110, 30);
		cancelBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setVisible(false);
			}
		});
		c.add(cancelBtn);

		// ȭ�� �߾ӿ� ��ġ
		Dimension dimen = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension dimen1 = getSize();
		int xpos = (int) (dimen.getWidth() / 2 - dimen1.getWidth() / 2);
		int ypos = (int) (dimen.getHeight() / 2 - dimen1.getHeight() / 2);
		setLocation(xpos, ypos);
		setVisible(true);
	}

	// ȸ������ �޼ҵ�
	void signup() {

		name = nameTf.getText().trim();
		id = idTf.getText().trim();
		password = passTf.getText().trim();
		tel = telTf.getText().trim();

		// ���Ͽ� ����
		File user = new File("C:/JavaSF/workspace/Project/���̾/" + id + "," + password);
		if (!user.exists()) {
			user.mkdirs();
		}
		try {
			OutputStream os = new FileOutputStream(
					"C:/JavaSF/workspace/Project/���̾/" + id + "," + password + "/���������.txt");
			Writer writer = new OutputStreamWriter(os);

			writer.write("ID : " + id + "\n");
			writer.write("PASSWORD : " + password + "\n");
			writer.write("�̸� : " + name + "\n");
			writer.write("Tel : " + tel + "\n");

			writer.flush();
			writer.close();
		} catch (Exception e) {
		}
	}

}
