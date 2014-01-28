package org.game.towers.gfx;

import java.awt.Rectangle;

import org.game.towers.configs.Config;
import org.game.towers.game.Game;
import org.game.towers.handlers.InputHandler;
import org.game.towers.level.Level;

public class Camera {

    private Level level;
    private int x;
    private int y;
    private InputHandler inputHandler;
    private int speed;
    private MouseBounds mouseBounds = new MouseBounds();

    public Camera(Level level, int x, int y, InputHandler inputHandler, int speed) {
        setLevel(level);
        setX(x);
        setY(y);
        setInputHandler(inputHandler);
        setSpeed(speed);
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
    							Config.BOX_SIZE));

            setLeft(new Rectangle(0,
        						0,
        						Config.BOX_SIZE,
        						Config.REAL_SCREEN_HEIGHT));

            setRight(new Rectangle(Config.REAL_SCREEN_WIDTH-Config.BOX_SIZE,
            						0,
            						Config.BOX_SIZE,
            						Config.REAL_SCREEN_HEIGHT));

            setBottom(new Rectangle(0,
            						Config.REAL_SCREEN_HEIGHT-Config.BOX_SIZE,
            						Config.REAL_SCREEN_WIDTH,
            						Config.BOX_SIZE));
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
        if (getInputHandler().left.isPressed() ||
    		getMouseBounds().getLeft().contains(Game.instance.getScreen().getMousePosition())) {
            xa--;
        }
        if (getInputHandler().right.isPressed() ||
    		getMouseBounds().getRight().contains(Game.instance.getScreen().getMousePosition())) {
            xa++;
        }
        if (getInputHandler().up.isPressed() ||
    		getMouseBounds().getTop().contains(Game.instance.getScreen().getMousePosition())) {
            ya--;
        }
        if (getInputHandler().down.isPressed() ||
    		getMouseBounds().getBottom().contains(Game.instance.getScreen().getMousePosition())) {
            ya++;
        }

        setX(getX() + xa * getSpeed());
        setY(getY() + ya * getSpeed());

        if (getX() < 0) {
            setX(0);
        }
        if (getX() > (getLevel().width << 3) - Game.instance.getScreen().width) {
            setX((getLevel().width << 3) - Game.instance.getScreen().width);
        }
        if (getY() < 0) {
            setY(0);
        }
        if (getY() > (getLevel().height << 3) - Game.instance.getScreen().height) {
            setY((getLevel().height << 3) - Game.instance.getScreen().height);
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
