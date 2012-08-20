package org.mapa17.utils;


import android.view.View.OnClickListener;

public abstract class AViewController implements AData.ViewUpdateListener, OnClickListener{

	private AData object;
	
	@Override
	public abstract void UpdateView(AData data);

	@Override
	public abstract void DeleteView(AData data);

	
	protected AViewController(AData object){
		this.object = object;
		this.object.registerViewListener(this);
	}
	
	public AData getObject(){
		return object;
	}	
	
	@Override
	public AViewController GetView() {
		return this;
	}

	public abstract boolean testIntegrity();
}

