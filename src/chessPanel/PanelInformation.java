package chessPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTextPane;

public class PanelInformation extends JPanel {
	private JTextPane textPane;

	public PanelInformation() {
		this.setLayout(new BorderLayout());
		this.setBounds(10, 10, 652, 60);
		textPane = new JTextPane();
		textPane.setBackground(Color.WHITE);
		textPane.setCaretColor(Color.BLACK);
		textPane.setSelectedTextColor(Color.YELLOW);
		add(textPane, BorderLayout.CENTER);
		textPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		textPane.setEditable(false);
		textPane.setBackground(Color.LIGHT_GRAY);
	}

	public void appendImage(Image image) {
		textPane.insertIcon(new ImageIcon(image));
	}

	public void clear() {
		textPane.setText("");
	}
	
}
