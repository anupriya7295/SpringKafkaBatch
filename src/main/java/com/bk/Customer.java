package com.bk;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

	private String id;
	private String name;
	
	@Override
	  public String toString() {
	    Gson gson = new GsonBuilder().create();
	    return gson.toJson(this);
	  }

}
