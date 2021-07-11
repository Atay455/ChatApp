import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientChatForm  extends JFrame implements ActionListener {
        JPanel panel;
        JTextField NewMes;
        JTextArea ChatHistory;
        JButton Send;

        private DataInputStream dis;
        private DataOutputStream dos;
        private Socket socket;

        public ClientChatForm() throws IOException {

            panel = new JPanel();
            NewMes = new JTextField();
            ChatHistory = new JTextArea();
            Send = new JButton("Send");
            this.setSize(500, 500);
            this.setVisible(true);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            panel.setLayout(null);
            this.add(panel);
            ChatHistory.setBounds(20, 20, 450, 360);
            panel.add(ChatHistory);
            NewMes.setBounds(20, 400, 340, 30);
            panel.add(NewMes);
            Send.setBounds(375, 400, 95, 30);
            panel.add(Send);
            this.setTitle("Client");


            Send.addActionListener(this);
            NewMes.addActionListener(this);

            socket = new Socket("127.0.0.1", 2020);

            while (true) {

                dis = new DataInputStream(socket.getInputStream());
                ChatHistory.append("\nServer:" + dis.readUTF());
            }
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            try {
                dos = new DataOutputStream(socket.getOutputStream());
                dos.flush();
                dos.writeUTF(NewMes.getText());
                ChatHistory.append("\n\t\t\tMe:" + NewMes.getText());
                NewMes.setText("");
            }
            catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        public static  void main(String[] args) throws IOException{
                new ClientChatForm();
        }
    }


