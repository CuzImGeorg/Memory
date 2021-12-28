package memory;

public class Start {

    private static MainFrame mf;

    public static void main(String[] args) {
         mf = new MainFrame();

    }

    public static MainFrame getMainFrame() {
        return mf;
    }
}
