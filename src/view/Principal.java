package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Principal extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public JLabel lblUsuario;
	public JButton btnUsuarios;
	public JButton btnRelatorio;
	private JLabel lbl;
	public JPanel panelRodape;
	public JButton btnUsuarios2;
	private JLabel lblData3;
	public JButton btnServico;
	private JButton btnCliente;
	private JButton btnTecnico;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal frame = new Principal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Principal() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Principal.class.getResource("/img/ar.png")));
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				setarData();
			}
		});
		setResizable(false);
		setTitle("Último Mestre do Ar");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 671, 430);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.text);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);

		setContentPane(contentPane);
		contentPane.setLayout(null);

		btnUsuarios2 = new JButton("");
		btnUsuarios2.setToolTipText("Usuários");
		btnUsuarios2.setEnabled(false);
		btnUsuarios2.setContentAreaFilled(false);
		btnUsuarios2.setBorderPainted(false);
		btnUsuarios2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				view.usuarios usuarios = new usuarios();
				usuarios.setVisible(true);
			}
			
		});
		btnUsuarios2.setIcon(new ImageIcon(Principal.class.getResource("/img/usuarios.png")));
		btnUsuarios2.setBounds(22, 11, 128, 128);
		contentPane.add(btnUsuarios2);

		JLabel lblLogo = new JLabel("");
		lblLogo.setIcon(new ImageIcon(Principal.class.getResource("/img/bob_1.jpg")));
		lblLogo.setBounds(383, 141, 248, 185);
		contentPane.add(lblLogo);
		
		JButton btnSobre1 = new JButton("");
		btnSobre1.setContentAreaFilled(false);
		btnSobre1.setBorderPainted(false);
		btnSobre1.setBorder(null);
		btnSobre1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				view.sobre1 Sobre1 = new sobre1();
				Sobre1.setVisible(true);
			}
		});
		btnSobre1.setForeground(SystemColor.text);
		btnSobre1.setBackground(SystemColor.text);
		btnSobre1.setIcon(new ImageIcon(Principal.class.getResource("/img/about.png")));
		btnSobre1.setBounds(503, 0, 128, 128);
		contentPane.add(btnSobre1);
		
		btnRelatorio = new JButton("");
		btnRelatorio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				view.Relatorios Relatorios = new Relatorios();
				Relatorios.setVisible(true);
			}
		});
		btnRelatorio.setToolTipText("Relatório");
		btnRelatorio.setContentAreaFilled(false);
		btnRelatorio.setBorderPainted(false);
		btnRelatorio.setIcon(new ImageIcon(Principal.class.getResource("/img/report.png")));
		btnRelatorio.setBounds(22, 150, 128, 128);
		contentPane.add(btnRelatorio);
		
				panelRodape = new JPanel();
				panelRodape.setBackground(new Color(0, 0, 0));
				panelRodape.setBounds(0, 337, 665, 54);
				contentPane.add(panelRodape);
				panelRodape.setLayout(null);
								
								lbl = new JLabel("Usuário:");
								lbl.setBounds(10, 11, 96, 30);
								panelRodape.add(lbl);
								lbl.setFont(new Font("Tahoma", Font.BOLD, 13));
								lbl.setForeground(new Color(255, 255, 255));
								
								lblUsuario = new JLabel("");
								lblUsuario.setBounds(72, 11, 202, 32);
								panelRodape.add(lblUsuario);
								lblUsuario.setForeground(new Color(255, 255, 255));
								lblUsuario.setFont(new Font("Tahoma", Font.BOLD, 12));
								
								lblData3 = new JLabel("");
								lblData3.setBounds(388, 9, 249, 32);
								panelRodape.add(lblData3);
								lblData3.setForeground(new Color(255, 255, 255));
		
		btnCliente = new JButton("");
		btnCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				view.Clientes Clientes = new Clientes();
				Clientes.setVisible(true);
				
			}
		});
		btnCliente.setToolTipText("Adicionar");
		btnCliente.setContentAreaFilled(false);
		btnCliente.setBorder(null);
		btnCliente.setBorderPainted(false);
		btnCliente.setDefaultCapable(false);
		btnCliente.setIcon(new ImageIcon(Principal.class.getResource("/img/client_2.png")));
		btnCliente.setBounds(160, 11, 128, 128);
		contentPane.add(btnCliente);
		
		btnServico = new JButton("");
		btnServico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				view.Servicos Servicos = new Servicos();
				Servicos.setVisible(true);
				
				Servicos.usuario = lblUsuario.getText();
			}
		});
		btnServico.setEnabled(false);
		btnServico.setToolTipText("Serviços");
		btnServico.setContentAreaFilled(false);
		btnServico.setBorderPainted(false);
		btnServico.setIcon(new ImageIcon(Principal.class.getResource("/img/tools_2.png")));
		btnServico.setBounds(170, 150, 128, 128);
		contentPane.add(btnServico);
		
		btnTecnico = new JButton("");
		btnTecnico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				view.Tecnico Tecnico = new Tecnico();
				Tecnico.setVisible(true);
			}
		});
		btnTecnico.setContentAreaFilled(false);
		btnTecnico.setDefaultCapable(false);
		btnTecnico.setToolTipText("Técnico");
		btnTecnico.setIcon(new ImageIcon(Principal.class.getResource("/img/tec_1.png")));
		btnTecnico.setBorder(null);
		btnTecnico.setBorderPainted(false);
		btnTecnico.setBounds(298, 11, 128, 128);
		contentPane.add(btnTecnico);
	}

	private void setarData() {
		Date data = new Date();
		DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
		lblData3.setText(formatador.format(data));
	}
}
