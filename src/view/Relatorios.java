package view;

import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import model.DAO;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import java.awt.Font;

public class Relatorios extends JDialog {
	
	DAO dao = new DAO();
	private Connection con;
	private ResultSet rs;
	private PreparedStatement pst;
	
	private JButton btnrelClientes;
	private JComboBox cboOS;
	private JTextField txtClientes;
	private JTextField txtPendentes;
	private JButton btnPendente;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Relatorios dialog = new Relatorios();
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
	public Relatorios() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Relatorios.class.getResource("/img/report.png")));
		setTitle("Relatório - MarviGold");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		
		btnrelClientes = new JButton("");
		btnrelClientes.setIcon(new ImageIcon(Relatorios.class.getResource("/img/client.png")));
		btnrelClientes.setToolTipText("Clientes - MarviGold");
		btnrelClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				relatorioClientes();
			}
		});
		btnrelClientes.setBounds(42, 23, 81, 79);
		getContentPane().add(btnrelClientes);
		
		cboOS = new JComboBox();
		cboOS.setModel(new DefaultComboBoxModel(new String[] {"", "Aguardando Técnico", "Aguardando Peças", "Aguardando Aprovação"}));
		cboOS.setBounds(224, 144, 116, 22);
		getContentPane().add(cboOS);
		
		btnPendente = new JButton("");
		btnPendente.setIcon(new ImageIcon(Relatorios.class.getResource("/img/tools.png")));
		btnPendente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				relatorioPendente1();
			}
		});
		btnPendente.setBounds(224, 23, 99, 79);
		getContentPane().add(btnPendente);
		
		txtClientes = new JTextField();
		txtClientes.setBorder(null);
		txtClientes.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtClientes.setText("   Clientes");
		txtClientes.setBounds(42, 113, 81, 20);
		getContentPane().add(txtClientes);
		txtClientes.setColumns(10);
		
		txtPendentes = new JTextField();
		txtPendentes.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtPendentes.setText("  Pendentes");
		txtPendentes.setBounds(224, 113, 96, 20);
		getContentPane().add(txtPendentes);
		txtPendentes.setColumns(10);

	}
	
	private void relatorioClientes() {
		Document document = new Document();
		document.setPageSize(PageSize.A4.rotate());
		try {
			PdfWriter.getInstance(document, new FileOutputStream("Clientes.pdf"));
			document.open();
			Date datarel = new Date();
			DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
			document.add(new Paragraph(formatador.format(datarel)));
			document.add(new Paragraph("Clientes:"));
			document.add(new Paragraph(" "));
			
			String readClientes = "select nome,cpf,celular from Clientes order by nome";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readClientes);
				rs = pst.executeQuery();
				
				PdfPTable tabela = new PdfPTable(3);
				PdfPCell col1 = new PdfPCell(new Paragraph("Cliente"));
				PdfPCell col2 = new PdfPCell(new Paragraph("CPF"));
				PdfPCell col3 = new PdfPCell(new Paragraph("Celular"));
				tabela.addCell(col1);
				tabela.addCell(col2);
				tabela.addCell(col3);
				while (rs.next()) {
					tabela.addCell(rs.getString(1));
					tabela.addCell(rs.getString(2));
					tabela.addCell(rs.getString(3));
				}
				document.add(tabela);
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}
		document.close();
		try {
			Desktop.getDesktop().open(new File("Clientes.pdf"));
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}
	
	private void relatorioPendente1() {
		Document document = new Document();
		document.setPageSize(PageSize.A4.rotate());
		
		if(cboOS.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "Selecione o status da OS");
			cboOS.requestFocus();
		}
		else if(cboOS.getSelectedItem().equals("Aguardando Técnico")){
				
		
		try {
			PdfWriter.getInstance(document, new FileOutputStream("Aguardando_Tecnico.pdf"));
			document.open();
			Date datarel = new Date();
			DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
			document.add(new Paragraph(formatador.format(datarel)));
			document.add(new Paragraph("Ordens de serviço sem Técnico:"));
			document.add(new Paragraph(" "));
			
			String readTecnico = "select servicos.os,servicos.modelo,servicos.observacao,date_format(servicos.dataOS,'%d/%m/%Y') from servicos where statusOS = 'Aguardando técnico'  order by dataOS";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readTecnico);
				rs = pst.executeQuery();
				
				PdfPTable tabela1 = new PdfPTable(4);
				PdfPCell col1 = new PdfPCell(new Paragraph("ID"));
				PdfPCell col2 = new PdfPCell(new Paragraph("Modelo"));
				PdfPCell col3 = new PdfPCell(new Paragraph("Informado"));
				PdfPCell col4 = new PdfPCell(new Paragraph("Data"));
				tabela1.addCell(col1);
				tabela1.addCell(col2);
				tabela1.addCell(col3);
				tabela1.addCell(col4);
				while (rs.next()) {
					tabela1.addCell(rs.getString(1));
					tabela1.addCell(rs.getString(2));
					tabela1.addCell(rs.getString(3));
					tabela1.addCell(rs.getString(4));
				}
				document.add(tabela1);
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}
		document.close();
		try {
			Desktop.getDesktop().open(new File("Aguardando_Tecnico.pdf"));
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}
		//Aguardando peças ----------------------------------------------------------------------------
		else if(cboOS.getSelectedItem().equals("Aguardando Peças")){
			try {
				PdfWriter.getInstance(document, new FileOutputStream("Aguardando_Pecas.pdf"));
				document.open();
				Date datarel = new Date();
				DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
				document.add(new Paragraph(formatador.format(datarel)));
				document.add(new Paragraph("Ordens de serviço com peças faltando:"));
				document.add(new Paragraph(" "));
				
				String readTecnico = "select servicos.os,servicos.modelo,servicos.observacao,servicos.encontrado,Tecnico.nome,date_format(servicos.dataOS,'%d/%m/%Y') from servicos inner join Tecnico on servicos.idtec = Tecnico.idtec  where statusOS = 'Aguardando peças'  order by dataOS";
				try {
					con = dao.conectar();
					pst = con.prepareStatement(readTecnico);
					rs = pst.executeQuery();
					
					PdfPTable tabela1 = new PdfPTable(6);
					PdfPCell col1 = new PdfPCell(new Paragraph("ID da ordem de serviço"));
					PdfPCell col2 = new PdfPCell(new Paragraph("Modelo"));
					PdfPCell col3 = new PdfPCell(new Paragraph("Informado"));
					PdfPCell col4 = new PdfPCell(new Paragraph("Encontrado"));
					PdfPCell col5 = new PdfPCell(new Paragraph("Técnico"));
					PdfPCell col6 = new PdfPCell(new Paragraph("Data"));
					tabela1.addCell(col1);
					tabela1.addCell(col2);
					tabela1.addCell(col3);
					tabela1.addCell(col4);
					tabela1.addCell(col5);
					tabela1.addCell(col6);
					while (rs.next()) {
						tabela1.addCell(rs.getString(1));
						tabela1.addCell(rs.getString(2));
						tabela1.addCell(rs.getString(3));
						tabela1.addCell(rs.getString(4));
						tabela1.addCell(rs.getString(5));
						tabela1.addCell(rs.getString(6));
					}
					document.add(tabela1);
					con.close();
				} catch (Exception e) {
					System.out.println(e);
				}
				
			} catch (Exception e) {
				System.out.println(e);
			}
			document.close();
			try {
				Desktop.getDesktop().open(new File("Aguardando_Pecas.pdf"));
			} catch (Exception e) {
				System.out.println(e);
			}
			
		}
		
		//aguardando APROVAÇÃO
		else if(cboOS.getSelectedItem().equals("Aguardando Aprovação")){
			try {
				PdfWriter.getInstance(document, new FileOutputStream("Aguardando_Aprovacao.pdf"));
				document.open();
				Date datarel = new Date();
				DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
				document.add(new Paragraph(formatador.format(datarel)));
				document.add(new Paragraph("Ordens de serviço aguardando aprovação:"));
				document.add(new Paragraph(" "));
				
				String readTecnico = "select servicos.os,servicos.modelo,servicos.informardo,servicos.encontrado,servicos.orcamento,date_format(servicos.dataOS,'%d/%m/%Y'),Clientes.nome,Clientes.cpf,Clientes.celular from servicos inner join Clientes on servicos.idcli = Clientes.idcli  where statusOS = 'Aguardando aprovação'  order by dataOS";
				try {
					con = dao.conectar();
					pst = con.prepareStatement(readTecnico);
					rs = pst.executeQuery();
					
					PdfPTable tabela1 = new PdfPTable(9);
					PdfPCell col1 = new PdfPCell(new Paragraph("ID da OS"));
					PdfPCell col2 = new PdfPCell(new Paragraph("Modelo"));
					PdfPCell col3 = new PdfPCell(new Paragraph("Informado"));
					PdfPCell col4 = new PdfPCell(new Paragraph("Encontrado"));
					PdfPCell col5 = new PdfPCell(new Paragraph("Orçamento"));
					PdfPCell col6 = new PdfPCell(new Paragraph("Data"));
					PdfPCell col7 = new PdfPCell(new Paragraph("Cliente"));
					PdfPCell col8 = new PdfPCell(new Paragraph("Telefone do cliente"));
					PdfPCell col9 = new PdfPCell(new Paragraph("Email do cliente"));
					tabela1.addCell(col1);
					tabela1.addCell(col2);
					tabela1.addCell(col3);
					tabela1.addCell(col4);
					tabela1.addCell(col5);
					tabela1.addCell(col6);
					tabela1.addCell(col7);
					tabela1.addCell(col8);
					tabela1.addCell(col9);
					while (rs.next()) {
						tabela1.addCell(rs.getString(1));
						tabela1.addCell(rs.getString(2));
						tabela1.addCell(rs.getString(3));
						tabela1.addCell(rs.getString(4));
						tabela1.addCell(rs.getString(5));
						tabela1.addCell(rs.getString(6));
						tabela1.addCell(rs.getString(7));
						tabela1.addCell(rs.getString(8));
						tabela1.addCell(rs.getString(9));
					}
					document.add(tabela1);
					con.close();
				} catch (Exception e) {
					System.out.println(e);
				}
				
			} catch (Exception e) {
				System.out.println(e);
			}
			document.close();
			try {
				Desktop.getDesktop().open(new File("Aguardando_Aprovacao.pdf"));
			} catch (Exception e) {
				System.out.println(e);
			}
			
		}
			
		}
} //fim do codigo
