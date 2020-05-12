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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.nulabinc.backlog4j.BacklogAPIException;
import com.nulabinc.backlog4j.BacklogClient;
import com.nulabinc.backlog4j.BacklogClientFactory;
import com.nulabinc.backlog4j.Project;
import com.nulabinc.backlog4j.ResponseList;
import com.nulabinc.backlog4j.Wiki;
import com.nulabinc.backlog4j.conf.BacklogConfigure;
import com.nulabinc.backlog4j.conf.BacklogJpConfigure;

/**
 * Backlog Wiki をローカルにコピーします。
 * 
 * @author Toshiki Iga
 */
public class BacklogWiki2Local {
    private BacklogClient bklClient = null;
    private Project bklProj = null;

    /**
     * Backlog API にログインします。
     * 
     * @param spaceKey Space key.
     * @param apiKey API key.
     * @param projectId Project ID.
     * @throws IOException IO例外が発生した場合。
     */
    public void login(final String spaceKey, final String apiKey, final String projectId) throws IOException {
        final BacklogConfigure bklConfig = new BacklogJpConfigure(spaceKey).apiKey(apiKey);
        bklClient = new BacklogClientFactory(bklConfig).newClient();
        try {
            bklProj = bklClient.getProject(projectId);
        } catch (BacklogAPIException ex) {
            throw new IOException("Login Failed:" + ex.toString(), ex);
        }
    }

    /**
     * 指定のディレクトリに Wiki を出力します。
     * 
     * @param outputdir Wiki 書き出し先ディレクトリ。
     * @throws IOException IO例外が発生した場合。
     */
    public void process(final File outputdir) throws IOException {
        if (bklClient == null || bklProj == null) {
            throw new IllegalArgumentException("Not connected to Backlog. Please login() before process().");
        }

        // Wiki名の一覧を取得します。
        List<Wiki> wikiEntryList = getWikiList();

        // Wiki をアルファベット順にソート
        Collections.sort(wikiEntryList, new Comparator<Wiki>() {
            @Override
            public int compare(Wiki o1, Wiki o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        if (outputdir.exists() == false) {
            outputdir.mkdir();
        }

        for (Wiki wikiEntry : wikiEntryList) {
            System.err.println("Download: " + wikiEntry.getName());

            Wiki lookup = bklClient.getWiki(wikiEntry.getId());
            FileUtils.write(new File(outputdir, wikiEntry.getName() + ".md"), lookup.getContent(), "UTF-8");
        }
    }

    /**
     * Wikiの一覧を取得します。
     * 
     * @return Wiki の一覧。
     */
    protected List<Wiki> getWikiList() {
        List<Wiki> result = new ArrayList<>();

        ResponseList<Wiki> wikiList = bklClient.getWikis(bklProj.getId());
        for (Wiki wikiLookup : wikiList) {
            result.add(wikiLookup);
        }

        return result;
    }
}
