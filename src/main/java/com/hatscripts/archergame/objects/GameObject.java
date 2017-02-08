package com.hatscripts.archergame.objects;

import com.hatscripts.archergame.window.Game;

import java.awt.*;
import java.util.LinkedList;

public abstract class GameObject {

	protected final int width, height;
	protected final float maxSpeed;
	protected final boolean solid;
	private final ObjectType type;
	protected float x, y;
	protected float xSpeed, ySpeed;

	public GameObject(ObjectType type, float x, float y) {
		this.type = type;
		this.x = x;
		this.y = y;
		this.solid = type.isSolid();
		this.width = type.getWidth();
		this.height = type.getHeight();
		this.maxSpeed = type.getMaxSpeed();
	}

	protected enum Bounds {
		TOP, BOTTOM, LEFT, RIGHT
	}

	public abstract void tick(LinkedList<GameObject> object);

	public abstract void render(Graphics g);

	public ObjectType getType() {
		return type;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getXSpeed() {
		return xSpeed;
	}

	public void setXSpeed(float xSpeed) {
		this.xSpeed = xSpeed;
	}

	public float getYSpeed() {
		return ySpeed;
	}

	public void setYSpeed(float ySpeed) {
		this.ySpeed = ySpeed;
	}

	public boolean isSolid() {
		return solid;
	}

	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, width, height);
	}

	public Rectangle getBounds(Bounds bounds) {
		int xs = (int) Math.abs(xSpeed);
		int ys = (int) Math.abs(ySpeed);
		switch (bounds) {
			case TOP:
				return new Rectangle((int) x + 5 + xs, (int) y - ys, width - 10 - xs * 2, ys + 5);
			case BOTTOM:
				return new Rectangle((int) x + 5 + xs, (int) y + height - 5, width - 10 - xs * 2, ys + 5);
			case LEFT:
				return new Rectangle((int) x - xs, (int) y + 5 + ys, xs + 5, height - 10 - ys * 2);
			case RIGHT:
				return new Rectangle((int) x + width - 5, (int) y + 5 + ys, xs + 5, height - 10 - ys * 2);
			default:
				throw new IllegalArgumentException();
		}
	}

	public boolean contains(Point point) {
		return getBounds().contains(point);
	}

	public boolean isOnScreen() {
		return Game.BOUNDS.intersects(getBounds());
	}

	public Point getCenterPoint() {
		return new Point((int) x + (width / 2), (int) y + (height / 2));
	}

	public double distanceTo(GameObject object) {
		return distanceTo(object.getCenterPoint());
	}

	public double distanceTo(Point point) {
		Point thisPoint = getCenterPoint();

		double dx = thisPoint.x - point.x;
		double dy = thisPoint.y - point.y;
		return Math.sqrt((dx * dx) + (dy * dy));
	}

	public GameObject getNearest() {
		return getNearest(null);
	}

	public GameObject getNearest(ObjectType type) {
		if (Objects.OBJECTS.size() <= 1) {
			return null;
		}
		GameObject nearest = null;
		double distanceToNearest = Double.MAX_VALUE;
		for (GameObject object : Objects.OBJECTS) {
			if ((type == null || type == object.getType())
					&& object != this) {
				double distance = distanceTo(object);
				if (distance < distanceToNearest) {
					nearest = object;
					distanceToNearest = distance;
				}
			}
		}
		return nearest;
	}

	protected boolean collisionCheck() {
		if (!solid) {
			throw new IllegalArgumentException("Object must be solid to perform a collision check");
		}
		for (GameObject object : Objects.OBJECTS) {
			if (object != this && object.isSolid()) {
				Rectangle objectBounds = object.getBounds();
				Bounds collisionLoc = getCollisionLocation(objectBounds);
				if (collisionLoc != null) {
					System.out.println(collisionLoc.toString());
					switch (collisionLoc) {
						case TOP:
							ySpeed = 0;
							y = objectBounds.y + objectBounds.height;
							break;
						case BOTTOM:
							ySpeed = 0;
							y = objectBounds.y - height;
							break;
						case LEFT:
							xSpeed = 0;
							x = objectBounds.x + objectBounds.width;
							break;
						case RIGHT:
							xSpeed = 0;
							x = objectBounds.x - width;
							break;
					}
					return true;
				}
			}
		}
		return false;
	}

	protected void bringOntoScreen() {
		if (x < 32) {
			x = 32;
			xSpeed = 0;
		} else if (x + width > Game.WIDTH - 32) {
			x = Game.WIDTH - 32 - width;
			xSpeed = 0;
		}
		if (y < 32) {
			y = 32;
			ySpeed = 0;
		} else if (y + height > Game.HEIGHT - 32) {
			y = Game.HEIGHT - 32 - height;
			ySpeed = 0;
		}
	}

	/*protected void bringOntoScreen() {
		if (x < 0) {
			x = 0;
			xSpeed = 0;
		} else if (x + width > Game.WIDTH) {
			x = Game.WIDTH - width;
			xSpeed = 0;
		}
		if (y < 0) {
			y = 0;
			ySpeed = 0;
		} else if (y + height > Game.HEIGHT) {
			y = Game.HEIGHT - height;
			ySpeed = 0;
		}
	}*/

	protected void capSpeed() {
		if (xSpeed > maxSpeed) {
			xSpeed = maxSpeed;
		} else if (xSpeed < -maxSpeed) {
			xSpeed = -maxSpeed;
		}
		if (ySpeed > maxSpeed) {
			ySpeed = maxSpeed;
		} else if (ySpeed < -maxSpeed) {
			ySpeed = -maxSpeed;
		}
	}

	protected float alterSpeed(float current, float amount, boolean toStop) {
		if (toStop) {
			amount = Math.abs(amount);
			if (current > 0) {
				return current > amount ? current - amount : 0;
			} else {
				return current < -amount ? current + amount : 0;
			}
		} else {
			return current + amount;
		}
	}

	private Bounds getCollisionLocation(Rectangle otherBounds) {
		for (Bounds bounds : Bounds.values()) {
			if (getBounds(bounds).intersects(otherBounds)) {
				return bounds;
			}
		}
		return null;
	}
}