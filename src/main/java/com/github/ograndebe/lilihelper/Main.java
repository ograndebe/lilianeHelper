package com.github.ograndebe.lilihelper;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class Main extends JFrame {

    private final JFrame frame;
    private final JPanel panel;
    private final Robot robot;
    private final boolean windows;

    public Main() throws AWTException {
        this.windows = System.getProperty("os.name").toLowerCase().contains("windows");
        System.out.println();
        this.frame = new JFrame("Lili Helper");
        this.panel = new JPanel();
        try {
            this.robot = new Robot();
        } catch (AWTException e) {
            JOptionPane.showMessageDialog(frame, e.getMessage());
            throw e;
        }

        panel.setLayout(new FlowLayout());
        createButtons();
        frame.add(panel);
        frame.setAlwaysOnTop(true);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    private void createButtons() {
        panel.add(botaoEditar());

        try {
            List<ButtonConfiguration> load = new ConfigurationLoader().load();
            load.forEach(this::createAndAddButton);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, e.getMessage());
        }



    }

    private void createAndAddButton(final ButtonConfiguration buttonConfiguration) {
        final JButton jButton = new JButton(buttonConfiguration.getLabel());
        jButton.addActionListener(l->{
            frame.setState(Frame.ICONIFIED);
            sleep(300);
            buttonConfiguration.getType().action(robot, buttonConfiguration.getKeys());
            frame.setState(Frame.NORMAL);

        });

        panel.add(jButton);
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private Component botaoEditar() {
        JButton jButton = new JButton("Edit");
        jButton.addActionListener(l->{
            final String command = windows?"notepad":"gedit";

            new Thread(() -> {
                try {
                    final Process process = Runtime.getRuntime().exec(new String[]{command, AppConstants.getConfFile().getAbsolutePath()});
                    process.waitFor();
                    System.out.println("Finalizou edicao");
                    panel.removeAll();
                    createButtons();
                    frame.pack();
                    frame.setVisible(true);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(frame, e.getMessage());
                }
            }).run();
        });
        return jButton;
    }

    public static void main(String[] args) throws AWTException {
        new Main();
    }

}
