package com.baizhi.springbootselenium.util;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonUtils {

	private static ObjectMapper mapper = new ObjectMapper();

	static {
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	public static Map<String, String> transMapStr(String json) throws JsonParseException, IOException {

		assertEmpty(json);

		JavaType jvt = mapper.getTypeFactory().constructParametricType(HashMap.class, String.class, String.class);

		return mapper.readValue(json, jvt);
	}

	public static Map<String, Object> transMap(String json) throws JsonParseException, IOException {

		assertEmpty(json);

		JavaType jvt = mapper.getTypeFactory().constructParametricType(HashMap.class, String.class, Object.class);

		return mapper.readValue(json, jvt);
	}

	private static void assertEmpty(String json) throws JsonParseException {

		if(StringUtils.isEmpty(json)) {
			throw new JsonParseException(null, json);
		}
	}

	public static List<Map<String, Object>> transList(String json) throws JsonParseException, IOException {

		assertEmpty(json);

		JavaType jvt = mapper.getTypeFactory().constructParametricType(ArrayList.class, HashMap.class);

		return mapper.readValue(json, jvt);
	}

	public static <T> T transBean(String json, Class<T> clazz) throws JsonParseException, IOException {

		assertEmpty(json);

		return mapper.readValue(json, clazz);
	}

	public static <T> List<T> transBeans(String json, Class<T> clazz) throws JsonParseException, IOException {

		assertEmpty(json);

		JavaType jvt = mapper.getTypeFactory().constructParametricType(ArrayList.class, clazz);

		return mapper.readValue(json, jvt);
	}

	public static String transString(Object value) throws JsonProcessingException {

		return mapper.writeValueAsString(value);
	}

	/**
	 * 构造服务器异常返回结果
	 * 
	 * @param desc 错误描述
	 * @return
	 */
	public static String errorInfo(String desc) {

		Map<String, Object> map = new HashMap();
		map.put("result", "false");
		map.put("errorInfo", desc);

		try {
			return mapper.writeValueAsString(map);
		} catch(JsonProcessingException e) {
			StringBuilder sb = new StringBuilder();
			sb.append("{\"result\":\"false\",\"errorInfo\":\"").append(desc).append("\"}");
			return sb.toString();
		}
	}

	public static String errorDesc(String desc) {

		Map<String, Object> map = new HashMap();
		map.put("success", false);
		map.put("desc", desc);

		try {
			return mapper.writeValueAsString(map);
		} catch(JsonProcessingException e) {
			StringBuilder sb = new StringBuilder();
			sb.append("{\"success\":false,\"desc\":\"").append(desc).append("\"}");
			return sb.toString();
		}
	}

	public static String sucess() {

		Map<String, Object> map = new HashMap();
		map.put("success", true);
		map.put("desc", "成功");

		try {
			return mapper.writeValueAsString(map);
		} catch(JsonProcessingException e) {
			return "{\"success\":true,\"desc\":\"成功\"}";
		}
	}

	public static String getLessStr(String primitive) {

		return primitive == null ? null : primitive.length() > 1000 ? primitive.substring(0, 1000) : primitive;
	}
}
