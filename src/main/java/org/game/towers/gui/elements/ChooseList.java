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
	private int scroll;
	private InputHandler input;

	private int width;
	private int height;

	public ChooseList(int id, Gui gui) {
		super(id, gui);
		setInput(gui.getInput());
	}

	public void addOption(Option option) {
		getOptions().add(option);
		setWidth(getWidth());
		if (getOptions().size() < ENTRIES_DISPLAYED) {
			setHeight(getOptions().size() * 8);
		} else {
			setHeight(ENTRIES_DISPLAYED * 8);
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
		if (event.key.id == getInput().down.id
				&& event.type == InputEventType.PRESSED) {
			setSelectedEntry(getSelectedEntry() + 1);
		}
		if (event.key.id == getInput().up.id
				&& event.type == InputEventType.PRESSED) {
			setSelectedEntry(getSelectedEntry() - 1);
		}
		if (getSelectedEntry() < 0) {
			setSelectedEntry(getOptions().size() - 1);
		}
		if (getSelectedEntry() >= getOptions().size()) {
			setSelectedEntry(0);
		}

		if (event.key.id == getInput().action.id
				&& event.type == InputEventType.PRESSED) {
			parent.guiActionPerformed(id, getSelectedEntry());
		}
		if (event.key.id == getInput().esc.id
				&& event.type == InputEventType.PRESSED) {
			parent.guiActionPerformed(id, -1);
		}
		if (getSelectedEntry() >= ENTRIES_DISPLAYED) {
			setScroll(getSelectedEntry() - ENTRIES_DISPLAYED + 1);
		}
		if (getSelectedEntry() < getScroll()) {
			setScroll(getSelectedEntry());
		}
	}

	public void renderCentered(Gui gui, int x, int y, int color) {
		this.render(gui, x - getWidth() / 2, y - getHeight() / 2, color);
	}

	public void render(Gui gui, int x, int y, int color) {
		for (Option o : getOptions()) {
			if ((o.id >= getScroll()) && (o.id < getScroll() + ENTRIES_DISPLAYED)) {
				if (o.isAvailable) {
					FontRenderer.drawString(o.text, gui, x + 5, y + o.id * 8
							- getScroll() * 8, color, 1);
					if (o.id == getSelectedEntry()) {
						FontRenderer.drawString(">", gui, x, y + o.id * 8
								- getScroll() * 8, color, 1);
					}
				} else {
					FontRenderer.drawString(o.text, gui, x + 5, y + o.id * 8
							- getScroll() * 8, 444, 1);
					if (o.id == getSelectedEntry()) {
						FontRenderer.drawString("#", gui, x, y + o.id * 8
								- getScroll() * 8, 444, 1);
					}
				}
			}
		}
		if (getScroll() < getOptions().size() - ENTRIES_DISPLAYED) {
			FontRenderer.drawString("}", gui, x + 20, y + 22, color, 1);
		}
		if (getScroll() > 0) {
			FontRenderer.drawString("{", gui, x + 20, y - 8, color, 1);
		}
	}

	private int getWidth() {
		int w = 0;
		for (Option o : getOptions()) {
			int w_c = FontRenderer.getStringWidth(o.text, 1);
			if (w_c > w) {
				w = w_c;
			}
		}
		return w;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public InputHandler getInput() {
		return input;
	}

	public void setInput(InputHandler input) {
		this.input = input;
	}

	public int getScroll() {
		return scroll;
	}

	public void setScroll(int scroll) {
		this.scroll = scroll;
	}

	public int getSelectedEntry() {
		return selectedEntry;
	}

	public void setSelectedEntry(int selectedEntry) {
		this.selectedEntry = selectedEntry;
	}

	public List<Option> getOptions() {
		return options;
	}

	public void setOptions(List<Option> options) {
		this.options = options;
	}
}
