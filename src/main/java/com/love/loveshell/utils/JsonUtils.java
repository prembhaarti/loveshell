package com.love.loveshell.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.StdSubtypeResolver;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

import java.io.IOException;
import java.util.Base64;
import java.util.TimeZone;

public class JsonUtils {
	private static ObjectMapper objectMapper;

	public static ObjectMapper getObjectMapper() {
		return getObjectMapper(false);
	}

	public static ObjectMapper getObjectMapper(boolean createNewInstance) {

		if (objectMapper != null && !createNewInstance)
			return objectMapper;

		ObjectMapper objectMapperInstance = new ObjectMapper();
		enrichObjectMapper(objectMapperInstance);
		// newInstance request should not override global ObjectMapper instance
		if (!createNewInstance)
			objectMapper = objectMapperInstance;

		return objectMapperInstance;
	}

	public static <DeserObject> DeserObject deser(String payload, Class<DeserObject> clazz) {
		try {
			return getObjectMapper().readValue(payload, clazz);
		} catch (IOException e) {
			throw new RuntimeException("Not able to deser to: " + clazz + " payload: " + payload, e);
		}
	}

	public static <DeserObject> DeserObject deser(byte[] payload, Class<DeserObject> clazz) {
		try {
			return getObjectMapper().readValue(payload, clazz);
		} catch (IOException e) {
			throw new RuntimeException("Not able to deser to: " + clazz + " payload: " + payload, e);
		}
	}

	public static String serialise(Object data) {
		try {
			return getObjectMapper().writeValueAsString(data);
		} catch (IOException e) {
			throw new RuntimeException("Not able to serialise : " + data, e);
		}
	}

	public static byte[] toBytes(Object data) {
		try {
			return getObjectMapper().writeValueAsBytes(data);
		} catch (IOException e) {
			throw new RuntimeException("Not able to getBytes : " + data);
		}
	}

	public static byte[] decodeToBytes(Object object) {
		return Base64.getDecoder().decode(object.toString());
	}

	public static String encodeBytes(byte[] bytes) {
		return Base64.getEncoder().encodeToString(bytes);
	}

	public static void enrichObjectMapper(ObjectMapper objectMapperInstance) {
		objectMapperInstance.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
		objectMapperInstance.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		objectMapperInstance.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
		objectMapperInstance.registerModule(new Jdk8Module());
		objectMapperInstance.setSubtypeResolver(new StdSubtypeResolver());
		objectMapperInstance.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
	}
}

