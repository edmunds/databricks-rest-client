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

package com.edmunds.rest.databricks.restclient;

import org.apache.http.impl.client.HttpClientBuilder;

/**
 * Responsible for building an http client builder.
 * It returns the builder rather then the client itself so that implementations can be more easily chained and
 * customized.
 */
public interface HttpClientBuilderFactory {

  /**
   * Returns the HttpClientBuilder which will be used by others. Override this method to customize how the builder is
   * created.
   *
   * @return the http client builder.
   */

  HttpClientBuilder createClientBuilder();

}
