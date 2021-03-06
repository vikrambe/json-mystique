/*
 * Copyright (c) Balajee TM 2016.
 * All rights reserved.
 * License -  @see <a href="http://www.apache.org/licenses/LICENSE-2.0"></a>
 */

/*
 * Created on 25 Aug, 2016 by balajeetm
 * http://www.balajeetm.com
 */
package com.balajeetm.mystique.core.bean;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.balajeetm.mystique.util.gson.bean.lever.MysCon;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

/**
 * The Class StringUtilsMystTurn.
 *
 * @author balajeetm
 */
@Component
public class StringUtilsMystTurn extends AbstractMystTurn {

	/* (non-Javadoc)
	 * @see com.futuresight.util.mystique.AbstractMystTurn#transmute(java.util.List, com.google.gson.JsonObject, com.google.gson.JsonObject, com.google.gson.JsonObject)
	 */
	@Override
	protected JsonElement transmute(List<JsonElement> source, JsonObject deps, JsonObject aces, JsonObject turn) {
		JsonElement elementSource = mystiqueLever.getFirst(source);
		turn = mystiqueLever.getAsJsonObject(turn, new JsonObject());
		JsonElement granularSource = getGranularSource(elementSource, turn, deps, aces);
		String sourceString = mystiqueLever.getAsString(granularSource, MysCon.EMPTY);
		return execute(sourceString, turn);
	}

	/**
	 * Execute.
	 *
	 * @param input the input
	 * @param turn the turn
	 * @return the json primitive
	 */
	private JsonPrimitive execute(String input, JsonObject turn) {
		JsonPrimitive transform = null;
		String action = mystiqueLever.getAsString(turn.get(MysCon.ACTION), MysCon.TRIM);
		switch (action) {
		case MysCon.TRIM:
			transform = new JsonPrimitive(StringUtils.trim(input));
			break;

		case MysCon.TRIM_TO_EMPTY:
			transform = new JsonPrimitive(StringUtils.trimToEmpty(input));
			break;

		case MysCon.SUBSTRING_AFTER_LAST:
			String separator = mystiqueLever.getAsString(turn.get(MysCon.SEPARATOR), MysCon.EMPTY);
			transform = new JsonPrimitive(StringUtils.substringAfterLast(input, separator));
			break;

		default:
			break;
		}
		return transform;
	}
}
