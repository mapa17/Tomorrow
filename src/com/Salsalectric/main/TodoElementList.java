package com.Salsalectric.main;

import java.util.ArrayList;
import java.util.HashMap;

import static junit.framework.Assert.*;
import java.util.Iterator;

import org.mapa17.utils.AData;


import android.content.Context;
import android.renderscript.Element;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.View.OnClickListener;

/**
 * @author dd
 *
 */
public class TodoElementList extends AData implements Iterable<TodoElement>{

	private static final String TAG = "TodoElementList";
	ArrayList<TodoElement> _list;
	
	protected TodoElementList() {
		super();
		this._list = new ArrayList<TodoElement>();
	}
	
	// The container Activity must implement this interface so the fragment can deliver messages
    public interface ProvideTodoElementList {
        /** Called by HeadlinesFragment when a list item is selected */
        public TodoElementList getTodoElementList();
    }
    
    public int addNewElement(TodoElement element)
    {
    	Log.d(TAG, "Adding new Element to List");
    	assertNotNull( "Null Pointer", element );
    	
    	this._list.add(element);
    	return this._list.size();
    }
    
    public TodoElement getElement(int index)
    {
    	Log.d(TAG, String.format( "Retreiving object [%d]" , index) );
    	assertTrue("Bad index: " + index, ( index > 0 ) && (index < this._list.size()) );
    	
    	return this._list.get(index);
    }

	@Override
	public Iterator<TodoElement> iterator() {		
		return this._list.iterator();
	}

	@Override
	protected void _set(HashMap<String, String> hmap) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public HashMap<String, String> get() {
		HashMap<String, String> newMap = new HashMap<String, String>();
		return newMap;
	}

	@Override
	public boolean testIntegrity() {
		// TODO Auto-generated method stub
		return false;
	}
}

