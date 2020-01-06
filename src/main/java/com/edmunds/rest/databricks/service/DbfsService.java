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

package com.edmunds.rest.databricks.service;

import com.edmunds.rest.databricks.DTO.DbfsReadDTO;
import com.edmunds.rest.databricks.DTO.dbfs.FileInfoDTO;
import com.edmunds.rest.databricks.DatabricksRestException;
import java.io.IOException;
import java.io.InputStream;

/**
 * A wrapper around the databricks DbfsService.
 *
 * @see <a href="https://docs.databricks.com/api/latest/dbfs.html">Documentation</a>
 */
public interface DbfsService {

  /**
   * Removes a dbfs path.
   * @see <a href="https://docs.databricks.com/api/latest/dbfs.html#delete">Documentation</a>
   * @param path the path to delete
   * @param recursive whether or not it should be recursive
   * @throws IOException any other errors
   * @throws DatabricksRestException any errors in request
   */
  void rm(String path, boolean recursive) throws IOException, DatabricksRestException;

  /**
   * Get info of a specific file or directory on dbfs.
   * @see <a href="https://docs.databricks.com/api/latest/dbfs.html#get-status">Documentation</a>
   * @param path the dbfs path
   * @return the file info status object
   * @throws IOException any other errors
   * @throws DatabricksRestException any errors in request
   */
  FileInfoDTO getInfo(String path) throws IOException, DatabricksRestException;

  /**
   * Lists files and directories in a dbfs path.
   * @see <a href="https://docs.databricks.com/api/latest/dbfs.html#list">Documentation</a>
   * @param path the dbfs path
   * @return an array of file info objects.
   * @throws IOException any other errors
   * @throws DatabricksRestException any errors in request
   */
  FileInfoDTO[] ls(String path) throws IOException, DatabricksRestException;

  /**
   * Makes a directory (and parent directories) at a given path.
   * @see <a href="https://docs.databricks.com/api/latest/dbfs.html#mkdirs">Documentation</a>
   * @param path the dbfs path to create
   * @throws IOException any other errors
   * @throws DatabricksRestException any errors in request
   */
  void mkdirs(String path) throws IOException, DatabricksRestException;

  /**
   * Moves a file from one path to another.
   * @see <a href="https://docs.databricks.com/api/latest/dbfs.html#move">Documentation</a>
   * @param sourcePath the source dbfs path
   * @param destinationPath the destination dbfs path
   * @throws IOException any other errors
   * @throws DatabricksRestException any errors with request
   */
  void mv(String sourcePath, String destinationPath) throws IOException, DatabricksRestException;

  /**
   * Will create a file and write to that file on dbfs.
   * Combination of:
   * @see <a href="https://docs.databricks.com/api/latest/dbfs.html#create">Documentation</a>
   * and
   * @see <a href="https://docs.databricks.com/api/latest/dbfs.html#put">Documentation</a>
   * @param path the path to create a file/write to
   * @param inputStream the stream to output to dbfs
   * @param overwrite whether or not you want to overwrite the file
   * @throws IOException if any other errors
   * @throws DatabricksRestException any errors in request
   */
  void write(String path, InputStream inputStream, boolean overwrite)
      throws IOException, DatabricksRestException;

  /**
   * Reads a file from dbfs.
   * @see <a href="https://docs.databricks.com/api/latest/dbfs.html#read">Documentation</a>
   * @param path the dbfs path to read from
   * @param offset the offset of the file you want to read from
   * @param length how many bytes you want to read
   * @return the read DTO object
   * @throws IOException any other errors
   * @throws DatabricksRestException any errors in request
   */
  DbfsReadDTO read(String path, long offset, long length)
      throws IOException, DatabricksRestException;

  /**
   * Will read a whole file.
   * @see <a href="https://docs.databricks.com/api/latest/dbfs.html#read">Documentation</a>
   * @param path the dbfs path to read from
   * @return the dbfs read DTO object
   * @throws IOException any other errors
   * @throws DatabricksRestException any errors in request
   */
  DbfsReadDTO read(String path) throws IOException, DatabricksRestException;

}