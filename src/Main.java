import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Main extends JFrame {

	private JPanel contentPane;
	private JTextField txt_mt;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JList listDebit;
	private JList listCredit;
	DefaultListModel<String> model1 = new DefaultListModel<>();
	DefaultListModel<String> model2 = new DefaultListModel<>();
	private JLabel lbl1;
	private JLabel lbl2;
	private JLabel lbl3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
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
	public Main() {
		setResizable(false);
		setTitle("Journal");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 608, 481);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 2), "Saisie des donnees",
				TitledBorder.CENTER, TitledBorder.TOP, null, null));
		panel.setBounds(10, 11, 269, 130);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblMontant = new JLabel("Montant :");
		lblMontant.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblMontant.setBounds(10, 26, 112, 14);
		panel.add(lblMontant);

		txt_mt = new JTextField();
		txt_mt.setDocument(new ControleDecimal());
		txt_mt.setBounds(120, 23, 139, 20);
		panel.add(txt_mt);
		txt_mt.setColumns(10);

		JLabel lblTypeDoperation = new JLabel("Type d'operation : ");
		lblTypeDoperation.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTypeDoperation.setBounds(10, 67, 112, 14);
		panel.add(lblTypeDoperation);

		JRadioButton rdDebit = new JRadioButton("Debit");
		rdDebit.setSelected(true);
		buttonGroup.add(rdDebit);
		rdDebit.setBounds(120, 63, 109, 23);
		panel.add(rdDebit);

		JRadioButton rdCredit = new JRadioButton("Credit");
		buttonGroup.add(rdCredit);
		rdCredit.setBounds(120, 93, 109, 23);
		panel.add(rdCredit);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 2), "Les operations", TitledBorder.CENTER,
				TitledBorder.TOP, null, null));
		panel_1.setBounds(289, 11, 293, 130);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		JButton btnNewButton = new JButton("Ecrire dans le journal");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (txt_mt.getText().trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Montant vide");
					txt_mt.requestFocus();
					return;
				}

				double mt = Double.parseDouble(txt_mt.getText());

				if (rdDebit.isSelected()) {
					model1.addElement(String.format("%.2f", mt));
					model2.addElement(String.format("%.2f", 0.0));
				} else {
					model1.addElement(String.format("%.2f", 0.0));
					model2.addElement(String.format("%.2f", mt));
				}

				double debit = TotalDebit(), credit = TotalCredit();
				double balance = credit - debit;

				lbl1.setText(String.format("%.2f", debit));
				lbl2.setText(String.format("%.2f", credit));
				lbl3.setText(String.format("%.2f", balance));

				txt_mt.setText("");
				txt_mt.requestFocus();
				JOptionPane.showMessageDialog(null, "Operation enregistree");
			}
		});
		btnNewButton.setBounds(10, 23, 273, 23);
		panel_1.add(btnNewButton);

		JButton btnAnnulerLaDerniere = new JButton("Annuler la derniere saisie");
		btnAnnulerLaDerniere.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (model1.size() != 0) {
					int lastposition = model1.size() - 1;

					model1.remove(lastposition);
					model2.remove(lastposition);

					double debit = TotalDebit(), credit = TotalCredit();
					double balance = credit - debit;

					lbl1.setText(String.format("%.2f", debit));
					lbl2.setText(String.format("%.2f", credit));
					lbl3.setText(String.format("%.2f", balance));

					txt_mt.setText("");
					txt_mt.requestFocus();
					JOptionPane.showMessageDialog(null, "Operation enregistree");
				}
			}
		});
		btnAnnulerLaDerniere.setBounds(10, 53, 273, 23);
		panel_1.add(btnAnnulerLaDerniere);

		JButton btnNewButton_1_1 = new JButton("Quitter l'application");
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnNewButton_1_1.setBounds(10, 87, 273, 23);
		panel_1.add(btnNewButton_1_1);

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 2), "Journal des ecritures",
				TitledBorder.CENTER, TitledBorder.TOP, null, null));
		panel_2.setBounds(10, 152, 572, 279);
		contentPane.add(panel_2);
		panel_2.setLayout(null);

		listDebit = new JList();
		listDebit.setModel(model1);
		listDebit.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		listDebit.setBounds(114, 47, 155, 162);
		panel_2.add(listDebit);

		listCredit = new JList();
		listCredit.setModel(model2);
		listCredit.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		listCredit.setBounds(279, 47, 155, 162);
		panel_2.add(listCredit);

		JLabel lblTotal = new JLabel("Total :");
		lblTotal.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblTotal.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTotal.setBounds(10, 220, 95, 14);
		panel_2.add(lblTotal);

		lbl1 = new JLabel("0.00 DH");
		lbl1.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl1.setBorder(new LineBorder(new Color(0, 0, 0)));
		lbl1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lbl1.setBounds(159, 220, 95, 14);
		panel_2.add(lbl1);

		lbl2 = new JLabel("0.00 DH");
		lbl2.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl2.setBorder(new LineBorder(new Color(0, 0, 0)));
		lbl2.setFont(new Font("Tahoma", Font.BOLD, 11));
		lbl2.setBounds(323, 220, 95, 14);
		panel_2.add(lbl2);

		JLabel lblTotal_1_1_1 = new JLabel("Balance :");
		lblTotal_1_1_1.setOpaque(true);
		lblTotal_1_1_1.setBackground(Color.PINK);
		lblTotal_1_1_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblTotal_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTotal_1_1_1.setBounds(467, 195, 95, 14);
		panel_2.add(lblTotal_1_1_1);

		lbl3 = new JLabel("0.00 DH");
		lbl3.setOpaque(true);
		lbl3.setBackground(Color.PINK);
		lbl3.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl3.setBorder(new LineBorder(new Color(0, 0, 0)));
		lbl3.setFont(new Font("Tahoma", Font.BOLD, 11));
		lbl3.setBounds(467, 220, 95, 14);
		panel_2.add(lbl3);

		JLabel lblDebit = new JLabel("Debit : ");
		lblDebit.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblDebit.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblDebit.setBounds(141, 22, 95, 14);
		panel_2.add(lblDebit);

		JLabel lblCredit = new JLabel("Credit : ");
		lblCredit.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblCredit.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblCredit.setBounds(303, 22, 95, 14);
		panel_2.add(lblCredit);
	}

	double TotalDebit() {
		double total = 0;
		for (int i = 0; i < model1.size(); i++)
			total += Double.parseDouble(model1.get(i).replace(',', '.'));

		return total;
	}

	double TotalCredit() {
		double total = 0;
		for (int i = 0; i < model2.size(); i++)
			total += Double.parseDouble(model2.get(i).replace(',', '.'));

		return total;
	}
}
