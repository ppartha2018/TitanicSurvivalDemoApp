package com.demo.titanic.utils;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class NumericBooleanDeserializer extends JsonDeserializer<Boolean> {


	/*
	 * Custom hook to deserialize a specific data type which parsing csv records.
	 * 
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.databind.JsonDeserializer#deserialize(com.fasterxml.jackson.core.JsonParser, com.fasterxml.jackson.databind.DeserializationContext)
	 */
	@Override
	public Boolean deserialize(JsonParser parser, DeserializationContext arg1)
			throws IOException, JsonProcessingException {
		return !"0".equals(parser.getText());	
	}       
}