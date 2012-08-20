/**
* Simple Comment
*/
package com.Salsalectric.Fragments;

import static junit.framework.Assert.assertNotNull;

import java.util.HashMap;

import org.mapa.R;

import com.Salsalectric.Fragments.TodoElementListFragment.OnElementClickListener;
import com.Salsalectric.Fragments.TodoElementListFragment.OnNewElementClick;
import com.Salsalectric.main.TodoElement;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * @author dd
 *
 */
public class TodoElementEditFragment extends Fragment
	implements OnClickListener{

	private static final String TAG = "TodoElementEditFragment";
	
	private TodoElement _activeElement;
	private Activity _parrentActivity;
	private OnElementUpdate _OnElementUpdateListener;
	private View _form;
	/**
	 * 
	 */
	public TodoElementEditFragment() {
		// TODO Auto-generated constructor stub
	}
	
    public interface OnElementUpdate {
        /** Called by HeadlinesFragment when a list item is selected */
        public void updateElement(TodoElement element);
    }
	
  //First thing called! When the main activity is attaching the fragment to itself
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        Log.d(TAG, "onAttach(): Getting all important objects from calling activity");
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception.
        try {
        	this._parrentActivity = activity;
        	this._OnElementUpdateListener = (OnElementUpdate) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + "parrent Activity has to implement OnElementUpdate Interface");
        }
    }
    
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
        Bundle savedInstanceState) {

        // If activity recreated (such as from screen rotate), restore
        // the previous article selection set by onSaveInstanceState().
        // This is primarily necessary when in the two-pane layout.
        if (savedInstanceState != null) {
            //mCurrentPosition = savedInstanceState.getInt(ARG_POSITION);
        }
        
        assertNotNull("No parrent Activity set!", this._parrentActivity);
        this._form = inflater.inflate(R.layout.edit_fragment, container, false); 
        Button button = (Button) this._form.findViewById(R.id.button_acceptChanges);
        assertNotNull("Cant find Accept Button!", button);
        button.setOnClickListener(this);
        
        // Inflate the layout for this fragment
        return this._form;
    }
	
	
	public void displayElement(TodoElement element){
		this._activeElement = element;
		((EditText)this._form.findViewById(R.id.name)).setText(element.getName());
		((EditText)this._form.findViewById(R.id.description)).setText(element.getDescription());
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.button_acceptChanges ){
			Log.d(TAG, "User has updated Element, notify parrent activity.");
			
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("name", ((EditText)this._form.findViewById(R.id.name)).getText().toString() );
			map.put("desc", ((EditText)this._form.findViewById(R.id.description)).getText().toString() );
			TodoElement.updateElement(this._activeElement, map);
			
			this._OnElementUpdateListener.updateElement(this._activeElement);
		}
		
	}

}

