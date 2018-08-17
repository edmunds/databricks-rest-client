/*
 * Copyright 2018 Edmunds.com, Inc.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.edmunds.rest.databricks;

import java.util.Map;

/**
 *
 */
public interface DatabricksRestClient {
    /**
     * Performs the given query.
     * @param requestMethod Type of request method. Only supports GET and POST methods.
     * @param path Path of the request URL
     * @param data Any data needed for the request
     * @return Returns a byte array of the response from the server
     * @throws DatabricksRestException
     */
    byte[] performQuery(RequestMethod requestMethod, String path, Map<String, Object> data) throws DatabricksRestException;

    /**
     * Returns the host string.
     * @return Host string.
     */
    String getHost();
}


