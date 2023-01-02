package com.KnowledgeTest.Model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Pokemon implements Comparable<Pokemon>{
	
	private String name;
	private int base_experience;
	private int height;
	private int weight;
	
	@Override
	public int compareTo(Pokemon o) {
		return this.base_experience - o.base_experience;
	}
	
	public int compareToHeight(Pokemon o) {
		return this.height - o.height;
	}
	
	public int compareToWeight(Pokemon o) {
		return this.weight - o.weight;
	}
	
}