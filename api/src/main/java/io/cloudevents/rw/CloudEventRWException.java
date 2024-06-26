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

package io.cloudevents.rw;

import io.cloudevents.lang.Nullable;

/**
 * This class is the exception Protocol Binding and Event Format implementers can use to signal errors while serializing/deserializing CloudEvent.
 */
public class CloudEventRWException extends RuntimeException {

    /**
     * The kind of error that happened while serializing/deserializing
     */
    public enum CloudEventRWExceptionKind {
        /**
         * Spec version string is not recognized by this particular SDK version.
         */
        INVALID_SPEC_VERSION,
        /**
         * The attribute name is not a valid/known context attribute.
         */
        INVALID_ATTRIBUTE_NAME,
        /**
         * The extension name is not valid because it doesn't follow the naming convention enforced by the CloudEvents spec.
         *
         * @see <a href="https://github.com/cloudevents/spec/blob/v1.0/spec.md#attribute-naming-convention">naming convention</a>
         */
        INVALID_EXTENSION_NAME,
        /**
         * The attribute/extension type is not valid.
         */
        INVALID_ATTRIBUTE_TYPE,
        /**
         * The attribute/extension value is not valid.
         */
        INVALID_ATTRIBUTE_VALUE,
        /**
         * The data type is not valid.
         */
        INVALID_DATA_TYPE,
        /**
         * Error while converting CloudEventData.
         */
        DATA_CONVERSION,
        /**
         * Invalid content type or spec version
         */
        UNKNOWN_ENCODING,
        /**
         * Other error.
         */
        OTHER
    }

    private final CloudEventRWExceptionKind kind;

    private CloudEventRWException(CloudEventRWExceptionKind kind, Throwable cause) {
        super(cause);
        this.kind = kind;
    }

    private CloudEventRWException(CloudEventRWExceptionKind kind, String message) {
        super(message);
        this.kind = kind;
    }

    private CloudEventRWException(CloudEventRWExceptionKind kind, String message, Throwable cause) {
        super(message, cause);
        this.kind = kind;
    }

    /**
     * @return the {@link CloudEventRWExceptionKind} associated to this exception instance.
     */
    public CloudEventRWExceptionKind getKind() {
        return kind;
    }

    /**
     * @param specVersion the invalid input spec version
     * @return a new {@link CloudEventRWException} instance
     */
    public static CloudEventRWException newInvalidSpecVersion(String specVersion) {
        return new CloudEventRWException(
            CloudEventRWExceptionKind.INVALID_SPEC_VERSION,
            "Invalid specversion: " + specVersion
        );
    }

    /**
     * @param attributeName the invalid attribute name
     * @return a new {@link CloudEventRWException} instance
     */
    public static CloudEventRWException newInvalidAttributeName(String attributeName) {
        return new CloudEventRWException(
            CloudEventRWExceptionKind.INVALID_ATTRIBUTE_NAME,
            "Invalid attribute: " + attributeName
        );
    }

    /**
     * @param extensionName the invalid extension name
     * @return a new {@link CloudEventRWException} instance
     */
    public static CloudEventRWException newInvalidExtensionName(String extensionName) {
        return new CloudEventRWException(
            CloudEventRWExceptionKind.INVALID_EXTENSION_NAME,
            "Invalid extensions name: " + extensionName
        );
    }

    /**
     * @param attributeName the invalid attribute name
     * @param clazz         the type of the attribute
     * @return a new {@link CloudEventRWException} instance
     */
    public static CloudEventRWException newInvalidAttributeType(String attributeName, Class<?> clazz) {
        return new CloudEventRWException(
            CloudEventRWExceptionKind.INVALID_ATTRIBUTE_TYPE,
            "Invalid attribute/extension type for \"" + attributeName + "\": " + clazz.getCanonicalName()
        );
    }

    public static CloudEventRWException newInvalidAttributeType(String attributeName,Object value){
        return new CloudEventRWException(
            CloudEventRWExceptionKind.INVALID_ATTRIBUTE_TYPE,
            "Invalid attribute/extension type for \""
                + attributeName
                + "\": Type" + value.getClass().getCanonicalName()
                + ". Value: " + value

        );
    }

    /**
     * @param attributeName the invalid attribute name
     * @param value         the value of the attribute
     * @param cause         an optional cause identifying the eventual validation error
     * @return a new {@link CloudEventRWException} instance
     */
    public static CloudEventRWException newInvalidAttributeValue(String attributeName, Object value, @Nullable Throwable cause) {
        return new CloudEventRWException(
            CloudEventRWExceptionKind.INVALID_ATTRIBUTE_VALUE,
            "Invalid attribute/extension value for \"" + attributeName + "\": " + value,
            cause
        );
    }

    /**
     * @param actual  the actual data type
     * @param allowed the list of allowed data types
     * @return a new {@link CloudEventRWException} instance
     */
    public static CloudEventRWException newInvalidDataType(String actual, String... allowed) {
        String message;
        if (allowed.length == 0) {
            message = "Invalid data type: " + actual;
        } else {
            message = "Invalid data type: " + actual + ". Allowed: " + String.join(", ", allowed);
        }
        return new CloudEventRWException(
            CloudEventRWExceptionKind.INVALID_DATA_TYPE,
            message
        );
    }

    /**
     * @param cause the cause of the conversion failure
     * @param from  the input data type
     * @param to    the target data type
     * @return a new {@link CloudEventRWException} instance
     */
    public static CloudEventRWException newDataConversion(Throwable cause, String from, String to) {
        return new CloudEventRWException(
            CloudEventRWExceptionKind.DATA_CONVERSION,
            "Error while trying to convert data from " + from + " to " + to,
            cause
        );
    }

    /**
     * @return a new {@link CloudEventRWException} instance.
     */
    public static CloudEventRWException newUnknownEncodingException() {
        return new CloudEventRWException(
            CloudEventRWExceptionKind.UNKNOWN_ENCODING,
            "Could not parse. Unknown encoding. Invalid content type or spec version"
        );
    }

    /**
     * This wraps a {@link Throwable} in a new generic instance of this exception.
     *
     * @param cause the cause of the exception
     * @return a new {@link CloudEventRWException} instance
     */
    public static CloudEventRWException newOther(Throwable cause) {
        return new CloudEventRWException(
            CloudEventRWExceptionKind.OTHER,
            cause
        );
    }

    /**
     *  An exception for use where none of the other variants are
     *  appropriate.
     *
     * @param msg A description error message.
     * @return a new {@link CloudEventRWException}
     */
    public static CloudEventRWException newOther(String msg){
        return new CloudEventRWException(CloudEventRWExceptionKind.OTHER, msg);
    }
}
