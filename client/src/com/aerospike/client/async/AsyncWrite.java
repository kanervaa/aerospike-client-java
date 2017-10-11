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
package com.aerospike.client.async;

import com.aerospike.client.AerospikeException;
import com.aerospike.client.Bin;
import com.aerospike.client.Key;
import com.aerospike.client.Metadata;
import com.aerospike.client.Operation;
import com.aerospike.client.cluster.Partition;
import com.aerospike.client.command.Buffer;
import com.aerospike.client.listener.WriteListener;
import com.aerospike.client.policy.WritePolicy;

public final class AsyncWrite extends AsyncCommand implements AsyncSingleCommand {
	private final WriteListener listener;
	private final WritePolicy writePolicy;
	private final Key key;
	private final Bin[] bins;
	private final Operation.Type operation;
	private int generation;
	private int expiration;

	public AsyncWrite(WriteListener listener, WritePolicy writePolicy, Key key, Bin[] bins, Operation.Type operation) {
		super(writePolicy, new Partition(key), null, false, true);
		this.listener = listener;
		this.writePolicy = writePolicy;
		this.key = key;
		this.bins = bins;
		this.operation = operation;
	}
	
	@Override
	protected void writeBuffer() {
		setWrite(writePolicy, operation, key, bins);
	}

	@Override
	public void parseResult() {
		generation = Buffer.bytesToInt(dataBuffer, 6);
		expiration = Buffer.bytesToInt(dataBuffer, 10);
		if (resultCode != 0) {
			throw new AerospikeException(resultCode);
		}
	}

	@Override
	protected void onSuccess() {
		if (listener != null) {
			listener.onSuccess(key, new Metadata(generation, expiration));
		}
	}	

	@Override
	protected void onFailure(AerospikeException e) {
		if (listener != null) {
			listener.onFailure(e);
		}
	}
}
