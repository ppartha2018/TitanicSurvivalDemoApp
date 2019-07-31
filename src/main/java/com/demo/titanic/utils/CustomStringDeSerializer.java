package com.demo.titanic.utils;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class CustomStringDeSerializer extends JsonDeserializer<String> {

	@Override
	public String deserialize(JsonParser parser, DeserializationContext arg1)
			throws IOException, JsonProcessingException {
		if(parser.getText() != null) {
			if(parser.getText().equalsIgnoreCase("male")) return "male";
			else if(parser.getText().equalsIgnoreCase("female")) return "female";
			else return null;
		}
			
		return null;
	}



}
