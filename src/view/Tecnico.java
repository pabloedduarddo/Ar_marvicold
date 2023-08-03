package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import model.DAO;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Tecnico extends JDialog {

	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtNome;
	private JTextField txtID;
	private JButton btnUsuarios;
	private JButton btnBuscar1;
	private JButton btnCreate;
	private JButton btnNewButton;
	private JButton btnNewButton_1;
	private JList listaUsuarios;
	private JScrollPane scrollPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					usuarios dialog = new usuarios();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	public Tecnico() {
		getContentPane().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				scrollPane.setVisible(false);
			}
		});
		setTitle("Técnico");
		setModal(true);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Tecnico.class.getResource("/img/tec_0.png")));
		setBounds(100, 100, 543, 410);
		getContentPane().setLayout(null);
		setLocationRelativeTo(null);

		scrollPane = new JScrollPane();
		scrollPane.setVisible(false);
		scrollPane.setBorder(null);
		scrollPane.setBounds(91, 103, 381, 130);
		getContentPane().add(scrollPane);

		listaUsuarios = new JList();
		listaUsuarios.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscarContatoLista();
			}
		});
		scrollPane.setViewportView(listaUsuarios);
		listaUsuarios.setBorder(null);

		JLabel lblNewLabel = new JLabel("Nome");
		lblNewLabel.setBounds(35, 77, 46, 27);
		getContentPane().add(lblNewLabel);

		txtNome = new JTextField();
		txtNome.setBorder(null);
		txtNome.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				listarUsuarios();
			}
		});
		txtNome.setBounds(91, 77, 381, 27);
		getContentPane().add(txtNome);
		txtNome.setColumns(10);

		JLabel lblNewLabel_4 = new JLabel("ID");
		lblNewLabel_4.setBounds(35, 33, 46, 14);
		getContentPane().add(lblNewLabel_4);

		txtID = new JTextField();
		txtID.setEditable(false);
		txtID.setBounds(91, 30, 86, 20);
		getContentPane().add(txtID);
		txtID.setColumns(10);

		btnCreate = new JButton("");
		btnCreate.setContentAreaFilled(false);
		btnCreate.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				adicionarContato();

			}

		});
		btnCreate.setToolTipText("Adicionar ");
		btnCreate.setBorderPainted(false);
		btnCreate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCreate.setIcon(new ImageIcon(usuarios.class.getResource("/img/add.png")));
		btnCreate.setBounds(103, 262, 74, 68);
		getContentPane().add(btnCreate);

		btnUsuarios = new JButton("");
		btnUsuarios.setContentAreaFilled(false);
		btnUsuarios.setBorderPainted(false);
		btnUsuarios.setBorder(null);
		btnUsuarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparCampos();

			}
		});
		btnUsuarios.setIcon(new ImageIcon(usuarios.class.getResource("/img/boracha.png")));
		btnUsuarios.setBounds(377, 270, 48, 48);
		getContentPane().add(btnUsuarios);

		btnBuscar1 = new JButton("");
		btnBuscar1.setContentAreaFilled(false);
		btnBuscar1.setBorderPainted(false);
		btnBuscar1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarContato();
			}
		});
		btnBuscar1.setBorder(null);
		btnBuscar1.setIcon(new ImageIcon(usuarios.class.getResource("/img/lupa.png")));
		btnBuscar1.setBounds(343, 132, 48, 48);
		getContentPane().add(btnBuscar1);

		getRootPane().setDefaultButton(btnBuscar1);

		btnNewButton = new JButton("");
		btnNewButton.setContentAreaFilled(false);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				}
			
		});
		btnNewButton.setBorder(null);
		btnNewButton.setBorderPainted(false);
		btnNewButton.setIcon(new ImageIcon(usuarios.class.getResource("/img/up.png")));
		btnNewButton.setBounds(206, 270, 48, 48);
		getContentPane().add(btnNewButton);

		btnNewButton_1 = new JButton("");
		btnNewButton_1.setContentAreaFilled(false);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirContato();
			}
		});
		btnNewButton_1.setIcon(new ImageIcon(usuarios.class.getResource("/img/trash(1).png")));
		btnNewButton_1.setBorderPainted(false);
		btnNewButton_1.setBounds(285, 270, 48, 48);
		getContentPane().add(btnNewButton_1);

	}

	private void adicionarContato() {
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "preencha o nome do usuario");
			txtNome.requestFocus();


		}

		else {
			String create = "insert into usuarios(nome,login,senha,perfil) values (?,?,md5(?),?)";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(create);
				pst.setString(1, txtNome.getText());
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Usuário adicionado com sucesso");

				limparCampos();
			} catch (java.sql.SQLIntegrityConstraintViolationException e1) {
				JOptionPane.showMessageDialog(null, "Usuário não adicionado.\nEste login já está sendo utilizado.");
				
			} catch (Exception e2) {
				System.out.println(e2);
			}
		} 
	}
	private void buscarContato() {
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showInternalMessageDialog(null, "Digite o login do usuario");
		} else {

			String read = "select * from usuarios where login = ?";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(read);
				pst.setString(1, txtNome.getText());
				rs = pst.executeQuery();
				if (rs.next()) {
					txtID.setText(rs.getString(1));
					txtNome.setText(rs.getString(2));
					btnCreate.setEnabled(false);

				} else {
					JOptionPane.showMessageDialog(null, "Usuario inexistente");
					btnCreate.setEnabled(true);
					btnBuscar1.setEnabled(false);
				}
				con.close();

			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	private void listarUsuarios() {
		DefaultListModel<String> modelo = new DefaultListModel<>();
		listaUsuarios.setModel(modelo);
		String readLista = "select * from usuarios where nome like '" + txtNome.getText() + "%'" + "order by nome";
		try {
			con = dao.conectar();
			pst = con.prepareStatement(readLista);
			rs = pst.executeQuery();
				while (rs.next()) {
				scrollPane.setVisible(true);
				modelo.addElement(rs.getString(2));
				if (txtNome.getText().isEmpty()) {
					scrollPane.setVisible(false);

				}
			 }
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}

	}
	private void buscarContatoLista() {
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showInternalMessageDialog(null, "Digite o login do usuario");
			txtNome.requestFocus();
		} else {
			int linha = listaUsuarios.getSelectedIndex(); 
			if (linha >= 0) {
 				String read2 = "select * from usuarios where nome like '" + txtNome.getText() + "%'" + "order by nome limit" + (linha) + " , 1";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(read2);
			    rs = pst.executeQuery();
				if (rs.next()) {
				scrollPane.setVisible(false);
					txtID.setText(rs.getString(1));
					txtNome.setText(rs.getString(2));
					btnCreate.setEnabled(false);
					
				} else {
					JOptionPane.showMessageDialog(null, "Usuario inexistente");
					btnCreate.setEnabled(true);
					btnBuscar1.setEnabled(false);
				}
				con.close();

			} catch (Exception e) {
				System.out.println(e);
			}
			} else {
				scrollPane.setVisible(false);
			}
		}
	}
	private void editarContato() {
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o nome");
			txtNome.requestFocus();

		} else {
			String update = "update usuarios set nome = ?, login = ?, perfil = ? where iduser = ?";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(update);
				pst.setString(1, txtNome.getText());
				pst.setString(2, txtID.getText());
				int confirma = pst.executeUpdate();
				System.out.println(confirma);
				JOptionPane.showMessageDialog(null, "Dados alterado com sucesso!");
				con.close();
				limparCampos();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	private void editarUsuario() {
		String update = "update usuarios set nome=?,login=?,senha=? where iduser=?";
		try {
			con = dao.conectar();
			pst = con.prepareStatement(update);
			pst.setString(1, txtNome.getText());
			pst.setString(2, txtID.getText());
			pst.executeUpdate();
			JOptionPane.showMessageDialog(null, "Dados do usuário editados com sucesso");
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void excluirContato() {
		int confirma = JOptionPane.showConfirmDialog(null, "Confirmar a exclusão deste contato?", "ATENÇÃO",
				JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_OPTION) {
			String delete = "delete from usuarios where iduser=?";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(delete);
				pst.setString(1, txtID.getText());
				pst.executeUpdate();
				limparCampos();
				JOptionPane.showMessageDialog(null, "Contato excluído");
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	private void limparCampos() {
		txtID.setText(null);
		txtNome.setText(null);
		scrollPane.setVisible(false);
		btnCreate.setEnabled(true);
		btnBuscar1.setEnabled(true);
	}
}
