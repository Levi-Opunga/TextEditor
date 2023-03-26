package dev.levi.presentation.components;

import javax.swing.*;

public class CustomDialog extends JDialog {
    private JFrame panel;

    public CustomDialog(JFrame parent, String title, String message) {
        super(parent, title, true);
        JPanel messagePane = new JPanel();
        messagePane.add(new JLabel(message));
        getContentPane().add(messagePane);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(parent);
    }
    public void showDialog() {
        setVisible(true);
    }

    public void hideDialog() {
        setVisible(false);
    }
//    public static void main(String[] args) {
//        JFrame parentFrame = new JFrame("Parent Frame");
//        parentFrame.setVisible(true);
//        parentFrame.setSize(234,567);
//        CustomDialog dialog = new CustomDialog(parentFrame, "Custom Dialog", "This is a custom dialog box.");
//        dialog.setVisible(true);
//    }
}
