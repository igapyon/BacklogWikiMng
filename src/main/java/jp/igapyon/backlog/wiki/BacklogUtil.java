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

import java.io.IOException;

import com.nulabinc.backlog4j.BacklogAPIException;
import com.nulabinc.backlog4j.BacklogClientFactory;
import com.nulabinc.backlog4j.conf.BacklogConfigure;
import com.nulabinc.backlog4j.conf.BacklogJpConfigure;

/**
 * Backlog Wiki をローカルにコピーします。
 * 
 * @author Toshiki Iga
 */
public class BacklogUtil {
    /**
     * Backlog API にログインします。
     * 
     * @param spaceKey Space key.
     * @param apiKey API key.
     * @param projectId Project ID.
     * @return Backlogへの接続情報。
     * @throws IOException IO例外が発生した場合。
     */
    public static BacklogConnection login(final String spaceKey, final String apiKey, final String projectId)
            throws IOException {
        BacklogConnection bklConn = new BacklogConnection();

        final BacklogConfigure bklConfig = new BacklogJpConfigure(spaceKey).apiKey(apiKey);
        bklConn.setClient(new BacklogClientFactory(bklConfig).newClient());
        try {
            bklConn.setProject(bklConn.getClient().getProject(projectId));
        } catch (BacklogAPIException ex) {
            throw new IOException("Login Failed:" + ex.toString(), ex);
        }

        return bklConn;
    }
}
