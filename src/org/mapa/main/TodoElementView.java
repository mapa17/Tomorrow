package org.mapa.main;

import com.Salsalectric.main.TodoElement;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class TodoElementView extends TextView {

	private TodoElement _element;
	
	private TodoElementView(Context context, TodoElement element) {
		super(context);
		this._element = element;
		// TODO Auto-generated constructor stub
	}

	private TodoElementView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	private TodoElementView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public TodoElement getTodoElement(){
		return this._element;
	}
	
	public static TodoElementView ElementFactory(TodoElement element, Context context){
		TodoElementView e;
		
		e = new TodoElementView(context, element);
		e.setText( element.getName() );
		
		return e;
	}
}

