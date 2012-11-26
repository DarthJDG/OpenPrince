package org.openprince.dat.res;

import java.util.HashMap;

public class ResourceManager {
	private static ResourceManager mInstance;

	public static ResourceManager getInstance() {
		if (mInstance == null) {
			mInstance = new ResourceManager();
		}
		return mInstance;
	}

	HashMap<Integer, Resource> mItems;

	private ResourceManager() {
		mItems = new HashMap<Integer, Resource>();
	}

	public void register(Resource res) {
		mItems.put(res.id, res);
	}

	public int getCount() {
		return mItems.size();
	}

	public Resource getById(int id) {
		return mItems.get(id);
	}
}
