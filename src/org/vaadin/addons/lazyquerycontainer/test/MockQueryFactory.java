/**
 * Copyright 2010 Tommi S.E. Laukkanen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.vaadin.addons.lazyquerycontainer.test;

import org.vaadin.addons.lazyquerycontainer.Query;
import org.vaadin.addons.lazyquerycontainer.QueryDefinition;
import org.vaadin.addons.lazyquerycontainer.QueryFactory;

/**
 * Mock implementation of QueryFactory interface for JUnit tests and example application.
 * @author Tommi S.E. Laukkanen
 */
public class MockQueryFactory implements QueryFactory {

	private QueryDefinition definition;
	private int resultSize;
	private int batchQueryMinTime;
	private int batchQueryMaxTime;
	
	public MockQueryFactory(int resultSize, int batchQueryMinTime, int batchQueryMaxTime) {
		this.resultSize=resultSize;
		this.batchQueryMinTime=batchQueryMinTime;
		this.batchQueryMaxTime=batchQueryMaxTime;
	}
	
	@Override
	public void setQueryDefinition(QueryDefinition definition) {
		this.definition=definition;
	}
	
	@Override
	public Query constructQuery(Object[] sortPropertyIds, boolean[] ascendingStates) {
		return new MockQuery(definition,resultSize,sortPropertyIds, ascendingStates,batchQueryMinTime,batchQueryMaxTime);
	}
	
}
