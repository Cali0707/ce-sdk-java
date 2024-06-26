/*
 * Copyright 2018-Present The CloudEvents Authors
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package io.cloudevents.protobuf;

import com.google.protobuf.Any;
import com.google.protobuf.Message;
import io.cloudevents.CloudEventData;

/**
 * A {@link CloudEventData} that supports access to a protocol
 * buffer message.
 */
public interface ProtoCloudEventData extends CloudEventData {

    /**
     * Convenience helper to wrap a Protobuf {@link Message} as
     * CloudEventData.
     *
     * @param protoMessage The message to wrap
     * @return The wrapping CloudEventData
     */
    static CloudEventData wrap(Message protoMessage) {
        return new ProtoDataWrapper(protoMessage);
    }

    /**
     * Gets the protobuf {@link Any} representation of this data.
     *
     * @return The data as an {@link Any}
     */
    Any getAny();
}
