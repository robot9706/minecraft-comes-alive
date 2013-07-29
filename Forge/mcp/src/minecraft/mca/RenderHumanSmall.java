/*******************************************************************************
 * RenderHumanSmall.java
 * Copyright (c) 2013 WildBamaBoy.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/

package mca;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;

import org.lwjgl.opengl.GL11;

/**
 * Determines how children are rendered.
 */
public class RenderHumanSmall extends RenderHuman
{
	/**
	 * Constructor
	 */
	public RenderHumanSmall()
	{
		super(new ModelBiped(0.0F), 0.5F);
	}

	/**
	 * Renders the Entity scaled down to a child's size, depending on its age.
	 * 
	 * @param	entity				The entity being rendered.
	 * @param	partialTickTime		The time since the last in-game tick.
	 */
	protected void renderScale(EntityBase entity, float partialTickTime)
	{
		if (MCA.instance.playerWorldManagerMap.get(Minecraft.getMinecraft().thePlayer.username).worldProperties.childrenGrowAutomatically && !(((EntityChild)entity).isAdult))
		{
			int age = ((EntityChild)entity).age;
			
			//Children initially start at 0.7F as their scale. Divide the distance between the player's size and the child's size by
			//the amount of time it takes for them to grow and multiply that times their age. This makes the child gradually get taller
			//as they get older.
			float distance = ((EntityChild)entity).gender.equals("Male") ? 0.2375F : 0.215F;
			float scale = 0.7F + ((0.2375F / MCA.instance.modPropertiesManager.modProperties.kidGrowUpTimeMinutes) * age);
			GL11.glScalef(scale, scale, scale);
		}

		//The child is an adult, so render it at the player's scale.
		else if (((EntityChild)entity).isAdult)
		{
			if (((EntityChild)entity).gender.equals("Female"))
			{
				GL11.glScalef(0.915F, 0.915F, 0.915F);
			}
			
			else
			{
				GL11.glScalef(0.9375F, 0.9375F, 0.9375F);
			}
		}
		
		//Render children the old way.
		else
		{
			GL11.glScalef(0.7F, 0.7F, 0.7F);
		}
	}

	/**
	 * Called after the model and specials have been rendered. Applies additional tweaking to the rendered model.
	 * 
	 * @param	EntityLivingBase	The entity being rendered.
	 * @param	partialTickTime	The time since the last in-game tick.
	 */
	@Override
	protected void preRenderCallback(EntityLivingBase EntityLivingBase, float partialTickTime)
	{
		renderScale((EntityBase)EntityLivingBase, partialTickTime);
	}
}