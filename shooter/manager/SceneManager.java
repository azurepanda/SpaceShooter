package manager;
import java.util.ArrayList;

import framework.Scene;

import scene.GameScene;
import scene.TitleScene;


public class SceneManager {

	private AudioManager am;
	private FontManager fm;
	private ArrayList<Scene> scenes;
	private Scene currentScene;
	
	public SceneManager(){
		am = new AudioManager();
		fm = new FontManager();
		Thread thread = new Thread(am);
		thread.start();
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
	
	public FontManager getFontManager(){
		return fm;
	}
	
	public AudioManager getAudioManager(){
		return am;
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
