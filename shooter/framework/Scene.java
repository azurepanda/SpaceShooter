package framework;
import java.util.concurrent.ConcurrentHashMap;

import manager.GamePanel;
import manager.KeyboardManager;
import manager.SceneManager;




public abstract class Scene {
	
	private ConcurrentHashMap<Integer, Sprite> particle;
	private ConcurrentHashMap<Integer, Sprite> top;
	private ConcurrentHashMap<Integer, Sprite> mid;
	private ConcurrentHashMap<Integer, Sprite> back;
	private ConcurrentHashMap<Integer, Sprite> proj;
	
	public Scene(){
		this.particle = new ConcurrentHashMap<Integer, Sprite>(); 
		this.top = new ConcurrentHashMap<Integer, Sprite>();
		this.mid = new ConcurrentHashMap<Integer, Sprite>();
		this.back = new ConcurrentHashMap<Integer, Sprite>();
		this.proj = new ConcurrentHashMap<Integer, Sprite>();
	}

	public ConcurrentHashMap<Integer, Sprite> getSpriteList(Layer layer){
		if(layer.equals(Layer.TOP)) return top;
		if(layer.equals(Layer.PARTICLE)) return particle;
		if(layer.equals(Layer.HUD)) return mid;
		if(layer.equals(Layer.BACK)) return back;
		if(layer.equals(Layer.PROJECTILE)) return proj;
		return null;
	}
	
	public int lowestAvailableKey(Layer layer) {	
		if(layer.equals(Layer.TOP)){
			if(top.isEmpty() == false) {
		        for(int i = 0; i <= top.size(); i++) {
		            if(top.containsKey(i) == false) {
		                return i;
		            }
		        }
		    }
		    return 0;
		}else if(layer.equals(Layer.PROJECTILE)){
			if(proj.isEmpty() == false) {
		        for(int i = 0; i <= proj.size(); i++) {
		            if(proj.containsKey(i) == false) {
		                return i;
		            }
		        }
		    }
		    return 0;
		}else if(layer.equals(Layer.PARTICLE)){
			if(particle.isEmpty() == false) {
		        for(int i = 0; i <= particle.size(); i++) {
		            if(particle.containsKey(i) == false) {
		                return i;
		            }
		        }
		    }
		    return 0;
		}else if(layer.equals(Layer.HUD)){
			if(mid.isEmpty() == false) {
		        for(int i = 0; i <= mid.size(); i++) {
		            if(mid.containsKey(i) == false) {
		                return i;
		            }
		        }
		    }
		    return 0;
		}else if(layer.equals(Layer.BACK)){
			if(back.isEmpty() == false) {
		        for(int i = 0; i <= back.size(); i++) {
		            if(back.containsKey(i) == false) {
		                return i;
		            }
		        }
		    }
		    return 0;
		}  
		return 0;
	}
	
	public void addSprite(int key, Sprite s, Layer layer){
		if(layer.equals(Layer.TOP)){
			top.put(key, s);
		}else if(layer.equals(Layer.PROJECTILE)){
			proj.put(key, s);
		}else if(layer.equals(Layer.PARTICLE)){
			particle.put(key, s);
		}else if(layer.equals(Layer.HUD)){
			mid.put(key, s);
		}else if(layer.equals(Layer.BACK)){
			back.put(key, s);
		}
	}
	
	public void removeSprite(int key, Layer layer){
		if(layer.equals(Layer.TOP)){
			top.remove(key);	
		}else if(layer.equals(Layer.PROJECTILE)){
			proj.remove(key);	
		}else if(layer.equals(Layer.PARTICLE)){
			particle.remove(key);	
		}else if(layer.equals(Layer.HUD)){
			mid.remove(key);	
		}else if(layer.equals(Layer.BACK)){
			back.remove(key);	
		}
	}
	
	public abstract void update(GamePanel gp, KeyboardManager km, SceneManager sm);
}
