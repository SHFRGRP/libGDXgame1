package com.shfrgrp.games.game1.utils;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;

public class Utils
{
	
	public static Animation getAnimation(Texture sprite, int cols, int rows){
        TextureRegion[][] tmp = TextureRegion.split(sprite, sprite.getWidth() / cols, sprite.getHeight() / rows);
        TextureRegion[] frames = new TextureRegion[cols * rows];
        int index = 0;
        for (int i = 0; i < rows; i++)
		{
            for (int j = 0; j < cols; j++)
			{
                frames[index++] = tmp[i][j];
            }
        }
       Animation animation = new Animation(0.025f, frames);
	   return animation;
		
	}
}
