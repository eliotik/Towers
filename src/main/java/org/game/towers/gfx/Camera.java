package org.game.towers.gfx;

import java.awt.Rectangle;

import org.game.towers.game.Config;
import org.game.towers.game.Game;
import org.game.towers.game.level.Level;
import org.game.towers.handlers.InputHandler;

public class Camera {

    private Level level;
    private int x;
    private int y;
    private InputHandler inputHandler;
    private int speed;
    private MouseBounds mouseBounds;

    public Camera(Level level, int x, int y, InputHandler inputHandler, int speed) {
        setLevel(level);
        setX(x);
        setY(y);
        setInputHandler(inputHandler);
        setSpeed(speed);
        setMouseBounds(new MouseBounds());
    }

    private class MouseBounds {
        private Rectangle top;
        private Rectangle left;
        private Rectangle right;
        private Rectangle bottom;

        public MouseBounds() {
            setTop(new Rectangle(0,
        						0,
    							Config.REAL_SCREEN_WIDTH,
    							Config.BOX_SIZE * 2));

            setLeft(new Rectangle(0,
        						0,
        						Config.BOX_SIZE * 2,
        						Config.REAL_SCREEN_HEIGHT));

            setRight(new Rectangle(Config.REAL_SCREEN_WIDTH-Config.BOX_SIZE * 2,
            						0,
            						Config.BOX_SIZE * 2,
            						Config.REAL_SCREEN_HEIGHT - Config.BOX_SIZE*4));

            setBottom(new Rectangle(0,
            						Config.REAL_SCREEN_HEIGHT-Config.BOX_SIZE * 2,
            						Config.REAL_SCREEN_WIDTH - Config.BOX_SIZE*4,
            						Config.BOX_SIZE * 2));
        }

        public Rectangle getTop() {
            return top;
        }

        public void setTop(Rectangle top) {
            this.top = top;
        }

        public Rectangle getLeft() {
            return left;
        }

        public void setLeft(Rectangle left) {
            this.left = left;
        }

        public Rectangle getRight() {
            return right;
        }

        public void setRight(Rectangle right) {
            this.right = right;
        }

        public Rectangle getBottom() {
            return bottom;
        }

        public void setBottom(Rectangle bottom) {
            this.bottom = bottom;
        }
    }

    public void tick() {
        int xa = 0;
        int ya = 0;
        Screen screen = Game.getInstance().getScreen();
		if (getInputHandler().getLeft().isPressed() ||
    		getMouseBounds().getLeft().contains(screen.getMousePosition())) {
            xa--;
        }
        if (getInputHandler().getRight().isPressed() ||
    		getMouseBounds().getRight().contains(screen.getMousePosition())) {
            xa++;
        }
        if (getInputHandler().getUp().isPressed() ||
    		getMouseBounds().getTop().contains(screen.getMousePosition())) {
            ya--;
        }
        if (getInputHandler().getDown().isPressed() ||
    		getMouseBounds().getBottom().contains(screen.getMousePosition())) {
            ya++;
        }

        setX(getX() + xa * getSpeed());
        setY(getY() + ya * getSpeed());

        if (getX() < 0) {
            setX(0);
        }
        if (getX() > (getLevel().getWidth() << Config.COORDINATES_SHIFTING) - screen.getWidth()) {
            setX((getLevel().getWidth() << Config.COORDINATES_SHIFTING) - screen.getWidth());
        }
        if (getY() < 0) {
            setY(0);
        }
        if (getY() > (getLevel().getHeight() << Config.COORDINATES_SHIFTING) - screen.getHeight()) {
            setY((getLevel().getHeight() << Config.COORDINATES_SHIFTING) - screen.getHeight());
        }
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public InputHandler getInputHandler() {
        return inputHandler;
    }

    public void setInputHandler(InputHandler inputHandler) {
        this.inputHandler = inputHandler;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

	public MouseBounds getMouseBounds() {
		return mouseBounds;
	}

	public void setMouseBounds(MouseBounds mouseBounds) {
		this.mouseBounds = mouseBounds;
	}
}
