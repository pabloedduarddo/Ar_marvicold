package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import model.DAO;
import util.Validador;

public class Login extends JFrame {
	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblStatus;
	private JLabel lblStatus2;
	private JTextField txtLogin;
	private JPasswordField txtSenha;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Login() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				status();
			}
		});
		setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/img/ar.png")));
		setTitle("Último Mestre do Ar");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		JButton btnLogin = new JButton("Acessar");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logar();
			}
		});
		btnLogin.setBounds(315, 216, 97, 23);
		contentPane.add(btnLogin);

		lblStatus2 = new JLabel("");
		lblStatus2.setIcon(new ImageIcon(Login.class.getResource("/img/dboff1.png")));
		lblStatus2.setBounds(36, 191, 48, 48);
		contentPane.add(lblStatus2);

		JLabel lblNewLabel = new JLabel("Login");
		lblNewLabel.setBounds(38, 39, 46, 14);
		contentPane.add(lblNewLabel);

		txtLogin = new JTextField();
		txtLogin.setBounds(89, 36, 226, 20);
		contentPane.add(txtLogin);
		txtLogin.setColumns(10);
		txtLogin.setDocument(new Validador(20));

		JLabel lblNewLabel_1 = new JLabel("Senha");
		lblNewLabel_1.setBounds(36, 83, 46, 14);
		contentPane.add(lblNewLabel_1);

		txtSenha = new JPasswordField();
		txtSenha.setBounds(89, 80, 226, 20);
		contentPane.add(txtSenha);
		txtSenha.setDocument(new Validador(250));
		getRootPane().setDefaultButton(btnLogin);

	}

	/**
	 * Método para verificar o status de conexão
	 */
	private void status() {
		try {
			con = dao.conectar();
			if (con == null) {
				System.out.println("erro");
				lblStatus2.setIcon(new ImageIcon(Login.class.getResource("/img/dboff1.png")));
			} else {
				lblStatus2.setIcon(new ImageIcon(Login.class.getResource("/img/dbon11.png")));
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * Método para autenticar um usuário
	 */
	private void logar() {
		String capturaSenha = new String(txtSenha.getPassword());
		if (txtLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Informe o login");
			txtLogin.requestFocus();
		} else if (capturaSenha.length() == 0) {
			JOptionPane.showMessageDialog(null, "Digite a senha");
			txtSenha.requestFocus();
		} else {
			String read = "select * from usuarios where login = ? and senha = md5(?)";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(read);
				pst.setString(1, txtLogin.getText());
				pst.setString(2, capturaSenha);
				rs = pst.executeQuery();
				if (rs.next()) {
					String perfil = rs.getString(5);
					if (perfil.equals("admin")) {
						System.out.println("admin logado");
						Principal principal = new Principal();
						principal.btnRelatorio.setEnabled(true);
						principal.btnUsuarios2.setEnabled(true);
						principal.btnServico.setEnabled(true);
						principal.panelRodape.setBackground(Color.RED);
						principal.setVisible(true);
						principal.lblUsuario.setText(rs.getString(2));
						this.dispose();
					} else {
						System.out.println("user logado");
						Principal principal = new Principal();
						principal.setVisible(true);
						principal.lblUsuario.setText(rs.getString(2));
						this.dispose();
					}
				} else {
					JOptionPane.showMessageDialog(null, "Usuário e/ou Senha inválida(s)");
				}
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	public JPanel getContentPane() {
		return contentPane;
	}

	public void setContentPane(JPanel contentPane) {
		this.contentPane = contentPane;
	}

	public JLabel getLblStatus() {
		return lblStatus;
	}

	public void setLblStatus(JLabel lblStatus) {
		this.lblStatus = lblStatus;
	}

}
