import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WebGui implements Runnable {
    WebServer server = new WebServer();

    public JPanel Form;
    public JButton Start;
    private JCheckBox CheckBox;
    private JTextField text;
    public JButton Stop;
    public JCheckBox checkBox = new JCheckBox("Switch to maintenance mode");

    boolean state = true;
    int curentPort = 0, oldPort = 0;

    Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);

    TitledBorder title = BorderFactory.createTitledBorder(loweredetched, "Web Server Control");
    //Form.SetBorder(title);

    public WebGui() {
        Start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkBox.setSelected(false);
                if (Start.getText().equals("Start server")) {
                    Stop.setText("Stop server");
                    JOptionPane.showMessageDialog(null, "Server Stoped!");
                    server.setStateServer(3);
                    Start.setEnabled(true);
                    state = true;

                } else {
                    Start.setText("Start server");
                    curentPort = Integer.parseInt(text.getText());
                    if (curentPort == oldPort) {
                        if (state == false)
                            server.setStateServer(1);
                        JOptionPane.showMessageDialog(null, "Server Retart!");
                    }
                    if ((curentPort != oldPort)) {
                        if (state == false) {
                            startWebServer();
                            state = true;
                        }
                    }
                }

            }
            public void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
                JFrame frame=new JFrame("App");

                frame.setContentPane(new WebGui().Form);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }


            public void startWebServer()
        {
            if (server.setPort(curentPort) && server.acceptServerPort()) {
                JOptionPane.showMessageDialog(null, "Server Start");
                Start.setEnabled(false);
                oldPort = curentPort;
                server.setStateServer(1);
                Thread interfaceThread = null;
                interfaceThread = new Thread(new WebGui());
                interfaceThread.start();
            } else {
                JOptionPane.showMessageDialog(null, "Port Opened!");

            }
        }
    });
}
    public void run() {
        server.listenForClients();
    }
}

