import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.JButton;

public class Cliente extends JFrame implements Runnable {

	private JPanel contentPane;
	InetSocketAddress direccion=new InetSocketAddress("localhost", 6666);
    static Socket socket=new Socket();
    BufferedReader br = null;
    PrintWriter pw = null;
    static String nick="";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Cliente frame = new Cliente(nick,socket);
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
	public Cliente(String nick, Socket socketCliente) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 855, 532);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setBounds(15, 88, 803, 327);
		contentPane.add(textArea);
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.setBounds(348, 431, 115, 29);
		contentPane.add(btnSalir);
		
		JButton btnEnviar = new JButton("Enviar");
		btnEnviar.setBounds(703, 31, 115, 29);
		contentPane.add(btnEnviar);
		
		JTextArea textArea_1 = new JTextArea();
		textArea_1.setBounds(15, 16, 675, 56);
		contentPane.add(textArea_1);
	}

	@Override
	public void run() {
		InetSocketAddress direccion=new InetSocketAddress("localhost", 6666);
        Socket socket=new Socket();
        BufferedReader br = null;
        PrintWriter pw = null;
        try {
			socket.connect(direccion);
		} catch (IOException e) {
			e.printStackTrace();
		}
        
		try {
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));                
			pw=new PrintWriter(socket.getOutputStream());
            pw.print("*\n");
            pw.print("HOLA SOY EL CLIENTE");
            pw.flush();
            String resultado=br.readLine();
            System.out.println ("El resultado es:"+resultado);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				socket.close();
				br.close();
				pw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
