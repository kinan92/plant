package boundary;

import javax.swing.*;
import java.awt.*;

public class HelpMenu extends JFrame{
    private int width;
    private int height;

    /**
     * Creates a frame for the help menu and adds a panel to show the help text
     * @author Elvira Grubb
     */
    public HelpMenu ()
    {
        this.width = 250;
        this.height = 270;
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

    /**
     * Creates a JScrollPane with the help text
     * @return a JScrollPane with help text
     * @author Elvira Grubb
     */
    private JScrollPane helpText()
    {
        JPanel helpPanel = new JPanel();
        helpPanel.setPreferredSize(new Dimension(230, 922));

        JLabel helpImage = new JLabel();
        helpImage.setIcon(new ImageIcon("images/help_menu.png"));
        helpPanel.add(helpImage);

        JScrollPane helpPanelScroll = new JScrollPane(helpPanel);
        helpPanelScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        helpPanelScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        return helpPanelScroll;
    }
}