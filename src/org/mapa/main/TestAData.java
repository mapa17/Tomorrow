package org.mapa.main;

import java.util.HashMap;

import org.mapa17.utils.AData;

public class TestAData extends AData {

	public static TestAData createEmptyElement() {
		TestAData newE = new TestAData();
		return newE;
	}

	public static TestAData createFilledElement(HashMap<String, String> hmap) {
		TestAData newE = TestAData.createEmptyElement();
		newE.set(hmap);
		return newE;
	}
	
	protected TestAData(){
		super();
		//TODO: initialize
	}

	@Override
	protected void _set(HashMap<String, String> hmap) {
		// TODO Auto-generated method stub

	}

	@Override
	public HashMap<String, String> get() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean testIntegrity() {
		// TODO Auto-generated method stub
		return false;
	}

}

