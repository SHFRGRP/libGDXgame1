package com.shfrgrp.games.game1.objects;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.*;

public class Rock extends GameObject
{
    Texture texture2 = new Texture(Gdx.files.internal("rock.png"));
    TextureRegion rockTexture = new TextureRegion(texture2, 25, 0, 250, 250);
    
   public Rock(Rectangle r){
        this.position = r;
        this.texture = rockTexture;
    }
   public Rock(){
       this.texture = rockTexture;
    }
   public Rock(TextureRegion t){
        this.texture = t;
    }
   public Rock(TextureRegion t, Rectangle r){
        this.position = r;
        this.texture = t;
    }
    
}
