import javax.swing.JFrame;

public class Begin extends JFrame {

    public Begin() {
        //add(new ());
        setTitle("Board");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    public static void main(String[] args) {
        new Begin();
    }
}

