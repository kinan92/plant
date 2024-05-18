package boundary;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class HelpMenu extends JFrame{
    private int width;
    private int height;

    public HelpMenu (int width, int height)
    {
        this.width = (width / 5) * 2 + 10;
        this.height = (height / 3) * 2;
        setTitle("Help");

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(this.width, this.height);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());
        ImageIcon icon = new ImageIcon("images/icon.png");
        setIconImage(icon.getImage());

        add(helpText(), BorderLayout.CENTER);
        setVisible(true);
    }

    private JScrollPane helpText()
    {
        JPanel helpPanel = new JPanel();
        helpPanel.setPreferredSize(new Dimension(width - 20, height));
        helpPanel.setBackground(new java.awt.Color(184, 200, 177));

        JTextArea plantCareTitle = new JTextArea(20, 1);
        plantCareTitle.setText("hej");
        plantCareTitle.setEditable(false);
        plantCareTitle.setSize(new Dimension(width, height));
        plantCareTitle.setFont(new Font("Calibri", Font.BOLD, 16));
        helpPanel.add(plantCareTitle);

        JScrollPane helpPanelScroll = new JScrollPane(helpPanel);
        helpPanelScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        helpPanelScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        return helpPanelScroll;
    }
}
