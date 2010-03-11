import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class VerticalButton extends JButton {

	private String caption;
	private boolean clockwise;

	public VerticalButton(String caption, boolean clockwise) {
		this.caption = caption;
		this.clockwise = clockwise;
	}

	public void paint(Graphics g) {
		super.paint(g);

		Font f = getFont();
		FontMetrics fm = getFontMetrics(f);
		int captionHeight = fm.getHeight();
		int captionWidth = fm.stringWidth(caption);

		BufferedImage bi = new BufferedImage(captionHeight + 4,
				captionWidth + 4, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = (Graphics2D) bi.getGraphics();

		g2.setColor(new Color(0, 0, 0, 0));
		g2.fillRect(0, 0, bi.getWidth(), bi.getHeight());

		g2.setColor(getForeground());
		g2.setFont(f);

		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		if (clockwise) {
			g2.rotate(Math.PI / 2);
		} else {
			g2.rotate(-Math.PI / 2);
			g2.translate(-bi.getHeight(), bi.getWidth());
		}

		g2.drawString(caption, 2, -6);

		Icon icon = new ImageIcon(bi);
		setIcon(icon);

		setMargin(new Insets(15, 2, 15, 2));
		setActionCommand(caption);
	}

	public void setText(String text) {
		this.caption = text;
	}
}