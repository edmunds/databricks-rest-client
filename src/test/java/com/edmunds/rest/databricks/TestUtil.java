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

import com.edmunds.rest.databricks.DTO.ClusterInfoDTO;
import com.edmunds.rest.databricks.DTO.ClusterStateDTO;
import com.edmunds.rest.databricks.DTO.LibraryDTO;
import com.edmunds.rest.databricks.DTO.LibraryFullStatusDTO;
import com.edmunds.rest.databricks.DTO.LibraryInstallStatusDTO;
import com.edmunds.rest.databricks.DTO.RunDTO;
import com.edmunds.rest.databricks.DTO.RunLifeCycleStateDTO;
import com.edmunds.rest.databricks.service.ClusterService;
import com.edmunds.rest.databricks.service.JobService;
import com.edmunds.rest.databricks.service.LibraryService;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.Callable;

public final class TestUtil {
    private TestUtil() {}

    public static String getDefaultClusterId(ClusterService clusterService) throws IOException, DatabricksRestException {
        ClusterInfoDTO[] results = clusterService.list();
        for (ClusterInfoDTO result : results) {
            String name = result.getClusterName();
            if (name.toLowerCase().contains("default")) {
                return result.getClusterId();
            }
        }

        return null;
    }

    public static Callable<Boolean> clusterStatusHasChangedTo(final ClusterStateDTO status, final String clusterId,
                                                       final ClusterService service) {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return Objects.equals(getClusterStatus(clusterId, service), status);
            }
        };
    }

    public static ClusterStateDTO getClusterStatus(String clusterId, ClusterService service) throws IOException,
        DatabricksRestException {
        ClusterInfoDTO clusterInfo = service.getInfo(clusterId);

        return clusterInfo.getState();
    }

    public static Callable<Boolean> runLifeCycleStateHasChangedTo(final RunLifeCycleStateDTO state, final long run_id,
                                                            final JobService service) {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return Objects.equals(getRunState(run_id, service), state);
            }
        };
    }

    private static RunLifeCycleStateDTO getRunState(long run_id, final JobService service) throws IOException,
        DatabricksRestException {
        RunDTO run = service.getRun(run_id);
        return run.getState().getLifeCycleState();
    }

    public static Callable<Boolean> jarLibraryStatusHasChangedTo(final LibraryInstallStatusDTO status,
                                                                 final String clusterId,
                                                                 final LibraryDTO library,
                                                                 final LibraryService service) {
        return new Callable<Boolean>() {
            public Boolean call() throws Exception {
                return Objects.equals(getLibraryStatus(clusterId, library, service), status);
            }
        };
    }

    private static LibraryInstallStatusDTO getLibraryStatus(final String clusterId,
                                                            final LibraryDTO library,
                                                            final LibraryService service) throws IOException,
        DatabricksRestException {
        LibraryFullStatusDTO[] libraryFullStatuses = service.clusterStatus(String.valueOf(clusterId)).getLibraryFullStatuses();
        for (LibraryFullStatusDTO libraryFullStatusDTO : libraryFullStatuses) {
            if (Objects.equals(libraryFullStatusDTO.getLibrary().getJar(), library.getJar())) {
                return libraryFullStatusDTO.getStatus();
            }
        }
        return LibraryInstallStatusDTO.PENDING;
    }
}
