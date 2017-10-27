package br.edu.ifpr;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import jdk.nashorn.internal.scripts.JO;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Vector;
import java.awt.event.ActionEvent;

public class TelaInicial extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	TelaCarro telaCarro = null;
	TelaCliente telaCliente = null;
	TelaAlugar telaAlugar = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaInicial frame = new TelaInicial();
					frame.setTitle("Aluguel Carros");
					frame.setVisible(true);
					frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
					frame.addWindowListener(new WindowListener() {
						
						@Override
						public void windowOpened(WindowEvent e) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void windowIconified(WindowEvent e) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void windowDeiconified(WindowEvent e) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void windowDeactivated(WindowEvent e) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void windowClosing(WindowEvent e) {
							if (JOptionPane.showConfirmDialog(null, "Deseja realmente Sair?", "Confirmação",
							        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
								System.exit(0);
							}	
						}
						
						@Override
						public void windowClosed(WindowEvent e) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void windowActivated(WindowEvent e) {
							// TODO Auto-generated method stub
							
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TelaInicial() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 410, 180);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnCarros = new JButton("Carros");
		btnCarros.setIcon(new ImageIcon(TelaInicial.class.getResource("/br/edu/resources/car.png")));
		btnCarros.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(telaCarro != null && telaCarro.isShowing()){
					telaCarro.requestFocus();
				}else if(telaAlugar != null && telaAlugar.isShowing()){
					telaAlugar.requestFocus();
				}else{					
					telaCarro = new TelaCarro();
					telaCarro.setVisible(true);					
				}
			}
		});
		btnCarros.setBounds(13, 106, 117, 25);
		contentPane.add(btnCarros);
		
		JButton btnClientes = new JButton("Clientes");
		btnClientes.setIcon(new ImageIcon(TelaInicial.class.getResource("/br/edu/resources/user.png")));
		btnClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(telaCliente != null && telaCliente.isShowing()){
					telaCliente.requestFocus();
				}else if(telaAlugar != null && telaAlugar.isShowing()){
					telaAlugar.requestFocus();
				}else{	
					telaCliente = new TelaCliente();
					telaCliente.setVisible(true);
				}
			}
		});
		btnClientes.setBounds(271, 106, 117, 25);
		contentPane.add(btnClientes);
		
		JButton btnAlugar = new JButton("Alugar");
		btnAlugar.setIcon(new ImageIcon(TelaInicial.class.getResource("/br/edu/resources/creditcards.png")));
		btnAlugar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(telaCarro != null && telaCarro.isShowing()){
					JOptionPane.showMessageDialog(null, "Favor fechar modulo de Carros");
				}else if(telaCliente != null && telaCliente.isShowing()){
					JOptionPane.showMessageDialog(null, "Favor fechar modulo de Clientes");
				}else{
					if(telaAlugar != null && telaAlugar.isShowing()){
						telaAlugar.requestFocus();
					}else{
						telaAlugar = new TelaAlugar();
						telaAlugar.setVisible(true);
					}
				}
			}
		});
		btnAlugar.setBounds(142, 106, 117, 25);
		contentPane.add(btnAlugar);
		
		JLabel label = new JLabel();
		label.setIcon(new ImageIcon(TelaInicial.class.getResource("/br/edu/resources/raca-car.png")));
		label.setBounds(-109, -43, 624, 231);
		contentPane.add(label);
	}
}
