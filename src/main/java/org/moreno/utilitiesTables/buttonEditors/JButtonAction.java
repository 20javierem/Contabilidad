package org.moreno.utilitiesTables.buttonEditors;

import org.moreno.App;
import org.moreno.utilities.Colors;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class JButtonAction extends JButton {
    Color selected= Colors.selectedRow1;

    public JButtonAction(String icon, String texto) {
        setIcon(new ImageIcon(App.class.getResource("Icons/"+icon)));
        setText(texto);
        setHorizontalTextPosition(2);
        initialize();
    }
    public JButtonAction(String icon) {
        setIcon(new ImageIcon(App.class.getResource("Icons/"+icon)));
        initialize();
    }
    public JButtonAction(String icon, Color background) {
        setBackground(background);
        setBorder(BorderFactory.createEmptyBorder());
        setIcon(new ImageIcon(App.class.getResource("Icons/"+icon)));
        initialize();
    }
    private void initialize() {
        setOpaque(false);
        setBorderPainted(false);
        setFocusable(false);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                setBackground(selected);
            }
        });
    }
    public void changeIcon(String icon){
        setIcon(new ImageIcon(App.class.getResource("Icons/"+icon)));
    }
}