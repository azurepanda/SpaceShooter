package shooter;
import java.util.ArrayList;


public class SceneManager {

	private ArrayList<Scene> scenes;
	private Scene currentScene;
	
	public SceneManager(){
		this.scenes = new ArrayList<Scene>();
		this.addScene(new TitleScene(this));
		this.addScene(new GameScene(this));
		this.setCurrentScene(TitleScene.class);
	}
	
	public void removeScene(Class<?> scene){
		for(Scene s : scenes){
			if(s.getClass().equals(scene)){
				scenes.remove(s);
			}
		}
	}
	
	public void addScene(Scene sm){
		scenes.add(sm);
	}
	
	public void setCurrentScene(Class<?> scene){
		for(Scene s : scenes){
			if(s.getClass().equals(scene)){
				currentScene = s;
			}
		}
	}
	
	public Scene getScene(Class<?> scene){
		for(Scene s : scenes){
			if(s.getClass().equals(scene)){
				return s;
			}
		}
		return null;
	}
	
	public Scene getCurrentScene(){
		return currentScene;	
	}
}
