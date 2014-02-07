package shooter;

public class GameScene extends Scene{

	private Background b;
	private VisibleAmmoCount ac;
	private Player p;
	private Enemy e;
	private Camera c;
	
	public GameScene(SceneManager sm) {
		int key = lowestAvailableKey(Layer.BACK);
		b = new Background(key, Layer.BACK);
		this.addSprite(key, b, Layer.BACK);
		
		key = lowestAvailableKey(Layer.HUD);
		ac = new VisibleAmmoCount(key, Layer.HUD, new AmmoCount(60, 60, 2, 0, false, 2));
		this.addSprite(key, ac, Layer.HUD);
		
		key = lowestAvailableKey(Layer.TOP);
		p = new Player(key, ac, Layer.TOP);
		this.addSprite(key, p, Layer.TOP);
		
		key = lowestAvailableKey(Layer.TOP);
		e = new Enemy(key, Layer.TOP);
		this.addSprite(key, e, Layer.TOP);
		
		c = new Camera(p);
	}

	public Player getPlayer(){
		return p;
	}
	
	public Background getBackground(){
		return b;
	}
	
	public Camera getCamera(){
		return c;
	}
	
	public void updateCamera(GamePanel gp, SceneManager sm){
		c.update(gp, sm);
	}
	
	@Override
	public void update(GamePanel gp, KeyboardManager km, SceneManager sm) {
		
	}
}
