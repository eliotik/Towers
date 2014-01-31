package org.game.towers.gui.elements;


import org.game.towers.handlers.InputHandler;
import org.game.towers.handlers.InputHandler.GameActionListener;
import org.game.towers.handlers.InputHandler.InputEvent;
import org.game.towers.handlers.InputHandler.InputEventType;
import org.game.towers.gui.Gui;

import java.util.ArrayList;
import java.util.List;


public class ChooseList extends GuiElement implements GameActionListener {

	private int ENTRIES_DISPLAYED = 3;

	private List<Option> options = new ArrayList<Option>();
	private int selectedEntry;
	public int scrol;
	private InputHandler input;

	private int width;
	private int height;

	public ChooseList(int id, Gui gui) {
		super(id, gui);
		input = gui.input;
	}

	public void addOption(Option option) {
		options.add(option);
		width = getWidth();
		if (options.size() < ENTRIES_DISPLAYED) {
			height = options.size() * 8;
		} else {
			height = ENTRIES_DISPLAYED * 8;
		}
	}

	public int getId() {
		return id;
	}

	public void setMaximumDisplayed(int max) {
		ENTRIES_DISPLAYED = max;
	}

	public class Option {
		public int id;
		public String text;
		public boolean isAvailable = true;

		public Option(int id, String text) {
			this.id = id;
			this.text = text;
		}
	}

	public void actionPerformed(InputEvent event) {
		if (event.key.id == input.down.id
				&& event.type == InputEventType.PRESSED) {
			selectedEntry++;
		}
		if (event.key.id == input.up.id
				&& event.type == InputEventType.PRESSED) {
			selectedEntry--;
		}
		if (selectedEntry < 0) {
			selectedEntry = options.size() - 1;
		}
		if (selectedEntry >= options.size()) {
			selectedEntry = 0;
		}

		if (event.key.id == input.action.id
				&& event.type == InputEventType.PRESSED) {
			parent.guiActionPerformed(id, selectedEntry);
		}
		if (event.key.id == input.esc.id
				&& event.type == InputEventType.PRESSED) {
			parent.guiActionPerformed(id, -1);
		}
		if (selectedEntry >= ENTRIES_DISPLAYED) {
			scrol = selectedEntry - ENTRIES_DISPLAYED + 1;
		}
		if (selectedEntry < scrol) {
			scrol = selectedEntry;
		}
	}

	public void renderCentered(Gui gui, int x, int y, int color) {
		this.render(gui, x - width / 2, y - height / 2, color);
	}

	public void render(Gui gui, int x, int y, int color) {
		for (Option o : options) {
			if ((o.id >= scrol) && (o.id < scrol + ENTRIES_DISPLAYED)) {
				if (o.isAvailable) {
					FontRenderer.drawString(o.text, gui, x + 5, y + o.id * 8
							- scrol * 8, color, 1);
					if (o.id == selectedEntry) {
						FontRenderer.drawString(">", gui, x, y + o.id * 8
								- scrol * 8, color, 1);
					}
				} else {
					FontRenderer.drawString(o.text, gui, x + 5, y + o.id * 8
							- scrol * 8, 444, 1);
					if (o.id == selectedEntry) {
						FontRenderer.drawString("#", gui, x, y + o.id * 8
								- scrol * 8, 444, 1);
					}
				}
			}
		}
		if (scrol < options.size() - ENTRIES_DISPLAYED) {
			FontRenderer.drawString("}", gui, x + 20, y + 22, color, 1);
		}
		if (scrol > 0) {
			FontRenderer.drawString("{", gui, x + 20, y - 8, color, 1);
		}
	}

	private int getWidth() {
		int w = 0;
		for (Option o : options) {
			int w_c = FontRenderer.getStringWidth(o.text, 1);
			if (w_c > w) {
				w = w_c;
			}
		}
		return w;
	}
}
