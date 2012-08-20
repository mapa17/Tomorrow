/**
* Simple Comment
*/
package com.Salsalectric.Fragments;

import org.mapa.R;
import org.mapa.main.TodoElementView;

import com.Salsalectric.main.TodoElement;
import com.Salsalectric.main.TodoElementList;
import com.Salsalectric.main.TodoElementList.ProvideTodoElementList;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import static junit.framework.Assert.*;


/**
 *
 */
public class TodoElementListFragment extends Fragment implements OnClickListener
{
	private static final String TAG = "TodoElementListFragment";
	private Activity parrentActivity;
	private OnElementClickListener _selectElementListener;
	private OnNewElementClick _newElementListener;
	private TodoElementList todoList;
	private LinearLayout list;
	
	private TextView newElement;
	private int _nElements;
	
	// The container Activity must implement this interface so the Fragment can deliver messages
    public interface OnElementClickListener {
        /** Called by HeadlinesFragment when a list item is selected */
        public void onTodoElementSelected(TodoElement element);
    }

    public interface OnNewElementClick{
    	public void eventNewElementClick();
    }
    
    //Called when creating the Fragment, after attaching to its activity
    //All heavy loading should be done here
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    //Called When Fragment is drawn for the first time, or after resume of operation
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    	View view = null;
        try
        {
	        Log.d(TAG, "onCreateView(): Creating Layout and adding default buttons!");
	        
	        view = inflater.inflate(R.layout.list_fragment, container, false);
	        // If activity recreated (such as from screen rotate), restore
	        // the previous article selection set by onSaveInstanceState().
	        // This is primarily necessary when in the two-pane layout.
	        if (savedInstanceState != null) {
	           // mCurrentPosition = savedInstanceState.getInt(ARG_POSITION);
	        }
	        
	        this.list = (LinearLayout) view.findViewById(R.id.TodoList);
	        if(this.list == null){
	        	Log.e(TAG, "Cant find TodoList in Layout!");
	        	throw new RuntimeException("Cant locate TodoList in layout!");
	        }	        
	        
	        this.newElement  = new TextView(this.parrentActivity.getApplicationContext());
	        newElement.setText( R.string.AddNewElement );
	        newElement.setId(0);
	        newElement.setOnClickListener(this);
	        
	        this.updateList();
	        
        } catch (Exception e){
        	throw new RuntimeException("Creating TodoElementListFrame failed! Error: ", e);
        }

        // Inflate the layout for this fragment
        return view;
    }

    //First thing called! When the main activity is attaching the fragment to itself
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        Log.d(TAG, "onAttach(): Getting all important objects from calling activity");
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception.
        try {
        	parrentActivity = activity;
        	this._selectElementListener = (OnElementClickListener) activity;
        	this._newElementListener = (OnNewElementClick) activity;
        	this.todoList = ((TodoElementList.ProvideTodoElementList) activity).getTodoElementList();
        	assertNotNull( "Illegal reference!", this.todoList );
        	
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnElementClickListener and TodoElementList.ProvideTodoElementList");
        }
    }

    public void updateList()
    {   
    	Log.d(TAG, "Updateing Todo List ...");
    	
    	this.list.removeAllViews();
    	this.list.addView( newElement, 0);
    	
    	this._nElements = 1;
    	for( TodoElement e : this.todoList ){    		
    		TodoElementView v = TodoElementView.ElementFactory(e, this.parrentActivity.getApplicationContext());
    		v.setId(this._nElements);
    		v.setOnClickListener(this);
    		this.list.addView( v , this._nElements);
    		Log.d(TAG, String.format("Adding Element with id %d", v.getId() ));
    		this._nElements++;
    	}
    	
    	Log.d(TAG, String.format("Have created a list with %d elements", this._nElements) );
    }
    
	@Override
	public void onClick(View v) {
		
		assertTrue("Illegal Selection" , (v.getId() <= this._nElements) && (v.getId() >= 0) );
		
		if(v.getId() == this.newElement.getId()){
			this._addNewElement();
		} else {
			Log.d(TAG, String.format("User selected element %d", v.getId()) );
			
			//Iterate through List and see which of the element was clicked
			TodoElementView selected = (TodoElementView) this.list.findViewById(v.getId());
				assertNotNull( "User clicked on something strange", selected );
			this._selectElementListener.onTodoElementSelected( selected.getTodoElement() );
		}
		
	}

	private void _addNewElement() {
		Log.d(TAG, "Adding new Element to the List");	
		this._newElementListener.eventNewElementClick();
	}

}

