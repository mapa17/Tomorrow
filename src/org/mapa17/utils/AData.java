package org.mapa17.utils;

import java.util.HashMap;
import java.util.Vector;


public abstract class AData {

	public interface ViewUpdateListener{
		void UpdateView(AData data);
		void DeleteView(AData data);
		AViewController GetView();
	}

	private Vector<ViewUpdateListener> _listeners;
	
	protected AData(){
		this._listeners = new Vector<AData.ViewUpdateListener>();
	}
	
	private enum ListenerEvent { UPDATE, REMOVE };
	
	public void remove(){
		this._notifyListeners(ListenerEvent.REMOVE);
	}
	
	protected abstract void _set(HashMap<String, String> hmap);
	
	public void set(HashMap<String, String> hmap){
		this._set(hmap);
		this._notifyListeners(ListenerEvent.UPDATE);
	}
	
	public abstract HashMap<String, String> get();
	
	private void _notifyListeners(ListenerEvent type){
		switch(type)
		{
			case UPDATE:{
				for( ViewUpdateListener e : this._listeners){
					e.UpdateView(this);
				}
				break;
			}
			case REMOVE:{
				for( ViewUpdateListener e : this._listeners){
					e.DeleteView(this);
				}
				break;
			}
		}
	}
	
	public void registerViewListener(ViewUpdateListener listener){
		this._listeners.add(listener);
	}
	
	public String getStrDescription() {
		return String.format("%s: [%s]", this.getClass().toString(), this.get().toString() );
	}
	
	public abstract boolean testIntegrity();
}

