package Algorithms;

import graphAlgorithms.distance;

public class vertexKey {
	
	int vertex;
	long weight;
	
	public vertexKey(int v,long w)
	{
		this.vertex = v;
		this.weight = w;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof vertexKey) {
			vertexKey vk = (vertexKey)o;
			return (this.vertex == vk.vertex);
		}
		return false;
	}

}
