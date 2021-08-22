import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;

public class Server extends JFrame implements Runnable{

	private JPanel contentPane;
	private Thread hiloCliente;
	
	ServerSocket serverSocket;
	Socket clientSocket=null;
	BufferedReader bf = null;
	PrintWriter pw = null;
	int clientCount = 0;
	
	Cliente clienteConectado;
	String nick = "POZO";
	
	ArrayList<Socket> clienteSocketList = new ArrayList<Socket>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Server frame = new Server();
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
	public Server() {
		hiloCliente = new Thread(this);
		hiloCliente.setName("hiloCliente");
		//hiloCliente.start();
		
		setTitle("Server Chat");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 959, 627);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNDeConexiones = new JLabel("N\u00BA de Conexiones: ");
		lblNDeConexiones.setBounds(15, 29, 138, 20);
		contentPane.add(lblNDeConexiones);
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.setBounds(807, 25, 115, 29);
		contentPane.add(btnSalir);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(15, 84, 907, 471);
		contentPane.add(textArea);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(147, 29, 38, 20);
		contentPane.add(lblNewLabel);
		
		//Crear el socket
		System.out.println("Start Server.....");
		serverSocket = null;
		try {
			serverSocket = new ServerSocket(6666);
			System.out.println("Server Started .....");
		} catch (IOException e) {
			System.out.println("No se pudo poner un socket " + "a escuchar en TCP 6666");
			return;
		}
		setName("Hilo Server");
	}
	
	@Override
	public void run() {
		while (true) {
			try {
				//El cliente se conecta con el servidor
				clientSocket = serverSocket.accept();
				clientCount++;
				
				//Añadir el socket del cliente al arrayList
				clienteSocketList.add(clientSocket);
				
				clienteConectado = new Cliente(nick,clientSocket);
				hiloCliente = new hiloCliente(clienteConectado);
				hiloCliente.start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private class hiloCliente extends Thread{
		public hiloCliente(Cliente clienteConectado) {
			try {
				// creamos los canutos
				bf = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				pw = new PrintWriter(clientSocket.getOutputStream());
				//Mandamos el resulatdo al cliente
				pw.write("SOY EL SERVER" + "\n");
				pw.flush(); // forzamos envio del paquete aunque este medio vacio (con poco contenido).
				String resultado=bf.readLine();
				System.out.println ("El resultado es:"+resultado);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					clientSocket.close();
					bf.close();
					pw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}


