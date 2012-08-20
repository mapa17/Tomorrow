/**
* Simple Comment
*/
package com.Salsalectric.main;

import java.util.HashMap;

import org.mapa.R;
import org.mapa17.utils.AData;

import android.content.Context;
import android.content.res.Resources;
import android.text.GetChars;
import android.util.Log;

public class TodoElement extends AData{
	
	private static final String TAG = "TodoElement";
	
	public enum TodoPriority {
	    LOW, MEDIUM, HIGH, UNKNOWN 
	}
	
	private String _name;
	private String _desc;
	private TodoPriority _prio;
	
	private TodoElement(String name, String desc){
		super();
		this._name = name;
		this._desc = desc;
	};
	
	public static TodoElement TodoElementFactory(Context c, HashMap<String, String> hmap){
		
		//new TodoElement();	
		TodoElement element = EmptyTodoElementFactory(c);
		element.set(hmap);
		return element;
	}

	public static TodoElement EmptyTodoElementFactory(Context c){		
		TodoElement element = new TodoElement( c.getString( R.string.todoElement_name_hint), c.getString( R.string.todoElement_desc_hint) );
		return element;
	}
	
	@Override
	protected void _set(HashMap<String, String> hmap)
	{
		String t;
		
		t = hmap.get("name");
		if(t != null){
			this._name = t;
		}
		
		t = hmap.get("desc");
		if(t != null){
			this._desc = t;
		}
		
		t = hmap.get("priority");
		if(t != null){
			this._prio = TodoPriority.valueOf(t);
		}
	}

	@Override
	public HashMap<String, String> get() {
		HashMap<String, String> hmap = new HashMap<String, String>();
		hmap.put("name", this._name);
		hmap.put("desc", this._desc);
		hmap.put("priority", this._prio.toString());
		return hmap;
	}

	@Override
	public boolean testIntegrity() {
		// TODO Auto-generated method stub
		return false;
	}
}

