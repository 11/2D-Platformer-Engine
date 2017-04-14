package com.pariah.engine.managers;

public class ActionManager
{

	private static ActionManager	actionManager	= null;

	private ActionManager()
	{

	}

	public static ActionManager createInstance()
	{
		if(actionManager==null)
		{
			actionManager = new ActionManager();
		}
		
		return actionManager;
	}
	
	public static void deleteInstance()
	{
		actionManager=null;
	}

}
