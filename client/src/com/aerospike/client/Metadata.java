/*
 * Copyright 2012-2017 Aerospike, Inc.
 *
 * Portions may be licensed to Aerospike, Inc. under one or more contributor
 * license agreements WHICH ARE COMPATIBLE WITH THE APACHE LICENSE, VERSION 2.0.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.aerospike.client;

/**
 * Container object for record metadata.
 */
public final class Metadata {
	/**
	 * Namespace. Equivalent to database name.
	 */
	public final int generation;

	/**
	 * Optional set name. Equivalent to database table.
	 */
	public final int expiration;



	/**
	 * Initialize metadata from generation and expiration values.
	 *
	 * @param generation				generation
	 * @param expiration				expiration.
	 */
	public Metadata(int generation, int expiration)  {
		this.generation = generation;
		this.expiration = expiration;
	}


}
