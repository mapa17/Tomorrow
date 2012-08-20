/*******************************************************************************
 * Copyright (c) 2012 Pasieka Manuel , mapa17@posgrado.upv.es.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     Pasieka Manuel , mapa17@posgrado.upv.es - initial API and implementation
 ******************************************************************************/
package com.Salsalectric.main;

import org.mapa.R;

import com.Salsalectric.Fragments.EmptyFragment;
import com.Salsalectric.Fragments.TodoElementEditFragment;
import com.Salsalectric.Fragments.TodoElementListFragment;
import com.Salsalectric.Fragments.TodoElementEditFragment.OnElementUpdate;
import com.Salsalectric.Fragments.TodoElementListFragment.OnElementClickListener;
import com.Salsalectric.Fragments.TodoElementListFragment.OnNewElementClick;
import com.Salsalectric.main.TodoElementList.ProvideTodoElementList;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;


public class MainActivity extends Activity
	implements TodoElementList.ProvideTodoElementList
	,TodoElementListFragment.OnElementClickListener
	,TodoElementListFragment.OnNewElementClick
	,TodoElementEditFragment.OnElementUpdate 
	{
    /** Called when the activity is first created. */
	
	private static final String TAG = "MainActiviy";
	private Boolean twoFragment;
	
	private TodoElementList todoList;
	private TodoElementEditFragment _ElementEditFragment;
	private EmptyFragment _EmptyFragment;
	
	
	public MainActivity(){
		Log.d(TAG, "Creating Main Activity");
        this._loadTodoList();      
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // Check whether the activity is using the layout version with
        // the fragment_container FrameLayout. If so, we must add the first fragment
        if (findViewById(R.id.fragment_container) != null) {

        	Log.i(TAG, "Using the single Fragment Layout!");
        	this.twoFragment = false;
            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create an instance of ExampleFragment
            TodoElementListFragment firstFragment = new TodoElementListFragment();

            // In case this activity was started with special instructions from an Intent,
            // pass the Intent's extras to the fragment as arguments
            firstFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getFragmentManager().beginTransaction().add(R.id.list_fragment, firstFragment).commit();
        }
        else 
        {
        	Log.i(TAG, "Using the double Fragment Layout!");
        	// Create an instance of ExampleFragment
        	this._ElementEditFragment = new TodoElementEditFragment();
            this._EmptyFragment = new EmptyFragment();

            // In case this activity was started with special instructions from an Intent,
            // pass the Intent's extras to the fragment as arguments
            this._ElementEditFragment.setArguments(getIntent().getExtras());
            this._EmptyFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getFragmentManager().beginTransaction().add(R.id.edit_fragment_container, this._ElementEditFragment).commit();
            getFragmentManager().beginTransaction().hide(this._ElementEditFragment).commit();
            getFragmentManager().beginTransaction().add(R.id.edit_fragment_container, this._EmptyFragment).commit();
        	
        	this.twoFragment = true;
        }
    }

    private enum SecondaryView { EMPTY, SHOW_ELEMENT }
    private void _setSecondaryView(SecondaryView show)
    {
    	if(show == SecondaryView.EMPTY){
    		Log.d(TAG, "Switching to Empty Fragment");
    		getFragmentManager().beginTransaction().show(this._EmptyFragment).commit();
    		getFragmentManager().beginTransaction().hide(this._ElementEditFragment).commit();    		
    		//getFragmentManager().beginTransaction().replace(R.id.edit_fragment_container, this._EmptyFragment).commit();
    	}
    	
    	if(show == SecondaryView.SHOW_ELEMENT) {
    		Log.d(TAG, "Switching to ShowElement Fragment");
    		getFragmentManager().beginTransaction().show(this._ElementEditFragment).commit();
    		getFragmentManager().beginTransaction().hide(this._EmptyFragment).commit();    		
    		//getFragmentManager().beginTransaction().replace(R.id.edit_fragment_container, this._ElementEditFragment).commit();
    	}
    }
    
    private void _loadTodoList()
    {
    	this.todoList = new TodoElementList();
    }
    
	@Override
	public TodoElementList getTodoElementList() {
		return todoList;
	}

	@Override
	public void onTodoElementSelected(TodoElement element) {
//		TodoElementEditFragment f = (TodoElementEditFragment)getFragmentManager().findFragmentById(R.id.edit_fragment);
//		if(f == null){
//			Log.e(TAG, "Cant find TodoElementEditFragment");
//			throw new RuntimeException("Cant find TodoElementEditFragment!");
//		}
//		f.displayElement(element);
		this._setSecondaryView(SecondaryView.SHOW_ELEMENT);
		this._ElementEditFragment.displayElement(element);
	}

	@Override
	public void updateElement(TodoElement element) {
		Log.d(TAG, "Updateing TodoListFragment ...");
		TodoElementListFragment f = (TodoElementListFragment)getFragmentManager().findFragmentById(R.id.list_fragment);
		if(f == null){
			Log.e(TAG, "Cant find TodoElementListFragment");
			throw new RuntimeException("Cant find TodoElementListFragment!");
		}
		f.updateList();
		this._setSecondaryView(SecondaryView.EMPTY);
	}

	@Override
	public void eventNewElementClick() {
		
		try{
			Log.d(TAG, "NewElement event");
					
			TodoElement newElement = TodoElement.EmptyTodoElementFactory(this.getApplicationContext());
			this.todoList.addNewElement(newElement);		
			this.onTodoElementSelected(newElement);
			this.updateElement(newElement);
		} catch (Exception e){
			throw new RuntimeException("Failed to add new Element!", e);
		}
		
	}

	
}
