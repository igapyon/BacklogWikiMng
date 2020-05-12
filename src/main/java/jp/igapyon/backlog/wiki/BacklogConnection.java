/*
 * Copyright 2020 Toshiki Iga
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
package jp.igapyon.backlog.wiki;

import com.nulabinc.backlog4j.BacklogClient;
import com.nulabinc.backlog4j.Project;

/**
 * Backlog への接続情報。
 * 
 * @author Toshiki Iga
 */
public class BacklogConnection {
    private BacklogClient bklClient = null;
    private Project bklProj = null;

    public BacklogClient getClient() {
        return bklClient;
    }

    public void setClient(BacklogClient bklClient) {
        this.bklClient = bklClient;
    }

    public Project getProject() {
        return bklProj;
    }

    public void setProject(Project bklProj) {
        this.bklProj = bklProj;
    }
}
