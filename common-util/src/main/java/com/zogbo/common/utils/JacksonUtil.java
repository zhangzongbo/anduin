package com.zogbo.common.utils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;


import java.io.IOException;
import java.io.StringWriter;
import java.text.DateFormat;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

/**
 * json tools
 */
@Slf4j
public class JacksonUtil {
	/**
	 * 反序列化JSON单个对象字符串到JAVA对象实例
	 * 
	 * @param json
	 *                JSON字符串
	 * @param type
	 *                实体类型
	 * @return 类型实例
	 */
	public static <T> T jsonToObj(String json, Class<T> type) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			return objectMapper.readValue(json, type);
		} catch (JsonParseException e) {
			log.error("the json conversion errors", e);
			return null;
		} catch (JsonMappingException e) {
			log.error("the json conversion errors", e);
			return null;
		} catch (IOException e) {
			log.error("the data stream error", e);
			return null;
		}
	}

	/**
	 * 序列化JAVA对象实例到JSON字符串
	 * 
	 * @param obj
	 *                JAVA对象实例
	 * @return JSON字符串
	 */
	public static String objToJson(Object obj) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			if (obj == null) {
				// return "{\"message\":\"没有数据\"}";
				return "\"\"";
			}
			return objectMapper.writeValueAsString(obj);
		} catch (JsonGenerationException e) {
			log.error("the json conversion errors", e);
			return null;
		} catch (JsonMappingException e) {
			log.error("the json conversion errors", e);
			return null;
		} catch (IOException e) {
			log.error("the data stream error", e);
			return null;
		}
	}

	/**
	 * 序列化JAVA对象实例到JSON字符串,时间格式可以格式化
	 * 
	 * @param obj
	 *                JAVA对象
	 * @param df
	 *                　时间格式化(如:SimpleDateFormater)
	 * @return json串
	 */
	public static String objToJson(Object obj, DateFormat df) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.setDateFormat(df);
			if (obj == null) {
				return "\"\"";
			}
			return objectMapper.writeValueAsString(obj);
		} catch (JsonGenerationException e) {
			log.error("the json conversion errors", e);
			return null;
		} catch (JsonMappingException e) {
			log.error("the json conversion errors", e);
			return null;
		} catch (IOException e) {
			log.error("the data stream error", e);
			return null;
		}
	}

	/**
	 * 序列化JAVA对象实例到JSON字符串,时间为timestamp
	 * 
	 * @param obj
	 *                JAVA对象
	 * @return json串
	 */
	public static String objTOJsonWithTimeStamp(Object obj) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(SerializationConfig.Feature.WRITE_DATE_KEYS_AS_TIMESTAMPS, true);
			if (obj == null) {
				return "\"\"";
			}
			return objectMapper.writeValueAsString(obj);
		} catch (JsonGenerationException e) {
			log.error("the json conversion errors", e);
			return null;
		} catch (JsonMappingException e) {
			log.error("the json conversion errors", e);
			return null;
		} catch (IOException e) {
			log.error("the data stream error", e);
			return null;
		}
	}

	public static <T> T jsonToObjWithTimeStamp(String json, Class<T> type) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(SerializationConfig.Feature.WRITE_DATE_KEYS_AS_TIMESTAMPS, true);
			objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			return objectMapper.readValue(json, type);
		} catch (JsonParseException e) {
			log.error("the json conversion errors", e);
			return null;
		} catch (JsonMappingException e) {
			log.error("the json conversion errors", e);
			return null;
		} catch (IOException e) {
			log.error("the data stream error", e);
			return null;
		}
	}

	// list turn json
	public static String listToJson(List list) {
		StringBuilder stringBuilder = new StringBuilder();
		if (list == null || list.size() == 0) {
			// stringBuilder.append("{\"message\":\"没有数据\"}");
			stringBuilder.append("\"\"");
			return stringBuilder.toString();
		} else {
			stringBuilder.append("[");
		}

		int count = 0;
		for (Object object : list) {
			if (count++ > 0) {
				stringBuilder.append(",");
			}
			stringBuilder.append(JacksonUtil.objToJson(object));
		}
		stringBuilder.append("]");
		return stringBuilder.toString();
	}


	// map转换为json
	public static String mapToJson(Map<String, String> map) {
		ObjectMapper objectMapper = new ObjectMapper();
		StringWriter stringWriter = new StringWriter();
		try {
			objectMapper.writeValue(stringWriter, map);
		} catch (IOException e) {
			log.error("Cannot write json.", e);
		}
		return stringWriter.toString();
	}

	// json转换为map
	public static Map<String, String> jsonToMap(String json) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.readValue(json, Map.class);
		} catch (IOException e) {
			log.error("Cannot parse json to map.", e);
		}
		return null;
	}

	// json转换为map
	public static Map jsonToObjectMap(String json) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.readValue(json, Map.class);
		} catch (IOException e) {
			log.error("Cannot parse json to map.", e);
		}
		return null;
	}
}
