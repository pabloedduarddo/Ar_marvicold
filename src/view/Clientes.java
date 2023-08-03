package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import model.DAO;
import util.Validador;

public class Clientes extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	private final JPanel conjunto = new JPanel();
	private JTextField txtNome;
	private JTextField txtCPF;
	private JTextField txtEndereco;
	private JTextField txtCelular;
	private JTextField txtEmail;
	private JButton btnAdicionar;
	private JButton btnDelet;
	private JTextField txtID;
	private JButton btnAtualizar;
	private JButton btnApagar;
	private JLabel lblNome;
	private JLabel lblCPF;
	private JLabel lblEndereco;
	private JLabel lblCelular;
	private JButton btnProcurar;
	private JLabel lblid;
	private JLabel lblEmail;
	private JTextField txtCep;
	private JTextField txtCidade;
	private JTextField txtBairro;
	private JComboBox<?> cboUf;
	private JTextField txtNumero;
	private JLabel lblNewLabel_4;
	private JTextField txtComplemento;
	private JLabel lblNewLabel_5;
	private JList<String> listarUsuarios1;
	private JScrollPane scrollPane;

	public Clientes() {
		getContentPane().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				scrollPane.setVisible(false);
			}
		});
		setTitle("Clientes");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Clientes.class.getResource("/img/client.png")));
		setBounds(100, 100, 662, 417);
		getContentPane().setLayout(new BorderLayout());
		conjunto.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(conjunto, BorderLayout.CENTER);
		conjunto.setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setVisible(false);
		scrollPane.setBorder(null);
		scrollPane.setBounds(89, 62, 203, 82);
		conjunto.add(scrollPane);

		listarUsuarios1 = new JList<String>();
		scrollPane.setViewportView(listarUsuarios1);
		listarUsuarios1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscarLista();
			}
		});
		listarUsuarios1.setBorder(null);

		lblNome = new JLabel("Nome:");
		lblNome.setBounds(23, 47, 46, 14);
		conjunto.add(lblNome);

		lblCPF = new JLabel("CPF:");
		lblCPF.setBounds(23, 69, 46, 14);
		conjunto.add(lblCPF);

		lblEmail = new JLabel("E-mail:");
		lblEmail.setBounds(23, 264, 62, 14);
		conjunto.add(lblEmail);

		lblCelular = new JLabel("Telefone:");
		lblCelular.setBounds(23, 100, 66, 14);
		conjunto.add(lblCelular);

		lblEndereco = new JLabel("Endereço:");
		lblEndereco.setBounds(23, 148, 66, 14);
		conjunto.add(lblEndereco);

		txtNome = new JTextField();
		txtNome.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				listarUsuarios1();
			}
		});
		txtNome.setBounds(89, 41, 203, 20);
		conjunto.add(txtNome);
		txtNome.setColumns(10);

		txtNome.setDocument(new Validador(15));

		txtCPF = new JTextField();
		txtCPF.setBounds(89, 66, 203, 20);
		conjunto.add(txtCPF);
		txtCPF.setColumns(10);

		txtCPF.setDocument(new Validador(12));

		txtEndereco = new JTextField();
		txtEndereco.setBounds(89, 145, 261, 20);
		conjunto.add(txtEndereco);
		txtEndereco.setColumns(10);

		txtEndereco.setDocument(new Validador(30));

		txtCelular = new JTextField();
		txtCelular.setBounds(89, 97, 203, 20);
		conjunto.add(txtCelular);
		txtCelular.setColumns(10);

		txtCelular.setDocument(new Validador(11));

		txtEmail = new JTextField();
		txtEmail.setBounds(89, 261, 237, 20);
		conjunto.add(txtEmail);
		txtEmail.setColumns(10);

		txtEmail.setDocument(new Validador(15));

		btnAdicionar = new JButton("");
		btnAdicionar.setContentAreaFilled(false);
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionarCliente();
			}
		});

		btnAdicionar.setBorder(null);
		btnAdicionar.setBorderPainted(false);
		btnAdicionar.setIcon(new ImageIcon(Clientes.class.getResource("/img/add_1.png")));
		btnAdicionar.setBounds(23, 289, 68, 68);
		conjunto.add(btnAdicionar);

		btnDelet = new JButton("");
		btnDelet.setContentAreaFilled(false);

		btnDelet.setContentAreaFilled(false);
		btnDelet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirContato();
			}
		});
		btnDelet.setBorder(null);
		btnDelet.setIcon(new ImageIcon(Clientes.class.getResource("/img/trash_2.png")));
		btnDelet.setBorderPainted(false);
		btnDelet.setBounds(540, 289, 68, 68);
		conjunto.add(btnDelet);

		btnAtualizar = new JButton("");
		btnAtualizar.setContentAreaFilled(false);
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				editarContato();
			}
		});
		btnAtualizar.setIcon(new ImageIcon(Clientes.class.getResource("/img/update.png")));
		btnAtualizar.setBorderPainted(false);
		btnAtualizar.setBounds(101, 289, 68, 68);
		conjunto.add(btnAtualizar);

		btnApagar = new JButton("");
		btnApagar.setContentAreaFilled(false);
		btnApagar.setBorderPainted(false);
		btnApagar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparCampos();
			}
		});
		btnApagar.setBorder(null);
		btnApagar.setIcon(new ImageIcon(Clientes.class.getResource("/img/boracha_4.png")));
		btnApagar.setBorderPainted(false);
		btnApagar.setBounds(462, 289, 68, 68);
		conjunto.add(btnApagar);

		lblid = new JLabel("ID: ");
		lblid.setBounds(23, 22, 46, 14);
		conjunto.add(lblid);

		txtID = new JTextField();
		txtID.setEditable(false);
		txtID.setBounds(89, 11, 86, 20);
		conjunto.add(txtID);
		txtID.setColumns(10);

		btnProcurar = new JButton("");
		btnProcurar.setContentAreaFilled(false);
		btnProcurar.setBorderPainted(false);
		btnProcurar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarContato();
			}
		});

		btnProcurar.setBorder(null);
		btnProcurar.setIcon(new ImageIcon(Clientes.class.getResource("/img/procurar.png")));
		btnProcurar.setBorderPainted(false);
		btnProcurar.setBounds(302, 22, 48, 48);
		conjunto.add(btnProcurar);

		txtCep = new JTextField();
		txtCep.setBounds(89, 120, 150, 20);
		conjunto.add(txtCep);
		txtCep.setColumns(10);

		JLabel lblNewLabel = new JLabel("CEP:");
		lblNewLabel.setBounds(23, 123, 46, 14);
		conjunto.add(lblNewLabel);

		txtCidade = new JTextField();
		txtCidade.setBounds(89, 176, 203, 20);
		conjunto.add(txtCidade);
		txtCidade.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Cidade:");
		lblNewLabel_1.setBounds(23, 181, 46, 14);
		conjunto.add(lblNewLabel_1);

		txtBairro = new JTextField();
		txtBairro.setBounds(89, 203, 203, 20);
		conjunto.add(txtBairro);
		txtBairro.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("Bairro:");
		lblNewLabel_2.setBounds(23, 206, 46, 14);
		conjunto.add(lblNewLabel_2);

		cboUf = new JComboBox();
		cboUf.setModel(new DefaultComboBoxModel(new String[] { "", "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA",
						"PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO" }));
		cboUf.setToolTipText("");
		cboUf.setBounds(394, 175, 86, 22);
		conjunto.add(cboUf);

		JLabel lblNewLabel_3 = new JLabel("UF:");
		lblNewLabel_3.setBounds(363, 179, 17, 14);
		conjunto.add(lblNewLabel_3);

		JButton btnBuscarCep = new JButton("Buscar CEP");
		btnBuscarCep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarCep();
			}
		});
		btnBuscarCep.setBounds(249, 120, 131, 20);
		conjunto.add(btnBuscarCep);

		txtNumero = new JTextField();
		txtNumero.setBounds(394, 145, 86, 20);
		conjunto.add(txtNumero);
		txtNumero.setColumns(10);

		lblNewLabel_4 = new JLabel("N°");
		lblNewLabel_4.setBounds(370, 148, 14, 14);
		conjunto.add(lblNewLabel_4);

		txtComplemento = new JTextField();
		txtComplemento.setBounds(89, 231, 203, 20);
		conjunto.add(txtComplemento);
		txtComplemento.setColumns(10);

		lblNewLabel_5 = new JLabel("Complemento:");
		lblNewLabel_5.setBounds(5, 234, 84, 14);
		conjunto.add(lblNewLabel_5);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
		}
	}

	private void buscarCep() {
		String logradouro = "";
		String tipoLogradouro = "";
		String cep = txtCep.getText();
		try {
			URL url = new URL("http://cep.republicavirtual.com.br/web_cep.php?cep=" + cep + "&formato=xml");
			SAXReader xml = new SAXReader();
			Document documento = xml.read(url);
			Element root = documento.getRootElement();
			for (Iterator<Element> it = root.elementIterator(); it.hasNext();) {
				Element element = it.next();
				if (element.getQualifiedName().equals("cidade")) {
					txtCidade.setText(element.getText());
				}
				if (element.getQualifiedName().equals("bairro")) {
					txtBairro.setText(element.getText());
				}
				if (element.getQualifiedName().equals("uf")) {
					cboUf.setSelectedItem(element.getText());
				}

				if (element.getQualifiedName().equals("tipo_logradouro")) {
					tipoLogradouro = element.getText();
				}
				if (element.getQualifiedName().equals("logradouro")) {
					logradouro = element.getText();
				}
				if (element.getQualifiedName().equals("resultado")) {
				}
				if (element.getQualifiedName().equals("resultado")) {
				}
			}
			txtEndereco.setText(tipoLogradouro + " " + logradouro);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void adicionarCliente() {
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o nome do cliente");
			txtNome.requestFocus();

		} else if (txtCPF.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o CPF");
			txtCPF.requestFocus();

		} else if (txtCelular.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o número do celular");
			txtCelular.requestFocus();

		} else if (txtEndereco.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o endereço");
			txtEndereco.requestFocus();

		} else if (txtNumero.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o número da casa");
			txtNumero.requestFocus();

		} else if (txtEmail.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o E-mail");
			txtEmail.requestFocus();
		}

		else {
			String create = "insert into Clientes(nome,cpf,celular,endereco,email,bairro,cidade,cep,uf,complemento,numero) values (?,?,?,?,?,?,?,?,?,?,?)";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(create);
				pst.setString(1, txtNome.getText());
				pst.setString(2, txtCPF.getText());
				pst.setString(3, txtCelular.getText());
				pst.setString(4, txtEndereco.getText());
				pst.setString(5, txtEmail.getText());
				pst.setString(6, txtBairro.getText());
				pst.setString(7, txtCidade.getText());
				pst.setString(8, txtCep.getText());
				pst.setString(9, cboUf.getSelectedItem().toString());
				pst.setString(10, txtComplemento.getText());
				pst.setString(11, txtNumero.getText());
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Usuário adicionado com sucesso");
				limparCampos();
			} catch (java.sql.SQLIntegrityConstraintViolationException e1) {
				JOptionPane.showMessageDialog(null, "Usuário não adicionado.\nEste login já está sendo utilizado.");
				txtNome.setText(null);
				txtNome.requestFocus();
			} catch (Exception e2) {
				System.out.println(e2);
			}
		}

	}

	private void buscarContato() {
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showInternalMessageDialog(null, "Digite o nome do cliente");
			txtNome.requestFocus();
		} else {
			String read = "select * from Clientes where nome = ?";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(read);
				pst.setString(1, txtNome.getText());
				ResultSet rs = pst.executeQuery();
				if (rs.next()) {
					txtID.setText(rs.getString(1));
					pst.setString(1, txtID.getText());
					txtCPF.setText(rs.getString(2));
					txtCelular.setText(rs.getString(4));
					txtEndereco.setText(rs.getString(5));
					txtEmail.setText(rs.getString(6));
					txtBairro.setText(rs.getString(7));
					txtCidade.setText(rs.getString(8));
					txtCep.setText(rs.getString(9));
					cboUf.setSelectedItem(rs.getString(10));
					txtComplemento.setText(rs.getString(11));
					txtNumero.setText(rs.getString(12));
					btnAdicionar.setEnabled(false);

				} else {
					JOptionPane.showMessageDialog(null, "Cliente não encontrado!");
					btnAdicionar.setEnabled(true);
					btnProcurar.setEnabled(false);
				}
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	private void listarUsuarios1() {
		DefaultListModel<String> modelo = new DefaultListModel<>();
		listarUsuarios1.setModel(modelo);
		String readLista = "select * from Clientes where nome like '" + txtNome.getText() + "%'" + "order by nome";
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

	private void buscarLista() {
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o nome do usuario");
			txtNome.requestFocus();
		} else {
			int linha = listarUsuarios1.getSelectedIndex();
			if (linha >= 0) {
				String read2 = "select * from Clientes where nome like '" + txtNome.getText() + "%'"
						+ "order by nome limit " + linha + " , 1";

				try {
					con = dao.conectar();
					pst = con.prepareStatement(read2);
					rs = pst.executeQuery();
					if (rs.next()) {
						txtID.setText(rs.getString(1));
						txtNome.setText(rs.getString(2));
						txtCPF.setText(rs.getString(3));
						txtCelular.setText(rs.getString(4));
						txtEndereco.setText(rs.getString(5));
						txtEmail.setText(rs.getString(6));
						txtBairro.setText(rs.getString(7));
						txtCidade.setText(rs.getString(8));
						txtCep.setText(rs.getString(9));
						cboUf.setSelectedItem(rs.getString(10));
						txtComplemento.setText(rs.getString(11));
						txtNumero.setText(rs.getString(12));
						scrollPane.setVisible(false);
						btnAdicionar.setEnabled(false);
						btnProcurar.setEnabled(false);
						btnAtualizar.setEnabled(true);
						btnDelet.setEnabled(true);
					} else {
						JOptionPane.showMessageDialog(null, "Usuario não encontrado");
						btnAdicionar.setEnabled(true);
						btnProcurar.setEnabled(false);
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
		} else if (txtCPF.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o CPF");
			txtCPF.requestFocus();

		} else if (txtCelular.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Celular");
			txtCelular.requestFocus();

		} else if (txtEndereco.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o endereço");
			txtEndereco.requestFocus();

		} else if (txtEmail.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o E-mail");
			txtEmail.requestFocus();

		} else if (txtNumero.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o número");
			txtEmail.requestFocus();

		} else {
			String update = "update Clientes set nome = ?, cpf = ?, endereco = ?, celular=?, email=? where idcli=?";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(update);
				pst.setString(1, txtNome.getText());
				pst.setString(2, txtCPF.getText());
				pst.setString(3, txtEndereco.getText());
				pst.setString(4, txtCelular.getText());
				pst.setString(5, txtEmail.getText());
				pst.setString(6, txtID.getText());
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

	private void excluirContato() {
		int confirma = JOptionPane.showConfirmDialog(null, "Confirmar a exclusão deste contato?", "ATENÇÃO",
				JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_OPTION) {
			String delete = "delete from usuarios where iduser=?";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(delete);
				pst.setString(1, txtNome.getText());
				pst.executeUpdate();
				limparCampos();
				JOptionPane.showMessageDialog(null, "Dados excluídos!");
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	private void limparCampos() {
		txtID.setText(null);
		txtNome.setText(null);
		txtCPF.setText(null);
		txtCelular.setText(null);
		txtEndereco.setText(null);
		txtEmail.setText(null);
		txtBairro.setText(null);
		txtCidade.setText(null);
		txtCep.setText(null);
		cboUf.setSelectedItem(null);
		txtComplemento.setText(null);
		txtNumero.setText(null);
		scrollPane.setVisible(false);
		btnAdicionar.setEnabled(true);
		btnProcurar.setEnabled(true);
	}
}
