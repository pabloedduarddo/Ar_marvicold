package view;

import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.toedter.calendar.JDateChooser;

import model.DAO;
import util.Validador;

public class Servicos extends JDialog {

	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtModelo;
	private JTextField txtOBS;
	private JTextField txtID;
	private JButton btnUsuarios;
	private JButton btnCreate;
	private JButton btnEdicao;
	private JButton btnDelet;
	private JLabel lblTec;
	private JLabel lblInfo;
	private JTextField txtInfo;
	private JLabel lblEncontrado;
	private JTextField txtEncontrado;
	private JLabel lblObs;
	private JLabel lblModelo;
	private JLabel lblOrcamento;
	private JTextField txtTecnico;
	private JLabel lblNewLabel_1;
	private JComboBox cbmStatus;
	private JTextField txtCliente;
	private JButton btnBuscar;
	private JScrollPane scrollPane;
	private JDateChooser txtData;
	private JDateChooser txtSaida;
	private JButton btnbuscarOS;
	private JTextField txtId;
	private JLabel lblNewLabel;
	private JButton btnBuscar_1;
	private JTextField txtUtilizados;

	public String usuario;
	private JTextField txtOS;
	private JComboBox cbmTipo;
	private JScrollPane scrollPane1;
	private JList listaUsuarios1;
	private JList listaUsuarios;
	private JTextField txtOrcamento;
	private JTextField txtLogado;
	private JButton btnImprimir;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Servicos dialog = new Servicos();
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
	public Servicos() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
			}
		});
		getContentPane().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		setTitle("Serviços");
		setModal(true);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Servicos.class.getResource("/img/tools.png")));
		setBounds(100, 100, 850, 431);
		getContentPane().setLayout(null);

		setLocationRelativeTo(null);

		txtCliente = new JTextField();
		txtCliente.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				listarClientes();
			}
		});

		scrollPane = new JScrollPane();
		scrollPane.setVisible(false);
		scrollPane.setBorder(null);
		scrollPane.setBounds(106, 49, 152, 46);
		getContentPane().add(scrollPane);

		listaUsuarios = new JList();
		listaUsuarios.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscarClientesLista();
			}
		});
		scrollPane.setViewportView(listaUsuarios);

		scrollPane1 = new JScrollPane();
		scrollPane1.setVisible(false);
		scrollPane1.setBounds(510, 45, 169, 46);
		getContentPane().add(scrollPane1);

		listaUsuarios1 = new JList();
		scrollPane1.setViewportView(listaUsuarios1);
		listaUsuarios1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscarTecnicosLista();
			}
		});
		txtCliente.setBounds(106, 27, 152, 20);
		getContentPane().add(txtCliente);
		txtCliente.setColumns(10);

		txtCliente.setDocument(new Validador(15));

		txtID = new JTextField();
		txtID.setBounds(116, 52, 76, 20);
		getContentPane().add(txtID);
		txtID.setColumns(10);

		txtID.setDocument(new Validador(2));

		lblModelo = new JLabel("Modelo");
		lblModelo.setBounds(19, 130, 46, 27);
		getContentPane().add(lblModelo);

		lblObs = new JLabel("Observação");
		lblObs.setBounds(389, 162, 85, 14);
		getContentPane().add(lblObs);

		JLabel lblNewLabel_2 = new JLabel("Atendimento");
		lblNewLabel_2.setBounds(471, 117, 76, 14);
		getContentPane().add(lblNewLabel_2);

		txtModelo = new JTextField();
		txtModelo.setBorder(null);
		txtModelo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
			}
		});
		txtModelo.setBounds(91, 130, 242, 27);
		getContentPane().add(txtModelo);
		txtModelo.setColumns(10);
		txtModelo.setDocument(new Validador(50));

		txtOBS = new JTextField();
		txtOBS.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		txtOBS.setBounds(484, 156, 242, 44);
		getContentPane().add(txtOBS);
		txtOBS.setColumns(10);

		txtOBS.setDocument(new Validador(200));

		JLabel lblID = new JLabel("ID");
		lblID.setBounds(62, 55, 38, 14);
		getContentPane().add(lblID);

		btnCreate = new JButton("");
		btnCreate.setContentAreaFilled(false);
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionarOS();
			}

		});
		btnCreate.setToolTipText("Adicionar ");
		btnCreate.setBorderPainted(false);
		btnCreate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCreate.setIcon(new ImageIcon(Servicos.class.getResource("/img/add.png")));
		btnCreate.setBounds(29, 313, 74, 68);
		getContentPane().add(btnCreate);

		btnUsuarios = new JButton("");
		btnUsuarios.setToolTipText("Limpar Campos");
		btnUsuarios.setContentAreaFilled(false);
		btnUsuarios.setBorderPainted(false);
		btnUsuarios.setBorder(null);
		btnUsuarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparCampos();

			}
		});
		btnUsuarios.setIcon(new ImageIcon(Servicos.class.getResource("/img/boracha.png")));
		btnUsuarios.setBounds(526, 323, 48, 48);
		getContentPane().add(btnUsuarios);

		btnEdicao = new JButton("");
		btnEdicao.setToolTipText("Atualizar Dados");
		btnEdicao.setContentAreaFilled(false);
		btnEdicao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editOS();
			}
		});
		btnEdicao.setBorder(null);
		btnEdicao.setBorderPainted(false);
		btnEdicao.setIcon(new ImageIcon(Servicos.class.getResource("/img/update.png")));
		btnEdicao.setBounds(177, 323, 48, 48);
		getContentPane().add(btnEdicao);

		btnDelet = new JButton("");
		btnDelet.setToolTipText("Apagar Dados");
		btnDelet.setContentAreaFilled(false);
		btnDelet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirOS();
			}
		});
		btnDelet.setIcon(new ImageIcon(Servicos.class.getResource("/img/trash(1).png")));
		btnDelet.setBorderPainted(false);
		btnDelet.setBounds(106, 323, 48, 48);
		getContentPane().add(btnDelet);

		lblTec = new JLabel("Técnico");
		lblTec.setBounds(454, 30, 46, 14);
		getContentPane().add(lblTec);

		lblInfo = new JLabel("Informado");
		lblInfo.setBounds(19, 186, 66, 14);
		getContentPane().add(lblInfo);

		txtInfo = new JTextField();
		txtInfo.setColumns(10);
		txtInfo.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		txtInfo.setBounds(91, 168, 242, 54);
		getContentPane().add(txtInfo);

		txtInfo.setDocument(new Validador(200));

		lblEncontrado = new JLabel("Encontrado");
		lblEncontrado.setBounds(393, 217, 81, 14);
		getContentPane().add(lblEncontrado);

		txtEncontrado = new JTextField();
		txtEncontrado.setColumns(10);
		txtEncontrado.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		txtEncontrado.setBounds(484, 211, 242, 49);
		getContentPane().add(txtEncontrado);

		txtEncontrado.setDocument(new Validador(200));

		cbmTipo = new JComboBox();
		cbmTipo.setModel(new DefaultComboBoxModel(new String[] { "", "Elétrico", "Hidráulico", "Instalação",
				"Preventiva", "Estrutura Predial", "Outros...." }));
		cbmTipo.setBounds(557, 113, 169, 22);
		getContentPane().add(cbmTipo);

		JLabel lblNewLabel_7 = new JLabel("Status");
		lblNewLabel_7.setBounds(501, 81, 46, 14);
		getContentPane().add(lblNewLabel_7);

		cbmStatus = new JComboBox();
		cbmStatus.setModel(new DefaultComboBoxModel(new String[] { "", "Aguardando Técnico", "Em andamento",
				"Aguardando peças", "Aguardando aprovação", "Orçamento reporvado", "Concluído" }));
		cbmStatus.setBounds(557, 77, 169, 22);
		getContentPane().add(cbmStatus);

		lblOrcamento = new JLabel("Orçamento");
		lblOrcamento.setBounds(19, 287, 66, 14);
		getContentPane().add(lblOrcamento);

		JLabel lblData = new JLabel("Chegada");
		lblData.setBounds(19, 98, 71, 14);
		getContentPane().add(lblData);

		JLabel lblEntrega = new JLabel("Entrega");
		lblEntrega.setBounds(501, 283, 46, 14);
		getContentPane().add(lblEntrega);

		txtTecnico = new JTextField();
		txtTecnico.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {
				listarTecnicos();
			}
		});
		txtTecnico.setColumns(10);
		txtTecnico.setBounds(510, 24, 169, 20);
		getContentPane().add(txtTecnico);

		txtTecnico.setDocument(new Validador(20));

		lblNewLabel_1 = new JLabel("Utilizados");
		lblNewLabel_1.setBounds(24, 239, 61, 14);
		getContentPane().add(lblNewLabel_1);

		JLabel lblCliente = new JLabel("Cliente");
		lblCliente.setBounds(35, 30, 46, 14);
		getContentPane().add(lblCliente);

		btnBuscar = new JButton("");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarClientesLista();
			}
		});
		btnBuscar.setBorder(null);
		btnBuscar.setContentAreaFilled(false);
		btnBuscar.setBorderPainted(false);
		btnBuscar.setIcon(new ImageIcon(Servicos.class.getResource("/img/lupa_00.png")));
		btnBuscar.setBounds(277, 11, 56, 45);
		getContentPane().add(btnBuscar);

		txtData = new JDateChooser();
		txtData.setBounds(106, 95, 152, 23);
		getContentPane().add(txtData);

		txtSaida = new JDateChooser();
		txtSaida.setBounds(557, 271, 169, 26);
		getContentPane().add(txtSaida);

		btnbuscarOS = new JButton("");
		btnbuscarOS.setBorderPainted(false);
		btnbuscarOS.setBorder(null);
		btnbuscarOS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarOS();
			}
		});
		btnbuscarOS.setIcon(new ImageIcon(Servicos.class.getResource("/img/lupa.png")));
		btnbuscarOS.setBounds(343, 11, 89, 61);
		getContentPane().add(btnbuscarOS);

		lblNewLabel = new JLabel("ID");
		lblNewLabel.setBounds(454, 50, 46, 14);
		getContentPane().add(lblNewLabel);

		btnBuscar_1 = new JButton("");
		btnBuscar_1.setIcon(new ImageIcon(Servicos.class.getResource("/img/lupa_00.png")));
		btnBuscar_1.setContentAreaFilled(false);
		btnBuscar_1.setBorderPainted(false);
		btnBuscar_1.setBorder(null);
		btnBuscar_1.setBounds(692, 27, 56, 45);
		getContentPane().add(btnBuscar_1);

		txtUtilizados = new JTextField();
		txtUtilizados.setBounds(91, 233, 242, 36);
		getContentPane().add(txtUtilizados);
		txtUtilizados.setColumns(10);

		JLabel lblUsuario = new JLabel("Usuário");
		lblUsuario.setBounds(330, 331, 46, 14);
		getContentPane().add(lblUsuario);

		txtOS = new JTextField();
		txtOS.setEditable(false);
		txtOS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		txtOS.setBounds(353, 78, 66, 20);
		getContentPane().add(txtOS);
		txtOS.setColumns(10);

		txtOS.setDocument(new Validador(2));

		txtId = new JTextField();
		txtId.setText("");
		txtId.setBounds(510, 47, 85, 20);
		getContentPane().add(txtId);
		txtId.setColumns(10);

		txtId.setDocument(new Validador(2));
		
		txtOrcamento = new JTextField();
		txtOrcamento.setText("0");
		txtOrcamento.setBounds(91, 284, 86, 20);
		getContentPane().add(txtOrcamento);
		txtOrcamento.setColumns(10);
		
		txtLogado = new JTextField();
		txtLogado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		txtLogado.setBounds(386, 328, 99, 20);
		getContentPane().add(txtLogado);
		txtLogado.setColumns(10);
		
		btnImprimir = new JButton("");
		btnImprimir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				imprimirOS();
			}
		});
		btnImprimir.setBorder(null);
		btnImprimir.setBorderPainted(false);
		btnImprimir.setIcon(new ImageIcon(Servicos.class.getResource("/img/impressora_0.png")));
		btnImprimir.setBounds(673, 303, 85, 68);
		getContentPane().add(btnImprimir);
	}// fim do construtor

	private void adicionarOS() {
		// System.out.println("Test");

		// validação de campos obrigatorios
		if (txtData.getDate() == null)  {
			JOptionPane.showMessageDialog(null, "Preencha a data de chegada");
			txtData.requestFocus();

		} else if (txtModelo.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o nome do Ar-Condicionado");
			txtModelo.requestFocus();

		} else if (txtInfo.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha as informações vindas do cliente");
			txtInfo.requestFocus();
			
		} else if (txtOBS.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a observação");
			txtOBS.requestFocus();


		} else {

			// logica principal
			// a linha abaixo cria uma variavel de nome create que recebe o codigo sql
			String create = "insert into servicos (usuario,chegada, modelo, informardo, encontrado, observacao, statusOS, atendimento, orcamento, Utilizados, entrega,idcli,idtec) values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
			// tratamento de exceção
			try {
				// abrir a conexão com o banco
				con = dao.conectar();
				// Uso da classe PreparedStatement para executar a instrução sql e replicar no
				// banco
				pst = con.prepareStatement(create);
				// setar (definir) os parâmetos (?,?,?) de acordo com o preenchimento das caixas
				// de texto
				pst.setString(1, txtLogado.getText());
				SimpleDateFormat formatador = new SimpleDateFormat("yyyyMMdd");

				if (txtData.getDate() == null) {
					pst.setString(2, null);
				} else {
					String dataFormatada = formatador.format(txtData.getDate());
					pst.setString(2, dataFormatada);
				}
				pst.setString(3, txtModelo.getText());
				pst.setString(4, txtInfo.getText());
				pst.setString(5, txtEncontrado.getText());
				pst.setString(6, txtOBS.getText());
				pst.setString(7, cbmStatus.getSelectedItem().toString());
				pst.setString(8, cbmTipo.getSelectedItem().toString());
				pst.setString(9, txtOrcamento.getText());
				pst.setString(10, txtUtilizados.getText());
				pst.setString(12, txtID.getText());
				
				if (!cbmStatus.getSelectedItem().equals("Aguardando Técnico") && txtTecnico.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Digite o nome do Técnico");
				} else {
					pst.setString(13, txtId.getText());
					if (txtId.getText().equals("")) {
						pst.setString(13, null);
					}
				}
				
				if (txtSaida.getDate() == null) {
					pst.setString(11, null);
				} else {
					String dataFormatada = formatador.format(txtSaida.getDate());
					pst.setString(11, dataFormatada);
				}
				pst.executeUpdate();
				
			
				
				pst.setString(13, txtId.getSelectedText());
				
				
				// executar a instrução sql (query)
				//int confirma = pst.executeUpdate();
				//System.out.println(confirma);

				JOptionPane.showMessageDialog(null, "OS adicionada com sucesso");
				// limpar os campos
				limparCampos();

				con.close();

				// recuperarOS();
				// btnOS.setVisible(true);

				// fechar a conexão com o banco
				// tratamento de exceção em caso de duplicação do login
				// JOptionPane.showMessageDialog(null, "OS não adicionada.\nEste login já está
				// sendo utilizado.");
				// txtCliente.setText(null);
				// txtCliente.requestFocus();

			} catch (Exception e1) {
				System.out.println(e1);
			}

		} // fim do método novaOS

	}// fim do metodo OS

	private void listarClientes() {
		// System.out.println("busca avançada");
		// criar um objeto -> lista (vetor dinamico) para exibir a lista de usuarios do
		// banco na pesquisa avançada
		DefaultListModel<String> modelo = new DefaultListModel<>();
		listaUsuarios.setModel(modelo);
		// CRUD Read
		String readLista = "select * from Clientes where nome like '" + txtCliente.getText() + "%'" + " order by nome";
		try {
			con = dao.conectar();
			pst = con.prepareStatement(readLista);
			rs = pst.executeQuery(readLista);
			// enquanto existir usuarios que começam com a(s) letra(s)
			/* if (rs.next()) { */
			while (rs.next()) {
				// exibir a lista
				// listaUsuarios.setVisible(true);
				scrollPane.setVisible(true);

				// exibir na lista
				modelo.addElement(rs.getString(2));
				// UX - Google
				if (txtCliente.getText().isEmpty()) {
					scrollPane.setVisible(false);
				}
			}
			/*
			 * } else { scrollPane.setVisible(false); btnCreate.setEnabled(true); //
			 * desativar botão pesquisar btnBuscar.setEnabled(false); }
			 */
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}// fim dometodo listar clientes - pesquisa avançada

	/**
	 * Metodo Responsavel por Buscar o CLiente pelo Nome
	 */
	private void buscarClientesLista() {

		// validação da busca
		if (txtCliente.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o Nome do Usuario");
			txtCliente.requestFocus(); // setar o cursor na caixa do texto
		} else {
			// selecionar o Index do vetor
			int linha = listaUsuarios.getSelectedIndex();
			if (linha >= 0) {
				// System.out.println(linha);
				// limit é uma instrução sql que limita o numero de registro selecinados da
				// tabela (índice,numéro de registro)
				String read2 = "select * from Clientes where nome like '" + txtCliente.getText() + "%'"
						+ "order by nome limit " + (linha) + " , 1";
				try {
					// abrir conexão
					con = dao.conectar();
					// preparar a execuçao do Querry
					pst = con.prepareStatement(read2);
					rs = pst.executeQuery();
					if (rs.next()) {
						scrollPane.setVisible(false);
						txtID.setText(rs.getString(1));
						txtCliente.setText(rs.getString(2));
					} else {
						JOptionPane.showMessageDialog(null, "Cliente Inexistente");
						btnCreate.setEnabled(true);
						// btnBuscar.setEnabled(false);
					}
					con.close();
				} catch (Exception e) {
					System.out.println(e);
				}
			} else {
				scrollPane.setVisible(false);
			}
		}
	}// fim do metodo buscar clientes pelo Nome

	/*
	 * listar usuario
	 */

	private void listarTecnicos() {
		// System.out.println("busca avançada");
		// criar um objeto -> lista (vetor dinamico) para exibir a lista de usuarios do
		// banco na pesquisa avançada
		DefaultListModel<String> modelo = new DefaultListModel<>();
		listaUsuarios1.setModel(modelo);
		// CRUD Read
		String readLista = "select * from Tecnico where nome like '" + txtTecnico.getText() + "%'" + " order by nome";
		try {
			con = dao.conectar();
			pst = con.prepareStatement(readLista);
			rs = pst.executeQuery(readLista);
			// enquanto existir usuarios que começam com a(s) letra(s)
			/* if (rs.next()) { */
			while (rs.next()) {
				// exibir a lista
				// listaUsuarios.setVisible(true);
				scrollPane1.setVisible(true);

				// exibir na lista
				modelo.addElement(rs.getString(2));
				// UX - Google
				if (txtTecnico.getText().isEmpty()) {
					scrollPane1.setVisible(false);
					// txtId.setText(rs.getString(1));
					txtTecnico.setText(rs.getString(1));
				}
			}
			/*
			 * } else { scrollPane.setVisible(false); btnCreate.setEnabled(true); //
			 * desativar botão pesquisar btnBuscar.setEnabled(false); }
			 */
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}// fim dometodo listar clientes - pesquisa avançada

	/**
	 * Metodo Responsavel por Buscar o usuario pelo Nome
	 */
	private void buscarTecnicosLista() {

		// validação da busca
		if (txtTecnico.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o Nome do Usuario");
			txtTecnico.requestFocus(); // setar o cursor na caixa do texto
		} else {
			// selecionar o Index do vetor
			int linha = listaUsuarios1.getSelectedIndex();
			if (linha >= 0) {
				// System.out.println(linha);
				// limit é uma instrução sql que limita o numero de registro selecinados da
				// tabela (índice,numéro de registro)
				String read2 = "select * from Tecnico where nome like '" + txtTecnico.getText() + "%'"
						+ "order by nome limit " + (linha) + " , 1";
				try {
					// abrir conexão
					con = dao.conectar();
					// preparar a execuçao do Querry
					pst = con.prepareStatement(read2);
					rs = pst.executeQuery();
					if (rs.next()) {
						scrollPane1.setVisible(false);
						txtId.setText(rs.getString(1));
						txtTecnico.setText(rs.getString(2));
					} else {
						JOptionPane.showMessageDialog(null, "Usuario Inexistente");
						btnCreate.setEnabled(true);
						// btnBuscar.setEnabled(false);
					}
					con.close();
				} catch (Exception e) {
					System.out.println(e);
				}
			} else {
				scrollPane1.setVisible(false);
			}
		}
	}// fim do metodo buscar clientes pelo Nome

	private void buscarOS() {

		String numOS = JOptionPane.showInputDialog("Número da OS");
		System.out.println(numOS);

		// validação da busca
		/*
		 * if (txtID.getText().isEmpty()) { JOptionPane.showMessageDialog(null,
		 * "Digite a Ordem de Serviço"); txtID.requestFocus(); // setar o cursor na
		 * caixa do texto } else {
		 */

		// Teste
		// System.out.println("Teste do botão Buscar");
		String read = "select * from servicos where os = ? ";
		try {
			// abrir conexão
			con = dao.conectar();
			// preparar a execuçao do Querry
			pst = con.prepareStatement(read);
			// egar o conteudo da caixa de texto e substituir o parametro ?
			pst.setString(1, numOS);
			// uso do ResultSet para obter os dados do contatos
			rs = pst.executeQuery();
			if (rs.next()) {
				// preencher as caixas de texto com o fone e o email
				// ATENÇÃO o Nome (2 campo da Janela ja foi preenchido

				String setarDataChegada = rs.getString(3);

				if (setarDataChegada == null) {
					txtData.setDate(null);
				} else {
					Date dataChegadaFormatada = new SimpleDateFormat("yyyy-MM-dd").parse(setarDataChegada);
					txtData.setDate(dataChegadaFormatada);
				}

				String setarDataSaida = rs.getString(13);

				if (setarDataSaida == null) {
					txtSaida.setDate(null);
				} else {
					Date dataSaidaFormatada = new SimpleDateFormat("yyyy-MM-dd").parse(setarDataSaida);
					txtSaida.setDate(dataSaidaFormatada);
				}

				txtOS.setText(rs.getString(1));
				txtLogado.setText(rs.getString(2));
				System.out.println(rs.getString(2));
				txtModelo.setText(rs.getString(4));
				txtInfo.setText(rs.getString(5));
				txtEncontrado.setText(rs.getString(6));
				txtOBS.setText(rs.getString(7));
				cbmStatus.setSelectedItem(rs.getString(8));
				cbmTipo.setSelectedItem(rs.getString(9));
				txtOrcamento.setText(rs.getString(10));
				txtUtilizados.setText(rs.getString(11));

				txtId.setText(rs.getString(15));
				txtID.setText(rs.getString(14));

			} else {
				JOptionPane.showMessageDialog(null, "Ordem de Serviço Inexistente");
				// ativar o botão adicionar
				btnCreate.setEnabled(true);
				// desativar botão pesquisar
				btnbuscarOS.setEnabled(false);
			}
			// Fechar Conexão
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	// }// fim do metodo buscar OS

	/**
	 * Método responsável pela edição dos dados de um contato
	 */
	private void editOS() {
		System.out.println("teste do botão editar");
		// Validação de campos obrigatórios
		if (txtModelo.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o modelo do Ar-Condicionado");
			txtModelo.requestFocus();
			
		} else if (txtInfo.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha os defeitos informados");
			txtInfo.requestFocus();
			
		/*} else if (cbmStatus.getSelectedItem().getClass()) {
			JOptionPane.showMessageDialog(null, "Preencha a observação");
			cbmStatus.requestFocus();
			
		} else if (cbmTipo.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a observação");
			cbmTipo.requestFocus();*/
			
		} else if (txtUtilizados.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Ferramentas para esse atendimento");
			txtUtilizados.requestFocus();

		} else {
			// Lógica principal (CRUD Update)
			// Criando uma variaável string que irá receber a query
			String update = "update servicos set usuario=?,modelo=?,informardo=?,encontrado=?,observacao=?,statusOS=?,atendimento=?,orcamento=?,Utilizados=?,idcli=? where os=?;";
			// Tratamento de exceções
			try {
				// Abrir a conexão com o banco
				con = dao.conectar();
				// Preparar a query(substituir ????)
				pst = con.prepareStatement(update);
				pst.setString(1, txtCliente.getText());
				pst.setString(2, txtModelo.getText());
				pst.setString(3, txtInfo.getText());
				pst.setString(4, txtEncontrado.getText());
				pst.setString(5, txtOBS.getText());
				pst.setString(6, cbmStatus.getSelectedItem().toString());
				pst.setString(7, cbmTipo.getSelectedItem().toString());
				pst.setString(8, txtOrcamento.getText());
				pst.setString(9, txtUtilizados.getText());
				pst.setString(10, txtID.getText());
				pst.setString(11, txtOS.getSelectedText());
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Dados alterado com sucesso!");
				con.close();
				limparCampos();
				
			}

			catch (java.sql.SQLIntegrityConstraintViolationException e1) {
				JOptionPane.showMessageDialog(null,
						"Ordem de Serviço não atualizada. \nEste Numero de OS está sendo Utilizado.");

			} catch (Exception e2) {
				System.out.println(e2);
			}
		}
	}
	private void imprimirOS() {
		if (txtOS.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Adicione a OS antes de imprimir");
			txtCliente.requestFocus();
		} else {
		Document document = new Document();
		try {
			PdfWriter.getInstance(document, new FileOutputStream("os.pdf"));
			document.open();
			String readOS = "select servicos.os, servicos.usuario, servicos.modelo, servicos.informardo, servicos.observacao, servicos.encontrado, servicos.atendimento, servicos.statusOS as status, Tecnico.nome as Tecnico, servicos.orcamento, date_format(servicos.chegada,'%d/%m/%Y') as data_chegada,date_format(servicos.entrega,'%d/%m/%Y') as entrega, Clientes.nome, Clientes.celular, Clientes.email as Clientes from servicos inner join Clientes on servicos.idcli = Clientes.idcli inner join Tecnico on servicos.idtec = Tecnico.idtec where os = ?";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readOS);
				pst.setString(1, txtOS.getText());
				rs = pst.executeQuery();
				
				if (rs.next()) {
					
					Paragraph texto1 = new Paragraph("MARVICOLD");
					texto1.setAlignment(Element.ALIGN_CENTER);
					texto1.getFont();
					document.add(texto1);
					
					Paragraph os = new Paragraph("OS: " + rs.getString(1));
					os.setAlignment(Element.ALIGN_LEFT);
					document.add(os);
					
					Paragraph Técnico = new Paragraph("Técnico:  " + rs.getString(9));
					Técnico.setAlignment(Element.ALIGN_RIGHT);
					document.add(Técnico);

					Paragraph Modelo = new Paragraph("Modelo: " + rs.getString(3));
					Modelo.setAlignment(Element.ALIGN_LEFT);
					document.add(Modelo);
					
					Paragraph datachegada = new Paragraph("Data de Chegada:  " + rs.getString(11));
					datachegada.setAlignment(Element.ALIGN_RIGHT);
					document.add(datachegada);
					
					
					Paragraph linha0 = new Paragraph("==========================================================================");
					linha0.setAlignment(Element.ALIGN_CENTER);
					document.add(linha0);
					
					Paragraph usuario = new Paragraph("Cliente:  " + rs.getString(13));
					usuario.setAlignment(Element.ALIGN_LEFT);	
					document.add(usuario);
					
					Paragraph celular = new Paragraph("Telefone:  " + rs.getString(14));
					celular.setAlignment(Element.ALIGN_LEFT);
					document.add(celular);
					
					Paragraph email = new Paragraph("email:  " + rs.getString(15));
					email.setAlignment(Element.ALIGN_LEFT);
					document.add(email);
					
					Paragraph informado = new Paragraph("Informado :  " + rs.getString(4));
					informado.setAlignment(Element.ALIGN_RIGHT);
					document.add(informado);
					
					Paragraph OBS = new Paragraph("OBS:  " + rs.getString(5));
					OBS.setAlignment(Element.ALIGN_RIGHT);
					document.add(OBS);
					
					Paragraph encontrado = new Paragraph("Encontrado:  " + rs.getString(6));
					encontrado.setAlignment(Element.ALIGN_RIGHT);
					document.add(encontrado);
					
					Paragraph status = new Paragraph("Status do serviço:  " + rs.getString(7));
					status.setAlignment(Element.ALIGN_RIGHT);
					document.add(status);
					
					Paragraph atendimento = new Paragraph(" Tipo de Atendimento:  " + rs.getString(8));
					atendimento.setAlignment(Element.ALIGN_RIGHT);
					document.add(atendimento);
				
					
					Paragraph linha = new Paragraph(" ");
					linha.setAlignment(Element.ALIGN_CENTER);
					document.add(linha);
					
					Paragraph linha1 = new Paragraph(" ");
					linha1.setAlignment(Element.ALIGN_CENTER);
					document.add(linha1);
					
					Paragraph linha2 = new Paragraph(" ");
					linha2.setAlignment(Element.ALIGN_CENTER);
					document.add(linha2);
					
					Paragraph linha3 = new Paragraph(" ");
					linha3.setAlignment(Element.ALIGN_CENTER);
					document.add(linha3);
					
					Paragraph linha4 = new Paragraph(" ");
					linha4.setAlignment(Element.ALIGN_CENTER);
					document.add(linha4);
					
					
					Paragraph linha5 = new Paragraph("==========================================================================");
					linha5.setAlignment(Element.ALIGN_CENTER);
					document.add(linha5);
					
					Paragraph Orcamento = new Paragraph("Orçamento:  " + rs.getString(10));
					Orcamento.setAlignment(Element.ALIGN_RIGHT);
					document.add(Orcamento);
					
					
					Paragraph datasaida = new Paragraph("Data de Saída:  " + rs.getString(12));
					datasaida.setAlignment(Element.ALIGN_RIGHT);
					document.add(datasaida);
					
					Image imagem = Image.getInstance(Servicos.class.getResource("/img/mv_2.png"));
					imagem.scaleToFit(200, 200);
					imagem.setAbsolutePosition(190, 180);
					document.add(imagem);
					
					Paragraph texto2 = new Paragraph("MARVICOLD");
					texto2.setAlignment(Element.ALIGN_CENTER);
					texto2.getFont();
					document.add(texto2);
					
					document.add(new Paragraph(" "));
					document.add(new Paragraph(" "));
					document.add(new Paragraph(" "));
					document.add(new Paragraph(" "));
					document.add(new Paragraph(" "));
					document.add(new Paragraph(" "));
					document.add(new Paragraph(" "));
					document.add(new Paragraph(" "));
					document.add(new Paragraph(" "));
					document.add(new Paragraph(" "));
					document.add(new Paragraph(" "));
					document.add(new Paragraph(" "));
					document.add(new Paragraph(" "));
					document.add(new Paragraph(" "));
					document.add(new Paragraph(" "));
					
					Paragraph texto5 = new Paragraph("            Assinatura do Cliente                                             Assinatura do responsável pela OS");
					texto5.setAlignment(Element.ALIGN_LEFT);
					document.add(texto5);
					
					document.add(new Paragraph(" "));
					
					Paragraph linha10 = new Paragraph("____________________________________________________________________");
					linha10.setAlignment(Element.ALIGN_CENTER);
					document.add(linha10);
					
				}
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		document.close();
		try {
			Desktop.getDesktop().open(new File("os.pdf"));
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	}

	private void excluirOS() {
		int confirma = JOptionPane.showConfirmDialog(null, "Confirmar a exclusão deste contato?", "ATENÇÃO",
				JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_OPTION) {
			
			String delete = "delete from serviços where iduser=?";
			
			try {		
				con = dao.conectar();
				pst = con.prepareStatement(delete);
				pst.setString(1, txtID.getText());
				pst.executeUpdate();
				limparCampos();
				JOptionPane.showMessageDialog(null, "OS excluído");				
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	private void recuperarOS() {
		String readOS = "select max(os) from Servicos";
		try {
			con = dao.conectar();
			pst = con.prepareStatement(readOS);
			rs = pst.executeQuery();
			if (rs.next()) {
				txtOS.setText(rs.getString(1));
			} else {
				JOptionPane.showMessageDialog(null, "OS Inexistente");
			}
		} catch (Exception e) {
			System.out.println();
		}

	}

	private void limparCampos() {
		txtID.setText(null);
		txtCliente.setText(null);
		txtModelo.setText(null);
		txtInfo.setText(null);
		txtEncontrado.setText(null);
		txtOBS.setText(null);
		txtTecnico.setText(null);
		cbmStatus.setSelectedItem("");
		cbmTipo.setSelectedItem("");
		txtOrcamento.setText("0");
		txtUtilizados.setText(null);
		txtData.setDate(null);
		txtSaida.setDate(null);
		txtOS.setText(null);
		txtTecnico.setText(null);
		txtId.setText(null);
		scrollPane.setVisible(false);
		scrollPane1.setVisible(false);
		
		btnCreate.setEnabled(true);
		btnbuscarOS.setEnabled(true);
	}
}
