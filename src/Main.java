import panels.MainFrame;

public class Main {

    private static MainFrame mFrame;

    public static void main(String[] args) {
        MainFrame.get().start();
    }

//    public static void showDialog(String mssg) {
//        JOptionPane.showMessageDialog(mFrame, mssg);
//    }
}